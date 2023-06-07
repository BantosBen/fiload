package com.banit.fiload.utils;

import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.banit.fiload.R;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

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

    public static String formatNumberWithCommas(String number) {
        try {
            // Parse the input number as a long
            long parsedNumber = Long.parseLong(number);

            // Create a NumberFormat instance with comma separators
            NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.getDefault());

            // Format the number with commas
            return numberFormat.format(parsedNumber);
        } catch (NumberFormatException e) {
            // Handle any parsing errors
            e.printStackTrace();
        }

        // Return the input number as is if there was an error
        return number;
    }

    public static String formatDate(String inputDate) {
        try {
            // Define the input and output date formats
            DateFormat inputDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
            DateFormat outputDateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);

            // Parse the input date string into a Date object
            Date date = inputDateFormat.parse(inputDate);

            // Format the date into the desired output format
            assert date != null;
            return outputDateFormat.format(date);
        } catch (ParseException e) {
            // Handle any parsing errors
            e.printStackTrace();
        }

        // Return the input date as is if there was an error
        return inputDate;
    }

}

