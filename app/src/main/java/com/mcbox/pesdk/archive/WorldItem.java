package com.mcbox.pesdk.archive;

import com.mcbox.pesdk.archive.io.nbt.NBTConverter;

import org.spout.nbt.CompoundTag;
import org.spout.nbt.stream.NBTInputStream;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.regex.Pattern;

public class WorldItem implements Serializable {
    private static final String EMPTY_NAME = "";
    public static final String WORLD_FOLDER_NAME_SUFFIX = "-";
    public static final String levelNameFileName = "levelname.txt";
    private static final long serialVersionUID = 1999765797018607246L;
    public final File folder;
    public boolean hasLevelFileName = false;
    int id;
    public final File levelDat;
    public String showName = null;
    private String size;

    public WorldItem(File file) {
        this.folder = file;
        this.levelDat = new File(this.folder, "level.dat");
        this.showName = null;
        File file2 = new File(this.folder, levelNameFileName);
        if (file2.isFile() && file2.exists()) {
            this.hasLevelFileName = true;
        }
    }

    public WorldItem(File file, String str) {
        this.folder = file;
        this.levelDat = new File(this.folder, "level.dat");
        this.size = str;
        this.showName = null;
        File file2 = new File(this.folder, levelNameFileName);
        if (file2.isFile() && file2.exists()) {
            this.hasLevelFileName = true;
        }
        this.id = -1;
    }

    public WorldItem(File file, String str, int i) {
        this.folder = file;
        this.levelDat = new File(this.folder, "level.dat");
        this.size = str;
        this.showName = null;
        File file2 = new File(this.folder, levelNameFileName);
        if (file2.isFile() && file2.exists()) {
            this.hasLevelFileName = true;
        }
        this.id = i;
    }

    public static int countMapSuffix(String str) {
        int i = 1;
        while (Pattern.compile("-").matcher(str).find()) {
            i++;
        }
        return i;
    }

    private String getFolderName() {
        if (this.folder == null) {
            return null;
        }
        String name = this.folder.getName();
        return name.indexOf("-") > -1 ? name.replaceAll("-", "") + countMapSuffix(name) : name;
    }

    public boolean equals(Object obj) {
        WorldItem worldItem = (WorldItem) obj;
        return worldItem == null ? false : (getName() == null && worldItem.getName() == null) ? true : (getName() == null || worldItem.getName() == null || !getName().equals(worldItem.getName())) ? false : true;
    }

    public File getFolder() {
        return this.folder;
    }

    public int getId() {
        return this.id;
    }

    public File getLevelDat() {
        return this.levelDat;
    }

    public String getMapKey() {
        String folderName = getFolderName();
        return folderName != null ? folderName + "#" + this.folder.lastModified() : folderName;
    }

    public String getName() {
        return this.folder != null ? getFolderName() : "";
    }

    public String getShowName() throws Throwable {
        BufferedInputStream bufferedInputStream;
        FileNotFoundException e;
        BufferedInputStream bufferedInputStream2 = null;
        Throwable th;
        IOException e2;
        NBTInputStream nBTInputStream = null;
        if (this.showName == null) {
            NBTInputStream nBTInputStream2;
            try {
                bufferedInputStream = new BufferedInputStream(new FileInputStream(this.levelDat));
                try {
                    bufferedInputStream.skip(8);
                    nBTInputStream2 = new NBTInputStream(bufferedInputStream, false, true);
                    try {
                        this.showName = NBTConverter.readLevel((CompoundTag) nBTInputStream2.readTag()).getLevelName();
                        if (nBTInputStream2 != null) {
                            nBTInputStream2.close();
                        }
                        if (bufferedInputStream != null) {
                            try {
                                bufferedInputStream.close();
                            } catch (IOException e4) {
                            }
                        }
                    } catch (Throwable th4) {
                        th = th4;
                        nBTInputStream = nBTInputStream2;
                        if (nBTInputStream != null) {
                            nBTInputStream.close();
                        }
                        if (bufferedInputStream != null) {
                            bufferedInputStream.close();
                        }
                        throw th;
                    }
                } catch (FileNotFoundException e13) {
                    e = e13;
                    nBTInputStream2 = null;
                    bufferedInputStream2 = bufferedInputStream;
                    e.printStackTrace();
                    if (nBTInputStream2 != null) {
                        nBTInputStream2.close();
                    }
                    if (bufferedInputStream2 != null) {
                        bufferedInputStream2.close();
                    }
                    return this.showName;
                } catch (IOException e14) {
                    e2 = e14;
                    e2.printStackTrace();
                    if (nBTInputStream != null) {
                        nBTInputStream.close();
                    }
                    if (bufferedInputStream != null) {
                        bufferedInputStream.close();
                    }
                    return this.showName;
                }
            } catch (FileNotFoundException e15) {
                e = e15;
                nBTInputStream2 = null;
                e.printStackTrace();
                if (nBTInputStream2 != null) {
                    nBTInputStream2.close();
                }
                if (bufferedInputStream2 != null) {
                    bufferedInputStream2.close();
                }
                return this.showName;
            } catch (IOException e16) {
                e2 = e16;
                bufferedInputStream = null;
                e2.printStackTrace();
                if (nBTInputStream != null) {
                    nBTInputStream.close();
                }
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                return this.showName;
            } catch (Throwable th5) {
                th = th5;
                bufferedInputStream = null;
                if (nBTInputStream != null) {
                    nBTInputStream.close();
                }
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                throw th;
            }
        }
        return this.showName;
    }

    public String getSize() {
        return this.size;
    }

    public String getTrueName() {
        return this.folder == null ? null : this.folder.getName();
    }

    public void setId(int i) {
        this.id = i;
    }

    public void setSize(String str) {
        this.size = str;
    }

    public String toString() {
        return getFolderName();
    }
}
