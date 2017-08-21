package com.mojang.minecraftpe.store;

public class Purchase {
  public String mProductId;
  public boolean mPurchaseActive;
  public String mReceipt;

  public Purchase(String paramString1, String paramString2, boolean paramBoolean) {
    this.mProductId = paramString1;
    this.mReceipt = paramString2;
    this.mPurchaseActive = paramBoolean;
  }
}

