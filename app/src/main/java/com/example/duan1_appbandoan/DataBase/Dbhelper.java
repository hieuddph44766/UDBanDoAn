package com.example.duan1_appbandoan.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {
    private static final String DB_NAME="PNLIB";
    private static final int DB_VERSION=1;

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
        String dropTableUser = "drop table if exists user";
        db.execSQL(dropTableUser);
        //
        String dropTableProduct = "drop table if exists product";
        db.execSQL(dropTableProduct);
        //
        String dropTableOrder = "drop table if exists order1";
        db.execSQL(dropTableOrder);
        //
        String dropTableOrderDetail = "drop table if exists order_detail";
        db.execSQL(dropTableOrderDetail);
        String dropTableReview= "drop table if exists review";
        db.execSQL(dropTableReview);
        String dropTableCategory = "drop table if exists category";
        db.execSQL(dropTableCategory);
        //
        onCreate(db);
    }
}
