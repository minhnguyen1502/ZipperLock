package com.example.zipperlock.ui.splash;

import android.os.Handler;

import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivitySplashBinding;
import com.example.zipperlock.ui.language.LanguageStartActivity;
import com.example.zipperlock.util.SharePrefUtils;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
// note: broadcast screen on off android

    @Override
    public ActivitySplashBinding getBinding() {
        return ActivitySplashBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        SharePrefUtils.increaseCountOpenApp(this);

        new Handler().postDelayed(() -> {
            startNextActivity(LanguageStartActivity.class, null);
            finishAffinity();
        }, 3000);

    }

    @Override
    public void bindView() {

    }

    @Override
    public void onBack() {

    }
}
