package com.example.zipperlock.ui.item.sound;

import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityListItemBinding;
import com.example.zipperlock.databinding.ItemSoundBinding;
import com.example.zipperlock.ui.apply.ApplyActivity;
import com.example.zipperlock.ui.item.row.adapter.RowAdapter;
import com.example.zipperlock.ui.item.row.model.Row;
import com.example.zipperlock.ui.item.sound.adapter.SoundAdapter;
import com.example.zipperlock.ui.item.sound.model.Sound;
import com.example.zipperlock.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class SoundActivity extends BaseActivity<ActivityListItemBinding> implements SoundAdapter.PlaySound {
    private List<Sound> zippers;
    private List<Sound> opens;
    private int pos;
    @Override
    public ActivityListItemBinding getBinding() {
        return ActivityListItemBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {

        zippers = new ArrayList<>();
        zippers.add(new Sound(R.drawable.img_sound_zipper, R.string.zipper_1, R.drawable.bg_sound_zip_1, R.drawable.bg_play_zip_1, R.raw.zip_1, R.string.zipper_sound));
        zippers.add(new Sound(R.drawable.img_sound_zipper, R.string.zipper_2, R.drawable.bg_sound_zip_2, R.drawable.bg_play_zip_2, R.raw.zip_2, R.string.zipper_sound));
        zippers.add(new Sound(R.drawable.img_sound_zipper, R.string.zipper_3, R.drawable.bg_sound_zip_3, R.drawable.bg_play_zip_3, R.raw.zip_3, R.string.zipper_sound));
        zippers.add(new Sound(R.drawable.img_sound_zipper, R.string.zipper_4, R.drawable.bg_sound_zip_4, R.drawable.bg_play_zip_4, R.raw.zip_4, R.string.zipper_sound));
        zippers.add(new Sound(R.drawable.img_sound_zipper, R.string.zipper_5, R.drawable.bg_sound_zip_5, R.drawable.bg_play_zip_5, R.raw.zip_5, R.string.zipper_sound));
        zippers.add(new Sound(R.drawable.img_sound_zipper, R.string.zipper_6, R.drawable.bg_sound_zip_6, R.drawable.bg_play_zip_6, R.raw.zip_6, R.string.zipper_sound));
        zippers.add(new Sound(R.drawable.img_sound_zipper, R.string.zipper_7, R.drawable.bg_sound_zip_7, R.drawable.bg_play_zip_7, R.raw.zip_7, R.string.zipper_sound));
        zippers.add(new Sound(R.drawable.img_sound_zipper, R.string.zipper_8, R.drawable.bg_sound_zip_8, R.drawable.bg_play_zip_8, R.raw.zip_8, R.string.zipper_sound));

        opens = new ArrayList<>();
        opens.add(new Sound(R.drawable.img_bell, R.string.bell, R.drawable.bg_open_sound, R.drawable.bg_play_open_sound, R.raw.bell, R.string.open_sound));
        opens.add(new Sound(R.drawable.img_broken, R.string.broken, R.drawable.bg_open_sound, R.drawable.bg_play_open_sound, R.raw.broken, R.string.open_sound));
        opens.add(new Sound(R.drawable.img_welcome, R.string.welcome, R.drawable.bg_open_sound, R.drawable.bg_play_open_sound, R.raw.welcome, R.string.open_sound));
        opens.add(new Sound(R.drawable.img_explosion, R.string.explosion, R.drawable.bg_open_sound, R.drawable.bg_play_open_sound, R.raw.explosion, R.string.open_sound));
        opens.add(new Sound(R.drawable.img_duck, R.string.duck, R.drawable.bg_open_sound, R.drawable.bg_play_open_sound, R.raw.duck, R.string.open_sound));
        opens.add(new Sound(R.drawable.img_laughing, R.string.laughing, R.drawable.bg_open_sound, R.drawable.bg_play_open_sound, R.raw.laughing, R.string.open_sound));
        opens.add(new Sound(R.drawable.img_gun, R.string.gun, R.drawable.bg_open_sound, R.drawable.bg_play_open_sound, R.raw.gun, R.string.open_sound));
        opens.add(new Sound(R.drawable.img_cat, R.string.cat, R.drawable.bg_open_sound, R.drawable.bg_play_open_sound, R.raw.cat, R.string.open_sound));

        Intent i = getIntent();
        pos = i.getIntExtra("position", -1);
        if (pos != -1) {
            if (pos == 0) {
                int currentSound = SPUtils.getInt(this, SPUtils.SOUND_ZIPPER, -1);
                SoundAdapter adapter = getAdapter(currentSound, zippers);
                binding.recycleView.setAdapter(adapter);
                binding.recycleView.setLayoutManager(new GridLayoutManager(this, 2));
            } else {
                int currentSound = SPUtils.getInt(this, SPUtils.SOUND_OPEN, -1);
                SoundAdapter adapter = getAdapter(currentSound, opens);
                binding.recycleView.setAdapter(adapter);
                binding.recycleView.setLayoutManager(new GridLayoutManager(this, 2));
            }
        } else {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void bindView() {
        binding.ivBack.setOnClickListener(v -> onBack());

    }

    private SoundAdapter getAdapter(int currentBackground, List<Sound> sounds) {
        int selectedPosition = 1;
        for (int i = 0; i < sounds.size(); i++) {
            if (sounds.get(i).getImg() == currentBackground) {
                selectedPosition = i;
                break;
            }
        }
        return new SoundAdapter(this, sounds, selectedPosition, (position, sound) -> {
            Intent i = new Intent(this, PlaySoundActivity.class);
            i.putExtra("type", pos);
            i.putExtra("sound", sound.getSound());
            i.putExtra("bg_color", sound.getColor_list());
            i.putExtra("img", sound.getImg());
            i.putExtra("name", sound.getName());
            startActivity(i);
        }, this);
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

    private MediaPlayer mediaPlayer;
    private boolean isPlay = false;
    @Override
    public void playSound(int soundResId, ItemSoundBinding binding) {
        isPlay = !isPlay;
        if (isPlay){
            binding.play.setImageResource(R.drawable.ic_pause_sound_in_list);
            mediaPlayer = MediaPlayer.create(this, soundResId);
            if (mediaPlayer != null) {
                mediaPlayer.start();
                mediaPlayer.setOnCompletionListener(mp -> {
                    mp.release();
                    mediaPlayer = null;
                    binding.play.setImageResource(R.drawable.ic_play_sound_in_list);
                });
            } else {
                Toast.makeText(this, "Unable to play sound", Toast.LENGTH_SHORT).show();
            }
        }else {
            binding.play.setImageResource(R.drawable.ic_play_sound_in_list);
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }
        }
    }
}
