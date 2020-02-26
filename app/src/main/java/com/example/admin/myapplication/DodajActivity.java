package com.example.admin.myapplication;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

public class DodajActivity extends AppCompatActivity {

    EditText login;
    EditText haslo;
    EditText imie;
    EditText nazwisko;
    CheckBox admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_dodaj);
            login = (EditText) findViewById(R.id.dodajLogin);
            haslo = (EditText) findViewById(R.id.dodajHaslo);
            imie = (EditText) findViewById(R.id.dodajImie);
            nazwisko = (EditText) findViewById(R.id.dodajNazwisko);
            admin = (CheckBox) findViewById(R.id.dodajAdmin);
        }catch(Exception e){}
    }

    public void dodaj(View v)
    {
        try {
            if (login.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Wprowadź login", Toast.LENGTH_LONG).show();
                return;
            }
            if (haslo.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Wprowadź hasło", Toast.LENGTH_LONG).show();
                return;
            }
            if (imie.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Wprowadź imię", Toast.LENGTH_LONG).show();
                return;
            }
            if (nazwisko.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Wprowadź nazwisko", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                String path = getApplicationInfo().dataDir;
                SQLiteDatabase db = SQLiteDatabase.openDatabase(path + "/bazadanych", null, SQLiteDatabase.CREATE_IF_NECESSARY);
                if (Users.sprawdzDostepnoscLoginu(db, login.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Podany login jest już zajęty", Toast.LENGTH_LONG).show();
                    return;
                }
                Users u = new Users(0, login.getText().toString(), haslo.getText().toString(), admin.isChecked(), imie.getText().toString(), nazwisko.getText().toString());
                ContentValues cv = new ContentValues();
                cv.putNull("id");
                cv.put("username", u.getUsername());
                cv.put("password", u.getPassword());
                cv.put("admin", u.isAdmin());
                cv.put("imie", u.imie);
                cv.put("nazwisko", u.nazwisko);
                db.insert("users", null, cv);
                Toast.makeText(getApplicationContext(), "Dodano nowego użytkownika", Toast.LENGTH_LONG).show();
            } catch (SQLiteException sqle) {
                Toast.makeText(getApplicationContext(), "Błąd odczytu pliku", Toast.LENGTH_LONG).show();
            } catch (NoSuchAlgorithmException nsae) {
                Toast.makeText(getApplicationContext(), "Błąd", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){}
    }
}
