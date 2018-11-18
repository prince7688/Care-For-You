package com.example.hp.careforyou;

public class NutritionItem {

    private final String mBrandName;

    private final String mItemName;

    private final String mWater;

    private final String mFat;

    private final String mEnergy;

    private final String mSalt;

    private final String mSugar;

    private final String mCholes;

    private final String mCharbo;

    private final String mFiber;

    private final String mProtien;

    private final String mVitaminA;

    public NutritionItem(String mBrandName, String mItemName, String mWater, String mFat, String mEnergy,
                         String mSalt, String mSugar, String mCholes, String mCharbo, String mFiber,
                         String mProtien, String mVitaminA) {
        this.mBrandName = mBrandName;
        this.mItemName = mItemName;
        this.mWater = mWater;
        this.mFat = mFat;
        this.mEnergy = mEnergy;
        this.mSalt = mSalt;
        this.mSugar = mSugar;
        this.mCholes = mCholes;
        this.mCharbo = mCharbo;
        this.mFiber = mFiber;
        this.mProtien = mProtien;
        this.mVitaminA = mVitaminA;
    }

    public String getmCholes() {
        return mCholes;
    }

    public String getmCharbo() {
        return mCharbo;
    }

    public String getmFiber() {
        return mFiber;
    }

    public String getmProtien() {
        return mProtien;
    }

    public String getmVitaminA() {
        return mVitaminA;
    }

    public String getmBrandName() {
        return mBrandName;
    }

    public String getmItemName() {
        return mItemName;
    }

    public String getmWater() {
        return mWater;
    }

    public String getmFat() {
        return mFat;
    }

    public String getmEnergy() {
        return mEnergy;
    }

    public String getmSalt() {
        Double salt = Double.valueOf(mSalt)*2.5;
        salt =salt/1000;
        return String.valueOf(salt);
    }

    public String getmSugar() {
        return mSugar;
    }
}
