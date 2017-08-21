package org.tlauncher.tlauncherpe.mc.presentationlayer.details.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Subcomponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.details.FragmentItemDetails

@PerFragment
@Subcomponent(modules = arrayOf(DetailsModule::class))
interface DetailsComponent {
    @Subcomponent.Builder
    interface Builder {
        fun build() : DetailsComponent
    }

    fun inject(fragment : FragmentItemDetails)
}