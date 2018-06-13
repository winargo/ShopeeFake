package com.fake.shopee.shopeefake.Main_pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.generator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

public class main_cart extends Activity {

    private FirebaseAuth mAuth;

    Button plus,minus;
    TextView angka;
    ImageButton backarrowiage;
    int temp=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mAuth = FirebaseAuth.getInstance();

        backarrowiage = findViewById(R.id.backarrow);

        RequestQueue queue = Volley.newRequestQueue(main_cart.this);
        /*String url ="http://"+ generator.ip+":3000/insertitem?pemilik="+generator.userlogin+"&stockid="+a+"&jumlah="+"1"+"&penjual="+b;
        AlertDialog b = new AlertDialog.Builder(main_cart.this,R.style.AppCompatAlertDialogStyle).setTitle("link").setMessage(url).show();

// Request a string response from the provided URL.
        JsonObjectRequest jsonobject = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response!=null){
                            Toast.makeText(main_cart.this, "Added to cart", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Toast.makeText(main_cart.this, "fail to add", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(main_cart.this, "not working "+error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

// Add the request to the RequestQueue.
        queue.add(jsonobject);
        */

        backarrowiage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    private String angkachecker(int currentqty,String operators){
        if(operators.equals("+")){
                return String.valueOf(currentqty + 1);
        }
        else {
            if (currentqty == 0) {
                return "0";
            } else {
                return String.valueOf(currentqty - 1);
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser==null){
            Intent i = new Intent(main_cart.this,loginactivity.class);
            startActivity(i);
            finish();
        }
    }

}
