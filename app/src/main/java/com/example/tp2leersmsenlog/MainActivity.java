package com.example.tp2leersmsenlog;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity
{
    private Intent service;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.M
                && checkSelfPermission(Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_SMS},1000);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();

        service = new Intent(this, LeerSms.class);
        startService(service);
    }

    @Override
    protected void onStop()
    {
        super.onStop();

        stopService(service);
    }
}