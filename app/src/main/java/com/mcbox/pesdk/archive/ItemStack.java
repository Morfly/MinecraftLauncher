package com.mcbox.pesdk.archive;

import org.spout.nbt.Tag;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ItemStack implements Serializable {
    private static final long serialVersionUID = 6950987436416689758L;
    private int amount;
    private short durability;
    private List<Tag> extras;
    private short id;

    public ItemStack() {
        this.extras = new ArrayList();
    }

    public ItemStack(ItemStack itemStack) {
        this(itemStack.getTypeId(), itemStack.getDurability(), itemStack.getAmount());
    }

    public ItemStack(short s, short s2, int i) {
        this.extras = new ArrayList();
        this.id = s;
        this.durability = s2;
        this.amount = i;
        this.extras = new ArrayList();
    }

    public int getAmount() {
        return this.amount;
    }

    public short getDurability() {
        return this.durability;
    }

    public List<Tag> getExtras() {
        return this.extras;
    }

    public short getTypeId() {
        return this.id;
    }

    public void setAmount(int i) {
        this.amount = i;
    }

    public void setDurability(short s) {
        this.durability = s;
    }

    public void setTypeId(short s) {
        this.id = s;
    }

    public String toString() {
        return "ItemStack: type=" + getTypeId() + ", durability=" + getDurability() + ", amount=" + getAmount() + ", extras=" + this.extras;
    }
}
