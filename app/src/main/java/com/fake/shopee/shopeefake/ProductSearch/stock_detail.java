package com.fake.shopee.shopeefake.ProductSearch;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.fake.shopee.shopeefake.Main_pages.main_cart;
import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.SQLclass;
import com.fake.shopee.shopeefake.generator;
import com.fake.shopee.shopeefake.session_class;
import com.google.gson.JsonObject;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.sql.ResultSet;

public class stock_detail extends AppCompatActivity {

    TextView nama,harga,keterangan,kategori,berat,stock,penjual,tittletext,kointext;
    Button beli;
    String a="";
    String b="";
    ImageView image;
    session_class session;
    SQLclass sqlclass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detail);

        sqlclass = new SQLclass();
        session = new session_class(this);

        nama = (TextView) findViewById(R.id.detailproduk);
        penjual = (TextView) findViewById(R.id.detailpenjual);

        tittletext = (TextView) findViewById(R.id.resultsearchbox);
        harga = (TextView) findViewById(R.id.detailharga);
        keterangan = (TextView) findViewById(R.id.detailketerangan);
        kategori = (TextView) findViewById(R.id.detailkategori);
        berat = (TextView) findViewById(R.id.detailberat);
        stock = (TextView) findViewById(R.id.detailstok);
        image = (ImageView) findViewById(R.id.detailimage);

        kointext = (TextView) findViewById(R.id.detailkoin);



        beli = (Button) findViewById(R.id.buttonbuy);

        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue = Volley.newRequestQueue(stock_detail.this);
                String url ="http://"+ generator.ip+":3000/insertitem?pemilik="+session.getusename()+"&stockid="+a+"&jumlah="+"1"+"&penjual="+b;
                AlertDialog a = new AlertDialog.Builder(stock_detail.this,R.style.AppCompatAlertDialogStyle).setTitle("link").setMessage(url).show();

// Request a string response from the provided URL.
                JsonObjectRequest jsonobject = new JsonObjectRequest(url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if(response!=null){
                                    Toast.makeText(stock_detail.this, "Added to cart", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(stock_detail.this, "fail to add", Toast.LENGTH_SHORT).show();
                                }
                                }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(stock_detail.this, "not working "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

// Add the request to the RequestQueue.
                queue.add(jsonobject);
                //Intent a = new Intent(stock_detail.this,main_cart.class);
                //startActivity(a);
              /*  ResultSet query = sqlclass.querydata("select * from cart where pemilik='"+session.getusename()+"' and stock_id='"+a+"' and penjual_pemilik='"+penjual.getText().toString()+"'");
                try{
                    while (query.next()){

                    }
                }catch (Exception e){
                    Log.e("sqlerror",e.getMessage());
                }
                int buy = sqlclass.queryexecute("INSERT INTO cart VALUES ('"+session.getusename()+"', '"+a+"', 1 ,'"+penjual.getText().toString()+"');");
                Intent a = new Intent(stock_detail.this,main_cart.class);
*/
            }
        });

        String s = getIntent().getStringExtra("request");
        Log.e("Request data",s);
        ResultSet result = sqlclass.querydata("select * from stock where namaproduk='"+s+"'");

        try{
            while (result.next()){
                a = result.getString("stock_id");
                nama.setText(result.getString("namaproduk"));
                tittletext.setText(result.getString("namaproduk"));
                keterangan.setText(result.getString("keterangan"));
                kategori.setText(result.getString("kategori"));
                berat.setText(result.getString("berat"));
                harga.setText("Rp "+result.getString("harga"));
                kointext.setText("Dapatkan "+String.valueOf("20,000"));
                Picasso.with(this).load(result.getString("imagedir")).resize(400,400).centerCrop().into(image);
                stock.setText(result.getString("stock"));
                penjual.setText("Penjual \n" +result.getString("pemilik").replace("@gmail.com",""));
                b=result.getString("pemilik");
            }

        }catch (Exception e){
            Log.e("stock detail error",e.getMessage());
        }

    }
}
