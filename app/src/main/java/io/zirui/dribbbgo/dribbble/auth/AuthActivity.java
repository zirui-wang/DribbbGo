package io.zirui.dribbbgo.dribbble.auth;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.zirui.dribbbgo.R;

public class AuthActivity extends AppCompatActivity {

    public static final String KEY_URL = "key_url";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }
}
