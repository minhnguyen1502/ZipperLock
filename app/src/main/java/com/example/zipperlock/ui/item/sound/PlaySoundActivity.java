package com.example.zipperlock.ui.item.sound;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityPlaySoundBinding;
import com.example.zipperlock.ui.apply.ApplyActivity;
import com.example.zipperlock.util.SPUtils;

public class PlaySoundActivity extends BaseActivity<ActivityPlaySoundBinding>{
    private MediaPlayer mediaPlayer;
    VolumeObserver volumeObserver;
    private boolean isPlay = false;
private int sound;
private int type;
    @Override
    public ActivityPlaySoundBinding getBinding() {
        return ActivityPlaySoundBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        Intent i = getIntent();
        sound = i.getIntExtra("sound",-1);
        type = i.getIntExtra("type",-1);
        int img = i.getIntExtra("img",-1);
        int color = i.getIntExtra("bg_color",-1);
        int name = i.getIntExtra("name",-1);
        if (sound != -1) {
            mediaPlayer = MediaPlayer.create(this, sound);
            mediaPlayer.setOnCompletionListener(mp -> binding.btnPlayPause.setImageResource(R.drawable.ic_play_play_sound));
        } else {
            Toast.makeText(this, getString(R.string.sound_not_found), Toast.LENGTH_SHORT).show();
        }
        if (color != -1){
            binding.view.setBackgroundResource(color);
        } else {
            Toast.makeText(this, getString(R.string.color_not_found), Toast.LENGTH_SHORT).show();
        }

        if (img != -1){
            binding.img.setImageResource(img);
        }else {
            Toast.makeText(this, getString(R.string.image_not_found), Toast.LENGTH_SHORT).show();

        }


        if (name != -1){
            binding.title.setText(name);
        }else {
            Toast.makeText(this, getString(R.string.name_not_found), Toast.LENGTH_SHORT).show();

        }
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        binding.volumeSeekBar.setMax(maxVolume);
        binding.volumeSeekBar.setProgress(currVolume);
        binding.tvVolumn.setText(String.valueOf(currVolume));
        if (currVolume == 0) {
            binding.ivSound.setImageResource(R.drawable.ic_min_sound);
        } else {
            binding.ivSound.setImageResource(R.drawable.ic_max_sound);
        }
        volumeObserver = new VolumeObserver(new Handler(Looper.getMainLooper()));
        getContentResolver().registerContentObserver(Settings.System.CONTENT_URI, true, volumeObserver);
    }

    @Override
    public void bindView() {
        binding.btnSelect.setOnClickListener(v -> {
            Intent i = new Intent(this, ApplyActivity.class);
            if (type!=-1){
                if (type == 0){
                    i.putExtra("sound_zipper", sound);
                    i.putExtra("type", type);
                }else {
                    i.putExtra("sound_open", sound);
                    i.putExtra("type", type);

                }
                startActivity(i);
            }else {
                Toast.makeText(this, getString(R.string.type_not_found), Toast.LENGTH_SHORT).show();
            }
        });
        binding.btnPlayPause.setOnClickListener(v -> {
            isPlay = !isPlay;
            if (isPlay){
                playSound();
            }else {
                stopSound();
            }

        });
        binding.ivBack.setOnClickListener(v -> onBack());
        binding.volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, AudioManager.FLAG_SHOW_UI);
                    binding.tvVolumn.setText(String.valueOf(i));
                if (i == 0) {
                    binding.ivSound.setImageResource(R.drawable.ic_min_sound);
                } else {
                    binding.ivSound.setImageResource(R.drawable.ic_max_sound);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    @SuppressLint("SetTextI18n")
    private void playSound() {
        binding.btnPlayPause.setImageResource(R.drawable.ic_pause_play_sound);
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @SuppressLint("SetTextI18n")
    private void stopSound() {
        binding.btnPlayPause.setImageResource(R.drawable.ic_play_play_sound);
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }

    }
    int currVolume;
    private AudioManager audioManager;
    private class VolumeObserver extends ContentObserver {

        VolumeObserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            currVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            binding.volumeSeekBar.setProgress(currVolume);
            binding.tvVolumn.setText(String.valueOf(currVolume));

        }
    }
    private boolean wasPlaying = false;

    @Override
    protected void onPause() {
        super.onPause();
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                wasPlaying = true;
                mediaPlayer.pause();
            } else {
                wasPlaying = false;
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mediaPlayer != null && wasPlaying) {
            mediaPlayer.start();
        }

    }

    @Override
    public void onBack() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}