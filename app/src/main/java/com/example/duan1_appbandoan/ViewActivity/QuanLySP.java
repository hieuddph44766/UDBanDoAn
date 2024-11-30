package com.example.duan1_appbandoan.ViewActivity;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_appbandoan.Adapter.ProductAdapter;
import com.example.duan1_appbandoan.Adapter.QLSPAdapter;
import com.example.duan1_appbandoan.DAO.ProductDAO;
import com.example.duan1_appbandoan.Model.Product;
import com.example.duan1_appbandoan.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class QuanLySP extends AppCompatActivity {
    private RecyclerView recyclerView;
    private QLSPAdapter adapter;
    private List<Product> productList;
    private ProductDAO productDAO;
    private FloatingActionButton fabAddProduct;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quan_ly_sp);
        recyclerView = findViewById(R.id.recycler_product_list);
        fabAddProduct=findViewById(R.id.fab_add_product);
        productDAO = new ProductDAO(this);
        productList = productDAO.getAllProducts();

        adapter = new QLSPAdapter(this, productList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        fabAddProduct.setOnClickListener(view -> {
            showAddProductDialog();
        });
    }
    private void showAddProductDialog() {
        // Inflate layout cho Dialog
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_sp, null);

        // Khởi tạo các thành phần trong Dialog
        EditText edtProductName = dialogView.findViewById(R.id.edtProductName);
        EditText edtProductDescription = dialogView.findViewById(R.id.edtProductDescription);
        EditText edtProductPrice = dialogView.findViewById(R.id.edtProductPrice);
        EditText edtProductCategory = dialogView.findViewById(R.id.edtProductCategory);

        // Tạo Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Thêm sản phẩm")
                .setView(dialogView)
                .setPositiveButton("Thêm", null) // Tạm thời không xử lý nút "Thêm"
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());  // Xử lý nút Hủy

        // Tạo dialog
        AlertDialog dialog = builder.create();

        // Hiển thị Dialog
        dialog.show();

        // Xử lý sự kiện khi nhấn nút "Thêm"
        Button btnAdd = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        btnAdd.setOnClickListener(view -> {
            // Lấy dữ liệu từ các trường nhập
            String name = edtProductName.getText().toString().trim();
            String description = edtProductDescription.getText().toString().trim();
            String priceText = edtProductPrice.getText().toString().trim();
            String categoryText = edtProductCategory.getText().toString().trim();

            // Kiểm tra dữ liệu rỗng
            if (name.isEmpty() || description.isEmpty() || priceText.isEmpty() || categoryText.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;  // Không đóng Dialog và không thêm sản phẩm nếu thiếu thông tin
            }

            int price, category;
            try {
                price = Integer.parseInt(priceText); // Chuyển giá sang kiểu int
                category = Integer.parseInt(categoryText); // Chuyển danh mục sang kiểu int
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá và danh mục phải là số hợp lệ", Toast.LENGTH_SHORT).show();
                return;  // Không thêm sản phẩm nếu giá và danh mục không hợp lệ
            }

            // Tạo đối tượng Product mới và thêm vào cơ sở dữ liệu
            Product product = new Product();
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setIdCategory(category);

            // Thêm sản phẩm vào cơ sở dữ liệu
            boolean isInserted = productDAO.insertProduct(product);
            if (isInserted) {
                Toast.makeText(this, "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                loadProductList(); // Load lại danh sách sản phẩm
                dialog.dismiss();  // Đóng Dialog khi thêm sản phẩm thành công
            } else {
                Toast.makeText(this, "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Hiển thị dialog để sửa sản phẩm
    public void showEditProductDialog(Product product) {
        // Inflate layout cho Dialog
        View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_sp, null);

        // Khởi tạo các thành phần trong Dialog
        EditText edtProductName = dialogView.findViewById(R.id.edtProductName);
        EditText edtProductDescription = dialogView.findViewById(R.id.edtProductDescription);
        EditText edtProductPrice = dialogView.findViewById(R.id.edtProductPrice);
        EditText edtProductCategory = dialogView.findViewById(R.id.edtProductCategory);

        // Điền sẵn thông tin của sản phẩm vào các trường
        edtProductName.setText(product.getName());
        edtProductDescription.setText(product.getDescription());
        edtProductPrice.setText(String.valueOf(product.getPrice()));
        edtProductCategory.setText(String.valueOf(product.getIdCategory()));

        // Tạo Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sửa sản phẩm")
                .setView(dialogView)
                .setPositiveButton("Lưu", null)  // Tạm thời không xử lý nút "Lưu"
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());  // Xử lý nút Hủy

        // Tạo dialog
        AlertDialog dialog = builder.create();

        // Hiển thị Dialog
        dialog.show();

        // Xử lý sự kiện khi nhấn nút "Lưu"
        Button btnSave = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        btnSave.setOnClickListener(view -> {
            // Lấy dữ liệu từ các trường nhập
            String name = edtProductName.getText().toString().trim();
            String description = edtProductDescription.getText().toString().trim();
            String priceText = edtProductPrice.getText().toString().trim();
            String categoryText = edtProductCategory.getText().toString().trim();

            // Kiểm tra dữ liệu rỗng
            if (name.isEmpty() || description.isEmpty() || priceText.isEmpty() || categoryText.isEmpty()) {
                Toast.makeText(this, "Vui lòng điền đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                return;  // Không đóng Dialog và không cập nhật nếu thiếu thông tin
            }

            int price, category;
            try {
                price = Integer.parseInt(priceText);  // Chuyển giá sang kiểu int
                category = Integer.parseInt(categoryText);  // Chuyển danh mục sang kiểu int
            } catch (NumberFormatException e) {
                Toast.makeText(this, "Giá và danh mục phải là số hợp lệ", Toast.LENGTH_SHORT).show();
                return;  // Không cập nhật nếu giá và danh mục không hợp lệ
            }

            // Cập nhật thông tin sản phẩm
            product.setName(name);
            product.setDescription(description);
            product.setPrice(price);
            product.setIdCategory(category);

            // Cập nhật sản phẩm vào cơ sở dữ liệu
            int result = productDAO.updateProduct(product);
            if (result > 0) {
                Toast.makeText(this, "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                loadProductList();  // Load lại danh sách sản phẩm
                dialog.dismiss();  // Đóng Dialog khi cập nhật thành công
            } else {
                Toast.makeText(this, "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // Xử lý sự kiện khi nhấn vào sản phẩm để xóa
    public void showDeleteProductDialog(Product product) {
        new AlertDialog.Builder(this)
                .setTitle("Xóa sản phẩm")
                .setMessage("Bạn có chắc chắn muốn xóa sản phẩm này?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    // Xóa sản phẩm khỏi cơ sở dữ liệu
                    int result = productDAO.deleteProduct(product.getIdProduct());
                    if (result > 0) {
                        Toast.makeText(this, "Xóa sản phẩm thành công", Toast.LENGTH_SHORT).show();
                        loadProductList();  // Load lại danh sách sản phẩm
                    } else {
                        Toast.makeText(this, "Xóa sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }



    private void loadProductList() {
        // Xóa danh sách cũ trước khi load lại
        productList.clear();

        // Lấy danh sách sản phẩm từ database
        List<Product> products = productDAO.getAllProducts();
        if (products != null && !products.isEmpty()) {
            productList.addAll(products);
        }
        // Thông báo cho adapter cập nhật dữ liệu
        adapter.notifyDataSetChanged();
    }


}