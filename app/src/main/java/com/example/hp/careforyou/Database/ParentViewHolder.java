package com.example.hp.careforyou.Database;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.hp.careforyou.R;

import java.nio.Buffer;

public class ParentViewHolder extends com.bignerdranch.expandablerecyclerview.ViewHolder.ParentViewHolder {

    public TextView mBrandName;

    public TextView mItemName;

    public TextView mFirstletter;

    public TextView mDate;

    public Button maddbutton;

    public ParentViewHolder(View itemView) {
        super(itemView);

        mBrandName =(TextView)itemView.findViewById(R.id.db_brandname);
        mItemName =(TextView)itemView.findViewById(R.id.db_itemname);

        mFirstletter =(TextView)itemView.findViewById(R.id.firstletter);

        mDate =(TextView)itemView.findViewById(R.id.db_date);


        maddbutton =(Button)itemView.findViewById(R.id.addbtn);




    }
}
