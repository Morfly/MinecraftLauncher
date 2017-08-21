package org.tlauncher.tlauncherpe.mc

import android.content.Context
import org.tlauncher.tlauncherpe.mc.datalayer.PreferencesManager
import org.tlauncher.tlauncherpe.mc.datalayer.SharedPreferencesManager

fun saveLang(value: Int, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.saveLang(value)
}

fun getLang(context: Context): Int {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getLang()
}

fun saveNotification(value: Int, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.saveNotificationState(value)
}

fun getNotification(context: Context): Int {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getNotificationState()
}

fun saveUpdates(value: Int, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.saveChackUpdates(value)
}

fun getUpdates(context: Context): Int {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getChackUpdates()
}

fun saveLastLoadedLang(value: Int, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.saveLastLoadedLang(value)
}

fun getLastLoadedLang(context: Context): Int {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getLastLoadedLang()
}

fun saveLastLoadedLangTex(value: Int, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.saveLastLoadedLangTex(value)
}

fun getLastLoadedLangTex(context: Context): Int {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getLastLoadedLangTex()
}

fun saveLastLoadedLangWor(value: Int, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.saveLastLoadedLangWor(value)
}

fun getLastLoadedLangWor(context: Context): Int {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getLastLoadedLangWor()
}

fun saveLastLoadedLangSkin(value: Int, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.saveLastLoadedLangSkin(value)
}

fun getLastLoadedLangSkin(context: Context): Int {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getLastLoadedLangSkin()
}

fun saveLastLoadedLangSid(value: Int, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.saveLastLoadedLangSid(value)
}

fun getLastLoadedLangSid(context: Context): Int {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getLastLoadedLangSid()
}

fun saveAddonFAB(value: Int, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.saveAddonFAB(value)
}

fun getAddonFAB(context: Context): Int {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getAddonFAB()
}

fun saveTextureFAB(value: Int, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.saveTextureFAB(value)
}

fun getTextureFAB(context: Context): Int {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getTextureFAB()
}

fun saveWorldFAB(value: Int, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.saveWorldFAB(value)
}

fun getWorldFAB(context: Context): Int {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getWorldFAB()
}

fun saveSkinFAB(value: Int, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.saveSkinFAB(value)
}

fun getSkinFAB(context: Context): Int {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getSkinFAB()
}

fun saveSidFAB(value: Int, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.saveSidFAB(value)
}

fun getSidFAB(context: Context): Int {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getSidFAB()
}

fun setISLoadMaps(value: Boolean, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.setISLoadMaps(value)
}

fun getIsLoadMaps(context: Context): Boolean {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getIsLoadMaps()
}

fun setISLoadAddons(value: Boolean, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.setISLoadAddons(value)
}

fun getIsLoadAddons(context: Context): Boolean {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getIsLoadAddons()
}

fun setISLoadTextures(value: Boolean, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.setISLoadTextures(value)
}

fun getIsLoadTextures(context: Context): Boolean {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getIsLoadTextures()
}

fun setISLoadSkins(value: Boolean, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.setISLoadSkins(value)
}

fun getIsLoadSkins(context: Context): Boolean {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getIsLoadSkins()
}

fun setISLoadSids(value: Boolean, context: Context) {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    sharedPreferences.setISLoadSids(value)
}

fun getIsLoadSids(context: Context): Boolean {
    val preferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)
    val sharedPreferences: PreferencesManager = SharedPreferencesManager(preferences)
    return sharedPreferences.getIsLoadSids()
}