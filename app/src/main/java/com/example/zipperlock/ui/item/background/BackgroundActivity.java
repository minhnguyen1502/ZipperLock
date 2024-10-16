package com.example.zipperlock.ui.item.background;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityListItemBinding;
import com.example.zipperlock.ui.apply.ApplyActivity;
import com.example.zipperlock.ui.item.background.adapter.BackgroundAdapter;
import com.example.zipperlock.ui.item.background.model.Background;
import com.example.zipperlock.ui.main.adapter.ItemAdapter;
import com.example.zipperlock.ui.main.model.ItemModel;
import com.example.zipperlock.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class BackgroundActivity extends BaseActivity<ActivityListItemBinding> {
    int currentBackground;
    private List<Background> listItems;
    @Override
    public ActivityListItemBinding getBinding() {
        return ActivityListItemBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        currentBackground = SPUtils.getInt(this, SPUtils.BG, -1);

        listItems = new ArrayList<>();
        listItems.add(new Background( R.drawable.img_bg_01));
        listItems.add(new Background( R.drawable.img_bg_02));
        listItems.add(new Background( R.drawable.img_bg_03));
        listItems.add(new Background( R.drawable.img_bg_04));
        listItems.add(new Background( R.drawable.img_bg_05));
        listItems.add(new Background( R.drawable.img_bg_06));
        listItems.add(new Background( R.drawable.img_bg_07));
        listItems.add(new Background( R.drawable.img_bg_08));
        listItems.add(new Background( R.drawable.img_bg_09));
        listItems.add(new Background( R.drawable.img_bg_10));
        listItems.add(new Background( R.drawable.img_bg_11));
        listItems.add(new Background( R.drawable.img_bg_12));
        listItems.add(new Background( R.drawable.img_bg_13));
        listItems.add(new Background( R.drawable.img_bg_14));
        listItems.add(new Background( R.drawable.img_bg_15));
        listItems.add(new Background( R.drawable.img_bg_16));
        listItems.add(new Background( R.drawable.img_bg_17));
        listItems.add(new Background( R.drawable.img_bg_18));
        listItems.add(new Background( R.drawable.img_bg_19));
        listItems.add(new Background( R.drawable.img_bg_20));
        BackgroundAdapter adapter = getBackgroundAdapter();
        binding.recycleView.setAdapter(adapter);
        binding.recycleView.setLayoutManager(new GridLayoutManager(this, 2));

        }

    @NonNull
    private BackgroundAdapter getBackgroundAdapter() {
        int selectedPosition = 1;
        for (int i = 0; i < listItems.size(); i++) {
            if (listItems.get(i).getImg() == currentBackground) {
                selectedPosition = i;
                break;
            }
        }
        BackgroundAdapter adapter = new BackgroundAdapter(this, listItems, selectedPosition, (position, backgroundModel) -> {
            Intent i = new Intent(this, ApplyActivity.class);
            i.putExtra("background", backgroundModel.getImg());
            startActivity(i);
        });
        return adapter;
    }

    @Override
    public void bindView() {
        binding.ivBack.setOnClickListener(v -> onBack());
    }


    @Override
    protected void onResume() {
        super.onResume();
        getBackgroundAdapter();
    }

    @Override
    public void onBack() {
        finish();
    }
}
