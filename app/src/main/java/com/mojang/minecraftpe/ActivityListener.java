package com.mojang.minecraftpe;

import android.content.Intent;

public abstract interface ActivityListener
{
    public abstract void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent);

    public abstract void onDestroy();
}
