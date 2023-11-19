package com.example.duan1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.database.DBHelper;
import com.example.duan1.model.Bill;

import java.util.ArrayList;
import java.util.List;

public class BillDAO {
    private DBHelper dbHelper;
    public BillDAO(Context context){
        this.dbHelper = new DBHelper(context);
    }

    public List<Bill> getAll(){
        String sql = "SELECT * FROM " + DBHelper.TABLE_BILL;
        List<Bill> list = getData(sql);
        if (list.size() > 0){
            return list;
        }
        return null;
    }
    public List<Bill> getAllCus(String email){
        String sql = "SELECT * FROM " + DBHelper.TABLE_BILL + " WHERE emailCus = ? ";
        List<Bill> list = getData(sql, email);
        if (list.size() > 0){
            return list;
        }
        return null;
    }

    public Bill getID(int id){
        String sql = "SELECT * FROM " + DBHelper.TABLE_BILL + " WHERE id = ?";
        List<Bill> list = getData(sql, String.valueOf(id));
        if (list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    public boolean insertCus(Bill object){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "INSERT INTO " + DBHelper.TABLE_BILL + "(idEmployee, idCustomer, date, shippingAddress, status, emailCus)"  + " VALUES(?,?,?,?,?,?)";
        db.execSQL(sql, new String[]{String.valueOf(object.getIdEmployee()), object.getIdCustomer(), object.getDate(), object.getShippingAddress(), object.getStatus(), object.getEmail()});
        if (db != null){
            db.close();
        }
        return true;
    }


    public boolean updatee(Bill object){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "UPDATE " + DBHelper.TABLE_BILL + " SET idEmployee = ?, idCustomer = ?, date = ?, shippingAddress = ?, status = ? WHERE id = ?";
        db.execSQL(sql, new String[]{object.getIdEmployee(), object.getIdCustomer(), object.getDate(), object.getShippingAddress(), object.getStatus(), String.valueOf(object.getId())});
        if (db != null){
            db.close();
        }
        return true;
    }

    public boolean delete(int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "DELETE FROM " + DBHelper.TABLE_BILL + " WHERE id = ?";
        db.execSQL(sql, new String[]{String.valueOf(id)});
        if (db != null){
            db.close();
        }
        return true;
    }

    private List<Bill> getData(String sql, String...selectionArgs){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Bill> list = new ArrayList<>();

        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()){
            list.add(new Bill(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)));
        }
        if (c != null){
            c.close();
        }
        if (db != null){
            db.close();
        }
        return list;
    }
}
