package com.example.zipperlock.ui.item.row;

import android.content.Intent;
import android.util.Log;

import androidx.recyclerview.widget.GridLayoutManager;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityListItemBinding;
import com.example.zipperlock.ui.apply.ApplyActivity;
import com.example.zipperlock.ui.item.row.adapter.RowAdapter;
import com.example.zipperlock.ui.item.row.model.Row;
import com.example.zipperlock.util.SPUtils;

import java.util.ArrayList;
import java.util.List;

public class RowActivity extends BaseActivity<ActivityListItemBinding> {
    private List<Row> listItems;
    int currentRow;

    @Override
    public ActivityListItemBinding getBinding() {
        return ActivityListItemBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        binding.title.setText(R.string.row_style);
        listItems = new ArrayList<>();
        listItems.add(new Row(R.drawable.img_row_list_1, R.drawable.img_row_01, R.drawable.img_row_r_1, R.drawable.img_row_l_1));
        listItems.add(new Row(R.drawable.img_row_list_2, R.drawable.img_row_02, R.drawable.img_row_r_2, R.drawable.img_row_l_2));
        listItems.add(new Row(R.drawable.img_row_list_3, R.drawable.img_row_03, R.drawable.img_row_r_3, R.drawable.img_row_l_3));
        listItems.add(new Row(R.drawable.img_row_list_4, R.drawable.img_row_04, R.drawable.img_row_r_4, R.drawable.img_row_l_4));
        listItems.add(new Row(R.drawable.img_row_list_5, R.drawable.img_row_05, R.drawable.img_row_r_5, R.drawable.img_row_l_5));
        listItems.add(new Row(R.drawable.img_row_list_6, R.drawable.img_row_06, R.drawable.img_row_r_6, R.drawable.img_row_l_6));
        listItems.add(new Row(R.drawable.img_row_list_7, R.drawable.img_row_07, R.drawable.img_row_r_7, R.drawable.img_row_l_7));
        listItems.add(new Row(R.drawable.img_row_list_8, R.drawable.img_row_08, R.drawable.img_row_r_8, R.drawable.img_row_l_8));
        listItems.add(new Row(R.drawable.img_row_list_9, R.drawable.img_row_09, R.drawable.img_row_r_9, R.drawable.img_row_l_9));
        listItems.add(new Row(R.drawable.img_row_list_10, R.drawable.img_row_10, R.drawable.img_row_r_10, R.drawable.img_row_l_10));
        listItems.add(new Row(R.drawable.img_row_list_11, R.drawable.img_row_11, R.drawable.img_row_r_11, R.drawable.img_row_l_11));
        listItems.add(new Row(R.drawable.img_row_list_12, R.drawable.img_row_12, R.drawable.img_row_r_12, R.drawable.img_row_l_12));
        listItems.add(new Row(R.drawable.img_row_list_13, R.drawable.img_row_13, R.drawable.img_row_r_13, R.drawable.img_row_l_13));
        listItems.add(new Row(R.drawable.img_row_list_14, R.drawable.img_row_14, R.drawable.img_row_r_14, R.drawable.img_row_l_14));
        listItems.add(new Row(R.drawable.img_row_list_15, R.drawable.img_row_15, R.drawable.img_row_r_15, R.drawable.img_row_l_15));
        listItems.add(new Row(R.drawable.img_row_list_16, R.drawable.img_row_16, R.drawable.img_row_r_16, R.drawable.img_row_l_16));
        currentRow = SPUtils.getInt(this, SPUtils.ROW, -1);
        RowAdapter adapter = getAdapter();
        binding.recycleView.setAdapter(adapter);
        binding.recycleView.setLayoutManager(new GridLayoutManager(this, 2));

    }

    @Override
    public void bindView() {
        binding.ivBack.setOnClickListener(v -> onBack());

    }

    private RowAdapter getAdapter() {
        int selectedPosition = 0;
        for (int i = 0; i < listItems.size(); i++) {
            if (listItems.get(i).getImg_full() == currentRow) {
                selectedPosition = i;
                break;
            }
        }
        return new RowAdapter(this, listItems, selectedPosition, (position, row) -> {
            Intent i = new Intent(this, ApplyActivity.class);
            i.putExtra("row", row.getImg_full());
            i.putExtra("row_r", row.getImg_right());
            i.putExtra("row_l", row.getImg_left());
            startActivity(i);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAdapter();
    }

    @Override
    public void onBack() {
        finish();
    }
}
