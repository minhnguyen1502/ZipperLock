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

        if (!Utils.isFirstOpenApp()){
            SPUtils.setInt(this, SPUtils.BG, R.drawable.img_bg_01);
            SPUtils.setInt(this, SPUtils.ZIPPER, R.drawable.img_zipper_list_1);
            SPUtils.setInt(this, SPUtils.ROW, R.drawable.img_row_1);
            SPUtils.setInt(this, SPUtils.ROW_RIGHT, R.drawable.img_row_r_1);
            SPUtils.setInt(this, SPUtils.ROW_LEFT, R.drawable.img_row_l_1);
        }

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
