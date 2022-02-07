package com.example.todo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.POST;

public class RegisterActivity extends AppCompatActivity {

    EditText emailEt, passwordEt, confirmPassEt;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailEt = findViewById(R.id.email);
        passwordEt = findViewById(R.id.password);
        confirmPassEt = findViewById(R.id.Cpassword);
        registerBtn = findViewById(R.id.signup);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://todooktay.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceAPI interfaceAPI = retrofit.create(InterfaceAPI.class);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = emailEt.getText().toString();
                String pass = passwordEt.getText().toString();
                String confPassword = confirmPassEt.getText().toString();

                if(!TextUtils.isEmpty(email) || !TextUtils.isEmpty(pass) || !TextUtils.isEmpty(confPassword)){

                    if(pass.equals(confPassword)){

                        Register register = new Register(email,pass);
                        Call<ResponseBody> call = interfaceAPI.register(register);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                                try {
                                    String s = response.body().string();
                                    //Toast.makeText(RegisterActivity.this, s, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(RegisterActivity.this, "Registeration Success", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                //Toast.makeText(RegisterActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                Toast.makeText(RegisterActivity.this, "Registeration Failure", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }else{
                        Toast.makeText(RegisterActivity.this, "Passwords Don't Match", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(RegisterActivity.this, "Please Enter Required Information", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}