package com.example.duan1_appbandoan.ViewActivity;

import static com.example.duan1_appbandoan.AppContanst.PASSWORD;
import static com.example.duan1_appbandoan.AppContanst.REMEMBER;
import static com.example.duan1_appbandoan.AppContanst.USERNAME;
import static com.example.duan1_appbandoan.AppContanst.USER_FILE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1_appbandoan.AdminActivity;
import com.example.duan1_appbandoan.DAO.UserDAO;
import com.example.duan1_appbandoan.MainActivity;
import com.example.duan1_appbandoan.R;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {
    TextInputEditText edUserName, edPassword;
    Button btnLogin, btnDangky;
    CheckBox chkRememberPass;
    String strUser, strPass;
    UserDAO Userdao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ view
        edUserName = findViewById(R.id.edt_user);
        edPassword = findViewById(R.id.edt_pass);
        btnLogin = findViewById(R.id.btn_login);
        btnDangky = findViewById(R.id.btn_register);
        chkRememberPass = findViewById(R.id.ckb_loginRemember);
        Userdao = new UserDAO(this);

        // Lấy thông tin lưu trữ từ SharedPreferences
        SharedPreferences pref = getSharedPreferences(USER_FILE, MODE_PRIVATE);
        String user = pref.getString(USERNAME, "");
        String pass = pref.getString(PASSWORD, "");
        Boolean rem = pref.getBoolean(REMEMBER, false);

        edUserName.setText(user);
        edPassword.setText(pass);
        chkRememberPass.setChecked(rem);

        // Xử lý sự kiện
        btnDangky.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        btnLogin.setOnClickListener(v -> checkLogin());
    }

    public void rememberUser(String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences(USER_FILE, MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status) {
            edit.clear();
        } else {
            edit.putString(USERNAME, u);
            edit.putString(PASSWORD, p);
            edit.putBoolean(REMEMBER, status);
        }
        edit.apply();
    }

    public void checkLogin() {
        strUser = edUserName.getText().toString();
        strPass = edPassword.getText().toString();

        if (strUser.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Bạn phải nhập tài khoản hoặc mật khẩu.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra đăng nhập
        int role = Userdao.checkLogin(strUser, strPass);
        if (role == -1) {
            Toast.makeText(getApplicationContext(), "Sai tài khoản hoặc mật khẩu.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "Đăng nhập thành công.", Toast.LENGTH_SHORT).show();
            rememberUser(strUser, strPass, chkRememberPass.isChecked());

            // Chuyển đến Activity tương ứng
            Intent intent;
            if (role == 0) {
                intent = new Intent(getApplicationContext(), AdminActivity.class); // Dành cho admin
            } else {
                intent = new Intent(getApplicationContext(), MainActivity.class); // Dành cho user
            }
            intent.putExtra("user", strUser);
            startActivity(intent);
            finish();
        }
    }
}
