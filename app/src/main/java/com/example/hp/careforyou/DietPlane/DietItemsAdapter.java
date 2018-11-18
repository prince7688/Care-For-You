package com.example.hp.careforyou.DietPlane;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.careforyou.Database.Extra;
import com.example.hp.careforyou.Database.Extra1;
import com.example.hp.careforyou.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import javax.xml.transform.Templates;

public class DietItemsAdapter extends RecyclerView.Adapter<DietItemsAdapter.ItemsViewHolder> {

    LayoutInflater inflater;
    private List<Extra1> itemslist;
    private static final String DATE_FORMAT = "dd/MM/yyy";
    private SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT, Locale.getDefault());

    public DietItemsAdapter(List<Extra1> extras) {

        itemslist = extras;
    }

    @NonNull
    @Override
    public ItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.consumed_itemlist;
        inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);

        ItemsViewHolder itemsViewHolder = new ItemsViewHolder(view);

        return itemsViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ItemsViewHolder holder, int position) {

        Extra1 extra = itemslist.get(position);
        holder.item_name.setText(extra.getItemName());
        holder.brand_name.setText(extra.getBrandName());
        holder.consumed_date.setText(dateFormat.format(extra.getDate()));
        holder.first_letter.setText(extra.getBrandName().substring(0,1));

//        GradientDrawable magnitudeCircle = (GradientDrawable) holder.first_letter.getBackground();
//        int magnitudeColor = getMagnitudeColor((extra.getBrandName()).substring(0,1));
//        magnitudeCircle.setColor(magnitudeColor);

    }

//    private int getMagnitudeColor(String substring) {
//
//        int ColorResourceId;
//        char letter = substring.charAt(0);
//        int askii = (int)letter;
//        if(askii >= 65 && askii <=75)
//            ColorResourceId = R.color.letter1;
//
//        else if(askii > 75 && askii <=85)
//            ColorResourceId = R.color.letter3;
//
//        else
//            ColorResourceId = R.color.letter2;
//
//
//        return ContextCompat.getColor(,ColorResourceId);
//    }


    @Override
    public int getItemCount() {
        return itemslist.size();
    }


    class ItemsViewHolder extends RecyclerView.ViewHolder {

        public TextView item_name,brand_name,first_letter,consumed_date;

    public ItemsViewHolder(View itemView) {
        super(itemView);

        item_name = (TextView)itemView.findViewById(R.id.db_itemname);
        brand_name =(TextView)itemView.findViewById(R.id.db_brandname);
        first_letter =(TextView)itemView.findViewById(R.id.firstletter);
        consumed_date =(TextView)itemView.findViewById(R.id.db_date);



    }



}

}
