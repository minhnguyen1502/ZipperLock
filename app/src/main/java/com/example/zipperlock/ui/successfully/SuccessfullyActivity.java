package com.example.zipperlock.ui.successfully;

import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivitySuccessBinding;

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
        binding.btnBack.setOnClickListener(v -> onBack());
    }

    @Override
    public void onBack() {
        finish();
    }
}
