package com.example.UDBanDoAn.FRAGMENT;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.UDBanDoAn.Activities.SearchItems;
import com.example.UDBanDoAn.Activities.ShowAll;
import com.example.UDBanDoAn.Adapter.LoaiSanPhamAdapter;
import com.example.UDBanDoAn.Adapter.PhotoAdapter;
import com.example.UDBanDoAn.Adapter.SanPhamAdapter;
import com.example.UDBanDoAn.DAO.LoaiSanPhamDAO;
import com.example.UDBanDoAn.DAO.SanPhamDAO;
import com.example.UDBanDoAn.DAO.ThongKeDAO;
import com.example.UDBanDoAn.MODEL.LoaiSanPham;
import com.example.UDBanDoAn.MODEL.Photo;
import com.example.UDBanDoAn.MODEL.SanPham;


import com.example.UDBanDoAn.R;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import me.relex.circleindicator.CircleIndicator;


public class HomeFragment extends Fragment {

    View view;
    Context context;
    EditText edt_findFood;
    TextInputLayout Home_TIL_findFood;
    RecyclerView RCL_loaiSP, RCL_SP, RCL_TSP;
    LoaiSanPhamDAO loaiSanPhamDAO;
    SanPhamDAO sanPhamDAO;
    List<LoaiSanPham> loaiSanPhamList;
    List<SanPham> sanPhamList, topSanPham;
    ThongKeDAO thongKeDAO;
    ViewPager viewPager;
    CircleIndicator indicator;
    List<Photo> listImage = getListPhoto();
    Timer timer;
    TextView Home_Show_All;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home, container, false);
        anhXa();
//        autoSidelImage();
        Home_TIL_findFood.setStartIconOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchItems.class);
            intent.putExtra("search", edt_findFood.getText().toString().trim());
            startActivity(intent);
        });
        Home_Show_All.setOnClickListener(v -> {
            startActivity(new Intent( getContext(), ShowAll.class));
        });

        getData();

        return view;
    }

    private void setRCLAdapter() {
        LoaiSanPhamAdapter adapter = new LoaiSanPhamAdapter(loaiSanPhamList, getContext());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        RCL_loaiSP.setLayoutManager(linearLayoutManager);
        RCL_loaiSP.setAdapter(adapter);

        SanPhamAdapter sanPhamAdapter = new SanPhamAdapter(sanPhamList, getContext(),1);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        RCL_SP.setLayoutManager(layoutManager);
        RCL_SP.setAdapter(sanPhamAdapter);

        SanPhamAdapter sanPhamAdapter1 = new SanPhamAdapter(topSanPham, getContext(),1);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        RCL_TSP.setLayoutManager(layoutManager1);
        RCL_TSP.setAdapter(sanPhamAdapter1);

        PhotoAdapter photoAdapter = new PhotoAdapter(context, getListPhoto());
        viewPager.setAdapter(photoAdapter);
        indicator.setViewPager(viewPager);
        photoAdapter.registerDataSetObserver(indicator.getDataSetObserver());

    }

    private List<Photo> getListPhoto() {
        List<Photo> list = new ArrayList<>();
        list.add(new Photo(R.drawable.banner1));
        list.add(new Photo(R.drawable.banner2));
        list.add(new Photo(R.drawable.banner3));
        list.add(new Photo(R.drawable.banner4));
        list.add(new Photo(R.drawable.banner5));
        return list;
    }
// cho nay da sua

    private void getData() {
        if (loaiSanPhamDAO.getDSLoaiSanPham().isEmpty()) {
            loaiSanPhamDAO.themLoaiSanPham(new LoaiSanPham(0, 0, "Món nước", String.valueOf(R.drawable.pho)
                    , "28/03/2024", "3/11/2024", 1));
            loaiSanPhamDAO.themLoaiSanPham(new LoaiSanPham(0, 0, "Đồ ăn nhanh", String.valueOf(R.drawable.doananh)
                    , "28/03/2024", "3/11/2024", 1));
            loaiSanPhamDAO.themLoaiSanPham(new LoaiSanPham(0, 0, "Cơm", String.valueOf(R.drawable.com)
                    , "28/03/2024", "3/11/2024", 1));
            loaiSanPhamDAO.themLoaiSanPham(new LoaiSanPham(0, 0, "Bánh", String.valueOf(R.drawable.bread)
                    , "28/03/2024", "3/11/2024", 1));
            loaiSanPhamDAO.themLoaiSanPham(new LoaiSanPham(0, 0, "Tráng miệng", String.valueOf(R.drawable.trangmieng)
                    , "28/03/2024", "3/11/2024", 1));
        }
        if (sanPhamDAO.getDSSanPham().isEmpty()) {
            sanPhamDAO.themSanpham(new SanPham(0, "Phở bò", String.valueOf(R.drawable.phobo), 35000
                    , "1", "28/03/2024", "3/11/2024", 1));
            sanPhamDAO.themSanpham(new SanPham(0, "Mì cay", String.valueOf(R.drawable.micay), 35000
                    , "1", "28/03/2024", "3/11/2024", 1));
            sanPhamDAO.themSanpham(new SanPham(0, "Bún bò", String.valueOf(R.drawable.bunbonoback), 35000
                    , "1", "28/03/2024", "3/11/2024", 1));
            sanPhamDAO.themSanpham(new SanPham(0, "Khoai tây chiên", String.valueOf(R.drawable.khaitaychiennoback), 35000
                    , "2", "28/03/2024", "28/03/2024", 1));
            sanPhamDAO.themSanpham(new SanPham(0, "Hot dog", String.valueOf(R.drawable.hotdog), 35000
                    , "2", "28/03/2024", "28/03/2024", 1));
            sanPhamDAO.themSanpham(new SanPham(0, "Hamburger", String.valueOf(R.drawable.hamburgernobackk), 45000
                    , "2", "28/03/2024", "28/03/2024", 1));
            sanPhamDAO.themSanpham(new SanPham(0, "Gà Rán", String.valueOf(R.drawable.garannoback), 35000
                    , "2", "28/03/2024", "28/03/2024", 1));
            sanPhamDAO.themSanpham(new SanPham(0, "Cơm tấm", String.valueOf(R.drawable.comtam), 25000
                    , "3", "28/03/2024", "3/11/2024", 1));
            sanPhamDAO.themSanpham(new SanPham(0, "Cơm rang  ", String.valueOf(R.drawable.comrangduabo), 25000
                    , "3", "28/03/2024", "3/11/2024", 1));
            sanPhamDAO.themSanpham(new SanPham(0, "Cơm tự chọn", String.valueOf(R.drawable.comvanphong), 25000
                    , "3", "28/03/2024", "28/03/2024", 1));
            sanPhamDAO.themSanpham(new SanPham(0, "Bánh mì", String.valueOf(R.drawable.banhminoback), 40000
                    , "4", "28/03/2024", "28/03/2024", 1));
            sanPhamDAO.themSanpham(new SanPham(0, "Bánh xèo", String.valueOf(R.drawable.banhxeonoback), 40000
                    , "4", "28/03/2024", "28/03/2024", 1));
            sanPhamDAO.themSanpham(new SanPham(0, "Bánh bao", String.valueOf(R.drawable.banhbaonoback), 40000
                    , "4", "28/03/2024", "28/03/2024", 1));
            sanPhamDAO.themSanpham(new SanPham(0, "Kem", String.valueOf(R.drawable.kem), 25000
                    , "5", "28/03/2024", "28/03/2024", 1));
            sanPhamDAO.themSanpham(new SanPham(0, "Sữa chua", String.valueOf(R.drawable.suachua), 25000
                    , "5", "28/03/2024", "28/03/2024", 1));
            sanPhamDAO.themSanpham(new SanPham(0, "Trà sữa", String.valueOf(R.drawable.trausuadautay), 25000
                    , "5", "28/03/2024", "28/03/2024", 1));
        }
        loaiSanPhamList = loaiSanPhamDAO.getDSLoaiSanPham();
        topSanPham = thongKeDAO.getTop();
        if (topSanPham.isEmpty() || topSanPham.size() < 10) {
            topSanPham = sanPhamDAO.getDSSanPham();
        }
        sanPhamList = sanPhamDAO.getDSSanPham();
        setRCLAdapter();
    }

    private void anhXa() {
        context = view.getContext();
        edt_findFood = view.findViewById(R.id.edt_findFood);
        Home_TIL_findFood = view.findViewById(R.id.Home_TIL_findFood);
        RCL_loaiSP = view.findViewById(R.id.RCL_loaiSP);
        RCL_SP = view.findViewById(R.id.RCL_SP);
        RCL_TSP = view.findViewById(R.id.RCL_TSP);
        loaiSanPhamDAO = new LoaiSanPhamDAO(context);
        sanPhamDAO = new SanPhamDAO(context);
        thongKeDAO = new ThongKeDAO(context);
        viewPager = view.findViewById(R.id.viewpager);
        indicator = view.findViewById(R.id.circle);
        Home_Show_All = view.findViewById(R.id.Home_Show_All);
    }

}