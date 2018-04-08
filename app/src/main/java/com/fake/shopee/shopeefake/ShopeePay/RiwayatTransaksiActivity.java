package com.fake.shopee.shopeefake.ShopeePay;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fake.shopee.shopeefake.R;

public class RiwayatTransaksiActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_transaksi);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_riwayatTransaksi);

        RecyclerAdapter adapter = new RecyclerAdapter(this);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
