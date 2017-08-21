package org.tlauncher.tlauncherpe.mc.datalayer

interface PreferencesManager {
    fun getIsLoadMaps(): Boolean
    fun setISLoadMaps(isLoadMaps: Boolean)
    fun getIsLoadAddons(): Boolean
    fun setISLoadAddons(isLoadMaps: Boolean)
    fun getIsLoadTextures(): Boolean
    fun setISLoadTextures(isLoadMaps: Boolean)
    fun getIsLoadSkins(): Boolean
    fun setISLoadSkins(isLoadMaps: Boolean)
    fun getIsLoadSids(): Boolean
    fun setISLoadSids(isLoadMaps: Boolean)
    fun saveLang(lang: Int)
    fun getLang(): Int
    fun saveNotificationState(notSt: Int)
    fun getNotificationState(): Int
    fun saveChackUpdates(chackUpdates: Int)
    fun getChackUpdates(): Int
    fun saveLastLoadedLang(lang: Int)
    fun getLastLoadedLang(): Int
    fun saveLastLoadedLangTex(lang: Int)
    fun getLastLoadedLangTex(): Int
    fun saveLastLoadedLangWor(lang: Int)
    fun getLastLoadedLangWor(): Int
    fun saveLastLoadedLangSkin(lang: Int)
    fun getLastLoadedLangSkin(): Int
    fun saveLastLoadedLangSid(lang: Int)
    fun getLastLoadedLangSid(): Int

    fun saveAddonFAB(fab: Int)
    fun getAddonFAB(): Int
    fun saveTextureFAB(fab: Int)
    fun getTextureFAB(): Int
    fun saveWorldFAB(fab: Int)
    fun getWorldFAB(): Int
    fun saveSkinFAB(fab: Int)
    fun getSkinFAB(): Int
    fun saveSidFAB(fab: Int)
    fun getSidFAB(): Int
}