package com.example.zipperlock.ui.permission;

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
import android.widget.RelativeLayout;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityPermissionBinding;
import com.example.zipperlock.databinding.DialogPermissionBinding;
import com.example.zipperlock.ui.main.MainActivity;
import com.example.zipperlock.util.SPUtils;
import com.example.zipperlock.util.SharePrefUtils;
import com.example.zipperlock.util.SystemUtil;

public class PermissionActivity extends BaseActivity<ActivityPermissionBinding> {
    private final int REQUEST_CODE_STORAGE_PERMISSION = 124;
    private final int REQUEST_CODE_NOTIFICATION_PERMISSION = 127;
    private int countStorage = 0;
    private int countNotification = 0;
    private boolean isShowDialog = false;

    Dialog dialog;
    @Override
    public ActivityPermissionBinding getBinding() {
        return ActivityPermissionBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        countStorage = SPUtils.getInt(this, SPUtils.STORAGE, 0);
        countNotification = SPUtils.getInt(this, SPUtils.NOTIFICATION, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            binding.noti.setVisibility(View.VISIBLE);
            checkAllPer();
        } else {
            binding.noti.setVisibility(View.GONE);
            checkSwStorage();
        }
    }

    @Override
    public void bindView() {
        binding.tvContinue.setOnClickListener(v -> startNextActivity());

        binding.ivSwNoti.setOnClickListener(view -> {
            if (!checkNotificationPermission()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_NOTIFICATION_PERMISSION);
                }
            }
        });

        binding.ivSwOverlay.setOnClickListener(v -> {
            if (!checkOverlayPermission()) {
                showDialogGotoSetting(3);
            }
        });

        binding.ivSwMedia.setOnClickListener(view -> {
            if (!checkStoragePermission()) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES}, REQUEST_CODE_STORAGE_PERMISSION);
                } else {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
                }
            }
        });
    }
    private void startNextActivity() {
        SharePrefUtils.increaseCountFirstHelp(this);
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
    }
    private boolean checkStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED ) {
            return true;
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED;
            }
            return false;
        }
    }

    private boolean checkNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED;
        } else return false;
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
                if (Settings.canDrawOverlays(PermissionActivity.this)) {
                } else {
                }
            } else {
            }
        }
    });
    public boolean checkOverlayPermission() {
        return Build.VERSION.SDK_INT < 23 || Settings.canDrawOverlays(PermissionActivity.this);
    }
    @SuppressLint("ClickableViewAccessibility")
    private void checkSwStorage() {
        if (checkStoragePermission()) {
            binding.ivSwMedia.setImageResource(R.drawable.img_sw_on);
        } else {
            binding.ivSwMedia.setImageResource(R.drawable.img_sw_off);
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    private void checkSwOverlay() {
        if (checkOverlayPermission()) {
            binding.ivSwOverlay.setImageResource(R.drawable.img_sw_on);
        } else {
            binding.ivSwOverlay.setImageResource(R.drawable.img_sw_off);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void checkSwNotification() {
        if (checkNotificationPermission()) {
            binding.ivSwNoti.setImageResource(R.drawable.img_sw_on);

        } else {
            binding.ivSwNoti.setImageResource(R.drawable.img_sw_off);
        }
    }

    private void checkAllPer(){
        checkSwOverlay();
        checkSwNotification();
        checkSwStorage();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAllPer();
    }

    @Override
    public void onBack() {
        finishAffinity();
    }
}
