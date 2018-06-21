package com.fake.shopee.shopeefake.Main_pages;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.generator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class add_address extends AppCompatActivity {

    EditText kodepos,alamatlengkap,notelp;

    Button save;

    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);

        kodepos = findViewById(R.id.alamat_pos);
        alamatlengkap = findViewById(R.id.alamat_lengkap);
        notelp = findViewById(R.id.alamat_telepon);

        back = findViewById(R.id.backarrow);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save =findViewById(R.id.btnsendalamat);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue queue = Volley.newRequestQueue(add_address.this);
                String url ="http://" + generator.ip + ":3000/insertaddress?pemilik=" + generator.userlogin+"&street='"+alamatlengkap.getText().toString()+"'&postal="+kodepos.getText().toString()+"&number="+notelp.getText().toString();
                Log.e("url",url);
                JsonObjectRequest jsonarray = new JsonObjectRequest(Request.Method.GET,url, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                if(response!=null){
                                    Toast.makeText(add_address.this, "Added new adress", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else {
                                    Toast.makeText(add_address.this, "fail to add", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(add_address.this, "not working "+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

// Add the request to the RequestQueue.
                queue.add(jsonarray);

            }
        });
    }
}
