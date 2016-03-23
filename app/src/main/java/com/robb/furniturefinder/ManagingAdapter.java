package com.robb.furniturefinder;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ManagingAdapter extends RecyclerView.Adapter<ManagingAdapter.ItemViewHolder> {

    public static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        CardView cv;
        TextView itemName;
        TextView itemDesc;
        ImageView itemPhoto;
        final Context context;

        ItemViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            itemName = (TextView)itemView.findViewById(R.id.name);
            itemDesc = (TextView)itemView.findViewById(R.id.description);
            itemPhoto = (ImageView)itemView.findViewById(R.id.photo);
            context = itemView.getContext();
            cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ManageSingleItem.class);
                    intent.putExtra("Name", itemName.getText());
                    context.startActivity(intent);
                }
            });
        }
    }

    List<Item> items;

    ManagingAdapter(List<Item> items){
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
