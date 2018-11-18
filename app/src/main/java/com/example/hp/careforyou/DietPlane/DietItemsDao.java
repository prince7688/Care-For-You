package com.example.hp.careforyou.DietPlane;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.Date;
import java.util.List;

@Dao
public interface DietItemsDao  {

    @Query("SELECT * FROM dietplaneitems")
    DietItemsPojo loadAllPlane();

//    @Query("UPDATE dietplaneitems SET Energy = :energy, Fat = :fat, Protien = :protien, Carbo = :carbo WHERE PlaneDate = :currentdate")
//    void updatePlane(String energy, String fat,String protien, String carbo, String currentdate);

    @Query("SELECT PlaneDate  FROM  dietplaneitems")
    List<String> loadPlaneDate();

    @Insert
    void insertPlane(DietItemsPojo dietItemsPojo);

    @Delete
    void deletePlane(DietItemsPojo dietItemsPojo);


    @Query("SELECT * FROM dietplaneitems WHERE PlaneDate = :date ")
    DietItemsPojo loadPlaneByDate(String date);





    @Query("SELECT * FROM dietplaneconsumeditems")
    DietConsumedItemsPojo loadAllConsumedPlane();

    @Query("UPDATE dietplaneconsumeditems SET Energy = :energy, Fat = :fat, Protien = :protien, Carbo = :carbo WHERE PlaneDate = :currentdate")
    void updateConsumedPlane(String energy, String fat,String protien, String carbo, String currentdate);

    @Query("SELECT PlaneDate  FROM  dietplaneconsumeditems")
    List<String> loadConsumedPlaneDate();

    @Insert
    void insertConsumedPlane(DietConsumedItemsPojo dietConsumedItemsPojo);

    @Delete
    void deleteConsumedPlane(DietConsumedItemsPojo dietConsumedItemsPojo);


    @Query("SELECT * FROM dietplaneconsumeditems WHERE PlaneDate = :date ")
    DietConsumedItemsPojo loadConsumedPlaneByDate(String date);

}
