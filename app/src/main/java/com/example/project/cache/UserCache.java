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
}
