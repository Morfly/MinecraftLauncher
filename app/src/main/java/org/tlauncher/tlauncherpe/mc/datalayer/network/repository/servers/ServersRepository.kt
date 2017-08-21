package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.servers

import io.reactivex.Single
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.GameServersResponse

interface ServersRepository {
    fun getListServersFromRetrofit() : Single<GameServersResponse>
}