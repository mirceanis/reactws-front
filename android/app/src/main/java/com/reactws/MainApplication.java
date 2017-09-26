package com.reactws;

import android.content.Intent;

import com.facebook.react.ReactPackage;
import com.reactnativenavigation.NavigationApplication;
import com.reactnativenavigation.controllers.ActivityCallbacks;

import java.util.Arrays;
import java.util.List;

public class MainApplication extends NavigationApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        setActivityCallbacks(new ActivityCallbacks() {
            @Override
            public void onActivityResult(int requestCode, int resultCode, Intent data) {
                getReactGateway().onActivityResult(requestCode, resultCode, data);
            }
        });
    }

    @Override
    public boolean isDebug() {
        // Make sure you are using BuildConfig from your own application
        return BuildConfig.DEBUG;
    }

    protected List<ReactPackage> getPackages() {
        // Add additional packages you require here
        // No need to add RnnPackage and MainReactPackage
        return Arrays.<ReactPackage>asList(
                // eg. new VectorIconsPackage()
                new CamPackage()
        );
    }

    @Override
    public List<ReactPackage> createAdditionalReactPackages() {
        return getPackages();
    }


}