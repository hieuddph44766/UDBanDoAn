package com.example.UDBanDoAn.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Dbhelper extends SQLiteOpenHelper {
    static final String dbName = "SAFRTYFOOD";
    static final int dbVersion = 1;

    public Dbhelper(Context context) {
        super(context, dbName, null, dbVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableTaiKhoan = "create table TaiKhoan(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " UserName TEXT NOT NULL," +
                "Password TEXT NOT NULL," +
                "Roled INTEGER REFERENCES VaiTro(Id))";
        db.execSQL(createTableTaiKhoan);

        String createTableVaiTro = "create table VaiTro(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT NOT NULL," +
                "Description TEXT NOT NULL," +
                "Created TEXT not null," +
                "Updated TEXT not null)";
        db.execSQL(createTableVaiTro);

        String createTableThongTinNguoiDung = "create table ThongTinNguoiDung(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "AccountId INTEGER REFERENCES TaiKhoan(Id)," +
                "FullName Text NOT NULL," +
                "Email TEXT NOT NULL," +
                "SDT TEXT NOT NULL,"+
                "Addres TEXT NOT NULL," +
                "Avatar TEXT NOT NULL," +
                "Birthday DATE NOT NULL," +
                "Gender INT NOT NULL," +
                "Created TEXT NOT NULL," +
                "Updated TEXT NOT NULL)";
        db.execSQL(createTableThongTinNguoiDung);

        String createTableDatHang = "create table DatHang(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "AccountId INTEGER REFERENCES TaiKhoan(Id)," +
                "TotalPrice Float NOT NULL," +
                "Created TEXT NOT NULL," +
                "Updated TEXT NOT NULL," +
                "Status INTEGER not null)";
        db.execSQL(createTableDatHang);

        String createTableChiTietDatHang = "create table ChiTietDatHang(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "OrderId INTEGER REFERENCES DatHang(Id)," +
                "ProductId INTEGER NOT NULL," +
                "UnitPrice Float NOT NULL ," +
                "Amount INTEGER NOT NULL)";
        db.execSQL(createTableChiTietDatHang);

        String createTableCuaHang = "create table CuaHang(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT NOT NULL," +
                "Image TEXT NOT NULL," +
                "Phone TEXT NOT NULL," +
                "Email TEXT NOT NULL," +
                "Addres TEXT NOT NULL," +
                "Created TEXT NOT NULL," +
                "Updated TEXT NOT NULL," +
                "Status INTEGER NOT NULL)";

        db.execSQL(createTableCuaHang);

        String createTableLoaiSanPham = "create table LoaiSanPham(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Idcuahang INTEGER REFERENCES CuaHang(Id)," +
                "Name TEXT NOT NULL," +
                "Image TEXT NOT NULL," +
                "Created TEXT NOT NULL," +
                "Updated TEXT," +
                "Status INTEGER)";
        db.execSQL(createTableLoaiSanPham);

        String createTableSanPham = "create table SanPham(" +
                "Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "Name TEXT NOT NULL," +
                "Image TEXT NOT NULL," +
                "Price FLOAT NOT NULL," +
                "TypeproDuct INTEGER REFERENCES LoaiSanPham(Id)," +
                "Created TEXT NOT NULL," +
                "Updated TEXT ," +
                "Status INTEGER )";
        db.execSQL(createTableSanPham);

        db.execSQL("INSERT INTO VaiTro VALUES(1,'ADMIN','ADMIN','31/11/2024','31/11/2024'),(2,'KHACHHANG','KHACHHANG','31/11/2024','31/11/2024')");
        db.execSQL("INSERT INTO TaiKhoan VALUES(1,'admin','admin',1),(2,'user','user',3)");
        db.execSQL("INSERT INTO ThongTinNguoiDung VALUES(0,2,'Nhom11','email@gmail.com','012345678','HaNoi','700042','31/11/2024','Nam','31/11/2024','31/11/2024')");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String dropTableTaiKhoan = "drop table if exists TaiKhoan";
        db.execSQL(dropTableTaiKhoan);
        String dropTableVaiTro = "drop table if exists VaiTro";
        db.execSQL(dropTableVaiTro);
        String dropTableThongTinNguoiDung = "drop table if exists ThongTinNguoiDung";
        db.execSQL(dropTableThongTinNguoiDung);
        String dropTableCuaHang = "drop table if exists CuaHang";
        db.execSQL(dropTableCuaHang);
        String dropTableDatHang = "drop table if exists DatHang";
        db.execSQL(dropTableDatHang);
        String dropTableChiTietDatHang = "drop table if exists ChiTietDatHang";
        db.execSQL(dropTableChiTietDatHang);
        String dropTableLoaiSanPham = "drop table if exists LoaiSanPham";
        db.execSQL(dropTableLoaiSanPham);
        String dropTableSanPham = "drop table if exists SanPham";
        db.execSQL(dropTableSanPham);
        onCreate(db);
    }
}