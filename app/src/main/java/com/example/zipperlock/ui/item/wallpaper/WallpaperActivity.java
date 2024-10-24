package com.example.zipperlock.ui.item.wallpaper;

import android.content.Intent;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityListItemBinding;
import com.example.zipperlock.ui.item.row.adapter.RowAdapter;
import com.example.zipperlock.ui.item.row.model.Row;
import com.example.zipperlock.ui.item.wallpaper.adapter.WallpaperAdapter;
import com.example.zipperlock.ui.item.wallpaper.model.Wallpaper;
import com.example.zipperlock.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class WallpaperActivity extends BaseActivity<ActivityListItemBinding> {
    private List<Wallpaper> listItems;

    @Override
    public ActivityListItemBinding getBinding() {
        return ActivityListItemBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        binding.title.setText(R.string.wallpaper);
        listItems = new ArrayList<>();
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_01));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_02));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_03));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_04));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_05));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_06));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_07));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_08));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_09));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_10));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_11));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_12));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_13));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_14));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_15));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_16));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_17));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_18));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_19));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_20));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_21));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_22));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_23));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_24));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_25));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_26));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_27));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_28));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_29));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_30));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_31));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_32));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_33));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_34));
        listItems.add(new Wallpaper( R.drawable.img_wallpaper_35));
        int currentBackground = SPUtils.getInt(this, SPUtils.WALLPAPER, -1);
        WallpaperAdapter adapter = getAdapter(currentBackground);
        binding.recycleView.setAdapter(adapter);
        binding.recycleView.setLayoutManager(new GridLayoutManager(this, 3));

    }

    @Override
    public void bindView() {
        binding.ivBack.setOnClickListener(v -> onBack());

    }
    private WallpaperAdapter getAdapter(int currentBackground) {

        return new WallpaperAdapter(this, listItems,  (position, wallpaper) -> {
            Intent i = new Intent(this, SetWallpaperActivity.class );
            i.putExtra("wallpaper", wallpaper.getImg());
            startActivity(i);
            SPUtils.setInt(this, SPUtils.WALLPAPER, wallpaper.getImg());
        });
    }

    @Override
    public void onBack() {
        finish();
    }
}
