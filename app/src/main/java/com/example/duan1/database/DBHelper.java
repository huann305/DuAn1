package com.example.duan1.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "duan1.db";
    public static final String TABLE_ACCOUNT_SHOP = "account_shop";
    public static final String TABLE_ACCOUNT_CUSTOMER = "account_customer";
    public static final String TABLE_CUSTOMER = "customer";
    public static final String TABLE_BILL = "bill";
    public static final String TABLE_BILL_DETAIL = "bill_detail";
    public static final String TABLE_PRODUCT = "product";
    public static final String TABLE_PRODUCT_DETAIL = "product_detail";
    public static final String TABLE_EMPLOYEE = "employee";
    public static final String TABLE_CART = "cart";

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 3);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE_ACCOUNT_SHOP = "CREATE TABLE " + TABLE_ACCOUNT_SHOP + "(" +
                "email TEXT PRIMARY KEY UNIQUE NOT NULL, " +
                "password TEXT, " +
                "role TEXT" +
                ")";

        String CREATE_TABLE_ACCOUNT_CUSTOMER = "CREATE TABLE " + TABLE_ACCOUNT_CUSTOMER + "(" +
                "email TEXT PRIMARY KEY UNIQUE NOT NULL, " +
                "password TEXT " +
                ")";

        String CREATE_TABLE_CUSTOMER = "CREATE TABLE " + TABLE_CUSTOMER + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT UNIQUE NOT NULL, " +
                "name TEXT, " +
                "phone TEXT, " +
                "address TEXT, " +
                "birthday TEXT, " +
                "status TEXT, " +
                "image TEXT" +
                ")";

        String CREATE_TABLE_BILL = "CREATE TABLE " + TABLE_BILL + " " + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idEmployee TEXT, " +
                "idCustomer TEXT, " +
                "date TEXT, " +
                "shippingAddress TEXT, " +
                "status TEXT," +
                "emailCus TEXT REFERENCES " + TABLE_CUSTOMER + "(email)," +
                "paymentMethod TEXT" + ")";

        String CREATE_TABLE_BILL_DETAIL = "CREATE TABLE " + TABLE_BILL_DETAIL + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idProduct INTEGER, " +
                "idBill INTEGER REFERENCES " + TABLE_BILL + "(id), " +
                "quantity INTEGER, " +
                "price INTEGER, " +
                "note TEXT" +
                ")";

        String CREATE_TABLE_PRODUCT = "CREATE TABLE " + TABLE_PRODUCT + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "name TEXT, " +
                "price INTEGER, " +
                "quantitySold INTEGER, " +
                "quantity INTEGER, " +
                "status TEXT, " +
                "image TEXT" + ")";

        String CREATE_TABLE_PRODUCT_DETAIL = "CREATE TABLE " + TABLE_PRODUCT_DETAIL + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idProduct INTEGER, " +
                "description TEXT, " +
                "image TEXT REFERENCES " + TABLE_PRODUCT + "(image)" +
                ")";
        String CREATE_TABLE_EMPLOYEE = "CREATE TABLE " + TABLE_EMPLOYEE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "email TEXT REFERENCES " + TABLE_ACCOUNT_SHOP + "(email) , " +
                "name TEXT, " +
                "phone TEXT, " +
                "address TEXT, " +
                "citizenshipID TEXT, " +
                "status TEXT, " +
                "date TEXT, " +
                "image TEXT" + ")";
        String CREATE_TABLE_CART = "CREATE TABLE " + TABLE_CART + " (" +
                "id  INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "idProduct INTEGER REFERENCES " + TABLE_PRODUCT + "(id)," +
                "name TEXT REFERENCES " + TABLE_PRODUCT + "(name)," +
                "quantity INTEGER," +
                "price INTEGER REFERENCES " + TABLE_PRODUCT + "(price), " +
                "emailCus TEXT REFERENCES " + TABLE_CUSTOMER + "(email)," +
                "image TEXT REFERENCES " + TABLE_PRODUCT + "(image)" + ")";

        sqLiteDatabase.execSQL(CREATE_TABLE_CART);
        sqLiteDatabase.execSQL(CREATE_TABLE_EMPLOYEE);
        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT_DETAIL);
        sqLiteDatabase.execSQL(CREATE_TABLE_PRODUCT);
        sqLiteDatabase.execSQL(CREATE_TABLE_BILL_DETAIL);
        sqLiteDatabase.execSQL(CREATE_TABLE_BILL);
        sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT_CUSTOMER);
        sqLiteDatabase.execSQL(CREATE_TABLE_ACCOUNT_SHOP);
        sqLiteDatabase.execSQL(CREATE_TABLE_CUSTOMER);

        //Initialize data for the account_shop table
        String INSERT_ACCOUNT_SHOP_DATA = "INSERT INTO " + TABLE_ACCOUNT_SHOP + " (email, password, role) VALUES " +
                "('admin@gmail.com', 'password1', 'admin'), " +
                "('employee1@gmail.com', 'password1', 'employeeSp'), " +
                "('employee2@gmail.com', 'password2', 'employeeSp'), " +
                "('employee3@gmail.com', 'password3', 'employeeChef')";
        sqLiteDatabase.execSQL(INSERT_ACCOUNT_SHOP_DATA);

        //Initialize data for the account_customer table
        String INSERT_ACCOUNT_CUSTOMER_DATA = "INSERT INTO " + TABLE_ACCOUNT_CUSTOMER + " (email, password) VALUES " +
                "('customer1@gmail.com', 'password1'), " +
                "('customer2@gmail.com', 'password2'), " +
                "('customer3@gmail.com', 'password3')";
        sqLiteDatabase.execSQL(INSERT_ACCOUNT_CUSTOMER_DATA);
        //Initialize data for the customer table
        String INSERT_CUSTOMER_DATA = "INSERT INTO " + TABLE_CUSTOMER + " (id, email, name, phone, address, birthday, status, image) VALUES " +
                "(1, 'customer1@gmail.com', 'John Doe', '123456789', '123 Main St', '1990-01-01', 'active', null), " +
                "(2, 'customer2@gmail.com', 'Jane Smith', '987654321', '456 Elm St', '1995-05-05', 'active', null), " +
                "(3, 'customer3@gmail.com', 'Mike Johnson', '555555555', '789 Oak St', '1985-10-10', 'inactive', null)";
        sqLiteDatabase.execSQL(INSERT_CUSTOMER_DATA);
        //Initialize data for the bill table
        String INSERT_BILL_DATA = "INSERT INTO " + TABLE_BILL + " (idEmployee, idCustomer, date, shippingAddress, status, paymentMethod) VALUES " +
                "('1', '1', '2022-01-01', '123 Main St', 'Đang chờ', 'Tiền mặt'), " +
                "('2', '1', '2022-02-02', '456 Elm St', 'Đang làm', 'Tiền mặt' ), " +
                "('2', '3', '2022-02-02', '456 Elm St', 'Đã hoàn thành', 'Tiền mặt'), " +
                "('3', '2', '2022-03-03', '789 Oak St', 'Xác nhận thanh toán', 'Zalo Pay')";
        sqLiteDatabase.execSQL(INSERT_BILL_DATA);
        //Initialize data for the bill_detail table
        String INSERT_BILL_DETAIL_DATA = "INSERT INTO " + TABLE_BILL_DETAIL + " (idProduct, idBill, quantity, price, note) VALUES " +
                "(1, 1, 5, 10, 'Note 1'), " +
                "(2, 1, 3, 15, 'Note 2'), " +
                "(3, 2, 2, 20, 'Note 3')";
        sqLiteDatabase.execSQL(INSERT_BILL_DETAIL_DATA);
        //Initialize data for the product table
        String INSERT_PRODUCT_DATA = "INSERT INTO " + TABLE_PRODUCT + " (name, price, quantitySold,quantity ,status, image) VALUES " +
                "('Ức gà trộn', 100000, 1,100 ,'Còn hàng', 'https://i.pinimg.com/564x/61/e1/cd/61e1cd8c1604d9d847318013692542a1.jpg'), " +
                "('Gà sốt kem', 150000, 2,50 ,'Còn hàng', 'https://i.pinimg.com/564x/a8/e2/0d/a8e20d82b13a3473f0b42cbf35dfeef7.jpg'), " +
                "('Đùi gà chiên', 150000, 3,100 ,'Còn hàng', 'https://i.pinimg.com/564x/e2/ff/4c/e2ff4c5e2b22df37d161ec12900bd6d4.jpg'), " +
                "('Salad gà', 150000, 4,200 ,'Còn hàng', 'https://i.pinimg.com/236x/06/95/52/0695523adb4489059bfce5299fd924c8.jpg'), " +
                "('Combo gà 1', 150000, 6,300 ,'Còn hàng', 'https://i.pinimg.com/564x/75/fd/d7/75fdd7dc538330cf7523f7da3d2566b6.jpg'), " +
                "('Bánh gà phomai', 150000, 5,120, 'Còn hàng', 'https://i.pinimg.com/236x/18/7a/8d/187a8d7677da78e1ba91729a945b8f6a.jpg'), " +
                "('Trà sữa caccao', 150000, 3,130 ,'Còn hàng', 'https://i.pinimg.com/564x/62/3f/ba/623fba0e89cb093c81d0add77826a149.jpg'), " +
                "('Trà sữa thái', 15000, 8,140 ,'Còn hàng', 'https://i.pinimg.com/564x/68/ef/9e/68ef9e4beef033b43de718970ed4e25a.jpg'), " +
                "('Combo sữa hạt', 20000, 10,160 ,'Còn hàng', 'https://i.pinimg.com/564x/d9/bc/dd/d9bcdd583afd439d479027c985218eaf.jpg')";
        sqLiteDatabase.execSQL(INSERT_PRODUCT_DATA);

        //Initialize data for the product_detail table
        String INSERT_PRODUCT_DETAIL_DATA = "INSERT INTO " + TABLE_PRODUCT_DETAIL + "(idProduct, description, image) VALUES " +
                "(1, 'Description 1', null), " +
                "(2, 'Description 2', null), " +
                "(3, 'Description 3', null), " +
                "(4, 'Description 4', null), " +
                "(5, 'Description 5', null), " +
                "(6, 'Description 6', null), " +
                "(7, 'Description 7', null), " +
                "(8, 'Description 8', null), " +
                "(9, 'Description 9', null)";
        sqLiteDatabase.execSQL(INSERT_PRODUCT_DETAIL_DATA);

        //Initialize data for the employee table
        String INSERT_EMPLOYEE_DATA = "INSERT INTO " + TABLE_EMPLOYEE + " (id, email, name, phone, address, citizenshipID, status, date, image) VALUES " +
                "(1, 'employee1@gmail.com', 'Employee 1', '1234567890', 'Address 1', '1234567890', 'inactive', '2022-01-01', null ), " +
                "(2, 'employee2@gmail.com', 'Employee 2', '0987654321', 'Address 2', '0987654321', 'active', '2022-02-02', null), " +
                "(3, 'employee3@gmail.com', 'Employee 3', '9876543210', 'Address 3', '9876543210', 'active', '2022-03-03', null)," +
                "(4, 'admin@gmail.com', 'Admin', '8765432109', 'Address 4', '8765432109', 'active', '2022-04-04', 'https://i.pinimg.com/564x/ee/f4/9d/eef49d27bbe8300d3a3fc1e1bb5a0f7c.jpg')";

        sqLiteDatabase.execSQL(INSERT_EMPLOYEE_DATA);

        //Initialize data for the cart table
        String INSERT_CART_DATA = "INSERT INTO " + TABLE_CART + " (idProduct, name, quantity, price, emailCus, image) VALUES " +
                "(1, 'Product 1', 1, 10, 'customer1@gmail', null), " +
                "(2, 'Product 2', 2, 15, 'customer2@gmail', null), " +
                "(3, 'Product 3', 1, 20, 'customer1@gmail', null)";
        sqLiteDatabase.execSQL(INSERT_CART_DATA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CUSTOMER);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_BILL_DETAIL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCT_DETAIL);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_EMPLOYEE);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT_SHOP);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNT_CUSTOMER);
        onCreate(sqLiteDatabase);
    }
}
