package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.versions

import io.reactivex.Single
import org.tlauncher.tlauncherpe.mc.datalayer.network.RestApi
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.GameVersionsResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class VersionsRetrofitRepository
@Inject
constructor(private val restApi: RestApi): VersionsRepository{
    override fun getVersionsList(): Single<GameVersionsResponse> {
        return restApi.getGameVersions()
    }
}