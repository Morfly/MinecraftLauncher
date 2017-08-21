package org.tlauncher.tlauncherpe.mc.presentationlayer.server

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.MenuItem
import org.tlauncher.tlauncherpe.mc.MinecraftApp
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.presentationlayer.main.di.ActivityComponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.di.ServerModul

class ServerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_server)

        // component
        getComponents()

        // replace fragment
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, ServerFragment())
                .addToBackStack(null)
                .commit()

        // add arrow to actionBar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setBackgroundDrawable(resources.getDrawable(R.drawable.tollbar_background))
    }

    // init component
    val component : ActivityComponent by lazy { createComponent() }

    // fun getter component
    fun getComponents() : ActivityComponent {
        return component
    }

    // add backPressed listener
    override fun onOptionsItemSelected(item : MenuItem?) : Boolean {
        if (item?.itemId == android.R.id.home) {
            finish()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    // create component
    private fun createComponent() =
            MinecraftApp.Companion.getComponent(this)
                    .plusActivityComponent()
                    .activityModule(ServerModul(this))
                    .build()

    fun fabButtonClick() {

        // check if minecraft PE installed in phone
        if (isPackageInstalled("com.mojang.minecraftpe", packageManager)) {
            val intent : Intent = packageManager.getLaunchIntentForPackage("com.mojang.minecraftpe")

            // check if intent pac is minecraft, and start this game
            if (intent.`package` == "com.mojang.minecraftpe") {
                startActivity(intent)
            }
        } else {

            // show dialog and download game if need
            downloadApplication()
        }
    }

    // check is package is installed
    fun isPackageInstalled(packageName : String, packageManager : PackageManager) : Boolean {
        try {
            packageManager.getPackageInfo(packageName, 0)
            return true
        } catch (e : PackageManager.NameNotFoundException) {
            return false
        }
    }

    // show dialog and download game
    fun downloadApplication() {
        val customBuilder = AlertDialog.Builder(this)
                //.setTitle(resources.getString(R.string.alert_no_app))
                .setNegativeButton(android.R.string.cancel, null)
                .setPositiveButton(android.R.string.ok, { dialog, _ ->
                    run {
                        dialog.dismiss()
                    }
                })
        val dialogView = layoutInflater.inflate(R.layout.alert_start_game, null)
        customBuilder.setView(dialogView)
        val dialog = customBuilder.create()
        dialog.show()
        // set green color in dialog button
        val negativeButton = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        negativeButton?.setTextColor(resources.getColor(R.color.progress_color))
        val positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
        positiveButton?.setTextColor(resources.getColor(R.color.progress_color))
    }
}
