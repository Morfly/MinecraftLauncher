package org.tlauncher.tlauncherpe.mc.datalayer.network.repository.token

import io.reactivex.Observable
import org.tlauncher.tlauncherpe.mc.datalayer.network.response.SaveTokenResponse

/**
 * Created by 85064 on 19.07.2017.
 */
interface TokenRepository {
    fun sandToken(uid: String): Observable<SaveTokenResponse>
}