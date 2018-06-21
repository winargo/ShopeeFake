package com.fake.shopee.shopeefake.fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fake.shopee.shopeefake.Admin.AdminActivity;
import com.fake.shopee.shopeefake.Main_pages.main_profile;
import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.SQLclass;
import com.fake.shopee.shopeefake.generator;
import com.fake.shopee.shopeefake.session_class;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.sql.ResultSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Riandy on 12/10/2017.
 */

public class fragment_login extends Fragment{

    EditText email,pass;
    Button btnlogin;
    private FirebaseAuth mAuth;
    ProgressDialog dialog;
    SQLclass sqLclass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mAuth = FirebaseAuth.getInstance();
        sqLclass = new SQLclass();

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_login, container, false);
        email = (EditText) rootView.findViewById(R.id.emaillogin);
        pass = (EditText) rootView.findViewById(R.id.passwordlogin);
        btnlogin = (Button) rootView.findViewById(R.id.btnloginlogin);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isvalidemail(email.getText().toString())){
                    DoLogin doLogin = new DoLogin();
                    doLogin.execute("");
                }
                else
                    {
                    Toast.makeText(getActivity(), "Bad Email",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });



        return rootView;
    }
    public class DoLogin extends AsyncTask<String, String, String> {
        String z = "Processing...";
        Boolean isSuccess = false;
        String userid = email.getText().toString();
        String password = pass.getText().toString();
        private ProgressDialog dialog=new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Logging in");
            this.dialog.show();
        }

        @Override
        protected void onPostExecute(String r) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(getActivity(), r, Toast.LENGTH_SHORT).show();
            if (isSuccess) {
                if(session_class.tempcommand==0){
                    Intent i = new Intent(getActivity(), main_profile.class);
                    startActivity(i);
                    getActivity().finish();
                }
                else {
                    Intent i = new Intent(getActivity(), AdminActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }
            }
        }


        @Override
        protected String doInBackground(String... params) {
            mAuth.signInWithEmailAndPassword(userid, password)
                    .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithEmail:success");
                                final FirebaseUser user = mAuth.getCurrentUser();
                                session_class session= new session_class(getActivity());
                                session.setusename(user.getEmail());
                                int admindata=0;

                                ResultSet check = sqLclass.querydata("select * from xuser where pemilik='"+user.getEmail()+"'");
                                try{
                                    int calc=0;
                                    while (check.next()){
                                        calc++;
                                        admindata=check.getInt("status");
                                    }
                                    if(calc==0){
                                        int register = sqLclass.queryexecute("insert into xuser values('"+user.getEmail()+"',0,0,'',0,0,getdate())");
                                        if(register>0){
                                            z="Signed in As "+user.getEmail();
                                            session_class.tempcommand=0;
                                            isSuccess=true;
                                            generator.tempactivity.finish();
                                            generator.tempactivity=null;
                                            onPostExecute(z);
                                        }
                                        else {
                                            z="sqlerror";
                                        }
                                    }
                                    else {
                                        if(admindata!=0) {
                                            generator.isadmin=1;
                                            DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    switch (which) {
                                                        case DialogInterface.BUTTON_POSITIVE:
                                                            z = "Signed in As " + user.getEmail();

                                                            generator.userlogin=user.getEmail().toString();
                                                            generator.sendtoken(getActivity());
                                                            isSuccess = true;
                                                            generator.tempactivity.finish();
                                                            generator.tempactivity=null;
                                                            session_class.tempcommand=1;
                                                            onPostExecute(z);

                                                            break;

                                                        case DialogInterface.BUTTON_NEGATIVE:
                                                            z = "Signed in As " + user.getEmail();
                                                            generator.sendtoken(getActivity());
                                                            generator.userlogin=user.getEmail().toString();
                                                            isSuccess = true;
                                                            session_class.tempcommand=0;
                                                            generator.tempactivity.finish();
                                                            generator.tempactivity=null;
                                                            onPostExecute(z);
                                                            break;
                                                    }
                                                }
                                            };
                                            SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("shopeefake", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = pref.edit();

                                            if(generator.isadmin==1) {
                                                editor.putString("admin",String.valueOf( generator.isadmin));
                                                editor.commit();
                                            }
                                            else if(generator.isadmin==2){
                                                editor.putString("admin",String.valueOf( generator.isadmin));
                                                editor.commit();
                                            }
                                            else {
                                                editor.putString("admin",String.valueOf( generator.isadmin));
                                                editor.commit();
                                            }
                                            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                            builder.setMessage("Admin account detected choice?").setPositiveButton("Admin page", dialogClickListener)
                                                    .setNegativeButton("User Page", dialogClickListener).show();
                                        }
                                        else {
                                            generator.isadmin=2;

                                            SharedPreferences pref = getActivity().getApplicationContext().getSharedPreferences("shopeefake", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = pref.edit();

                                            if(generator.isadmin==1) {
                                                editor.putString("admin",String.valueOf( generator.isadmin));
                                                editor.commit();
                                            }
                                            else if(generator.isadmin==2){
                                                editor.putString("admin",String.valueOf( generator.isadmin));
                                                editor.commit();
                                            }
                                            else {
                                                editor.putString("admin",String.valueOf( generator.isadmin));
                                                editor.commit();
                                            }

                                            z = "Signed in As " + user.getEmail();
                                            generator.userlogin = user.getEmail();
                                            generator.sendtoken(getActivity());
                                            isSuccess = true;
                                            generator.tempactivity.finish();
                                            generator.tempactivity=null;
                                            session_class.tempcommand=0;
                                            onPostExecute(z);
                                        }

                                    }
                                }
                                catch (Exception e){
                                    Log.e("ERror",e.getMessage());
                                }

                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w(TAG, "signInWithEmail:failure", task.getException());
                                Toast.makeText(getActivity(), "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                session_class session= new session_class(getActivity());
                                session.setusename("");
                                generator.userlogin="";
                                z="Error Login";
                                onPostExecute(z);
                            }

                            // ...
                        }
                    });
            return z;
        }
    }
    public boolean isvalidpassword (final String password){

            Pattern pattern;
            Matcher matcher;

            final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{4,}$";

            pattern = Pattern.compile(PASSWORD_PATTERN);
            matcher = pattern.matcher(password);

            return matcher.matches();

        }

    public boolean isvalidemail (final String email){

        Pattern pattern;
        Matcher matcher;

        final String PASSWORD_PATTERN = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(email);

        return matcher.matches();

    }

}

