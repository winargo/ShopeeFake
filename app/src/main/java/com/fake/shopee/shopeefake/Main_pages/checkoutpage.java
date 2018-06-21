package com.fake.shopee.shopeefake.Main_pages;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.SQLclass;
import com.fake.shopee.shopeefake.generator;
import com.fake.shopee.shopeefake.objects.stocklist;
import com.fake.shopee.shopeefake.recyclerviews.recycler_cart;
import com.fake.shopee.shopeefake.recyclerviews.recycler_checkout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class checkoutpage extends AppCompatActivity {
    TextView title,address;
    ImageButton back;
    LinearLayout pilihalamat,pilihongkir;
    
    TextView totalcheckout,totalongkir,subtotal;

    recycler_checkout mAdapter;

    SQLclass sqLclass;

    List<String> penjual;
    List<String[]> itempenjual;


    Button executecheckout;

    RecyclerView recycler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        penjual = new ArrayList<>();
        itempenjual = new ArrayList<>();

        sqLclass = new SQLclass();

        pilihalamat = findViewById(R.id.adresschoice);


        generator.totalcheckout=null;
        generator.hargaongkirtemp = 0.0d;
        generator.subtotalcheckout=null;
        totalcheckout = findViewById(R.id.total_checkout);
        generator.totalcheckout = findViewById(R.id.total_checkout);
        subtotal = findViewById(R.id.subtotalcheckout);

        totalongkir = findViewById(R.id.totalongkir);
        generator.subtotalcheckout=totalongkir;
        totalcheckout.setText("Rp 0");
        totalongkir.setText("Rp 0");
        subtotal.setText("Rp 0");

        executecheckout = findViewById(R.id.execute_checkout);

        address = findViewById(R.id.chosenadress);

        recycler = findViewById(R.id.recycle_checkout);

        title = findViewById(R.id.title_checkout);

        back = findViewById(R.id.checkoutback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        title.setText("Checkout Pesanan");

        pilihalamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent alamat = new Intent(checkoutpage.this,pilih_alamat.class);
                generator.chosenadress=address;
                startActivity(alamat);
            }
        });

        executecheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final RequestQueue queue = Volley.newRequestQueue(checkoutpage.this);
                final String[] url = {"http://" + generator.ip + ":3000/getitem/" + generator.userlogin};

                final JsonArrayRequest jsonarray = new JsonArrayRequest(Request.Method.GET, url[0], null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                if(response!=null){
                                    try {

                                        for (int i = 0; i < response.length(); i++) {

                                            final String[] tempsting = new String[5];
                                            tempsting[0] = response.getJSONObject(i).getString("pemilik");
                                            tempsting[1] = response.getJSONObject(i).getString("stock_id");
                                            tempsting[2] = response.getJSONObject(i).getString("imagedata");
                                            tempsting[3] = response.getJSONObject(i).getString("jumlah");
                                            tempsting[4] = response.getJSONObject(i).getString("penjual_pemilik");
                                            itempenjual.add(tempsting);
                                            url[0] = "http://" + generator.ip + ":3000/checkout?pemilik="+response.getJSONObject(i).getString("pemilik")+"&stockid="+response.getJSONObject(i).getString("stock_id")+"&jumlah="+response.getJSONObject(i).getString("jumlah")+"&penjual="+response.getJSONObject(i).getString("penjual_pemilik")+"&imagedata='"+response.getJSONObject(i).getString("imagedata")+"'&address='"+generator.chosenadress.getText().toString().replace("\n", "").replace(" ","")+"'";
                                            Log.e("url", url[0] );
                                            JsonObjectRequest jsonarray1 = new JsonObjectRequest(Request.Method.GET, url[0], null,
                                                    new Response.Listener<JSONObject>() {
                                                        @Override
                                                        public void onResponse(JSONObject response) {
                                                            if(response!=null){
                                                                generator.sendnotification(checkoutpage.this,tempsting[4],generator.userlogin);
                                                            }
                                                            else {
                                                                Toast.makeText(checkoutpage.this, "fail to add", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    }, new Response.ErrorListener() {
                                                @Override
                                                public void onErrorResponse(VolleyError error) {
                                                    Toast.makeText(checkoutpage.this, "not working notif"+error.toString(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            queue.add(jsonarray1);
                                            
                                        }
                                        showNotification("Shopee","Pesanan sudah di kirim ke penjual");
                                        //Toast.makeText(checkoutpage.this, penjual, Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    Toast.makeText(checkoutpage.this, "fail to add", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(checkoutpage.this, "not working "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

// Add the request to the RequestQueue.
                queue.add(jsonarray);

                
                


            /*    NotificationCompat.Builder b = new NotificationCompat.Builder(getBaseContext());
                b.setAutoCancel(true)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setWhen(System.currentTimeMillis())
                        .setSmallIcon(R.drawable.logo)
                        .setContentTitle("title");
                NotificationManager nm = (NotificationManager)getBaseContext().getSystemService(Context.NOTIFICATION_SERVICE);
                nm.notify(1, b.build());*/
            }
        });

        getstockdata fetchstock = new getstockdata(checkoutpage.this);
        fetchstock.execute("");
    }

    private class getstockdata extends AsyncTask<String, String, String> {
        private ProgressDialog dialog;
        private boolean issuccess=false;

        public getstockdata(Activity a) {
            dialog = new ProgressDialog(a,R.style.AppCompatAlertDialogStyle);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Loading....");
            dialog.show();
        }

        protected String doInBackground(String... args) {
            generator.stockdata.clear();
            ResultSet result = sqLclass.querydata("select * from stock order by stock_id asc");
            try {
                while (result.next()){
                    generator.stockdata.add(new stocklist(result.getString("stock_id"),result.getString("imagedir"),result.getString("imagefile"),result.getString("namaproduk"),result.getString("kategori"),result.getString("pemilik"),result.getString("keterangan"),Double.parseDouble(result.getString("harga").replace(",","")),result.getDouble("berat"),result.getInt("stock")));
                }
                issuccess=true;
                onPostExecute(issuccess,"");
            }
            catch (Exception e){
                Log.e("Exception sql",e.getMessage().toString());
                dialog.dismiss();
            }
            return "";
        }

        protected String onPostExecute(Boolean b,String a) {
            // do UI work here
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if(b){
                RequestQueue queue = Volley.newRequestQueue(checkoutpage.this);
                String url ="http://"+ generator.ip+":3000/getitem/"+generator.userlogin;

                JsonArrayRequest jsonarray = new JsonArrayRequest(Request.Method.GET,url, null,
                        new Response.Listener<JSONArray>() {
                            @Override
                            public void onResponse(JSONArray response) {
                                if(response!=null){
                                    String tempseller="";
                                    try {

                                        for (int i = 0; i < response.length(); i++) {
                                            if(tempseller.equals("")){
                                                tempseller=response.getJSONObject(i).getString("penjual_pemilik");
                                                penjual.add(response.getJSONObject(i).getString("penjual_pemilik"));
                                            }
                                            if(!tempseller.equals(response.getJSONObject(i).getString("penjual_pemilik"))){
                                                penjual.add(response.getJSONObject(i).getString("penjual_pemilik"));
                                                tempseller=response.getJSONObject(i).getString("penjual_pemilik");
                                            }
                                            Log.e("Json data",response.getJSONObject(i).getString("penjual_pemilik"));
                                            Log.e("response length",String.valueOf(response.length()));
                                            String[] tempsting = new String[5];
                                            tempsting[0]=response.getJSONObject(i).getString("pemilik");
                                            tempsting[1]=response.getJSONObject(i).getString("stock_id");
                                            tempsting[2]=response.getJSONObject(i).getString("imagedata");
                                            tempsting[3]=response.getJSONObject(i).getString("jumlah");
                                            tempsting[4]=response.getJSONObject(i).getString("penjual_pemilik");
                                            itempenjual.add(tempsting);
                                  /*  if(tempseller.equals("")){

                                    }
                                    penjual.add(response.getJSONObject(i).getString("penjual_pemilik"));
                                    String[] tempitem=new String[]{};
                                    for(int j=0;j<response.getJSONObject(i).length();j++){
                                        Log.e("String json data",response.getJSONObject(j));
                                    }*/
                                            //itempenjual.add(tempitem);
                                        }
                                        mAdapter = new recycler_checkout(checkoutpage.this,penjual,itempenjual,subtotal,totalongkir,totalcheckout);
                                        recycler.setLayoutManager(new GridLayoutManager(checkoutpage.this, 1));
                                        recycler.setItemAnimator(new DefaultItemAnimator());
                                        recycler.setAdapter(mAdapter);
                                        mAdapter.notifyDataSetChanged();
                                        //Toast.makeText(checkoutpage.this, penjual, Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    Toast.makeText(checkoutpage.this, "fail to add", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(checkoutpage.this, "not working "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

// Add the request to the RequestQueue.
                queue.add(jsonarray);
            }
            return "";
        }
    }

    void showNotification(String title, String content) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("default",
                    "ShopeeFake",
                    NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription("Shopeefakedata");
            mNotificationManager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getApplicationContext(), "default")
                .setSmallIcon(R.drawable.logo) // notification icon
                .setContentTitle(title) // title for notification
                .setContentText(content)// message for notification
              //  .setSound(alarmSound) // set alarm sound for notification
                .setAutoCancel(true); // clear notification after click
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(pi);
        mNotificationManager.notify(0, mBuilder.build());
    }
}
