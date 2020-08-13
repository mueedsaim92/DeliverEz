package com.deliverez.com.tools;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;


public class PermissionCommon {

    public static boolean getCameraPer(Context context){
        boolean isCameraPermission=false;
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA);
        int result1 = ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (result == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED) {
            isCameraPermission=true;
        }
        return isCameraPermission;
    }

    public static boolean getAudioPer(Context context){
        boolean isAudioPermission=false;
        int result = ContextCompat.checkSelfPermission(context, Manifest.permission.RECORD_AUDIO);
        int result1 = ContextCompat.checkSelfPermission(context,Manifest.permission.READ_EXTERNAL_STORAGE);
        int result2 = ContextCompat.checkSelfPermission(context,Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (result == PackageManager.PERMISSION_GRANTED &&result2 == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED) {
            isAudioPermission=true;
        }
        return isAudioPermission;
    }



}