package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.token

import io.reactivex.Observable
import org.tlauncher.tlauncherpe.mc.datalayer.network.RestApi
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.SaveTokenResponse
import javax.inject.Inject

/**
 * Created by 85064 on 19.07.2017.
 */
class TokenRetrofitRepository
@Inject
constructor(private val restApi: RestApi): TokenRepository{

    override fun sandToken(uid: String): Observable<SaveTokenResponse> {
        return restApi.saveToken(uid)
    }
}