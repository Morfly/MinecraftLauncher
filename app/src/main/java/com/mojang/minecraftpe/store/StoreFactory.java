package com.mojang.minecraftpe.store;

import com.mojang.minecraftpe.MainActivity;


import com.mojang.minecraftpe.MainActivity;

import android.util.Log;

/*public class StoreFactory {
  public static Store createAmazonAppStore(StoreListener paramStoreListener) {
    return new Store(paramStoreListener);
  }
  
  public static Store createGooglePlayStore(String s, StoreListener listener) {
    return new Store(listener);
  }
  
  public static Store createSamsungAppStore(StoreListener storeListener) {
    return new Store(storeListener);
  }
}*/


public class StoreFactory {
  static Store createAmazonAppStore(StoreListener paramStoreListener, boolean paramBoolean) {
    return /*new AmazonAppStore(MainActivity.mInstance, paramStoreListener, paramBoolean)*/null;
  }

  static Store createGooglePlayStore(String paramString, StoreListener paramStoreListener) {
    return /*new GooglePlayStore(MainActivity.mInstance, paramString, paramStoreListener)*/null;
  }
}