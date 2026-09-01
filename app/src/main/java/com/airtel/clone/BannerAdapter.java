package com.airtel.clone;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class BannerAdapter extends RecyclerView.Adapter<BannerAdapter.ViewHolder> {

    private final Context context;
    private final List<Integer> images;

    public BannerAdapter(Context context, List<Integer> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType
    ) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_banner, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position
    ) {

        int page = position % 3;

        if (page == 0) {

            // CARD 1
            holder.container.setBackgroundColor(Color.rgb(225, 0, 35));

            holder.small.setText("AIRTEL 5G PLUS");

            holder.title.setText("Unlimited 5G Data");

            holder.description.setText(
                    "Experience speed like never before"
            );

            holder.action.setText("EXPLORE");

        } else if (page == 1) {

            // CARD 2
            holder.container.setBackgroundColor(Color.rgb(30, 42, 65));

            holder.small.setText("AIRTEL REWARDS");

            holder.title.setText("Exciting Rewards");

            holder.description.setText(
                    "Earn points and enjoy amazing benefits"
            );

            holder.action.setText("VIEW REWARDS");

        } else {

            // CARD 3
            holder.container.setBackgroundColor(Color.rgb(15, 80, 150));

            holder.small.setText("QUICK RECHARGE");

            holder.title.setText("Easy Recharges");

            holder.description.setText(
                    "Recharge in seconds, anytime and anywhere"
            );

            holder.action.setText("RECHARGE");

        }
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        FrameLayout container;
        TextView small;
        TextView title;
        TextView description;
        TextView action;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.banner_container);
            small = itemView.findViewById(R.id.banner_small);
            title = itemView.findViewById(R.id.banner_title);
            description = itemView.findViewById(R.id.banner_description);
            action = itemView.findViewById(R.id.banner_action);
        }
    }
}
