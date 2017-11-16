package com.reactws;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.BaseActivityEventListener;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.onfido.android.sdk.capture.ExitCode;
import com.onfido.android.sdk.capture.Onfido;
import com.onfido.android.sdk.capture.OnfidoConfig;
import com.onfido.android.sdk.capture.OnfidoFactory;
import com.onfido.android.sdk.capture.upload.Captures;
import com.onfido.api.client.data.Applicant;

/**
 * Created by mir on 22/09/2017.
 * -
 */
class CamModule extends ReactContextBaseJavaModule {

    private static final int ONFIDO_APPLICANT_REQUEST = 43278;
    private final Onfido onfido;

    public CamModule(ReactApplicationContext reactContext) {
        super(reactContext);
        // Add the listener for `onActivityResult`
        reactContext.addActivityEventListener(mActivityEventListener);
        onfido = OnfidoFactory.create(reactContext).getClient();
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

        Applicant applicant = Applicant.builder()
                .withFirstName("John")
                .withLastName("Smith")
                .build();

        final OnfidoConfig config = OnfidoConfig.builder()
                .withToken("test_KsOjtW5G-ROAOebM_NzuE6NNQ6-buNCq")
                .withApplicant(applicant)
                .build();

        mPickerPromise = promise;

        try {
            onfido.startActivityForResult(getCurrentActivity(), ONFIDO_APPLICANT_REQUEST, config);
        } catch (Exception e) {
            Log.e("onfido", "E_FAILED_TO_START_ONFIDO_FLOW", e);
            mPickerPromise.reject("E_FAILED_TO_START_ONFIDO_FLOW", e);
            mPickerPromise = null;
        }

    }

    private Promise mPickerPromise;

    private final ActivityEventListener mActivityEventListener = new BaseActivityEventListener() {

        @Override
        public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
            if (requestCode == ONFIDO_APPLICANT_REQUEST && mPickerPromise != null) {
                onfido.handleActivityResult(resultCode, data, new Onfido.OnfidoResultListener() {
                    @Override
                    public void userCompleted(@NonNull Applicant applicant, @NonNull Captures captures) {
                        if (getCurrentActivity() != null) {
                            Toast.makeText(getCurrentActivity(), "applicant ok: " + applicant.getId(), Toast.LENGTH_SHORT).show();
                            mPickerPromise.resolve(applicant.getId());
                            mPickerPromise = null;
                        }
                    }

                    @Override
                    public void userExited(@NonNull ExitCode exitCode, @NonNull Applicant applicant) {
                        if (getCurrentActivity() != null ) {
                            Toast.makeText(getCurrentActivity(), "applicant quit", Toast.LENGTH_SHORT).show();
                            mPickerPromise.reject("E_ONFIDO_USER_EXITED", "The user has quit onfido flow");
                            mPickerPromise = null;
                        }
                    }
                });

            }
        }
    };

}
