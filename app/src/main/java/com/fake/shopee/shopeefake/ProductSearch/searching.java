package com.fake.shopee.shopeefake.ProductSearch;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.fake.shopee.shopeefake.DatabaseHandler;
import com.fake.shopee.shopeefake.R;
import com.fake.shopee.shopeefake.sqliteclass;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class searching extends AppCompatActivity {

    Button closeback ;
    EditText searchbox;
    SharedPreferences prefs;
    List<String> history;
    String passedkey;
    Adapter adapter;
    ImageButton searchback;
    ListView lvsearchhistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        prefs = this.getSharedPreferences("Settings", Context.MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searching);

        Bundle extras = getIntent().getExtras();
        if(extras !=null) {
            passedkey = extras.getString("KEY","");
        }

        final DatabaseHandler db = new DatabaseHandler(this);

        closeback = (Button) findViewById(R.id.closeback);
        searchbox = (EditText) findViewById(R.id.searchbox);
        searchback = (ImageButton) findViewById(R.id.searchback);
        lvsearchhistory = (ListView) findViewById(R.id.lvsearch);

        prefs= PreferenceManager.getDefaultSharedPreferences(this);
        final SharedPreferences.Editor editor = prefs.edit();

        final int l = prefs.getInt("keysqlite", 0);
        history = new ArrayList<String>();


        List<sqliteclass> contacts = db.getAllContacts();
        for (sqliteclass cn : contacts) {
            history.add(cn.get_searchhistory());
        }

        Collections.reverse(history);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                history );
        arrayAdapter.notifyDataSetChanged();
        lvsearchhistory.setAdapter(arrayAdapter);

        lvsearchhistory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(searching.this, searchresult.class);
                String data=(String)parent.getItemAtPosition(position);
                intent.putExtra("KEY",data);
                startActivity(intent);
                finish();
            }
        });

        searchbox.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    Intent a = new Intent(searching.this,searchresult.class);
                    a.putExtra("KEY",searchbox.getText().toString());
                    String temp = searchbox.getText().toString();
                    int data = l +1 ;
                    editor.putInt("keysqlite",data);
                    editor.apply();
                    db.addContact(new sqliteclass(l, temp));
                    startActivity(a);
                    finish();
                    return true;
                }
                return false;
            }
        });
        searchback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        closeback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
