package com.example.zipperlock.ui.main;

import android.Manifest;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityMainBinding;
import com.example.zipperlock.databinding.DialogBottomPermissionBinding;
import com.example.zipperlock.ui.main.adapter.ItemAdapter;
import com.example.zipperlock.ui.main.model.ItemModel;
import com.example.zipperlock.util.SPUtils;
import com.example.zipperlock.util.Utils;

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
        if (Utils.isFirstOpenApp()){
            showDialogBottomPer();
        }
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

    private void showDialogBottomPer() {
        Dialog dialog = new Dialog(this);
        DialogBottomPermissionBinding bindingPer = DialogBottomPermissionBinding.inflate(getLayoutInflater());
        dialog.setContentView(bindingPer.getRoot());
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        bindingPer.step1.setOnClickListener(v -> {
            bindingPer.step1.setBackgroundResource(R.drawable.bg_btn_status_2);
            bindingPer.step3.setBackgroundResource(R.drawable.bg_btn_status_1);
            bindingPer.step2.setBackgroundResource(R.drawable.bg_btn_status_1);
            bindingPer.ivImg.setImageResource(R.drawable.img_noti_dialog);
            bindingPer.overlay.setVisibility(View.GONE);
            bindingPer.media.setVisibility(View.GONE);
            bindingPer.noti.setVisibility(View.VISIBLE);
        });
        bindingPer.step2.setOnClickListener(v -> {
            bindingPer.step1.setBackgroundResource(R.drawable.bg_btn_status_1);
            bindingPer.step3.setBackgroundResource(R.drawable.bg_btn_status_1);
            bindingPer.step2.setBackgroundResource(R.drawable.bg_btn_status_2);
            bindingPer.ivImg.setImageResource(R.drawable.img_noti_dialog);
            bindingPer.overlay.setVisibility(View.VISIBLE);
            bindingPer.media.setVisibility(View.GONE);
            bindingPer.noti.setVisibility(View.GONE);
        });
        bindingPer.step3.setOnClickListener(v -> {
            bindingPer.step1.setBackgroundResource(R.drawable.bg_btn_status_1);
            bindingPer.step3.setBackgroundResource(R.drawable.bg_btn_status_2);
            bindingPer.step2.setBackgroundResource(R.drawable.bg_btn_status_1);
            bindingPer.ivImg.setImageResource(R.drawable.img_noti_dialog);
            bindingPer.overlay.setVisibility(View.GONE);
            bindingPer.media.setVisibility(View.VISIBLE);
            bindingPer.noti.setVisibility(View.GONE);
        });

        bindingPer.ivClose.setOnClickListener(v -> {
                dialog.dismiss();
        });

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog.show();

    }

    @Override
    public void bindView() {

    }
    public boolean checkOverlayPermission() {
        return Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(this);
    }
    private boolean checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
        } else return false;
    }

    @Override
    public void onBack() {
        finishThisActivity();
    }

}
