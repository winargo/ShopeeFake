package com.fake.shopee.shopeefake;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fake.shopee.shopeefake.ProductSearch.result;
import com.fake.shopee.shopeefake.ProductSearch.resultadapter;
import com.fake.shopee.shopeefake.objects.stocklist;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class generator {

    public static TextView statusConnection = null;
    public static String userlogin="";
    public static String refreshedToken="";
    public static String ip="192.168.1.11";
    public static String port="";

    public static Double hargaongkirtemp;

    public static TextView chosenadress;
    public static TextView chosenongkir;

    public static TextView totalongkir;

    public static int isoffline=0;
    public static int isadmin=0;
    public static String setupfirstisdone="";

    public static resultadapter tempadapter;
    public static List<result> templist;
    public static Activity tempactivity;

    public static List<stocklist> stockdata=new ArrayList<>();

    public static String isdone="0";

    public static TextView totalcart;


    public static TextView totalcheckout;
    public static TextView subtotalcheckout;

    public static void sendtoken(final Context context){
        final RequestQueue queue = Volley.newRequestQueue(context);
        final String[] url = {"http://" + generator.ip + ":3000/checktoken/" + generator.refreshedToken};

        JsonArrayRequest jsonarray = new JsonArrayRequest(Request.Method.GET, url[0], null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("URl", url[0]);
                        if(response!=null){
                            url[0] = "http://" + generator.ip + ":3000/inserttoken?pemilik="+generator.userlogin+"&token=" + generator.refreshedToken;
                            JsonArrayRequest jsonarray1 = new JsonArrayRequest(Request.Method.GET, url[0], null,
                                    new Response.Listener<JSONArray>() {
                                        @Override
                                        public void onResponse(JSONArray response) {
                                            Log.e("URl", url[0]);
                                            if(response!=null){
                                                Log.e("Token success", "added");
                                            }
                                            else {
                                                Log.e("Token Error", "Fail to add");
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("Token Error", "not working "+error.toString());
                                }
                            });
                            //String url ="http://"+ generator.ip+":3000/insertitem?pemilik="+session.getusename()+"&stockid="+a+"&jumlah="+"1"+"&penjual="+b;


// Add the request to the RequestQueue.
                            queue.add(jsonarray1);

                        }
                        else {
                            Toast.makeText(context, "fail to add", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "not working "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        //String url ="http://"+ generator.ip+":3000/insertitem?pemilik="+session.getusename()+"&stockid="+a+"&jumlah="+"1"+"&penjual="+b;


// Add the request to the RequestQueue.
        queue.add(jsonarray);
    }
    public static void removetoken(final Context context){
        final RequestQueue queue = Volley.newRequestQueue(context);
        final String[] url = {"http://" + generator.ip + ":3000/deltoken?pemilik="+generator.userlogin+"&token=" + generator.refreshedToken};

        JsonArrayRequest jsonarray = new JsonArrayRequest(Request.Method.GET, url[0], null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("URl", url[0]);
                        if(response!=null){
                            Toast.makeText(context, "Refreshed data", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, "fail to add", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "not working "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        //String url ="http://"+ generator.ip+":3000/insertitem?pemilik="+session.getusename()+"&stockid="+a+"&jumlah="+"1"+"&penjual="+b;


// Add the request to the RequestQueue.
        queue.add(jsonarray);
    }
    public static void sendnotification(final Context context,String towho,String sender){
        final RequestQueue queue = Volley.newRequestQueue(context);
        final String[] url = {"http://" + generator.ip + ":3000/sendinfo/"+towho+"/"+sender};
        Log.e("URl", url[0]);
        JsonArrayRequest jsonarray = new JsonArrayRequest(Request.Method.GET, url[0], null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        if(response!=null){
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(context, "fail to add", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "not working "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        //String url ="http://"+ generator.ip+":3000/insertitem?pemilik="+session.getusename()+"&stockid="+a+"&jumlah="+"1"+"&penjual="+b;


// Add the request to the RequestQueue.
        queue.add(jsonarray);
    }
}
