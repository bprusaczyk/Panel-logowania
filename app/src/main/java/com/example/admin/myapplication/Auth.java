package com.example.admin.myapplication;

public class Auth {
    public String Username;
    public boolean isAdmin;
    private String Password;
    //public static final String SALT1="Fjh9*";
    //public static final String SALT2="^JJkd$4";


    public Auth(String username, String haslo, boolean isAdmin)
    {
        this.Username=username;
        this.Password=haslo;
        this.isAdmin=isAdmin;
    }

    public boolean zaloguj(String login, String haslo)
    {
        if(this.Username.equals(login) && this.Password.equals(haslo))
        {
            return true;
        }
        else return false;
    }
}

