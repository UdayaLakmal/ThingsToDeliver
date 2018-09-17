package com.udayalakmal.lmdeliveries.view;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.udayalakmal.lmdeliveries.R;
import com.udayalakmal.lmdeliveries.model.Deliveries;

import java.util.List;

/**
 * Created by Lakmal on 9/16/18.
 */

public abstract class DeliveryDataAdapter extends RecyclerView.Adapter<DeliveryDataAdapter.ViewHolder> {

        private List<Deliveries> mData;
        private LayoutInflater mInflater;
        private Context context;
        private ItemClickListener mClickListener;

        // data is passed into the constructor
        public DeliveryDataAdapter(Context context, List<Deliveries> data) {
            this.context = context;
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
        }

        public abstract void load();
        // inflates the row layout from xml when needed
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.item_layout, parent, false);
            return new ViewHolder(view);
        }

        // binds the data to the TextView in each row
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            final Deliveries deliveries = mData.get(position);
            holder.description.setText(deliveries.getDescription());
            holder.location.setText("Address :"+deliveries.getLocation().getAddress());

            try {
                Glide.with(context).load(deliveries.getImageUrl()).into(holder.imageView);
            } catch (Exception e) {
                e.printStackTrace();
            }

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent i = new Intent(context, DeliveryDetails.class);
                    i.putExtra("data", deliveries);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    context.startActivity(i);

                }
            });

            if ((position >= getItemCount() - 1))
                load();
        }

        // total number of rows
        @Override
        public int getItemCount() {
            return mData.size();
        }


        // stores and recycles views as they are scrolled off screen
        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            TextView description;
            ImageView imageView;
            TextView location;
            CardView cardView;

            ViewHolder(View itemView) {
                super(itemView);
                description = itemView.findViewById(R.id.description);
                imageView = itemView.findViewById(R.id.imageView);
                location = itemView.findViewById(R.id.location_text);
                cardView = itemView.findViewById(R.id.card_view);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            }
        }

        // convenience method for getting data at click position
        Deliveries getItem(int id) {
            return mData.get(id);
        }

        // allows clicks events to be caught
        void setClickListener(ItemClickListener itemClickListener) {
            this.mClickListener = itemClickListener;
        }

        // parent activity will implement this method to respond to click events
        public interface ItemClickListener {
            void onItemClick(View view, int position);
        }
    }

