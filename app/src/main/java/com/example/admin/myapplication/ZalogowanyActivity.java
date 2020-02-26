package com.example.admin.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ZalogowanyActivity extends AppCompatActivity {

    String login;
    String haslo;
    TextView loginEkran;
    TextView hasloEkran;
    Button zarzadzajUzytkownikamiPrzycisk;
    final int ID=1000;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_zalogowany);
            Bundle extras = getIntent().getExtras();
            login = extras.getString("login");
            haslo = extras.getString("hasło");
            if (login.equals("admin") && haslo.equals("secure")) {
                Intent i = new Intent(ZalogowanyActivity.this, ZmianaHaslaActivity.class);
                startActivityForResult(i, 1000);
            }
            loginEkran = (TextView) findViewById(R.id.loginZ);
            hasloEkran = (TextView) findViewById((R.id.ranga));
            zarzadzajUzytkownikamiPrzycisk =(Button)findViewById(R.id.zarzadzajUzytkownikami);
            loginEkran.setText(login);
            hasloEkran.setText(haslo);
            String path = getApplicationInfo().dataDir;
            try {
                db = SQLiteDatabase.openDatabase(path + "/bazadanych", null, SQLiteDatabase.OPEN_READONLY);
                String[] argumenty = {extras.getString("login")};
                Cursor c = db.rawQuery("SELECT admin FROM users WHERE username=?", argumenty);
                c.moveToFirst();
                boolean value = c.getInt(0) > 0;
                if (value) {
                    zarzadzajUzytkownikamiPrzycisk.setVisibility(View.VISIBLE);
                }
            }
            catch (SQLiteException sqle)
            {
                Toast.makeText(getApplicationContext(), "Błąd odczytu pliku", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){}
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if ((requestCode == ID) && (resultCode == Activity.RESULT_OK) && (data != null)) {
                haslo = data.getStringExtra("hasło");
                hasloEkran.setText(haslo);
                Toast.makeText(getApplicationContext(), "Hasło zostało zmienione", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){}
    }

    public void zarzadzajUzytkownikami(View v)
    {
        Intent i =new Intent(ZalogowanyActivity.this, ZarzadzanieUzytkownikamiActivity.class);
        startActivity(i);
    }
}
