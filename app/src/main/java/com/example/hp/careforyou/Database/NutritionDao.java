package com.example.hp.careforyou.Database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NutritionDao {

    @Query("SELECT * FROM Nutrition")
    //LiveData<List<NutritionEntity>> loadAllItems();
    List<NutritionEntity> loadAllItems();

    @Query("SELECT BrandName, ItemName , date  FROM Nutrition")
    List<Extra> loadParentItems();

//    @Query("SELECT Water, Fat, Energy, Salt, Sugar  FROM Nutrition")
//    List<ChildView> loadChildItems();

    @Query("SELECT Water, Fat, Energy, Salt, Sugar FROM Nutrition WHERE id = :id")
    ChildView loadChildItems(int id);


    @Insert
    void insertItem(NutritionEntity nutritionEntity);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateItem(NutritionEntity nutritionEntity);

    @Delete
    void deleteItem(NutritionEntity nutritionEntity);

}
