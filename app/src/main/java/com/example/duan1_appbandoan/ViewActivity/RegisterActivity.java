package com.example.duan1_appbandoan.ViewActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1_appbandoan.DAO.UserDAO;
import com.example.duan1_appbandoan.Model.User;
import com.example.duan1_appbandoan.R;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {
    private UserDAO Userdao;
    TextInputEditText edt_rePassword,edt_Password,edt_UserName;
    Button btnRegister,btn_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    edt_UserName=findViewById(R.id.edt_loginUser);

    edt_Password=findViewById(R.id.edt_registerPass);
    edt_rePassword=findViewById(R.id.edt_registerRePass);
    btnRegister=findViewById(R.id.btn_register);
    btn_back=findViewById(R.id.btn_cancel);

    Userdao= new UserDAO(this);
        btnRegister.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String user=edt_UserName.getText().toString();
            String pass=edt_Password.getText().toString();
            String rePass=edt_rePassword.getText().toString();
            if(!pass.equals(rePass)){
                Toast.makeText(RegisterActivity.this, "Nhập lại mật khẩu sai", Toast.LENGTH_SHORT).show();
            }else{
                long check=Userdao.insertUser(new User(user,pass));
                if (check >0){
                    Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    finish();
                }else{
                    Toast.makeText(RegisterActivity.this, "Đăng ký thất bại", Toast.LENGTH_SHORT).show();
                }
            }
        }
    });
        btn_back.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent= new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(intent);
        }
    });
    }

}