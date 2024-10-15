package com.example.zipperlock.ui.intro;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import androidx.viewpager.widget.ViewPager;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityIntroBinding;
import com.example.zipperlock.ui.main.MainActivity;
import com.example.zipperlock.ui.permission.PermissionActivity;
import com.example.zipperlock.util.SharePrefUtils;
import com.example.zipperlock.util.Utils;

public class IntroActivity extends BaseActivity<ActivityIntroBinding> {
    ImageView[] dots = null;
    IntroAdapter introAdapter;
    String[] content;
    ViewPager viewPager;

    @Override
    public ActivityIntroBinding getBinding() {
        return ActivityIntroBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {

        viewPager = binding.viewPager2;

        content = new String[]{"Zipper Lock Screen", "Various Zipper styles","Realistic Zipper opening sound" };
        dots = new ImageView[]{binding.ivCircle01, binding.ivCircle02, binding.ivCircle03};
        introAdapter = new IntroAdapter(this);
        viewPager.setAdapter(introAdapter);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                changeContentInit(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private final int position = 0;
    @Override
    public void bindView() {
        binding.btnNext.setOnClickListener(view -> {
            if (viewPager.getCurrentItem() == 0) {
//                EventTracking.logEvent(IntroActivity.this, "Intro1_next_click");
            } else if (viewPager.getCurrentItem() == 1) {
//                EventTracking.logEvent(IntroActivity.this, "Intro2_next_click");
            }
            viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
        });

        binding.btnStart.setOnClickListener(view -> {
//            EventTracking.logEvent(IntroActivity.this, "Intro3_next_click");

            goToNextScreen();
        });
    }

    @Override
    public void onBack() {
        finishAffinity();
    }

    private void changeContentInit(int position) {
        binding.tvContent.setText(content[position]);

        for (int i = 0; i < 3; i++) {
            if (i == position) dots[i].setImageResource(R.drawable.ic_intro_s);
            else dots[i].setImageResource(R.drawable.ic_intro_sn);
        }

        switch (position) {
            case 0:
//                EventTracking.logEvent(this, "Intro1_view");

            case 1:
//                EventTracking.logEvent(this, "Intro2_view");

                binding.btnNext.setVisibility(View.VISIBLE);
                binding.btnStart.setVisibility(View.GONE);
                break;
            case 2:
//                EventTracking.logEvent(this, "Intro3_view");

                binding.btnNext.setVisibility(View.GONE);
                binding.btnStart.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void goToNextScreen() {
        if(!Utils.isFirstOpenApp()){
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Intent intent = new Intent(IntroActivity.this, PermissionActivity.class);
            startActivity(intent);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        changeContentInit(viewPager.getCurrentItem());
    }
}