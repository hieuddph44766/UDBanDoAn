package com.example.UDBanDoAn.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import com.example.UDBanDoAn.Database.Dbhelper;
import com.example.UDBanDoAn.MODEL.SanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPhamDAO {
    Dbhelper dbhelper;
    private final String TAG = SanPhamDAO.class.getSimpleName();

    public SanPhamDAO(Context context) {
        dbhelper = new Dbhelper(context);
    }

    public ArrayList<SanPham> getDSSanPham() {
        ArrayList<SanPham> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SanPham", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new SanPham(cursor.getInt(0), cursor.getString(1),
                        cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                        cursor.getString(5), cursor.getString(6), cursor.getInt(7)));
            } while (cursor.moveToNext());
        }
        return list;
    }
    public boolean themSanpham(SanPham sanPham) {
        Log.e(TAG, "themSanpham: " + sanPham );
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", sanPham.getNameSanpham());
        contentValues.put("Image", sanPham.getImgSanpham() == null ? "abc" : sanPham.getImgSanpham());
        contentValues.put("Price", sanPham.getPriceSanpham());
        contentValues.put("TypeproDuct", sanPham.getLoaiSanpham());
        contentValues.put("Created", sanPham.getCreateSanpham());
        contentValues.put("Updated", sanPham.getUpdatedSanpham());
        contentValues.put("Status", sanPham.getStatusSanpham());
        long check = sqLiteDatabase.insert("SanPham", null, contentValues);
        if (check == -1){
            return false;}
        else{return true;}
    }

    public boolean capnhatSanpham(SanPham sanPham) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", sanPham.getNameSanpham());
        contentValues.put("Image", sanPham.getImgSanpham());
        contentValues.put("Price", sanPham.getPriceSanpham());
        contentValues.put("TypeproDuct", sanPham.getLoaiSanpham());
        contentValues.put("Created", sanPham.getCreateSanpham());
        contentValues.put("Updated", sanPham.getUpdatedSanpham());
        contentValues.put("Status", sanPham.getStatusSanpham());
        long check = sqLiteDatabase.update("SanPham", contentValues, "Id = ?", new String[]{String.valueOf(sanPham.getId())});
        if (check == -1)
            return false;
        return true;
    }

    public boolean xoaSanPham(String Id) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
//        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM SanPham WHERE id = ?", new String[]{String.valueOf(Id)});
//        if (cursor.getCount() != 0) {
//            return -1;
//        }

        long check = sqLiteDatabase.delete("SanPham", "Id = ?", new String[]{String.valueOf(Id)});
//        if (check == -1)
//            return 0;
//        return 1;
        if (check>0){
            return true;
        }
        return false;
    }

    public SanPham getID(int id) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        String sql = "Select * from SanPham where id=?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(id)});

        cursor.moveToFirst();

        return new SanPham(cursor.getInt(0), cursor.getString(1),
                cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                cursor.getString(5), cursor.getString(6), cursor.getInt(7));
    }

    public List<SanPham> getSpTT(int loaiSP, int idSP) {
        List<SanPham> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        String sql = "select * from SanPham where TypeproDuct =? and Id!=?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(loaiSP), String.valueOf(idSP)});

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            list.add(new SanPham(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6), cursor.getInt(7)));
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

    public List<SanPham> getListLoaiSP(int loaiSP) {
        List<SanPham> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        String sql = "select * from SanPham where TypeproDuct =?";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(loaiSP)});

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            list.add(new SanPham(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6), cursor.getInt(7)));
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

    public List<SanPham> getListName(String name){
        List<SanPham> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        String sql = "select * from SanPham where Name like '%'||?||'%'";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(name)});

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            list.add(new SanPham(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6), cursor.getInt(7)));
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

    public List<SanPham> getListMoney(int type){
        List<SanPham> list = new ArrayList<>();
        String text = "asc";
        if (type==1){
            text="desc";
        }
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        String sql = "SELECT * from SanPham ORDER by Price "+text;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            list.add(new SanPham(cursor.getInt(0), cursor.getString(1),
                    cursor.getString(2), cursor.getInt(3), cursor.getString(4),
                    cursor.getString(5), cursor.getString(6), cursor.getInt(7)));
            cursor.moveToNext();
        }
        cursor.close();

        return list;
    }

}
