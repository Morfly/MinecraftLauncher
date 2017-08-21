package org.tlauncher.tlauncherpe.mc.presentationlayer.texture.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Subcomponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.texture.TextureFragment


@PerFragment
@Subcomponent(modules = arrayOf(TextureModule::class))
interface TextureComponent {
    @Subcomponent.Builder
    interface Builder{
        fun build(): TextureComponent
    }

    fun inject(textureFragment: TextureFragment)
}