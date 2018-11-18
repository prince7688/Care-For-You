package com.example.hp.careforyou.DietPlane;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.util.Util;
import com.example.hp.careforyou.Database.NutritionDatabase;
import com.example.hp.careforyou.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.FormatFlagsConversionMismatchException;
import java.util.Locale;

public class DietPlaneView extends android.support.v4.app.Fragment {

    private NutritionDatabase mDb;

    private ProgressBar energyProgress , fatProgress, protienProgress, carboProgress;
    private static final String DATE_FORMAT = "dd/MM/yyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    private TextView   energyProgressvalue, fatProgressvalue, protienProgressvalue, carboProgressvalue;
    private TextView planemessage;
    private RelativeLayout visivisliylayout;

    private TextView breakfast_rec, lunch_rec, dinner_rec;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.activity_diet_plane_view,container,false);

        energyProgress =(ProgressBar)view.findViewById(R.id.progressBar2);
        fatProgress =(ProgressBar)view.findViewById(R.id.progressBar3);
        protienProgress =(ProgressBar)view.findViewById(R.id.progressBar4);
        carboProgress =(ProgressBar)view.findViewById(R.id.progressBar5);


        energyProgressvalue =(TextView)view.findViewById(R.id.progressenergy);
        fatProgressvalue =(TextView)view.findViewById(R.id.progressfat);
        protienProgressvalue =(TextView)view.findViewById(R.id.progressprotien);
        carboProgressvalue =(TextView)view.findViewById(R.id.progresscarbo);


        visivisliylayout =(RelativeLayout)view.findViewById(R.id.ststicslayout);
        mDb = NutritionDatabase.getInstance(getActivity().getApplicationContext());


        breakfast_rec =(TextView)view.findViewById(R.id.breakfast_rec);
        lunch_rec = (TextView)view.findViewById(R.id.lunch_rec);
        dinner_rec =(TextView)view.findViewById(R.id.dinner_rec);

        Date cureentdate =  new Date();
        DietConsumedItemsPojo dietConsumedItemsPojo = mDb.dietItemsDao().loadConsumedPlaneByDate((dateFormat.format(cureentdate)).toString());

        if(dietConsumedItemsPojo==null)
        {
            DietItemsPojo dietItemsPojo =  mDb.dietItemsDao().loadAllPlane();

            DietConsumedItemsPojo currentdateplane = new DietConsumedItemsPojo(dateFormat.format(cureentdate).toString(),
                    dietItemsPojo.getEnergy(),dietItemsPojo.getFat(),dietItemsPojo.getProtien(),dietItemsPojo.getCarbo());

            mDb.dietItemsDao().insertConsumedPlane(currentdateplane);
        }

        updateUi();



        return view;
    }



//    @Override
//    protected void onResume() {
//        super.onResume();
//        updateUi();
//    }

    private void updateUi() {

        Date cureentdate =  new Date();


        DietItemsPojo dietItemsPojo =  mDb.dietItemsDao().loadAllPlane();
        DietConsumedItemsPojo dietConsumedItemsPojo = mDb.dietItemsDao().loadConsumedPlaneByDate((dateFormat.format(cureentdate)).toString());


        if(dietConsumedItemsPojo!=null)
        {


            Log.d("prince", "Enter in updateui");
//            planemessage.setVisibility(View.INVISIBLE);
//            visivisliylayout.setVisibility(View.VISIBLE);
            energyProgressvalue.setText(String.valueOf(Math.round(dietConsumedItemsPojo.getEnergy())));
            fatProgressvalue.setText(String.valueOf(Math.round(dietConsumedItemsPojo.getFat())) + "g Left");
            protienProgressvalue.setText(String.valueOf(Math.round(dietConsumedItemsPojo.getProtien()))+ "g Left");
            carboProgressvalue.setText(String.valueOf(Math.round(dietConsumedItemsPojo.getCarbo()))+ "g Left");

            double urrentenergy = Math.round(dietConsumedItemsPojo.getEnergy());
            double dataenergy =  Math.round(dietItemsPojo.getEnergy());

            int energypercent = (int) ((urrentenergy/dataenergy)*100);
            energyProgress.setProgress(100 - energypercent);
            Log.d("energyprogress",String.valueOf(energypercent));


            double urrentprotein = Math.round(dietConsumedItemsPojo.getProtien());
            double dataprotien = Math.round(dietItemsPojo.getProtien());
            int protienpercent = (int) ((urrentprotein/dataprotien)*100);
            protienProgress.setProgress(100-protienpercent);


            double urrentfat = Math.round(dietConsumedItemsPojo.getFat());
            double datafat = Math.round(dietItemsPojo.getFat());
            int fatpercent = (int) ((urrentfat/datafat)*100);
            fatProgress.setProgress(100-fatpercent);


            double urrentcarbo = Math.round(dietConsumedItemsPojo.getCarbo());
            double datacarbo = Math.round(dietItemsPojo.getCarbo());
            int carbopercent = (int) ((urrentcarbo/datacarbo)*100);
            carboProgress.setProgress(100-carbopercent);



//            int carbspercent = (int) (((Math.round(dietConsumedItemsPojo.getCarbo()))/(Math.round(dietItemsPojo.getCarbo())))*100);
//            carboProgress.setProgress(100-carbspercent);


            int lower_breakfast = Math.round((Math.round(dietItemsPojo.getEnergy()) * 25)/100);
            int upper_breakfast = Math.round((Math.round(dietItemsPojo.getEnergy()) * 35)/100);

            int lower_lunch = Math.round((Math.round(dietItemsPojo.getEnergy()) * 30)/100);
            int upper_lunch = Math.round((Math.round(dietItemsPojo.getEnergy()) * 40)/100);

            int lower_dinner = Math.round((Math.round(dietItemsPojo.getEnergy()) * 35)/100);
            int upper_dinner = Math.round((Math.round(dietItemsPojo.getEnergy()) * 51)/100);


            breakfast_rec.setText("RECOMMENDED " + String.valueOf(lower_breakfast) + " - " + String.valueOf(upper_breakfast) + " KCAL" );
            lunch_rec.setText("RECOMMENDED " + String.valueOf(lower_lunch) + " - " + String.valueOf(upper_lunch) + " KCAL" );
            dinner_rec.setText("RECOMMENDED " + String.valueOf(lower_dinner) + " - " + String.valueOf(upper_dinner) + " KCAL" );

        }
    }

}
