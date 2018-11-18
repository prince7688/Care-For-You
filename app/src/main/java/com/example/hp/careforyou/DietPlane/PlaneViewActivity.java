package com.example.hp.careforyou.DietPlane;

import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Switch;

import com.example.hp.careforyou.R;

public class PlaneViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plane_view);

        loadfragment(new DietPlaneView());

        BottomNavigationView bottomnav = (BottomNavigationView)findViewById(R.id.bottom_navigation_view);

        bottomnav.setOnNavigationItemSelectedListener(navlistner);


    }

    private BottomNavigationView.OnNavigationItemSelectedListener navlistner =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    android.support.v4.app.Fragment selected_fragment =null;

                    switch(item.getItemId())
                    {
                        case R.id.plane_view:
                            selected_fragment = new DietPlaneView();
                            break;

                        case R.id.food_items:
                            selected_fragment =new Consumed_items();
                            break;
                    }

                    loadfragment(selected_fragment);

                    return true;
                }
            };


    private boolean loadfragment(android.support.v4.app.Fragment fragment)
    {
       if(fragment!=null)
       {
           getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();
           return true;
       }

       return false;

    }
}
