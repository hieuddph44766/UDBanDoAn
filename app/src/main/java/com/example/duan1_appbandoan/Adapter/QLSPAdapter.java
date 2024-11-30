package com.example.duan1_appbandoan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_appbandoan.Model.Product;
import com.example.duan1_appbandoan.R;
import com.example.duan1_appbandoan.ViewActivity.QuanLySP;

import java.util.List;

public class QLSPAdapter extends RecyclerView.Adapter<QLSPAdapter.ProductViewHolder> {
    private List<Product> productList;
    private Context context;

    public QLSPAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_qlsp, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.txtProductName.setText(product.getName());
        holder.txtProductPrice.setText("Giá: " + product.getPrice() + " VNĐ");
        // Sự kiện sửa sản phẩm
        holder.itemView.setOnClickListener(v -> {
            // Gọi phương thức sửa sản phẩm
            ((QuanLySP) context).showEditProductDialog(product);
        });

        // Sự kiện xóa sản phẩm
        holder.itemView.setOnLongClickListener(v -> {
            // Gọi phương thức xóa sản phẩm
            ((QuanLySP) context).showDeleteProductDialog(product);
            return true;
        });
        // Sự kiện sửa sản phẩm khi nhấn button
        holder.btnEdit.setOnClickListener(v -> {
            // Gọi phương thức sửa sản phẩm
            ((QuanLySP) context).showEditProductDialog(product);
        });

        // Sự kiện xóa sản phẩm khi nhấn button
        holder.btnDelete.setOnClickListener(v -> {
            // Gọi phương thức xóa sản phẩm
            ((QuanLySP) context).showDeleteProductDialog(product);
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView txtProductName, txtProductPrice;
        Button btnEdit, btnDelete;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            txtProductName = itemView.findViewById(R.id.txt_product_name);
            txtProductPrice = itemView.findViewById(R.id.txt_product_price);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}

