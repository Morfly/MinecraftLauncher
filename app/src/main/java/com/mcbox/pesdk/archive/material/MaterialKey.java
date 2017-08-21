package com.mcbox.pesdk.archive.material;

import com.yy.hiidostatis.defs.obj.Elem;

public final class MaterialKey {
    public short damage;
    public short typeId;

    public MaterialKey(MaterialKey materialKey) {
        this(materialKey.typeId, materialKey.damage);
    }

    public MaterialKey(short s, short s2) {
        this.typeId = s;
        this.damage = s2;
    }

    public static MaterialKey parse(String str, int i) {
        String[] split = str.split(Elem.DIVIDER);
        if (split.length != 0) {
            return split.length == 1 ? new MaterialKey(Short.parseShort(split[0], i), (short) -1) : new MaterialKey(Short.parseShort(split[0], i), Short.parseShort(split[1], i));
        } else {
            throw new IllegalArgumentException("Why is the string blank?!");
        }
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof MaterialKey)) {
            return false;
        }
        MaterialKey materialKey = (MaterialKey) obj;
        return materialKey.typeId == this.typeId && materialKey.damage == this.damage;
    }

    public int hashCode() {
        return ((this.typeId + 31) * 31) + this.damage;
    }

    public String toString() {
        return "MaterialKey[typeId=" + this.typeId + ";damage=" + this.damage + "]";
    }
}
