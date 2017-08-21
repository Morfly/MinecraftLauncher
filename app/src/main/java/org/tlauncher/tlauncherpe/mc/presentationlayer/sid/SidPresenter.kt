package org.tlauncher.tlauncherpe.mc.presentationlayer.sid

import android.content.Context
import android.util.Log
import com.astamobi.kristendate.domainlayer.extention.applyApiRequestSchedulers
import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import org.json.JSONArray
import org.tlauncher.tlauncherpe.mc.*
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MySids
import org.tlauncher.tlauncherpe.mc.domainlayer.sid.SidInteractor
import java.util.*
import javax.inject.Inject

@PerFragment
class SidPresenter
@Inject
constructor(val sidInteractor: SidInteractor): SidContract.Presenter(){

    init {
        viewModel = SidViewModel()
    }

    override fun getListData(context: Context, refresh: Boolean,lang: Int) {
        //if phone have Internet connection load data from Internet else from DB
        if (getLastLoadedLangSid(context) == getLang(context)) {
            if (getUpdates(context) == 1) {
                if (NetworkUtils.isNetworkConnected(context, true)) {
                    subscribe(sidInteractor.getListData(refresh, lang)
                            .applyApiRequestSchedulers()
                            .subscribe({
                                view.setListData(it, refresh)
                                saveLastLoadedLangSid(lang, context)
                            }, {}))
                } else {
                    subscribe(sidInteractor./*getWorldLIstFromDb*/getSidLIstFromDbWithChack()
                            .applyApiRequestSchedulers()
                            .subscribe({ view.setListData(it, true) }, { view.showError(it) }))
                }
            } else {
                subscribe(sidInteractor./*getWorldLIstFromDb*/getSidLIstFromDbWithChack()
                        .applyApiRequestSchedulers()
                        .subscribe({ view.setListData(it, true) }, { view.showError(it) }))
            }
        }else{
            if (NetworkUtils.isNetworkConnected(context, true)) {
                subscribe(sidInteractor.getListData(refresh, lang)
                        .applyApiRequestSchedulers()
                        .subscribe({
                            view.setListData(it, refresh)
                            saveLastLoadedLangSid(lang, context)
                        }, {}))
            } else {
                subscribe(sidInteractor./*getWorldLIstFromDb*/getSidLIstFromDbWithChack()
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
        subscribe(sidInteractor.downloadCounter(id)
                .applyApiRequestSchedulers()
                .toObservable()
                .subscribe({ /*view.setListData(it)*/ }, { view.showError(it) }))
    }

    override fun saveToMy(mySids: MySids, loadsState: Int) {
        //if loadsState=1 reload list else just save data to DB
        subscribe(sidInteractor.saveMySidsList(mySids)
                .applyApiRequestSchedulers()
                .subscribe({
                    if (loadsState == 1) {
                        getMyList()
                    }
                },Throwable::printStackTrace))
    }

    override fun getMyList() {
        subscribe(sidInteractor.getMySidsList()
                ?.applyApiRequestSchedulers()
                ?.subscribe({ view.setListData(it,true) }, { view.showError(it) }))
    }

    override fun removeMySidById(id: Int) {
        subscribe(sidInteractor.removeMySidById(id)
                .applyApiRequestSchedulers()
                .subscribe({},Throwable::printStackTrace))
    }

    override fun getMySidById(id: Int, path: String) {
        subscribe(sidInteractor.getMySidsById(id)
                .applyApiRequestSchedulers()
                .subscribe({ Log.w("esfe","fweffe")},Throwable::printStackTrace))
    }

    override fun saveCheckedData(checkMap: HashMap<Int, String>, array: JSONArray) {
        view.saveCheckedData(checkMap, array)
    }
}