package com.example.duan1_appbandoan.DAO;


import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.duan1_appbandoan.DataBase.Dbhelper;
import com.example.duan1_appbandoan.Model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO{
    private Dbhelper dbHelper;
    private Context context;

    public UserDAO(Context context) {
        this.context = context;
        dbHelper = new Dbhelper(context);
    }
    // Thêm User
    public long insertUser(User user) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", user.getPassword());
        values.put("userName", user.getUserName());
        values.put("email","");
        values.put("address","");
        values.put("phone", "");
        values.put("role",1);
        long result = db.insert("user", null, values);
        db.close();
        return result;
    }
    public boolean updatePassword(String username, String newPassword) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);

        // Cập nhật mật khẩu dựa vào tên người dùng
        int rows = db.update("user", values, "userName = ?", new String[]{username});
        db.close();

        return rows > 0; // Trả về true nếu cập nhật thành công
    }

    // Xóa User
    public int deleteUser(int idUser) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
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
    public List<User> getData(String sql, String... selectionArgs) {
        List<User> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
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

    public int checkLogin(String username, String password) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT role FROM user WHERE userName=? AND password=?", new String[]{username, password});
        if (cursor.moveToFirst()) {
            int role = cursor.getInt(0);
            cursor.close();
            return role; // Trả về role: 0 cho admin, 1 cho user
        }
        cursor.close();
        return -1; // Trả về -1 nếu không tìm thấy tài khoản
    }
}
