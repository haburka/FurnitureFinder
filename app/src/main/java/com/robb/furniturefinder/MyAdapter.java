package com.robb.furniturefinder;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ItemViewHolder>
{
    private Context context;

    public static class ItemViewHolder extends RecyclerView.ViewHolder
    {
        CardView cv;
        TextView itemName;
        TextView itemDesc;
        ImageView itemPhoto;
        ImageButton closeButton;

        ItemViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            itemName = (TextView)itemView.findViewById(R.id.name);
            itemDesc = (TextView)itemView.findViewById(R.id.description);
            itemPhoto = (ImageView)itemView.findViewById(R.id.photo);
            closeButton=(ImageButton)itemView.findViewById(R.id.closeButton);
        }
    }

    List<Item> items;

    MyAdapter(List<Item> items)
    {
        this.items = items;
    }

    MyAdapter(Context context,List<Item> items)
    {
        this.items = items;
        this.context=context;
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
    public void onBindViewHolder(ItemViewHolder personViewHolder, final int i)
    {
        personViewHolder.itemName.setText(items.get(i).name);
        personViewHolder.itemDesc.setText(items.get(i).description);
        personViewHolder.itemPhoto.setImageResource(items.get(i).photoId);

        //Sets the removal listener
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
