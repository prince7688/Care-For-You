package com.example.hp.careforyou.Database;

public class ChildView {

    private  String Water;

    private  String Fat;

    private  String Energy;

    private  String Salt;

    private  String Sugar;

    public ChildView(String Water, String Fat, String Energy, String Salt, String Sugar) {
        this.Water = Water;
        this.Fat = Fat;
        this.Energy = Energy;
        this.Salt = Salt;
        this.Sugar = Sugar;
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
}
