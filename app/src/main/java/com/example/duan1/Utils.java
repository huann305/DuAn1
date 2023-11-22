package com.example.duan1;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Utils {
    public static final String CUSTOM = "custom";
    public static final String SHOP = "shop";
    public static final String TYPE = "type";
    public static final String EMAIL = "email";
    public static final String ROLE = "role";
    public static final String QR = "qr";
    public static final String REMEMBER_PASSWORD = "remember_password";

}
