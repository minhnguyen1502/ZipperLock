package com.example.zipperlock.ui.item.personalized;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityListItemBinding;
import com.example.zipperlock.ui.item.personalized.adapter.PersonalizedAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class PersonalizedActivity extends BaseActivity<ActivityListItemBinding> {
    private static final int PAGE_SIZE = 40;
    private int currentPage = 0;
    private boolean isLoading = false;

    private PersonalizedAdapter adapter;
    private final List<Uri> imageList = new ArrayList<>();

    @Override
    public ActivityListItemBinding getBinding() {
        return ActivityListItemBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        binding.recycleView.setLayoutManager(new GridLayoutManager(this, 4));

        adapter = new PersonalizedAdapter(this, imageList);
        binding.recycleView.setAdapter(adapter);

        loadImages();

        binding.recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && !isLoading &&
                        layoutManager.findLastVisibleItemPosition() >= adapter.getItemCount() - 1) {
                    loadImages();
                }
            }
        });
    }

    @Override
    public void bindView() {
        binding.ivBack.setOnClickListener(v -> onBack());
    }

    private void loadImages() {
        isLoading = true;

        Executors.newSingleThreadExecutor().execute(() -> {
            List<Uri> newImages = getImagesForPage(currentPage, PAGE_SIZE);
            runOnUiThread(() -> {
                if (!newImages.isEmpty()) {
                    adapter.addImages(newImages);
                    currentPage++;
                }
                isLoading = false;
            });
        });
    }

    private List<Uri> getImagesForPage(int page, int pageSize) {
        List<Uri> images = new ArrayList<>();
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME
        };

        String limit = pageSize + " OFFSET " + (page * pageSize);

        try (Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                null,
                null,
                MediaStore.Images.Media.DATE_TAKEN + " DESC LIMIT " + limit
        )) {
            if (cursor != null) {
                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);

                while (cursor.moveToNext()) {
                    long id = cursor.getLong(idColumn);
                    Uri contentUri = ContentUris.withAppendedId(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

//                    Log.d("ImageLoader", "Loaded image URI: " + contentUri.toString());
                    images.add(contentUri);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return images;
    }

    @Override
    public void onBack() {
        finish();
    }
}
