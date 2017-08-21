package com.mcbox.pesdk.archive;

import java.io.Serializable;

public class InventorySlot implements Serializable {
    private ItemStack contents;
    private byte slot;

    public InventorySlot(byte b, ItemStack itemStack) {
        this.slot = b;
        this.contents = itemStack;
    }

    public ItemStack getContents() {
        return this.contents;
    }

    public byte getSlot() {
        return this.slot;
    }

    public void setContents(ItemStack itemStack) {
        this.contents = itemStack;
    }

    public void setSlot(byte b) {
        this.slot = b;
    }

    public String toString() {
        return "Slot " + getSlot() + ": Type: " + this.contents.getTypeId() + "; Damage: " + this.contents.getDurability() + "; Amount: " + this.contents.getAmount();
    }
}
