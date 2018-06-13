package com.fake.shopee.shopeefake.ProductSearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fake.shopee.shopeefake.Main_pages.main_cart;
import com.fake.shopee.shopeefake.Main_pages.main_profile;
import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.SQLclass;
import com.fake.shopee.shopeefake.fragment.fragment_profile_sell;
import com.fake.shopee.shopeefake.generator;
import com.fake.shopee.shopeefake.session_class;
import com.squareup.picasso.Picasso;

import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class stock_detail_seller extends AppCompatActivity {

    NumberFormat formatter = new DecimalFormat("###,###,###");
    TextView nama,harga,keterangan,kategori,berat,stock,penjual,tittletext,kointext;
    Button edit,hapus;
    ImageView back;
    String a="";
    ImageView image;
    session_class session;
    SQLclass sqlclass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_detailseller);

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

        back = findViewById(R.id.resultback);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        edit = (Button) findViewById(R.id.buttonedit);
        hapus = findViewById(R.id.buttondelete);

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent act = new Intent(stock_detail_seller.this,main_cart.class);
                startActivity(act);
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

        hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int query = sqlclass.queryexecute("delete from stock where stock_id='"+a+"'");

                if(query>=1){

                    Intent a = new Intent(stock_detail_seller.this,main_profile.class);
                    startActivity(a);

                    generator.tempactivity.finish();
                    finish();

                    Toast.makeText(stock_detail_seller.this, "Hapus Berhasil", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(stock_detail_seller.this, "Hapus Gagal", Toast.LENGTH_SHORT).show();
                }
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
                harga.setText("Rp "+formatter.format(Integer.parseInt(result.getString("harga").replace(",",""))));

                kointext.setText("Dapatkan "+String.valueOf("20,000"));
                Picasso.with(this).load(result.getString("imagedir")).resize(400,400).centerCrop().into(image);
                stock.setText(result.getString("stock"));
            }
            penjual.setText(session.getusename());
        }catch (Exception e){
            Log.e("stock detail error",e.getMessage());
        }

    }
}
