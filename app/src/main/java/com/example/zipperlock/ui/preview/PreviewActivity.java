package com.example.zipperlock.ui.preview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

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
    @Override
    public ActivityPreviewBinding getBinding() {
        return ActivityPreviewBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        int zip_sound = SPUtils.getInt(this, SPUtils.SOUND_ZIPPER, -1);
        int open_sound = SPUtils.getInt(this, SPUtils.SOUND_ZIPPER, -1);
        int row = SPUtils.getInt(this, SPUtils.ROW, -1);
        int row_r = SPUtils.getInt(this, SPUtils.ROW_RIGHT, -1);
        int row_l = SPUtils.getInt(this, SPUtils.ROW_LEFT, -1);
        int bg = SPUtils.getInt(this, SPUtils.BG, -1);
        String bgUri = SPUtils.getString(this, SPUtils.BG_PER, null);
        int zip = SPUtils.getInt(this, SPUtils.ZIPPER, -1);

        if (row != -1) {
            binding.zip.setBitmapRow(row);
        }

        if (zip != -1) {
            binding.zip.setBitmapZipper(zip);
        }

        if (bg != -1) {
            binding.zip.setBitmapZipperBg(bg);
        } else if (bgUri != null) {
            Uri imageUri = Uri.parse(bgUri);
            try {
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                binding.zip.setBitmapZipperBg(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }


        if (row_r != -1) {
            binding.zip.setBitmapZipperRight(row_r);
        }

        if (row_l != -1) {
            binding.zip.setBitmapZipperLeft(row_l);
        }

        if (zip_sound != -1) {
            sound_zip = MediaPlayer.create(this, zip_sound);
        }

        if (open_sound != -1) {
            sound_open = MediaPlayer.create(this, open_sound);
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
                if (sound_open != null && !sound_open.isPlaying()) {
                    sound_open.start();
                    sound_open.setLooping(true);
                    isSoundOpenPlaying = true;
                }
                finish();
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
        if (sound_open != null && sound_open.isPlaying()) {
            sound_open.pause();
            isSoundOpenPlaying = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (sound_zip != null && isSoundPlaying) {
            sound_zip.start();
        }
        if (sound_open != null && isSoundOpenPlaying) {
            sound_open.start();
        }
    }
    @Override
    public void onBack() {
        if (sound_zip != null) {
            sound_zip.release();
            sound_zip = null;
        }
        if (sound_open != null) {
            sound_open.release();
            sound_open = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sound_zip != null) {
            sound_zip.release();
            sound_zip = null;
        }
        if (sound_open != null) {
            sound_open.release();
            sound_open = null;
        }
    }
}