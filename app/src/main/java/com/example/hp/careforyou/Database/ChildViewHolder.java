package com.example.hp.careforyou.Database;

import android.view.View;
import android.widget.TextView;

import com.example.hp.careforyou.R;

public class ChildViewHolder extends com.bignerdranch.expandablerecyclerview.ViewHolder.ChildViewHolder {

    TextView mWater, mEnergy, mFat, mSalt, mSugar;

    public ChildViewHolder(View itemView) {
        super(itemView);

        mWater =(TextView)itemView.findViewById(R.id.db_water);
        mEnergy =(TextView)itemView.findViewById(R.id.db_energy);
        mFat =(TextView)itemView.findViewById(R.id.db_fat);
        mSalt =(TextView)itemView.findViewById(R.id.db_salt);
        mSugar =(TextView)itemView.findViewById(R.id.db_sugar);


    }
}
