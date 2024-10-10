package com.example.zipperlock.ui.item.sound.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zipperlock.databinding.ItemRowBinding;
import com.example.zipperlock.databinding.ItemSoundBinding;
import com.example.zipperlock.databinding.ItemSoundTypeBinding;
import com.example.zipperlock.ui.item.row.adapter.RowAdapter;
import com.example.zipperlock.ui.item.row.model.Row;
import com.example.zipperlock.ui.item.sound.model.Sound;
import com.example.zipperlock.ui.item.sound.model.SoundType;

import java.util.List;

public class SoundAdapter extends RecyclerView.Adapter<SoundAdapter.ItemViewHolder> {
    private final List<Sound> listItem;
    private final ClickItem clickItem;
    private final Context context;
    private int selectedPosition;

    public SoundAdapter(Context context, List<Sound> backgroundList, int selectedPosition, ClickItem clickItem) {
        this.context = context;
        this.listItem = backgroundList;
        this.selectedPosition = selectedPosition; // Initialize it
        this.clickItem = clickItem;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSoundBinding binding = ItemSoundBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ItemViewHolder(binding);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Sound sound = listItem.get(position);

        Glide.with(holder.binding.img.getContext()).load(sound.getImg()).into(holder.binding.img);
        holder.binding.bg.setBackgroundResource(sound.getColor_item());
        holder.binding.name.setText(sound.getName());
        if (selectedPosition == position) {
            holder.binding.choose.setVisibility(View.VISIBLE);
        } else {
            holder.binding.choose.setVisibility(View.INVISIBLE);
        }
        holder.binding.img.setOnClickListener(v -> {
            selectedPosition = position;
            notifyDataSetChanged();
            clickItem.clickItem(position, sound);
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
        ItemSoundBinding binding;

        public ItemViewHolder(ItemSoundBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface ClickItem {
        void clickItem(int position, Sound sound);
    }
}

