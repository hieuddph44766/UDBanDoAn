package com.example.duan1_appbandoan.ViewActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1_appbandoan.AdminActivity;
import com.example.duan1_appbandoan.DataBase.Dbhelper;
import com.example.duan1_appbandoan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import java.util.List;

public class ThongKeActivity extends AppCompatActivity {
    BottomNavigationView bottom_NaView;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_thong_ke);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Dbhelper dbhelper = new Dbhelper(this);

        // Kết nối TextView từ layout
        TextView tvRevenue = findViewById(R.id.tvRevenue);
        TextView tvBestProduct = findViewById(R.id.tvBestProduct);
        TextView tvTop10Products = findViewById(R.id.tvTop10Products);

        // Hiển thị dữ liệu thống kê
        tvRevenue.setText("Tổng doanh thu: " + dbhelper.getTotalRevenue() + " VNĐ");
        tvBestProduct.setText("Sản phẩm bán chạy nhất: " + dbhelper.getBestSellingProduct());


        // Lấy danh sách Top 10 sản phẩm bán chạy
        List<String> top10Products = dbhelper.getTop10BestSellingProducts();
        StringBuilder topProductsDisplay = new StringBuilder("Top 10 sản phẩm bán chạy:\n");
        for (int i = 0; i < top10Products.size(); i++) {
            topProductsDisplay.append(i + 1).append(". ").append(top10Products.get(i)).append("\n");
        }
        tvTop10Products.setText(topProductsDisplay.toString());


        // Thiết lập BottomNavigationView
        bottom_NaView = findViewById(R.id.bottom_NaView);
        bottom_NaView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_hoadon) {
                    return true;
                } else if (item.getItemId() == R.id.menu_Home) {
                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.menu_thongke) {
                    startActivity(new Intent(getApplicationContext(), ThongKeActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.menu_CaNhan) {
                    startActivity(new Intent(getApplicationContext(), CaNhanActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else {
                    return false;
                }

            }
        });
    }
}
