package org.tlauncher.tlauncherpe.mc.presentationlayer.texture.di

import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import dagger.Binds
import dagger.Module
import org.tlauncher.tlauncherpe.mc.domainlayer.texture.TextureInteractor
import org.tlauncher.tlauncherpe.mc.domainlayer.texture.TextureUseCase
import org.tlauncher.tlauncherpe.mc.presentationlayer.texture.TextureContract
import org.tlauncher.tlauncherpe.mc.presentationlayer.texture.TexturePresenter

@Module
interface TextureModule {

    @PerFragment
    @Binds
    fun getTextureInteractor(textureUseCase: TextureUseCase): TextureInteractor

    @Binds
    @PerFragment
    fun texturePresenter(texturePresenter: TexturePresenter) : TextureContract.Presenter
}