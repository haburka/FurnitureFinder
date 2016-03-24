package com.robb.furniturefinder;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
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
        ImageButton closeButton;
        final Context context;

        ItemViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            itemName = (TextView)itemView.findViewById(R.id.name);
            itemDesc = (TextView)itemView.findViewById(R.id.description);
            itemPhoto = (ImageView)itemView.findViewById(R.id.photo);
            closeButton=(ImageButton)itemView.findViewById(R.id.closeButton);
            context = itemView.getContext();
        }
    }

    List<ManageItem> items;
    Context context;

    ManagingAdapter(List<ManageItem> items, Context context ){
        this.items = items;
        this.context = context;
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
    public void onBindViewHolder(final ItemViewHolder personViewHolder, final int i) {
        personViewHolder.itemName.setText(items.get(i).name);
        personViewHolder.itemDesc.setText(items.get(i).description);
        personViewHolder.itemPhoto.setImageBitmap(items.get(i).bm);

        personViewHolder.cv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ManageSingleItem.class);
                intent.putExtra("newTitle", items.get(i).name);
                intent.putExtra("newDesc", items.get(i).description);
                intent.putExtra("newImage", items.get(i).bm);
                intent.putExtra("pos", i);
                context.startActivity(intent);
            }
        });

        personViewHolder.closeButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                new AlertDialog.Builder(context)
                        .setTitle("Remove Item")
                        .setMessage("Are you sure you want to remove " + items.get(i).name + "?")
                        .setPositiveButton(android.R.string.no, null)
                        .setNegativeButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton)
                            {
                                items.remove(i);
                                notifyDataSetChanged();
                            }})
                        .show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
