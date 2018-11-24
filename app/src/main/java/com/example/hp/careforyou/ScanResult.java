package com.example.hp.careforyou;

import android.app.LoaderManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.careforyou.Database.AppExecutors;
import com.example.hp.careforyou.Database.Extra;
import com.example.hp.careforyou.Database.Extra1;
import com.example.hp.careforyou.Database.NutritionDatabase;
import com.example.hp.careforyou.Database.NutritionEntity;
import com.example.hp.careforyou.DietPlane.DietConsumedItemsPojo;
import com.example.hp.careforyou.DietPlane.DietItemsPojo;
import com.example.hp.careforyou.DietPlane.PlaneViewActivity;
import com.example.hp.careforyou.DietPlane.Plane_temp;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ScanResult extends AppCompatActivity implements LoaderManager.LoaderCallbacks<NutritionItem> {

    private static final int NUTRITION_LOADER_ID = 1;
    private static  String Food_REQUEST_URL;

    private NutritionDatabase mDb;

    private int temp = 0;

    private int consumed=0;

    double finalenergy;

    private static final String Food_BASE_REQUEST_URL ="https://api.nutritionix.com/v1_1/item";
    final static String PARAM_QUERY_upc = "upc";

    final static String PARAM_QUERY_appid = "appId";

    final static String PARAM_QUERY_appkey = "appKey";

    final static String appid = "7c5ec594";

    final static String appkey = "d5d2b7f30df24e9b91794a789a2ad788";

    public static final String LOG_TAG = ScanResult.class.getSimpleName();
    TextView itemname,watergm,fatgm,energygm,saltgm,sugargm;
    TextView cholesgm,charbogm,fibergm,protiengm,vitaminAgm;

    double energy=0, fat=0,protien=0, carbo=0;
    String brand_consumed, item_consumed;

    LinearLayout cardviewlayout;
    private static final String DATE_FORMAT = "dd/MM/yyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        Intent intent = getIntent();
        String upccode = intent.getStringExtra("barcode");
        BuildUrl(upccode);

        ConnectivityManager connectivityManager =(ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo!=null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NUTRITION_LOADER_ID, null, this);
        }

        else
        {
            TextView internetview =  (TextView)findViewById(R.id.internettext);
            View loadingIndicator = findViewById(R.id.progressBar);
            loadingIndicator.setVisibility(View.GONE);
            internetview.setVisibility(View.VISIBLE);
        }

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbarresult);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.barcodeimage);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

        mDb = NutritionDatabase.getInstance(getApplicationContext());

    }


    void BuildUrl(String upccode)
    {
        Uri builtUri = Uri.parse(Food_BASE_REQUEST_URL).buildUpon()
                      .appendQueryParameter(PARAM_QUERY_upc,upccode)
                      .appendQueryParameter(PARAM_QUERY_appid,appid)
                      .appendQueryParameter(PARAM_QUERY_appkey,appkey)
                   .build();
        Food_REQUEST_URL = builtUri.toString();
    }

    private void updateUi(NutritionItem s) {

        itemname =(TextView)findViewById(R.id.itemname);
        watergm =(TextView)findViewById(R.id.water);
        fatgm =(TextView)findViewById(R.id.fat);
        energygm =(TextView)findViewById(R.id.energy);
        saltgm =(TextView)findViewById(R.id.salt);
        sugargm =(TextView)findViewById(R.id.sugar);
        cholesgm =(TextView)findViewById(R.id.choles);
        charbogm =(TextView)findViewById(R.id.charbo);
        fibergm =(TextView)findViewById(R.id.fiber);
        protiengm =(TextView)findViewById(R.id.protien);
        vitaminAgm =(TextView)findViewById(R.id.vitaminA);


        itemname.setText(s.getmBrandName()+ ", " + s.getmItemName());
        DecimalFormat precision = new DecimalFormat("0.00");

        brand_consumed = s.getmBrandName();
        item_consumed =  s.getmItemName();

        if(s.getmWater()!="null")
             watergm.setText(precision.format(Double.valueOf(s.getmWater()))+ " gm");
        else
            watergm.setText(0.00 + " gm");


        if(s.getmFat()!="null") {
            fatgm.setText(precision.format(Double.valueOf(s.getmFat())) + " gm");
            fat = Double.parseDouble(s.getmFat());
        }

        else {
            fatgm.setText(0.00 + " gm");
            fat = 0;
        }

        if(s.getmEnergy()!="null") {
            energygm.setText(precision.format(Double.valueOf(s.getmEnergy())) + " Cal");
            energy = Double.parseDouble(s.getmEnergy());
        }
        else {
            energygm.setText(0.00 + " Cal");
            energy =0;
        }

        if(s.getmSalt()!="null")
            saltgm.setText(precision.format(Double.valueOf(s.getmSalt()))+ " gm");
        else
            saltgm.setText(0.00 + " gm");

        if(s.getmSugar()!="null")
            sugargm.setText(precision.format(Double.valueOf(s.getmSugar()))+ " gm");
        else
            sugargm.setText(0.00 + " gm");

        if(s.getmCholes()!="null")
            cholesgm.setText(precision.format(Double.valueOf(s.getmCholes()))+ " gm");
        else
            cholesgm.setText(0.00 + " gm");

        if(s.getmCharbo()!="null") {
            charbogm.setText(precision.format(Double.valueOf(s.getmCharbo())) + " gm");
            carbo = Double.parseDouble(s.getmCharbo());
        }
        else {
            charbogm.setText(0.00 + " gm");
            carbo = 0;
        }

        if(s.getmFiber()!="null")
            fibergm.setText(precision.format(Double.valueOf(s.getmFiber()))+ " gm");
        else
            fibergm.setText(0.00 + " gm");

        if(s.getmProtien()!="null") {
            protiengm.setText(precision.format(Double.valueOf(s.getmProtien())) + " gm");
            protien = Double.parseDouble(s.getmProtien());
        }
        else {
            protiengm.setText(0.00 + " gm");
            protien = 0;
        }

        if(s.getmVitaminA()!="null")
                    vitaminAgm.setText(precision.format(Double.valueOf(s.getmVitaminA()))+ " gm");
                else
                    vitaminAgm.setText(0.00 + " gm");

                Date date = new Date();
       final NutritionEntity nutritionEntity = new NutritionEntity(s.getmBrandName(),s.getmItemName(),s.getmWater(),s.getmFat(),s.getmEnergy()
        ,s.getmSalt(),s.getmSugar(),date);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.nutritionDao().insertItem(nutritionEntity);
            }
        });

           // mDb.nutritionDao().insertItem(nutritionEntity);

            }


    @Override
    public Loader<NutritionItem> onCreateLoader(int i, Bundle bundle) {

        return new NutritionItemsLoader(this,Food_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<NutritionItem> loader, NutritionItem nutritionItem) {

        View loadingIndicator = findViewById(R.id.progressBar);
        loadingIndicator.setVisibility(View.GONE);

        if (nutritionItem != null) {
            cardviewlayout =(LinearLayout)findViewById(R.id.cardviewlayout);
            cardviewlayout.setVisibility(View.VISIBLE);
            temp=1;
            updateUi(nutritionItem);
        }
        else
        {
           TextView emptyview = (TextView)findViewById(R.id.empty_view);
           emptyview.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<NutritionItem> loader) {

        //updateUi(new NutritionItem(null,null,null,null,null,null,null));
    }


//    public boolean onPrepareOptionsMenu(Menu menu)
//    {
//        MenuItem register = menu.findItem(R.id.addicon);
//        if(temp==0)
//        {
//            register.setVisible(false);
//        }
//        else
//        {
//            register.setVisible(true);
//        }
//        return true;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.scanmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(temp==1) {

            switch (item.getItemId()) {
                case R.id.addicon:
                    additem();
                    return true;
            }

            return false;
        }


        return false;

    }

    private void additem() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Are you sure to consume this item?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                //dialog.dismiss();
                consumeditem();
                if(consumed==1)
                {
                    Extra1 extra = new Extra1(brand_consumed,item_consumed,new Date());
                    mDb.dietItemsList().insertItem(extra);
                    sendNotification();
                }
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                // Do nothing
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();
    }



    public void sendNotification() {

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this);

        //Create the intent that’ll fire when the user taps the notification//

        Intent intent = new Intent(ScanResult.this, PlaneViewActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        mBuilder.setContentIntent(pendingIntent);

        mBuilder.setSmallIcon(R.drawable.welcome_image);
        mBuilder.setContentTitle("Today Calory Reminder!!!");
        mBuilder.setContentText(String.valueOf(Math.round(finalenergy))+ " KCalory Left");

        NotificationManager mNotificationManager =

                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(001, mBuilder.build());
    }

    private void consumeditem() {

        Date currentdate = new Date();

        DietConsumedItemsPojo dietConsumedItemsPojo = mDb.dietItemsDao().loadConsumedPlaneByDate((dateFormat.format(currentdate)).toString());
        DietItemsPojo dietItemsPojo =  mDb.dietItemsDao().loadAllPlane();

        if(dietItemsPojo!=null) {

            if (dietConsumedItemsPojo == null) {


                DietConsumedItemsPojo currentdateplane = new DietConsumedItemsPojo(dateFormat.format(currentdate).toString(),
                        (dietItemsPojo.getEnergy()-energy), (dietItemsPojo.getFat() - fat) , (dietItemsPojo.getProtien()-protien),
                        (dietItemsPojo.getCarbo()-carbo));

                mDb.dietItemsDao().insertConsumedPlane(currentdateplane);
                Toast.makeText(ScanResult.this,"You Consumed this Item",Toast.LENGTH_LONG).show();
                consumed=1;
                finalenergy =dietItemsPojo.getEnergy()-energy;


            }

            else {
                finalenergy = dietConsumedItemsPojo.getEnergy() - energy;
                double finalprotine = dietConsumedItemsPojo.getProtien() - protien;
                double finalfat = dietConsumedItemsPojo.getFat() - fat;
                double finalcarbo = dietConsumedItemsPojo.getCarbo() - carbo;

                if (finalenergy < 0 || finalprotine < 0 || finalcarbo < 0 || finalfat < 0) {
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(ScanResult.this);
                    builder1.setTitle("You Consumed Your daily limit !!");
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    builder1.setNegativeButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();

                } else {

                    mDb.dietItemsDao().updateConsumedPlane(String.valueOf(finalenergy), String.valueOf(finalfat), String.valueOf(finalprotine),
                            String.valueOf(finalcarbo), dateFormat.format(currentdate));
                    Toast.makeText(ScanResult.this, "You Consumed this Item", Toast.LENGTH_LONG).show();
                    consumed = 1;
                }
            }
        }

        else
        {

            AlertDialog.Builder builder1 = new AlertDialog.Builder(ScanResult.this);
            builder1.setTitle("You have not set up Your Profile !!");
            builder1.setMessage("Set Profile ??");
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            Intent intent = new Intent(ScanResult.this, Plane_temp.class);
                            startActivity(intent);
                            finish();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
        }

    }



}
