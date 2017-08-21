package com.mcbox.pesdk.archive.material;

public final class MaterialType {
    private Integer index;
    private String name;

    public MaterialType(Integer num, String str) {
        this.index = num;
        this.name = str;
    }

    public Integer getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

    public void setIndex(Integer num) {
        this.index = num;
    }

    public void setName(String str) {
        this.name = str;
    }
}
