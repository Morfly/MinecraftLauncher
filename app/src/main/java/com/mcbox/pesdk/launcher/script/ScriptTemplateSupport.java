package com.mcbox.pesdk.launcher.script;

public class ScriptTemplateSupport implements ScriptTemplate {
    private static final String EMPTY_NAME = "";
    protected boolean enabled = true;

    public void attackHook(long j, long j2) {
    }

    public void deathHook(int i, int i2) {
    }

    public void deathHook(long j, long j2) {
    }

    public void destroyBlock(int i, int i2, int i3, int i4) {
    }

    public void entityAddedHook(long j) {
    }

    public void explodeHook(long j, float f, float f2, float f3, float f4, boolean z) {
    }

    public String getName() {
        return "";
    }

    public void init() {
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public void leaveGame() {
    }

    public void mobTick() {
    }

    public void newLevel(boolean z) {
    }

    public void reset() {
        this.enabled = false;
    }

    public void setEnabled(boolean z) {
        this.enabled = z;
    }

    public void useItem(int i, int i2, int i3, int i4, int i5, int i6, int i7, int i8) {
    }
}
