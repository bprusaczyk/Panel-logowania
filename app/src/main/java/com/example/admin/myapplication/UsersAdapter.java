package com.example.admin.myapplication;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class UsersAdapter extends BaseAdapter {

    private ArrayList<Users> uzytkownicy;
    private LayoutInflater li;

    public UsersAdapter(Context kontekst, ArrayList<Users> uzytkownicy)
    {
        this.li = LayoutInflater.from(kontekst);
        this.uzytkownicy = uzytkownicy;
    }

    @Override
    public int getCount() {
        return uzytkownicy.size();
    }

    @Override
    public Users getItem(int i) {
        return uzytkownicy.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        PoleListy pl=new PoleListy();
        if(view == null)
        {
            pl = new PoleListy();
            view = li.inflate(R.layout.pole_listy, null);
            pl.nazwisko=(TextView)view.findViewById(R.id.plNazwisko);
            pl.imie=(TextView)view.findViewById(R.id.plImie);
            pl.login=(TextView)view.findViewById(R.id.plLogin);
            view.setTag(pl);
        }
        else
        {
            pl = (PoleListy) view.getTag();
        }
        pl.nazwisko.setText(uzytkownicy.get(i).nazwisko);
        pl.imie.setText(uzytkownicy.get(i).imie);
        pl.login.setText(uzytkownicy.get(i).getUsername());
        return view;
    }

    private class PoleListy
    {
        TextView nazwisko;
        TextView imie;
        TextView login;
    }
}
