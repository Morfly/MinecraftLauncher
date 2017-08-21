package com.mcbox.pesdk.launcher;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;

import com.mcbox.pesdk.mcfloat.func.DtPlugin;
import com.mcbox.pesdk.mcfloat.model.BagItem;

import java.io.File;
import java.util.List;

public interface LauncherRuntime {
    void addAttackPotion(int i, int i2, int i3);

    void addEnchantItemInventory(int i, int i2, int[] iArr);

    void addEnchantItemInventory(int i, int i2, int[] iArr, int i3);

    boolean addItemInventory(int i, int i2, int i3, int i4);

    void addPlayerEffect(int i, int i2, int i3, boolean z);

    void changeAttackPotionDuration(int i);

    void changeGameGuiStatus();

    void clearAllAttackNegativePotions();

    void clearAllAttackPotions();

    void enableCanFly(boolean z);

    void enableDeathNoDrop(boolean z);

    void enableFlyButton(boolean z);

    void enableMiniMap(boolean z);

    void enablePlayerInvincible(boolean z);

    void enablePotionAttack(boolean z);

    void enableScript(String str, boolean z);

    void enableShowBlood(boolean z);

    void enableSprintRunDoubleClick(boolean z);

    void enableSprintRunInternal(boolean z);

    DtPlugin getDtPlugin();

    Integer getEffectIdByName(String str);

    int getGameMode();

    int getGameTime();

    int getGuiScale();

    String getInventoryCustomName(int i);

    Activity getMcActivity();

    List<Long> getPlayerIds();

    int getPlayerLevel();

    float getPlayerLoc(int i);

    String getPlayerName(Long l);

    float getRemotePlayerLoc(Long l, int i);

    int getRespawnLoc(int i);

    int getServerMaxConnection();

    String getTransmute(String str, int i, int i2);

    List<BagItem> getUserBag();

    void init(Context context);

    boolean isDeathNoDropEnabled();

    boolean isHideGameGui();

    boolean isLanWorld();

    boolean isPlayerCanFly();

    boolean isPlayerInvincible();

    boolean isPotionAttackEnabled();

    boolean isRemoteWorld();

    boolean isSpringRunInternalEnabled();

    void loadModPkg(File file);

    void loadScript(File file);

    void playerJump();

    void refreshUserBag(List<BagItem> list);

    void removeEntityTypeItems(int i);

    void removePlayerAllEffect();

    void removePlayerAllNegativeEffect();

    void removePlayerEffect(int i);

    void resetSprintRunDoubleClick();

    void resetSprintRunInternal();

    void revokeBuilding();

    void saveDeathPosition();

    void saveGameData();

    void saveScreenShotBitmap(Bitmap bitmap, String str);

    void setBlockUpdateForbidden(boolean z);

    void setEventListener(LauncherEventListener launcherEventListener);

    void setExplodeForbidden(boolean z);

    void setFallWithNoDamage(boolean z);

    void setFastAddItem(boolean z);

    void setFov(float f, boolean z);

    void setGameFontColor(String str);

    void setGameMode(int i);

    void setGameTime(int i);

    void setGuiScale(int i);

    void setInventoryCustomName(int i, String str);

    void setMaxConnection(int i);

    void setNoclip(boolean z);

    void setParsedScriptState(String str, boolean z);

    void setPlayerLevel(int i);

    boolean setPlayerPos(float f, float f2, float f3);

    boolean setRemotePlayerPos(Long l, float f, float f2, float f3);

    boolean setRespawnPos(float f, float f2, float f3);

    void setTimeStop(int i);

    void setWeather(int i, float f);

    long setspawnEntityWithProperties(int i, String str, String str2, int i2, int[] iArr, int[] iArr2, int i3, int i4, int i5);

    long setspawnEntityWithProperties(String str, String str2, int[] iArr, int[] iArr2, Object[] objArr, int[] iArr3, int[] iArr4);

    void showMessage(String str, String str2);

    void takeScreenShot(String str);

    void takeScreenShot(String str, boolean z);

    void takeScreenShotWithWM(String str, boolean z, boolean z2);
}
