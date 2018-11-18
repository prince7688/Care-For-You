package com.example.hp.careforyou.Database;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "Nutrition")
public class NutritionEntity  {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private  String BrandName;

    private  String ItemName;

    private  String Water;

    private  String Fat;

    private  String Energy;

    private  String Salt;

    private  String Sugar;

    private Date  date;

    public NutritionEntity(@NonNull int id, String BrandName,
                           String ItemName, String Water, String Fat, String Energy, String Salt, String Sugar,Date date) {
        this.id = id;
        this.BrandName = BrandName;
        this.ItemName = ItemName;
        this.Water = Water;
        this.Fat = Fat;
        this.Energy = Energy;
        this.Salt = Salt;
        this.Sugar = Sugar;
        this.date = date;
    }

    @Ignore
    public NutritionEntity(String BrandName, String ItemName, String Water, String Fat, String Energy,
                           String Salt, String Sugar,Date date) {
        this.BrandName = BrandName;
        this.ItemName = ItemName;
        this.Water = Water;
        this.Fat = Fat;
        this.Energy = Energy;
        this.Salt = Salt;
        this.Sugar = Sugar;
        this.date = date;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getWater() {
        return Water;
    }

    public void setWater(String water) {
        Water = water;
    }

    public String getFat() {
        return Fat;
    }

    public void setFat(String fat) {
        Fat = fat;
    }

    public String getEnergy() {
        return Energy;
    }

    public void setEnergy(String energy) {
        Energy = energy;
    }

    public String getSalt() {
        return Salt;
    }

    public void setSalt(String salt) {
        Salt = salt;
    }

    public String getSugar() {
        return Sugar;
    }

    public void setSugar(String sugar) {
        Sugar = sugar;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
