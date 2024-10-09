package com.example.zipperlock.ui.permission;

import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityPermissionBinding;
import com.example.zipperlock.ui.main.MainActivity;

public class PermissionActivity extends BaseActivity<ActivityPermissionBinding> {


    @Override
    public ActivityPermissionBinding getBinding() {
        return ActivityPermissionBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {

    }

    @Override
    public void bindView() {
        binding.tvContinue.setOnClickListener(v -> {
            startNextActivity(MainActivity.class, null);
            finishAffinity();
        });
    }

    @Override
    public void onBack() {
        finishAffinity();
    }
}
