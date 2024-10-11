package com.example.zipperlock.ui.item.personalized;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityListItemBinding;
import com.example.zipperlock.ui.item.personalized.adapter.PersonalizedAdapter;

import java.util.ArrayList;
import java.util.List;

public class PersonalizedActivity extends BaseActivity<ActivityListItemBinding> {
    @Override
    public ActivityListItemBinding getBinding() {
        return ActivityListItemBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
//        binding.recycleView.setLayoutManager(new GridLayoutManager(this, 3)); // Hiển thị ảnh theo dạng lưới (3 cột)
//
//        List<String> imagePaths = getAllImages();
//        PersonalizedAdapter adapter = new PersonalizedAdapter(imagePaths, this);
//        binding.recycleView.setAdapter(adapter);

        binding.recycleView.setLayoutManager(new GridLayoutManager(this,4));

// Get all images
        List<Uri> imageList = getAllImages();

// Set the adapter
        PersonalizedAdapter adapter = new PersonalizedAdapter(this, imageList);
        binding.recycleView.setAdapter(adapter);
    }

    @Override
    public void bindView() {

    }
    private List<Uri> getAllImages() {
        List<Uri> imageList = new ArrayList<>();

        // Define the projection (columns to retrieve)
        String[] projection = new String[]{
                MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME
        };

        // Query the external storage for images
        Cursor cursor = getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,  // URI for external images
                projection,    // The columns to return
                null,          // Selection clause (optional)
                null,          // Selection arguments (optional)
                MediaStore.Images.Media.DATE_TAKEN + " DESC"  // Sort order
        );

        if (cursor != null) {
            int idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);

            // Loop through the cursor to add images to the list
            while (cursor.moveToNext()) {
                long id = cursor.getLong(idColumn);
                Uri contentUri = ContentUris.withAppendedId(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id);

                imageList.add(contentUri);
            }
            cursor.close();
        }

        return imageList;
    }
    @Override
    public void onBack() {

    }
}
