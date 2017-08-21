package com.mcbox.pesdk.launcher.script;

public interface ScriptTemplate {
    public static final int FUNC_INDEX_deathHook = 2;
    public static final int FUNC_INDEX_mobTick = 1;

    void attackHook(long j, long j2);

    void deathHook(int i, int i2);

    void deathHook(long j, long j2);

    void destroyBlock(int i, int i2, int i3, int i4);

    void entityAddedHook(long j);

    void explodeHook(long j, float f, float f2, float f3, float f4, boolean z);

    String getName();

    void init();

    boolean isEnabled();

    void leaveGame();

    void mobTick();

    void newLevel(boolean z);

    void reset();

    void setEnabled(boolean z);

    void useItem(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8);
}
