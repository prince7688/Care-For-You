package com.example.hp.careforyou.Database;

import android.arch.persistence.room.Entity;

import java.util.Date;
import java.util.List;

public class Extra {
    private  String ItemName;
    private  String BrandName;
    private Date date;


    public Extra(String BrandName, String ItemName,Date date) {
        this.BrandName = BrandName;
        this.ItemName = ItemName;
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
}

