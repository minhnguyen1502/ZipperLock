package com.example.zipperlock.ui.preview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityPreviewBinding;
import com.example.zipperlock.util.SPUtils;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class PreviewActivity extends BaseActivity<ActivityPreviewBinding> {

    private MediaPlayer sound_zip;
    private MediaPlayer sound_open;
    private boolean isSoundPlaying = false;
    private boolean isSoundOpenPlaying = false;
    private int zip_sound_t;
    private int open_sound_t;
    private int row_t;
    private int row_r_t;
    private int row_l_t;
    private int bg_t;
    private String bgUri_t;
    private int zip_t;

    @Override
    public ActivityPreviewBinding getBinding() {
        return ActivityPreviewBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {

        int zip_sound = SPUtils.getInt(this, SPUtils.SOUND_ZIPPER, -1);
        int open_sound = SPUtils.getInt(this, SPUtils.SOUND_OPEN, -1);
        int row = SPUtils.getInt(this, SPUtils.ROW, -1);
        int row_r = SPUtils.getInt(this, SPUtils.ROW_RIGHT, -1);
        int row_l = SPUtils.getInt(this, SPUtils.ROW_LEFT, -1);
        int bg = SPUtils.getInt(this, SPUtils.BG, -1);
//        String bgUri = SPUtils.getString(this, SPUtils.BG_PER, null);
        int zip = SPUtils.getInt(this, SPUtils.ZIPPER, -1);


        Intent i = getIntent();
        zip_sound_t = i.getIntExtra("sound_zipper", zip_sound);
        open_sound_t = i.getIntExtra("sound_open", open_sound);
        row_t = i.getIntExtra("row", row);
        row_l_t = i.getIntExtra("row_left", row_l);
        row_r_t = i.getIntExtra("row_right", row_r);
        bg_t = i.getIntExtra("background", bg);
        bgUri_t = i.getStringExtra("img_uri");
        zip_t = i.getIntExtra("zip", zip);

        if (row_t != -1) {
            binding.zip.setBitmapRow(row_t);
        }

        if (zip_t != -1) {
            binding.zip.setBitmapZipper(zip_t);
        }

        if (bgUri_t == null && bg_t == -1){
            String bgPer = SPUtils.getString(this, SPUtils.BG_PER, null);
            int bgInt = SPUtils.getInt(this, SPUtils.BG, -1);
            if (bgPer != null) {
                Uri imageUri = Uri.parse(bgPer);
                try {
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    binding.zip.setBitmapZipperBg(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            } else if (bgInt != -1) {
                binding.zip.setBitmapZipperBg(bgInt);
            }
        }

        if (bgUri_t != null) {
            Uri imageUri = Uri.parse(bgUri_t);
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                binding.zip.setBitmapZipperBg(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        } else if (bg_t != -1) {
            binding.zip.setBitmapZipperBg(bg_t);
        } else {
            Log.e("minh", "Background not found");
        }



        if (row_r_t != -1) {
            binding.zip.setBitmapZipperRight(row_r_t);
        }

        if (row_l_t != -1) {
            binding.zip.setBitmapZipperLeft(row_l_t);
        }

        if (zip_sound_t != -1) {
            sound_zip = MediaPlayer.create(this, zip_sound_t);
        }

        if (open_sound_t != -1) {
            sound_open = MediaPlayer.create(this, open_sound_t);
        }

    }

    @Override
    public void bindView() {

        binding.zip.setCompleteListener(new ZipperScreenLockView.IZipperListener() {
            @Override
            public void zipperSuccess() {
                if (sound_zip != null && sound_zip.isPlaying()) {
                    sound_zip.stop();
                    sound_zip.prepareAsync();
                    isSoundPlaying = false;
                }
                binding.zip.setVisibility(View.GONE);

                if (sound_open != null && !sound_open.isPlaying()) {
                    sound_open.start();
                    isSoundOpenPlaying = true;

                    sound_open.setOnCompletionListener(mp -> {
                        isSoundOpenPlaying = false;
                        sound_open.release();
                        sound_open = null;
                        finish();
                    });
                } else {
                    finish();
                }

            }

            @Override
            public void zipperMoving() {
                if (sound_zip != null && !sound_zip.isPlaying()) {
                    sound_zip.start();
                    sound_zip.setLooping(true);
                    isSoundPlaying = true;
                }
            }

            @Override
            public void zipperCancel() {
                if (sound_zip != null && sound_zip.isPlaying()) {
                    sound_zip.pause();
                    isSoundPlaying = false;
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (sound_zip != null && sound_zip.isPlaying()) {
            sound_zip.pause();
            isSoundPlaying = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sound_zip != null && isSoundPlaying) {
            sound_zip.start();
        }
    }

    @Override
    public void onBack() {
        if (sound_zip != null) {
            sound_zip.release();
            sound_zip = null;
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sound_zip != null) {
            sound_zip.release();
            sound_zip = null;
        }
    }
}