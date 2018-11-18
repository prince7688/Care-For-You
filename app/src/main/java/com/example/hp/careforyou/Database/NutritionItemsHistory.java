package com.example.hp.careforyou.Database;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.hp.careforyou.NutritionItem;
import com.example.hp.careforyou.R;

import java.util.ArrayList;
import java.util.List;

public class NutritionItemsHistory extends AppCompatActivity  {

    private NutritionDatabase mDb;

    private RecyclerView mRecyclerview;

    private static final String TAG = NutritionItemsHistory.class.getSimpleName();

    private HistoryAdapter historyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mDb = NutritionDatabase.getInstance(getApplicationContext());

        setContentView(R.layout.activity_nutrition_items_history);

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbarhistory);
        setSupportActionBar(toolbar);

        mRecyclerview = (RecyclerView)findViewById(R.id.recyclerviewitems);

        mRecyclerview.setLayoutManager(new LinearLayoutManager(this));

        historyAdapter =new HistoryAdapter(this, initdata());


        //mRecyclerview.setAdapter(itemsAdapter);

        mRecyclerview.setAdapter(historyAdapter);




        //retrieveItems();

    }


    private List<ParentObject> initdata()
    {
        List<Extra> extras = mDb.nutritionDao().loadParentItems();

        List<ParentView> parentViews = new ArrayList<>();

        for(Extra ex : extras)
        {
            parentViews.add(new ParentView(ex.getBrandName() ,ex.getItemName(),ex.getDate()));
        }

        List<ParentObject> parentObject =  new ArrayList<>();

        int i=1;
        for(ParentView  parentView : parentViews)
        {
            List<Object> childlist = new ArrayList<>();
           // int currentid = parentView.getId();
            childlist.add(mDb.nutritionDao().loadChildItems(i));
            parentView.setChildObjectList(childlist);
            parentView.setFirstletter(parentView.getBrandName());
            parentObject.add(parentView);
            i++;
        }
        return parentObject;
    }

    @Override
    protected void onResume() {
        super.onResume();
        //itemsAdapter.setItems(mDb.nutritionDao().loadAllItems());
        //retrieveItems();
    }

    //  private void retrieveItems()
    //{
//        Log.d(TAG, "Actively retrieving the items from the DataBase");
//
//        AppExecutors.getInstance().diskIO().execute(new Runnable() {
//            @Override
//            public void run() {
//                Log.d(TAG, "Receiving database update from LiveData");
//                nutritionEntities =  mDb.nutritionDao().loadAllItems();
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        itemsAdapter.setItems(nutritionEntities);
//                    }
//                });
//
//            }
//        });


      //  nutritionEntities =  mDb.nutritionDao().loadAllItems();
       // itemsAdapter.setItems(nutritionEntities);

//        LiveData<List<NutritionEntity>> items =  mDb.nutritionDao().loadAllItems();
//        items.observe(this, new Observer<List<NutritionEntity>>() {
//            @Override
//            public void onChanged(@Nullable List<NutritionEntity> nutritionEntities) {
//                Log.d(TAG, "Receiving database update from LiveData");
//                itemsAdapter.setItems(nutritionEntities);
//            }
//        });
    //}


}
