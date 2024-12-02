package com.example.duan1_appbandoan;

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
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_appbandoan.Adapter.ProductAdapter;
import com.example.duan1_appbandoan.DAO.ProductDAO;
import com.example.duan1_appbandoan.ViewActivity.CaNhanActivity;
import com.example.duan1_appbandoan.ViewActivity.QuanLyDHAcitivity;
import com.example.duan1_appbandoan.ViewActivity.ThongKeActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class AdminActivity extends AppCompatActivity {
    ProductDAO productDAO;
    ProductAdapter productAdapter;
    RecyclerView recyclerView;
    BottomNavigationView bottom_NaView;
    String user;
    TextView tv_User;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_admin);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        recyclerView = findViewById(R.id.re_Cate);
        productDAO=new ProductDAO(this);
        productAdapter=new ProductAdapter(this,productDAO.getAllProducts());
        recyclerView.setAdapter(productAdapter);

        bottom_NaView = findViewById(R.id.bottom_NaView);
        bottom_NaView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_Home) {
                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.menu_hoadon) {
                    startActivity(new Intent(getApplicationContext(), QuanLyDHAcitivity.class));
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
