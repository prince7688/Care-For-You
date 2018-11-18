package com.example.hp.careforyou.Database;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bignerdranch.expandablerecyclerview.Adapter.ExpandableRecyclerAdapter;
import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.hp.careforyou.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


public class HistoryAdapter extends ExpandableRecyclerAdapter<ParentViewHolder,ChildViewHolder> {

    LayoutInflater inflater;
    private static final String DATE_FORMAT = "dd/MM/yyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());
    private String LOG_TAG = "prince";

    public HistoryAdapter(Context context, List<ParentObject> parentItemList) {
        super(context, parentItemList);
        inflater = LayoutInflater.from(context);
    }

    @Override
    public ParentViewHolder onCreateParentViewHolder(ViewGroup viewGroup) {
       View view = inflater.inflate(R.layout.parent_history,viewGroup,false);
        return new ParentViewHolder(view);
    }

    @Override
    public ChildViewHolder onCreateChildViewHolder(ViewGroup viewGroup) {
        View view = inflater.inflate(R.layout.child_history,viewGroup,false);
        return new ChildViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindParentViewHolder(final ParentViewHolder parentViewHolder, int i, Object o) {

        ParentView parentView = (ParentView)o;
        parentViewHolder.mBrandName.setText(parentView.getBrandName());
        parentViewHolder.mItemName.setText(parentView.getItemName());
        parentViewHolder.mFirstletter.setText((parentView.getFirstletter()).substring(0,1));


        String updatedAt = dateFormat.format(parentView.getDate());

        parentViewHolder.mDate.setText(updatedAt);

        GradientDrawable magnitudeCircle = (GradientDrawable) parentViewHolder.mFirstletter.getBackground();
        int magnitudeColor = getMagnitudeColor((parentView.getFirstletter()).substring(0,1));
        magnitudeCircle.setColor(magnitudeColor);


        parentViewHolder.maddbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(LOG_TAG,parentViewHolder.mItemName.getText().toString());
//                AlertDialog.Builder builder = new AlertDialog.Builder(g);
//
//                builder.setTitle("Confirm");
//                builder.setMessage("Are you sure?");
//
//                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//
//                    public void onClick(DialogInterface dialog, int which) {
//                        // Do nothing but close the dialog
//
//                        dialog.dismiss();
//                    }
//                });
//
//                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                        // Do nothing
//                        dialog.dismiss();
//                    }
//                });
//
//                AlertDialog alert = builder.create();
//                alert.show();
            }
        });

    }

    private int getMagnitudeColor(String substring) {

        int ColorResourceId;
        char letter = substring.charAt(0);
        int askii = (int)letter;
        if(askii >= 65 && askii <=75)
            ColorResourceId = R.color.letter1;

        else if(askii > 75 && askii <=85)
            ColorResourceId = R.color.letter3;

        else
            ColorResourceId = R.color.letter2;


            return ContextCompat.getColor(mContext,ColorResourceId);
    }

    @Override
    public void onBindChildViewHolder(ChildViewHolder childViewHolder, int i, Object o) {

        ChildView childView =(ChildView)o;
        DecimalFormat precision = new DecimalFormat("0.00");

        if(childView.getWater().matches("null"))
            childViewHolder.mWater.setText(0.00 + " gm");

        else
            childViewHolder.mWater.setText(childView.getWater()+ " gm");



        if(childView.getFat().matches("null"))
            childViewHolder.mFat.setText(0.00 + " gm");

        else
            childViewHolder.mFat.setText(precision.format(Double.valueOf(childView.getFat()))+ " gm");


        if(childView.getEnergy().matches("null"))
            childViewHolder.mEnergy.setText(0.00 + " cal");

        else
            childViewHolder.mEnergy.setText(precision.format(Double.valueOf(childView.getEnergy()))+ " cal");


        if(childView.getSalt().matches("null"))
            childViewHolder.mSalt.setText(0.00 + " gm");

        else
            childViewHolder.mSalt.setText(precision.format(Double.valueOf(childView.getSalt()))+ " gm");



        if(childView.getSugar().matches("null"))
            childViewHolder.mSugar.setText(0.00 + " gm");

        else
            childViewHolder.mSugar.setText(precision.format(Double.valueOf(childView.getSugar()))+ " gm");




//        childViewHolder.mWater.setText(childView.getWater());
//        childViewHolder.mEnergy.setText(childView.getEnergy());
//        childViewHolder.mFat.setText(childView.getFat());
//        childViewHolder.mSalt.setText(childView.getSalt());
//        childViewHolder.mSugar.setText(childView.getSugar());

    }

}
