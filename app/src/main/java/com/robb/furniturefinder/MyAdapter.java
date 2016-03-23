package com.robb.furniturefinder;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemViewHolder> {

    public static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        CardView cv;
        TextView itemName;
        TextView itemDesc;
        ImageView itemPhoto;

        ItemViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            itemName = (TextView)itemView.findViewById(R.id.name);
            itemDesc = (TextView)itemView.findViewById(R.id.description);
            itemPhoto = (ImageView)itemView.findViewById(R.id.photo);
        }
    }

    List<Item> items;

    MyAdapter(List<Item> items){
        this.items = items;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        ItemViewHolder ivh = new ItemViewHolder(v);
        return ivh;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder personViewHolder, int i) {
        personViewHolder.itemName.setText(items.get(i).name);
        personViewHolder.itemDesc.setText(items.get(i).description);
        personViewHolder.itemPhoto.setImageResource(items.get(i).photoId);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
