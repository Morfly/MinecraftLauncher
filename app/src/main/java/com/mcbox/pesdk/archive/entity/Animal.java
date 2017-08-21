package com.mcbox.pesdk.archive.entity;

public class Animal extends LivingEntity {
    private int age = 0;
    private int inLove = 0;

    public int getAge() {
        return this.age;
    }

    public int getInLove() {
        return this.inLove;
    }

    public void setAge(int i) {
        this.age = i;
    }

    public void setInLove(int i) {
        this.inLove = i;
    }
}
