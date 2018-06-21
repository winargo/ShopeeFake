package com.fake.shopee.shopeefake.Main_pages;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.formula.MyDividerItemDecoration;
import com.fake.shopee.shopeefake.generator;
import com.fake.shopee.shopeefake.recyclerviews.recycler_ongkir;

import org.w3c.dom.Text;

public class pilih_ongkir extends AppCompatActivity{
    RecyclerView recyclerView;
    ImageButton back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pilihongkir);

        back = findViewById(R.id.backarrow);
        recyclerView = findViewById(R.id.listongkir);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recycler_ongkir mAdapter = new recycler_ongkir(this, generator.chosenongkir);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(pilih_ongkir.this, LinearLayoutManager.VERTICAL, 16));
        mAdapter.notifyDataSetChanged();
    }
}
