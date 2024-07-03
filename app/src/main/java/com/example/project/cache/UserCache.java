package com.example.project.cache;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.project.model.User;

public class UserCache {

    public static void saveToken(Context context, String accessToken, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("accessToken", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("accessToken", accessToken);
        editor.putString("username", username);
        editor.apply();
    }
    public static void clearToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("accessToken", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("accessToken");
        editor.remove("username");// Xóa giá trị token từ Shared Preferences với key "accessToken"
        editor.apply(); // Áp dụng các thay đổi
    }
    public static String getToken(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("accessToken", context.MODE_PRIVATE);
        String token = sharedPreferences.getString("accessToken", "");
        return token;
    }
    public static String getUser(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("accessToken", context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        return username;
    }
    public static void saveRegister(Context context, String username) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("register", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();
    }
    public static String checkRegister(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("register", context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");
        return username;
    }
    public static void saveNotifyForgot(Context context, String notify) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("forgot", context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("notify", notify);
        editor.apply();
    }
    public static String getNotifyForgot(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("forgot", context.MODE_PRIVATE);
        String notify = sharedPreferences.getString("notify", "");
        return notify;
    }
}