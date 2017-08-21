package org.tlauncher.tlauncherpe.mc

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.util.Log
import io.realm.Realm
import org.tlauncher.tlauncherpe.mc.datalayer.di.ApplicationModule
import org.tlauncher.tlauncherpe.mc.datalayer.di.BindingComponent
import org.tlauncher.tlauncherpe.mc.datalayer.di.RetrofitApiModule
import java.util.*

open class MinecraftApp : Application() {

    //region Singleton
    public val component : ApplicationComponent by lazy {
        return@lazy DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .retrofitApiModule(RetrofitApiModule(BuildConfig.API_ENDPOINT))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (getLang(this) == 1){
            changeLanguage("ru")
        }else{
            changeLanguage("en")
        }
        Realm.init(this)
        DataBindingUtil.setDefaultComponent(BindingComponent())
        val intent = Intent(this, UnzipService::class.java)
        startService(intent)
    }

    companion object {
        var instance : MinecraftApp? = null
            private set
            get

        fun getComponent(context : Context) : ApplicationComponent {
            val application = context.applicationContext as MinecraftApp
            return application.component
        }

        fun  getApplication(): Any {return MinecraftApp}
    }

    fun changeLanguage(language: String) {
        /**
         * Change language
         */
        if (language.equals("", ignoreCase = true)) {
            return
        }

        val locale = Locale(language)
        saveLocaleToSharedPreferences(language)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)

        baseContext.resources
                .updateConfiguration(configuration, baseContext
                        .resources.displayMetrics)
    }

    private fun saveLocaleToSharedPreferences(language: String) {
        /**
         * Save data languages from SharedPreferences
         */
        val languagePreferences = "Language"

        val sharedPreferences = this
                .getSharedPreferences("StringKey", Activity.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putString(languagePreferences, language)
        editor.apply()
    }

    fun loadLocaleFromSharedPreferences() {
        /**
         * Load data languages from SharedPreferences
         */
        val languagePreferences = "Language"
        val sharedPreferences = getSharedPreferences("StringKey", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString(languagePreferences, "")
        try {
            changeLanguage(language)
            Log.i("tag", "Change language successfully")
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e("tag", "Error, not changed language")
        }

    }
}