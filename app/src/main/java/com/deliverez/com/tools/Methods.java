package com.deliverez.com.tools;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.deliverez.com.R;

import java.net.InetAddress;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;

/**
 * Created by coderzlab on 18/1/16.
 */
public class Methods {

    private static final String TAG = Methods.class.getSimpleName();

    private static final int PULSE_ANIMATOR_DURATION = 300;

    private static SimpleDateFormat NOTIF_DATE_FORMAT = new SimpleDateFormat("d MMM");
    private static SimpleDateFormat NOTIF_DATE_WITH_YEAR_FORMAT = new SimpleDateFormat("d MMM yy");

    private static SimpleDateFormat orderDateTimeFormat = new SimpleDateFormat("d MMM, yy");
    private static SimpleDateFormat orderMonthYearFormat = new SimpleDateFormat("MMM, yy");
    private static SimpleDateFormat orderDayDateFormat = new SimpleDateFormat("d");
    private static SimpleDateFormat orderHvrMinFormat = new SimpleDateFormat("hh:mm a");

    private static SimpleDateFormat chatTimeFormat = new SimpleDateFormat("hh:mm a");
    private static SimpleDateFormat chatHeaderTimeFormat = new SimpleDateFormat("dd-MMM-yy");
    private static SimpleDateFormat inputDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd");

    //2016-06-14 19:43:28
    private static SimpleDateFormat inputCompleteDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    private static SimpleDateFormat dobDateFormat = new SimpleDateFormat("d':'MMMM yyyy");


    public static String getDobFormattedDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return dobDateFormat.format(date.getTime()).replace(":", getDayOfMonthSuffix(calendar.get(Calendar.DAY_OF_MONTH)));
    }


    //yyyy-MM-dd HH:mm:ss  eg : 2017-04-28 17:37:33
    public static Date toDateTime(String inputDate) {
        try {
            if (inputDate != null) {
                Date inDnT = inputCompleteDateTimeFormat.parse(inputDate);
                return inDnT;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //1976-02-13
    public static String getDobFormattedDate(String inputDate) {
        try {
            Date inDnT = inputDateTimeFormat.parse(inputDate);
            return getDobFormattedDate(inDnT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputDate;
    }


    //yyyy-MM-dd HH:mm:ss
    public static String toZoopDateTime(String inputDate) {
        try {
            if (inputDate != null) {
                Date inDnT = inputCompleteDateTimeFormat.parse(inputDate);
                return formatZoopTimeRevised(inDnT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    public static String toCommentDateTime(String inputDate) {
        try {
            if (inputDate != null) {
                Date inDnT = inputCompleteDateTimeFormat.parse(inputDate);
                return formatCommentTimeRevised(inDnT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    private static String getDayOfMonthSuffix(final int n) {
        if (n >= 11 && n <= 13) {
            return "th ";
        }
        switch (n % 10) {
            case 1:
                return "st ";
            case 2:
                return "nd ";
            case 3:
                return "rd ";
            default:
                return "th ";
        }
    }


    public static String formatDateTimeForOderDetail(Date inputDateTime) {
        return orderDateTimeFormat.format(inputDateTime);
    }

    public static String formatMonthYearForOderDetail(Date inputDateTime) {
        return orderMonthYearFormat.format(inputDateTime);
    }

    public static String formatDayDateForOderDetail(Date inputDateTime) {
        return orderDayDateFormat.format(inputDateTime);
    }

    public static String formatHvrMinForOderDetail(Date inputDateTime) {
        return orderHvrMinFormat.format(inputDateTime);
    }


    public static Date timestampToDate(long timestamp) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(timestamp);
        return calendar.getTime();
    }


    public static String timestampToOrderDateTime(long timestamp) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(timestamp * 1000);
        return formatDateTimeForOderDetail(calendar.getTime());
    }

    public static String timestampToOrderMonthYear(long timestamp) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(timestamp * 1000);
        return formatMonthYearForOderDetail(calendar.getTime());
    }

    public static String timestampToOrderDayDate(long timestamp) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(timestamp * 1000);
        return formatDayDateForOderDetail(calendar.getTime());
    }

    public static String timestampToOrderHvrMinDate(long timestamp) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(timestamp * 1000);
        return formatHvrMinForOderDetail(calendar.getTime());
    }


    //2016-06-14 19:43:28
    public static String getNotificationFormattedDate(String inputDate) {
        try {
            Date inDnT = inputCompleteDateTimeFormat.parse(inputDate);
            return formatNotificationTime(inDnT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return inputDate;
    }



    /*public static String timestampToNotificationTime(Date date) {
        return formatNotificationTime(date);
    }*/

    public static String timestampToNotificationTime(long timestamp) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        calendar.setTimeInMillis(timestamp);
        return formatNotificationTime(calendar.getTime());
    }

    public static String getChatTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time);
        String date = chatTimeFormat.format(cal.getTime());//,new StringBuffer("hh:mm a"),new FieldPosition(0));
        return date.toUpperCase();
    }


    public static String getChatHeaderDateTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time);
        String date = chatHeaderTimeFormat.format(cal.getTime());//,new StringBuffer("hh:mm a"),new FieldPosition(0));
        return date.toUpperCase();
    }


    public static String getThreadTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time);
        String date = formatNotificationTime(cal.getTime());//,new StringBuffer("hh:mm a"),new FieldPosition(0));
        return date.toUpperCase();
    }


    public static String getLastSeenTime(long time) {
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        cal.setTimeInMillis(time);
        String date = formatLastSeenTime(cal.getTime());//,new StringBuffer("hh:mm a"),new FieldPosition(0));
        return date;
    }


    public static String getStaticData(Context context) {
        SharedPreferences mcpPreferences = getDobizSharedPreferences(context);
        return mcpPreferences.getString("staticData", null);
    }

    public static boolean saveStaticData(Context context, String staticData) {
        SharedPreferences mcpPreferences = getDobizSharedPreferences(context);
        SharedPreferences.Editor editor = mcpPreferences.edit();
        return editor.putLong("lastSavedStaticData", System.currentTimeMillis()).putString("staticData", staticData).commit();
    }


    public static long getLastSavedStaticDataTime(Context context) {
        SharedPreferences mcpPreferences = getDobizSharedPreferences(context);
        return mcpPreferences.getLong("lastSavedStaticData", 0);
    }


    public static boolean saveLastSavedGcm(Context context) {
        SharedPreferences mcpPreferences = getDobizSharedPreferences(context);
        SharedPreferences.Editor editor = mcpPreferences.edit();
        return editor.putLong("lastSavedGcm", System.currentTimeMillis()).commit();
    }

    public static long getLastSavedGcmKeyTime(Context context) {
        SharedPreferences mcpPreferences = getDobizSharedPreferences(context);
        return mcpPreferences.getLong("lastSavedGcm", 0);
    }


    public static HashSet<String> getCookies(Context context) {
        SharedPreferences mcpPreferences = getDobizSharedPreferences(context);
        return (HashSet<String>) mcpPreferences.getStringSet("cookies", new HashSet<String>());
    }

    public static boolean setCookies(Context context, HashSet<String> cookies) {
        SharedPreferences mcpPreferences = getDobizSharedPreferences(context);
        SharedPreferences.Editor editor = mcpPreferences.edit();
        return editor.putStringSet("cookies", cookies).commit();
    }


    public static boolean saveLastUserSyncTime(Context context) {
        SharedPreferences mcpPreferences = getDobizSharedPreferences(context);
        SharedPreferences.Editor editor = mcpPreferences.edit();
        return editor.putLong("lastUserSync", System.currentTimeMillis()).commit();
    }

    public static long getLastUserSyncTime(Context context) {
        SharedPreferences mcpPreferences = getDobizSharedPreferences(context);
        return mcpPreferences.getLong("lastUserSync", 0);
    }


    public static String getPreviousAppVersion(Context context) {
        SharedPreferences mcpPreferences = getDobizSharedPreferences(context);
        return mcpPreferences.getString("previousAppVersion", null);
    }

    public static boolean saveCurrentAppVersion(Context context, String currentAppVersion) {
        SharedPreferences mcpPreferences = getDobizSharedPreferences(context);
        SharedPreferences.Editor editor = mcpPreferences.edit();
        return editor.putString("previousAppVersion", currentAppVersion).commit();
    }


    public static boolean setShowBirthday(Context context, boolean status) {
        SharedPreferences mcpPreferences = getDobizSharedPreferences(context);
        SharedPreferences.Editor editor = mcpPreferences.edit();
        return editor.putBoolean("showBirthday", status).commit();
    }

    public static boolean getShowBirthday(Context context) {
        SharedPreferences mcpPreferences = getDobizSharedPreferences(context);
        return mcpPreferences.getBoolean("showBirthday", true);
    }


    public static void requestForCall(Context context, String mobileNumber) {
        try {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + Uri.encode(mobileNumber.trim())));
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(callIntent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static String formatNotificationTime(Date createdAt) {

        String time = "";
        long diff = new Date().getTime() - createdAt.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        if (diffDays > 1)
            time = NOTIF_DATE_FORMAT.format(createdAt);
        else if (diffDays > 0)
            time = "Yesterday";
        else if (diffHours > 0)
            time = diffHours + " hours ago";
        else if (diffMinutes > 0)
            time = diffMinutes + " minutes ago";
        else
            time = "Now";

        return time;
    }


    public static String formatZoopTime(Date createdAt) {

        String time = "";
        long diff = new Date().getTime() - createdAt.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (diffDays > 30)
            time = "a month ago";
        else if (diffDays > 1)
            time = diffDays + " days ago";
        else if (diffDays > 0)
            time = "Yesterday";
        else if (diffHours > 0)
            time = diffHours + " hours ago";
        else if (diffMinutes > 0)
            time = diffMinutes + " minutes ago";
        else
            time = "just now";

        return time;
    }


    public static String formatZoopTimeRevised(Date createdAt) {

        String time = "";
        long diff = new Date().getTime() - createdAt.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        if (diffDays > 7) {
            time = new Date().getYear() == createdAt.getYear() ? NOTIF_DATE_FORMAT.format(createdAt) : NOTIF_DATE_WITH_YEAR_FORMAT.format(createdAt);
        } else if (diffDays == 7)
            time = "1 week ago";
        else if (diffDays > 1)
            time = diffDays + " days ago";
        else if (diffDays == 1)
            time = diffDays + " day ago";
        /*else if (diffDays > 0)
            time = "Yesterday";
        */
        else if (diffHours > 1)
            time = diffHours + " hours ago";
        else if (diffHours == 1)
            time = diffHours + " hour ago";
        else if (diffMinutes > 1)
            time = diffMinutes + " mins ago";
        else if (diffMinutes == 1)
            time = diffMinutes + " min ago";
        else
            time = "just now";

        return time;
    }


    public static String formatCommentTimeRevised(Date createdAt) {

        String time = "";
        long diff = new Date().getTime() - createdAt.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);

        long diffWeek = diffDays / 7;
        long diffMonth = diffDays / 30;
        long diffYear = diffMonth / 12;

       /* if (diffDays > 30)

           time = "a month ago";*/

        if (diffMonth > 12)
            time = diffYear + " year";
        else if (diffDays > 30)
            time = diffMonth + " mon";
        else if (diffDays > 27) {
            time = "4 w";
        } else if (diffDays > 20) {
            time = "3 w";
        } else if (diffDays > 13) {
            time = "2 w";
        } else if (diffDays > 6) {
            time = "1 w";
        } else if (diffDays > 1) {
            time = diffDays + " d";
        }
        /*else if (diffDays > 0)
            time = "Yesterday";*/
        else if (diffHours > 0) {
            time = diffHours + " h";
        } else if (diffMinutes > 0)
            time = diffMinutes + " m";
        else
            time = "just now";

        return time;
    }


    public static String formatLastSeenTime(Date createdAt) {

        String time = "Last seen ";
        long diff = new Date().getTime() - createdAt.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        if (diffDays > 1)
            time = time + NOTIF_DATE_FORMAT.format(createdAt);
        else if (diffDays > 0)
            time = time + "Yesterday";
        else if (diffHours > 0)
            time = time + diffHours + " hours ago";
        else if (diffMinutes > 0)
            time = time + diffMinutes + " minutes ago";
        else
            time = "Online";

        return time;
    }


    public static String formatRequirementFulfilledTime(Date createdAt, Date updatedAt) {

        String time = "";
        long diff = updatedAt.getTime() - createdAt.getTime();
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000) % 24;
        long diffDays = diff / (24 * 60 * 60 * 1000);
        if (diffDays > 0)
            time = diffDays + " Days";
        else if (diffHours > 0)
            time = diffHours + " Hours";
        else if (diffMinutes > 0)
            time = diffMinutes + " Minutes";
        else
            time = "Now";

        return time;
    }


    public static String getFormattedAmount(double value) {
        return String.format("%.2f", value);
    }

   /* public static String getFormattedDate(Date inputDateTime){

        if(inputDateTime==null){
            return "NA";
        }
        try {
            return outputDateTimeFormat.format(inputDateTime);
        }catch (Exception e){
            e.printStackTrace();
        }
        return  inputDateTime.toString();
    }
*/


    public static boolean isMobileNumber(String paramCharSequence) {
        return paramCharSequence.matches("[0-9]+");
    }

    public static boolean isValidMobileNumber(String paramCharSequence) {
        return paramCharSequence.matches("[0-9]+") && paramCharSequence.length() >= 5;
    }

    public static boolean isValidEmail(CharSequence paramCharSequence) {
        return (!TextUtils.isEmpty(paramCharSequence) &&
                Patterns.EMAIL_ADDRESS.matcher(paramCharSequence).matches());
    }

    public static ProgressDialog setUpProgressDialog(Context paramContext, String paramString, boolean cancelable) {
        ProgressDialog localProgressDialog = new ProgressDialog(paramContext);
        localProgressDialog.setMessage(paramString);
        localProgressDialog.setCancelable(cancelable);
        if (paramContext instanceof Activity && !((Activity) paramContext).isFinishing())
            localProgressDialog.show();
        else
            localProgressDialog.show();

        return localProgressDialog;
    }

    public static ProgressDialog setUpProgressDialogNew(Context paramContext, String paramString, boolean cancelable, boolean progress) {
        ProgressDialog localProgressDialog = new ProgressDialog(paramContext);
        localProgressDialog.setMessage(paramString);
        localProgressDialog.setCancelable(cancelable);
        //progress=true;

        if (paramContext instanceof Activity && !((Activity) paramContext).isFinishing()) {
            {
                if (progress == true) {
                    localProgressDialog.dismiss();
                } else
                    localProgressDialog.show();
            }
        } else {
            if (progress == true) {
                localProgressDialog.dismiss();
            } else
                localProgressDialog.show();
        }

        return localProgressDialog;
    }


    public static void storeRegistrationId(Context paramContext, String paramString) {
        Object localObject = Methods.getDobizSharedPreferences(paramContext);
        //int i = Methods.getAppVersion(paramContext);
        localObject = ((SharedPreferences) localObject).edit();
        ((SharedPreferences.Editor) localObject).putString("registration_id", paramString);
        //((SharedPreferences.Editor)localObject).putInt("appVersion", i);
        ((SharedPreferences.Editor) localObject).commit();
        Log.d("PUSH", "Push Token Saved Locally");
    }


    public static int getAppVersion(Context paramContext) {
        try {
            int i = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0).versionCode;
            return i;
        } catch (PackageManager.NameNotFoundException localNameNotFoundException) {
            throw new RuntimeException("Could not get package name: " + localNameNotFoundException);
        }
    }


    public static String getAppVersionName(Context context) {

        String versionName = "";
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            versionName = packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(Methods.class.getSimpleName(), e.getLocalizedMessage());
        }
        return versionName;
    }

    public static String getRegistrationId(Context paramContext) {
        SharedPreferences localSharedPreferences = getDobizSharedPreferences(paramContext);
        String regID = localSharedPreferences.getString("registration_id", "");

        return regID;
    }


    public static void openShortToast(Context paramContext, String paramString) {
        Toast.makeText(paramContext, paramString, Toast.LENGTH_SHORT).show();
    }


    public static String fetchInitial(String paramString) {
        String str;
        if (!valid(paramString))
            str = "";
        else
            str = Character.toString(paramString.charAt(0)).toUpperCase();
        return str;
    }


    public static boolean valid(String paramString) {
        boolean bool;
        if ((paramString != null) && (!paramString.trim().equals("")) && (!paramString.equals("null")) && (!isOnlyWhiteSpaces(paramString)))
            bool = true;
        else
            bool = false;
        return bool;
    }

    public static boolean validDateString(String paramString) {
        return (Methods.valid(paramString) && !paramString.contains("0000-00-00") && !paramString.contains("1900-01-01"));
    }

    private static boolean isOnlyWhiteSpaces(String paramString) {
        boolean bool;
        if (paramString.trim().length() != 0)
            bool = false;
        else
            bool = true;
        return bool;
    }


    public static void showAppendedErrorMsg(Context paramContext, String paramString) {
        String str = paramContext.getResources().getString(R.string.some_error_occurred);
        if (!valid(paramString)) {
            str = str;
        } else {
            str = paramString + ". " + str;
        }
        openShortToast(paramContext, str);
    }


    public static boolean isInternetConnected(Context paramContext, boolean showToast) {
        NetworkInfo localNetworkInfo =
                ((ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        boolean bool;
        if ((localNetworkInfo == null) || (!localNetworkInfo.isConnected())) {
            bool = false;
            if (showToast) {
                Toast.makeText(paramContext, paramContext.getResources().getString(R.string.please_check_your_internet_connection), Toast.LENGTH_SHORT).show();
            }

        } else
            bool = true;

        return bool;
    }

    public static boolean isOnline(final Context paramContext) {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");
            //You can replace it with your name
            if(!ipAddr.equals("") || ipAddr==null){
                Toast.makeText(paramContext, "Slow", Toast.LENGTH_SHORT).show();
            }else Toast.makeText(paramContext, "Fast", Toast.LENGTH_SHORT).show();
            return !ipAddr.equals("");
        } catch (Exception e) {
            return false;
        }


        /*try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal == 0);
            Toast.makeText(paramContext, "Not Access Internet", Toast.LENGTH_SHORT).show();
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
       // return false;
    }



    public static void noInternetDialog(Context paramContext) {
        openShortToast(paramContext, paramContext.getResources().getString(R.string.please_check_your_internet_connection));
    }


    public static boolean isInternetConnected(Context paramContext) {
        NetworkInfo localNetworkInfo =
                ((ConnectivityManager) paramContext.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        boolean bool;
        if ((localNetworkInfo == null) || (!localNetworkInfo.isConnected()))
            bool = false;
        else
            bool = true;
        return bool;
    }

    public static void hideSoftKeyboard(Activity paramActivity) {
        InputMethodManager localInputMethodManager = (InputMethodManager) paramActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        View localView = paramActivity.getCurrentFocus();
        if (localView != null)
            localInputMethodManager.hideSoftInputFromWindow(localView.getWindowToken(), 0);
        // Log.e("boolean", String.valueOf(localInputMethodManager.hideSoftInputFromWindow(localView.getWindowToken(), 0)));
    }

    private static SharedPreferences getDobizSharedPreferences(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }


    /**
     * API for creating thumbnail from Video
     *
     * @param filePath - video file path
     * @param type     - size MediaStore.Images.Thumbnails.MINI_KIND or MICRO_KIND
     * @return thumbnail bitmap
     */
    public static Bitmap createThumbnailFromPath(String filePath, int type) {
        return ThumbnailUtils.createVideoThumbnail(filePath, type);
    }


    public static String getImagePath(Context paramContext, Uri paramUri) {
        String[] localArr = new String[1];
        localArr[0] = "_data";

        String path = null;
        Cursor cursor = ((Activity) paramContext).managedQuery(paramUri, localArr, null, null, null);

        if (cursor != null) {
            int i = cursor.getColumnIndexOrThrow("_data");
            cursor.moveToFirst();
            path = cursor.getString(i);
        }

        if (path == null)
            path = paramUri.getPath();

        return path;
    }




  /*  public static boolean isMe(FZUser userOne, FZUser userTwo){
        if(userOne!=null && userTwo!=null){
            return userOne.getObjectId() == userTwo.getObjectId();
        }

        Log.e(Methods.class.getSimpleName(),"Both Users are null");
        return false;
    }*/





   /* public static boolean hasCameraPermissions(Context context) {

        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, FZConstants.ACCESS_CAMERA_PERMISSION);

            return  false;
        } else {
            return true;
        }
    }


    public static boolean hasStoragePermissions(Context context) {

        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, FZConstants.ACCESS_STORAGE_PERMISSION);

            return  false;
        } else {
            return true;
        }
    }

    public static boolean hasSmsReceivePermissions(Context context) {

        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.RECEIVE_SMS}, FZConstants.ACCESS_SMS_PERMISSION);

            return  false;
        } else {
            return true;
        }
    }
*/

    public static boolean hasCameraPermissions1(Context context) {

        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, 1);

            return false;
        } else {
            return true;
        }
    }


    public static boolean hasStoragePermissions1(Context context) {

        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);

            return false;
        } else {
            return true;
        }
    }

    public static boolean hasCameraPermissions(Context context, int requestCode) {

        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CAMERA}, requestCode);

            return false;
        } else {
            return true;
        }
    }


    public static boolean hasStoragePermissions(Context context, int requestCode) {

        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);

            return false;
        } else {
            return true;
        }
    }

    public static boolean hasSmsReceivePermissions(Context context, int requestCode) {

        if (ActivityCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.RECEIVE_SMS}, requestCode);

            return false;
        } else {
            return true;
        }
    }


/*
    public static void trackScreen(Activity paramActivity, String screenName)
    {
        Tracker localTracker = ((PayBizApplication)paramActivity.getApplication()).getAppTracker();
        localTracker.setScreenName(screenName);
        localTracker.send(new HitBuilders.AppViewBuilder().build());

    }
*/


    public static String removeSpecialCharacters(String s) {
        if (Methods.valid(s))
            return s.replaceAll("\\s+", "").replaceAll("[^a-zA-Z0-9]+", "").replaceAll("-+", "-");

        return s;
    }


    /*public static void trackAction(Activity paramActivity, String screenName,String category,String action, ParseUser  parseUser)
    {
        Tracker localTracker = ((PayBizApplication)paramActivity.getApplication()).getAppTracker();
        localTracker.setScreenName(screenName);
        localTracker.send(new HitBuilders.EventBuilder()
                .setCategory(category)
                .setLabel(parseUser.getObjectId())
                .setAction(action)
                .build());

    }
*/

    public static SharedPreferences getPreferances(Context context) {
        return context.getSharedPreferences(context.getPackageName(), Context.MODE_PRIVATE);
    }

    public static String generateObjectId(int size, String userId, String threadId) {
        if (size == 0)
            throw new RuntimeException("Length can't be zero");

        if (!Methods.valid(userId))
            throw new RuntimeException("user id is null or empty");

        if (!Methods.valid(threadId))
            throw new RuntimeException("thread id is null or empty");


        String objectId = UUID.randomUUID().toString();
        return objectId;
    }


    public static int getSampleSizeByFileSize(long fileSize) {
        if (fileSize >= 1000000 * 3) // > 3MB
            return 8;

        else if (fileSize >= 1000000 * 1) // > 1 MB
            return 4;
        else if (fileSize >= 1000000 / 2) // > 500 KB
            return 2;

        return 1;
    }


    public static Bitmap decodeFromBase64ToBitmap(String encodedImage, ImageView imageView) {
        if (Methods.valid(encodedImage)) {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedBitmap);

            return decodedBitmap;
        }
        return null;
    }

    public static Bitmap decodeFromBase64ToBitmap(String encodedImage) {
        if (Methods.valid(encodedImage)) {
            byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
            Bitmap decodedBitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            return decodedBitmap;
        }
        return null;
    }


    public static boolean isValidList(List list) {
        return list != null && !list.isEmpty();
    }


    public static String[] explodeName(String fullName) {
        String[] name = new String[2];
        name[0] = "";
        name[1] = "";
        if (Methods.valid(fullName)) {
            String[] temp = fullName.trim().replaceAll("\\s+", ":!:").split(":!:");
            if (temp != null && temp.length >= 1)
                name[0] = temp[0];

            if (temp != null && temp.length >= 2)
                name[1] = temp[1];

        }
        return name;
    }




    /*public static String rectifyAvatarUrl(String avatar) {
        if (Methods.valid(avatar)) {
            avatar=avatar.trim();
            return (avatar.startsWith("http://") || avatar.startsWith("https://") ? "":ApiClient.BASE_WEB_URL) + avatar.replaceFirst("_\\%d_", "_" + size + "_");
        }
        return avatar;
    }*/


    /*public static String formatWithK(int count) {

        String str = "";
        if (count > 0) {

            if (count < 1000)
                str = String.valueOf(count) + " ";
            else
                str = (count / 1000) + ((count % 1000 != 0) ? "." + (count % 1000) : "") + "k ";
        }

        return str;

    }*/

    public static String formatWithK(int val) {
        return formatWithK(val, false);
    }

    public static String formatWithK(int val, boolean avoidZero) {

        if (val > 999) {
            int q = (val / 1000);
            int r = (val % 1000);
            return String.valueOf((r == 0) ? q + "k" : q + "." + r + "k") + " ";
        } else
            return (val <= 0 && avoidZero) ? "" : String.valueOf(val) + " ";
    }


    public static int getRandomColor() {
        Random rnd = new Random();
        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        return color;
    }


    public static Bitmap takeScreenshot(View view) {
        view.setDrawingCacheEnabled(true);
        return view.getDrawingCache();
    }


    ////////////////




    public static String dateFormat1(String currentDate){
        SimpleDateFormat readFormat = null, writeFormat = null;
        Date startDate = null, endDate = null;
        //  String strStartDate = "2014-12-09";
        readFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        writeFormat = new SimpleDateFormat("dd MMM, yyyy hh:mm aa");

        try {
            startDate = readFormat.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return writeFormat.format(startDate);
    }

    public static String dateFormat(String currentDate){
        SimpleDateFormat readFormat = null, writeFormat = null;
        Date startDate = null, endDate = null;
        //  String strStartDate = "2014-12-09";
        readFormat = new SimpleDateFormat("yyyy-MM-dd");
        writeFormat = new SimpleDateFormat("dd MMM, yyyy");

        try {
            startDate = readFormat.parse(currentDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return writeFormat.format(startDate);
    }

    public static boolean checkPermissionREAD_EXTERNAL_STORAGE( final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    DeliverezConstants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public static void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                DeliverezConstants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
    }

    public static String getTime(Context context, final TextView timeTV){
        final String[] time = {""};
        TimePickerDialog mTimePicker;
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        mTimePicker = new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                String selectedTime = selectedHour + ":" + selectedMinute;
                SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                Date date = null;
                try {
                    date = fmt.parse(selectedTime );
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");
                String formattedTime=fmtOut.format(date);

                timeTV.setText(formattedTime);

                String hvrs="",minut="";
                if(selectedHour < 10){
                    hvrs="0"+selectedHour;
                }else hvrs=String.valueOf(selectedHour);

                if(selectedMinute < 10){
                    minut="0"+selectedMinute;
                }else minut=String.valueOf(selectedMinute);

                time[0] = hvrs+":"+minut+":00";

                //Log.d("<<<<<","====> "+hvrs+":"+minut+":00");
            }
        }, hour, minute, false);//No 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

        return time[0];
    }




}



