package org.tlauncher.tlauncherpe.mc.presentationlayer.main

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.databinding.DataBindingUtil
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.morfly.cleanarchitecture.core.presentationlayer.BaseActivity
import com.morfly.cleanarchitecture.core.presentationlayer.SelectedActivity
import kotlinx.android.synthetic.main.app_bar_activity_drawer.*
import org.tlauncher.tlauncherpe.mc.*
import org.tlauncher.tlauncherpe.mc.R.id.*
import org.tlauncher.tlauncherpe.mc.databinding.ActivityMainBinding
import org.tlauncher.tlauncherpe.mc.databinding.TabCustomItemBinding
import org.tlauncher.tlauncherpe.mc.presentationlayer.main.adapter.PagerAdapter
import org.tlauncher.tlauncherpe.mc.presentationlayer.main.di.ActivityComponent
import org.tlauncher.tlauncherpe.mc.presentationlayer.main.di.MainModuls
import org.tlauncher.tlauncherpe.mc.presentationlayer.server.ServerActivity
import org.tlauncher.tlauncherpe.mc.presentationlayer.setting.FragmentSetting
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.charset.Charset
import java.util.*

class MainActivity : BaseActivity<ActivityMainBinding>(), MainContract.View, NavigationView.OnNavigationItemSelectedListener {
    companion object {
        val DETAILS_REQUEST_CODE = 2001
    }

    var tabPosition: Int = 0
    var langState: Int = 0
    // presenter init
    val presenter : MainPresenter = MainPresenter()

    internal val PERMISSIONS_REQUEST_GET_ACCOUNTS = 123

    // init component
    val component : ActivityComponent by lazy { createComponent() }

    // fun getter component
    fun getComponents() : ActivityComponent {
        return component
    }

    // create component
    private fun createComponent() =
            MinecraftApp.Companion.getComponent(this)
                    .plusActivityComponent()
                    .activityModule(MainModuls(this))
                    .build()

    // set root layout
    override fun getLayoutId() = R.layout.activity_main

    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        //for set language after change in settings
        if (getLang(this) == 1){
            changeLanguage("ru")
        }else{
            changeLanguage("en")
        }

        // attach view
        presenter.attachView(this)

        // :D
        getComponents()

        // set root layout presenter
        binding.presenter = presenter

        // set fab button layout presenter
        binding.appBarActivityDrawer.contentActivityDrawer.presenter = presenter

        // check permissions read and write to external storage
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSIONS_REQUEST_GET_ACCOUNTS)
        } else {

            // java code in Kotlin Project, wtf?
            /*addFragment(new LogInFragment())*/
        }

        // array with text to tab layout
        val namesArray = resources.getStringArray(R.array.tabs)

        // array with icons to tab layout
        val iconsArray = intArrayOf(R.drawable.mods_selector, R.drawable.textures_selector,
                R.drawable.maps_selector, R.drawable.skins_selector, R.drawable.sids_selector)

        // set text <item>Моды</item> to tab layout
        binding.appBarActivityDrawer.contentActivityDrawer.tabLayout
                .addTab(binding.appBarActivityDrawer.contentActivityDrawer.tabLayout
                        .newTab().setText(namesArray[0].toString()))

        // set text <item>Текстуры</item> to tab layout
        binding.appBarActivityDrawer.contentActivityDrawer.tabLayout
                .addTab(binding.appBarActivityDrawer.contentActivityDrawer.tabLayout
                        .newTab().setText(namesArray[1].toString()))


        // set text <item>Миры</item> to tab layout
        binding.appBarActivityDrawer.contentActivityDrawer.tabLayout
                .addTab(binding.appBarActivityDrawer.contentActivityDrawer.tabLayout
                        .newTab().setText(namesArray[2].toString()))

        // set text <item>Скины</item> to tab layout
        binding.appBarActivityDrawer.contentActivityDrawer.tabLayout
                .addTab(binding.appBarActivityDrawer.contentActivityDrawer.tabLayout
                        .newTab().setText(namesArray[3].toString()))

        // set text  <item>Сиды</item> to tab layout
        binding.appBarActivityDrawer.contentActivityDrawer.tabLayout
                .addTab(binding.appBarActivityDrawer.contentActivityDrawer.tabLayout
                        .newTab().setText(namesArray[4].toString()))

        // set new toolbar
        setSupportActionBar(toolbar)

        // toggle button
        /*val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)*/
        val toggle = object : ActionBarDrawerToggle(this, binding.drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name
        ) {
            override fun onDrawerClosed(view: View?) {
                invalidateOptionsMenu()
            }

            override fun onDrawerOpened(drawerView: View?) {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
                invalidateOptionsMenu()
            }
        }
        // add DrawerListener
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()



        // set onClickListener to NavigationDrawer item list
        binding.navView.setNavigationItemSelectedListener(this)

        // set items to tab layout
        namesArray?.forEachIndexed { index, _ ->
            val tabCustomItemBinding : TabCustomItemBinding? = DataBindingUtil.inflate(
                    LayoutInflater.from(this), R.layout.tab_custom_item, null, false)

            // set text from array
            tabCustomItemBinding?.text?.text = namesArray[index]

            // set images from array
            tabCustomItemBinding?.image?.setImageDrawable(resources.getDrawable(iconsArray[index], null))

            // select tab from array
            binding.appBarActivityDrawer.contentActivityDrawer.tabLayout
                    .getTabAt(index)?.customView = tabCustomItemBinding?.root


        }

        /*binding.myFAB.setOnClickListener{
            if(isPackageInstalled("com.mojang.minecraftpe",packageManager)) {
                val intent: Intent = packageManager.getLaunchIntentForPackage("com.mojang.minecraftpe")
                if (intent.`package` == "com.mojang.minecraftpe") {
                    startActivity(intent)
                }
            }else{
                downloadApplication()
            }
        }*/

        // set counts tab to adapter
        val pagerAdapter : PagerAdapter = PagerAdapter(supportFragmentManager,
                binding.appBarActivityDrawer.contentActivityDrawer.tabLayout.tabCount)

        // set new custom adapter
        binding.appBarActivityDrawer.contentActivityDrawer.pager.adapter = pagerAdapter

        // add onPageChangeListener to tab layout
        binding.appBarActivityDrawer.contentActivityDrawer.pager
                .addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(
                        binding.appBarActivityDrawer.contentActivityDrawer.tabLayout))
        /////////
        //binding.appBarActivityDrawer.contentActivityDrawer.tabLayout.setSelectedTabIndicatorHeight(0)
        ////////
        // add tab layout selected listener
        binding.appBarActivityDrawer.contentActivityDrawer.tabLayout
                .addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

                    override fun onTabSelected(tab : TabLayout.Tab) {
                        binding.appBarActivityDrawer.contentActivityDrawer.pager.currentItem = tab.position
                        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        if (currentFocus != null) {
                            imm.hideSoftInputFromWindow(currentFocus.windowToken, 0)
                        }
                        tabPosition = tab.position
                        when (tab.position){
                            0->{
                                hideShowFabButton("addon")
                            }
                            1->{
                                hideShowFabButton("texture")
                            }
                            2->{
                                hideShowFabButton("world")
                            }
                            3->{
                                hideShowFabButton("skin")
                            }
                            4->{
                                hideShowFabButton("sid")
                            }
                        }
                    }

                    override fun onTabUnselected(tab : TabLayout.Tab) {

                    }

                    override fun onTabReselected(tab : TabLayout.Tab) {

                    }
                })

        val decorView = window.decorView
        decorView.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            val windowVisibleDisplayFrame = Rect()
            var lastVisibleDecorViewHeight : Int = 0
            val MIN_KEYBOARD_HEIGHT_PX : Int = 150

            override fun onGlobalLayout() {
                // Retrieve visible rectangle inside window.
                decorView.getWindowVisibleDisplayFrame(windowVisibleDisplayFrame)
                val visibleDecorViewHeight = windowVisibleDisplayFrame.height()

                // Decide whether keyboard is visible from changing decor view height.
                if (lastVisibleDecorViewHeight != 0) {
                    if (lastVisibleDecorViewHeight > visibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX) {
                        hideShowFabButtonR(true)
                        Log.w("keyboard", "show")
                    } else if (lastVisibleDecorViewHeight + MIN_KEYBOARD_HEIGHT_PX < visibleDecorViewHeight) {
                        //hideShowFabButtonR(false)
                        when (tabPosition){
                            0->{
                                hideShowFabButton("addon")
                            }
                            1->{
                                hideShowFabButton("texture")
                            }
                            2->{
                                hideShowFabButton("world")
                            }
                            3->{
                                hideShowFabButton("skin")
                            }
                            4->{
                                hideShowFabButton("sid")
                            }
                        }
                        Log.w("keyboard", "hide")
                    }
                }
                // Save current decor view height for the next call.
                lastVisibleDecorViewHeight = visibleDecorViewHeight
            }
        })
    }



    // fab button click
    override fun fabButtonClick() {

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

    // Navigation Drawer item listener
    override fun onNavigationItemSelected(item : MenuItem) : Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            menu_settings -> {
                val intent = SelectedActivity.getStartIntent(this, FragmentSetting::class.java.name, null)
                intent.putExtra(SelectedActivity.ACTIVITY_THEME, R.style.SelectedTheme)
                startActivityForResult(intent,228)
            }
            menu_servers -> {
                startActivity(Intent(this, ServerActivity::class.java))
            }
            menu_how_to_start -> Toast.makeText(this, "menu_how_to_start", Toast.LENGTH_SHORT).show()
            menu_how_to -> Toast.makeText(this, "menu_how_to", Toast.LENGTH_SHORT).show()
            menu_faq -> Toast.makeText(this, "menu_faq", Toast.LENGTH_SHORT).show()
            menu_about_app -> Toast.makeText(this, "menu_about_app", Toast.LENGTH_SHORT).show()
        }

        // close Navigation Drawer
        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    // close Navigation Drawer is user press back button
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
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

    // hide fab button
    /*fun hideShowFabButton(hide : Boolean) {
        if (hide) {
            binding.appBarActivityDrawer.contentActivityDrawer.btnFab.visibility = View.GONE
        } else {
            binding.appBarActivityDrawer.contentActivityDrawer.btnFab.visibility = View.VISIBLE
        }
    }*/

    fun hideShowFabButtonR(hide : Boolean) {
        if (hide) {
            binding.appBarActivityDrawer.contentActivityDrawer.btnFab.visibility = View.GONE
        } else {
            binding.appBarActivityDrawer.contentActivityDrawer.btnFab.visibility = View.VISIBLE
        }
    }

    fun hideShowFabButton(tabName: String) {
        when (tabName){
            "addon"->{
                if (tabPosition == 0) {
                    if (getAddonFAB(this@MainActivity) == 0) {
                        binding.appBarActivityDrawer.contentActivityDrawer.btnFab.visibility = View.VISIBLE
                    } else {
                        binding.appBarActivityDrawer.contentActivityDrawer.btnFab.visibility = View.GONE
                    }
                }
            }
            "texture"->{
                if (tabPosition == 1) {
                    if (getTextureFAB(this@MainActivity) == 0) {
                        binding.appBarActivityDrawer.contentActivityDrawer.btnFab.visibility = View.VISIBLE
                    } else {
                        binding.appBarActivityDrawer.contentActivityDrawer.btnFab.visibility = View.GONE
                    }
                }
            }
            "world"->{
                if (tabPosition == 2) {
                    if (getWorldFAB(this@MainActivity) == 0) {
                        binding.appBarActivityDrawer.contentActivityDrawer.btnFab.visibility = View.VISIBLE
                    } else {
                        binding.appBarActivityDrawer.contentActivityDrawer.btnFab.visibility = View.GONE
                    }
                }
            }
            "skin"->{
                if (tabPosition == 3) {
                    if (getSkinFAB(this@MainActivity) == 0) {
                        binding.appBarActivityDrawer.contentActivityDrawer.btnFab.visibility = View.VISIBLE
                    } else {
                        binding.appBarActivityDrawer.contentActivityDrawer.btnFab.visibility = View.GONE
                    }
                }
            }
            "sid"->{
                if (tabPosition == 4) {
                    if (getSidFAB(this@MainActivity) == 0) {
                        binding.appBarActivityDrawer.contentActivityDrawer.btnFab.visibility = View.VISIBLE
                    } else {
                        binding.appBarActivityDrawer.contentActivityDrawer.btnFab.visibility = View.GONE
                    }
                }
            }
        }
    }


    override fun onDestroy() {
        component.databaseManager.close()
        super.onDestroy()
    }

    // check permissions
    override fun onRequestPermissionsResult(requestCode : Int, permissions : Array<String>, grantResults : IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_GET_ACCOUNTS -> if (grantResults.isNotEmpty()
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                // java code in Kotlin Project, wtf?
                /*addFragment(new LogInFragment());*/
            } else {

            }
            else -> super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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

    //For remove directoris with maps, textures, addons in My lists
    fun removeDirectoty(path : String) {
        val fdelete = File(path)
        if (fdelete.isDirectory) {
            fdelete.deleteRecursively()
        } else {
            fdelete.delete()
        }
    }

    fun openAddon(uri : String) {
//        val intent = Intent(Intent.ACTION_VIEW)
//        intent.setDataAndType(Uri.parse("file://"+uri),"*/*")
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//        startActivity(Intent.createChooser(intent,"Chose app"))
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse("file://" + uri), "*/*")
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.`package` = "com.mojang.minecraftpe"
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        langState = getLang(this)
    }

    /*fun changeLanguage(language: String) {
        /**
         * Change language
         */
        if (language.equals("", ignoreCase = true)) {
            return
        }

        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = Configuration()
        configuration.setLocale(locale)

        baseContext.resources
                .updateConfiguration(configuration, baseContext
                        .resources.displayMetrics)
    }*/

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //restart main activity after change language in settings
        if (requestCode == 228){
            if (langState != getLang(this)) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)
                finish()
            }
        }
    }

    fun loadJSONFromAsset(filePath: String): String? {
        var json: String? = null
        try {
            val `is` = FileInputStream(File(filePath))
            val size = `is`.available()
            val buffer = ByteArray(size)
            `is`.read(buffer)
            `is`.close()
            json = String(buffer, Charset.forName("UTF-8"))
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json

    }
}
