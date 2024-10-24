package com.example.zipperlock.ui.item.background.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zipperlock.databinding.ItemBackgroundBinding;
import com.example.zipperlock.databinding.ItemHomeBinding;
import com.example.zipperlock.ui.item.background.model.Background;
import com.example.zipperlock.ui.main.adapter.ItemAdapter;
import com.example.zipperlock.ui.main.model.ItemModel;
import com.example.zipperlock.util.SPUtils;

import java.util.List;

public class BackgroundAdapter extends RecyclerView.Adapter<BackgroundAdapter.ItemViewHolder> {
    private final List<Background> listItem;
    private final ClickItem clickItem;
    private final Context context;
    private int selectedPosition;

    public BackgroundAdapter(Context context, List<Background> backgroundList, int selectedPosition, ClickItem clickItem) {
        this.context = context;
        this.listItem = backgroundList;
        this.selectedPosition = selectedPosition; // Initialize it
        this.clickItem = clickItem;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBackgroundBinding binding = ItemBackgroundBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ItemViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Background background = listItem.get(position);
        Glide.with(context)
                .load(background.getImg())  
                .into(holder.binding.img);
        if (selectedPosition == position) {
            holder.binding.choose.setVisibility(View.VISIBLE);
        } else {
            holder.binding.choose.setVisibility(View.INVISIBLE);
        }
        holder.itemView.setOnClickListener(v -> {
            notifyDataSetChanged();
            clickItem.clickItem(position, background);
        });
    }

    @Override
    public int getItemCount() {
        if (listItem != null) {
            return listItem.size();
        } else {
            return 0;
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        ItemBackgroundBinding binding;

        public ItemViewHolder(ItemBackgroundBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ClickItem {
        void clickItem(int position, Background background);
    }
}
