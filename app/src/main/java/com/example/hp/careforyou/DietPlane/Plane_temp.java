package com.example.hp.careforyou.DietPlane;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.hp.careforyou.R;
import com.example.hp.careforyou.ScanItemsActivity;
import com.example.hp.careforyou.ScanResult;

public class Plane_temp extends AppCompatActivity{

    private Button image_male_button , image_female_button , male_button , female_button;
    LinearLayout linearlayout_image, linearlayout_details;

    TextInputLayout text_inputlayout_age , text_inputlayout_height, text_inputlayout_weight;

    EditText text_age, text_height, text_weight;

    Button next_button;

    double Bmr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_details);

        image_male_button = (Button)findViewById(R.id.image_male_button);
        image_female_button = (Button)findViewById(R.id.image_female_button);

        male_button =(Button)findViewById(R.id.male_button);
        female_button =(Button)findViewById(R.id.female_button);


        linearlayout_details =(LinearLayout)findViewById(R.id.linearlayout_details);
        linearlayout_image =(LinearLayout)findViewById(R.id.linearlayout_image);

        text_inputlayout_age = (TextInputLayout)findViewById(R.id.text_inputlayout_age);
        text_inputlayout_height = (TextInputLayout)findViewById(R.id.text_inputlayout_height);
        text_inputlayout_weight = (TextInputLayout)findViewById(R.id.text_inputlayout_weight);

        text_age =(EditText)findViewById(R.id.text_age);
        text_height =(EditText)findViewById(R.id.text_height);
        text_weight =(EditText)findViewById(R.id.text_weight);

        next_button =(Button)findViewById(R.id.next_button);


        image_male_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearlayout_image.setVisibility(View.GONE);
                linearlayout_details.setVisibility(View.VISIBLE);

                female_button.setEnabled(false);

                next_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(checkerror())
                        {
                            double Bmr1  = (10 * (Double.parseDouble(text_weight.getText().toString())));
                            double Bmr2  = (6.25 * (Double.parseDouble(text_height.getText().toString())));
                            int Bmr3 =  (5 * (Integer.parseInt(text_age.getText().toString())));

                            Bmr = Bmr1 +Bmr2 -Bmr3 +5;
                            Log.d("Bmr value is ",Double.toString(Bmr) );
                            Toast.makeText(Plane_temp.this, "Bmr is " + Double.toString(Bmr), Toast.LENGTH_LONG).show();
                            Intent planeintent = new Intent(Plane_temp.this,Plane_temp2.class);
                            planeintent.putExtra("Bmrvalue",Bmr);
                            planeintent.putExtra("Weightvalue",text_weight.getText().toString());
                            startActivity(planeintent);
                            finish();
                        }

                    }
                });

            }
        });



        image_female_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                linearlayout_image.setVisibility(View.GONE);
                linearlayout_details.setVisibility(View.VISIBLE);

                male_button.setEnabled(false);

                next_button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if(checkerror())
                        {
                            double Bmr1  = (10 * (Double.parseDouble(text_weight.getText().toString())));
                            double Bmr2  = (6.25 * (Double.parseDouble(text_height.getText().toString())));
                            int Bmr3 =  (5 * (Integer.parseInt(text_age.getText().toString())));

                            Bmr = Bmr1 +Bmr2 - Bmr3 - 161;
                            Log.d("Bmr value is ",Double.toString(Bmr) );
                            Toast.makeText(Plane_temp.this,"Bmr is " + Double.toString(Bmr), Toast.LENGTH_LONG).show();
                            Intent planeintent = new Intent(Plane_temp.this,Plane_temp2.class);
                            planeintent.putExtra("Bmrvalue",Bmr);
                            planeintent.putExtra("Weightvalue",text_weight.getText().toString());
                            startActivity(planeintent);
                            finish();
                        }
                    }
                });

            }
        });



    }


   private  boolean checkerror()
    {
                int erroraage=0,errorheight=0,errorweight=0;
                boolean agedigitsOnly = TextUtils.isDigitsOnly(text_age.getText());

                boolean heightdigitsOnly = TextUtils.isDigitsOnly(text_height.getText());

                boolean weightdigitsOnly = TextUtils.isDigitsOnly(text_weight.getText());

                if(!text_age.getText().toString().equals("")) {

                    if (text_age.getText() == null || agedigitsOnly == false || Integer.parseInt(text_age.getText().toString()) < 1
                            || Integer.parseInt(text_age.getText().toString()) > 110)
                        text_inputlayout_age.setError("*Please Enter a Valid Age");

                    else {
                        text_inputlayout_age.setError(null);
                        erroraage = 1;
                    }
                }
                else
                    text_inputlayout_age.setError("*Please Enter Age");



                if(!text_height.getText().toString().equals("")) {
                    if (text_height.getText() == null || heightdigitsOnly == false || Integer.parseInt(text_height.getText().toString()) < 50
                            || Integer.parseInt(text_height.getText().toString()) > 300)
                        text_inputlayout_height.setError("*Please Enter a Valid Height");

                    else {
                        text_inputlayout_height.setError(null);
                        errorheight = 1;
                    }
                }

                else
                    text_inputlayout_height.setError("*Please Enter  Height");


                if (!text_weight.getText().toString().equals("")) {

                    if (text_weight.getText() == null || weightdigitsOnly == false || Integer.parseInt(text_weight.getText().toString()) < 10
                            || Integer.parseInt(text_weight.getText().toString()) > 200)
                        text_inputlayout_weight.setError("*Please Enter a Valid Weight");

                    else {
                        text_inputlayout_weight.setError(null);
                        errorweight = 1;
                    }
                }

                else
                    text_inputlayout_weight.setError("*Please Enter  Weight");


                if(erroraage==1 && errorheight==1 && errorweight==1)
                {
//                    Intent planeintent = new Intent(this,Plane_temp2.class);
//                    startActivity(planeintent);
                     return true;
                }

                return false;


    }

}
