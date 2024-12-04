package com.example.UDBanDoAn.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.UDBanDoAn.Adapter.SanPhamAdapter;
import com.example.UDBanDoAn.DAO.SanPhamDAO;
import com.example.UDBanDoAn.MODEL.LoaiSanPham;
import com.example.UDBanDoAn.MODEL.SanPham;
import com.example.UDBanDoAn.R;

import java.util.List;

public class  AllLoaiSanPham extends AppCompatActivity {
    RecyclerView loaiSP_List;
    SanPhamDAO sanPhamDAO;
    List<SanPham> sanPhamList;
    Toolbar loaiSP_Toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_loai_san_pham);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("bundle");
        LoaiSanPham loaiSanPham = (LoaiSanPham) bundle.getSerializable("loaiSP");

        anhXa();

        setSupportActionBar(loaiSP_Toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        getData(loaiSanPham);

    }

    private void getData(LoaiSanPham loaiSanPham) {
        sanPhamList = sanPhamDAO.getListLoaiSP(loaiSanPham.getId());
        Log.e("ALL", "getData: " + sanPhamList.get(0));
        setList();
    }

    private void setList() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        loaiSP_List.setLayoutManager(gridLayoutManager);
        SanPhamAdapter adapter = new SanPhamAdapter(sanPhamList, getApplicationContext(),1);
        loaiSP_List.setAdapter(adapter);
    }

    private void anhXa() {
        loaiSP_List = findViewById(R.id.loaiSP_List);
        loaiSP_Toolbar = findViewById(R.id.loaiSP_Toolbar);
        sanPhamDAO = new SanPhamDAO(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}