package com.banit.fiload.utils;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.banit.fiload.R;

import java.io.Serializable;
import java.util.HashMap;

public class ActivityUtils {

    public static void loadActivity(AppCompatActivity activity, Class<?> cls, HashMap<String, String> dataString) {
        Intent intent = new Intent(activity, cls);

        if (dataString != null && !dataString.isEmpty()) {
            for (String key : dataString.keySet()) {
                intent.putExtra(key, dataString.get(key));
            }
        }

        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}

