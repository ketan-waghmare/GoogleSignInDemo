package com.example.googlesignindemo.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.googlesignindemo.BuildConfig;
import com.example.googlesignindemo.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;


public class Utils {

    private static int quality = 60;
    private static int density = 80;
    public static final String EMAIL_PATTERN = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@" +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\." +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
            ")+";
    static Matcher matcher;

    public static final int REQUEST_PERMISSIONS_ALL = 600;
    public static final int REQUEST_BLUETOOTH = 601;
    public static final int REQUEST_WIFI = 602;
    public static int screenHeight;

    public static int screenWidth;

    public static Typeface regularFont;
    public static Typeface lightFont;
    public static Typeface boldFont;
    public static Typeface CHALKBOARD_SE_REGULAR_FONT;
    public static Typeface CHALKBOARD_SE_BOLD_FONT;
    public static Typeface CHALKBOARD_SE_LIGHT_FONT;

    public static PopupWindow mAddTaskPopupWindow;
    public static PopupWindow mPopupWindow;
    public int REQUEST_EXTERNAL_LOCATION = 592;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    public String[] PERMISSIONS_LOCATION = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    public String[] PERMISSIONS_BLUETOOTH = {
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN
    };
    public String[] PERMISSIONS_WIFI = {
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE
    };
    public String[] PERMISSIONS_ALL = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CAMERA,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE

    };
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 3;

    private static Utils utils;
    private int MY_PERMISSIONS_REQUEST_READ_PHONE_STATE = 565;
    private int MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION = 566;

    private Utils() {
    }

    public static Utils getInstance() {
        if (utils == null) {
            utils = new Utils();
        }
        return utils;
    }
    //Screen Size Setting For All Devices by HARI
    public static void setDimensions(Context _context) {
        try {
            DisplayMetrics displaymetrics = new DisplayMetrics();
            ((Activity) _context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
            screenWidth = displaymetrics.widthPixels;
            screenHeight = displaymetrics.heightPixels;
//            regularFont = Typeface.createFromAsset(_context.getAssets(), Constants.Poppins_Font);
//            lightFont = Typeface.createFromAsset(_context.getAssets(), Constants.Poppins_Font_Light);
//            boldFont = Typeface.createFromAsset(_context.getAssets(), Constants.Poppins_Font_Bold);
//            CHALKBOARD_SE_BOLD_FONT = Typeface.createFromAsset(_context.getAssets(), Constants.CHALKBOARD_SE_BOLD_FONT);
//            CHALKBOARD_SE_REGULAR_FONT = Typeface.createFromAsset(_context.getAssets(), Constants.CHALKBOARD_SE_REGULAR_FONT);
//            CHALKBOARD_SE_LIGHT_FONT = Typeface.createFromAsset(_context.getAssets(), Constants.CHALKBOARD_SE_LIGHT_FONT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideKeyboard(Activity activity, View view) {
        // Check if no view has focus:
        if (view == null) {
            view = activity.getCurrentFocus();
        }
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public static void hideKeyboardContext(Context context, View view) {
        // Check if no view has focus:

       // if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        //}
    }

    //For InterNet Checking
    public static boolean isOnline(Context _Context) {
        ConnectivityManager cm = (ConnectivityManager) _Context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static boolean isValidEmail(CharSequence target) {
        if (target == null) {
            return false;
        } else {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
        }
    }

    public static boolean isValidMobile(String phone) {
        if (phone == null) {
            return false;
        } else {
            return android.util.Patterns.PHONE.matcher(phone).matches();
        }
    }

    /*public static void showCustomToast(String toastMsg, Activity _activity) {
        try {
            LayoutInflater inflater = from_Context(_activity);
            View layout;
            if (inflater != null) {
                layout = inflater.inflate(R.layout.custom_toast, (ViewGroup) _activity.findViewById(R.id.custom_toast_layout_id));
                TextView tv = (TextView) layout.findViewById(R.id.text);
                // The actual toast generated here.
                Toast toast = new Toast(_activity);
                tv.setText(toastMsg);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(layout);
//                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                Toast.makeText(_activity, "" + toastMsg, Toast.LENGTH_SHORT).show();
            }
        } catch (AssertionError | Exception e) {
            Log.w("HARI-->DEBUG", e);
            Toast.makeText(_activity, "" + toastMsg, Toast.LENGTH_SHORT).show();
        }
    }*/

    /**
     * Obtains the LayoutInflater from the given context.
     */
    private static LayoutInflater from_Context(Context context) {
        LayoutInflater layoutInflater = null;
        try {
            if (context != null) {
                layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }

            if (layoutInflater == null) {
                throw new AssertionError("LayoutInflater not found.");
            }
        } catch (Exception e) {
            Log.w("HARI-->DEBUG", e);
            layoutInflater = null;
        }
        return layoutInflater;
    }

    public static void statusBarSetup(Activity _activity) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                View decor = _activity.getWindow().getDecorView();
                decor.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                _activity.getWindow().setStatusBarColor(_activity.getResources().getColor(R.color.colorPrimaryDark));
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    _activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    _activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    _activity.getWindow().setStatusBarColor(_activity.getResources().getColor(R.color.colorPrimaryDark));
                }
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }
    public boolean getPhoneStatePermission(Activity context) {
// Here, thisActivity is the current activity
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.READ_PHONE_STATE},
                    MY_PERMISSIONS_REQUEST_READ_PHONE_STATE);
            return false;
        } else {
            return true;
        }
    }
    public boolean getCoarseLocationPermission(Activity context) {
// Here, thisActivity is the current activity
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_ACCESS_COARSE_LOCATION);
            return false;
        } else {
            return true;
        }
    }


    public static boolean verifyStoragePermissions(Activity activity) {
        // Check if we have read or write permission
        int writePermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        int readPermission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE);

        if (writePermission != PackageManager.PERMISSION_GRANTED || readPermission != PackageManager.PERMISSION_GRANTED ) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
            return false;
        } else {
            return true;
        }
    }

    public static String getFilename(Context context, String extention) {
        String filepath = Environment.getExternalStorageDirectory().getPath() + "/"
                + context.getResources().getString(R.string.app_name);
        File file = new File(filepath);

        if (!file.exists()) {
            file.mkdirs();
        }
        String timeStamp = new SimpleDateFormat("ddMMyyyy").format(Calendar.getInstance().getTime());
        return (file.getAbsolutePath() + "/" + timeStamp + extention);
    }

    public static void visibleLabelOfSpinner(Spinner spinner, TextView textView) {


        if (spinner.getSelectedItemPosition() == 0) {
            textView.setVisibility(View.INVISIBLE);
        } else {
            textView.setVisibility(View.VISIBLE);
        }

    }

    public static void visibleLabel(EditText editText, TextView textView, boolean hasFocus) {
        if (hasFocus) {
            textView.setVisibility(View.VISIBLE);
        } else {
            if (editText.getText().length() > 0) {
                textView.setVisibility(View.VISIBLE);
            } else {
                textView.setVisibility(View.INVISIBLE);
            }
        }
    } public static void visibleLabelForTextView(TextView textView1, final TextView textView) {
        textView1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i2 > 0) {
                    textView.setVisibility(View.VISIBLE);
                }else {
                    textView.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }


    public void saveToSharedPreference(Context context, String key, Object value) {
        String localValue;
        if (value != null && context != null) {
            localValue = value.toString();
        } else {
            localValue = null;
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, localValue);
        editor.apply();
    }

    /**
     * Get String value from Shared Preference
     *
     * @param context The context of the current state
     * @param key     The name of the preference to retrieve
     * @return Value for the given preference if exist else null.
     */
    public String getFromSharedPreference(Context context, String key) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(key, null);
    }

    public static boolean isSimSupport(Context context)
    {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);  //gets the current TelephonyManager
        return !(tm.getSimState() == TelephonyManager.SIM_STATE_ABSENT);

    }

    public boolean giveLocationPermission(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_LOCATION,
                    REQUEST_EXTERNAL_LOCATION
            );
            return false;
        } else {
            return true;
        }
    }
    public boolean giveBluetoothPermission(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_BLUETOOTH,
                    REQUEST_BLUETOOTH
            );
            return false;
        } else {
            return true;
        }
    }
    public boolean giveWifiPermission(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_WIFI,
                    REQUEST_WIFI
            );
            return false;
        } else {
            return true;
        }
    }

    public static boolean getCameraPermission(Activity context) {
// Here, thisActivity is the current activity
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
            return false;
        } else {
            return true;
        }
    }

    public boolean giveTestAppAllPermission(Activity activity) {
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED&& ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH) != PackageManager.PERMISSION_GRANTED&& ActivityCompat.checkSelfPermission(activity, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_WIFI_STATE) != PackageManager.PERMISSION_GRANTED&& ActivityCompat.checkSelfPermission(activity, Manifest.permission.CHANGE_WIFI_STATE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_ALL,
                    REQUEST_PERMISSIONS_ALL
            );
            return false;
        } else {
            return true;
        }
    }

    public String getIMEI(Activity activity, int slotID) {
        try {
            TelephonyManager mTelephonyMgr = (TelephonyManager) activity
                    .getSystemService(Context.TELEPHONY_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return mTelephonyMgr.getDeviceId(slotID);
            } else {
                return mTelephonyMgr.getDeviceId();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    static SecureRandom rnd = new SecureRandom();

    public static String randomString(int len){
        StringBuilder sb = new StringBuilder( len );
        for( int i = 0; i < len; i++ )
            sb.append( AB.charAt( rnd.nextInt(AB.length()) ) );
        return sb.toString();
    }


   /* public static KProgressHUD showProgressDialog(Context _activity, String loadingText) {
        try {
            KProgressHUD hud = KProgressHUD.create(_activity)
                    .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                    .setLabel(loadingText)
                    .setDimAmount(0.6f)
                    .setCancellable(false);
            hud.show();
            return hud;
        } catch (Exception ee) {
            ee.printStackTrace();
        }
        return null;
    }

    public static void hideProgressDialog(KProgressHUD hud) {
        if (hud != null && hud.isShowing()) {
            hud.dismiss();
        }
        if (hud != null) {
            hud = null;
        }
    }*/


    public static boolean isSDCardValid(Context context, boolean showToast) {
        String state = Environment.getExternalStorageState();

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }

        if (Environment.MEDIA_REMOVED.equals(state)) {
            if (showToast) {
                Toast.makeText(context, "SD card not present",
                        Toast.LENGTH_LONG).show();
            }

            return false;
        }

        if (Environment.MEDIA_UNMOUNTED.equals(state)) {
            if (showToast) {
                Toast.makeText(context, "SD card not mounted",
                        Toast.LENGTH_LONG).show();
            }

            return false;
        }

        if (showToast) {
            Toast.makeText(
                    context,
                    "The SD card in the device is in '" + state
                            + "' state, and cannot be used.", Toast.LENGTH_LONG)
                    .show();
        }

        return false;
    }


    public void copyFile(Context context, String src, String dst) {
        if (TextUtils.equals(src, dst)) {
            if (BuildConfig.DEBUG) {
                Log.w(TAG, "Source (" + src + ") and destination (" + dst
                        + ") are the same. Skipping file copying.");
            }
            return;
        }

        FileInputStream in = null;
        FileOutputStream out = null;

        try {
            in = new FileInputStream(src);
            out = new FileOutputStream(dst);

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } catch (IOException e) {
            Toast.makeText(
                    context,
                    "Failed to copy " + src + " to " + dst + ": "
                            + e.getMessage(), Toast.LENGTH_LONG).show();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    if (BuildConfig.DEBUG) {
                        Log.w(TAG,
                                "Ignored the exception caught while closing input stream for "
                                        + src + ": " + e.getMessage(), e);
                    }
                }
            }

            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    if (BuildConfig.DEBUG) {
                        Log.w(TAG,
                                "Ignored the exception caught while closing output stream for "
                                        + dst + ": " + e.getMessage(), e);
                    }
                }
            }
        }
    }
    public String getImageFullPath(Object path, String url) {
        String imagePath = null;
        if (path instanceof String) {
            imagePath = (String) path;
            if (!imagePath.contains("file:///")) {
                imagePath = "file://" + imagePath;
            }
        } else if (path instanceof Long) {
            imagePath = url + path;
        }
        return imagePath;
    }

    /**
     * Call to a given phone number
     *
     * @param context
     * @param phoneNo
     */
    @SuppressLint("MissingPermission")
    public void callANumber(Context context, String phoneNo) {
//        <uses-permission android:name="android.permission.CALL_PHONE" />
        Intent callIntent = new Intent(Intent.ACTION_CALL);
        callIntent.setData(Uri.parse("tel:" + phoneNo));
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    (Activity) context,
                    new String[]{Manifest.permission.CALL_PHONE},
                    2);
            return;
        }
        context.startActivity(callIntent);
    }


    /**
     * @param context
     * @param to      email id
     * @param subject
     * @param body
     */
    public void sendEmail(Context context, String to, String subject, String body) {
        if (TextUtils.isEmpty(subject)) {
            subject = "";
        }
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto", to, null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        emailIntent.putExtra(Intent.EXTRA_TEXT, body);
        context.startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }

    /**
     * Check for network availability
     *
     * @param context Activity context
     * @return true if network available else false.
     */
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        try {
            Toast.makeText(context, "Network not available", Toast.LENGTH_LONG)
                    .show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean isConnectingToInternet(Context appContext) {
        // Method to check internet connection
        ConnectivityManager connectivity = (ConnectivityManager) appContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
        }
        return false;
    }



    public static boolean isEmailId(EditText editText) {
        // method to check edit text is fill or no
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(editText.getText().toString().trim());
        if (matcher.matches()) {
            return false;
        }
        return true;
    }

    public static String getPath(final Context context, final Uri uri) {

        // DocumentProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, uri)) {

            if (isExternalStorageDocument(uri)) {// ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                String storageDefinition;


                if("primary".equalsIgnoreCase(type)){

                    return Environment.getExternalStorageDirectory() + "/" + split[1];

                } else {

                    if(Environment.isExternalStorageRemovable()){
                        storageDefinition = "EXTERNAL_STORAGE";

                    } else{
                        storageDefinition = "SECONDARY_STORAGE";
                    }

                    return System.getenv(storageDefinition) + "/" + split[1];
                }

            } else if (isDownloadsDocument(uri)) {// DownloadsProvider

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);

            } else if (isMediaDocument(uri)) {// MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }

        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore (and general)

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);

        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }


    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    public static String resizedBitmapEncodeToBase64(Bitmap image) {
        if(image!=null) {
            System.gc();
            Bitmap immagex = image;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] b = new byte[0];
            try {
                immagex.setDensity(density);
                immagex.compress(Bitmap.CompressFormat.JPEG, quality, baos);
                b = baos.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
            String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
            return imageEncoded;
        }else{
            return  "";
        }
    }

    public static Bitmap getResizedBitmap(Bitmap image, int maxSize) {

        if(image != null) {
            int width = image.getWidth();
            int height = image.getHeight();

            float bitmapRatio = (float) width / (float) height;
            if (bitmapRatio > 1) {
                width = maxSize;
                Log.e("width", "" + width);
                height = (int) (width / bitmapRatio);
                Log.e("height", "" + height);
            } else {
                height = maxSize;
                width = (int) (height * bitmapRatio);
            }

            return Bitmap.createScaledBitmap(image, width, height, true);
        }else{
            return null;
        }
    }
}
