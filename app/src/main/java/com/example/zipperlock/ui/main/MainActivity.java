package com.example.zipperlock.ui.main;

import android.view.View;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityMainBinding;
import com.example.zipperlock.ui.main.adapter.ItemAdapter;
import com.example.zipperlock.ui.main.model.ItemModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding> {
    private List<ItemModel> listItems;
    private ItemAdapter adapter;

    @Override
    public ActivityMainBinding getBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        binding.recycleView.setLayoutManager(new GridLayoutManager(this, 2));

        listItems = new ArrayList<>();
        listItems.add(new ItemModel(R.string.background, R.drawable.img_background));
        listItems.add(new ItemModel(R.string.zipper_style, R.drawable.img_zipper_style));
        listItems.add(new ItemModel(R.string.row_style, R.drawable.img_row_style));
        listItems.add(new ItemModel(R.string.sound_style, R.drawable.img_sound_style));
        listItems.add(new ItemModel(R.string.personalized, R.drawable.img_personalized));
        listItems.add(new ItemModel(R.string.wallpaper, R.drawable.img_wallpaper));

        adapter = new ItemAdapter(listItems, position -> {
            switch (position){
                case 0:
                    Toast.makeText(this, "1", Toast.LENGTH_SHORT).show();
                    break;
                case 1:
                    Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();

                    break;
                case 2:
                    Toast.makeText(this, "3", Toast.LENGTH_SHORT).show();

                    break;
                case 3:
                    Toast.makeText(this, "4", Toast.LENGTH_SHORT).show();

                    break;
                case 4:
                    Toast.makeText(this, "5", Toast.LENGTH_SHORT).show();

                    break;
                case 5:
                    Toast.makeText(this, "6", Toast.LENGTH_SHORT).show();

                    break;
            }
        },this);

        binding.recycleView.setAdapter(adapter);
    }

    @Override
    public void bindView() {

    }

    @Override
    public void onBack() {
        finishThisActivity();
    }

}
