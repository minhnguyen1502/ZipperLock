package com.example.zipperlock.ui.setting;

import android.content.Intent;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityAboutBinding;
import com.example.zipperlock.ui.policy.PolicyActivity;

public class AboutActivity extends BaseActivity<ActivityAboutBinding> {
    @Override
    public ActivityAboutBinding getBinding() {
        return ActivityAboutBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
//        binding.txt.setText(getString(R.string.version) + " " + BuildConfig.VERSION_NAME);

        binding.ivBack.setOnClickListener(v -> onBack());

        binding.tvPolicy.setOnClickListener(v -> startActivity(new Intent(AboutActivity.this, PolicyActivity.class)));
    }

    @Override
    public void bindView() {

    }

    @Override
    public void onBack() {
        finish();
    }
}
