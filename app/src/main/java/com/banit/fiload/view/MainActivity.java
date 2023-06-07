package com.banit.fiload.view;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.banit.fiload.databinding.ActivityMainBinding;
import com.banit.fiload.utils.ActivityUtils;
import com.banit.fiload.utils.DialogUtils;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Context mContext;

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
            }
        });
        binding.txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogUtils.toast("Still Under development. Just join", mContext);
            }
        });
    }
}