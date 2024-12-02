package com.example.duan1_appbandoan.ViewActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_appbandoan.AdminActivity;
import com.example.duan1_appbandoan.DAO.UserDAO;
import com.example.duan1_appbandoan.DataBase.Dbhelper;
import com.example.duan1_appbandoan.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class CaNhanActivity extends AppCompatActivity {
    UserDAO dao=new UserDAO(this);
    BottomNavigationView bottom_NaView;

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

        bottom_NaView = findViewById(R.id.bottom_NaView);
        bottom_NaView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.menu_CaNhan) {
                    startActivity(new Intent(getApplicationContext(), CaNhanActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                } else if (item.getItemId() == R.id.menu_hoadon) {
                    // Chạy Activity quản lý đơn hàng
                    startActivity(new Intent(getApplicationContext(), QuanLyDHAcitivity.class));
                    overridePendingTransition(0, 0);
                    bottom_NaView.setSelectedItemId(R.id.menu_hoadon); // Đánh dấu mục được chọn
                    return true;
                } else if (item.getItemId() == R.id.menu_thongke) {
                    // Chạy Activity thống kê
                    startActivity(new Intent(getApplicationContext(), ThongKeActivity.class));
                    overridePendingTransition(0, 0);
                    bottom_NaView.setSelectedItemId(R.id.menu_thongke); // Đánh dấu mục được chọn
                    return true;
                } else if (item.getItemId() == R.id.menu_Home) {
                    // Chạy Activity cá nhân
                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                    overridePendingTransition(0, 0);
                    bottom_NaView.setSelectedItemId(R.id.menu_CaNhan); // Đánh dấu mục được chọn
                    return true;
                } else {
                    return false;
                }
            }
        });


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
                String userName = input.getText().toString();
                String newPassword = input.getText().toString();

                if (newPassword.isEmpty()) {
                    Toast.makeText(this, "Mật khẩu không được để trống!", Toast.LENGTH_SHORT).show();
                } else {
                    // Gọi DatabaseHelper để lưu mật khẩu mới
                    boolean isUpdated = dao.updatePassword(userName, newPassword);

                    if (isUpdated) {
                        Toast.makeText(this, "Đổi mật khẩu thành công!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Đổi mật khẩu thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
                Log.d("UserDAO", "Updating password for user: " + userName);

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
