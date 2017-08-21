package org.tlauncher.tlauncherpe.mc.presentationlayer.explorer

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import org.tlauncher.tlauncherpe.mc.MinecraftApp
import org.tlauncher.tlauncherpe.mc.R
import org.tlauncher.tlauncherpe.mc.presentationlayer.explorer.di.ExplorerModul
import org.tlauncher.tlauncherpe.mc.presentationlayer.main.di.ActivityComponent
import java.util.*

class ExplorerActivity: AppCompatActivity() {

    var type: String = ""
    var dateObj: String = ""
    val chackStrings: MutableList<String> = mutableListOf<String>()
    var array: String = ""
    var path: String = ""

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explorer)

        // component
        getComponents()

        type = intent.getSerializableExtra("type").toString()
        val hashMap = intent.getSerializableExtra("chackStrings")
        array = intent.getSerializableExtra("array").toString()
        dateObj = intent.getSerializableExtra("dateObj").toString()
        path = intent.getSerializableExtra("path").toString()
        (hashMap as HashMap<Int, String>).entries.forEach {
            chackStrings.add(it.value)
        }

        // replace fragment
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, FragmentExplorer(chackStrings,array,dateObj,type, path))
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
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    // create component
    private fun createComponent() =
            MinecraftApp.Companion.getComponent(this)
                    .plusActivityComponent()
                    .activityModule(ExplorerModul(this))
                    .build()

    fun openThree(path: String){
        supportFragmentManager
                .beginTransaction()
                .add(R.id.container, FragmentExplorer(chackStrings,array,dateObj,type, path))
                .addToBackStack(path)
                .commit()
    }
}