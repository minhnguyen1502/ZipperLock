package com.example.zipperlock.ui.item.zipper;

import android.content.Intent;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityListItemBinding;
import com.example.zipperlock.ui.apply.ApplyActivity;
import com.example.zipperlock.ui.item.background.adapter.BackgroundAdapter;
import com.example.zipperlock.ui.item.zipper.adapter.ZipperAdapter;
import com.example.zipperlock.ui.item.zipper.model.Zipper;
import com.example.zipperlock.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class ZipperActivity extends BaseActivity<ActivityListItemBinding> {
    private List<Zipper> listItems;

    @Override
    public ActivityListItemBinding getBinding() {
        return ActivityListItemBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        listItems = new ArrayList<>();
        listItems.add(new Zipper( R.drawable.img_zipper_list_1));
        listItems.add(new Zipper( R.drawable.img_zipper_list_2));
        listItems.add(new Zipper( R.drawable.img_zipper_list_3));
        listItems.add(new Zipper( R.drawable.img_zipper_list_4));
        listItems.add(new Zipper( R.drawable.img_zipper_list_5));
        listItems.add(new Zipper( R.drawable.img_zipper_list_6));
        listItems.add(new Zipper( R.drawable.img_zipper_list_7));
        listItems.add(new Zipper( R.drawable.img_zipper_list_8));
        listItems.add(new Zipper( R.drawable.img_zipper_list_9));
        listItems.add(new Zipper( R.drawable.img_zipper_list_10));
        listItems.add(new Zipper( R.drawable.img_zipper_list_11));
        listItems.add(new Zipper( R.drawable.img_zipper_list_12));
        listItems.add(new Zipper( R.drawable.img_zipper_list_13));
        listItems.add(new Zipper( R.drawable.img_zipper_list_14));
        listItems.add(new Zipper( R.drawable.img_zipper_list_15));
        listItems.add(new Zipper( R.drawable.img_zipper_list_16));
        listItems.add(new Zipper( R.drawable.img_zipper_list_17));
        listItems.add(new Zipper( R.drawable.img_zipper_list_18));
        listItems.add(new Zipper( R.drawable.img_zipper_list_19));
        listItems.add(new Zipper( R.drawable.img_zipper_list_20));
        listItems.add(new Zipper( R.drawable.img_zipper_list_21));
        listItems.add(new Zipper( R.drawable.img_zipper_list_22));
        int currentBackground = SPUtils.getInt(this, SPUtils.ZIPPER, -1);
        ZipperAdapter adapter = getAdapter(currentBackground);
        binding.recycleView.setAdapter(adapter);
        binding.recycleView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    @Override
    public void bindView() {
        binding.ivBack.setOnClickListener(v -> onBack());

    }
    private ZipperAdapter getAdapter(int currentBackground) {
        int selectedPosition = 3;
        for (int i = 0; i < listItems.size(); i++) {
            if (listItems.get(i).getImg() == currentBackground) {
                selectedPosition = i;
                break;
            }
        }
        return new ZipperAdapter(this, listItems,selectedPosition,  (position, zipper) -> {
            Intent i = new Intent(this, ApplyActivity.class);
            i.putExtra("zipper", zipper.getImg());
            startActivity(i);
        });
    }

    @Override
    public void onBack() {
        finish();
    }
}
