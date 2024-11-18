package com.example.duan1_appbandoan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.duan1_appbandoan.DataBase.Dbhelper;
import com.example.duan1_appbandoan.Model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private Dbhelper dbHelper;

    public ProductDAO(Context context) {
        dbHelper = new Dbhelper(context);
    }

    // Thêm Product
    public long insertProduct(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("id_Review", product.getIdReview());
        values.put("id_Category", product.getIdCategory());
        values.put("total_sale", product.getTotalSale());
        long result = db.insert("product", null, values);
        db.close();
        return result;
    }

    // Cập nhật Product
    public int updateProduct(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("id_Review", product.getIdReview());
        values.put("id_Category", product.getIdCategory());
        values.put("total_sale", product.getTotalSale());
        int result = db.update("product", values, "id_product=?", new String[]{String.valueOf(product.getIdProduct())});
        db.close();
        return result;
    }

    // Xóa Product
    public int deleteProduct(int idProduct) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete("product", "id_product=?", new String[]{String.valueOf(idProduct)});
        db.close();
        return result;
    }

    // Lấy danh sách tất cả Product
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM product", null);
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setIdProduct(cursor.getInt(cursor.getColumnIndexOrThrow("id_product")));
                product.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                product.setIdReview(cursor.getInt(cursor.getColumnIndexOrThrow("id_Review")));
                product.setIdCategory(cursor.getInt(cursor.getColumnIndexOrThrow("id_Category")));
                product.setTotalSale(cursor.getInt(cursor.getColumnIndexOrThrow("total_sale")));
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }
}
