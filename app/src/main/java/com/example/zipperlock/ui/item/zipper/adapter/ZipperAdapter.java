package com.example.zipperlock.ui.item.zipper.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zipperlock.databinding.ItemBackgroundBinding;
import com.example.zipperlock.databinding.ItemZipperBinding;
import com.example.zipperlock.ui.item.background.adapter.BackgroundAdapter;
import com.example.zipperlock.ui.item.background.model.Background;
import com.example.zipperlock.ui.item.zipper.model.Zipper;

import java.util.List;

public class ZipperAdapter extends RecyclerView.Adapter<ZipperAdapter.ItemViewHolder> {
    private final List<Zipper> listItem;
    private final ClickItem clickItem;
    private final Context context;
    private int selectedPosition;

    public ZipperAdapter(Context context, List<Zipper> backgroundList, int selectedPosition, ClickItem clickItem) {
        this.context = context;
        this.listItem = backgroundList;
        this.selectedPosition = selectedPosition; // Initialize it
        this.clickItem = clickItem;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemZipperBinding binding = ItemZipperBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ItemViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Zipper zipper = listItem.get(position);
        holder.binding.img.setImageResource(zipper.getImg());
        if (selectedPosition == position) {
            holder.binding.choose.setVisibility(View.VISIBLE);
        } else {
            holder.binding.choose.setVisibility(View.INVISIBLE);
        }
        holder.binding.img.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
            clickItem.clickItem(position, zipper);
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
        ItemZipperBinding binding;

        public ItemViewHolder(ItemZipperBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ClickItem {
        void clickItem(int position, Zipper zipper);
    }
}
