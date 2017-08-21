package com.mcbox.pesdk.archive.material;

import com.yy.hiidostatis.defs.obj.Elem;

import java.io.Serializable;

public class Material implements Serializable {
    private static final long serialVersionUID = 1915871173596188705L;
    private short damage;
    private boolean damageable;
    private boolean hasSubtypes;
    private int id;
    private String name;

    public Material(int i, String str) {
        this(i, str, (short) 0, false);
    }

    public Material(int i, String str, short s) {
        this(i, str, s, true);
    }

    public Material(int i, String str, short s, boolean z) {
        this.damageable = false;
        this.id = i;
        this.name = str;
        this.damage = s;
        this.hasSubtypes = z;
    }

    public short getDamage() {
        return this.damage;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name == null ? "" : this.name;
    }

    public boolean hasSubtypes() {
        return this.hasSubtypes;
    }

    public boolean isDamageable() {
        return this.damageable;
    }

    public void setDamageable(boolean z) {
        this.damageable = z;
    }

    public String toString() {
        return getName() + " : " + getId() + (this.damage != (short) 0 ? Elem.DIVIDER + this.damage : "");
    }
}
