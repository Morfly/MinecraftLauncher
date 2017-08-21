package com.mcbox.pesdk.archive;

import com.mcbox.pesdk.archive.io.EntityDataConverter.EntityData;

public interface EntityDataLoadListener {
    void onEntitiesLoaded(EntityData entityData);
}
