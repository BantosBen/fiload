package com.banit.fiload.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.banit.fiload.databinding.ActivityMainBinding;
import com.banit.fiload.utils.ActivityUtils;
import com.banit.fiload.utils.DialogUtils;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Context mContext;

    private int REQUEST_PERMISSION = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        mContext = this;
        setContentView(binding.getRoot());

        binding.btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.loadActivity(MainActivity.this, DashboardActivity.class, null);
                finish();
            }
        });
        binding.txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.toast("Still Under development. Just join", mContext);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        requestPermissionAndOpenFilePicker();
    }

    private void requestPermissionAndOpenFilePicker() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
        }
    }
}