package com.robb.furniturefinder;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xinhai Xu on 2016/3/22.
 */
public class ManageTab extends Fragment
{
    private List<Item> items;
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

        return view;
    }

    private void initializeData()
    {
        items = new ArrayList<>();
        items.add(new Item("Chair", "Test", R.drawable.stock_chair));
        items.add(new Item("Sofa", "Test", R.drawable.stock_couch));
        items.add(new Item("Desk", "Test", R.drawable.stock_desk));
    }

    private void initializeAdapter()
    {
        ManagingAdapter adapter = new ManagingAdapter(items);
        rv.setAdapter(adapter);
    }

}