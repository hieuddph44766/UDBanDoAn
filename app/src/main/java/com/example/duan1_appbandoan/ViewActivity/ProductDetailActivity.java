package com.example.duan1_appbandoan.ViewActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_appbandoan.Model.Product;
import com.example.duan1_appbandoan.R;
import com.example.duan1_appbandoan.DAO.ProductDAO;

public class ProductDetailActivity extends AppCompatActivity {
    private ImageView imgProductDetail;
    private TextView tvProductNameDetail, tvProductDescriptionDetail, tvProductPriceDetail;
    private Button btnAddToCart;
    private ProductDAO productDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Ánh xạ view
        imgProductDetail = findViewById(R.id.imgProductDetail);
        tvProductNameDetail = findViewById(R.id.tvProductNameDetail);
        tvProductDescriptionDetail = findViewById(R.id.tvProductDescriptionDetail);
        tvProductPriceDetail = findViewById(R.id.tvProductPriceDetail);
        btnAddToCart = findViewById(R.id.btnAddToCart);

        // Khởi tạo ProductDAO
        productDAO = new ProductDAO(this);

        // Nhận dữ liệu từ Intent
        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("product");

        if (product != null) {
            // Gán dữ liệu vào các view
            tvProductNameDetail.setText(product.getName());
            tvProductDescriptionDetail.setText(product.getDescription());
            tvProductPriceDetail.setText("Giá: " + product.getTotalSale() + " VND");
            // Nếu có ảnh, bạn có thể load qua Glide hoặc Picasso
        }

        // Thêm sản phẩm vào giỏ hàng khi nhấn nút
        btnAddToCart.setOnClickListener(view -> {

            int orderId = 1;
            int quantity = 1;

            // Thêm sản phẩm vào giỏ hàng
            long result = productDAO.addToCart(orderId, product.getIdProduct(), quantity, product.getPrice());

            if (result > 0) {
                Toast.makeText(ProductDetailActivity.this, "Đã thêm sản phẩm vào giỏ hàng!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(ProductDetailActivity.this, "Lỗi khi thêm vào giỏ hàng.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}