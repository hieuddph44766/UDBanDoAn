package com.example.UDBanDoAn.Activities;

import static com.example.UDBanDoAn.MainActivity.account_all;
import static com.example.UDBanDoAn.MainActivity.cart_all;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.UDBanDoAn.Adapter.SanPhamAdapter;
import com.example.UDBanDoAn.DAO.ChiTietDatHangDAO;
import com.example.UDBanDoAn.DAO.SanPhamDAO;
import com.example.UDBanDoAn.MODEL.SanPham;
import com.example.UDBanDoAn.R;


import java.util.List;

public class SearchItems extends AppCompatActivity {
    RecyclerView Search_List;
    SanPhamDAO sanPhamDAO;
    List<SanPham> list;
    Toolbar Search_toolbar;
    TextView sizeCart;
    ChiTietDatHangDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_items);

        Search_List = findViewById(R.id.Search_List);
        Search_toolbar = findViewById(R.id.Search_toolbar);

        sanPhamDAO = new SanPhamDAO(this);
        dao = new ChiTietDatHangDAO(this);

        setSupportActionBar(Search_toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent( );
        String search = intent.getStringExtra("search");

        getData(search);

    }

    private void getData(String search) {
        list = sanPhamDAO.getListName(search);
        setData();
    }

    private void setData() {
        SanPhamAdapter adapter = new SanPhamAdapter(list,this,0);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        Search_List.setLayoutManager(gridLayoutManager);
        Search_List.setAdapter(adapter);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        } else if (item.getItemId() == R.id.badge_cart) {
            startActivity(new Intent(SearchItems.this, BadgeCart.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.badge_cart_menu,menu);

        MenuItem menuItem = menu.findItem(R.id.badge_cart);
        if (account_all.getRole()!=3){
            menuItem.setVisible(false);
        }
        View actionMenu = menuItem.getActionView();
        sizeCart = actionMenu.findViewById(R.id.number_size_cart);

        setCartSize();

        actionMenu.setOnClickListener(v -> {
            onOptionsItemSelected(menuItem);
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void setCartSize(){
        sizeCart.setText(String.valueOf(dao.getSum(cart_all.getId( ))));
    }

    @Override
    protected void onResume() {
        super.onResume( );
        if (sizeCart!=null){
            setCartSize();
        }
    }
}