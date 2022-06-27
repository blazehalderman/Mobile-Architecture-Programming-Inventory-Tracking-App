package com.example.cs360blazehaldermanprojecttwo;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY = "session_user";

    public SessionManagement(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(String user_id) {
        // save session of user whenever user is logged in
        editor.putString(SESSION_KEY, user_id).commit();
    }

    public String getSession() {
        // return saved user session
        return sharedPreferences.getString(SESSION_KEY, null);
    }

    public void removeSession() {
        editor.putString(SESSION_KEY, null).commit();
    }
}
