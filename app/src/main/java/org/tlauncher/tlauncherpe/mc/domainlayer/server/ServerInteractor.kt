package org.tlauncher.tlauncherpe.mc.domainlayer.server

import android.util.Log
import com.morfly.cleanarchitecture.core.di.scope.PerFragment
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import org.tlauncher.tlauncherpe.mc.datalayer.db.DbManager
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.servers.ServersRepository
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.ServerItemViewModel
import javax.inject.Inject

interface ServerInteractor {
    fun loadServers() : Single<ArrayList<ServerItemViewModel>>
}

@PerFragment
class ServerUseCase
@Inject
constructor(private val serversRepository : ServersRepository,
            private val database : DbManager) : ServerInteractor {

    override fun loadServers() : Single<ArrayList<ServerItemViewModel>> {
        return serversRepository.getListServersFromRetrofit()
                .doOnSuccess {
                    it.servers?.forEachIndexed { _, servers ->
                        database.saveServers(servers)
                                .andThen(database.getServers())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({ Log.d("TAG", it.name + " " + it.text + " " + it.ip) },
                                        Throwable::printStackTrace)
                    }
                }
                .flatMap {
                    database.getServers()
                            .toList()
                            .map {
                                val list : ArrayList<ServerItemViewModel> = ArrayList()
                                it.forEachIndexed { _, servers ->
                                    list.add(ServerItemViewModel(servers.id, servers.name, servers.text, servers.ip,
                                            servers.rank, servers.versions))
                                }
                                return@map list
                            }
                }
    }
}