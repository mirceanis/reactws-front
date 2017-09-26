package com.reactws;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

/**
 * Created by mir on 22/09/2017.
 * -
 */
class CamModule extends ReactContextBaseJavaModule {

    private static final int IMAGE_PICKER_REQUEST = 15612;

    public CamModule(ReactApplicationContext reactContext) {
        super(reactContext);
        // Add the listener for `onActivityResult`
        reactContext.addActivityEventListener(mActivityEventListener);
    }

    @Override
    public String getName() {
        return "CamModule";
    }

    @ReactMethod
    public void show(String message) {
        Log.d("native", "gigele, suntem bastinasi");
        Toast.makeText(getReactApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @ReactMethod
    public void resolvePromiseNow(String message, Promise promise) {
        Log.d("native", "gigele, promitem ca suntem bastinasi");
        promise.resolve("message: " + message);
    }

    @ReactMethod
    public void resolvePromiseLater(final String message, final Promise promise) {
        Log.d("native", "gigele, promitem ca suntem bastinasi mai tarziu");
        new Handler(getReactApplicationContext().getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("native", "gigele, am promis");
                promise.resolve("later message: " + message);
            }
        }, 5000);

    }

    @ReactMethod
    public void checkApplicant(final Promise promise) {
        Activity currentActivity = getCurrentActivity();

        if (currentActivity == null) {
            promise.reject("E_ACTIVITY_DOES_NOT_EXIST", "Activity doesn't exist");
            return;
        }

        // Store the promise to resolve/reject when picker returns data
        mPickerPromise = promise;

        try {
            Intent intent = new Intent(getReactApplicationContext(), PickerActivity.class);
            currentActivity.startActivityForResult(intent, IMAGE_PICKER_REQUEST);
        } catch (Exception e) {
            mPickerPromise.reject("E_FAILED_TO_SHOW_PICKER", e);
            mPickerPromise = null;
        }
    }

    private Promise mPickerPromise;

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {

        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent intent) {
            if (requestCode == IMAGE_PICKER_REQUEST) {
                if (mPickerPromise != null) {
                    if (resultCode == Activity.RESULT_CANCELED) {
                        mPickerPromise.reject("E_PICKER_CANCELLED", "Picker was cancelled sucka!");
                    } else if (resultCode == Activity.RESULT_OK) {
                        mPickerPromise.resolve("gigele, all OK boss");
                    }

                    mPickerPromise = null;
                }
            }
        }
    };

}
