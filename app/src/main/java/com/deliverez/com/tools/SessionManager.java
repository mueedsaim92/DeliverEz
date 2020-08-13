package com.deliverez.com.tools;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Context context;

    public SessionManager(Context context) {
      this.context=context;
      sharedPreferences=Methods.getPreferances(context);
        editor = sharedPreferences.edit();
    }

    public void setUserName(String userName) {
        editor.putString("UserName", userName);
        editor.commit();
    }
    public String getUserName() {
        return  sharedPreferences.getString("UserName", "");
    }

    public void setToken(String token) {
        editor.putString("SessionToken", token);
        editor.commit();
    }

    public String getToken() {
        return  sharedPreferences.getString("SessionToken", "");
    }



    public void setCryptoUserId(String discount) {
        editor.putString("UserId", discount);
        editor.commit();
    }

    public String getCryptoUserId() {
        return  sharedPreferences.getString("UserId", null);
    }



    public boolean isUserLoggedIn(){
        return sharedPreferences.getBoolean("IS_LOGGED_IN", false);
    }


    public boolean setUserLoggedIn(boolean status){
        return sharedPreferences.edit().putBoolean("IS_LOGGED_IN",status).commit();
    }

    public void logout(){
        sharedPreferences.edit().remove("IS_LOGGED_IN").commit();
    }





}
