package com.fake.shopee.shopeefake.Main_pages;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.fake.shopee.shopeefake.ProductSearch.stock_detail;
import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.generator;
import com.fake.shopee.shopeefake.recyclerviews.recycler_alamat;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class pilih_alamat extends AppCompatActivity{

    RecyclerView listalamat;

    LinearLayout newalamat;
    List<String> alamat;
    ImageButton back;
    recycler_alamat mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihalamat);

        back = findViewById(R.id.backarrow);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        newalamat = findViewById(R.id.tambahalamatbaru);

        newalamat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(pilih_alamat.this,add_address.class);
                startActivity(a);
            }
        });

        alamat = new ArrayList<>();

        listalamat = findViewById(R.id.recycle_cart_items);

        RequestQueue queue = Volley.newRequestQueue(pilih_alamat.this);
        String url ="http://" + generator.ip + ":3000/getaddress?pemilik=" + generator.userlogin;

        Log.e("url",url);

        JsonArrayRequest jsonarray = new JsonArrayRequest(Request.Method.GET,url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if(response!=null){
                            for(int i=0;i<response.length();i++){
                                try {
                                    alamat.add(response.getJSONObject(i).getString("address_pemilik").replace("@gmail.com","")+"\n"+response.getJSONObject(i).getString("address_street")+"\n"+response.getJSONObject(i).getString("address_postal")+"\n"+response.getJSONObject(i).getString("address_number"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            mAdapter = new recycler_alamat(pilih_alamat.this, alamat);
                            listalamat.setLayoutManager(new GridLayoutManager(pilih_alamat.this, 1));
                            listalamat.setItemAnimator(new DefaultItemAnimator());
                            listalamat.setAdapter(mAdapter);
                        }
                        else {
                            Toast.makeText(pilih_alamat.this, "fail to add", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(pilih_alamat.this, "not working "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonarray);
        
        

    }
}
