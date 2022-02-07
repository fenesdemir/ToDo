package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    EditText etEmail, etPassword;
    Button btnSignin, btnSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.email);
        etPassword = findViewById(R.id.password);
        btnSignin = findViewById(R.id.signin);
        btnSignup = findViewById(R.id.signup);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://todooktay.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceAPI interfaceAPI = retrofit.create(InterfaceAPI.class);

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = etEmail.getText().toString();
                String pass = etPassword.getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass)){

                    Login login = new Login(email,pass);
                    Call<LoginResponse> call = interfaceAPI.login(login);
                    call.enqueue(new Callback<LoginResponse>() {
                        @Override
                        public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                            //String s = response.body().string();
                            //Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                            LoginResponse loginResponse = response.body();
                            String token = loginResponse.getToken();
                            String email = loginResponse.getEmail();
                            //Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("token",token);
                            intent.putExtra("email",email);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<LoginResponse> call, Throwable t) {
                            //Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this, "Login Failure", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(LoginActivity.this, "Please Enter Required Information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}