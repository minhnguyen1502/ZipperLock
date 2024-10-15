package com.example.zipperlock.ui.apply;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
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
    private boolean isApply = false;
   private int background;
   private int zipper;
   private int row;
   private int row_r;
   private int row_l;
   private int currentBackground;
   private int currentZipper;
   private int currentRow;
   private int currentRowRight ;
   private int currentRowLeft  ;
    private int sound_open;
    private int sound_zipper;
    private String imageUriString;
    @Override
    public ActivityApplyBinding getBinding() {
        return ActivityApplyBinding.inflate(getLayoutInflater());
    }
    @Override
    public void initView() {
        Intent intent = getIntent();
        imageUriString = intent.getStringExtra("imageUri");
        background  = intent.getIntExtra("background", SPUtils.getInt(this, SPUtils.BG, -1));
        zipper      = intent.getIntExtra("zipper", -1);
        sound_open  = intent.getIntExtra("sound_zipper", -1);
        sound_zipper    = intent.getIntExtra("sound_open", -1);
        row         = intent.getIntExtra("row", -1);
        row_r       = intent.getIntExtra("row_r", -1);
        row_l       = intent.getIntExtra("row_l", -1);
        if (imageUriString != null) {
            Uri imageUri = Uri.parse(imageUriString);
            binding.bg.setImageURI(imageUri);
        } else if (background != -1) {
            binding.bg.setImageResource(background);
        } else {
            Toast.makeText(this, "Background not found", Toast.LENGTH_SHORT).show();
        }
        if (zipper != -1){
            binding.zip.setImageResource(zipper);
        }else {
            Toast.makeText(this, "Zipper not found", Toast.LENGTH_SHORT).show();
        }
        if (row != -1){
            binding.row.setImageResource(row);
        }else {
            Toast.makeText(this, "Row not found", Toast.LENGTH_SHORT).show();
        }

        if (sound_zipper == -1){
            Toast.makeText(this, "Sound zipper not found", Toast.LENGTH_SHORT).show();

        }
        if (sound_open == -1){
            Toast.makeText(this, "Sound open not found", Toast.LENGTH_SHORT).show();

        }
        currentBackground = SPUtils.getInt(this, SPUtils.BG, -1);
        currentZipper= SPUtils.getInt(this, SPUtils.ZIPPER, -1);
        currentRow= SPUtils.getInt(this, SPUtils.ROW, -1);
        currentRowRight = SPUtils.getInt(this, SPUtils.ROW_RIGHT, -1);
        currentRowLeft  = SPUtils.getInt(this, SPUtils.ROW_LEFT, -1);
    }

    @Override
    public void bindView() {

        binding.btnApply.setOnClickListener(v -> applyAll());

        binding.ivBack.setOnClickListener(v -> {
            onBack();
        });
        binding.ivHome.setOnClickListener(v -> {
                onBack();
        });
        binding.btnBg.setOnClickListener(v -> startActivity(new Intent(this, BackgroundActivity.class)));
        binding.btnRow.setOnClickListener(v -> startActivity(new Intent(this, RowActivity.class)));
        binding.btnSound.setOnClickListener(v -> startActivity(new Intent(this, SoundActivity.class)));
        binding.btnZip.setOnClickListener(v -> startActivity(new Intent(this, ZipperActivity.class)));
    }

    private void confirmDiscard() {
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
        bindingDiscard.tvDiscard.setOnClickListener(v -> finish());

        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        dialog.show();

    }

    private void applyAll() {
        SPUtils.setInt(this, SPUtils.BG,background);
        SPUtils.setString(this, SPUtils.BG,imageUriString);
        SPUtils.setInt(this, SPUtils.ZIPPER,zipper);
        SPUtils.setInt(this, SPUtils.ROW,row);
        SPUtils.setInt(this, SPUtils.ROW_RIGHT,row_r);
        SPUtils.setInt(this, SPUtils.ROW_LEFT,row_l);
        SPUtils.setInt(this, SPUtils.SOUND_ZIPPER,sound_zipper);
        SPUtils.setInt(this, SPUtils.SOUND_OPEN,sound_open);
        startActivity(new Intent(this, SuccessfullyActivity.class));
    }

    @Override
    public void onBack() {
       confirmDiscard();
    }
}
