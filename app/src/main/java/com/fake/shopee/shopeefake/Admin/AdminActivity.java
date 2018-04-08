package com.fake.shopee.shopeefake.Admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fake.shopee.shopeefake.Main_pages.main_profile;
import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.session_class;
import com.google.firebase.auth.FirebaseAuth;

public class AdminActivity extends Activity {

    Button btnlogout;
    session_class session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        session = new session_class(this);

        ImageView setDiskon = (ImageView) findViewById(R.id.ivDiskonAdmin);
        btnlogout = (Button) findViewById(R.id.btnlogout);

        setDiskon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent setDiskonIntent = new Intent(AdminActivity.this, DiskonDashboardActivity.class);
                startActivity(setDiskonIntent);
            }
        });

        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AdminActivity.this,main_profile.class);
                FirebaseAuth.getInstance().signOut();
                session.setusename("");
                startActivity(i);
                finish();
            }
        });

    }
}
