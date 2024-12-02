package com.example.duan1_appbandoan.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private UpdateTotalPriceListener updateTotalPriceListener;

    public CartAdapter(List<Product> cartItems, Context context, ProductDAO productDAO) {
        this.cartItems = cartItems;
        this.context = context;
        this.productDAO = productDAO;
    }

    public void setUpdateTotalPriceListener(UpdateTotalPriceListener listener) {
        this.updateTotalPriceListener = listener;
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
        holder.tvQuantity.setText("Số lượng: " + product.getQuantity());
        holder.tvPrice.setText("VND " + product.getTotalSale());


        Glide.with(context)
                .load(product.getImageUrl())
                .placeholder(R.drawable.ic_cart)
                .error(R.drawable.ic_lock)
                .into(holder.imgProduct);

        holder.btnIncrease.setOnClickListener(v -> {
            int newQuantity = product.getQuantity() + 1;
            product.setQuantity(newQuantity);
            productDAO.updateCartItem(1, product.getIdProduct(), newQuantity, product.getPrice());
            notifyItemChanged(position);
            if (updateTotalPriceListener != null) {
                updateTotalPriceListener.onUpdateTotalPrice();
            }
        });

        holder.btnDecrease.setOnClickListener(v -> {
            if (product.getQuantity() > 1) {
                int newQuantity = product.getQuantity() - 1;
                product.setQuantity(newQuantity);
                productDAO.updateCartItem(1, product.getIdProduct(), newQuantity, product.getPrice());
                notifyItemChanged(position);
                if (updateTotalPriceListener != null) {
                    updateTotalPriceListener.onUpdateTotalPrice();
                }
            }
        });

        // Xóa sản phẩm
        holder.btnDelete.setOnClickListener(v -> {
            new AlertDialog.Builder(context)
                    .setTitle("Xác nhận")
                    .setMessage("Bạn có chắc chắn muốn xoá sản phẩm này khỏi giỏ hàng?")
                    .setPositiveButton("Xoá", (dialog, which) -> {
                        productDAO.removeFromCart(1, product.getIdProduct());
                        cartItems.remove(position);
                        notifyItemRemoved(position);
                        if (updateTotalPriceListener != null) {
                            updateTotalPriceListener.onUpdateTotalPrice(); // Cập nhật lại tổng giá
                        }
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
        TextView tvProductName, tvQuantity, tvPrice, tvTotalPrice;
        ImageView btnDecrease, btnIncrease, btnDelete;
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

    // Interface callback
    public interface UpdateTotalPriceListener {
        void onUpdateTotalPrice();
    }
}
