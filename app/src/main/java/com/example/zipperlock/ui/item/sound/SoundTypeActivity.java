package com.example.zipperlock.ui.item.sound;

import android.content.Intent;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivitySoundTypeBinding;
import com.example.zipperlock.ui.item.sound.adapter.SoundTypeAdapter;
import com.example.zipperlock.ui.item.sound.model.SoundType;

import java.util.ArrayList;
import java.util.List;

public class SoundTypeActivity extends BaseActivity<ActivitySoundTypeBinding> {
    private List<SoundType> listItems;
    @Override
    public ActivitySoundTypeBinding getBinding() {
        return ActivitySoundTypeBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {

        listItems = new ArrayList<>();
        listItems.add(new SoundType(R.string.zipper_sound, R.string.set_zipper_sound,R.drawable.bg_soundtype_1, R.drawable.img_zip));
        listItems.add(new SoundType(R.string.open_sound, R.string.set_sound_when_open,R.drawable.bg_soundtype_2, R.drawable.img_open));
        SoundTypeAdapter adapter = new SoundTypeAdapter(this, listItems,  (position, soundType) -> {
            startActivity(new Intent(this, SoundActivity.class));
        });
        binding.recycleView.setAdapter(adapter);
        binding.recycleView.setLayoutManager(new GridLayoutManager(this,1));
    }

    @Override
    public void bindView() {

    }

    @Override
    public void onBack() {

    }
}
