package com.fake.shopee.shopeefake;

import android.app.Activity;
import android.widget.TextView;

import com.fake.shopee.shopeefake.ProductSearch.result;
import com.fake.shopee.shopeefake.ProductSearch.resultadapter;
import com.fake.shopee.shopeefake.objects.stocklist;

import java.util.ArrayList;
import java.util.List;

public class generator {

    public static TextView statusConnection = null;
    public static String userlogin="";
    public static String ip="169.254.166.46";
    public static String port="";
    public static int isoffline=0;
    public static int isadmin=0;
    public static String setupfirstisdone="";

    public static resultadapter tempadapter;
    public static List<result> templist;
    public static Activity tempactivity;

    public static List<stocklist> stockdata=new ArrayList<>();

    public static String isdone="0";

    public static TextView totalcart;
}
