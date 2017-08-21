package com.mcbox.pesdk.archive.geo;

import com.mcbox.pesdk.archive.material.MaterialCount;
import com.mcbox.pesdk.archive.material.MaterialKey;
import com.mcbox.pesdk.archive.util.Vector3f;

import java.util.HashMap;
import java.util.Map;

public final class GeoUtils {
    public static Map<MaterialKey, MaterialCount> countBlocks(AreaBlockAccess areaBlockAccess, CuboidRegion cuboidRegion) {
        Map<MaterialKey, MaterialCount> hashMap = new HashMap();
        MaterialKey materialKey = new MaterialKey((short) 0, (short) 0);
        for (int i = cuboidRegion.f3575x; i < cuboidRegion.f3575x + cuboidRegion.width; i++) {
            for (int i2 = cuboidRegion.f3577z; i2 < cuboidRegion.f3577z + cuboidRegion.length; i2++) {
                for (int i3 = cuboidRegion.f3576y; i3 < cuboidRegion.f3576y + cuboidRegion.height; i3++) {
                    materialKey.typeId = (short) areaBlockAccess.getBlockTypeId(i, i3, i2);
                    materialKey.damage = (short) areaBlockAccess.getBlockData(i, i3, i2);
                    MaterialCount materialCount = (MaterialCount) hashMap.get(materialKey);
                    if (materialCount == null) {
                        MaterialKey materialKey2 = new MaterialKey(materialKey);
                        hashMap.put(materialKey2, new MaterialCount(materialKey2, 1));
                    } else {
                        materialCount.count++;
                    }
                }
            }
        }
        return hashMap;
    }

    public static int makeCylinder(AreaBlockAccess areaBlockAccess, Vector3f vector3f, int i, int i2, MaterialKey materialKey, boolean z) {
        int blockX = vector3f.getBlockX();
        int blockY = vector3f.getBlockY();
        int blockZ = vector3f.getBlockZ();
        int i3 = 0;
        int i4 = i * i;
        int i5 = (i - 1) * (i - 1);
        int i6 = -i;
        while (i6 < i) {
            int i7 = i3;
            for (int i8 = -i; i8 < i; i8++) {
                double pow = Math.pow((double) i6, 2.0d) + Math.pow((double) i8, 2.0d);
                if (pow <= ((double) i4) && (!z || pow >= ((double) i5))) {
                    i3 = blockY;
                    while (i3 < blockY + i2) {
                        areaBlockAccess.setBlockTypeId(blockX + i6, i3, blockZ + i8, materialKey.typeId);
                        areaBlockAccess.setBlockData(blockX + i6, i3, blockZ + i8, materialKey.damage);
                        i3++;
                        i7++;
                    }
                }
            }
            i6++;
            i3 = i7;
        }
        return i3;
    }

    public static int makeDome(AreaBlockAccess areaBlockAccess, int i, int i2, int i3, int i4, MaterialKey materialKey, boolean z) {
        int i5 = 0;
        int i6 = i4 * i4;
        int i7 = (i4 - 1) * (i4 - 1);
        for (int i8 = -i4; i8 < i4; i8++) {
            int i9 = -i4;
            while (i9 < i4) {
                int i10 = i5;
                for (i5 = 0; i5 < i4; i5++) {
                    double pow = (Math.pow((double) i8, 2.0d) + Math.pow((double) i5, 2.0d)) + Math.pow((double) i9, 2.0d);
                    if (pow <= ((double) i6) && (!z || pow >= ((double) i7))) {
                        areaBlockAccess.setBlockTypeId(i + i8, i2 + i5, i3 + i9, materialKey.typeId);
                        areaBlockAccess.setBlockData(i + i8, i2 + i5, i3 + i9, materialKey.damage);
                        i10++;
                    }
                }
                i9++;
                i5 = i10;
            }
        }
        return i5;
    }

    public static int makeDome(AreaBlockAccess areaBlockAccess, Vector3f vector3f, int i, MaterialKey materialKey, boolean z) {
        return makeDome(areaBlockAccess, vector3f.getBlockX(), vector3f.getBlockY(), vector3f.getBlockZ(), i, materialKey, z);
    }

    public static int makeHollowCuboid(AreaBlockAccess areaBlockAccess, CuboidRegion cuboidRegion, MaterialKey materialKey) {
        CuboidRegion cuboidRegion2 = new CuboidRegion(cuboidRegion);
        cuboidRegion2.height = 1;
        setBlocks(areaBlockAccess, cuboidRegion2, materialKey);
        cuboidRegion2.f3576y = (cuboidRegion.f3576y + cuboidRegion.height) - 1;
        setBlocks(areaBlockAccess, cuboidRegion2, materialKey);
        int blockCount = 0 + (cuboidRegion2.getBlockCount() * 2);
        cuboidRegion2 = new CuboidRegion(cuboidRegion);
        cuboidRegion2.width = 1;
        setBlocks(areaBlockAccess, cuboidRegion2, materialKey);
        cuboidRegion2.f3575x = (cuboidRegion.f3575x + cuboidRegion.width) - 1;
        setBlocks(areaBlockAccess, cuboidRegion2, materialKey);
        blockCount += cuboidRegion2.getBlockCount() * 2;
        cuboidRegion2 = new CuboidRegion(cuboidRegion);
        cuboidRegion2.length = 1;
        setBlocks(areaBlockAccess, cuboidRegion2, materialKey);
        cuboidRegion2.f3577z = (cuboidRegion.f3577z + cuboidRegion.length) - 1;
        setBlocks(areaBlockAccess, cuboidRegion2, materialKey);
        return blockCount + (cuboidRegion2.getBlockCount() * 2);
    }

    public static int makePyramid(AreaBlockAccess areaBlockAccess, int i, int i2, int i3, int i4, MaterialKey materialKey, boolean z) {
        int i5 = 0;
        for (int i6 = 0; i6 <= i4; i6++) {
            int i7 = i4 - i6;
            int i8 = i7 - 1;
            int i9 = (-i7) + 1;
            while (i9 < i7) {
                int i10 = i5;
                i5 = (-i7) + 1;
                while (i5 < i7) {
                    if (!z || i9 <= (-i8) + 1 || i9 >= i8 || i5 <= (-i8) + 1 || i5 >= i8) {
                        areaBlockAccess.setBlockTypeId(i + i9, i2 + i6, i3 + i5, materialKey.typeId);
                        areaBlockAccess.setBlockData(i + i9, i2 + i6, i3 + i5, materialKey.damage);
                        i10++;
                    }
                    i5++;
                }
                i9++;
                i5 = i10;
            }
        }
        return i5;
    }

    public static int makePyramid(AreaBlockAccess areaBlockAccess, Vector3f vector3f, int i, MaterialKey materialKey, boolean z) {
        return makePyramid(areaBlockAccess, vector3f.getBlockX(), vector3f.getBlockY(), vector3f.getBlockZ(), i, materialKey, z);
    }

    public static int makeSphere(AreaBlockAccess areaBlockAccess, int i, int i2, int i3, int i4, MaterialKey materialKey, boolean z) {
        int i5 = 0;
        int i6 = i4 * i4;
        int i7 = (i4 - 1) * (i4 - 1);
        for (int i8 = -i4; i8 < i4; i8++) {
            int i9 = -i4;
            while (i9 < i4) {
                int i10 = i5;
                for (i5 = -i4; i5 < i4; i5++) {
                    double pow = (Math.pow((double) i8, 2.0d) + Math.pow((double) i5, 2.0d)) + Math.pow((double) i9, 2.0d);
                    if (pow <= ((double) i6) && (!z || pow >= ((double) i7))) {
                        areaBlockAccess.setBlockTypeId(i + i8, i2 + i5, i3 + i9, materialKey.typeId);
                        areaBlockAccess.setBlockData(i + i8, i2 + i5, i3 + i9, materialKey.damage);
                        i10++;
                    }
                }
                i9++;
                i5 = i10;
            }
        }
        return i5;
    }

    public static int makeSphere(AreaBlockAccess areaBlockAccess, Vector3f vector3f, int i, MaterialKey materialKey, boolean z) {
        return makeSphere(areaBlockAccess, vector3f.getBlockX(), vector3f.getBlockY(), vector3f.getBlockZ(), i, materialKey, z);
    }

    public static int replaceBlocks(AreaBlockAccess areaBlockAccess, CuboidRegion cuboidRegion, MaterialKey materialKey, MaterialKey materialKey2) {
        int i = 0;
        for (int i2 = cuboidRegion.f3575x; i2 < cuboidRegion.f3575x + cuboidRegion.width; i2++) {
            int i3 = cuboidRegion.f3577z;
            while (i3 < cuboidRegion.f3577z + cuboidRegion.length) {
                int i4 = i;
                for (i = cuboidRegion.f3576y; i < cuboidRegion.f3576y + cuboidRegion.height; i++) {
                    short blockTypeId = (short) areaBlockAccess.getBlockTypeId(i2, i, i3);
                    short blockData = (short) areaBlockAccess.getBlockData(i2, i, i3);
                    if (blockTypeId == materialKey.typeId && (blockData == materialKey.damage || materialKey.damage == (short) -1)) {
                        areaBlockAccess.setBlockTypeId(i2, i, i3, materialKey2.typeId);
                        areaBlockAccess.setBlockData(i2, i, i3, materialKey2.damage);
                        i4++;
                    }
                }
                i3++;
                i = i4;
            }
        }
        return i;
    }

    public static void setBlocks(AreaBlockAccess areaBlockAccess, CuboidRegion cuboidRegion, MaterialKey materialKey) {
        for (int i = cuboidRegion.f3575x; i < cuboidRegion.f3575x + cuboidRegion.width; i++) {
            for (int i2 = cuboidRegion.f3577z; i2 < cuboidRegion.f3577z + cuboidRegion.length; i2++) {
                for (int i3 = cuboidRegion.f3576y; i3 < cuboidRegion.f3576y + cuboidRegion.height; i3++) {
                    areaBlockAccess.setBlockTypeId(i, i3, i2, materialKey.typeId);
                    areaBlockAccess.setBlockData(i, i3, i2, materialKey.damage);
                }
            }
        }
    }
}
