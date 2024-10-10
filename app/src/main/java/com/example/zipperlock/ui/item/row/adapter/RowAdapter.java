package com.example.zipperlock.ui.item.row.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zipperlock.databinding.ItemRowBinding;
import com.example.zipperlock.databinding.ItemZipperBinding;
import com.example.zipperlock.ui.item.row.model.Row;
import com.example.zipperlock.ui.item.zipper.adapter.ZipperAdapter;
import com.example.zipperlock.ui.item.zipper.model.Zipper;

import java.util.List;

public class RowAdapter extends RecyclerView.Adapter<RowAdapter.ItemViewHolder> {
    private final List<Row> listItem;
    private final ClickItem clickItem;
    private final Context context;
    private int selectedPosition;

    public RowAdapter(Context context, List<Row> backgroundList, int selectedPosition, ClickItem clickItem) {
        this.context = context;
        this.listItem = backgroundList;
        this.selectedPosition = selectedPosition; // Initialize it
        this.clickItem = clickItem;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemRowBinding binding = ItemRowBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new  ItemViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull  ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Row row = listItem.get(position);
        holder.binding.img.setImageResource(row.getImg());
        if (selectedPosition == position) {
            holder.binding.choose.setVisibility(View.VISIBLE);
        } else {
            holder.binding.choose.setVisibility(View.INVISIBLE);
        }
        holder.binding.img.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
            clickItem.clickItem(position, row);
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
        ItemRowBinding binding;

        public ItemViewHolder(ItemRowBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ClickItem {
        void clickItem(int position, Row row);
    }
}

