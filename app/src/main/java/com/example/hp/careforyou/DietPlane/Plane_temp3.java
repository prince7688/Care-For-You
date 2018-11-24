package com.example.hp.careforyou.DietPlane;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.hp.careforyou.Database.NutritionDatabase;
import com.example.hp.careforyou.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Plane_temp3 extends AppCompatActivity {


    RadioGroup goal_group;
    RadioButton loss_weight, gain_weight, be_healthier;
    Button caldiet_button;
    EditText goal_weight, goal_days;
    String Weightvalue;
    double Tdervalue;
    double cc;
    int temp=0;
    TextInputLayout text_goal_weight, text_goal_days,text_radio_goal;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private DatabaseReference mMessagesDatabaseReferencegoalweight;
    private DatabaseReference mMessagesDatabaseReferencegoal;

    private NutritionDatabase mDb;
    private static final String DATE_FORMAT = "dd/MM/yyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_temp3);

        Intent intent = getIntent();
        Tdervalue =intent.getDoubleExtra("Tdervalue",0);
        Weightvalue = intent.getStringExtra("Weightvalue");

        goal_group =(RadioGroup)findViewById(R.id.radiogrp_activityoption);

        loss_weight =(RadioButton)findViewById(R.id.radiobtn_lossweight);
        gain_weight =(RadioButton)findViewById(R.id.radiobtn_gainweight);
        be_healthier =(RadioButton)findViewById(R.id.radiobtn_healthier);

        text_radio_goal = (TextInputLayout)findViewById(R.id.text_inputlayout_radio);
        text_goal_weight =(TextInputLayout)findViewById(R.id.text_inputlayout_weight);
        text_goal_days =(TextInputLayout)findViewById(R.id.text_inputlayout_days);

        goal_weight =(EditText)findViewById(R.id.text_weight);
        goal_days =(EditText)findViewById(R.id.text_days);

        caldiet_button =(Button)findViewById(R.id.calbutton);


        goal_group.clearCheck();

        mDb = NutritionDatabase.getInstance(getApplicationContext());

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("goaldays");
        mMessagesDatabaseReferencegoalweight = mFirebaseDatabase.getReference().child("goalweight");
        mMessagesDatabaseReferencegoal = mFirebaseDatabase.getReference().child("goal");

        goal_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                RadioButton rb =(RadioButton)findViewById(checkedId);
                if (null != rb && checkedId > -1) {

                    if(rb.getText().equals("Gain Weight") || rb.getText().equals("Loss Weight"))
                    {
                        text_goal_weight.setVisibility(View.VISIBLE);
                        text_goal_days.setVisibility(View.VISIBLE);
                    }

                    else {
                        text_goal_weight.setVisibility(View.INVISIBLE);
                        text_goal_days.setVisibility(View.INVISIBLE);
                    }

                }
            }
        });

        caldiet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkerror())
                {
                    RadioButton rb2 = (RadioButton)findViewById(goal_group.getCheckedRadioButtonId());
                     if(rb2.getText().equals("Be Healthier"))
                    {
                        mMessagesDatabaseReference.push().setValue(0);
                        mMessagesDatabaseReferencegoal.push().setValue("Be Healthier");
                        mMessagesDatabaseReferencegoalweight.push().setValue(0);
                        cc = Tdervalue;
                        temp=1;
                    }


                   if(rb2.getText().equals("Gain Weight") ) {
                       int targetvalue = Integer.parseInt(goal_weight.getText().toString());
                       int weightdiff = Integer.parseInt(Weightvalue) - targetvalue;
                       int absweightdiff = Math.abs(weightdiff);
                       int comvalue = (absweightdiff * 7200)/Integer.parseInt(goal_days.getText().toString());

                      if (Integer.parseInt(goal_weight.getText().toString())
                            <= Integer.parseInt(Weightvalue)) {
                        text_goal_weight.setError("*Weight must be grater than your weight");
                        temp=0;
                    } else {
                          mMessagesDatabaseReferencegoal.push().setValue("GAIN WEIGHT");
                        text_goal_weight.setError(null);
                          mMessagesDatabaseReference.push().setValue(goal_days.getText().toString());
                          mMessagesDatabaseReferencegoalweight.push().setValue(goal_weight.getText().toString());
                        cc = Tdervalue + comvalue;
                        temp = 1;

                    }
                     }

                   else if(rb2.getText().equals("Loss Weight"))

                    {
                        int targetvalue = Integer.parseInt(goal_weight.getText().toString());
                        int weightdiff = Integer.parseInt(Weightvalue) - targetvalue;
                        int absweightdiff = Math.abs(weightdiff);
                        int comvalue = (absweightdiff * 7200)/Integer.parseInt(goal_days.getText().toString());
                        if (Integer.parseInt(goal_weight.getText().toString())
                                >= Integer.parseInt(Weightvalue)) {
                            text_goal_weight.setError("*Weight must be lower than your weight");
                            temp=0;
                        } else {
                            mMessagesDatabaseReferencegoal.push().setValue("lOSS WEIGHT");
                            mMessagesDatabaseReference.push().setValue(goal_days.getText().toString());
                            mMessagesDatabaseReferencegoalweight.push().setValue(goal_weight.getText().toString());
                            text_goal_weight.setError(null);
                            cc = Tdervalue - comvalue;
                            temp = 1;
                        }
                    }


                    if(temp==1)
                    {
                        double protien = (cc/4)* 0.5;
                        double carbo = (cc/4)* 0.4;
                        double fat = (cc/9) * 0.1;
                        Toast.makeText(Plane_temp3.this, "protien is  " + Double.toString(protien) + "carbo is " + Double.toString(carbo)
                                + "fat is "+ Double.toString(fat),Toast.LENGTH_LONG).show();

                        Date date = new Date();
                        DietItemsPojo dietItemsPojo = new DietItemsPojo(dateFormat.format(date).toString(),cc,fat,
                                protien,carbo);
                        DietConsumedItemsPojo dietConsumedItemsPojo = new DietConsumedItemsPojo(dateFormat.format(date).toString(),cc,fat,
                                protien,carbo);
                        Log.d("prince","insert first data sucessfully");

                        mDb.dietItemsDao().insertPlane(dietItemsPojo);
                        mDb.dietItemsDao().insertConsumedPlane(dietConsumedItemsPojo);
                        Intent planeview =  new Intent(Plane_temp3.this,PlaneViewActivity.class);
                        startActivity(planeview);
                        finish();



                    }
                }



            }
        });



    }

    private boolean  checkerror() {

        if(goal_group.getCheckedRadioButtonId() ==-1) {
            text_radio_goal.setError("*Please Select Your Goal");
            return false;
        }

        else
        {
            text_radio_goal.setError(null);
            RadioButton rb =(RadioButton)findViewById(goal_group.getCheckedRadioButtonId());
            if(rb.getText().equals("Gain Weight") || rb.getText().equals("Loss Weight"))

            {
                boolean weightdigitsOnly = TextUtils.isDigitsOnly(goal_weight.getText());
                boolean daysdigitsOnly = TextUtils.isDigitsOnly(goal_days.getText());

                if (!goal_weight.getText().toString().equals("")) {
                    if (goal_weight.getText() == null || weightdigitsOnly == false || Integer.parseInt(goal_weight.getText().toString()) < 10
                            || Integer.parseInt(goal_weight.getText().toString()) > 200) {
                        text_goal_weight.setError("*Please Enter a Valid Weight");
                        return false;
                    } else {
                        text_goal_weight.setError(null);
                    }
                } else {
                    text_goal_weight.setError("*Please Enter  Weight");
                    return false;
                }


                if (!goal_days.getText().toString().equals("")) {

                    if (goal_days.getText() == null || daysdigitsOnly == false) {
                        text_goal_days.setError("*Please Enter Valid Days");
                        return false;
                    } else {
                        int targetvalue = Integer.parseInt(goal_weight.getText().toString());
                        int weightdiff = Integer.parseInt(Weightvalue) - targetvalue;
                        int absweightdiff = Math.abs(weightdiff);
                        int comvalue = (absweightdiff * 7200) / Integer.parseInt(goal_days.getText().toString());
                        //Toast.makeText(Plane_temp3.this,comvalue,Toast.LENGTH_LONG).show();
                        if (comvalue > 1000) {
                            text_goal_days.setError("*Days are Unhealthier Please Enter Valid Days");
                            return false;
                        } else
                            text_goal_days.setError(null);
                        //
                    }
                } else {
                    text_goal_days.setError("*Please Enter Days");
                    return false;
                }

            }


            return true;

        }
    }
}
