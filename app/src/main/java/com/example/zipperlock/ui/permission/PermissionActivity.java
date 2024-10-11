package com.example.zipperlock.ui.permission;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityPermissionBinding;
import com.example.zipperlock.ui.main.MainActivity;
import com.example.zipperlock.util.SPUtils;
import com.example.zipperlock.util.SharePrefUtils;

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
    }

    @Override
    public void bindView() {
        binding.tvContinue.setOnClickListener(v -> {
            startNextActivity();
        });
//        binding.tvReadNotifi.setOnClickListener(view -> {
//            if (!checkNotificationPermission()) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                    ActivityCompat.requestPermissions(PermissionActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, REQUEST_CODE_NOTIFICATION_PERMISSION);
//                }
//            }
//        });
//
//        binding.tvRead.setOnClickListener(view -> {
//            if (!checkStoragePermission()) {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO, Manifest.permission.READ_MEDIA_AUDIO}, REQUEST_CODE_STORAGE_PERMISSION);
//                } else {
//                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_STORAGE_PERMISSION);
//                }
//            }
//        });
    }
    private void startNextActivity() {
        SharePrefUtils.increaseCountFirstHelp(this);
        startActivity(new Intent(this, MainActivity.class));
        finishAffinity();
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
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_IMAGES) && !shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_AUDIO) && !shouldShowRequestPermissionRationale(Manifest.permission.READ_MEDIA_VIDEO)) {
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
                        if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) && !shouldShowRequestPermissionRationale(Manifest.permission.READ_EXTERNAL_STORAGE)) {
                            countStorage++;
//                            AppOpenManager.getInstance().disableAppResumeWithActivity(PermissionActivity.class);
                            SPUtils.setInt(this, SPUtils.STORAGE, countStorage);
                            if (countStorage > 1) {
                                if (!isShowDialog) {
                                    showDialogGotoSetting(3);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @SuppressLint("ClickableViewAccessibility")
    private void checkSwStorage() {
        if (checkStoragePermission()) {
        } else {
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void checkSwNotification() {
        if (checkNotificationPermission()) {
        } else {
        }
    }
    private void showDialogGotoSetting(int i) {
    }

    @Override
    public void onBack() {
        finishAffinity();
    }
}
