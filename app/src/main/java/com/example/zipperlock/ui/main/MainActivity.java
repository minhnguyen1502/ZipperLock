package com.example.zipperlock.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.viewpager2.widget.ViewPager2;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityMainBinding;
import com.example.zipperlock.databinding.DialogBottomPermissionBinding;
import com.example.zipperlock.databinding.DialogPermissionBinding;
import com.example.zipperlock.ui.item.background.BackgroundActivity;
import com.example.zipperlock.ui.item.personalized.PersonalizedActivity;
import com.example.zipperlock.ui.item.row.RowActivity;
import com.example.zipperlock.ui.item.sound.SoundActivity;
import com.example.zipperlock.ui.item.sound.SoundTypeActivity;
import com.example.zipperlock.ui.item.wallpaper.WallpaperActivity;
import com.example.zipperlock.ui.item.zipper.ZipperActivity;
import com.example.zipperlock.ui.main.adapter.ItemAdapter;
import com.example.zipperlock.ui.main.model.ItemModel;
import com.example.zipperlock.ui.permission.PermissionActivity;
import com.example.zipperlock.ui.setting.SettingActivity;
import com.example.zipperlock.util.SPUtils;
import com.example.zipperlock.util.SystemUtil;
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
                    startActivity(new Intent(this, BackgroundActivity.class));
                    break;
                case 1:
                    startActivity(new Intent(this, ZipperActivity.class));
                    break;
                case 2:
                    startActivity(new Intent(this, RowActivity.class));
                    break;
                case 3:
                    startActivity(new Intent(this, SoundTypeActivity.class));
                    break;
                case 4:
                    startActivity(new Intent(this, PersonalizedActivity.class));
                    break;
                case 5:
                    startActivity(new Intent(this, WallpaperActivity.class));
                    break;
            }
        },this);

        binding.recycleView.setAdapter(adapter);
    }
    @Override
    public void bindView() {
        binding.ivSetting.setOnClickListener(v -> startActivity(new Intent(this, SettingActivity.class)));
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

        checkSwOverlay(bindingPer);
        checkSwStorage(bindingPer);
        checkSwNotification(bindingPer);

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
            bindingPer.ivImg.setImageResource(R.drawable.img_overlay_dialog);
            bindingPer.overlay.setVisibility(View.VISIBLE);
            bindingPer.media.setVisibility(View.GONE);
            bindingPer.noti.setVisibility(View.GONE);
        });
        bindingPer.step3.setOnClickListener(v -> {
            bindingPer.step1.setBackgroundResource(R.drawable.bg_btn_status_1);
            bindingPer.step3.setBackgroundResource(R.drawable.bg_btn_status_2);
            bindingPer.step2.setBackgroundResource(R.drawable.bg_btn_status_1);
            bindingPer.ivImg.setImageResource(R.drawable.img_media_dialog);
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
    @SuppressLint("ClickableViewAccessibility")
    private void checkSwStorage(DialogBottomPermissionBinding bindingPer) {
        if (checkStoragePermission()) {
            bindingPer.ivSwMedia.setImageResource(R.drawable.img_sw_on);
        } else {
            bindingPer.ivSwMedia.setImageResource(R.drawable.img_sw_off);
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    private void checkSwOverlay(DialogBottomPermissionBinding bindingPer) {
        if (checkOverlayPermission()) {
            bindingPer.ivSwOverlay.setImageResource(R.drawable.img_sw_on);
        } else {
            bindingPer.ivSwOverlay.setImageResource(R.drawable.img_sw_off);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void checkSwNotification(DialogBottomPermissionBinding bindingPer) {
        if (checkNotificationPermission()) {
            bindingPer.ivSwNoti.setImageResource(R.drawable.img_sw_on);

        } else {
            bindingPer.ivSwNoti.setImageResource(R.drawable.img_sw_off);
        }
    }

    private boolean checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
            }
            return false;
        }
    }
    public boolean checkOverlayPermission() {
        return Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(MainActivity.this);
    }
    private boolean checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
        } else return false;
    }
    @SuppressLint("UseCompatLoadingForDrawables")
    private final ActivityResultLauncher<Intent> louncherOverlay = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == RESULT_OK) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(MainActivity.this)) {
                } else {
                }
            } else {
            }
        }
    });
    private boolean isShowDialog = false;

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showDialogGotoSetting(int type) {
        Dialog dialog = new Dialog(this);
        SystemUtil.setLocale(this);
        DialogPermissionBinding bindingPer = DialogPermissionBinding.inflate(getLayoutInflater());
        dialog.setContentView(bindingPer.getRoot());

        if (dialog.getWindow() != null) {
            dialog.getWindow().setGravity(Gravity.CENTER);
            dialog.getWindow().setLayout(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        if (type == 1) {
            bindingPer.tvContent.setText(R.string.content_dialog_per_1);
        } else if (type == 2) {
            bindingPer.tvContent.setText(R.string.content_dialog_per_2);
        } else if (type == 3) {
            bindingPer.tvContent.setText(R.string.content_dialog_per_3);
        }

        bindingPer.tvStay.setOnClickListener(view -> dialog.dismiss());

        bindingPer.tvAgree.setOnClickListener(view -> {
//            AppOpenManager.getInstance().disableAppResumeWithActivity(PermissionActivity.class);
            if (type == 3){
                Intent intent = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                }
                louncherOverlay.launch(intent);
            }else {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
            dialog.dismiss();

        });

        if (!isShowDialog) {
            dialog.show();
            isShowDialog = true;
        }
    }
    @Override
    public void onBack() {
        finishThisActivity();
    }

}
