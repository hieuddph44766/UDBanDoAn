package com.example.UDBanDoAn.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.UDBanDoAn.MODEL.LoaiSanPham;
import com.example.UDBanDoAn.R;


import java.util.List;

public class LoaiSanPhamSpinnerAdapter extends ArrayAdapter<LoaiSanPham> {
    private Context context;
    private List<LoaiSanPham>list;
    TextView LoaiSanPhamSPN;
    public LoaiSanPhamSpinnerAdapter(@NonNull Context context, List<LoaiSanPham> list) {
        super(context, 0, list);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          view =  inflater.inflate(R.layout.loai_san_pham_spn,null);

        }
        final LoaiSanPham item = list.get(position);
        if (item!=null){
            LoaiSanPhamSPN = view.findViewById(R.id.loaiSanPhamSPN);
            LoaiSanPhamSPN.setText(item.getNameLoaisanpham());
        }
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        if (view == null){
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          view =  inflater.inflate(R.layout.loai_san_pham_spn,null);

        }
        final LoaiSanPham item = list.get(position);
        if (item!=null){
            LoaiSanPhamSPN = view.findViewById(R.id.loaiSanPhamSPN);
            LoaiSanPhamSPN.setText(item.getNameLoaisanpham());
        }
        return view;
    }
}
