package com.example.zipperlock.ui.item.wallpaper.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zipperlock.databinding.ItemBackgroundBinding;
import com.example.zipperlock.databinding.ItemWallpaperBinding;
import com.example.zipperlock.ui.item.background.model.Background;
import com.example.zipperlock.ui.item.wallpaper.model.Wallpaper;

import java.util.List;

public class WallpaperAdapter extends RecyclerView.Adapter< WallpaperAdapter.ItemViewHolder> {
    private final List<Wallpaper> listItem;
    private final  ClickItem clickItem;
    private final Context context;

    public  WallpaperAdapter(Context context, List<Wallpaper> backgroundList, ClickItem clickItem) {
        this.context = context;
        this.listItem = backgroundList;
        this.clickItem = clickItem;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemWallpaperBinding binding = ItemWallpaperBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new  ItemViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull  ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Wallpaper wallpaper = listItem.get(position);
        holder.binding.img.setImageResource(wallpaper.getImg());

        holder.binding.img.setOnClickListener(v -> {
            clickItem.clickItem(position, wallpaper);
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
        ItemWallpaperBinding binding;

        public ItemViewHolder(ItemWallpaperBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ClickItem {
        void clickItem(int position, Wallpaper wallpaper);
    }
}
