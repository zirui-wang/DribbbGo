package io.zirui.dribbbgo.dribbble;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;


import java.io.IOException;
import java.util.List;

import io.zirui.dribbbgo.model.Shot;
import io.zirui.dribbbgo.model.User;
import io.zirui.dribbbgo.utils.ModelUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class Dribbble {

    public static final int COUNT_PER_PAGE = 12;

    private static final String TAG = "Dribbble API";

    private static final String API_URL = "https://api.dribbble.com/v2/";
    
    private static final String KEY_ACCESS_TOKEN = "access_token";
    private static final String SP_AUTH = "auth";
    private static final String USER_END_POINT = API_URL + "user";
    private static final String SHOTS_END_POINT = API_URL + "user/shots";

    private static final TypeToken<User> USER_TYPE = new TypeToken<User>(){};;
    private static final TypeToken<List<Shot>> SHOT_LIST_TYPE = new TypeToken<List<Shot>>(){};

    private static OkHttpClient client = new OkHttpClient();

    private static String accessToken;
    private static User user;

    public static void init(@NonNull Context context) {
        accessToken = loadAccessToken(context);
        if (accessToken != null){
            user = loadUser(context);
        }
    }

    private static User loadUser(Context context) {
        return null;
    }

    public static String loadAccessToken(@NonNull Context context) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(
                SP_AUTH, Context.MODE_PRIVATE);
        return sp.getString(KEY_ACCESS_TOKEN, null);
    }

    public static boolean isLoggedIn() {
        System.out.print(accessToken != null);
        return accessToken != null;
    }

    public static void login(Context context, String accessToken) throws IOException, JsonSyntaxException {
        Dribbble.accessToken = accessToken;
        storeAccessToken(context, accessToken);
        Dribbble.user = getUser();
        storeUser(context, user);
    }

    private static void storeUser(Context context, User user) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(
                SP_AUTH, Context.MODE_PRIVATE);
        sp.edit().putString(KEY_ACCESS_TOKEN, null).apply();
    }

    public static void storeAccessToken(@NonNull Context context, @Nullable String token) {
        SharedPreferences sp = context.getApplicationContext().getSharedPreferences(
                SP_AUTH, Context.MODE_PRIVATE);
        sp.edit().putString(KEY_ACCESS_TOKEN, token).apply();
    }

    private static User getUser() throws IOException {
        return parseResponse(makeGetRequest(USER_END_POINT), USER_TYPE);
    }

    private static Response makeGetRequest(String url) throws IOException {
        Request request = authRequestBuilder(url).build();
        return makeRequest(request);
    }

    private static Request.Builder authRequestBuilder(String url) {
        System.out.println("---------------" + accessToken);
        return new Request.Builder()
                .addHeader("Authorization", "Bearer " + accessToken)
                .url(url);
    }

    private static Response makeRequest(Request request) throws IOException {
        Response response = client.newCall(request).execute();
        return response;
    }

    private static <T> T parseResponse(Response response,
                                       TypeToken<T> typeToken) throws IOException, JsonSyntaxException {
        String responseString = response.body().string();
        System.out.println("---------------" + responseString);
        Log.d(TAG, responseString);
        return ModelUtils.toObject(responseString, typeToken);
    }

    public static User getCurrentUser() {
        return user;
    }

    public static List<Shot> getShots(int page) throws IOException, JsonSyntaxException {
        String url = SHOTS_END_POINT + "?page=" + page;
        return parseResponse(makeGetRequest(url), SHOT_LIST_TYPE);
    }
}
