package com.example.duan1_appbandoan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1_appbandoan.DAO.ProductDAO;
import com.example.duan1_appbandoan.Model.Product;
import com.example.duan1_appbandoan.R;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private List<Product> cartItems;
    private Context context;
    private ProductDAO productDAO;

    public CartAdapter(List<Product> cartItems, Context context, ProductDAO productDAO) {
        this.cartItems = cartItems;
        this.context = context;
        this.productDAO = productDAO;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Product product = cartItems.get(position);
        holder.tvProductName.setText(product.getName());
        holder.tvQuantity.setText("Số : " + product.getQuantity());
        holder.tvPrice.setText("VND" + product.getPrice());

        // Tải ảnh từ URL hoặc từ resource
        Glide.with(context)
                .load(product.getImageUrl())  // Tải ảnh từ URL hoặc từ resource
                .placeholder(R.drawable.ic_cart)  // Ảnh mặc định khi chưa tải
                .error(R.drawable.ic_lock)  // Ảnh lỗi nếu có sự cố khi tải
                .into(holder.imgProduct);

        // Xử lý sự kiện tăng số lượng
        holder.btnIncrease.setOnClickListener(v -> {
            int newQuantity = product.getQuantity() + 1;
            product.setQuantity(newQuantity);
            productDAO.updateCartItem(1, product.getIdProduct(), newQuantity, product.getPrice());
            notifyItemChanged(position);
        });

        // Xử lý sự kiện giảm số lượng
        holder.btnDecrease.setOnClickListener(v -> {
            if (product.getQuantity() > 1) {
                int newQuantity = product.getQuantity() - 1;
                product.setQuantity(newQuantity);
                productDAO.updateCartItem(1, product.getIdProduct(), newQuantity, product.getPrice());
                notifyItemChanged(position);
            }
        });

        // Xử lý sự kiện xóa sản phẩm

        holder.btnDelete.setOnClickListener(v -> {
            // Tạo một AlertDialog để xác nhận hành động xoá
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn xoá sản phẩm này khỏi giỏ hàng?")
                    .setPositiveButton("Xoá", (dialog, which) -> {
                        productDAO.removeFromCart(1, product.getIdProduct());
                        cartItems.remove(position);
                        notifyItemRemoved(position);
                        //updateTotalPrice(cartItems);  // Cập nhật lại tổng giá trị giỏ hàng
                        Toast.makeText(context, "Sản phẩm đã được xoá", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("Hủy", null)
                    .show();
        });

    }



    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvProductName, tvQuantity, tvPrice;
        ImageButton btnDecrease, btnIncrease, btnDelete;
        ImageView imgProduct;
        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvQuantity = itemView.findViewById(R.id.tvQuantity);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            imgProduct = itemView.findViewById(R.id.imgProduct);
            btnIncrease = itemView.findViewById(R.id.btnIncrease);
            btnDecrease = itemView.findViewById(R.id.btnDecrease);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            
        }
    }
}
