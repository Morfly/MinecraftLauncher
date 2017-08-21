package com.mcbox.pesdk.archive.entity;

import java.io.Serializable;

public class PlayerAbilities implements Serializable {
    public boolean flying = false;
    public boolean instabuild = false;
    public boolean invulnerable = false;
    public boolean mayFly = false;
    public float walkSpeed = 0.1f;

    public float getWalkSpeed() {
        return this.walkSpeed;
    }

    public void initForGameType(int i) {
        boolean z = true;
        boolean z2 = i == 1;
        this.invulnerable = z2;
        this.instabuild = z2;
        this.mayFly = z2;
        if (!(this.flying && z2)) {
            z = false;
        }
        this.flying = z;
    }

    public boolean isFlying() {
        return this.flying;
    }

    public boolean isInstabuild() {
        return this.instabuild;
    }

    public boolean isInvulnerable() {
        return this.invulnerable;
    }

    public boolean isMayFly() {
        return this.mayFly;
    }

    public void setFlying(boolean z) {
        this.flying = z;
    }

    public void setInstabuild(boolean z) {
        this.instabuild = z;
    }

    public void setInvulnerable(boolean z) {
        this.invulnerable = z;
    }

    public void setMayFly(boolean z) {
        this.mayFly = z;
    }

    public void setWalkSpeed(float f) {
        this.walkSpeed = f;
    }
}
