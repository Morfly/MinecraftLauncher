package org.tlauncher.tlauncherpe.mc;

public class McVersion {
    private static final String BETA_PREFIX = "b";
    private Integer beta = Integer.valueOf(0);
    private int len;
    private Integer major = Integer.valueOf(0);
    private Integer minor = Integer.valueOf(0);
    private Integer patch = Integer.valueOf(0);

    public McVersion(Integer num, Integer num2, Integer num3, Integer num4) {
        this.major = num;
        this.minor = num2;
        this.patch = num3;
        this.beta = num4;
    }

    public static McVersion fromVersionString(String str) {
        if (str == null || str.length() == 0) {
            return new McVersion(0,0,0,0);
        }
        McVersion mcVersion = new McVersion(0,0,0,0);
        String[] split = str.split("[.]");
        if (split.length > 0) {
            mcVersion.setMajor(parse(split[0]));
        }
        if (split.length > 1) {
            mcVersion.setMinor(parse(split[1]));
        }
        if (split.length > 2) {
            mcVersion.setPatch(parse(split[2]));
        }
        if (split.length > 3) {
            mcVersion.setBeta(parse(split[3]));
        }
        mcVersion.setLen(split.length);
        return mcVersion;
    }

    private static Integer parse(String str) {
        Integer valueOf = Integer.valueOf(0);
        try {
            if (str.startsWith(BETA_PREFIX)) {
                str = str.substring(1);
            }
            valueOf = Integer.valueOf(Integer.parseInt(str));
        } catch (Exception e) {
        }
        return valueOf;
    }

    public Integer getBeta() {
        return this.beta;
    }

    public int getLen() {
        return this.len;
    }

    public Integer getMajor() {
        return this.major;
    }

    public Integer getMinor() {
        return this.minor;
    }

    public Integer getPatch() {
        return this.patch;
    }

    public void setBeta(Integer num) {
        this.beta = num;
    }

    public void setLen(int i) {
        this.len = i;
    }

    public void setMajor(Integer num) {
        this.major = num;
    }

    public void setMinor(Integer num) {
        this.minor = num;
    }

    public void setPatch(Integer num) {
        this.patch = num;
    }
}
