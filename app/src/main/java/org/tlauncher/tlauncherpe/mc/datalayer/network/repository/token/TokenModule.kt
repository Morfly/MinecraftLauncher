package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.token

import dagger.Module
import dagger.Provides
import org.tlauncher.tlauncherpe.mc.FirebaseInstanceIDService

@Module
class TokenModule {

    @Provides
    internal fun provideToken(): FirebaseInstanceIDService{
        return FirebaseInstanceIDService()
    }
}