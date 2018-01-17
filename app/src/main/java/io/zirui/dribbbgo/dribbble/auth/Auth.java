package io.zirui.dribbbgo.dribbble.auth;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

public class Auth {

    private static final int REQ_CODE = 100;

    private static final String KEY_CLIENT_ID = "client_id";
    private static final String KEY_REDIRECT_URI = "redirect_uri";
    private static final String KEY_SCOPE = "scope";

    private static final String CLIENT_ID = "bc3dcd6f2b089b7ff1b8a9bbec88226467488f53f2a8bb9ec0c3c22359d5ae1e";

    private static final String CLIENT_SECRET = "c101a9c524f2935b0fd981c0153363b9812962fd8f25236e3b9abadf3df80f1e";

    private static final String SCOPE = "public+write";

    private static final String URI_AUTHORIZE = "https://dribbble.com/oauth/authorize";
    public static final String REDIRECT_URI = "http://www.google.com";

    public static void openAuthActivity(Activity activity) {
        Intent intent = new Intent(activity, AuthActivity.class);
        intent.putExtra(AuthActivity.KEY_URL, getAuthorizeUrl());
        activity.startActivityForResult(intent, REQ_CODE);
    }

    private static String getAuthorizeUrl() {
        String url = Uri.parse(URI_AUTHORIZE)
                .buildUpon()
                .appendQueryParameter(KEY_CLIENT_ID, CLIENT_ID)
                .build()
                .toString();

        url += "&" + KEY_REDIRECT_URI + "=" + REDIRECT_URI;
        url += "&" + KEY_SCOPE + "=" + SCOPE;

        return url;
    }

}
