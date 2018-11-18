package com.example.hp.careforyou;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class NutritionItemsLoader extends AsyncTaskLoader<NutritionItem> {

    private static  final String LOG_TAG = NutritionItemsLoader.class.getName();

    private String mUrl;

    public NutritionItemsLoader(Context context,String url) {
        super(context);
        mUrl =url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public NutritionItem loadInBackground() {
       if(mUrl == null)
            return null;

        NutritionItem nutritionItem =  HttpQueryUtils.FetchNutritionData(mUrl);

        return nutritionItem;


    }
}
