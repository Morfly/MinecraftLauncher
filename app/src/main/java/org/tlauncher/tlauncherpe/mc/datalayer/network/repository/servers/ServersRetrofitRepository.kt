package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.servers

import io.reactivex.Single
import org.tlauncher.tlauncherpe.mc.datalayer.db.DbManager
import org.tlauncher.tlauncherpe.mc.datalayer.network.RestApi
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.GameServersResponse
import javax.inject.Inject

class ServersRetrofitRepository
@Inject
constructor(private val restApi : RestApi, private val database : DbManager) : ServersRepository {

    override fun getListServersFromRetrofit() : Single<GameServersResponse> {
        return restApi.getGameServers()
    }
}
