//package com.reactws;
//
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.View;
//
//public class PickerActivity extends AppCompatActivity implements View.OnClickListener {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_picker);
//
//        findViewById(R.id.ok).setOnClickListener(this);
//        findViewById(R.id.cancel).setOnClickListener(this);
//    }
//
//    @Override
//    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.ok:
//                setResult(RESULT_OK);
//                finish();
//                break;
//            case R.id.cancel:
//                setResult(RESULT_CANCELED);
//                finish();
//                break;
//        }
//    }
//}
