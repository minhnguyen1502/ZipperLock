package com.example.zipperlock.ui.item.sound.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zipperlock.databinding.ItemRowBinding;
import com.example.zipperlock.databinding.ItemSoundTypeBinding;
import com.example.zipperlock.ui.item.row.adapter.RowAdapter;
import com.example.zipperlock.ui.item.row.model.Row;
import com.example.zipperlock.ui.item.sound.model.SoundType;

import java.util.List;

public class SoundTypeAdapter extends RecyclerView.Adapter<SoundTypeAdapter.ItemViewHolder> {
private final List<SoundType> listItem;
private final ClickItem clickItem;
private final Context context;

public SoundTypeAdapter(Context context, List<SoundType> backgroundList, ClickItem clickItem) {
    this.context = context;
    this.listItem = backgroundList;
    this.clickItem = clickItem;
}

@NonNull
@Override
public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    ItemSoundTypeBinding binding = ItemSoundTypeBinding.inflate(LayoutInflater.from(parent.getContext()));
    return new ItemViewHolder(binding);
}

@SuppressLint("NotifyDataSetChanged")
@Override
public void onBindViewHolder(@NonNull ItemViewHolder holder, @SuppressLint("RecyclerView") int position) {
    SoundType soundType = listItem.get(position);
    holder.binding.img.setImageResource(soundType.getImg());
    holder.binding.content.setText(soundType.getContent());
    holder.binding.title.setText(soundType.getTitle());
    holder.binding.color.setBackgroundResource(soundType.getColor());
    holder.itemView.setOnClickListener(v -> {
        clickItem.clickItem(position, soundType);
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
    ItemSoundTypeBinding binding;

    public ItemViewHolder(ItemSoundTypeBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }
}

public interface ClickItem {
    void clickItem(int position, SoundType soundType);
}
}

