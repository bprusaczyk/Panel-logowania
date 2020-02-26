package com.example.admin.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class Users {
    private int ID;
    private String Username;
    private String Password;
    private boolean Admin;
    public String imie;
    public String nazwisko;

    public Users(int id, String username, String password, boolean isAdmin) throws NoSuchAlgorithmException {
        this.ID = id;
        this.Username = username;
        this.Password = hash(password);
        this.Admin = isAdmin;
    }

    public Users(int id, String username, String password, boolean isAdmin, String imie, String nazwisko) throws NoSuchAlgorithmException{
        this.ID = id;
        this.Username = username;
        this.Password = hash(password);
        this.Admin = isAdmin;
        this.imie=imie;
        this.nazwisko=nazwisko;
    }

    public Users(int id, String username, boolean isAdmin, String imie, String nazwisko) throws NoSuchAlgorithmException{
        this.ID = id;
        this.Username = username;
        this.Admin = isAdmin;
        this.imie=imie;
        this.nazwisko=nazwisko;
    }

    public Users() {
    }

    public int getID() {
        return ID;
    }

    public String getUsername() {
        return Username;
    }

    public boolean isAdmin() { return Admin; }

    public String getPassword() {
        return Password;
    }

    public static boolean sprawdzDostepnoscLoginu(SQLiteDatabase db, String login)
    {
        String[] argumenty = {login};
        Cursor c = db.rawQuery("SELECT count(*) FROM users WHERE username=?", argumenty);
        c.moveToFirst();
        String value = c.getString(0);
        return !value.equals("0");
    }

    public static boolean sprawdzDostepnoscLoginu(SQLiteDatabase db, String login, int id)
    {
        String[] argumenty = {login, Integer.toString(id)};
        Cursor c = db.rawQuery("SELECT count(*) FROM users WHERE username=? AND id!=?", argumenty);
        c.moveToFirst();
        String value = c.getString(0);
        return !value.equals("0");
    }

    public static String hash(String haslo) throws NoSuchAlgorithmException
    {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(haslo.getBytes(StandardCharsets.UTF_8));
        return new String(Base64.getEncoder().encode(hash));
    }
}