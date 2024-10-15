package com.example.zipperlock.ui.successfully;

import android.content.Intent;

import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivitySuccessBinding;
import com.example.zipperlock.ui.main.MainActivity;

public class SuccessfullyActivity extends BaseActivity<ActivitySuccessBinding> {
    @Override
    public ActivitySuccessBinding getBinding() {
        return ActivitySuccessBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {

    }

    @Override
    public void bindView() {
        binding.btnBack.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finishAffinity();
        });
    }

    @Override
    public void onBack() {

    }
}
