package com.example.zipperlock.util;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
    public static final String PREFERENCE = "PREFERENCE";
    public static final String SHARED_PREFS_NAME = "SHARED_PREFS_NAME";
    public static final String STORAGE = "STORAGE";
    public static final String NOTIFICATION = "NOTIFICATION";
    public static final String BG = "BG";
    public static final String ZIPPER = "ZIPPER";
    public static final String ROW = "ROW";
    public static final String ROW_RIGHT = "ROW_RIGHT";
    public static final String ROW_LEFT = "ROW_LEFT";
    public static final String WALLPAPER = "WALLPAPER";
    public static final String SOUND_OPEN = "SOUND_OPEN";
    public static final String SOUND_ZIPPER = "SOUND_ZIPPER";
    public static final String BG_PER = "BG_PER";


    public static SharedPreferences getPref(Context context) {
        return context.getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void setString(Context context, String str, String str2) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCE, 0).edit();
        edit.putString(str, str2);
        edit.apply();
    }

    public static String getString(Context context, String str, String str2) {
        return context.getSharedPreferences(PREFERENCE, 0).getString(str, str2);
    }

    public static void setLong(Context context, String str, long i) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCE, 0).edit();
        edit.putLong(str, i);
        edit.apply();
    }

    public static long getLong(Context context, String str, long i) {
        return context.getSharedPreferences(PREFERENCE, 0).getLong(str, i);
    }

    public static void setInt(Context context, String str, int i) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCE, 0).edit();
        edit.putInt(str, i);
        edit.apply();
    }

    public static int getInt(Context context, String str, int i) {
        return context.getSharedPreferences(PREFERENCE, 0).getInt(str, i);
    }

    public static void setBoolean(Context context, String str, boolean b) {
        SharedPreferences.Editor edit = context.getSharedPreferences(PREFERENCE, 0).edit();
        edit.putBoolean(str, b);
        edit.apply();
    }

    public static boolean getBoolean(Context context, String str, boolean b) {
        return context.getSharedPreferences(PREFERENCE, 0).getBoolean(str, b);
    }
}
