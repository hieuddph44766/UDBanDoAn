package com.example.UDBanDoAn.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.UDBanDoAn.Database.Dbhelper;
import com.example.UDBanDoAn.MODEL.LoaiSanPham;

import java.util.ArrayList;

public class LoaiSanPhamDAO {
    private final String TAG = LoaiSanPhamDAO.class.getSimpleName();
    Dbhelper dbhelper;

    public LoaiSanPhamDAO(Context context) {
        dbhelper = new Dbhelper(context);
    }

    public ArrayList<LoaiSanPham> getDSLoaiSanPham() {
        ArrayList<LoaiSanPham> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from LoaiSanPham", null);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new LoaiSanPham(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6)));
            } while (cursor.moveToNext());
        }
        return list;
    }
    public ArrayList<LoaiSanPham> getDSLoaiSanPham(String sql , String...select) {
        ArrayList<LoaiSanPham> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbhelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sql, select);
        if (cursor.getCount() != 0) {
            cursor.moveToFirst();
            do {
                list.add(new LoaiSanPham(
                        cursor.getInt(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getInt(6)));
            } while (cursor.moveToNext());
        }
        return list;
    }
    public boolean themLoaiSanPham(LoaiSanPham loaiSanPham) {
        Log.e(TAG, "themLoaiSanPham: " + loaiSanPham );
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Idcuahang", loaiSanPham.getIdCuahang());
        contentValues.put("Name", loaiSanPham.getNameLoaisanpham());
        contentValues.put("Image", loaiSanPham.getImgLoaisanpham() == null ? "abc" : loaiSanPham.getImgLoaisanpham());
        contentValues.put("Created", loaiSanPham.getCreateLoaisanpham());
        contentValues.put("Updated", loaiSanPham.getUpdatedLoaisanpham());
        contentValues.put("Status", loaiSanPham.getStatusLoaisanpham());
        long check = sqLiteDatabase.insert("LoaiSanPham", null, contentValues);
        if (check == -1)
            return false;
        return true;
    }

    public boolean capnhatLoaiSanPham(LoaiSanPham loaiSanPham) {
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Idcuahang", loaiSanPham.getIdCuahang());
        contentValues.put("Name", loaiSanPham.getNameLoaisanpham());
        contentValues.put("Image", loaiSanPham.getImgLoaisanpham());
        contentValues.put("Created", loaiSanPham.getCreateLoaisanpham());
        contentValues.put("Updated", loaiSanPham.getUpdatedLoaisanpham());
        contentValues.put("Status", loaiSanPham.getStatusLoaisanpham());
        long check = sqLiteDatabase.update("LoaiSanPham", contentValues, "Id = ?", new String[]{String.valueOf(loaiSanPham.getId())});
        if (check == -1)
            return false;
        return true;
    }
    public boolean deleteLoaiSanPham(String id){
        SQLiteDatabase sqLiteDatabase = dbhelper.getWritableDatabase();
        int row = sqLiteDatabase.delete("LoaiSanPham","id=?",new String[]{id});
        if (row > 0) {
            return true;
        }
        return false;
    }
    public LoaiSanPham getID(String id){
        String sql = "select * from LoaiSanPham where id=?";
        ArrayList<LoaiSanPham> list = getDSLoaiSanPham(sql,id);
        return list.get(0);
    }
    public ArrayList<LoaiSanPham> getAll(){
        String sql = "select * from LoaiSanPham";
        return getDSLoaiSanPham(sql);
    }
}
