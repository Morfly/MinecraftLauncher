package org.tlauncher.tlauncherpe.mc

import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService
import org.tlauncher.tlauncherpe.mc.datalayer.network.RestApi
import org.tlauncher.tlauncherpe.mc.datalayer.network.repository.token.TokenRetrofitRepository
import javax.inject.Inject

/**
 * Created by 85064_000 on 07.12.2016.
 */

open class FirebaseInstanceIDService
@Inject
constructor() : FirebaseInstanceIdService() {

    @Inject
    lateinit var tokenRepository: TokenRetrofitRepository

    override fun onTokenRefresh() {
            MinecraftApp.getComponent(applicationContext)
                    .plusFirebaseInstanceIDService()
                    .build().inject(this)
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d(TAG, "Refreshed token: " + refreshedToken!!)
        sendRegistrationToServer(refreshedToken)
    }

    private fun sendRegistrationToServer(token: String) {
        val userId = Settings.Secure.getString(applicationContext.contentResolver,
                Settings.Secure.ANDROID_ID)
        if (!TextUtils.isEmpty(userId)) {
            UpdateToken(userId, token)
            tokenRepository.sandToken(token)
        }

    }

    companion object {

        private val TAG = "FirebaseIIDService"

        val token: String
            get() = FirebaseInstanceId.getInstance().token!!
    }
}