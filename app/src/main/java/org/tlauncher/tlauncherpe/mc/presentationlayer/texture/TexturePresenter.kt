package org.tlauncher.tlauncherpe.mc.presentationlayer.texture

import android.content.Context
import android.util.Log
import com.astamobi.kristendate.domainlayer.extention.applyApiRequestSchedulers
import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import org.json.JSONArray
import org.tlauncher.tlauncherpe.mc.*
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MyTextures
import org.tlauncher.tlauncherpe.mc.domainlayer.texture.TextureInteractor
import java.util.*
import javax.inject.Inject

@PerFragment
class TexturePresenter
@Inject
constructor(val textureInteractor: TextureInteractor): TextureContract.Presenter(){

    init {
        viewModel = TextureViewModel()
    }

    override fun getListData(context: Context, refresh: Boolean,lang: Int) {
        //if phone have Internet connection load data from Internet else from DB
        if (getLastLoadedLangTex(context) == getLang(context)) {
            if (getUpdates(context) == 1) {
                if (NetworkUtils.isNetworkConnected(context, true)) {
                    subscribe(textureInteractor.getListData(refresh, lang)
                            .applyApiRequestSchedulers()
                            .subscribe({
                                view.setListData(it, refresh)
                                saveLastLoadedLangTex(lang, context)
                            }, {}))
                } else {
                    subscribe(textureInteractor./*getWorldLIstFromDb*/getTextureLIstFromDbWithChack()
                            .applyApiRequestSchedulers()
                            .subscribe({ view.setListData(it, true) }, { view.showError(it) }))
                }
            } else {
                subscribe(textureInteractor./*getWorldLIstFromDb*/getTextureLIstFromDbWithChack()
                        .applyApiRequestSchedulers()
                        .subscribe({ view.setListData(it, true) }, { view.showError(it) }))
            }
        }else{
            if (NetworkUtils.isNetworkConnected(context, true)) {
                subscribe(textureInteractor.getListData(refresh, lang)
                        .applyApiRequestSchedulers()
                        .subscribe({
                            view.setListData(it, refresh)
                            saveLastLoadedLangTex(lang, context)
                        }, {}))
            } else {
                subscribe(textureInteractor./*getWorldLIstFromDb*/getTextureLIstFromDbWithChack()
                        .applyApiRequestSchedulers()
                        .subscribe({ view.setListData(it, true) }, { view.showError(it) }))
            }
        }
    }

    override fun startDownload(url: String) {
        view.onDownloadButtonClick(url)
    }

    override fun cancelDownload() {
        view.showMessage("cancelDownload")
    }

    override fun openDetailInfo() {
        view.showMessage("openDetailInfo")
    }

    override fun downloadCounter(id: Int) {
        subscribe(textureInteractor.downloadCounter(id)
                .applyApiRequestSchedulers()
                .toObservable()
                .subscribe({ /*view.setListData(it)*/ }, { view.showError(it) }))
    }

    override fun saveToMy(myTextures: MyTextures, loadsState: Int) {
        //if loadsState=1 reload list else just save data to DB
        subscribe(textureInteractor.saveMyTexturesList(myTextures)
                .applyApiRequestSchedulers()
                .subscribe({
                    if (loadsState == 1) {
                        getMyList()
                    }
                },Throwable::printStackTrace))
    }

    override fun getMyList() {
        subscribe(textureInteractor.getMyTexturesList()
                ?.applyApiRequestSchedulers()
                ?.subscribe({ view.setListData(it, true) }, { view.showError(it) }))
    }

    override fun removeMyTextureById(id: Int) {
        subscribe(textureInteractor.removeMyTextureById(id)
                .applyApiRequestSchedulers()
                .subscribe({},Throwable::printStackTrace))
    }

    override fun getMyTextureById(id: Int, path: String) {
        subscribe(textureInteractor.getMyTexturesById(id)
                .applyApiRequestSchedulers()
                .subscribe({ Log.w("esfe","fweffe")},Throwable::printStackTrace))
    }

    override fun updateMyTextureItem(myTextures: MyTextures, pos: Int, secondPos: Int) {
        subscribe(textureInteractor.updateMyItem(myTextures)
                .applyApiRequestSchedulers()
                .subscribe({view.updateList(pos,secondPos)},Throwable::printStackTrace))
    }

    override fun saveCheckedData(checkMap: HashMap<Int, String>, array: JSONArray) {
        view.saveCheckedData(checkMap, array)
    }
}