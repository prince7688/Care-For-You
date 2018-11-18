package com.example.hp.careforyou.DietPlane;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = "dietplaneconsumeditems")
public class DietConsumedItemsPojo {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int Id;

    private String PlaneDate;

    private double  Energy;

    private double Fat;

    private double Protien;

    private double Carbo;

    public DietConsumedItemsPojo(@NonNull int Id, String PlaneDate, double Energy, double Fat, double Protien, double Carbo) {
        this.Id = Id;
        this.PlaneDate = PlaneDate;
        this.Energy = Energy;
        this.Fat = Fat;
        this.Protien = Protien;
        this.Carbo = Carbo;
    }


    @Ignore
    public DietConsumedItemsPojo(String PlaneDate, double Energy, double Fat, double Protien, double Carbo) {
        this.PlaneDate = PlaneDate;
        this.Energy = Energy;
        this.Fat = Fat;
        this.Protien = Protien;
        this.Carbo = Carbo;
    }

    @NonNull
    public int getId() {
        return Id;
    }

    public void setId(@NonNull int id) {
        this.Id = id;
    }

    public String getPlaneDate() {
        return PlaneDate;
    }

    public void setPlaneDate(String planeDate) {
        this.PlaneDate = planeDate;
    }

    public double getEnergy() {
        return  Energy;
    }

    public void setEnergy(double energy) {
        this.Energy = energy;
    }

    public double getFat() {
        return  Fat;
    }

    public void setFat(double fat) {
        this.Fat = fat;
    }

    public double getProtien() {
        return Protien;
    }

    public void setProtien(double protien) {
        this.Protien = protien;
    }

    public double getCarbo() {
        return  Carbo;
    }

    public void setCarbo(double carbo) {
        this.Carbo = carbo;
    }

}
