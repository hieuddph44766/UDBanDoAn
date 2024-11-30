package com.example.duan1_appbandoan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_appbandoan.Model.Product;
import com.example.duan1_appbandoan.R;

import java.util.List;

// Trong HoaDonAdapter.java
public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.HoaDonViewHolder> {
    private List<Product> productList;
    private Context context;

    public HoaDonAdapter(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public HoaDonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hoadon, parent, false);
        return new HoaDonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HoaDonViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvProductQuantity.setText("Số lượng: " + product.getQuantity());
        holder.tvHDPrice.setText("VND " + product.getPrice());
        holder.tvTotalPrice.setText("Tổng: VND " + (product.getPrice() * product.getQuantity()));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class HoaDonViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvProductQuantity, tvHDPrice, tvTotalPrice;

        public HoaDonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvProductQuantity = itemView.findViewById(R.id.tvProductQuantity);
            tvHDPrice = itemView.findViewById(R.id.tvHDPrice);
            tvTotalPrice = itemView.findViewById(R.id.tvTotalHDPrice);
        }
    }
}
