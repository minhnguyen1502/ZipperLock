package com.example.zipperlock.ui.item.sound;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityListItemBinding;
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

    @Override
    public ActivityListItemBinding getBinding() {
        return ActivityListItemBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {

        zippers = new ArrayList<>();
        zippers.add(new Sound(R.drawable.img_sound_zipper, R.string.zipper_1, R.drawable.bg_sound_zip_1, R.color.color_zip_1,R.raw.zip_1, "Zipper Sound" ));
        zippers.add(new Sound(R.drawable.img_sound_zipper, R.string.zipper_2, R.drawable.bg_sound_zip_2, R.color.color_zip_2,R.raw.zip_2, "Zipper Sound" ));
        zippers.add(new Sound(R.drawable.img_sound_zipper, R.string.zipper_3, R.drawable.bg_sound_zip_3, R.color.color_zip_3,R.raw.zip_3, "Zipper Sound" ));
        zippers.add(new Sound(R.drawable.img_sound_zipper, R.string.zipper_4, R.drawable.bg_sound_zip_4, R.color.color_zip_4,R.raw.zip_4, "Zipper Sound" ));
        zippers.add(new Sound(R.drawable.img_sound_zipper, R.string.zipper_5, R.drawable.bg_sound_zip_5, R.color.color_zip_5,R.raw.zip_5, "Zipper Sound" ));
        zippers.add(new Sound(R.drawable.img_sound_zipper, R.string.zipper_6, R.drawable.bg_sound_zip_6, R.color.color_zip_6,R.raw.zip_6, "Zipper Sound" ));
        zippers.add(new Sound(R.drawable.img_sound_zipper, R.string.zipper_7, R.drawable.bg_sound_zip_7, R.color.color_zip_7,R.raw.zip_7, "Zipper Sound" ));
        zippers.add(new Sound(R.drawable.img_sound_zipper, R.string.zipper_8, R.drawable.bg_sound_zip_8, R.color.color_zip_8,R.raw.zip_8, "Zipper Sound" ));

        opens = new ArrayList<>();
        opens.add(new Sound(R.drawable.img_bell, R.string.bell, R.drawable.bg_open_sound, R.color.color_open_sound,R.raw.bell, "Open Sound" ));
        opens.add(new Sound(R.drawable.img_broken, R.string.broken, R.drawable.bg_open_sound, R.color.color_open_sound,R.raw.broken, "Open Sound" ));
        opens.add(new Sound(R.drawable.img_welcome, R.string.welcome, R.drawable.bg_open_sound, R.color.color_open_sound,R.raw.welcome, "Open Sound" ));
        opens.add(new Sound(R.drawable.img_explosion, R.string.explosion, R.drawable.bg_open_sound, R.color.color_open_sound,R.raw.explosion, "Open Sound" ));
        opens.add(new Sound(R.drawable.img_duck, R.string.duck, R.drawable.bg_open_sound, R.color.color_open_sound,R.raw.duck, "Open Sound" ));
        opens.add(new Sound(R.drawable.img_laughing, R.string.laughing, R.drawable.bg_open_sound, R.color.color_open_sound,R.raw.laughing, "Open Sound" ));
        opens.add(new Sound(R.drawable.img_gun, R.string.gun, R.drawable.bg_open_sound, R.color.color_open_sound,R.raw.gun, "Open Sound" ));
        opens.add(new Sound(R.drawable.img_cat, R.string.cat, R.drawable.bg_open_sound, R.color.color_open_sound,R.raw.cat, "Open Sound" ));
        int currentBackground = SPUtils.getInt(this, SPUtils.OPEN, -1);
        SoundAdapter adapter = getAdapter(currentBackground);
        binding.recycleView.setAdapter(adapter);
        binding.recycleView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    public void bindView() {

    }
    private SoundAdapter getAdapter(int currentBackground) {
        int selectedPosition = 3;
        for (int i = 0; i < zippers.size(); i++) {
            if (zippers.get(i).getImg() == currentBackground) {
                selectedPosition = i;
                break;
            }
        }
        return new SoundAdapter(this, zippers,selectedPosition,  (position, zipper) -> {
            SPUtils.setInt(this, SPUtils.OPEN, zipper.getImg());
        });
    }
    @Override
    public void onBack() {

    }

}
