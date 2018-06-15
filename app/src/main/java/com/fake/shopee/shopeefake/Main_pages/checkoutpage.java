package com.fake.shopee.shopeefake.Main_pages;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fake.shopee.shopeefake.R;

import java.util.concurrent.TimeoutException;

public class checkoutpage extends AppCompatActivity {
    TextView title,address;
    LinearLayout adresschoice;
    Button back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);


    }
}
