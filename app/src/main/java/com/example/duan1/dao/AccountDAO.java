package com.example.duan1.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.database.DBHelper;
import com.example.duan1.model.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    DBHelper dbHelper;

    public AccountDAO(Context context) {
        dbHelper = new DBHelper(context);
    }

    public Account getID(String id, String TABLE) {
        String sql = "SELECT * FROM " + TABLE + " WHERE email = ?";
        List<Account> list = getData(sql, id);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public List<Account> getAll(String TABLE) {
        String sql = "SELECT * FROM " + TABLE;
        List<Account> list = getData(sql, TABLE);
        if (list.size() > 0) {
            return list;
        }
        return null;
    }

    public boolean insert(Account object) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "INSERT INTO " + DBHelper.TABLE_ACCOUNT_CUSTOMER + " VALUES(?,?,?)";
        db.execSQL(sql, new String[]{object.getEmail(), object.getPassword(), object.getRole()});
        if(db != null) {
            db.close();
        }
        return true;
    }

    public boolean updatee(Account object, String id) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "UPDATE " + DBHelper.TABLE_ACCOUNT_CUSTOMER + " SET password = ? WHERE email = ?";
        sqLiteDatabase.execSQL(sql, new String[]{object.getPassword(), id});
        if(sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
        return true;
    }

    public void delete(Object object) {

    }

    private List<Account> getData(String sql, String TABLE, String... selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Account> list = new ArrayList<>();

        Cursor c = db.rawQuery(sql, selectionArgs);
        if (TABLE.equals(DBHelper.TABLE_ACCOUNT_CUSTOMER)) {
            while (c.moveToNext()) {
                list.add(new Account(c.getString(0), c.getString(1), null));
            }
        } else {
            while (c.moveToNext()) {
                list.add(new Account(c.getString(0), c.getString(1), c.getString(2)));
            }
        }

        return list;
    }

    public boolean checkLogin(String email, String password, String TABLE) {
        String sql = "SELECT * FROM " + TABLE + " WHERE email = ? AND password = ?";
        List<Account> list = getData(sql, TABLE, email, password);
        if (list.size() > 0) {
            return true;
        }
        return false;
    }
}
