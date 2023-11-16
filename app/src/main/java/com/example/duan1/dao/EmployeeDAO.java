package com.example.duan1.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.database.DBHelper;
import com.example.duan1.model.Account;
import com.example.duan1.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private DBHelper dbHelper;
    public EmployeeDAO(Context context) {
        this.dbHelper = new DBHelper(context);
    }
    public List<Employee> getAll() {
        String sql = "SELECT * FROM " + DBHelper.TABLE_EMPLOYEE;
        List<Employee> list = getData(sql);
        if (list.size() > 0) {
            return list;
        }
        return null;
    }
    public Employee getID(String id) {
        String sql = "SELECT * FROM " + DBHelper.TABLE_EMPLOYEE + " WHERE email = ?";
        List<Employee> list = getData(sql, id);
        if (list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public boolean insert(Employee object) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "INSERT INTO " + DBHelper.TABLE_EMPLOYEE + " (email, name, phone, address, citizenshipID, status) " + " VALUES(?,?,?,?,?,?)";
        db.execSQL(sql, new String[]{ object.getEmail(), object.getName(), object.getPhone(), object.getAddress(), object.getCitizenshipID(), object.getStatus()});
        if (db != null) {
            db.close();
        }
        return true;
    }

    public boolean updateStatus(Employee object, String id) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "UPDATE " + DBHelper.TABLE_EMPLOYEE + " SET status = ? WHERE email = ?";
        sqLiteDatabase.execSQL(sql, new String[]{object.getStatus(), id});
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
        return true;
    }
    public boolean updatee(Employee object, String id) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "UPDATE " + DBHelper.TABLE_EMPLOYEE + " SET status = ? WHERE email = ?";
        sqLiteDatabase.execSQL(sql, new String[]{object.getStatus(), id});
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
        }
        return true;
    }
    private List<Employee> getData(String sql, String... selectionArgs) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        List<Employee> list = new ArrayList<>();

        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            list.add(new Employee(c.getInt(0), c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5), c.getString(6)));
        }
        return list;
    }
}
