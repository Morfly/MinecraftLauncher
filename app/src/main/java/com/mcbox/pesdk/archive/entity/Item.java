package com.mcbox.pesdk.archive.entity;

import com.mcbox.pesdk.archive.ItemStack;

public class Item extends Entity {
    private short age = (short) 0;
    private short health = (short) 5;
    private ItemStack stack;

    public short getAge() {
        return this.age;
    }

    public short getHealth() {
        return this.health;
    }

    public ItemStack getItemStack() {
        if (this.stack == null) {
            this.stack = new ItemStack((short) 0, (short) 0, 0);
        }
        return this.stack;
    }

    public void setAge(short s) {
        this.age = s;
    }

    public void setHealth(short s) {
        this.health = s;
    }

    public void setItemStack(ItemStack itemStack) {
        this.stack = itemStack;
    }
}
