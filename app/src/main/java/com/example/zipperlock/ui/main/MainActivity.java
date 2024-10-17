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
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
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
import com.example.zipperlock.ui.main.lock.Lockscreen;
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
    private boolean isLock;
    private Dialog dialog;
    private final int REQUEST_CODE_STORAGE_PERMISSION = 124;
    private final int REQUEST_CODE_NOTIFICATION_PERMISSION = 127;
    private int countStorage = 0;
    private int countNotification = 0;
    DialogBottomPermissionBinding bindingPer;
    Dialog dialogPer;
    @Override
    public ActivityMainBinding getBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        isLock = SPUtils.getBoolean(this,SPUtils.IS_LOCK, false);
        countStorage = SPUtils.getInt(this, SPUtils.STORAGE, 0);
        countNotification = SPUtils.getInt(this, SPUtils.NOTIFICATION, 0);
        if (!checkNotificationPermission() || !checkStoragePermission() || !checkOverlayPermission()){
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
        binding.ivEnable.setOnClickListener(v -> {
            isLock = !isLock;
            if (isLock){
                binding.ivEnable.setImageResource(R.drawable.img_sw_enable_on);
                SPUtils.setBoolean(this, SPUtils.IS_LOCK, isLock);
                Lockscreen.getInstance(this).startLockscreenService();
            }else {
                binding.ivEnable.setImageResource(R.drawable.img_sw_enable_off);
                SPUtils.setBoolean(this, SPUtils.IS_LOCK, isLock);
                Lockscreen.getInstance(this).stopLockscreenService();

            }
        });
    }
    private void showDialogBottomPer() {
        dialogPer = new Dialog(this);
        bindingPer = DialogBottomPermissionBinding.inflate(getLayoutInflater());
        dialogPer.setContentView(bindingPer.getRoot());
        Window window = dialogPer.getWindow();
        if (window != null) {
            window.setGravity(Gravity.BOTTOM);
            window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialogPer.setCancelable(true);
        dialogPer.setCanceledOnTouchOutside(true);

        checkSwOverlay();
        checkSwStorage();
        checkSwNotification();

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.TIRAMISU) {
            bindingPer.ivSwNoti.setImageResource(R.drawable.ic_sw_dialog_on);
            checkSwOverlay();
            checkSwStorage();
        }
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
        bindingPer.ivSwNoti.setOnClickListener(v -> {
            if (!checkOverlayPermission()) {
                showDialogGotoSetting(3);
            }
        });
        bindingPer.ivSwNoti.setOnClickListener(view -> {
            if (!checkNotificationPermission()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_NOTIFICATION_PERMISSION);
                }
            }
        });
        bindingPer.ivSwMedia.setOnClickListener(view -> {
            if (!checkStoragePermission()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_CODE_STORAGE_PERMISSION);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
                }
            }
        });
        bindingPer.ivClose.setOnClickListener(v -> {
            dialogPer.dismiss();
        });

        if (dialogPer.isShowing()) {
            dialogPer.dismiss();
        }
        dialogPer.show();

    }
    @SuppressLint("ClickableViewAccessibility")
    private void checkSwStorage() {
        if (checkStoragePermission()) {
            bindingPer.ivSwMedia.setImageResource(R.drawable.img_sw_on);
        } else {
            bindingPer.ivSwMedia.setImageResource(R.drawable.img_sw_off);
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    private void checkSwOverlay() {
        if (checkOverlayPermission()) {
            bindingPer.ivSwOverlay.setImageResource(R.drawable.img_sw_on);
        } else {
            bindingPer.ivSwOverlay.setImageResource(R.drawable.img_sw_off);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_CODE_NOTIFICATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkSwNotification();
            }

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                checkSwNotification();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (!shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                        countNotification++;
                        SPUtils.setInt(this, SPUtils.NOTIFICATION, countNotification);
                        if (countNotification > 1) {
                            if (!isShowDialog) {
                                showDialogGotoSetting(2);
                            }
                        }
                    }
                }
            }
        }

        if (requestCode == REQUEST_CODE_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkSwStorage();
            }

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED) {
                checkSwStorage();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES)) {
                            countStorage++;
//                            AppOpenManager.getInstance().disableAppResumeWithActivity(PermissionActivity.class);
                            SPUtils.setInt(this, SPUtils.STORAGE, countStorage);
                            if (countStorage > 1) {
                                if (!isShowDialog) {
                                    showDialogGotoSetting(4);
                                }
                            }
                        }
                    } else {
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            countStorage++;
//                            AppOpenManager.getInstance().disableAppResumeWithActivity(PermissionActivity.class);
                            SPUtils.setInt(this, SPUtils.STORAGE, countStorage);
                            if (countStorage > 1) {
                                if (!isShowDialog) {
                                    showDialogGotoSetting(1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    private void checkSwNotification() {
        if (checkNotificationPermission()) {
            bindingPer.ivSwNoti.setImageResource(R.drawable.img_sw_on);

        } else {
            bindingPer.ivSwNoti.setImageResource(R.drawable.img_sw_off);
        }
    }


    private boolean checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
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

    private boolean isShowDialog = false;
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void showDialogGotoSetting(int type) {
        dialog = new Dialog(this);
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
        } else if (type == 4) {
            bindingPer.tvContent.setText(R.string.content_dialog_per_4);
        }

        bindingPer.tvStay.setOnClickListener(view ->
        {
            isShowDialog = false;
            dialog.dismiss();
        });

        bindingPer.tvAgree.setOnClickListener(view -> {
//            AppOpenManager.getInstance().disableAppResumeWithActivity(PermissionActivity.class);
            if (type == 3){
                Intent intent = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                }
                louncherOverlay.launch(intent);
                dialog.dismiss();
                isShowDialog = false;

            }else {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
                dialog.dismiss();
                isShowDialog = false;

            }

        });

        if (!isShowDialog) {
            dialog.show();
            isShowDialog = true;
        }
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
    @Override
    public void onBack() {
        finishThisActivity();
    }

}
