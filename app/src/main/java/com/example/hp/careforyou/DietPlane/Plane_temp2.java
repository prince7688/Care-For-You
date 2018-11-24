package com.example.hp.careforyou.DietPlane;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Toast;

import com.example.hp.careforyou.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Plane_temp2 extends AppCompatActivity {

    CardView activity_sedentry , activity_lightactive , activity_moderateactive , activity_veryactive, activity_extraactive;
    double Tder;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_temp2);

        Intent intent = getIntent();
        final double Bmrvalue =intent.getDoubleExtra("Bmrvalue",0);
        final String Weightvalue = intent.getStringExtra("Weightvalue");
        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbaractivity);
        setSupportActionBar(toolbar);

        activity_sedentry =(CardView)findViewById(R.id.activity_sedentaryactive);
        activity_lightactive =(CardView)findViewById(R.id.activity_lightactive);
        activity_moderateactive =(CardView)findViewById(R.id.activity_moderateactive);
        activity_veryactive = (CardView)findViewById(R.id.activity_veryactive);
        activity_extraactive =(CardView)findViewById(R.id.activity_extraactive);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessagesDatabaseReference = mFirebaseDatabase.getReference().child("acitivity");

        activity_sedentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tder =Bmrvalue * 1.2;
                mMessagesDatabaseReference.push().setValue("sedentry");
                Toast.makeText(Plane_temp2.this,"Tdee value is "+ Tder, Toast.LENGTH_LONG).show();
                Intent PlaneIntent2 = new Intent(Plane_temp2.this,Plane_temp3.class);
                PlaneIntent2.putExtra("Tdervalue",Tder);
                PlaneIntent2.putExtra("Weightvalue",Weightvalue);
                startActivity(PlaneIntent2);
                finish();
            }
        });

        activity_lightactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tder = Bmrvalue * 1.375;
                mMessagesDatabaseReference.push().setValue("lighactive");
                Toast.makeText(Plane_temp2.this,"Tdee value is "+ Tder, Toast.LENGTH_LONG).show();
                Intent PlaneIntent2 = new Intent(Plane_temp2.this,Plane_temp3.class);
                PlaneIntent2.putExtra("Tdervalue",Tder);
                PlaneIntent2.putExtra("Weightvalue",Weightvalue);
                startActivity(PlaneIntent2);
                finish();
            }
        });

        activity_moderateactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tder =  Bmrvalue * 1.55;
                mMessagesDatabaseReference.push().setValue("moderateactive");
                Toast.makeText(Plane_temp2.this,"Tdee value is "+ Tder, Toast.LENGTH_LONG).show();
                Intent PlaneIntent2 = new Intent(Plane_temp2.this,Plane_temp3.class);
                PlaneIntent2.putExtra("Tdervalue",Tder);
                PlaneIntent2.putExtra("Weightvalue",Weightvalue);
                startActivity(PlaneIntent2);
                finish();
            }
        });

        activity_veryactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tder = Bmrvalue * 1.725;
                mMessagesDatabaseReference.push().setValue("veryactive");
                Toast.makeText(Plane_temp2.this,"Tdee value is "+ Tder, Toast.LENGTH_LONG).show();
                Intent PlaneIntent2 = new Intent(Plane_temp2.this,Plane_temp3.class);
                PlaneIntent2.putExtra("Tdervalue",Tder);
                PlaneIntent2.putExtra("Weightvalue",Weightvalue);
                startActivity(PlaneIntent2);
                finish();
            }
        });

        activity_extraactive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tder =  Bmrvalue* 1.9;
                mMessagesDatabaseReference.push().setValue("extraeactive");
                Toast.makeText(Plane_temp2.this,"Tdee value is "+ Tder, Toast.LENGTH_LONG).show();
                Intent PlaneIntent2 = new Intent(Plane_temp2.this,Plane_temp3.class);
                PlaneIntent2.putExtra("Tdervalue",Tder);
                PlaneIntent2.putExtra("Weightvalue",Weightvalue);
                startActivity(PlaneIntent2);
                finish();
            }
        });



    }
}
