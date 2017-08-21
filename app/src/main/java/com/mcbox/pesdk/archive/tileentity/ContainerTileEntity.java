package com.mcbox.pesdk.archive.tileentity;

import com.mcbox.pesdk.archive.InventorySlot;

import java.util.List;

public class ContainerTileEntity extends TileEntity {
    private List<InventorySlot> items;

    public int getContainerSize() {
        return 27;
    }

    public List<InventorySlot> getItems() {
        return this.items;
    }

    public void setItems(List<InventorySlot> list) {
        this.items = list;
    }
}
