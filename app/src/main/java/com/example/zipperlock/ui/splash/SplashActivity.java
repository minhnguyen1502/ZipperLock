package com.example.zipperlock.ui.splash;

import android.os.Handler;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivitySplashBinding;
import com.example.zipperlock.ui.language.LanguageStartActivity;
import com.example.zipperlock.util.SPUtils;
import com.example.zipperlock.util.SharePrefUtils;
import com.example.zipperlock.util.Utils;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

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
