package com.fake.shopee.shopeefake.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.Main_pages.main_belanjaanku;
import com.fake.shopee.shopeefake.SQLclass;
import com.fake.shopee.shopeefake.ShopeePay.RiwayatTransaksiActivity;
import com.fake.shopee.shopeefake.ShopeePay.ShopeePayActivity;
import com.fake.shopee.shopeefake.session_class;

import java.sql.ResultSet;

/**
 * Created by Riandy on 12/1/2017.
 */


public class fragment_profile extends Fragment{
    public static int page=1;
    session_class session;
    SQLclass sqlclass;
    public void oncreate(Bundle state){
        super.onCreate(state);

        final Bundle args = getArguments();
    }
    TextView walletamount,coinamount;
    LinearLayout belanjaanku,koinshopee,dompetshopee,terakhirdilihat,wallet,coin;
    ImageButton belumbayar,dikemas,dikirim,pengembalian,smallbutton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                        R.layout.row_profile1, container, false);
        belanjaanku = (LinearLayout) rootView.findViewById(R.id.cart);
        belumbayar = (ImageButton) rootView.findViewById(R.id.belumbayar);
        dikemas = (ImageButton) rootView.findViewById(R.id.dikemas);
        dikirim = (ImageButton) rootView.findViewById(R.id.dikirim);
        pengembalian =(ImageButton) rootView.findViewById(R.id.pengembalian);
        coin = (LinearLayout) rootView.findViewById(R.id.coinshopee);

        coinamount = (TextView) rootView.findViewById(R.id.coinleft);
        sqlclass = new SQLclass();
        session = new session_class(getActivity());

        if(session.getusename()==""){

        }
        else {
            ResultSet getcoin = sqlclass.querydata("select * from xuser where pemilik='" + session.getusename() + "'");
            Log.e("session username",session.getusename());
            try {
                while (getcoin.next()) {
                    coinamount.setText(String.valueOf(getcoin.getInt("koin"))+"Koin");
                }
            } catch (Exception e) {
                Log.e("SQLERROR", e.getMessage());
            }
        }

        belanjaanku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getActivity(),main_belanjaanku.class);
                startActivity(a);

            }
        });

        belumbayar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getActivity(),main_belanjaanku.class);
                session.currentactivity="0";
                startActivity(a);

            }
        });
        dikemas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getActivity(),main_belanjaanku.class);
                session.currentactivity="1";
                startActivity(a);

            }
        });
        dikirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getActivity(),main_belanjaanku.class);
                session.currentactivity="2";
                startActivity(a);
            }
        });
        pengembalian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getActivity(),main_belanjaanku.class);
                session.currentactivity="5";
                startActivity(a);
            }
        });
        coin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getActivity(),main_belanjaanku.class);
                startActivity(a);
            }
        });
        wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(getActivity(),ShopeePayActivity.class);
                startActivity(a);
            }
        });
        return rootView;
    }
}
