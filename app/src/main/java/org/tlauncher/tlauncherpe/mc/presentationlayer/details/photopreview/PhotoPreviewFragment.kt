package org.tlauncher.tlauncherpe.mc.presentationlayer.details.photopreview

import android.os.Bundle
import com.morfly.cleanarchitecture.core.presentationlayer.BaseFragment
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.databinding.FragmentPhotoPreviewBinding
import org.tlauncher.tlauncherpe.mc.presentationlayer.details.ItemDetailPresenter
import org.tlauncher.tlauncherpe.mc.presentationlayer.details.ItemDetailsContract
import java.util.*
import kotlin.collections.ArrayList

class PhotoPreviewFragment : BaseFragment<ItemDetailsContract.Presenter, FragmentPhotoPreviewBinding>(), ItemDetailsContract.View {
    override fun openExp() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun loadingState(loading: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val IMAGES_DATA_KEY = "images_data_key"
        val CURRENT_ITEM_KEY = "current_item_key"

        fun newInstance(data : List<String>?, currentItem : Int) : PhotoPreviewFragment {
            val fragment = PhotoPreviewFragment()
            val bundle = Bundle()
            bundle.putStringArrayList(IMAGES_DATA_KEY, ArrayList(data))
            bundle.putInt(CURRENT_ITEM_KEY, currentItem)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun getViewModelBindingId() : Int = 0

    override fun getLayoutId() : Int = R.layout.fragment_photo_preview

    override fun onCreateView(savedInstanceState : Bundle?) {
        binding.presenter = presenter
        binding.pager.adapter = PhotoAdapter(arguments?.getStringArrayList(IMAGES_DATA_KEY) ?: Collections.emptyList())
        binding.pager.currentItem = arguments?.getInt(CURRENT_ITEM_KEY) ?: 0
    }

    override fun inject() {
        presenter = ItemDetailPresenter(null)
    }

    override fun onItemDownloadCancel(id: Int, type: String, mime: List<String>) {

    }

    override fun showPreview() {

    }

    override fun closePhotoPreview() {
        fragmentManager.popBackStack()
    }

    override fun onItemDownloaded() {

    }

    override fun downloadApplication() {
        
    }
}