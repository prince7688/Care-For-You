package com.example.hp.careforyou.Database;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ParentView implements ParentObject {

    private List <Object> mchildrenlist;
    private  String ItemName;
    private  String BrandName;
    private String Firstletter;
    private Date date;

    public ParentView(String BrandName,String ItemName,Date date) {
        this.BrandName = BrandName;
        this.ItemName = ItemName;
        this.date=  date;
    }

    @Override
    public List<Object> getChildObjectList() {
        return mchildrenlist;
    }

    @Override
    public void setChildObjectList(List<Object> list) {

        mchildrenlist =list;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Object> getMchildrenlist() {
        return mchildrenlist;
    }

    public void setMchildrenlist(List<Object> mchildrenlist) {
        this.mchildrenlist = mchildrenlist;
    }

    public String getFirstletter() {
        return Firstletter;
    }

    public void setFirstletter(String firstletter) {
        Firstletter = firstletter;
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
