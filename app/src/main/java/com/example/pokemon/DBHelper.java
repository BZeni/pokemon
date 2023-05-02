package com.example.pokemon;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    public static int idLogado = 0;

    public DBHelper(Context context) {
        super(context,"Pokedex.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase myDB) {

        myDB.execSQL("create table users(id Integer primary key autoincrement, username Text, password Text)");
        myDB.execSQL("create table favoritos(id Integer primary key autoincrement, userId Integer, pokemonId Integer)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase myDB, int i, int i1) {
        myDB.execSQL("drop table if exists users");
        myDB.execSQL("drop table if exists favoritos");
    }

    public Boolean insertNewUser(String username, String password){

        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",username);
        contentValues.put("password", password);
        long result = myDB.insert("users",null,contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkUsername(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ?", new String[]{username});
        if (cursor.getCount() > 0){
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("Range")
    public Boolean checkUserPass (String username, String password){

        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("select * from users where username = ? and password = ?", new String[]{username, password});
        if (cursor.getCount() > 0){
                cursor.moveToFirst();
                idLogado = cursor.getInt(cursor.getColumnIndex("id"));
                return true;
            } else {
                return false;
        }
    }

    public Boolean inserirNovoFavorito(Integer idUsuario, Integer idPokemon){

        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("idUsuario",idUsuario);
        contentValues.put("idPokemon", idPokemon);
        long result = myDB.insert("favoritos",null,contentValues);

        if (result == -1){
            return false;
        } else {
            return true;
        }
    }

    public Integer[] buscarFavoritos(int userId) {
        SQLiteDatabase myDB = this.getWritableDatabase();
        Integer[] listaPokemon = new Integer[] {};
        Cursor cursor = myDB.rawQuery("select * from favoritos where userId = ?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") Integer id = cursor.getInt(cursor.getColumnIndex("idPokemon"));
                listaPokemon = Arrays.copyOf(listaPokemon, listaPokemon.length + 1);
                listaPokemon[listaPokemon.length - 1] = id;
            } while (cursor.moveToNext());
        }
        cursor.close();
        return listaPokemon;
    }


}
