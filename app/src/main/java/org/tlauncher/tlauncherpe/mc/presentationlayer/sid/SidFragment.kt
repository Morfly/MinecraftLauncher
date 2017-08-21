package org.tlauncher.tlauncherpe.mc.presentationlayer.sid

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import com.astamobi.kristendate.domainlayer.extention.applyApiRequestSchedulers
import com.github.nuptboyzhb.lib.SuperSwipeRefreshLayout
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.morfly.cleanarchitecture.core.presentationlayer.BaseFragment
import com.morfly.cleanarchitecture.core.presentationlayer.SelectedActivity
import org.json.JSONArray
import org.tlauncher.tlauncherpe.mc.*
import org.tlauncher.tlauncherpe.mc.databinding.FragmentSidBinding
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MySids
import org.tlauncher.tlauncherpe.mc.presentationlayer.addon.unzipM
import org.tlauncher.tlauncherpe.mc.presentationlayer.details.FragmentItemDetails
import org.tlauncher.tlauncherpe.mc.presentationlayer.details.ItemDetailsViewModel
import org.tlauncher.tlauncherpe.mc.presentationlayer.explorer.ExplorerActivity
import org.tlauncher.tlauncherpe.mc.presentationlayer.main.MainActivity
import org.tlauncher.tlauncherpe.mc.presentationlayer.main.MainActivity.Companion.DETAILS_REQUEST_CODE
import org.tlauncher.tlauncherpe.mc.presentationlayer.sid.adapter.SidAdapter
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class SidFragment : BaseFragment<SidContract.Presenter, FragmentSidBinding>(), SidContract.View,
        View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener {

    var options : MenuItem? = null
    var adapter : SidAdapter? = null
    var listData : MutableList<SidItemViewModel> = ArrayList()
    var isShow : Boolean = false
    var firstSort : Int = 0
    var layoutManager : LinearLayoutManager? = null
    var sortState: Int = 0
    var screenState: String = ""
    var chackMap: HashMap<Int,String> = HashMap()
    var array: JSONArray = JSONArray()

    override fun setListData(list : ArrayList<SidItemViewModel>, refresh : Boolean) {
        //if refresh=true clear list and add new data.
        //if refresh=false add loaded data to list
        if (refresh) {
            listData = list
            binding.list.isNestedScrollingEnabled = true
            layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
            binding.list.layoutManager = layoutManager
            binding.list.adapter = adapter
            adapter?.updateItem(list)
            //isShow need for show My sids vews
            adapter?.showMyIcons(isShow)
            adapter?.showCheckB(false)
            binding.swipeRefresh.isRefreshing = false
        } else {
            val start = listData.size
            listData.addAll(list)
            //adapter?.showMyIcons(isShow)
            adapter?.addNewItems(isShow, start, listData.size)
            binding.swipeRefresh.isRefreshing = false
        }
        //screenStare know if screen state is change
        //if (!screenState.equals("")) {
        if (refresh){
            //sort data after load or change screen state
            when (sortState) {
                0 -> {
                    adapter?.sortList("date")
                    screenState = ""
                }
                1 -> {
                    adapter?.sortList("name")
                    screenState = ""
                }
                2 -> {
                    adapter?.sortList("fileSize")
                    screenState = ""
                }
            }
        }
        binding.firstSort.setSelection(firstSort)
    }

    override fun getViewModelBindingId() = BR.viewModel

    override fun getLayoutId() : Int = R.layout.fragment_sid

    override fun onDownloadButtonClick(url : String) {

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //get second spinner selection position
        if (savedInstanceState?.getInt("sortState") != null) {
            sortState = savedInstanceState.getInt("sortState")
        }
        //get first spinner selection position
        if (savedInstanceState?.getInt("firstSort") != null) {
            firstSort = savedInstanceState.getInt("firstSort")
            saveSidFAB(firstSort,activity)
        }
        //get screen stage
        if (savedInstanceState?.getString("screenState") != null){
            screenState = savedInstanceState.getString("screenState")
        }
    }

    override fun onCreateView(savedInstanceState : Bundle?) {
        binding.presenter = presenter
        adapter = SidAdapter(ArrayList<SidItemViewModel>(), this, presenter, activity, activity)
        //presenter.getListData(activity)
        //set to second spinner items
        val secondAd: ArrayAdapter<CharSequence>? = ArrayAdapter.createFromResource(activity,
                R.array.secondSort, android.R.layout.simple_spinner_item)
        secondAd?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.secondSort.adapter = secondAd

        binding.secondSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent : AdapterView<*>?, view : View?, position : Int, id : Long) {
                when (position) {
                    0 -> {
                        adapter?.sortList("date")
                        sortState = 0
                    }
                    1 -> {
                        adapter?.sortList("name")
                        sortState = 1
                    }
                    2 -> {
                        adapter?.sortList("fileSize")
                        sortState = 2
                    }
                }
            }

            override fun onNothingSelected(parent : AdapterView<*>) {}
        }
        //set to first spinner items
        val firstAd: ArrayAdapter<CharSequence>? = ArrayAdapter.createFromResource(activity,
                R.array.firstSortSids, android.R.layout.simple_spinner_item)
        firstAd?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.firstSort.adapter = firstAd

        binding.bottomNavView.setOnNavigationItemSelectedListener(this)
        binding.firstSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent : AdapterView<*>?, view : View?, position : Int, id : Long) {
                when (position) {
                    0 -> {
                        options?.isVisible = false
                        firstSort = 0
                        saveSidFAB(firstSort,activity)
                        //(activity as? MainActivity)?.hideShowFabButton(false)
                        (activity as? MainActivity)?.hideShowFabButton("sid")
                        binding.bottomNavView.visibility = View.GONE
                        //set language selected in settings
                        if (getLang(context) == 1) {
                            presenter.getListData(activity, true, 1)
                        }else{
                            presenter.getListData(activity, true, 2)
                        }
                        isShow = false
                        adapter?.clearList()
                        binding.buttonImport.visibility = View.GONE
                    }
                    1 -> {
                        options?.isVisible = true
                        firstSort = 1
                        saveSidFAB(firstSort,activity)
                        //(activity as? MainActivity)?.hideShowFabButton(true)
                        (activity as? MainActivity)?.hideShowFabButton("sid")
                        //binding.bottomNavView.visibility = View.VISIBLE
                        presenter.getMyList()
                        isShow = true
                        adapter?.clearList()
                        binding.buttonImport.visibility = View.VISIBLE
                    }
                }
            }

            override fun onNothingSelected(parent : AdapterView<*>) {}
        }
        //add swipe refresh listener
        val v : View = LayoutInflater.from(activity).inflate(R.layout.pull_to_refresh_header, null, false)
        binding.swipeRefresh.setHeaderView(v)
        binding.swipeRefresh.isTargetScrollWithLayout = true
        binding.swipeRefresh.setOnPullRefreshListener(object : SuperSwipeRefreshLayout.OnPullRefreshListener {

            override fun onRefresh() {
                when (firstSort) {
                    0 -> {
                        if (getLang(context) == 1) {
                            presenter.getListData(activity, true, 1)
                        }else{
                            presenter.getListData(activity, true, 2)
                        }
                    }
                    1 -> presenter.getMyList()
                }
            }

            override fun onPullDistance(distance : Int) {
                Log.e("SwipeRefresh", "onPullDistance")
            }

            override fun onPullEnable(enable : Boolean) {
                Log.e("SwipeRefresh", "onPullEnable")
            }
        })
        binding.list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val pos : Int = layoutManager?.findFirstVisibleItemPosition()!!
                val last : Int = layoutManager?.findLastCompletelyVisibleItemPosition()!!
                //if last visible list item is last, load new page and add to list
                if (listData.size - 1 == last) {
                    //if (dy < 0) {
                        //load data only if catalog list(in My list always all data from DB)
                        if (firstSort == 0) {
                            if (NetworkUtils.isNetworkConnected(context, true)) {
                                if (getLang(context) == 1) {
                                    presenter.getListData(activity, false, 1)
                                } else {
                                    presenter.getListData(activity, false, 2)
                                }
                            }
                        }
                    //}
                }
                binding.swipeRefresh.isEnabled = pos == 0
                Log.e("ScrollPos", pos.toString())
            }
        })

        //open file manager to choose file to import in app
        binding.buttonImport.setOnClickListener {
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "file/*"
            startActivityForResult(i, 1)
        }
    }

    override fun onResume() {
        super.onResume()
    }


    override fun inject() {
        (activity as MainActivity)
                .getComponents()
                .plusSidComponent()
                .build()
                .inject(this)
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        //set language selected in settings
        if (getLang(activity) == 1){
            changeLanguage("ru")
        }else{
            changeLanguage("en")
        }
    }

    override fun onOptionsItemSelected(item : MenuItem?) : Boolean {
        if (item?.itemId == R.id.highlight_all) {
            //show checkBoxes and check all of My list items
            //adapter?.chackAll(true)
            adapter?.checkAllAndShowCheck(true)
            binding.bottomNavView.visibility = View.VISIBLE
        } else if (item?.itemId == R.id.highlight) {
            //just show checkBoxes
            adapter?.showCheckB(true)
            binding.bottomNavView.visibility = View.VISIBLE
        }else if(item?.itemId == R.id.ubackup){
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "file/*"
            startActivityForResult(i, 105)
        }else if (item?.itemId == R.id.backup_all){
            listData.forEachIndexed { index, sidItemViewModel ->
                chackMap.put(sidItemViewModel.id,sidItemViewModel.filePath!!)
            }
            array = SidJsonCreator(listData as ArrayList<SidItemViewModel>)
            backupList()
        }
        return super.onOptionsItemSelected(item)
    }

    fun backupList(){
        val d = Date()
        val curFormater = SimpleDateFormat("dd-MM-yyyy")
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = d.time
        val dateObj = curFormater.format(calendar.time)
        val chackStrings: MutableList<String> = mutableListOf<String>()
        chackMap.entries.forEach {
            chackStrings.add(it.value)
        }
        val f = File(Environment.getExternalStorageDirectory().toString() +
                context.resources.getString(R.string.backup_sids))
        if (f.exists()) {

        } else {
            if (!f.isDirectory) {
                f.mkdirs()
            }
        }
        val path = Environment.getExternalStorageDirectory()
        val myIntent = Intent(activity, ExplorerActivity::class.java)
        myIntent.putExtra("array", array.toString())
        myIntent.putExtra("chackStrings",chackMap)
        myIntent.putExtra("dateObj",dateObj)
        myIntent.putExtra("type","sid")
        myIntent.putExtra("path",path)
        activity.startActivity(myIntent)
    }

    override fun onCreateOptionsMenu(menu : Menu?, inflater : MenuInflater?) {
        inflater?.inflate(R.menu.search, menu)
        super.onCreateOptionsMenu(menu, inflater)
        val menuItem = menu?.findItem(R.id.item_search)
        options = menu?.findItem(R.id.options)
        //options?.isVisible = false
        options?.isVisible = firstSort == 1
        val searchView : SearchView = menuItem?.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query : String?) : Boolean {
                return false
            }

            override fun onQueryTextChange(newText : String?) : Boolean {
                if (newText.isNullOrEmpty().not()) {
                    binding.swipeRefresh.isEnabled = false
                    var dat : MutableList<SidItemViewModel> = ArrayList()
                    listData.forEachIndexed { _, sidItemViewModel ->
                        var txt : String = newText!!
                        if (sidItemViewModel.name!!.contains(txt, true)) {
                            dat.add(sidItemViewModel)
                        }
                    }
                    adapter?.updateItem(dat as ArrayList<SidItemViewModel>)
                } else {
                    adapter?.updateItem(listData as ArrayList<SidItemViewModel>)
                }
                return false
            }
        })
        searchView.setOnCloseListener {
            binding.swipeRefresh.isEnabled = true
            return@setOnCloseListener false
        }
    }

    override fun onPause() {
        super.onPause()
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onNavigationItemSelected(item : MenuItem) : Boolean {
        when (item.itemId) {
            R.id.action_share -> {
                backupList()
            }
            R.id.action_delete -> adapter?.chackAll(false)

        }
        return true
    }

    override fun saveCheckedData(checkMap: HashMap<Int, String>, array: JSONArray) {
        this.chackMap = checkMap
        this.array = array
    }

    override fun onClick(p0 : View?) {
        //open detail info screen
        if (p0 != null && p0.tag != null && adapter != null) {
            val position = p0.tag as Int
            val viewModel = adapter!!.data[position]
            val bundle = Bundle()
            bundle.putParcelable(FragmentItemDetails.ITEM_DETAILS_KEY, ItemDetailsViewModel.map(viewModel))
            val intent = SelectedActivity.getStartIntent(context, FragmentItemDetails::class.java.name, bundle)
            startActivityForResult(intent, DETAILS_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MainActivity.DETAILS_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            //set language selected in settings
            if (getLang(context) == 1) {
                presenter.getListData(context, true, 1)
            }else{
                presenter.getListData(context, true, 2)
            }
        } else if (requestCode == 1) {
            //unzip selected in file system archive
            if (data != null) {
                val rand = Random()
                val n = 1000000 - 10000 + 1
                val i = Math.abs(rand.nextInt() % n)
                val path = data?.data?.path
                val mySid: MySids = MySids()
                unz(path!!, Environment.getExternalStorageDirectory().toString()
                        + context.resources.getString(R.string.sid_folder), i)
                        .subscribe({
                            mySid.name = it.name
                            mySid.id = it.id
                            mySid.filePath = it.path
                            presenter.saveToMy(mySid, 1)
                            Log.e("unzip", "success")
                        }, { Log.e("unzip", "error") })
                presenter.saveToMy(mySid, 1)
                Log.e("unzip", "success")
            }
        }else if(requestCode == 105){
            if (data != null) {
                val path = data?.data?.path
                unzipM(path!!, Environment.getExternalStorageDirectory().toString()
                        + context.resources.getString(R.string.sid_folder))
                        .applyApiRequestSchedulers()
                        .subscribe({
                            val file = File(path)
                            file.delete()
                            val json = JSONArray((activity as MainActivity).loadJSONFromAsset(it[it.size - 1]))
                            (0..json.length() - 1)
                                    .map { Gson().fromJson(Gson().fromJson(json.getJSONObject(it).toString(), JsonElement::class.java), MySids::class.java) }
                                    .forEach { presenter.saveToMy(it, 0) }
                            presenter.getMyList()
                            adapter?.showCheckB(false)
                            binding.bottomNavView.visibility = View.GONE
                            Log.e("unzip", "success")
                        }, {
                            Log.e("unzip", "error")
                        })
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        //save first spinner state
        outState?.putInt("firstSort",firstSort)
        //save second spinner state
        outState?.putInt("sortState", sortState)
        outState?.putString("screenState", "screenState")
    }
}