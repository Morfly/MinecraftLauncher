package com.mcbox.pesdk.archive.material;

import com.mcbox.pesdk.archive.ItemStack;

import java.util.HashSet;
import java.util.Set;

public final class RepairableMaterials {
    public static final Set<Integer> ids = new HashSet();

    static {
        add(256, 259);
        add(261);
        add(267, 279);
        add(283, 286);
        add(298, 317);
        add(359);
    }

    private static void add(int i) {
        ids.add(Integer.valueOf(i));
    }

    private static void add(int i, int i2) {
        while (i <= i2) {
            ids.add(Integer.valueOf(i));
            i++;
        }
    }

    public static boolean isRepairable(ItemStack itemStack) {
        return ids.contains(new Integer(itemStack.getTypeId()));
    }
}
