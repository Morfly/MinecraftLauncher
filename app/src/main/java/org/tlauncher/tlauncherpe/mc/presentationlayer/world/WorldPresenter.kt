package org.tlauncher.tlauncherpe.mc.presentationlayer.world

import android.content.Context
import android.util.Log
import com.astamobi.kristendate.domainlayer.extention.applyApiRequestSchedulers
import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import org.json.JSONArray
import org.tlauncher.tlauncherpe.mc.*
import org.tlauncher.tlauncherpe.mc.datalayer.network.entity.MyMods
import org.tlauncher.tlauncherpe.mc.domainlayer.world.WorldInteractor
import java.util.*
import javax.inject.Inject

@PerFragment
class WorldPresenter
@Inject
constructor(val worldInteractor: WorldInteractor): WorldContract.Presenter(){

    init {
        viewModel = WorldViewModel()
    }

    override fun getListData(context: Context, refresh: Boolean,lang: Int) {
        //if phone have Internet connection load data from Internet else from DB
        if (getLastLoadedLangWor(context) == getLang(context)) {
            if (getUpdates(context) == 1) {
                if (NetworkUtils.isNetworkConnected(context, true)) {
                    subscribe(worldInteractor.getListData(refresh, lang)
                            .applyApiRequestSchedulers()
                            .subscribe({
                                view.setListData(it, refresh)
                                saveLastLoadedLangWor(lang, context)
                            }, {}))
                } else {
                    subscribe(worldInteractor./*getWorldLIstFromDb*/getWorldLIstFromDbWithChack()
                            .applyApiRequestSchedulers()
                            .subscribe({ view.setListData(it, true) }, { view.showError(it) }))
                }
            } else {
                subscribe(worldInteractor./*getWorldLIstFromDb*/getWorldLIstFromDbWithChack()
                        .applyApiRequestSchedulers()
                        .subscribe({ view.setListData(it, true) }, { view.showError(it) }))
            }
        }else{
            if (NetworkUtils.isNetworkConnected(context, true)) {
                subscribe(worldInteractor.getListData(refresh, lang)
                        .applyApiRequestSchedulers()
                        .subscribe({
                            view.setListData(it, refresh)
                            saveLastLoadedLangWor(lang, context)
                        }, {}))
            } else {
                subscribe(worldInteractor./*getWorldLIstFromDb*/getWorldLIstFromDbWithChack()
                        .applyApiRequestSchedulers()
                        .subscribe({ view.setListData(it, true) }, { view.showError(it) }))
            }
        }
    }

    override fun downloadCounter(id: Int) {
        subscribe(worldInteractor.downloadCounter(id)
                .applyApiRequestSchedulers()
                .toObservable()
                .subscribe({ /*view.setListData(it)*/ }, { view.showError(it) }))
    }


    override fun startDownload(url:String/*, pos: Int*/) {
        view.onDownloadButtonClick(url)
    }

    override fun cancelDownload() {
        view.showMessage("cancelDownload")
    }

    override fun openDetailInfo() {
        view.showMessage("openDetailInfo")
    }

    override fun saveToMy(myMods: MyMods,loadsState: Int) {
        //if loadsState=1 reload list else just save data to DB
        subscribe(worldInteractor.saveMyWorldsList(myMods)
                .applyApiRequestSchedulers()
                .subscribe({
                    if (loadsState == 1) {
                        getMyList()
                    }
                },Throwable::printStackTrace))
    }

    override fun getMyList() {
        subscribe(worldInteractor.getMyWorldsList()
                ?.applyApiRequestSchedulers()
                ?.subscribe({ view.setListData(it,true) }, { view.showError(it) }))
    }
    override fun removeMyModById(id: Int) {
        subscribe(worldInteractor.removeMyModById(id)
                .applyApiRequestSchedulers()
                .subscribe({},Throwable::printStackTrace))
    }

    override fun getMyModById(id: Int, path: String) {
        subscribe(worldInteractor.getMyModsById(id)
                .applyApiRequestSchedulers()
                .subscribe({ Log.w("esfe","fweffe")},Throwable::printStackTrace))
    }

    override fun saveCheckedData(checkMap: HashMap<Int, String>, array: JSONArray) {
        view.saveCheckedData(checkMap, array)
    }
}