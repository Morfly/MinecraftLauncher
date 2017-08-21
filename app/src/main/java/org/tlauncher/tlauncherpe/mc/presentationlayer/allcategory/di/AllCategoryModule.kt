package org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Binds
import dagger.Module
import org.tlauncher.tlauncherpe.mc.domainlayer.allcategory.AllCategoryInteractor
import org.tlauncher.tlauncherpe.mc.domainlayer.allcategory.AllCategoryUseCase
import org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory.AllCategoryContract
import org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory.AllCategoryPresenter

/**
 * Created by 85064 on 26.07.2017.
 */
@Module
interface AllCategoryModule {

    @PerFragment
    @Binds
    fun getAllCategoryInteractor(allCategoryUseCase: AllCategoryUseCase): AllCategoryInteractor

    @Binds
    @PerFragment
    fun allCategoryPresenter(allCategoryPresenter: AllCategoryPresenter) : AllCategoryContract.Presenter
}