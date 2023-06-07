package com.banit.fiload.utils;

import com.banit.fiload.model.MyColor;

import java.util.ArrayList;

public class ColorsUtils {
    public static ArrayList<MyColor> getColors() {
        ArrayList<MyColor> colorsList = new ArrayList<>();

        colorsList.add(new MyColor("#34DEDE", "#F0FFFF"));
        colorsList.add(new MyColor("#F45656", "#FEEEEE"));
        colorsList.add(new MyColor("#FFDE6C", "#FFFBEC"));
        colorsList.add(new MyColor("#567DF4", "#EEF7FE"));

        return colorsList;
    }
}
