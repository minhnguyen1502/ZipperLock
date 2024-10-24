package com.example.zipperlock.ui.item.sound;

import android.content.Intent;
import android.media.MediaPlayer;
import android.util.Log;
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

public class SoundActivity extends BaseActivity<ActivityListItemBinding> {
    private List<Sound> zippers;
    private List<Sound> opens;
    private int pos;
    @Override
    public ActivityListItemBinding getBinding() {
        return ActivityListItemBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        binding.title.setText(R.string.sound_style);

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
        int title = i.getIntExtra("title", -1);
        if (pos != -1) {
            if (pos == 0) {
                int currentSound = SPUtils.getInt(this, SPUtils.SOUND_ZIPPER, -1);
                setupRecyclerView(currentSound, zippers);
                binding.title.setText(title);
            } else {
                int currentSound = SPUtils.getInt(this, SPUtils.SOUND_OPEN, -1);
                setupRecyclerView(currentSound, opens);
                binding.title.setText(title);
            }
        } else {
            Log.e("minh", "no data");

        }

        if (title == -1){
            Log.e("minh", "no title");
        }

    }

    @Override
    public void bindView() {
        binding.ivBack.setOnClickListener(v -> onBack());

    }
    private void setupRecyclerView(int currentSound, List<Sound> soundList) {
        SoundAdapter adapter = getAdapter(currentSound, soundList);
        binding.recycleView.setAdapter(adapter);
        binding.recycleView.setLayoutManager(new GridLayoutManager(this, 2));
    }
    private SoundAdapter getAdapter(int currentBackground, List<Sound> sounds) {
        int selectedPosition = -1;
        for (int i = 0; i < sounds.size(); i++) {
            if (sounds.get(i).getSound() == currentBackground) {
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
        });
    }


    @Override
    protected void onPause() {
        super.onPause();


    }

    @Override
    protected void onResume() {
        super.onResume();
        if (pos == 0) {
            int currentSound = SPUtils.getInt(this, SPUtils.SOUND_ZIPPER, -1);
            setupRecyclerView(currentSound, zippers);
        } else {
            int currentSound = SPUtils.getInt(this, SPUtils.SOUND_OPEN, -1);
            setupRecyclerView(currentSound, opens);
        }
    }

    @Override
    public void onBack() {

        finish();
    }
}
