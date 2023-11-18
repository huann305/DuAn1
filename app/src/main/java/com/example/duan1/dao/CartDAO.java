package com.example.duan1.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1.database.DBHelper;
import com.example.duan1.model.Cart;
import com.example.duan1.model.Product;

import java.util.ArrayList;

public class CartDAO {
    DBHelper dbhelper;

    public CartDAO(Context context) {
        dbhelper = new DBHelper(context);
    }

    public ArrayList<Cart> getAllCart() {
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        ArrayList<Cart> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM cart", null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                list.add(new Cart(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4)));
            } while (cursor.moveToNext());
        }
        return list;
    }

    public boolean insertToCart(Product product) {
        // Mở cơ sở dữ liệu để ghi
        SQLiteDatabase database = dbhelper.getWritableDatabase();
        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
        Cursor cursor = database.rawQuery("SELECT * FROM cart WHERE name = ?", new String[]{product.getName()});
        long check;

        if (cursor.getCount() > 0) {
            // Nếu sản phẩm đã tồn tại, số lượng trong giỏ hàng tăng thêm 1
            cursor.moveToFirst();
            int currentQuantity = cursor.getInt(3);
            currentQuantity += 1;

            ContentValues values = new ContentValues();
            values.put("quantity", currentQuantity);
            check = database.update(DBHelper.TABLE_CART, values, "name = ?", new String[]{product.getName()});

        } else {
            // Nếu sản phẩm chưa tồn tại, thêm mới vào giỏ hàng
            int quantity = 1;
            ContentValues values = new ContentValues();
            values.put("idProduct", product.getId());
            values.put("name", product.getName());
            values.put("quantity", quantity);
            values.put("price", product.getPrice());
            check = database.insert(DBHelper.TABLE_CART, null, values);
        }
        if (check == -1) {
            return false;
        }
        return true;
    }

    //tăng sản phẩm bằng btn trong giỏ hàng, tăng số lượng sản phẩm trong giỏ hàng
    public boolean augmentQuantity(Cart cart) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int quantity = cart.getQuantity();
        quantity++;
        contentValues.put("quantity", quantity);
        long check = sqLiteDatabase.update(DBHelper.TABLE_CART, contentValues, "id = ?", new String[]{String.valueOf(cart.getId())});
        if (check == -1) {
            return false;
        }
        return true;
    }

    public boolean reduceQuantity(Cart cart) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        int quantity = cart.getQuantity();
        quantity--;
        contentValues.put("quantity", quantity);
        long check = sqLiteDatabase.update(DBHelper.TABLE_CART, contentValues, "id = ?", new String[]{String.valueOf(cart.getId())});
        if (check == -1) {
            return false;
        }
        return true;
    }
    public boolean deleteCart(int id){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT id FROM cart WHERE id = ?", new String[]{String.valueOf(id)});
        if(cursor.getCount() != 0){
            long check = sqLiteDatabase.delete(DBHelper.TABLE_CART, "id = ?", new String[]{String.valueOf(id)});
            if (check == -1) {
                return false;
            } else
                return true;
        }
        return false;
    }
}
