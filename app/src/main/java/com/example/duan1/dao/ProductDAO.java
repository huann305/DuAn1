package com.example.duan1.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.database.DBHelper;
import com.example.duan1.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private DBHelper dbHelper;
    public ProductDAO(Context context){
        this.dbHelper = new DBHelper(context);
    }

    public List<Product> getAll(){
        String sql = "SELECT * FROM " + DBHelper.TABLE_PRODUCT;
        List<Product> list = getData(sql);
        if (list.size() > 0){
            return list;
        }
        return null;
    }

    public Product getID(int id){
        String sql = "SELECT * FROM " + DBHelper.TABLE_PRODUCT + " WHERE id = ?";
        List<Product> list = getData(sql, String.valueOf(id));
        if (list.size() > 0){
            return list.get(0);
        }
        return null;
    }

    public boolean insert(Product product){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "INSERT INTO " + DBHelper.TABLE_PRODUCT + "(name, image, price, quantitySold, status)" + " VALUES(?,?,?,?,?)";
        db.execSQL(sql, new String[]{String.valueOf(product.getName()), product.getImage(), String.valueOf(product.getPrice()), String.valueOf(product.getQuantitySold()), product.getStatus()});
        if (db != null){
            db.close();
        }
        return true;
    }

    public boolean updatee(Product product, int id){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String sql = "UPDATE " + DBHelper.TABLE_PRODUCT + " SET name = ?, image = ?, price = ?, quantitySold = ?, status = ?" + " WHERE id = ?";
        db.execSQL(sql, new String[]{product.getName(), product.getImage(), String.valueOf(product.getPrice()), String.valueOf(product.getQuantitySold()), product.getStatus(), String.valueOf(id)});
        if (db != null){
            db.close();
        }
        return true;
    }
    private List<Product> getData(String sql, String...selectionArgs){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Product> list = new ArrayList<>();

        Cursor cursor = db.rawQuery(sql, selectionArgs);
        while (cursor.moveToNext()){
            list.add(new Product(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5)));
        }
        if (cursor != null){
            cursor.close();
        }
        if (db != null){
            db.close();
        }
        return list;
    }
}