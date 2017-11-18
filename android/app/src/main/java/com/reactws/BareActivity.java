package com.reactws;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

public class BareActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.bare_metal);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "Running version:" + BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ")", Toast.LENGTH_LONG).show();
    }
}
