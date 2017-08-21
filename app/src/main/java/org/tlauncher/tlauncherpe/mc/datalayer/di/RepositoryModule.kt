package org.tlauncher.tlauncherpe.mc.datalayer.di

import dagger.Binds
import dagger.Module
import org.tlauncher.tlauncherpe.mc.ActivityScope
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.map.MapsRepository
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.map.MapsRetrofitRepository
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.servers.ServersRepository
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.servers.ServersRetrofitRepository

@Module
abstract class RepositoryModule {

    @ActivityScope
    @Binds
    abstract fun provideRepository(repository: MapsRetrofitRepository): MapsRepository

    @ActivityScope
    @Binds
    abstract fun provideServerRepository(repository:ServersRetrofitRepository):ServersRepository
}