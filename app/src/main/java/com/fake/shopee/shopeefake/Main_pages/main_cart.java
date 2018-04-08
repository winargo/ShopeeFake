package com.fake.shopee.shopeefake.Main_pages;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fake.shopee.shopeefake.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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
        minus = (Button) findViewById(R.id.btn_minusCheckout);
        plus = (Button) findViewById(R.id.btn_plsCheckout);
        angka = (TextView) findViewById(R.id.txt_qtyCheckout);
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String operators = "-";
                temp = Integer.parseInt(angka.getText().toString());
                angka.setText(angkachecker(temp,operators));
            }
        });
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String operators = "+";
                temp = Integer.parseInt(angka.getText().toString());
                angka.setText(angkachecker(temp,operators));
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
