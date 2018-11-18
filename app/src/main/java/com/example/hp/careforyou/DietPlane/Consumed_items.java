package com.example.hp.careforyou.DietPlane;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hp.careforyou.Database.Extra1;
import com.example.hp.careforyou.Database.NutritionDatabase;
import com.example.hp.careforyou.R;

import java.util.List;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class Consumed_items extends Fragment {

    private RecyclerView recyclerView;
    private DietItemsAdapter itemAdapter;
    private List<Extra1> extra1s;
    private NutritionDatabase mDb;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View  view = inflater.inflate(R.layout.consumed_item,container,false);

        mDb = NutritionDatabase.getInstance(getActivity().getApplicationContext());

        recyclerView =(RecyclerView)view.findViewById(R.id.recycler_consumeditem);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

        extra1s =  mDb.dietItemsList().loadAllItems();

        itemAdapter = new DietItemsAdapter(extra1s);

        recyclerView.setAdapter(itemAdapter);

        return view;
    }
}
