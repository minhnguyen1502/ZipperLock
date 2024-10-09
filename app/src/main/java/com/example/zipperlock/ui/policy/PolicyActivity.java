package com.example.zipperlock.ui.policy;

import android.annotation.SuppressLint;
import android.view.View;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityPolicyBinding;

public class PolicyActivity extends BaseActivity<ActivityPolicyBinding> {

    String linkPolicy = "";

    @Override
    public ActivityPolicyBinding getBinding() {
        return ActivityPolicyBinding.inflate(getLayoutInflater());
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.loadUrl(linkPolicy);
    }

    @Override
    public void bindView() {
//        binding.viewTop.ivBack.setOnClickListener(v -> onBack());
    }

    @Override
    public void onBack() {
        finishThisActivity();
    }
}
