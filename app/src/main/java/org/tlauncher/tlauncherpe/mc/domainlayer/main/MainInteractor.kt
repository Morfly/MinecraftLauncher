package org.tlauncher.tlauncherpe.mc.domainlayer.main

import com.morfly.cleanarchitecture.core.di.scope.PerActivity
import javax.inject.Inject

interface MainInteractor {
}

@PerActivity
class MainUseCase
@Inject
constructor(): MainInteractor {

}