package com.naman.actnetworkstest.adapters;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.Glide;
import com.naman.actnetworkstest.R;
import com.naman.actnetworkstest.models.CounttryDetailModel;

import java.util.List;

import static android.content.ContentValues.TAG;


public class AdapterCountry extends RecyclerView.Adapter<AdapterCountry.ViewHolder> {

    private final Context mContext;
    private ViewHolder prevHolder;

    private final List<CounttryDetailModel> counttryDetaillist;

    public AdapterCountry(Context mContext, List<CounttryDetailModel> counttryDetaillist) {
        this.mContext = mContext;
        this.counttryDetaillist = counttryDetaillist;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_country_select, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int i) {

        CounttryDetailModel countryDetail = counttryDetaillist.get(i);

        holder.countryName.setText(countryDetail.getName());
        Glide.with(mContext.getApplicationContext())
                .load(countryDetail.getImg())
                .thumbnail(0.5f)
                .into(holder.flag);

        SharedPreferences sp = mContext.getSharedPreferences("shared preferences", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        holder.ring_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {

                    editor.putString("Image", countryDetail.getImg());
                    editor.putString("Name", countryDetail.getName());
                    editor.apply();

                    holder.ring_circle.setVisibility(View.GONE);
                    holder.filled_circle.setVisibility(View.VISIBLE);
                    prevHolder.filled_circle.setVisibility(View.GONE);
                    prevHolder.ring_circle.setVisibility(View.VISIBLE);
                    prevHolder = holder;
                } catch (NullPointerException e) {
                    prevHolder = holder;
                }


            }
        });
        holder.filled_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Log.d(TAG, "onClick:2 " + prevHolder + "  " + holder);

                    holder.filled_circle.setVisibility(View.GONE);
                    holder.ring_circle.setVisibility(View.VISIBLE);
                    prevHolder.filled_circle.setVisibility(View.VISIBLE);
                    prevHolder.ring_circle.setVisibility(View.GONE);
                    prevHolder = holder;
                } catch (NullPointerException e) {
                    prevHolder = holder;
                }


            }
        });


    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public long getItemId(int position) {

        return counttryDetaillist.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return counttryDetaillist.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView countryName;
        private final ImageView flag;
        private final ImageView filled_circle;
        private final ImageView ring_circle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            countryName = itemView.findViewById(R.id.country);
            flag = itemView.findViewById(R.id.flag);
            filled_circle = itemView.findViewById(R.id.radio);
            ring_circle = itemView.findViewById(R.id.radio2);


        }
    }


}


