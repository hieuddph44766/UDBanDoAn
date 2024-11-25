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
        edUserName = findViewById(R.id.edt_user);
        edPassword = findViewById(R.id.edt_pass);
        btnLogin = findViewById(R.id.btn_login);
        btnDangky = findViewById(R.id.btn_register);
        chkRememberPass = findViewById(R.id.ckb_loginRemember);
        Userdao = new UserDAO(this);

        SharedPreferences pref = getSharedPreferences(USER_FILE, MODE_PRIVATE);
        String user = pref.getString(USERNAME, "");
        String pass = pref.getString(PASSWORD, "");
        Boolean rem = pref.getBoolean(REMEMBER, false);

        edUserName.setText(user);
        edPassword.setText(pass);
        chkRememberPass.setChecked(rem);

        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
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
        edit.commit();
    }

    public void checkLogin() {
        strUser = edUserName.getText().toString();
        strPass = edPassword.getText().toString();
        if (strUser.isEmpty() || strPass.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Bạn Phải Nhập Tài Khoản Hoặc Mật Khẩu.", Toast.LENGTH_SHORT).show();
        } else {
            if (Userdao.checkLogin(strUser, strPass) > 0) {
                Toast.makeText(getApplicationContext(), "Đăng Nhập Thành Công", Toast.LENGTH_SHORT).show();
                rememberUser(strUser, strPass, chkRememberPass.isChecked());
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                i.putExtra("user", strUser);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Sai Tài Khoản Hoặc Mật Khẩu.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
