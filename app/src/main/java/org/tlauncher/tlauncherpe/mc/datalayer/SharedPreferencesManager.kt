package org.tlauncher.tlauncherpe.mc.datalayer

import android.content.SharedPreferences

class SharedPreferencesManager(private val sharedPreferences: SharedPreferences) : PreferencesManager {

    override fun getIsLoadSids(): Boolean = sharedPreferences.getBoolean("isLoadSids", false)

    override fun setISLoadSids(isLoadMaps: Boolean) {
        sharedPreferences.edit().putBoolean("isLoadSids", isLoadMaps).apply()
    }

    override fun getIsLoadTextures(): Boolean = sharedPreferences.getBoolean("isLoadTextures", false)

    override fun setISLoadTextures(isLoadMaps: Boolean) {
        sharedPreferences.edit().putBoolean("isLoadTextures", isLoadMaps).apply()
    }

    override fun getIsLoadAddons(): Boolean = sharedPreferences.getBoolean("isLoadAddons", false)

    override fun setISLoadAddons(isLoadMaps: Boolean) {
        sharedPreferences.edit().putBoolean("isLoadAddons", isLoadMaps).apply()
    }

    override fun setISLoadMaps(isLoadMaps: Boolean) {
        sharedPreferences.edit().putBoolean("isLoadMaps", isLoadMaps).apply()
    }

    override fun getIsLoadMaps(): Boolean = sharedPreferences.getBoolean("isLoadMaps", false)

    override fun getIsLoadSkins(): Boolean = sharedPreferences.getBoolean("isLoadSkins", false)

    override fun setISLoadSkins(isLoadMaps: Boolean) {
        sharedPreferences.edit().putBoolean("isLoadSkins", isLoadMaps).apply()
    }

    override fun saveLang(lang: Int) {
        sharedPreferences.edit().putInt("lang", lang).apply()
    }

    override fun getLang(): Int = sharedPreferences.getInt("lang", 1)

    fun saveLangitude(value: Int) {

    }

    override fun saveNotificationState(notSt: Int) {
        sharedPreferences.edit().putInt("notSt", notSt).apply()
    }

    override fun getNotificationState(): Int = sharedPreferences.getInt("notSt", 0)

    override fun saveChackUpdates(chackUpdates: Int) {
        sharedPreferences.edit().putInt("chackUpdates", chackUpdates).apply()
    }

    override fun getChackUpdates(): Int = sharedPreferences.getInt("chackUpdates", 1)

    override fun saveLastLoadedLang(lang: Int) {
        sharedPreferences.edit().putInt("lastLoadedLang", lang).apply()
    }

    override fun getLastLoadedLang(): Int = sharedPreferences.getInt("lastLoadedLang", 1)

    override fun saveLastLoadedLangTex(lang: Int) {
        sharedPreferences.edit().putInt("lastLoadedLangTex", lang).apply()
    }

    override fun getLastLoadedLangTex(): Int = sharedPreferences.getInt("lastLoadedLangTex", 1)

    override fun saveLastLoadedLangWor(lang: Int) {
        sharedPreferences.edit().putInt("lastLoadedLangWor", lang).apply()
    }

    override fun getLastLoadedLangWor(): Int = sharedPreferences.getInt("lastLoadedLangWor", 1)

    override fun saveLastLoadedLangSkin(lang: Int) {
        sharedPreferences.edit().putInt("lastLoadedLangSkin", lang).apply()
    }

    override fun getLastLoadedLangSkin(): Int = sharedPreferences.getInt("lastLoadedLangSkin", 1)

    override fun saveLastLoadedLangSid(lang: Int) {
        sharedPreferences.edit().putInt("lastLoadedLangSid", lang).apply()
    }

    override fun getLastLoadedLangSid(): Int = sharedPreferences.getInt("lastLoadedLangSid", 1)

    override fun saveAddonFAB(fab: Int) {
        sharedPreferences.edit().putInt("fabAddon", fab).apply()
    }

    override fun getAddonFAB(): Int = sharedPreferences.getInt("fabAddon", 0)

    override fun saveTextureFAB(fab: Int) {
        sharedPreferences.edit().putInt("fabTexture", fab).apply()
    }

    override fun getTextureFAB(): Int = sharedPreferences.getInt("fabTexture", 0)

    override fun saveWorldFAB(fab: Int) {
        sharedPreferences.edit().putInt("fabWorld", fab).apply()
    }

    override fun getWorldFAB(): Int = sharedPreferences.getInt("fabWorld", 0)

    override fun saveSkinFAB(fab: Int) {
        sharedPreferences.edit().putInt("fabSkin", fab).apply()
    }

    override fun getSkinFAB(): Int = sharedPreferences.getInt("fabSkin", 0)

    override fun saveSidFAB(fab: Int) {
        sharedPreferences.edit().putInt("fabSid", fab).apply()
    }

    override fun getSidFAB(): Int = sharedPreferences.getInt("fabSid", 0)
}