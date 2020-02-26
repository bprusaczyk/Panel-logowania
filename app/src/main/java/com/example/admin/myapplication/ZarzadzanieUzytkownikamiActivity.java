package com.example.admin.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class ZarzadzanieUzytkownikamiActivity extends AppCompatActivity {

    ListView lv;
    Button dodajPrzycisk;
    ArrayList<Users> uzytkownicy;
    UsersAdapter adapter;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_zarzadzanie_uzytkownikami);
            lv = (ListView) findViewById(R.id.listaUzytkownikow);
            lv.setOnItemClickListener(myClickListener);
            dodajPrzycisk = (Button) findViewById(R.id.listaDodaj);
            uzytkownicy = new ArrayList<Users>();
            String path = getApplicationInfo().dataDir;
            try {
                db = SQLiteDatabase.openDatabase(path + "/bazadanych", null, SQLiteDatabase.OPEN_READONLY);
                uzytkownicy = zwrocUzytkownikow();
                adapter = new UsersAdapter(this, uzytkownicy);
                lv.setAdapter(adapter);
            } catch (SQLiteException sqle) {
                Toast.makeText(getApplicationContext(), "Błąd odczytu pliku", Toast.LENGTH_LONG).show();
            } catch (NoSuchAlgorithmException nsae) {
                Toast.makeText(getApplicationContext(), "Błąd", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){}
    }

    private ArrayList<Users> zwrocUzytkownikow() throws NoSuchAlgorithmException {
        ArrayList<Users> al=new ArrayList<Users>();
        Cursor c = db.rawQuery("SELECT id, username, admin, imie, nazwisko FROM users", null);
        if (c.moveToFirst()) {
            do {
                al.add(new Users(c.getInt(0), c.getString(1),c.getInt(2) > 0, c.getString(3), c.getString(4)));
            } while (c.moveToNext());
        }
        return al;
    }

    @Override
    protected void onPostResume() {
        try {
            super.onPostResume();
            try {
                uzytkownicy = zwrocUzytkownikow();
                adapter = new UsersAdapter(this, uzytkownicy);
                lv.setAdapter(adapter);
            } catch (NoSuchAlgorithmException nsae) {
                Toast.makeText(getApplicationContext(), "Błąd", Toast.LENGTH_LONG).show();
            } catch (SQLiteException sqle) {
                Toast.makeText(getApplicationContext(), "Błąd odczytu pliku", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){}
    }

    public void dodaj(View v)
    {
        try {
            Intent i = new Intent(ZarzadzanieUzytkownikamiActivity.this, DodajActivity.class);
            startActivity(i);
        }catch (Exception e){}
    }

    public AdapterView.OnItemClickListener myClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
            try {
                Intent i = new Intent(ZarzadzanieUzytkownikamiActivity.this, EdycjaActivity.class);
                Users u = adapter.getItem(position);
                i.putExtra("id", u.getID());
                i.putExtra("login", u.getUsername());
                i.putExtra("admin", u.isAdmin());
                i.putExtra("imie", u.imie);
                i.putExtra("nazwisko", u.nazwisko);
                startActivity(i);
            }catch (Exception e){}
        }
    };
}
