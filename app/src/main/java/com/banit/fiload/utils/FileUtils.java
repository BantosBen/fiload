package com.banit.fiload.utils;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.DownloadManager;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.io.IOException;

public class FileUtils {
    public static String getFileNameFromUri(Uri uri, ContentResolver contentResolver) {
        String[] projection = {MediaStore.Images.Media.DISPLAY_NAME};
        Cursor cursor = contentResolver.query(uri, projection, null, null, null);
        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    int displayNameIndex = cursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME);
                    return cursor.getString(displayNameIndex);
                }
            } finally {
                cursor.close();
            }
        }
        return null;
    }

    public static long getFileSize(File file) throws IOException {
        if (file.exists()) {
            return file.length();
        } else {
            throw new IOException("File does not exist.");
        }
    }

    public static String getFileType(File file) throws IOException {
        if (file.exists()) {
            String fileName = file.getName();
            String extension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
            switch (extension) {
                case "doc":
                case "docx":
                    return "word";
                case "pdf":
                    return "pdf";
                case "jpg":
                case "jpeg":
                case "png":
                case "gif":
                    return "image";
                case "xls":
                case "xlsx":
                    return "excel";
                default:
                    return "other";
            }
        } else {
            throw new IOException("File does not exist.");
        }
    }

}
