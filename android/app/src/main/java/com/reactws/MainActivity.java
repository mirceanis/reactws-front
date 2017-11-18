//package com.reactws;
//
//import android.content.Intent;
//import android.widget.Toast;
//
//import com.reactnativenavigation.NavigationApplication;
//import com.reactnativenavigation.controllers.SplashActivity;
//
//public class MainActivity extends SplashActivity {
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        // This activity must be coerced into a single instance in the manifest (`launchMode="singleTask"`)
//        // making this callback the place to capture deep links and pass them to react.
//        NavigationApplication.instance.getReactGateway().onNewIntent(intent);
//        NavigationApplication.instance.getActivityCallbacks().onNewIntent(intent);
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        Toast.makeText(this, "Running version:" + BuildConfig.VERSION_NAME + "(" + BuildConfig.VERSION_CODE + ")", Toast.LENGTH_LONG).show();
//        if (NavigationApplication.instance.isReactContextInitialized()) {
//            //in case the app is resumed into an already initialized react-context,
//            // the splash needs to disappear
//            finish();
//        }
//    }
//}
