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
    private Context context;

    public ProductDAO(Context context) {
        this.context = context;
        dbHelper = new Dbhelper(context);
    }

    // Thêm Product
    public boolean insertProduct(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        values.put("price", product.getPrice());
        values.put("id_Category", product.getIdCategory());

        // Kiểm tra và gán giá trị mặc định cho id_Review nếu không có giá trị
        if (product.getIdReview() == null) {
            values.put("id_Review", -1);  // Gán giá trị mặc định, chẳng hạn là -1
        } else {
            values.put("id_Review", product.getIdReview());
        }
        values.put("total_sale", 0);
        values.put("image", "");
        long result = db.insert("product", null, values);
        db.close();
        return result != -1; // Trả về true nếu thêm thành công
    }

    // Cập nhật Product
    public int updateProduct(Product product) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("description", product.getDescription());
        if (product.getIdReview() == null) {
            values.put("id_Review", -1);  // Gán giá trị mặc định, chẳng hạn là -1
        } else {
            values.put("id_Review", product.getIdReview());
        }
        values.put("id_Category", product.getIdCategory());
        values.put("total_sale", "");
        values.put("image", "");
        values.put("price", product.getPrice());
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
                product.setPrice(cursor.getInt(cursor.getColumnIndexOrThrow("price")));
                String imageName = cursor.getString(cursor.getColumnIndexOrThrow("image"));
                int imageResId = context.getResources().getIdentifier(imageName, "drawable", context.getPackageName());
                product.setImageResId(imageResId);
                productList.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return productList;
    }

    // Thêm sản phẩm vào giỏ hàng
    public long addToCart(int orderId, int productId, int quantity, int price) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id_Order", orderId);
        values.put("id_Product", productId);
        values.put("quantity", quantity);
        values.put("totalprice", quantity * price);
        long result = db.insert("order_detail", null, values);
        db.close();
        return result;
    }

    // Lấy danh sách sản phẩm trong giỏ hàng
    public List<Product> getCartItems(int orderId) {
        List<Product> cartItems = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT p.id_product, p.name, p.description, p.id_Review, p.id_Category, p.total_sale, p.price, od.quantity, od.totalprice " +
                        "FROM product p JOIN order_detail od ON p.id_product = od.id_Product " +
                        "WHERE od.id_Order = ?",
                new String[]{String.valueOf(orderId)}
        );
        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setIdProduct(cursor.getInt(cursor.getColumnIndexOrThrow("id_product")));
                product.setName(cursor.getString(cursor.getColumnIndexOrThrow("name")));
                product.setDescription(cursor.getString(cursor.getColumnIndexOrThrow("description")));
                product.setIdReview(cursor.getInt(cursor.getColumnIndexOrThrow("id_Review")));
                product.setIdCategory(cursor.getInt(cursor.getColumnIndexOrThrow("id_Category")));
                product.setTotalSale(cursor.getInt(cursor.getColumnIndexOrThrow("total_sale")));
                product.setPrice(cursor.getInt(cursor.getColumnIndexOrThrow("price")));
                product.setQuantity(cursor.getInt(cursor.getColumnIndexOrThrow("quantity")));
                cartItems.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return cartItems;
    }

    // Xóa sản phẩm khỏi giỏ hàng
    public int removeFromCart(int orderId, int productId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete("order_detail", "id_Order = ? AND id_Product = ?",
                new String[]{String.valueOf(orderId), String.valueOf(productId)});
        db.close();
        return result;
    }

    // Xóa toàn bộ sản phẩm trong giỏ hàng
    public int clearCart(int orderId) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int result = db.delete("order_detail", "id_Order = ?", new String[]{String.valueOf(orderId)});
        db.close();
        return result;
    }

    // Cập nhật số lượng sản phẩm trong giỏ hàng
    public int updateCartItem(int orderId, int productId, int newQuantity, int price) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("quantity", newQuantity);
        values.put("totalprice", newQuantity * price);
        int result = db.update("order_detail", values, "id_Order = ? AND id_Product = ?",
                new String[]{String.valueOf(orderId), String.valueOf(productId)});
        db.close();
        return result;
    }
}
