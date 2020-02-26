package com.example.admin.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    EditText login;
    EditText haslo;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            login = (EditText) findViewById(R.id.login);
            haslo = (EditText) findViewById(R.id.haslo);
            String path = getApplicationInfo().dataDir;
            try {
                db = SQLiteDatabase.openDatabase(path + "/bazadanych", null, SQLiteDatabase.CREATE_IF_NECESSARY);
                String[] argumenty = {"users"};
                Cursor c = db.rawQuery("SELECT count(*) FROM sqlite_master WHERE type='table' AND name=?", argumenty);
                c.moveToFirst();
                String value = c.getString(0);
                if (value.equals("0")) {
                    db.execSQL("CREATE TABLE users (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT, admin BOOLEAN, imie TEXT, nazwisko TEXT)");
                    Users u = new Users(1, "admin", "secure", true, "Jan", "Kowalski");
                    ContentValues cv = new ContentValues();
                    cv.put("id", u.getID());
                    cv.put("username", u.getUsername());
                    cv.put("password", u.getPassword());
                    cv.put("admin", u.isAdmin());
                    cv.put("imie", u.imie);
                    cv.put("nazwisko", u.nazwisko);
                    db.insert("users", null, cv);
                    Users u1 = new Users(2, "user1", "12345", false, "Jan", "Nowak");
                    ContentValues cv2 = new ContentValues();
                    cv2.putNull("id");
                    cv2.put("username", u1.getUsername());
                    cv2.put("password", u1.getPassword());
                    cv2.put("admin", u1.isAdmin());
                    cv2.put("imie", u1.imie);
                    cv2.put("nazwisko", u1.nazwisko);
                    db.insert("users", null, cv2);
                    Users u2 = new Users(3, "user2", "qwerty", false, "Anna", "Nowak");
                    ContentValues cv3 = new ContentValues();
                    cv3.putNull("id");
                    cv3.put("username", u2.getUsername());
                    cv3.put("password", u2.getPassword());
                    cv3.put("admin", u2.isAdmin());
                    cv3.put("imie", u2.imie);
                    cv3.put("nazwisko", u2.nazwisko);
                    db.insert("users", null, cv3);
                }
            } catch(SQLiteException e)
            {
                Toast.makeText(getApplicationContext(), "Błąd odczytu pliku", Toast.LENGTH_LONG).show();
            }
            catch (NoSuchAlgorithmException nsae) {
                Toast.makeText(getApplicationContext(), "Błąd", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e){}
    }

    public void login1(View v)
    {
        try {
            String[] kolumny = {"count(*) AS total"};
            String[] argumenty = {login.getText().toString(), Users.hash(haslo.getText().toString())};
            Cursor c = db.query("users", kolumny, "username=? AND password=?", argumenty, null, null, null);
            c.moveToFirst();
            String value = c.getString(0);
            if (value.equals("0")) {
                Toast.makeText(getApplicationContext(), "Niepoprawny login lub hasło", Toast.LENGTH_LONG).show();
            } else {
                Intent i = new Intent(MainActivity.this, ZalogowanyActivity.class);
                i.putExtra("login", login.getText().toString());
                i.putExtra("hasło", haslo.getText().toString());
                startActivity(i);
            }
        }
        catch (SQLiteException sqle)
        {
            Toast.makeText(getApplicationContext(), "Błąd odczytu pliku", Toast.LENGTH_LONG).show();
        }
        catch (NoSuchAlgorithmException nsae) {
            Toast.makeText(getApplicationContext(), "Błąd", Toast.LENGTH_LONG).show();
        }
        catch(Exception e){}
    }

    public void login2(View v)
    {
        try {
            String[] argumenty = {login.getText().toString(), Users.hash(haslo.getText().toString())};
            Cursor c = db.rawQuery("SELECT count(*) AS total FROM users WHERE username='"+login.getText().toString()+"' AND password='"+haslo.getText().toString()+"'", null);
            //Cursor c = db.rawQuery("SELECT count(*) AS total FROM users WHERE username=? AND password=?", argumenty);
            c.moveToFirst();
            String value = c.getString(0);
            if (value.equals("0")) {
                Toast.makeText(getApplicationContext(), "Niepoprawny login lub hasło", Toast.LENGTH_LONG).show();
            } else {
                Intent i = new Intent(MainActivity.this, ZalogowanyActivity.class);
                i.putExtra("login", login.getText().toString());
                i.putExtra("hasło", haslo.getText().toString());
                startActivity(i);
            }
        }
        catch (SQLiteException sqle)
        {
            Toast.makeText(getApplicationContext(), "Błąd odczytu pliku", Toast.LENGTH_LONG).show();
        }
        catch (NoSuchAlgorithmException nsae)
        {
            Toast.makeText(getApplicationContext(), "Błąd", Toast.LENGTH_LONG).show();
        }
        catch (Exception e){}
    }
}