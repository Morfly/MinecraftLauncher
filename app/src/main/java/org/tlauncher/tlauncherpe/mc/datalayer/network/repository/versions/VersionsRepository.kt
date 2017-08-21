package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.versions

import io.reactivex.Single
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.GameVersionsResponse

interface VersionsRepository {

    fun getVersionsList(): Single<GameVersionsResponse>
}