package org.tlauncher.tlauncherpe.mc.presentationlayer.server

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.morfly.cleanarchitecture.core.presentationlayer.BaseFragment
import org.tlauncher.tlauncherpe.mc.BR
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.databinding.FragmentServersBinding
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.adapter.ServerAdapter

class ServerFragment : BaseFragment<ServerContract.Presenter, FragmentServersBinding>(), ServerContract.View, View.OnClickListener {
    override fun onClick(v : View?) {

    }

    var listData : ArrayList<ServerItemViewModel> = ArrayList()
    var layoutManager : LinearLayoutManager? = null
    var adapter : ServerAdapter? = null

    override fun getViewModelBindingId() : Int = BR.viewModel

    override fun getLayoutId() : Int = R.layout.fragment_servers

    override fun inject() {

//        ApplicationComponent.getComponent(context)
//                .plusActivityComponent()
//                .build()
//                .inject(this)

        (activity as ServerActivity)
                .getComponents()
                .plusServerComponent()
                .build()
                .inject(this)
    }

    override fun setListServers(list : ArrayList<ServerItemViewModel>, refresh : Boolean) {
        if (refresh) {
            listData = list
            binding.listOfServers.isNestedScrollingEnabled = true
            layoutManager = LinearLayoutManager(activity, LinearLayout.VERTICAL, false)
            binding.listOfServers.layoutManager = layoutManager
            binding.listOfServers.adapter = adapter
            val decorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            decorator.setDrawable(ContextCompat.getDrawable(context, R.drawable.list_divider))
            binding.listOfServers.addItemDecoration(decorator)
            adapter?.updateItem(list)
        }
    }

    // fab button click
    override fun fabButtonClick() {
        (activity as ServerActivity).fabButtonClick()
    }

    override fun onCreateView(savedInstanceState : Bundle?) {
        binding.presenter = presenter
        adapter = ServerAdapter(ArrayList<ServerItemViewModel>(), this, presenter, activity)
        presenter.getServerList(activity, true)
    }
}