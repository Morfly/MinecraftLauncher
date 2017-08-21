package org.tlauncher.tlauncherpe.mc.presentationlayer.skin

import android.content.Context
import android.util.Log
import com.astamobi.kristendate.domainlayer.extention.applyApiRequestSchedulers
import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import org.json.JSONArray
import org.tlauncher.tlauncherpe.mc.*
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MySkins
import org.tlauncher.tlauncherpe.mc.domainlayer.skin.SkinInteractor
import java.util.*
import javax.inject.Inject

@PerFragment
class SkinPresenter
@Inject
constructor(val skinInteractor: SkinInteractor): SkinContract.Presenter(){

    init {
        viewModel = SkinViewModel()
    }

    override fun getListData(context: Context, refresh: Boolean,lang: Int) {
        //if phone have Internet connection load data from Internet else from DB
        if (getLastLoadedLangSkin(context) == getLang(context)) {
            if (getUpdates(context) == 1) {
                if (NetworkUtils.isNetworkConnected(context, true)) {
                    subscribe(skinInteractor.getListData(refresh, lang)
                            .applyApiRequestSchedulers()
                            .subscribe({
                                view.setListData(it, refresh)
                                saveLastLoadedLangSkin(lang, context)
                            }, {}))
                } else {
                    subscribe(skinInteractor./*getWorldLIstFromDb*/getSkinLIstFromDbWithChack()
                            .applyApiRequestSchedulers()
                            .subscribe({ view.setListData(it, true) }, { view.showError(it) }))
                }
            } else {
                subscribe(skinInteractor./*getWorldLIstFromDb*/getSkinLIstFromDbWithChack()
                        .applyApiRequestSchedulers()
                        .subscribe({ view.setListData(it, true) }, { view.showError(it) }))
            }
        }else{
            if (NetworkUtils.isNetworkConnected(context, true)) {
                subscribe(skinInteractor.getListData(refresh, lang)
                        .applyApiRequestSchedulers()
                        .subscribe({
                            view.setListData(it, refresh)
                            saveLastLoadedLangSkin(lang, context)
                        }, {}))
            } else {
                subscribe(skinInteractor./*getWorldLIstFromDb*/getSkinLIstFromDbWithChack()
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
        subscribe(skinInteractor.downloadCounter(id)
                .applyApiRequestSchedulers()
                .toObservable()
                .subscribe({ /*view.setListData(it)*/ }, { view.showError(it) }))
    }

    override fun saveToMy(mySkins: MySkins, loadsState: Int) {
        //if loadsState=1 reload list else just save data to DB
        subscribe(skinInteractor.saveMySkinsList(mySkins)
                .applyApiRequestSchedulers()
                .subscribe({
                    if (loadsState == 1) {
                        getMyList()
                    }
                },Throwable::printStackTrace))
    }

    override fun getMyList() {
        subscribe(skinInteractor.getMySkinsList()
                ?.applyApiRequestSchedulers()
                ?.subscribe({ view.setListData(it,true) }, { view.showError(it) }))
    }

    override fun removeMySkinById(id: Int) {
        subscribe(skinInteractor.removeMySkinById(id)
                .applyApiRequestSchedulers()
                .subscribe({},Throwable::printStackTrace))
    }

    override fun getMySkinById(id: Int, path: String) {
        subscribe(skinInteractor.getMySkinsById(id)
                .applyApiRequestSchedulers()
                .subscribe({ Log.w("esfe","fweffe")},Throwable::printStackTrace))
    }

    override fun updateMySkinItem(mySkins: MySkins, pos: Int, secondPos: Int) {
        subscribe(skinInteractor.updateMyItem(mySkins)
                .applyApiRequestSchedulers()
                .subscribe({view.updateList(pos,secondPos)},Throwable::printStackTrace))
    }

    override fun saveCheckedData(checkMap: HashMap<Int, String>, array: JSONArray) {
        view.saveCheckedData(checkMap, array)
    }
}