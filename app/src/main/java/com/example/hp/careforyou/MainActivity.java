package com.example.hp.careforyou;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.hp.careforyou.Database.NutritionDatabase;
import com.example.hp.careforyou.Database.NutritionItemsHistory;
import com.example.hp.careforyou.DietPlane.DietItemsPojo;
import com.example.hp.careforyou.DietPlane.DietPlaneView;
import com.example.hp.careforyou.DietPlane.PlaneViewActivity;
import com.example.hp.careforyou.DietPlane.Plane_temp;
import com.example.hp.careforyou.Location.LocationActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    GridLayout mGridView;
    TextView usertextview;
    CircleImageView circleImageView;
    Button logoutbtn;
    CardView mLocationCardView,mScanBrcodesCardView , mHistoryCardView, mDietPlaneView;
    public static final int RC_SIGN_IN = 1;
    private String mUsername;
    private Uri mUserImageUrl;
    public static final String ANONYMOUS = "anonymous";
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;

    private DrawerLayout drawerLayout;

    private static final String DATE_FORMAT = "dd/MM/yyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());


    private NutritionDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDb = NutritionDatabase.getInstance(getApplicationContext());


        mScanBrcodesCardView = (CardView)findViewById(R.id.scanfooditemscardview);
        mScanBrcodesCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ScanitemsIntent = new Intent(getApplicationContext(),ScanItemsActivity.class);
                startActivity(ScanitemsIntent);
            }
        });



        mHistoryCardView =(CardView)findViewById(R.id.historyview);
        mHistoryCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent historyIntent = new Intent(getApplicationContext(), NutritionItemsHistory.class);
                startActivity(historyIntent);
            }
        });



        mLocationCardView =(CardView)findViewById(R.id.locationcardview);
        mLocationCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent locationIntent = new Intent(getApplicationContext(),LocationActivity.class);
                startActivity(locationIntent);
            }
        });



        mDietPlaneView =(CardView)findViewById(R.id.dietplane);
        mDietPlaneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Date todaydate = new Date();
               // String acutalDate = dateFormat.format(todaydate);
                DietItemsPojo dietItemsPojo =  mDb.dietItemsDao().loadAllPlane();
                if(dietItemsPojo!=null) {
                    Intent dietplaneIntent = new Intent(getApplicationContext(), PlaneViewActivity.class);
                    startActivity(dietplaneIntent);
                }

                else
                {
                    Intent dietplaneSetIntent = new Intent(getApplicationContext(), Plane_temp.class);
                    startActivity(dietplaneSetIntent);
                }
            }
        });



        drawerLayout =(DrawerLayout)findViewById(R.id.drawer);
        NavigationView navigationView =(NavigationView)findViewById(R.id.navview);
        View headerview = navigationView.getHeaderView(0);
        usertextview = (TextView) headerview.findViewById(R.id.username);
        logoutbtn =(Button)headerview.findViewById(R.id.logoutbtn);
        circleImageView =(CircleImageView)headerview.findViewById(R.id.profile_image);

        mGridView =(GridLayout)findViewById(R.id.gridview);
        mFirebaseAuth = FirebaseAuth.getInstance();

        signmethod();

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthUI.getInstance().signOut(MainActivity.this);
                drawerLayout.closeDrawer(GravityCompat.START);

            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }
    }
    private void signmethod() {

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                // User is signed out
                if (user != null) {
                    // User is signed in
                    //onSignInInitialized(user.getDisplayName());
                    mUsername =user.getDisplayName();
                    mUserImageUrl =user.getPhotoUrl();
                    usertextview.setText(mUsername);
                    Glide.with(MainActivity.this).load(mUserImageUrl).into(circleImageView);
                    //circleImageView.setImageURI(user.getPhotoUrl());
                    //Toast.makeText(MainActivity.this, "You're now signed in. Welcome to CareForYou.", Toast.LENGTH_SHORT).show();
                } else {
                   // onSignOutCleanup();
                    mUsername = ANONYMOUS;
                    mUserImageUrl =null;
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setLogo(R.drawable.common_google_signin_btn_icon_dark)
                                    .setTheme(R.style.Theme_AppCompat_Light_NoActionBar)
                                    .setAvailableProviders(Arrays.asList(
                                            new AuthUI.IdpConfig.EmailBuilder().build(),
                                            new AuthUI.IdpConfig.PhoneBuilder().build(),
                                            new AuthUI.IdpConfig.GoogleBuilder().build()
                                    ))
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode == RESULT_OK)
                Toast.makeText(getApplicationContext(), "Sign in !", Toast.LENGTH_LONG).show();
            else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(), "Sign in canceled !", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

//    public void ScanBarcodeclickoncardview(View view) {
//
//        mScanBrcodesCardView = (CardView)findViewById(R.id.scanfooditemscardview);
//        mScanBrcodesCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent ScanitemsIntent = new Intent(getApplicationContext(),ScanItemsActivity.class);
//                startActivity(ScanitemsIntent);
//            }
//        });
//
//    }

//    public void historyViewclickOnCardView(View view)
//    {
//        mHistoryCardView =(CardView)findViewById(R.id.historyview);
//        mHistoryCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent historyIntent = new Intent(getApplicationContext(), NutritionItemsHistory.class);
//                startActivity(historyIntent);
//            }
//        });
//
//    }

}
