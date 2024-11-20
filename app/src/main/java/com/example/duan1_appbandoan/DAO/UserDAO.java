package com.example.duan1_appbandoan.DAO;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_appbandoan.DataBase.Dbhelper;
import com.example.duan1_appbandoan.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Dbhelper dbHelper;
    private SQLiteDatabase db;
    public UserDAO(Context context) {
        dbHelper = new Dbhelper(context);
    }
    // Thêm User
    public long insertUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName", user.getUserName());
        values.put("password", user.getPassword());
        values.put("email", "");
        values.put("address", "");
        values.put("phone", "");
        values.put("role", 1);
        long result = db.insert("user", null, values);
        db.close();
        return result;
    }
    // Cập nhật User
    public int updateUser(User user) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("userName", user.getUserName());
        values.put("password", user.getPassword());
        values.put("email", user.getEmail());
        values.put("address", user.getAddress());
        values.put("phone", user.getPhone());
        values.put("role", user.getRole());
        int result = db.update("user", values, "id_user=?", new String[]{String.valueOf(user.getIdUser())});
        db.close();
        return result;
    }

    // Xóa User
    public int deleteUser(int idUser) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete("user", "id_user=?", new String[]{String.valueOf(idUser)});
        db.close();
        return result;
    }

    // Lấy danh sách tất cả User
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user", null);
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setIdUser(cursor.getInt(cursor.getColumnIndexOrThrow("id_user")));
                user.setUserName(cursor.getString(cursor.getColumnIndexOrThrow("userName")));
                user.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("password")));
                user.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("email")));
                user.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("address")));
                user.setPhone(cursor.getString(cursor.getColumnIndexOrThrow("phone")));
                user.setRole(cursor.getInt(cursor.getColumnIndexOrThrow("role")));
                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return userList;
    }
    @SuppressLint("Range")
    public List<User> getdata(String sql, String... selectionArgs) {
        List<User> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase(); // Khởi tạo đối tượng db ở đây
        Cursor c = db.rawQuery(sql, selectionArgs);
        while (c.moveToNext()) {
            User obj = new User();
            obj.setUserName(c.getString(c.getColumnIndex("userName")));
            obj.setPassword(c.getString(c.getColumnIndex("password")));
            list.add(obj);
        }
        c.close();
        db.close();
        return list;
    }
    public int checkLogin(String id, String password){
        String sql="select * from user where userName=? and password=?";
        List<User> list =getdata(sql,id,password);
        if (list.size()==0){
            return -1;
        }
        return 1;
    }
}
