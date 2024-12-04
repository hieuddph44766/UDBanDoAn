package com.example.duan1_appbandoan.DataBase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Dbhelper extends SQLiteOpenHelper {
    private static final String DB_NAME="PNLIB";
    private static final int DB_VERSION=8;

    public Dbhelper(@Nullable Context context) {
        super(context,DB_NAME,null,DB_VERSION);
    }

    static final String CREATE_TABLE_USER =
           "CREATE TABLE user (\n" +
                   "    id_user INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                   "    userName TEXT NOT NULL,\n" +
                   "    password TEXT NOT NULL,\n" +
                   "    email TEXT NOT NULL,\n" +
                   "    address TEXT NOT NULL,\n" +
                   "    phone TEXT NOT NULL,\n" +
                   "    role INTEGER NOT NULL\n" +
                   ")";

    static final String CREATE_TABLE_CATEGORY =
           "CREATE TABLE category (\n" +
                   "    id_Category INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                   "    name_Category TEXT NOT NULL\n" +
                   ");"  ;

    static final String CREATE_REVIEW =
         "CREATE TABLE review (\n" +
                 "    id_Review INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                 "    review INTEGER NOT NULL,\n" +
                 "    id_User INTEGER NOT NULL,\n" +
                 "    FOREIGN KEY (id_User) REFERENCES user(id_User)\n" +
                 ")";

    static final String CREATE_ORDER =
           "CREATE TABLE orders (\n" +
                   "    id_Order INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                   "    status INTEGER NOT NULL,\n" +
                   "    id_User INTEGER NOT NULL,\n" +
                   "    date TEXT NOT NULL,\n" +
                   "    FOREIGN KEY (id_User) REFERENCES user(id_User)\n" +
                   ")";
    static final String CREATE_TABLE_PRODUCT =
          "CREATE TABLE product (\n" +
                  "    id_product INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                  "    name TEXT NOT NULL,\n" +
                  "    description TEXT NOT NULL,\n" +
                  "    id_Review INTEGER NOT NULL,\n" +
                  "    id_Category INTEGER NOT NULL,\n" +
                  "    total_sale INTEGER NOT NULL,\n" +
                  "    image TEXT NOT NULL,\n" +
                  "    price INTEGER NOT NULL,\n" +
                  "    FOREIGN KEY (id_Review) REFERENCES review(id_Review),\n" +
                  "    FOREIGN KEY (id_Category) REFERENCES category(id_Category)\n" +
                  ")";

    static final String CREATE_ORDER_DETAIL =
            "CREATE TABLE order_detail (\n" +
                    "    id_orderDetail INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                    "    id_Order INTEGER NOT NULL,\n" +
                    "    id_Product INTEGER NOT NULL,\n" +
                    "    quantity INTEGER NOT NULL,\n" +
                    "    totalprice INTEGER NOT NULL,\n" +
                    "    FOREIGN KEY (id_Order) REFERENCES orders(id_Order),\n" +
                    "    FOREIGN KEY (id_Product) REFERENCES product(id_Product)\n" +
                    ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_USER);
        db.execSQL(CREATE_TABLE_PRODUCT);
        db.execSQL(CREATE_ORDER);
        db.execSQL(CREATE_ORDER_DETAIL);
        db.execSQL(CREATE_REVIEW);
        db.execSQL(CREATE_TABLE_CATEGORY);
        db.execSQL(Data_SQLite.INSERT_USER);
        db.execSQL(Data_SQLite.INSERT_PRODUCT);
        db.execSQL(Data_SQLite.INSERT_CATEGORY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        if (i < i1) {
            // Xóa các bảng cũ nếu cần thiết
            db.execSQL("DROP TABLE IF EXISTS product");
            db.execSQL("DROP TABLE IF EXISTS user");
            db.execSQL("DROP TABLE IF EXISTS orders");
            db.execSQL("DROP TABLE IF EXISTS order_detail");
            db.execSQL("DROP TABLE IF EXISTS review");
            db.execSQL("DROP TABLE IF EXISTS category");

            // Tạo lại các bảng mới
            onCreate(db);
        }
    }
    public int getTotalUsers() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM user", null);
        int totalUsers = 0;
        if (cursor.moveToFirst()) {
            totalUsers = cursor.getInt(0);
        }
        cursor.close();
        return totalUsers;
    }

    public int getTotalRevenue() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT SUM(totalprice) FROM order_detail", null);
        int totalRevenue = 0;
        if (cursor.moveToFirst()) {
            totalRevenue = cursor.getInt(0);
        }
        cursor.close();
        return totalRevenue;
    }

    public String getBestSellingProduct() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT name, SUM(quantity) as total FROM product " +
                "INNER JOIN order_detail ON product.id_product = order_detail.id_Product " +
                "GROUP BY name ORDER BY total DESC LIMIT 1", null);
        String bestSelling = "N/A";
        if (cursor.moveToFirst()) {
            bestSelling = cursor.getString(0);
        }
        cursor.close();
        return bestSelling;
    }

    public int getCompletedOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM orders WHERE status = 1", null);
        int completedOrders = 0;
        if (cursor.moveToFirst()) {
            completedOrders = cursor.getInt(0);
        }
        cursor.close();
        return completedOrders;
    }
    public List<String> getTop10BestSellingProducts() {
        SQLiteDatabase db = this.getReadableDatabase();
        List<String> topProducts = new ArrayList<>();

        // Truy vấn lấy Top 10 sản phẩm bán chạy nhất
        Cursor cursor = db.rawQuery(
                "SELECT name, SUM(quantity) as total_sales FROM product " +
                        "INNER JOIN order_detail ON product.id_product = order_detail.id_Product " +
                        "GROUP BY name " +
                        "ORDER BY total_sales DESC " +
                        "LIMIT 10", null);

        if (cursor.moveToFirst()) {
            do {
                // Lấy tên sản phẩm và số lượng bán
                String productName = cursor.getString(0);
                int totalSales = cursor.getInt(1);
                topProducts.add(productName + " - Số lượng: " + totalSales);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return topProducts;
    }

}
