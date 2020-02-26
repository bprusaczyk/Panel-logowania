package com.example.admin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

public class ZmianaHaslaActivity extends AppCompatActivity {

    EditText haslo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_zmiana_hasla);
            haslo = (EditText) findViewById(R.id.noweHaslo);
        }catch (Exception e){}
    }

    public void zmienHaslo(View v)
    {
        try {
            String path = getApplicationInfo().dataDir;
            try {
                SQLiteDatabase db = SQLiteDatabase.openDatabase(path + "/bazadanych", null, SQLiteDatabase.OPEN_READWRITE);
                String[] argumenty = {Users.hash(haslo.getText().toString())};
                db.execSQL("UPDATE users SET password=? WHERE username='admin'", argumenty);
                Intent i = new Intent(ZmianaHaslaActivity.this, ZalogowanyActivity.class);
                i.putExtra("hasło", haslo.getText().toString());
                setResult(Activity.RESULT_OK, i);
                finish();
            }
            catch (SQLiteException e)
            {
                Toast.makeText(getApplicationContext(), "Błąd odczytu pliku", Toast.LENGTH_LONG).show();
            }
            catch (NoSuchAlgorithmException nsae)
            {
                Toast.makeText(getApplicationContext(), "Błąd", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){}
    }
}
