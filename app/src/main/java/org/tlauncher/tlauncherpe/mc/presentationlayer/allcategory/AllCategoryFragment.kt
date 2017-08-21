package org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.morfly.cleanarchitecture.BR
import com.morfly.cleanarchitecture.core.presentationlayer.BaseFragment
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.databinding.FragmentAllCategoryBinding
import org.tlauncher.tlauncherpe.mc.getLang
import org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory.adapter.AllCategoryAdapter
import org.tlauncher.tlauncherpe.mc.presentationlayer.main.MainActivity

/**
 * Created by 85064 on 26.07.2017.
 */
class AllCategoryFragment: BaseFragment<AllCategoryContract.Presenter,FragmentAllCategoryBinding>()
        ,AllCategoryContract.View, View.OnClickListener {

    var layoutManager : LinearLayoutManager? = null
    var adapter : AllCategoryAdapter? = null

    override fun getViewModelBindingId() = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_all_category

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding.presenter = presenter
        adapter = AllCategoryAdapter(ArrayList<AllCategoryItemViewModel>(), this, presenter, activity, activity)
        presenter.getList(false)
    }

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        //setHasOptionsMenu(true)
        //set language selected in settings
        if (getLang(activity) == 1){
            changeLanguage("ru")
        }else{
            changeLanguage("en")
        }
    }

    override fun inject() {
        (activity as MainActivity)
                .getComponents()
                .plusAllCategoryComponent()
                .build()
                .inject(this)
    }

    override fun setList(list: ArrayList<AllCategoryItemViewModel>) {
        binding.list.isNestedScrollingEnabled = true
        layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
        binding.list.layoutManager = layoutManager
        binding.list.adapter = adapter
    }

    override fun onClick(v: View?) {

    }
}