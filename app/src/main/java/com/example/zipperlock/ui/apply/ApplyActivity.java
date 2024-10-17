package com.example.zipperlock.ui.apply;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.zipperlock.base.BaseActivity;
import com.example.zipperlock.databinding.ActivityApplyBinding;
import com.example.zipperlock.databinding.DialogBottomPermissionBinding;
import com.example.zipperlock.databinding.DialogDiscardBinding;
import com.example.zipperlock.databinding.DialogPermissionBinding;
import com.example.zipperlock.ui.item.background.BackgroundActivity;
import com.example.zipperlock.ui.item.row.RowActivity;
import com.example.zipperlock.ui.item.sound.SoundActivity;
import com.example.zipperlock.ui.item.zipper.ZipperActivity;
import com.example.zipperlock.ui.main.MainActivity;
import com.example.zipperlock.ui.successfully.SuccessfullyActivity;
import com.example.zipperlock.util.SPUtils;
import com.example.zipperlock.util.SystemUtil;

public class ApplyActivity extends BaseActivity<ActivityApplyBinding> {
    private final boolean isApply = false;
    private int background;
    private int zipper;
    private int row;
    private int row_r;
    private int row_l;
    private int sound_open;
    private int sound_zipper;
    private String imageUriString;
    private String TAG = "data";
    @Override
    public ActivityApplyBinding getBinding() {
        return ActivityApplyBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        imageUriString = intent.getStringExtra("imageUri");
        background = intent.getIntExtra("background", SPUtils.getInt(this, SPUtils.BG, -1));
        zipper = intent.getIntExtra("zipper", SPUtils.getInt(this, SPUtils.ZIPPER, -1));
        sound_open = intent.getIntExtra("sound_zipper", SPUtils.getInt(this, SPUtils.SOUND_OPEN, -1));
        sound_zipper = intent.getIntExtra("sound_open", SPUtils.getInt(this, SPUtils.SOUND_ZIPPER, -1));
        row = intent.getIntExtra("row", SPUtils.getInt(this, SPUtils.ROW, -1));
        row_r = intent.getIntExtra("row_r", SPUtils.getInt(this, SPUtils.ROW_RIGHT, -1));
        row_l = intent.getIntExtra("row_l", SPUtils.getInt(this, SPUtils.ROW_LEFT, -1));

        if (imageUriString == null && background == -1){
            String bgPer = SPUtils.getString(this, SPUtils.BG_PER, null);
            int bgInt = SPUtils.getInt(this, SPUtils.BG, -1);
            if (bgPer != null) {
                binding.bg.setImageURI(Uri.parse(bgPer));
            } else if (bgInt != -1) {
                binding.bg.setImageResource(bgInt);
            }
        }

        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            binding.bg.setImageURI(imageUri);
        } else if (background != -1) {
            binding.bg.setImageResource(background);
        } else {
            Log.e(TAG,"Background not found");
        }

        if (zipper != -1) {
            binding.zip.setImageResource(zipper);
        } else {
            Log.e(TAG,"Zipper not found");
        }

        if (row != -1) {
            binding.row.setImageResource(row);
        } else {
            Log.e(TAG,"Row not found");
        }

        if (sound_zipper == -1) {
            Log.e(TAG,"Sound zipper not found");
        }

        if (sound_open == -1) {
            Log.e(TAG,"Sound open not found");
        }
    }

    @Override
    public void bindView() {

        binding.btnApply.setOnClickListener(v -> applyAll());

        binding.ivBack.setOnClickListener(v -> {
            confirmDiscard(1);
        });
        binding.ivHome.setOnClickListener(v -> {
            confirmDiscard(2);
        });
        binding.btnBg.setOnClickListener(v -> startActivity(new Intent(this, BackgroundActivity.class)));
        binding.btnRow.setOnClickListener(v -> startActivity(new Intent(this, RowActivity.class)));
        binding.btnSound.setOnClickListener(v -> startActivity(new Intent(this, SoundActivity.class)));
        binding.btnZip.setOnClickListener(v -> startActivity(new Intent(this, ZipperActivity.class)));
    }

    private void confirmDiscard(int back) {
        Dialog dialog = new Dialog(this);
        DialogDiscardBinding bindingDiscard = DialogDiscardBinding.inflate(getLayoutInflater());
        dialog.setContentView(bindingDiscard.getRoot());
        Window window = dialog.getWindow();
        if (window != null) {
            window.setGravity(Gravity.CENTER);
            window.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        bindingDiscard.tvCancel.setOnClickListener(v -> dialog.dismiss());
        bindingDiscard.tvDiscard.setOnClickListener(v -> {
            if (back == 1){
                finish();
            } else if (back == 2) {
                startActivity(new Intent(this, MainActivity.class));
                finishAffinity();
            }
        });

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog.show();

    }

    private void applyAll() {
        if (imageUriString != null){
            SPUtils.setInt(this, SPUtils.BG, -1);
            SPUtils.setString(this, SPUtils.BG_PER, imageUriString);

        }else {
            SPUtils.setInt(this, SPUtils.BG, background);
            SPUtils.setString(this, SPUtils.BG_PER, null);
        }

        SPUtils.setInt(this, SPUtils.ZIPPER, zipper);
        SPUtils.setInt(this, SPUtils.ROW, row);
        SPUtils.setInt(this, SPUtils.ROW_RIGHT, row_r);
        SPUtils.setInt(this, SPUtils.ROW_LEFT, row_l);
        SPUtils.setInt(this, SPUtils.SOUND_ZIPPER, sound_zipper);
        SPUtils.setInt(this, SPUtils.SOUND_OPEN, sound_open);
        startActivity(new Intent(this, SuccessfullyActivity.class));
    }

    @Override
    public void onBack() {
        confirmDiscard(1);
    }
}
