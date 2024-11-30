package com.example.duan1_appbandoan.ViewActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_appbandoan.R;

public class CaNhanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_ca_nhan);

        // Ánh xạ các view
        TextView tvUserName = findViewById(R.id.tv);
        Button btnChangePassword = findViewById(R.id.btn_changePassword);
        Button btnLogout = findViewById(R.id.btn_logout);
        Button btn_qlsp = findViewById(R.id.btn_qlsp);

        // Lấy thông tin người dùng (giả sử lấy từ Intent hoặc SharedPreferences)
        String userName = getIntent().getStringExtra("username");
        if (userName != null) {
            tvUserName.setText("Xin chào, " + userName + "!");
        }
        // Sự kiện cho nút  quản  lý sp
        btn_qlsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CaNhanActivity.this, QuanLySP.class));
                finish();
            }
        });
        // Sự kiện cho nút Đổi mật khẩu
        btnChangePassword.setOnClickListener(v -> showChangePasswordDialog());

        // Sự kiện cho nút Đăng xuất
        btnLogout.setOnClickListener(v -> showLogoutConfirmation());
    }

    // Hiển thị Dialog để đổi mật khẩu
    private void showChangePasswordDialog() {
        // Tạo dialog nhập mật khẩu mới
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đổi mật khẩu");

        // Thêm EditText vào dialog
        final android.widget.EditText input = new android.widget.EditText(this);
        input.setHint("Nhập mật khẩu mới");
        builder.setView(input);

        // Xử lý khi người dùng nhấn OK
        builder.setPositiveButton("OK", (dialog, which) -> {
            String newPassword = input.getText().toString();
            if (newPassword.isEmpty()) {
                Toast.makeText(this, "Mật khẩu không được để trống!", Toast.LENGTH_SHORT).show();
            } else {
                // Gọi API hoặc lưu mật khẩu mới vào SharedPreferences
                Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý khi người dùng nhấn Hủy
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());

        builder.show();
    }

    // Hiển thị xác nhận đăng xuất
    private void showLogoutConfirmation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Đăng xuất");
        builder.setMessage("Bạn có chắc chắn muốn đăng xuất không?");
        builder.setPositiveButton("Đăng xuất", (dialog, which) -> {
            // Xóa thông tin đăng nhập (ví dụ: SharedPreferences)
            Toast.makeText(this, "Đã đăng xuất!", Toast.LENGTH_SHORT).show();

            // Điều hướng về màn hình LoginActivity
            Intent intent = new Intent(CaNhanActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
        builder.setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss());
        builder.show();
    }
}
