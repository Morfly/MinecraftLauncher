package com.mcbox.pesdk.archive.entity;

import com.mcbox.pesdk.archive.util.Vector3f;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Entity implements Serializable {
    private short air = (short) 300;
    private List<Object> extras = new ArrayList();
    private float fallDistance;
    private short fire;
    private Vector3f location = new Vector3f(0.0f, 0.0f, 0.0f);
    private Vector3f motion = new Vector3f(0.0f, 0.0f, 0.0f);
    private boolean onGround = false;
    private float pitch;
    private Entity riding = null;
    private int typeId = 0;
    private float yaw;

    public short getAirTicks() {
        return this.air;
    }

    public EntityType getEntityType() {
        EntityType byId = EntityType.getById(this.typeId);
        return byId == null ? EntityType.UNKNOWN : byId;
    }

    public int getEntityTypeId() {
        return this.typeId;
    }

    public List<Object> getExtraTags() {
        return this.extras;
    }

    public float getFallDistance() {
        return this.fallDistance;
    }

    public short getFireTicks() {
        return this.fire;
    }

    public Vector3f getLocation() {
        return this.location;
    }

    public float getPitch() {
        return this.pitch;
    }

    public Entity getRiding() {
        return this.riding;
    }

    public Vector3f getVelocity() {
        return this.motion;
    }

    public float getYaw() {
        return this.yaw;
    }

    public boolean isOnGround() {
        return this.onGround;
    }

    public void setAirTicks(short s) {
        this.air = s;
    }

    public void setEntityTypeId(int i) {
        this.typeId = i;
    }

    public void setFallDistance(float f) {
        this.fallDistance = f;
    }

    public void setFireTicks(short s) {
        this.fire = s;
    }

    public void setLocation(Vector3f vector3f) {
        this.location = vector3f;
    }

    public void setOnGround(boolean z) {
        this.onGround = z;
    }

    public void setPitch(float f) {
        this.pitch = f;
    }

    public void setRiding(Entity entity) {
        this.riding = entity;
    }

    public void setVelocity(Vector3f vector3f) {
        this.motion = vector3f;
    }

    public void setYaw(float f) {
        this.yaw = f;
    }
}
