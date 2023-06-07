package com.banit.fiload.utils;


import android.content.Context;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.view.ContextThemeWrapper;

import com.banit.fiload.R;
import com.sanj.besid.exception.BESIDException;
import com.sanj.besid.info.BESIDInfoDialog;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class DialogUtils {

    // Toast message utility
    public static void toast(String message, Context context) {
        Toast.makeText(context.getApplicationContext(), message, Toast.LENGTH_LONG).show();
    }

    // Error dialog without an action
    public static void errorDialog(String title, String message, Context context) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText(title);
        dialog.setContentText(message);
        dialog.setCancelable(false);
        dialog.show();
    }

    // Error dialog with an action
    public static void errorDialog(String title, String message, Context context, Runnable action) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE);
        dialog.setTitleText(title);
        dialog.setContentText(message);
        dialog.setConfirmClickListener(sweetAlertDialog -> {
            sweetAlertDialog.dismiss();
            action.run();
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    // Warning dialog
    public static void warningDialog(String title, String message, Context context) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.WARNING_TYPE);
        dialog.setTitleText(title);
        dialog.setContentText(message);
        dialog.setConfirmText("Okay");
        dialog.setCancelable(false);
        dialog.show();
    }

    // Success dialog with an action
    public static void successDialog(String title, String message, Context context, Runnable action) {
        SweetAlertDialog dialog = new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitleText(title);
        dialog.setContentText(message);
        dialog.setConfirmText("Okay");
        dialog.setConfirmClickListener(sweetAlertDialog -> {
            sweetAlertDialog.dismiss();
            action.run();
        });
        dialog.setCancelable(false);
        dialog.show();
    }

    // Info message dialog with an action
    public static void infoMessage(String title, String message, Context context, BESIDInfoDialog.BESIDInfoDialogInterface.OnClickListener listener) throws BESIDException {
        new BESIDInfoDialog.Builder(context)
                .setMessage(message)
                .setTitle(title)
                .setCancelable(true)
                .setActionButton("Okay, I understand", listener)
                .build()
                .show();
    }

    // Confirmation dialog with positive and negative actions
    public static void confirmationDialog(String title, String message, Context context, Runnable actionPositive, Runnable actionNegative) {
        AlertDialog alertDialog;
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(new ContextThemeWrapper(context, R.style.MyAlertDialogTheme));
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle(title);
        alertBuilder.setMessage(message);
        alertBuilder.setPositiveButton("Proceed", (dialog, which) -> {
            dialog.dismiss();
            actionPositive.run();
        });
        alertBuilder.setNegativeButton("Cancel", (dialog, which) -> {
            dialog.dismiss();
            actionNegative.run();
        });
        alertDialog = alertBuilder.create();
        alertDialog.show();
    }
}
