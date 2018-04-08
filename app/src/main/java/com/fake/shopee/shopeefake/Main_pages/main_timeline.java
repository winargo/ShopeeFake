package com.fake.shopee.shopeefake.Main_pages;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.upload.activity_galery;
import com.fake.shopee.shopeefake.upload.camera_test;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class main_timeline extends Activity {

    ImageButton mainhome,maintimeline,maincamera,mainnotif,mainprofile,maincart;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_timeline);

            mAuth= FirebaseAuth.getInstance();

            mainhome = (ImageButton) findViewById(R.id.timemainmenu);
            maintimeline = (ImageButton) findViewById(R.id.timetimeline);
            maincamera = (ImageButton) findViewById(R.id.timecamera);
            mainnotif = (ImageButton) findViewById(R.id.timenotif);
            mainprofile = (ImageButton) findViewById(R.id.timeprofile);
            maincart = (ImageButton) findViewById(R.id.timecart);

            mainhome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(main_timeline.this , MainActivity.class);
                    startActivity(i);
                    finish();
                }
            });

            maintimeline.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(main_timeline.this , main_timeline.class);
                    startActivity(i);
                    finish();
                }
            });
            maincamera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final CharSequence[] item = {"Kamera","Foto"};
                    AlertDialog.Builder dialog = new AlertDialog.Builder(main_timeline.this,R.style.AppCompatAlertDialogStyle);
                    dialog.setTitle("Select");
                    dialog.setItems(item,new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int position) {

                            if(position==0) {
                                Intent a = new Intent(main_timeline.this, camera_test.class);
                                startActivity(a);
                            }
                            if(position==1) {
                                Intent a = new Intent(main_timeline.this, activity_galery.class);
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
                    Intent i = new Intent(main_timeline.this , main_notification.class);
                    startActivity(i);
                    finish();
                }
            });
            mainprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(main_timeline.this , main_profile.class);
                    startActivity(i);
                    finish();
                }
            });
            maincart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(main_timeline.this , main_cart.class);
                startActivity(i);
                }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {

    }
}
