package com.example.admin.myapplication;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.security.NoSuchAlgorithmException;

public class EdycjaActivity extends AppCompatActivity {

    Button edytujPrzycisk;
    Button usunPrzycisk;
    EditText login;
    EditText haslo;
    EditText imie;
    EditText nazwisko;
    CheckBox admin;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_edycja);
            edytujPrzycisk = (Button) findViewById(R.id.edytujPrzycisk);
            usunPrzycisk = (Button) findViewById(R.id.usunPrzycisk);
            login = (EditText) findViewById(R.id.edycjaLogin);
            haslo = (EditText) findViewById(R.id.edycjaHaslo);
            imie = (EditText) findViewById(R.id.edycjaImie);
            nazwisko = (EditText) findViewById(R.id.edycjaNazwisko);
            admin = (CheckBox) findViewById(R.id.edycjaAdmin);
            Bundle extras = getIntent().getExtras();
            login.setText(extras.getString("login"));
            imie.setText(extras.getString("imie"));
            nazwisko.setText(extras.getString("nazwisko"));
            admin.setChecked(extras.getBoolean("admin"));
            id = extras.getInt("id");
        }catch (Exception e){}
    }

    public void edytuj(View v)
    {
        try {
            if (login.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Wprowadź login", Toast.LENGTH_LONG).show();
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
                SQLiteDatabase db = SQLiteDatabase.openDatabase(path + "/bazadanych", null, SQLiteDatabase.OPEN_READWRITE);
                if (Users.sprawdzDostepnoscLoginu(db, login.getText().toString(), id)) {
                    Toast.makeText(getApplicationContext(), "Podany login jest już zajęty", Toast.LENGTH_LONG).show();
                    return;
                }
                if (!haslo.getText().toString().equals("")) {
                    Users u = new Users(id, login.getText().toString(), haslo.getText().toString(), admin.isChecked(), imie.getText().toString(), nazwisko.getText().toString());
                    ContentValues cv = new ContentValues();
                    cv.put("username", u.getUsername());
                    cv.put("password", u.getPassword());
                    cv.put("admin", u.isAdmin());
                    cv.put("imie", u.imie);
                    cv.put("nazwisko", u.nazwisko);
                    String[] argumenty = {Integer.toString(u.getID())};
                    db.update("users", cv, "id=?", argumenty);
                } else {
                    Users u = new Users(id, login.getText().toString(), admin.isChecked(), imie.getText().toString(), nazwisko.getText().toString());
                    ContentValues cv = new ContentValues();
                    cv.put("username", u.getUsername());
                    cv.put("admin", u.isAdmin());
                    cv.put("imie", u.imie);
                    cv.put("nazwisko", u.nazwisko);
                    String[] argumenty = {Integer.toString(u.getID())};
                    db.update("users", cv, "id=?", argumenty);
                }
                Toast.makeText(getApplicationContext(), "Zmodyfikowano dane użytkownika", Toast.LENGTH_LONG).show();
            } catch (SQLiteException sqle) {
                Toast.makeText(getApplicationContext(), "Błąd odczytu pliku", Toast.LENGTH_LONG).show();
            } catch (NoSuchAlgorithmException nsae) {
                Toast.makeText(getApplicationContext(), "Błąd", Toast.LENGTH_LONG).show();
            }
        }catch (Exception e){}
    }

    public void usun(View v)
    {
        try
        {
            String path = getApplicationInfo().dataDir;
            SQLiteDatabase db = SQLiteDatabase.openDatabase(path + "/bazadanych", null, SQLiteDatabase.OPEN_READWRITE);
            String[] argumenty={Integer.toString(id)};
            db.delete("users", "id=?", argumenty);
            Toast.makeText(getApplicationContext(), "Usunięto użytkownika", Toast.LENGTH_LONG).show();
        }
        catch (SQLiteException sqle)
        {
            Toast.makeText(getApplicationContext(), "Błąd odczytu pliku", Toast.LENGTH_LONG).show();
        }catch (Exception e){}
    }
}
