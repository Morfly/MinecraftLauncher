package com.mcbox.pesdk.archive.entity;

import org.spout.nbt.CompoundTag;
import org.spout.nbt.ListTag;

import java.util.ArrayList;
import java.util.List;

public class PlayerAttributes {
    public static final String ATTR_NAME_EMPTY = "";
    public static final String ATTR_NAME_PLAYER_EXHASTION = "player.exhastion";
    public static final String ATTR_NAME_PLAYER_EXPERIENCE = "player.experience";
    public static final String ATTR_NAME_PLAYER_HUNGER = "player.hunger";
    public static final String ATTR_NAME_PLAYER_LEVEL = "player.level";
    public static final String ATTR_NAME_PLAYER_SATURATION = "player.saturation";
    public static final String ATTR_NAME_PREFIX_GENERIC = "generic";
    public static final String TAG_NAME = "Attributes";
    private PlayerAttrItem exhastion;
    private PlayerAttrItem experience;
    private List<CompoundTag> genericAttrs = new ArrayList();
    private PlayerAttrItem hunger;
    private PlayerAttrItem level;
    private PlayerAttrItem saturation;

    public static PlayerAttributes fromTag(ListTag<CompoundTag> listTag) {
        PlayerAttributes playerAttributes = new PlayerAttributes();
        if (listTag == null) {
            return playerAttributes;
        }
        for (CompoundTag compoundTag : listTag.getValue()) {
            PlayerAttrItem formTag = PlayerAttrItem.formTag(compoundTag);
            if (formTag.getName() != null && formTag.getName().startsWith(ATTR_NAME_PREFIX_GENERIC)) {
                playerAttributes.getGenericAttrs().add(compoundTag);
            } else if (ATTR_NAME_PLAYER_HUNGER.equals(formTag.getName())) {
                playerAttributes.setHunger(formTag);
            } else if (ATTR_NAME_PLAYER_EXHASTION.equals(formTag.getName())) {
                playerAttributes.setExhastion(formTag);
            } else if (ATTR_NAME_PLAYER_LEVEL.equals(formTag.getName())) {
                playerAttributes.setLevel(formTag);
            } else if (ATTR_NAME_PLAYER_EXPERIENCE.equals(formTag.getName())) {
                playerAttributes.setExperience(formTag);
            } else if (ATTR_NAME_PLAYER_SATURATION.equals(formTag.getName())) {
                playerAttributes.setSaturation(formTag);
            }
        }
        return playerAttributes;
    }

    public PlayerAttrItem getExhastion() {
        return this.exhastion;
    }

    public PlayerAttrItem getExperience() {
        return this.experience;
    }

    public List<CompoundTag> getGenericAttrs() {
        return this.genericAttrs;
    }

    public PlayerAttrItem getHunger() {
        return this.hunger;
    }

    public PlayerAttrItem getLevel() {
        return this.level;
    }

    public PlayerAttrItem getSaturation() {
        return this.saturation;
    }

    public void setExhastion(PlayerAttrItem playerAttrItem) {
        this.exhastion = playerAttrItem;
    }

    public void setExperience(PlayerAttrItem playerAttrItem) {
        this.experience = playerAttrItem;
    }

    public void setHunger(PlayerAttrItem playerAttrItem) {
        this.hunger = playerAttrItem;
    }

    public void setLevel(PlayerAttrItem playerAttrItem) {
        this.level = playerAttrItem;
    }

    public void setSaturation(PlayerAttrItem playerAttrItem) {
        this.saturation = playerAttrItem;
    }

    public int size() {
        return this.genericAttrs.size() + 5;
    }

    public ListTag<CompoundTag> toTag() {
        List arrayList = new ArrayList();
        arrayList.addAll(this.genericAttrs);
        if (this.hunger != null) {
            arrayList.add(this.hunger.toTag(""));
        }
        if (this.exhastion != null) {
            arrayList.add(this.exhastion.toTag(""));
        }
        if (this.level != null) {
            arrayList.add(this.level.toTag(""));
        }
        if (this.experience != null) {
            arrayList.add(this.experience.toTag(""));
        }
        if (this.saturation != null) {
            arrayList.add(this.saturation.toTag(""));
        }
        return new ListTag(TAG_NAME, CompoundTag.class, arrayList);
    }
}
