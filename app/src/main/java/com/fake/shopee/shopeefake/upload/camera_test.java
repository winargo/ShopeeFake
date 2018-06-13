package com.fake.shopee.shopeefake.upload;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.sql.ResultSet;


public class camera_test extends Activity {
    private FirebaseAuth mAuth;
    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    ImageView done;
    SQLclass sqlclass;
    boolean data=false;
    TextView numbercount;
    session_class session;
    Uri targetUri=null;
    private StorageReference mStorageRef;
    EditText nameproduct,harga,berat,stock,kategori,keterangan;
    byte[] tempupload=null;

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
        if (currentUser==null){
            Intent i = new Intent(camera_test.this,loginactivity.class);
            startActivity(i);
            finish();
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);

        sqlclass = new SQLclass();

        session = new session_class(this);

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        kategori = (EditText) findViewById(R.id.kategoricamera);
        nameproduct=(EditText) findViewById(R.id.namaprodukcamera);
        harga = (EditText) findViewById(R.id.hargacamera);
        numbercount = (TextView) findViewById(R.id.numbercount);
        stock =(EditText) findViewById(R.id.stockcamera);
        berat =(EditText) findViewById(R.id.beratcamera);

        harga.addTextChangedListener(new thousandedittext(harga));

        done = (ImageButton) findViewById(R.id.donecamera);
        keterangan = (EditText)  findViewById(R.id.keterangancamera);
        this.imageView = (ImageView)this.findViewById(R.id.imageView1);




        if(mAuth.getCurrentUser()==null){

        }
        else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        }
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameproduct.getText().toString().trim().equals("") || kategori.getText().toString().trim().equals("") || berat.getText().toString().trim().equals("") || harga.getText().toString().trim().equals("") || stock.getText().toString().trim().equals("") || keterangan.getText().toString().trim().equals("")){
                        Toast.makeText(getApplicationContext(),"Data Tidak lengkap",Toast.LENGTH_SHORT).show();
                }
                else{
                    YourAsyncTask b = new YourAsyncTask(camera_test.this);
                    b.execute();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            targetUri = getImageUri(getApplicationContext(), photo);
            imageView.setImageBitmap(photo);
            imageView.setDrawingCacheEnabled(true);
            imageView.buildDrawingCache();
            Bitmap bitmap = imageView.getDrawingCache();
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(),
                inImage, "Title", null);
        return Uri.parse(path);
    }

    private class YourAsyncTask extends AsyncTask<Void, Void, Void> {
        private ProgressDialog dialog;

        public YourAsyncTask(camera_test a) {
            dialog = new ProgressDialog(a, R.style.AppCompatAlertDialogStyle);
        }

        @Override
        protected void onPreExecute() {
            dialog.setMessage("Processing....");
            dialog.show();
        }

        protected Void doInBackground(Void... args) {
            String temp = nameproduct.getText().toString();

            final String referencename = temp;
            StorageReference riversRef = mStorageRef.child(referencename + ".jpg");

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
                                String b ="insert into stock values("+a+",'"+tempa+"','"+nameproduct.getText().toString()+"','"+harga.getText().toString().substring(3)+"',"+Integer.parseInt(stock.getText().toString())+",'"+kategori.getText().toString()+"','"+session.getusename()+"','"+berat.getText().toString()+"','"+keterangan.getText().toString()+"',0)";
                                Log.e("b", b.toString());
                                int result = sqlclass.queryexecute("insert into stock values("+a+",'"+tempa+"','"+referencename+".jpg"+"','"+nameproduct.getText().toString()+"','"+harga.getText().toString()+"',"+Integer.parseInt(stock.getText().toString())+",'"+kategori.getText().toString()+"','"+session.getusename()+"','"+berat.getText().toString()+"','"+keterangan.getText().toString()+"',0)");
                                Log.e("data sql",String.valueOf(result));
                                Log.e("data sql",tempa);
                            }catch (Exception e){
                                Log.e("SQL ERROR",e.getMessage());
                            }
                            Log.e("picture",downloadUrl.toString());
                            onPostExecute(true,"Success");
                            Log.e("picture", downloadUrl.toString());
                            onPostExecute(true, "Success");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            // Handle unsuccessful uploads
                            onPostExecute(false, "failed");
                        }
                    });
            return null;
        }

        protected void onPostExecute(Boolean b, String a) {
            // do UI work here
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
            if (b = true) {
                finish();
            } else {

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