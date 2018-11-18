//package com.example.hp.careforyou.DietPlane;
//
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import com.example.hp.careforyou.Database.NutritionDatabase;
//import com.example.hp.careforyou.R;
//
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.Locale;
//
//public class SetPlane extends AppCompatActivity {
//
//    private EditText mEnergy, mFat, mProtien, mCarbo;
//    private NutritionDatabase mDb;
//    private Button summitbutton;
//
//    private static final String DATE_FORMAT = "dd/MM/yyy";
//    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.setplane);
//
//        Log.d("prince","enter in oncreate");
//
//        mEnergy  =(EditText)findViewById(R.id.energy);
//        mFat  =(EditText)findViewById(R.id.fat);
//        mProtien = (EditText)findViewById(R.id.protien);
//        mCarbo =(EditText)findViewById(R.id.carbo);
//        summitbutton  =(Button)findViewById(R.id.summitplane);
//
//
//        mDb = NutritionDatabase.getInstance(getApplicationContext());
//
//        summitbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//             {
//
//                Date date = new Date();
//                DietItemsPojo dietItemsPojo = new DietItemsPojo(dateFormat.format(date).toString(),mEnergy.getText().toString(),mFat.getText().toString(),
//                        mProtien.getText().toString(),mCarbo.getText().toString());
//
//
//                if(mDb.dietItemsDao().loadPlaneDate().isEmpty()){
//
//                    Log.d("prince","insert first data sucessfully");
//                    mDb.dietItemsDao().insertPlane(dietItemsPojo);
//                    Toast.makeText(SetPlane.this,"Set Your Plane Successfully",Toast.LENGTH_LONG).show();
//                    finish(); }
//
//                else
//                {
//                    List<String> dates =  mDb.dietItemsDao().loadPlaneDate();
//
//                    for(String currentdate: dates)
//                    {
//                        if(currentdate.matches(dateFormat.format(date).toString()))
//                        {
//                            Log.d("prince","update data sucessfully");
//                            mDb.dietItemsDao().updatePlane(mEnergy.getText().toString(),mFat.getText().toString(),
//                                    mProtien.getText().toString(),mCarbo.getText().toString(),currentdate);
//                            Toast.makeText(SetPlane.this,"Update Your Plane Successfully",Toast.LENGTH_LONG).show();
//                            finish();
//                        }
//                    }
//                    Log.d("prince","insert data sucessfully");
//                    mDb.dietItemsDao().insertPlane(dietItemsPojo);
//                    Toast.makeText(SetPlane.this,"Set Your Plane Successfully",Toast.LENGTH_LONG).show();
//                    finish();
//                }
//
//            }
//
//        });
//
//
//
//    }
//}
//
