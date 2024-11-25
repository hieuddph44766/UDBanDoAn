package com.example.duan1_appbandoan.ViewActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.duan1_appbandoan.Adapter.CartAdapter;
import com.example.duan1_appbandoan.DAO.ProductDAO;
import com.example.duan1_appbandoan.Model.Product;
import com.example.duan1_appbandoan.R;

import java.util.List;

public class CartActivity extends AppCompatActivity {
    private RecyclerView rvCart;
    private TextView tvTotalPrice;
    private Button btnCheckout;
    private CartAdapter cartAdapter;
    private ProductDAO productDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cart);

        rvCart = findViewById(R.id.rvCart);
        tvTotalPrice = findViewById(R.id.tvTotalPrice);
        btnCheckout = findViewById(R.id.btnCheckout);

        productDAO = new ProductDAO(this);

        List<Product> cartItems = productDAO.getCartItems(1); // Thay 1 bằng ID order hiện tại
        cartAdapter = new CartAdapter(cartItems, this, productDAO);
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.setAdapter(cartAdapter);
        updateTotalPrice(cartItems);

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CartActivity.this, "Checkout thành công!", Toast.LENGTH_SHORT).show();
                productDAO.clearCart(1);
                finish();
            }
        });

    }
    private void updateTotalPrice(List<Product> cartItems) {
        int totalPrice = 0;
        for (Product item : cartItems) {
            totalPrice += item.getPrice() * item.getQuantity();
        }
        tvTotalPrice.setText("Total: $" + totalPrice);
    }
}