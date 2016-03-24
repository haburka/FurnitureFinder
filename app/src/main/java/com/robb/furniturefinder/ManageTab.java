package com.robb.furniturefinder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xinhai Xu on 2016/3/22.
 */
public class ManageTab extends Fragment
{
    private List<ManageItem> items;
    private RecyclerView rv;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.manage_layout, container, false);
        rv=(RecyclerView)view.findViewById(R.id.rv);


        Context context=container.getContext();
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        rv.setLayoutManager(layoutManager);

        initializeData();
        initializeAdapter();

        Intent intent = getActivity().getIntent();
        if(intent!=null && intent.getIntExtra("back", 2) == 0 ){
            int pos = intent.getIntExtra("pos", 0);
            items.get(pos).name = intent.getStringExtra("newTitle");
            items.get(pos).description = intent.getStringExtra("newDesc");
            items.get(pos).bm = intent.getParcelableExtra("newImage");
        }

        return view;
    }

    private void initializeData()
    {
        items = new ArrayList<>();

        items.add(new ManageItem("Chair", "Test", BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.stock_chair)));
        items.add(new ManageItem("Sofa", "Test", BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.stock_couch)));
        items.add(new ManageItem("Desk", "Test", BitmapFactory.decodeResource(getContext().getResources(),
                R.drawable.stock_desk)));
    }

    private void initializeAdapter()
    {
        ManagingAdapter adapter = new ManagingAdapter(items, getActivity());
        rv.setAdapter(adapter);
    }

}