package com.mcbox.pesdk.archive.material;

import com.yy.hiidostatis.defs.obj.Elem;

public class MaterialCount {
    public int count;
    public MaterialKey key;

    public MaterialCount(MaterialKey materialKey, int i) {
        this.key = materialKey;
        this.count = i;
    }

    public String toString() {
        return "[" + this.key.typeId + Elem.DIVIDER + this.key.damage + "]: " + this.count;
    }
}
