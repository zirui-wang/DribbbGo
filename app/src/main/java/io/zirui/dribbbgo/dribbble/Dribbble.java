package io.zirui.dribbbgo.dribbble;

import android.content.Context;
import android.content.SharedPreferences;

import io.zirui.dribbbgo.model.User;


public class Dribbble {

    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String SP_AUTH = "auth";

    private static String accessToken;
    private static User user;

    public static void init(Context context) {
        accessToken = loadAccessToken(context);
        if (accessToken != null){
            user = loadUser(context);
        }
    }

    private static User loadUser(Context context) {
        return null;
    }

    private static String loadAccessToken(Context context) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(
                SP_AUTH, Context.MODE_PRIVATE);
        return sp.getString(KEY_ACCESS_TOKEN, null);
    }

    public static boolean isLoggedIn() {
        return accessToken != null;
    }
}
