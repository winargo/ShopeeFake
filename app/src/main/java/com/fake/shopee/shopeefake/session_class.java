package com.fake.shopee.shopeefake;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Riandy on 12/11/2017.
 */

public class session_class {
    private SharedPreferences prefs;

    public session_class(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setusename(String usename) {
        prefs.edit().putString("usename", usename).commit();
    }

    public String getusename() {
        String usename = prefs.getString("usename","");
        return usename;
    }
    public static String currentactivity="";
    public static int tempcommand=0;
    //command for passing activity fragment pages
}
