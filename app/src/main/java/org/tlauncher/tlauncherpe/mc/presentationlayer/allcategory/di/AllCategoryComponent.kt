package org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Subcomponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.allcategory.AllCategoryFragment

/**
 * Created by 85064 on 26.07.2017.
 */
@PerFragment
@Subcomponent(modules = arrayOf(AllCategoryModule::class))
interface AllCategoryComponent {
    @Subcomponent.Builder
    interface Builder{
        fun build(): AllCategoryComponent
    }

    fun inject(allCategoryFragment: AllCategoryFragment)
}