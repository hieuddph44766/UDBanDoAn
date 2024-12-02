package com.example.duan1_appbandoan.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duan1_appbandoan.Model.Product;
import com.example.duan1_appbandoan.R;
import com.example.duan1_appbandoan.ViewActivity.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private List<Product> filteredProductList;

    private Context context;

    public ProductAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
        this.filteredProductList = new ArrayList<>(productList);
    }
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charSequenceString = constraint.toString();
                if (charSequenceString.isEmpty()) {
                    filteredProductList = productList;
                } else {
                    List<Product> filteredList = new ArrayList<>();
                    for (Product product : productList) {
                        if (product.getName().toLowerCase().contains(charSequenceString.toLowerCase())) {
                            filteredList.add(product);
                        }
                        filteredProductList = filteredList;
                    }
                }
                FilterResults results = new FilterResults();
                results.values = filteredProductList;
                return results;
            }
            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredProductList = (List<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }
    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = filteredProductList.get(position);

        holder.productName.setText(product.getName());
        holder.productDescription.setText(product.getDescription());
        holder.productTotalSale.setText("Total Sale: " + product.getTotalSale());
        Glide.with(context)
                .load(product.getImageResId())
                .error(R.drawable.ic_launcher_background)
                .into(holder.img_FoodItem);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ProductDetailActivity.class);
                intent.putExtra("product", product);
                context.startActivity(intent);
            }
        });

    }
    @Override
    public int getItemCount() {
        return filteredProductList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productName, productDescription, productTotalSale;
        Button btnEdit,btnDelete;
        ImageView img_FoodItem;
        public ProductViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productDescription = itemView.findViewById(R.id.productDescription);
            productTotalSale = itemView.findViewById(R.id.productTotalSale);
            btnEdit = itemView.findViewById(R.id.btn_edit);
            btnDelete = itemView.findViewById(R.id.btn_delete);
            img_FoodItem = itemView.findViewById(R.id.img_FoodItem);
        }
    }
}

