package com.example.zipperlock.ui.item.personalized;

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
        binding.recycleView.setLayoutManager(new GridLayoutManager(this, 3)); // Hiển thị ảnh theo dạng lưới (3 cột)

        List<String> imagePaths = getAllImages();
        PersonalizedAdapter adapter = new PersonalizedAdapter(imagePaths, this);
        binding.recycleView.setAdapter(adapter);
    }

    @Override
    public void bindView() {

    }
    private List<String> getAllImages() {
        List<String> imagePaths = new ArrayList<>();
        String[] projection = {MediaStore.Images.Media.DATA};
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String imagePath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
                imagePaths.add(imagePath);
            }
            cursor.close();
        }

        return imagePaths;
    }
    @Override
    public void onBack() {

    }
}
