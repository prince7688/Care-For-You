package com.example.hp.careforyou;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanItemsActivity extends AppCompatActivity {

    SurfaceView mSurfaceView;
    CameraSource cameraSource;
    String upccode;
    EditText upctext;
    Button upcsummitbutton;
    TextInputLayout til;
   // Toolbar mToolbar;
    Boolean alreadyExecuted = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_items);

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceview);

        CreateCameraSource();

        upctext =(EditText)findViewById(R.id.upctext);
        upcsummitbutton =(Button)findViewById(R.id.summitbutton);
        til = (TextInputLayout) findViewById(R.id.text_input_layout);
        upcbuttonclicklistner();

        android.support.v7.widget.Toolbar toolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setLogo(R.drawable.barcodeimage);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.scanmenu, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
//    }

    private void upcbuttonclicklistner() {

        //upcsummitbutton.setEnabled(true);

            upcsummitbutton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean digitsOnly = TextUtils.isDigitsOnly(upctext.getText());

                    if(upctext.getText()==null || digitsOnly == false || upctext.length()!= 12)
                        til.setError("*Please Enter a Valid UPC Code");
                    else {
                        til.setError(null);
                        upccode = upctext.getText().toString();
                        Intent intent = new Intent(ScanItemsActivity.this, ScanResult.class);
                        intent.putExtra("barcode", upccode);
                        //setResult(CommonStatusCodes.SUCCESS);
                        finish();
                        startActivity(intent);
                    }
                }
            });

    }

    @Override

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 100) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                try {
                    if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    cameraSource.start(mSurfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else {

                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();

            }

        }}//end onRequestPermissionsResult

    private void CreateCameraSource() {

        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();
          cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600, 1024)
                .build();

        mSurfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(ScanItemsActivity.this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ScanItemsActivity.this, new String[] {android.Manifest.permission.CAMERA}, 100);

                    return;
                }
                try {
                    cameraSource.start(mSurfaceView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }



            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

                @Override
                public void receiveDetections (Detector.Detections < Barcode > detections) {

                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                upccode = barcodes.valueAt(0).rawValue;
                if (barcodes.size() > 0 && !alreadyExecuted) {
                    Intent intent = new Intent(ScanItemsActivity.this, ScanResult.class);
                    intent.putExtra("barcode", upccode);
                    //setResult(CommonStatusCodes.SUCCESS);
                    finish();
                    startActivity(intent);
                    alreadyExecuted = true;
                }

            }


        });


    }
}
