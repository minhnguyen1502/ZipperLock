package com.example.zipperlock.ui.item.personalized;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

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
//                else {
//                    Toast.makeText(this, "Loading failed", Toast.LENGTH_SHORT).show();
//                }
                isLoading = false;
            });
        });
    }

//    private List<Uri> getImagesForPage(int page, int pageSize) {
//        List<Uri> images = new ArrayList<>();
//        String[] projection = new String[]{
//                MediaStore.Images.Media._ID,
//                MediaStore.Images.Media.DISPLAY_NAME
//        };
//
//        // Thêm điều kiện kiểm tra phiên bản Android 11 trở lên
//        String selection = null;
//        String[] selectionArgs = null;
//
//        // Điều chỉnh limit cho query
//        String limit = pageSize + " OFFSET " + (page * pageSize);
//
//        Uri collection;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
//            collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL);
//        } else {
//            collection = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//        }
//
//        try (Cursor cursor = getContentResolver().query(
//                collection,
//                projection,
//                selection,
//                selectionArgs,
//                MediaStore.Images.Media.DATE_TAKEN + " DESC LIMIT " + limit
//        )) {
//            if (cursor != null) {
//                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
//
//                while (cursor.moveToNext()) {
//                    long id = cursor.getLong(idColumn);
//                    Uri contentUri;
////                    if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.Q) {
//                        contentUri = ContentUris.withAppendedId(
//                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);
////                    } else {
////                        contentUri = ContentUris.withAppendedId(
////                                MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL), id);
////                    }
//
//                    Log.d("ImageLoader", "Loaded image URI: " + contentUri);
//                    images.add(contentUri);
//                }
//            }
//        } catch (Exception e) {
//            Log.e("dklfasdf", e.toString());
//            e.printStackTrace();
//        }
//
//        return images;
//    }

    private List<Uri> getImagesForPage(int page, int pageSize) {
        List<Uri> imageUris = new ArrayList<>();

        String[] projection = new String[]{
                MediaStore.Images.Media._ID
        };

        Uri imagesUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        try (Cursor cursor = getContentResolver().query(imagesUri, projection, null, null, MediaStore.Images.Media.DATE_ADDED + " DESC")) {
            if (cursor != null && cursor.moveToFirst()) {
                int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);

                int startIndex = page * pageSize;
                int endIndex = Math.min(startIndex + pageSize, cursor.getCount());

                if (cursor.moveToPosition(startIndex)) {
                    do {
                        long id = cursor.getLong(idColumn);
                        Uri contentUri = ContentUris.withAppendedId(imagesUri, id);
                        imageUris.add(contentUri);

                        if (imageUris.size() >= (endIndex - startIndex)) {
                            break;
                        }
                    } while (cursor.moveToNext());
                }
            }
        } catch (Exception e) {
            Toast.makeText(this, "Loading failed", Toast.LENGTH_SHORT).show();
        }

        return imageUris;
    }


    @Override
    public void onBack() {
        finish();
    }
}
