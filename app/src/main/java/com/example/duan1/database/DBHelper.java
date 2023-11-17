package com.example.duan1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "app.db";
    public static final String TABLE_ACCOUNT_SHOP = "account_shop";
    public static final String TABLE_ACCOUNT_CUSTOMER = "account_customer";
    public static final String TABLE_CUSTOMER = "customer";
    public static final String TABLE_BILL = "bill";
    public static final String TABLE_BILL_DETAIL = "bill_detail";
    public static final String TABLE_PRODUCT = "product";
    public static final String TABLE_PRODUCT_DETAIL = "product_detail";
    public static final String TABLE_EMPLOYEE = "employee";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_ACCOUNT_SHOP = "CREATE TABLE " + TABLE_ACCOUNT_SHOP + "(" +
                "email TEXT PRIMARY KEY UNIQUE NOT NULL, " +
                "password TEXT, " +
                "role TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT_SHOP);
        //Initialize data for the account_shop table
        String INSERT_ACCOUNT_SHOP_DATA = "INSERT INTO " + TABLE_ACCOUNT_SHOP + " (email, password, role) VALUES " +
                "('admin@gmail.com', 'password1', 'admin'), " +
                "('employee2@gmail.com', 'password2', 'employeeSp'), " +
                "('employee3@gmail.com', 'password3', 'employeeChef')";
        sqLiteDatabase.execSQL(INSERT_ACCOUNT_SHOP_DATA);

        String CREATE_TABLE_ACCOUNT_CUSTOMER = "CREATE TABLE " + TABLE_ACCOUNT_CUSTOMER + "(" +
                "email TEXT PRIMARY KEY UNIQUE NOT NULL, " +
                "password TEXT " +
                ")";
        sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT_CUSTOMER);
        //Initialize data for the account_customer table
        String INSERT_ACCOUNT_CUSTOMER_DATA = "INSERT INTO " + TABLE_ACCOUNT_CUSTOMER + " (email, password) VALUES " +
                        "('customer1@gmail.com', 'password1'), " +
                        "('customer2@gmail.com', 'password2'), " +
                        "('customer3@gmail.com', 'password3')";
        sqLiteDatabase.execSQL(INSERT_ACCOUNT_CUSTOMER_DATA);

        String CREATE_TABLE_CUSTOMER = "CREATE TABLE " + TABLE_CUSTOMER + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT UNIQUE NOT NULL, " +
                "name TEXT, " +
                "phone TEXT, " +
                "address TEXT, " +
                "birthday TEXT, " +
                "status TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_TABLE_CUSTOMER);
        //Initialize data for the customer table
        String INSERT_CUSTOMER_DATA = "INSERT INTO " + TABLE_CUSTOMER + " (id, email, name, phone, address, birthday, status) VALUES " +
                "(1, 'customer1@gmail.com', 'John Doe', '123456789', '123 Main St', '1990-01-01', 'active'), " +
                "(2, 'customer2@gmail.com', 'Jane Smith', '987654321', '456 Elm St', '1995-05-05', 'active'), " +
                "(3, 'customer3@gmail.com', 'Mike Johnson', '555555555', '789 Oak St', '1985-10-10', 'inactive')";
        sqLiteDatabase.execSQL(INSERT_CUSTOMER_DATA);

        String CREATE_TABLE_BILL = "CREATE TABLE " + TABLE_BILL + " " + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idEmployee TEXT, " +
                "idCustomer TEXT, " +
                "date TEXT, " +
                "shippingAddress TEXT, " +
                "status TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_TABLE_BILL);
        //Initialize data for the bill table
        String INSERT_BILL_DATA = "INSERT INTO " + TABLE_BILL + " (idEmployee, idCustomer, date, shippingAddress, status) VALUES " +
                "('employee1', 'customer1', '2022-01-01', '123 Main St', 'active'), " +
                "('employee2', 'customer2', '2022-02-02', '456 Elm St', 'active'), " +
                "('employee3', 'customer3', '2022-03-03', '789 Oak St', 'inactive')";
        sqLiteDatabase.execSQL(INSERT_BILL_DATA);

        String CREATE_TABLE_BILL_DETAIL = "CREATE TABLE " + TABLE_BILL_DETAIL + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idProduct INTEGER, " +
                "idBill INTEGER, " +
                "quantity INTEGER, " +
                "price INTEGER, " +
                "note TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_TABLE_BILL_DETAIL);
        //Initialize data for the bill_detail table
        String INSERT_BILL_DETAIL_DATA = "INSERT INTO " + TABLE_BILL_DETAIL + " (idProduct, idBill, quantity, price, note) VALUES " +
                        "(1, 1, 5, 10, 'Note 1'), " +
                        "(2, 1, 3, 15, 'Note 2'), " +
                        "(3, 2, 2, 20, 'Note 3')";
        sqLiteDatabase.execSQL(INSERT_BILL_DETAIL_DATA);

        String CREATE_TABLE_PRODUCT = "CREATE TABLE " + TABLE_PRODUCT + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "image BLOB, " +
                "price INTEGER, " +
                "quantitySold INTEGER, " +
                "status TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT);
        //Initialize data for the product table
        String INSERT_PRODUCT_DATA = "INSERT INTO " + TABLE_PRODUCT + " (name, image, price, quantitySold, status) VALUES " +
                "('Product 1', null, 10, 0, 'active'), " +
                "('Product 2', null, 15, 0, 'active'), " +
                "('Product 3', null, 20, 0, 'inactive')";
        sqLiteDatabase.execSQL(INSERT_PRODUCT_DATA);

        String CREATE_TABLE_PRODUCT_DETAIL = "CREATE TABLE " + TABLE_PRODUCT_DETAIL + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idProduct TEXT, " +
                "description TEXT" +
                ")";
        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT_DETAIL);
        //Initialize data for the product_detail table
        String INSERT_PRODUCT_DETAIL_DATA = "INSERT INTO " + TABLE_PRODUCT_DETAIL + " (idProduct, description) VALUES " +
                        "('product1', 'Description 1'), " +
                        "('product2', 'Description 2'), " +
                        "('product3', 'Description 3')";
        sqLiteDatabase.execSQL(INSERT_PRODUCT_DETAIL_DATA);

        String CREATE_TABLE_EMPLOYEE = "CREATE TABLE " + TABLE_EMPLOYEE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT REFERENCES " + TABLE_ACCOUNT_SHOP + "(email) , " +
                "name TEXT, " +
                "phone TEXT, " +
                "address TEXT, " +
                "citizenshipID TEXT, " +
                "status TEXT, " +
                "date TEXT"+ ")";
        sqLiteDatabase.execSQL(CREATE_TABLE_EMPLOYEE);
        //Initialize data for the employee table
        String INSERT_EMPLOYEE_DATA = "INSERT INTO " + TABLE_EMPLOYEE + " (id, email, name, phone, address, citizenshipID, status, date) VALUES " +
                "(1, 'employee1@gmail.com', 'Employee 1', '1234567890', 'Address 1', '1234567890', 'active', '2022-01-01'), " +
                "(2, 'employee2@gmail.com', 'Employee 2', '0987654321', 'Address 2', '0987654321', 'active', '2022-02-02'), " +
                "(3, 'employee3@gmail.com', 'Employee 3', '9876543210', 'Address 3', '9876543210', 'inactive', '2022-03-03')," +
                "(4, 'admin@gmail.com', 'Employee 4', '8765432109', 'Address 4', '8765432109', 'inactive', '2022-04-04')";

        sqLiteDatabase.execSQL(INSERT_EMPLOYEE_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL_DETAIL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_DETAIL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        onCreate(sqLiteDatabase);
    }
}
