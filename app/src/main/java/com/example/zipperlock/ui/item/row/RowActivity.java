package com.example.zipperlock.ui.item.row;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityListItemBinding;
import com.example.zipperlock.ui.item.row.adapter.RowAdapter;
import com.example.zipperlock.ui.item.row.model.Row;
import com.example.zipperlock.ui.item.zipper.adapter.ZipperAdapter;
import com.example.zipperlock.ui.item.zipper.model.Zipper;
import com.example.zipperlock.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class RowActivity extends BaseActivity<ActivityListItemBinding> {
    private List<Row> listItems;

    @Override
    public ActivityListItemBinding getBinding() {
        return ActivityListItemBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        listItems = new ArrayList<>();
        listItems.add(new Row( R.drawable.img_row_list_1));
        listItems.add(new Row( R.drawable.img_row_list_2));
        listItems.add(new Row( R.drawable.img_row_list_3));
        listItems.add(new Row( R.drawable.img_row_list_4));
        listItems.add(new Row( R.drawable.img_row_list_5));
        listItems.add(new Row( R.drawable.img_row_list_6));
        listItems.add(new Row( R.drawable.img_row_list_7));
        listItems.add(new Row( R.drawable.img_row_list_8));
        listItems.add(new Row( R.drawable.img_row_list_9));
        listItems.add(new Row( R.drawable.img_row_list_10));
        listItems.add(new Row( R.drawable.img_row_list_11));
        listItems.add(new Row( R.drawable.img_row_list_12));
        listItems.add(new Row( R.drawable.img_row_list_13));
        listItems.add(new Row( R.drawable.img_row_list_14));
        listItems.add(new Row( R.drawable.img_row_list_15));
        listItems.add(new Row( R.drawable.img_row_list_16));
        int currentBackground = SPUtils.getInt(this, SPUtils.ROW, -1);
        RowAdapter adapter = getAdapter(currentBackground);
        binding.recycleView.setAdapter(adapter);
        binding.recycleView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    @Override
    public void bindView() {

    }
    private RowAdapter getAdapter(int currentBackground) {
        int selectedPosition = 3;
        for (int i = 0; i < listItems.size(); i++) {
            if (listItems.get(i).getImg() == currentBackground) {
                selectedPosition = i;
                break;
            }
        }
        return new RowAdapter(this, listItems,selectedPosition,  (position, zipper) -> {
            SPUtils.setInt(this, SPUtils.ZIPPER, zipper.getImg());
        });
    }

    @Override
    public void onBack() {

    }
}
