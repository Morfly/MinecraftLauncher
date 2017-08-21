package org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory

import android.util.Log
import com.astamobi.kristendate.domainlayer.extention.applyApiRequestSchedulers
import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import org.tlauncher.tlauncherpe.mc.Constants
import org.tlauncher.tlauncherpe.mc.domainlayer.allcategory.AllCategoryInteractor
import java.util.ArrayList
import javax.inject.Inject

/**
 * Created by 85064 on 26.07.2017.
 */
@PerFragment
class AllCategoryPresenter
@Inject
constructor(val allCategoryInteractor: AllCategoryInteractor): AllCategoryContract.Presenter(){

    override fun getList(refresh: Boolean) {
        var list: ArrayList<AllCategoryItemViewModel> = arrayListOf()
        subscribe(allCategoryInteractor.getAllListsOfData(refresh)
                .applyApiRequestSchedulers()
                .subscribe({
                    list = it
                    subscribe(allCategoryInteractor.getWorldListData(refresh,1)
                            .applyApiRequestSchedulers()
                            .subscribe({
                                list.add(AllCategoryItemViewModel(Constants.HEADER, Constants.WORLD,null))
                                var counter = 0
                                it.forEachIndexed { index, worldItemViewModel ->
                                    if (counter < 4) {
                                        val data: Data = Data()
                                        data.id = worldItemViewModel.id
                                        data.category = worldItemViewModel.category
                                        data.date = worldItemViewModel.date
                                        data.downloads = worldItemViewModel.downloads
                                        data.filePath = worldItemViewModel.filePath
                                        data.fileSize = worldItemViewModel.fileSize
                                        data.image = worldItemViewModel.image
                                        data.loaded = worldItemViewModel.loaded
                                        data.name = worldItemViewModel.name
                                        data.text = worldItemViewModel.text
                                        data.type = worldItemViewModel.type
                                        data.url = worldItemViewModel.url
                                        data.views = worldItemViewModel.views
                                        list.add(AllCategoryItemViewModel(Constants.ITEMS, Constants.WORLD, data))
                                        counter ++
                                    }
                                }
                                subscribe(allCategoryInteractor.getSkinListData(refresh,1)
                                        .applyApiRequestSchedulers()
                                        .subscribe({
                                            list.add(AllCategoryItemViewModel(Constants.HEADER,Constants.SKIN,null))
                                            var counter1 = 0
                                            it.forEachIndexed { index, skinItemViewModel ->
                                                if (counter1 < 4) {
                                                    val data: Data = Data()
                                                    data.id = skinItemViewModel.id
                                                    data.category = skinItemViewModel.category
                                                    data.date = skinItemViewModel.date
                                                    data.downloads = skinItemViewModel.downloads
                                                    data.filePath = skinItemViewModel.filePath
                                                    data.fileSize = skinItemViewModel.fileSize
                                                    data.image = skinItemViewModel.image
                                                    data.isImported = skinItemViewModel.isImported
                                                    data.loaded = skinItemViewModel.loaded
                                                    data.name = skinItemViewModel.name
                                                    data.text = skinItemViewModel.text
                                                    data.type = skinItemViewModel.type
                                                    data.url = skinItemViewModel.url
                                                    data.views = skinItemViewModel.views
                                                    list.add(AllCategoryItemViewModel(Constants.ITEMS, Constants.SKIN, data))
                                                    counter1++
                                                }
                                            }
                                            subscribe(allCategoryInteractor.getSidListData(refresh,1)
                                                    .applyApiRequestSchedulers()
                                                    .subscribe({
                                                        list.add(AllCategoryItemViewModel(Constants.HEADER,Constants.SID,null))
                                                        var counter2 = 0
                                                        it.forEachIndexed { index, sidItemViewModel ->
                                                            if (counter2 < 4) {
                                                                val data: Data = Data()
                                                                data.id = sidItemViewModel.id
                                                                data.category = sidItemViewModel.category
                                                                data.date = sidItemViewModel.date
                                                                data.downloads = sidItemViewModel.downloads
                                                                data.filePath = sidItemViewModel.filePath
                                                                data.fileSize = sidItemViewModel.fileSize
                                                                data.image = sidItemViewModel.image
                                                                data.loaded = sidItemViewModel.loaded
                                                                data.name = sidItemViewModel.name
                                                                data.text = sidItemViewModel.text
                                                                data.type = sidItemViewModel.type
                                                                data.url = sidItemViewModel.url
                                                                data.views = sidItemViewModel.views
                                                                list.add(AllCategoryItemViewModel(Constants.ITEMS, Constants.SID, data))
                                                                counter2++
                                                            }
                                                        }
                                                        view.setList(list)
                                                    },{Log.e("getSidListDataError",it.message)}))
                                        },{Log.e("getSkinListDataError",it.message)}))
                            },{ Log.e("getWorldListDataError",it.message)}))
                },{ Log.e("getAllListsOfDataError",it.message)}))
    }
}