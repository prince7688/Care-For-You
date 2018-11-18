package com.example.hp.careforyou.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity(tableName = "dietplaneitemlist")
public class Extra1 {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    private  String brandName;

    private  String itemName;

    private Date date;

    public Extra1(@NonNull int id, String brandName, String itemName, Date date) {
        this.id = id;
        this.brandName = brandName;
        this.itemName = itemName;
        this.date = date;
    }

    @Ignore
    public Extra1(String brandName, String itemName, Date date) {
        this.brandName = brandName;
        this.itemName = itemName;
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
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}

