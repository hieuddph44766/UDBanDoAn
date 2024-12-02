package com.example.duan1_appbandoan.ViewActivity;

import android.content.Intent;
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

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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

        List<Product> cartItems = productDAO.getCartItems(1);
        cartAdapter = new CartAdapter(cartItems, this, productDAO);
        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.setAdapter(cartAdapter);
        updateTotalPrice(cartItems);

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<Product> cartItems = productDAO.getCartItems(1);


                if (cartItems != null && !cartItems.isEmpty()) {

                    String currentDate = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(new Date());


                    for (Product product : cartItems) {
                        product.setOderDate(currentDate);
                    }


                    Intent intent = new Intent(CartActivity.this, HoaDonActivity.class);


                    Bundle bundle = new Bundle();
                    bundle.putSerializable("cartItems", (Serializable) cartItems);
                    intent.putExtras(bundle);


                    startActivity(intent);
                } else {
                    Toast.makeText(CartActivity.this, "Giỏ hàng trống", Toast.LENGTH_SHORT).show();
                }
            }
        });




        cartAdapter.setUpdateTotalPriceListener(new CartAdapter.UpdateTotalPriceListener() {
            @Override
            public void onUpdateTotalPrice() {
                updateTotalPrice(cartItems);
            }
        });


    }

    private void updateTotalPrice(List<Product> cartItems) {
        double total = 0;

        for (Product product : cartItems) {
            total += product.getTotalSale() * product.getQuantity();
        }
        TextView tvTotalPrice = findViewById(R.id.tvTotalPrice);
        tvTotalPrice.setText("Total: VND" + total);
    }


}