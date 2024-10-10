package com.example.zipperlock.ui.item.wallpaper;

import android.app.Dialog;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.Window;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivitySetWallpaperBinding;
import com.example.zipperlock.databinding.DialogSetWallpaperBinding;
import com.example.zipperlock.ui.successfully.SuccessfullyActivity;

import java.io.IOException;
import java.io.InputStream;

public class SetWallpaperActivity extends BaseActivity<ActivitySetWallpaperBinding> {
    private int wallpaper;
    @Override
    public ActivitySetWallpaperBinding getBinding() {
        return ActivitySetWallpaperBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        Intent i = getIntent();
         wallpaper = i.getIntExtra("wallpaper",-1);
        if (wallpaper != -1){
            binding.ivWallpaper1.setImageResource(wallpaper);
            binding.ivWallpaper2.setImageResource(wallpaper);
        }else {
            Toast.makeText(this, "not found", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void bindView() {
        binding.ivBack.setOnClickListener(v -> onBack());
        binding.btnSet.setOnClickListener(v -> openDialogSet());
    }

    private void openDialogSet() {
        Dialog dialog = new Dialog(this);
        DialogSetWallpaperBinding bindingDialog = DialogSetWallpaperBinding.inflate(getLayoutInflater());
        dialog.setContentView(bindingDialog.getRoot());
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        bindingDialog.home.setOnClickListener(v -> {
            setWallpaperHome(wallpaper);
            dialog.dismiss();

        });
        bindingDialog.lock.setOnClickListener(v -> {
            setWallpaperLockScreen(wallpaper);
            dialog.dismiss();

        });
        bindingDialog.both.setOnClickListener(v -> {
            setWallpaperLockScreen(wallpaper);
            setWallpaperHome(wallpaper);
            dialog.dismiss();

        });

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog.show();


    }

    private void setWallpaperHome(int wallpaperResId) {
        try {
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
            Bitmap bitmap = ((BitmapDrawable) getResources().getDrawable(wallpaperResId)).getBitmap();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(bitmap, null, true, WallpaperManager.FLAG_SYSTEM);
                startActivity(new Intent(this, SuccessfullyActivity.class));
            }
//            Toast.makeText(this, "Home screen wallpaper set successfully", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to set home screen wallpaper", Toast.LENGTH_SHORT).show();
        }
    }

    private void setWallpaperLockScreen(int wallpaperResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            try {
                WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
                InputStream inputStream = getResources().openRawResource(wallpaperResId);
                wallpaperManager.setStream(inputStream, null, true, WallpaperManager.FLAG_LOCK);
                startActivity(new Intent(this, SuccessfullyActivity.class));
//                Toast.makeText(this, "Lock screen wallpaper set successfully", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed to set lock screen wallpaper", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Lock screen wallpaper not supported on your device", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBack() {
        finish();
    }
}
