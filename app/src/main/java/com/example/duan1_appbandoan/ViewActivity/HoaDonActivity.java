package com.example.duan1_appbandoan.ViewActivity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_appbandoan.Adapter.HoaDonAdapter;
import com.example.duan1_appbandoan.Model.Product;
import com.example.duan1_appbandoan.R;

import java.util.List;

public class HoaDonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hoa_don);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            List<Product> cartItems = (List<Product>) bundle.getSerializable("cartItems");

            // Hiển thị các sản phẩm trong hóa đơn (dùng RecyclerView)
            RecyclerView rvInvoice = findViewById(R.id.rvInvoice);
            HoaDonAdapter hoaDonAdapter = new HoaDonAdapter(cartItems, this);
            rvInvoice.setLayoutManager(new LinearLayoutManager(this));
            rvInvoice.setAdapter(hoaDonAdapter);

            // Tính tổng tiền và hiển thị
            double total = 0;
            for (Product product : cartItems) {
                total += product.getTotalSale() * product.getQuantity();  // Tính tổng tiền
            }
            TextView tvTotal = findViewById(R.id.tvTotalPrice);
            tvTotal.setText("Tổng tiền: VND " + total);
        }
    }
}