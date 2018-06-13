package com.fake.shopee.shopeefake.Main_pages;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fake.shopee.shopeefake.ProductSearch.searching;
import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.generator;
import com.fake.shopee.shopeefake.upload.activity_galery;
import com.fake.shopee.shopeefake.upload.camera_test;
import com.fake.shopee.shopeefake.session_class;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends Activity {

    ImageButton mainhome,maintimeline,maincamera,mainnotif,mainprofile,maincart,mainsearch;
    session_class session;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mainhome = (ImageButton) findViewById(R.id.mainhome);
        maintimeline = (ImageButton) findViewById(R.id.maintimeline);
        maincamera = (ImageButton) findViewById(R.id.maincamera);
        mainnotif = (ImageButton) findViewById(R.id.mainnotif);
        mainprofile = (ImageButton) findViewById(R.id.mainprofile);
        maincart = (ImageButton) findViewById(R.id.maincart);
        mainsearch = (ImageButton) findViewById(R.id.mainsearchbox);

        mainhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , MainActivity.class);
                startActivity(i);
                finish();
            }
        });

        maintimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , main_timeline.class);
                startActivity(i);
                finish();
            }
        });
        maincamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final CharSequence[] item = {"Kamera","Foto"};
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this,R.style.AppCompatAlertDialogStyle);
                dialog.setTitle("Select");
                dialog.setItems(item,new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int position) {

                        if(position==0) {
                            Intent a = new Intent(MainActivity.this, camera_test.class);
                            startActivity(a);
                        }
                        if(position==1) {
                            Intent a = new Intent(MainActivity.this, activity_galery.class);
                            startActivity(a);
                        }
                        Toast.makeText(getApplicationContext(),"selected Item:"+position, Toast.LENGTH_SHORT).show();
                    }

                });
                dialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();
            }
        });
        mainnotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , main_notification.class);
                startActivity(i);
                finish();
            }
        });
        mainprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , main_profile.class);
                startActivity(i);
                finish();
            }
        });
        maincart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this , main_cart.class);
                startActivity(i);
            }
        });

        mainsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this , searching.class);
                startActivity(i);
            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        session = new session_class(this);
        if(session.getusename()==null){
            session.setusename("");
        }
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser!=null)
        generator.userlogin=currentUser.getEmail();

        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if(currentUser!=null)
        generator.userlogin = currentUser.getEmail();
    }
}