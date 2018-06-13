package com.fake.shopee.shopeefake.upload;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fake.shopee.shopeefake.Main_pages.loginactivity;
import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.SQLclass;
import com.fake.shopee.shopeefake.formula.thousandedittext;
import com.fake.shopee.shopeefake.session_class;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.pddstudio.urlshortener.URLShortener;

import java.io.FileNotFoundException;
import java.sql.ResultSet;

public class activity_galery extends Activity {

    private FirebaseAuth mAuth;

    SQLclass sqlclass;

    TextView textTargetUri,numbercount;
    EditText nameproduct,harga,berat,stock,kategori,keterangan;
    ImageView targetImage;
    boolean data=false;
    Uri targetUri=null;
    session_class session;
    ImageButton backarrow,done;
    private StorageReference mStorageRef;

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser==null){
            Intent i = new Intent(activity_galery.this,loginactivity.class);
            startActivity(i);
            finish();
        }
    }
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_galery);

        sqlclass = new SQLclass();

         session= new session_class(this);

        mAuth = FirebaseAuth.getInstance();

        nameproduct =(EditText) findViewById(R.id.namaproduk);
        harga = (EditText) findViewById(R.id.hargagalery);
        stock =(EditText) findViewById(R.id.stockgalery);
        berat =(EditText) findViewById(R.id.beratgalery);
        kategori = (EditText) findViewById(R.id.kategorigalery);

        harga.addTextChangedListener(new thousandedittext(harga));

        numbercount = (TextView) findViewById(R.id.numbercountgalery);
        keterangan = (EditText)  findViewById(R.id.keterangangalery);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        backarrow = (ImageButton) findViewById(R.id.backarrow);
        done = (ImageButton) findViewById(R.id.donegalery);

        if (!nameproduct.getText().toString().trim().equals("") && !kategori.getText().toString().trim().equals("") && !berat.getText().toString().trim().equals("") && !harga.getText().toString().trim().equals("") && !stock.getText().toString().trim().equals("") && !keterangan.getText().toString().trim().equals("")){
            data=true;
        }

        targetImage = (ImageView)findViewById(R.id.targetimage);
        if(mAuth.getCurrentUser()==null){

        }
        else{
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, 0);
        }
        nameproduct.addTextChangedListener(mTextEditorWatcher);
        targetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 0);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameproduct.getText().toString().trim().equals("") || kategori.getText().toString().trim().equals("") || berat.getText().toString().trim().equals("") || harga.getText().toString().trim().equals("") || stock.getText().toString().trim().equals("") || keterangan.getText().toString().trim().equals("")){
                    Toast.makeText(getApplicationContext(),"Data Tidak lengkap",Toast.LENGTH_SHORT).show();
                }
                else{
                    YourAsyncTask b = new YourAsyncTask(activity_galery.this);
                    b.execute();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            targetUri = data.getData();
            Toast.makeText(getApplicationContext(),targetUri.toString(), Toast.LENGTH_SHORT).show();
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                targetImage.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    private class YourAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;

        public YourAsyncTask(activity_galery a) {
            dialog = new ProgressDialog(a,R.style.AppCompatAlertDialogStyle);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Processing....");
            dialog.show();
        }

        protected Void doInBackground(Void... args) {
            Log.e("erro",targetUri.getPath());
            final String temp=nameproduct.getText().toString();

            final String referencename = temp;
            StorageReference riversRef = mStorageRef.child(referencename+".jpg");

            riversRef.putFile(targetUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            // Get a URL to the uploaded content
                            Uri downloadUrl = taskSnapshot.getDownloadUrl();
                            ResultSet count = sqlclass.querydata("select count(stock_id) as jumlah from stock");
                            try{
                                int a=0;
                                while (count.next()){
                                    a = count.getInt("jumlah");
                                }
                                String tempa = URLShortener.shortUrl(downloadUrl.toString());
                                String b ="insert into stock values("+a+",'"+tempa+"','"+referencename+".jpg"+"','"+nameproduct.getText().toString()+"','"+harga.getText().toString()+"',"+Integer.parseInt(stock.getText().toString())+",'"+kategori.getText().toString()+"','"+session.getusename()+"','"+berat.getText().toString()+"','"+keterangan.getText().toString()+"',0)";
                                Log.e("b", b);
                                int result = sqlclass.queryexecute(b);
                                Log.e("data sql",String.valueOf(result));
                                Log.e("data sql",tempa);
                            }catch (Exception e){
                                Log.e("SQL ERROR",e.getMessage());
                            }
                            Log.e("picture",downloadUrl.toString());
                            data=true;
                            onPostExecute(true,"Success");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            data=false;
                            // Handle unsuccessful uploads
                            onPostExecute(false,"failed");
                        }
                    });
            return null;
        }

        protected void onPostExecute(Boolean b,String a) {
            // do UI work here
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(getApplicationContext(),a, Toast.LENGTH_SHORT).show();
            if(b=true){
                finish();
            }
            else{

            }
        }
    }

    private final TextWatcher mTextEditorWatcher = new TextWatcher() {
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        public void onTextChanged(CharSequence s, int start, int before, int count) {
            //This sets a textview to the current length
            numbercount.setText(String.valueOf(s.length())+" / 100");
        }

        public void afterTextChanged(Editable s) {
        }
    };
}
