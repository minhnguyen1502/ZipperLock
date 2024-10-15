package com.example.zipperlock.ui.item.personalized.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.zipperlock.R;
import com.example.zipperlock.ui.apply.ApplyActivity;

import java.util.List;

public class PersonalizedAdapter extends RecyclerView.Adapter<PersonalizedAdapter.ImageViewHolder> {
    private List<Uri> imageList;
    private Context context;

    public PersonalizedAdapter(Context context, List<Uri> imageList) {
        this.context = context;
        this.imageList = imageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Uri imageUri = imageList.get(position);
        Glide.with(context)
                .load(imageUri)
                .into(holder.imageView);
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ApplyActivity.class);
            intent.putExtra("imageUri", imageUri.toString());
            context.startActivity(intent);
        });
    }
    public void addImages(List<Uri> newImages) {
        int startPosition = imageList.size();
        imageList.addAll(newImages);
        notifyItemRangeInserted(startPosition, newImages.size());
    }
    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public static class ImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
        }
    }
}
