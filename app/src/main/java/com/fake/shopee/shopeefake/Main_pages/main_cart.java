package com.fake.shopee.shopeefake.Main_pages;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.fake.shopee.shopeefake.ProductSearch.resultadapter;
import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.SQLclass;
import com.fake.shopee.shopeefake.generator;
import com.fake.shopee.shopeefake.objects.stocklist;
import com.fake.shopee.shopeefake.recyclerviews.recycler_cart;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class main_cart extends Activity {

    private FirebaseAuth mAuth;

    recycler_cart mAdapter;

    Button checkout;

    SQLclass sqlclass;

    RecyclerView cartitems;
    
    List<String> penjual;
    List<String[]> itempenjual;

    Button plus,minus;
    TextView angka;
    ImageButton backarrowiage;
    int temp=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cart);

        sqlclass = new SQLclass();
        
        cartitems = findViewById(R.id.recycle_cart_items);

        penjual = new ArrayList<>();
        itempenjual = new ArrayList<>();

        checkout = findViewById(R.id.btn_checkout);

        mAuth = FirebaseAuth.getInstance();

        backarrowiage = findViewById(R.id.backarrow);
        if(generator.totalcart==null){
            generator.totalcart = findViewById(R.id.total_allcart);
            generator.totalcart.setText("Rp 0");
        }else {
            generator.totalcart.setText("Rp 0");
        }


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(main_cart.this,checkoutpage.class);
                startActivity(a);
            }
        });


        backarrowiage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getstockdata fetchstock = new getstockdata(main_cart.this);
        fetchstock.execute("");
    }

    private String angkachecker(int currentqty,String operators){
        if(operators.equals("+")){
                return String.valueOf(currentqty + 1);
        }
        else {
            if (currentqty == 0) {
                return "0";
            } else {
                return String.valueOf(currentqty - 1);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser==null){
            Intent i = new Intent(main_cart.this,loginactivity.class);
            startActivity(i);
            finish();
        }
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
            ResultSet result = sqlclass.querydata("select * from stock order by stock_id asc");
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
                RequestQueue queue = Volley.newRequestQueue(main_cart.this);
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
                                        mAdapter = new recycler_cart(main_cart.this,penjual,itempenjual);
                                        cartitems.setLayoutManager(new GridLayoutManager(main_cart.this, 1));
                                        cartitems.setItemAnimator(new DefaultItemAnimator());
                                        cartitems.setAdapter(mAdapter);
                                        mAdapter.notifyDataSetChanged();
                                        //Toast.makeText(main_cart.this, penjual, Toast.LENGTH_SHORT).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                else {
                                    Toast.makeText(main_cart.this, "fail to add", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(main_cart.this, "not working "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

// Add the request to the RequestQueue.
                queue.add(jsonarray);
            }
            return "";
        }
    }

}
