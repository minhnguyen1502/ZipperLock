package com.example.zipperlock.ui.main;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.example.zipperlock.R;
import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityMainBinding;
import com.example.zipperlock.databinding.DialogBottomPermissionBinding;
import com.example.zipperlock.databinding.DialogPermissionBinding;
import com.example.zipperlock.ui.item.background.BackgroundActivity;
import com.example.zipperlock.ui.item.personalized.PersonalizedActivity;
import com.example.zipperlock.ui.item.row.RowActivity;
import com.example.zipperlock.ui.item.sound.SoundTypeActivity;
import com.example.zipperlock.ui.item.wallpaper.WallpaperActivity;
import com.example.zipperlock.ui.item.zipper.ZipperActivity;
import com.example.zipperlock.ui.lockscreen.LockScreen;
import com.example.zipperlock.ui.main.adapter.ItemAdapter;
import com.example.zipperlock.ui.main.model.ItemModel;
import com.example.zipperlock.ui.preview.PreviewActivity;
import com.example.zipperlock.ui.setting.SettingActivity;
import com.example.zipperlock.util.SPUtils;
import com.example.zipperlock.util.SystemUtil;

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
    private String ModelName;
    private boolean isShowDialogBottom = false;

    @Override
    public ActivityMainBinding getBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        countStorage = SPUtils.getInt(this, SPUtils.STORAGE, 0);
        countNotification = SPUtils.getInt(this, SPUtils.NOTIFICATION, 0);
        LockScreen.getInstance().init(this, true);
        if (LockScreen.getInstance().isActive()) {
            isLock = true;
            binding.ivEnable.setImageResource(R.drawable.img_sw_enable_on);
        } else {
            binding.ivEnable.setImageResource(R.drawable.img_sw_enable_off);
            isLock = false;

        }
        if (!checkNotificationPermission() || !checkStoragePermission() || !checkOverlayPermission()) {
            if (!isShowDialogBottom) {
                showDialogBottomPer();

            }
        }
        ModelName = Build.MANUFACTURER;
        f993sp = this.getSharedPreferences("USER_PREF", 0);

        binding.recycleView.setLayoutManager(new GridLayoutManager(this, 2));

        listItems = new ArrayList<>();
        listItems.add(new ItemModel(R.string.background, R.drawable.img_background));
        listItems.add(new ItemModel(R.string.zipper_style, R.drawable.img_zipper_style));
        listItems.add(new ItemModel(R.string.row_style, R.drawable.img_row_style));
        listItems.add(new ItemModel(R.string.sound_style, R.drawable.img_sound_style));
        listItems.add(new ItemModel(R.string.personalized, R.drawable.img_personalized));
        listItems.add(new ItemModel(R.string.wallpaper, R.drawable.img_wallpaper));

        adapter = new ItemAdapter(listItems, position -> {
            switch (position) {
                case 0:
                    if (checkOverlayPermission() && checkNotificationPermission() && checkStoragePermission()) {
                        startActivity(new Intent(this, BackgroundActivity.class));
                        break;
                    } else {
                        if (!isShowDialogBottom) {
                            showDialogBottomPer();

                        }
                    }

                case 1:
                    if (checkOverlayPermission() && checkNotificationPermission() && checkStoragePermission()) {

                        startActivity(new Intent(this, ZipperActivity.class));
                        break;
                    } else {
                        if (!isShowDialogBottom) {
                            showDialogBottomPer();

                        }
                    }
                case 2:
                    if (checkOverlayPermission() && checkNotificationPermission() && checkStoragePermission()) {

                        startActivity(new Intent(this, RowActivity.class));
                        break;
                    } else {
                        if (!isShowDialogBottom) {
                            showDialogBottomPer();

                        }
                    }
                case 3:
                    if (checkOverlayPermission() && checkNotificationPermission() && checkStoragePermission()) {

                        startActivity(new Intent(this, SoundTypeActivity.class));
                        break;
                    } else {
                        if (!isShowDialogBottom) {
                            showDialogBottomPer();

                        }
                    }
                case 4:
                    if (checkOverlayPermission() && checkNotificationPermission() && checkStoragePermission()) {

                        startActivity(new Intent(this, PersonalizedActivity.class));
                        break;
                    } else {
                        if (!isShowDialogBottom) {
                            showDialogBottomPer();

                        }
                    }
                case 5:
                    if (checkOverlayPermission() && checkNotificationPermission() && checkStoragePermission()) {

                        startActivity(new Intent(this, WallpaperActivity.class));
                        break;
                    } else {
                        if (!isShowDialogBottom) {
                            showDialogBottomPer();

                        }
                    }
            }
        }, this);

        binding.recycleView.setAdapter(adapter);
    }

    private SharedPreferences f993sp;

    public void XiaomiBackGroundPapWindowsPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Permission");
        builder.setMessage("please allow permission display pop up windows while running in the background");
        builder.setCancelable(true);
        builder.setPositiveButton("Setting", (dialogInterface, i) -> {
            dialogInterface.cancel();
            SharedPreferences.Editor edit = f993sp.edit();
            edit.putBoolean("POP_WINDOWS_PERMISSION", true);
            edit.apply();
            //An resume
            Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
            intent.putExtra("extra_pkgname", this.getPackageName());
            this.startActivity(intent);
        });

        builder.setNegativeButton(R.string.cancel, (dialogInterface, i) -> {
            dialogInterface.cancel();
        });

        builder.create().show();
    }

    @Override
    public void bindView() {
        binding.ivSetting.setOnClickListener(v -> {
            if (checkOverlayPermission() && checkNotificationPermission() && checkStoragePermission()) {

                startActivity(new Intent(this, SettingActivity.class));
            } else {
                if (!isShowDialogBottom) {
                    showDialogBottomPer();

                }
            }
        });
        binding.btnPreview.setOnClickListener(v -> {
            if (checkOverlayPermission() && checkNotificationPermission() && checkStoragePermission()) {
                startActivity(new Intent(this, PreviewActivity.class));
            } else {
                if (!isShowDialogBottom) {
                    showDialogBottomPer();

                }
            }
        });
        binding.ivEnable.setOnClickListener(v -> {
            if (checkOverlayPermission() && checkNotificationPermission() && checkStoragePermission()) {

                isLock = !isLock;
                if (isLock) {
                    LockScreen.getInstance().active();
                    binding.ivEnable.setImageResource(R.drawable.img_sw_enable_on);

                } else {
                    LockScreen.getInstance().deactivate();
                    binding.ivEnable.setImageResource(R.drawable.img_sw_enable_off);

                }
            } else {
                if (!isShowDialogBottom) {
                    showDialogBottomPer();

                }
            }
        });
    }

    @SuppressLint("ResourceAsColor")
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

        bindingPer.step2.setOnClickListener(v -> {
            if (checkNotificationPermission()) {
                bindingPer.step2.setTextColor(R.color.white);
                bindingPer.step1.setBackgroundResource(R.drawable.bg_btn_status_3);
                bindingPer.step3.setBackgroundResource(R.drawable.bg_btn_status_1);
                bindingPer.step2.setBackgroundResource(R.drawable.bg_btn_status_2);
                bindingPer.ivImg.setImageResource(R.drawable.img_overlay_dialog);
                bindingPer.overlay.setVisibility(View.VISIBLE);
                bindingPer.media.setVisibility(View.GONE);
                bindingPer.noti.setVisibility(View.GONE);
                bindingPer.step1.setClickable(false);
            } else {
                Toast.makeText(this, getString(R.string.permission_is_required_to_proceed_to_the_next_step), Toast.LENGTH_SHORT).show();
            }

        });
        bindingPer.step3.setOnClickListener(v -> {
            if (checkOverlayPermission()) {
                bindingPer.step2.setTextColor(R.color.white);
                bindingPer.step3.setTextColor(R.color.white);
                bindingPer.step1.setBackgroundResource(R.drawable.bg_btn_status_3);
                bindingPer.step3.setBackgroundResource(R.drawable.bg_btn_status_2);
                bindingPer.step2.setBackgroundResource(R.drawable.bg_btn_status_3);
                bindingPer.ivImg.setImageResource(R.drawable.img_media_dialog);
                bindingPer.overlay.setVisibility(View.GONE);
                bindingPer.media.setVisibility(View.VISIBLE);
                bindingPer.noti.setVisibility(View.GONE);
                bindingPer.step1.setClickable(false);
                bindingPer.step2.setClickable(false);
            } else {
                Toast.makeText(this, getString(R.string.permission_is_required_to_proceed_to_the_next_step), Toast.LENGTH_SHORT).show();
            }

        });
        bindingPer.ivSwOverlay.setOnClickListener(v -> {
            if (ModelName.equals("Xiaomi") && !f993sp.getBoolean("POP_WINDOWS_PERMISSION", false)) {
                XiaomiBackGroundPapWindowsPermission();
            } else if (!checkOverlayPermission()) {
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
            isShowDialogBottom = false;
            dialogPer.dismiss();
        });
        dialogPer.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isShowDialogBottom = false;

            }
        });

        if (!isShowDialogBottom) {
            dialogPer.show();
            isShowDialogBottom = true;
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private void checkSwStorage() {
        if (dialogPer != null) {
            if (checkStoragePermission()) {
                bindingPer.ivSwMedia.setImageResource(R.drawable.ic_sw_dialog_on);
            } else {
                bindingPer.ivSwMedia.setImageResource(R.drawable.img_sw_off);
            }
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    private void checkSwOverlay() {
        if (dialogPer != null) {
            if (checkOverlayPermission()) {
                bindingPer.ivSwOverlay.setImageResource(R.drawable.ic_sw_dialog_on);
            } else {
                bindingPer.ivSwOverlay.setImageResource(R.drawable.img_sw_off);
            }
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
        if (dialogPer != null) {
            if (checkNotificationPermission()) {

                bindingPer.ivSwNoti.setImageResource(R.drawable.ic_sw_dialog_on);

            } else {
                bindingPer.ivSwNoti.setImageResource(R.drawable.img_sw_off);
            }
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
        } else return true;
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

        bindingPer.tvStay.setOnClickListener(view -> {
            isShowDialog = false;
            dialog.dismiss();
        });

        bindingPer.tvAgree.setOnClickListener(view -> {
//            AppOpenManager.getInstance().disableAppResumeWithActivity(PermissionActivity.class);
            if (type == 3) {
                Intent intent = null;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                    intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
                }
                louncherOverlay.launch(intent);
                dialog.dismiss();
                isShowDialog = false;

            } else {
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
    protected void onResume() {
        super.onResume();
        checkSwNotification();
        checkSwStorage();
        checkSwOverlay();
        if (dialogPer != null) {
            if (checkStoragePermission() && checkNotificationPermission() && checkOverlayPermission()) {
                dialogPer.dismiss();
                isShowDialogBottom = false;

            }
        }

    }

    @Override
    public void onBack() {
        finishThisActivity();
    }

}
