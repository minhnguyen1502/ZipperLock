package com.example.zipperlock.ui.apply;

import android.content.Intent;
import android.net.Uri;

import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityApplyBinding;

public class ApplyActivity extends BaseActivity<ActivityApplyBinding> {
    @Override
    public ActivityApplyBinding getBinding() {
        return ActivityApplyBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        String imageUriString = intent.getStringExtra("imageUri");

        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            binding.bg.setImageURI(imageUri);
        }
    }

    @Override
    public void bindView() {

    }

    @Override
    public void onBack() {

    }
}
