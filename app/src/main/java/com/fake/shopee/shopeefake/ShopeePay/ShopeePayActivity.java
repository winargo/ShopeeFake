package com.fake.shopee.shopeefake.ShopeePay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.SQLclass;
import com.fake.shopee.shopeefake.session_class;

import java.sql.ResultSet;

public class ShopeePayActivity extends Activity {

    session_class session;
    SQLclass sqlclass;

    LinearLayout penarikanShopeePay, transaksiShopeePay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopee_pay);

        sqlclass= new SQLclass();
        session = new session_class(ShopeePayActivity.this);

        ResultSet money = sqlclass.querydata("Select * from xuser where pemilik='"+session.getusename()+"'");

        penarikanShopeePay = (LinearLayout) findViewById(R.id.penarikan_shopeePay);
        transaksiShopeePay = (LinearLayout) findViewById(R.id.transaksi_shopeePay);

        penarikanShopeePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent penarikanIntent = new Intent(ShopeePayActivity.this, PenarikanActivity.class);
                startActivity(penarikanIntent);
            }
        });

        transaksiShopeePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent transaksiIntent = new Intent(ShopeePayActivity.this, RiwayatTransaksiActivity.class);
                startActivity(transaksiIntent);
            }
        });

    }
}
