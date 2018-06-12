package com.fake.shopee.shopeefake.ShopeePay;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.SQLclass;
import com.fake.shopee.shopeefake.session_class;

import java.sql.ResultSet;

public class RiwayatTransaksiActivity extends Activity {

    SQLclass sqlclass;
    session_class session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopee_pay);




    }
}
