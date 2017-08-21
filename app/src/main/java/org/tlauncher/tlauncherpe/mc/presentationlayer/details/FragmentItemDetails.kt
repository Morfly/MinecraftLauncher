package org.tlauncher.tlauncherpe.mc.presentationlayer.details

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.opengl.Visibility
import android.os.Bundle
import android.os.Environment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.morfly.cleanarchitecture.core.presentationlayer.BaseFragment
import com.morfly.cleanarchitecture.core.presentationlayer.SelectedActivity
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.tlauncher.tlauncherpe.mc.*
import org.tlauncher.tlauncherpe.mc.databinding.FragmentDetailsBinding
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.*
import org.tlauncher.tlauncherpe.mc.presentationlayer.details.photopreview.PhotoPreviewFragment
import org.tlauncher.tlauncherpe.mc.presentationlayer.explorer.FragmentExplorer
import org.tlauncher.tlauncherpe.mc.presentationlayer.main.MainActivity
import java.util.*


class FragmentItemDetails : BaseFragment<ItemDetailsContract.Presenter, FragmentDetailsBinding>(), ItemDetailsContract.View {
    companion object {
        val ITEM_DETAILS_KEY = "item_details_key"
    }

    var loading: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        //set language selected in settings
        if (getLang(activity) == 1){
            changeLanguage("ru")
        }else{
            changeLanguage("en")
        }
    }

    override fun getViewModelBindingId(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.fragment_details

    override fun openExp() {
        val intent = SelectedActivity.getStartIntent(activity, FragmentExplorer::class.java.name, null)
        intent.putExtra(SelectedActivity.ACTIVITY_THEME, R.style.SelectedTheme)
        startActivity(intent)
    }

    override fun onCreateView(savedInstanceState: Bundle?) {
        binding.progress.progressDrawable.setColorFilter(ContextCompat.getColor(context, R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
        val viewModel: ItemDetailsViewModel? = arguments?.getParcelable(ITEM_DETAILS_KEY)
        presenter.viewModel = viewModel
        binding.presenter = presenter
        binding.recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerView.adapter = ImagesAdapter(viewModel?.images ?: Collections.emptyList(), this::onPhotoClick)
        binding.recyclerView.addItemDecoration(SpaceItemDecorator(resources.getDimensionPixelOffset(R.dimen.images_spacing)))
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(
                ContextCompat.getDrawable(context, R.drawable.tollbar_background)
        )
        when (viewModel?.type) {
            ItemDetailsViewModel.Type.Addon -> updateTitle(R.string.addons, R.string.addons_description)
            ItemDetailsViewModel.Type.Texture -> updateTitle(R.string.textures, R.string.textures_description)
            ItemDetailsViewModel.Type.World -> updateTitle(R.string.worlds, R.string.worlds_description)
            ItemDetailsViewModel.Type.Skin -> updateTitle(R.string.skins, R.string.skins_description)
            ItemDetailsViewModel.Type.Sid -> updateTitle(R.string.sids, R.string.sids_description)
        }
    }

    private fun updateTitle(titleId: Int, descriptionId: Int) {
        activity.setTitle(titleId)
        binding.descriptionTitle.text = getString(R.string.mode_description, getString(descriptionId))
    }

    fun onPhotoClick(imageUrl: String) {
        presenter.viewModel?.currentImage?.set(imageUrl)
    }

    override fun inject() {
        MinecraftApp.instance?.component?.plusDetailsComponent()?.build()?.inject(this)
    }

    override fun onItemDownloadCancel(id: Int, type: String, mime: List<String>) {
        Toast.makeText(context,context.resources.getString(R.string.pause_load),Toast.LENGTH_SHORT).show()
        val dialog = AlertDialog.Builder(context)
                .setTitle(context.resources.getString(R.string.alert_success))
                .setMessage(context.resources.getString(R.string.you_really_want_stop_loading))
                .setPositiveButton(android.R.string.yes, { _, _ -> presenter.onDownloadCancel() })
                .setNegativeButton(android.R.string.no, { _, _->
                    val viewModel: ItemDetailsViewModel? = arguments?.getParcelable(ITEM_DETAILS_KEY)
                    val path = "${Environment.getExternalStorageDirectory()}$type${mime[mime.size - 1]}"
                    FileLoader.getLoad(id)
                            .subscribeOn(AndroidSchedulers.mainThread())
                            .subscribe({
                                val progress = ((it.bytesRead * 100) / it.contentLength).toInt()
                                if (progress == 100) {
                                    val action = if (mime.contains("zip")) {
                                        unz(path, "${Environment.getExternalStorageDirectory()}$type", viewModel?.id!!)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .flatMapCompletable {
                                                    return@flatMapCompletable presenter.saveToDB(it.path)
                                                }
                                    } else {
                                        presenter.saveToDB(path)
                                    }
                                    /*action.subscribe({
                                                binding.buttonLoad.text = activity.resources.getString(R.string.downloaded)
                                                binding.progress.visibility = View.GONE
                                                onItemDownloaded()
                                            }, {})*/
                                }
                                //if (!isPAuse) {
                                    binding.progress.progress = progress
                                //}
                            },{
                                Log.w("res","error")
                            })
                })
                .show()
        val negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        negativeButton?.setTextColor(resources.getColor(R.color.progress_color))
        val positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        positiveButton?.setTextColor(resources.getColor(R.color.progress_color))
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.item_details_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.action_share) {
            val text = activity.resources.getString(R.string.download)+" ${presenter.viewModel.name} " + activity.resources.getString(R.string.downl_text)
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_TEXT, text)
            startActivity(Intent.createChooser(intent, activity.resources.getString(R.string.share)))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showPreview() {
        val list = presenter.viewModel.images
        val currentItem = list?.indexOf(presenter.viewModel.currentImage.get())
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, PhotoPreviewFragment.newInstance(list, currentItem ?: 0))
                .addToBackStack(null)
                .commit()
    }

    override fun closePhotoPreview() {

    }

    override fun onItemDownloaded() {
        activity.setResult(Activity.RESULT_OK)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            1 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


                }
                return
            }
        }
    }

    override fun loadingState(loading: Boolean) {
        this.loading = loading
    }

    override fun onDestroy() {
        super.onDestroy()
        if (loading) {
            val viewModel: ItemDetailsViewModel? = arguments?.getParcelable(ITEM_DETAILS_KEY)
            val stringRes = when (viewModel?.type!!) {
                ItemDetailsViewModel.Type.Addon -> R.string.addons_folder
                ItemDetailsViewModel.Type.Texture -> R.string.textures_folder
                ItemDetailsViewModel.Type.World -> R.string.maps_folder
                ItemDetailsViewModel.Type.Skin -> R.string.skins_folder
                ItemDetailsViewModel.Type.Sid -> R.string.sid_folder
            }
            val type = activity.resources.getString(stringRes)
            val mime = viewModel.downloadUrl!!.split("/")
            val path = "${Environment.getExternalStorageDirectory()}$type${mime[mime.size - 1]}"
            val intent = Intent(Constants.UNZIP)
            if (viewModel.downloadUrl.contains("zip")) {
                intent.putExtra(Constants.MIME, "zip")
            } else {
                intent.putExtra(Constants.MIME, "")
            }
            intent.putExtra(Constants.RES_ID, viewModel.id)
            intent.putExtra(Constants.PATH, path)
            intent.putExtra(Constants.TYPE, type)
            intent.putExtra(Constants.DETAIL_ITEM, viewModel)
            activity.sendBroadcast(intent)
        }
    }

    // show dialog and download game
    override fun downloadApplication() {
        val customBuilder = AlertDialog.Builder(context)
                //.setTitle(resources.getString(R.string.alert_no_app))
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, { dialog, _ ->
                    run {
                        dialog.dismiss()
                    }
                })
        val dialogView = activity.layoutInflater.inflate(R.layout.alert_start_game, null)
        customBuilder.setView(dialogView)
        val dialog = customBuilder.create()
        dialog.show()
        // set green color in dialog button
        val negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        negativeButton?.setTextColor(context.resources.getColor(R.color.progress_color))
        val positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        positiveButton?.setTextColor(context.resources.getColor(R.color.progress_color))
    }
}