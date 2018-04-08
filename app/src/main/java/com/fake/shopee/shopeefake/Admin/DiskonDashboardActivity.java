package com.fake.shopee.shopeefake.Admin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fake.shopee.shopeefake.R;

public class DiskonDashboardActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diskon_dashboard);

        Button editDiskon = (Button) findViewById(R.id.btn_diskonEdit);
        editDiskon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent inputDiskonIntent = new Intent(DiskonDashboardActivity.this, DiskonEditActivity.class);
                startActivity(inputDiskonIntent);
            }
        });
    }
}
