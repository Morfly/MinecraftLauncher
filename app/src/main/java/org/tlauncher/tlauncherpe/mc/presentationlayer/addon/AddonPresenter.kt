package org.tlauncher.tlauncherpe.mc.presentationlayer.addon

import android.content.Context
import android.util.Log
import com.astamobi.kristendate.domainlayer.extention.applyApiRequestSchedulers
import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import org.json.JSONArray
import org.tlauncher.tlauncherpe.mc.*
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MyAddons
import org.tlauncher.tlauncherpe.mc.domainlayer.addon.AddonInteractor
import java.util.*
import javax.inject.Inject

@PerFragment
class AddonPresenter
@Inject
constructor(val addonInteractor: AddonInteractor): AddonContract.Presenter(){

    init {
        viewModel = AddonViewModel()
    }

    override fun getListData(context: Context, refresh: Boolean,lang: Int) {
        //if phone have Internet connection load data from Internet else from DB
        if (getLastLoadedLang(context) == getLang(context)) {
            if (getUpdates(context) == 1) {
                if (NetworkUtils.isNetworkConnected(context, true)) {
                    subscribe(addonInteractor.getListData(refresh, lang)
                            .applyApiRequestSchedulers()
                            .subscribe({
                                view.setListData(it, refresh)
                                saveLastLoadedLang(lang, context)
                            }, {}))
                } else {
                    subscribe(addonInteractor./*getWorldLIstFromDb*/getAddonLIstFromDbWithChack()
                            .applyApiRequestSchedulers()
                            .subscribe({ view.setListData(it, true) }, { view.showError(it) }))
                }
            } else {
                subscribe(addonInteractor./*getWorldLIstFromDb*/getAddonLIstFromDbWithChack()
                        .applyApiRequestSchedulers()
                        .subscribe({ view.setListData(it, true) }, { view.showError(it) }))
            }
        }else{
            if (NetworkUtils.isNetworkConnected(context, true)) {
                subscribe(addonInteractor.getListData(refresh, lang)
                        .applyApiRequestSchedulers()
                        .subscribe({
                            view.setListData(it, refresh)
                            saveLastLoadedLang(lang, context)
                        }, {}))
            } else {
                subscribe(addonInteractor./*getWorldLIstFromDb*/getAddonLIstFromDbWithChack()
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
        subscribe(addonInteractor.downloadCounter(id)
                .applyApiRequestSchedulers()
                .toObservable()
                .subscribe({ /*view.setListData(it)*/ }, { view.showError(it) }))
    }

    override fun saveToMy(myAddons: MyAddons, loadsState: Int) {
        //if loadsState=1 reload list else just save data to DB
        subscribe(addonInteractor.saveMyAddonsList(myAddons)
                .applyApiRequestSchedulers()
                .subscribe({
                    if (loadsState == 1) {
                        getMyList()
                    }
                },Throwable::printStackTrace))
    }

    override fun getMyList() {
        subscribe(addonInteractor.getMyAddonsList()
                ?.applyApiRequestSchedulers()
                ?.subscribe({ view.setListData(it,true) }, { view.showError(it) }))
    }

    override fun removeMyAddonById(id: Int) {
        subscribe(addonInteractor.removeMyAddonById(id)
                .applyApiRequestSchedulers()
                .subscribe({},Throwable::printStackTrace))
    }

    override fun getMyAddonById(id: Int, path: String) {
        subscribe(addonInteractor.getMyAddonsById(id)
                .applyApiRequestSchedulers()
                .subscribe({ Log.w("esfe","fweffe")},Throwable::printStackTrace))
    }

    override fun updateMyAddonItem(myAddons: MyAddons, pos: Int, secondPos: Int) {
        subscribe(addonInteractor.updateMyItem(myAddons)
                .applyApiRequestSchedulers()
                .subscribe({view.updateList(pos,secondPos)},Throwable::printStackTrace))
    }

    override fun saveCheckedData(checkMap: HashMap<Int, String>, array: JSONArray) {
        view.saveCheckedData(checkMap, array)
    }
}