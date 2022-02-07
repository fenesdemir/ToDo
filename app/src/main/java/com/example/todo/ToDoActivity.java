package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ToDoActivity extends AppCompatActivity {

    EditText titleET, iconET, colorET;
    Button postBtn;
    RadioGroup rg;
    RadioButton reminderRB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do);

        String token;
        Bundle extras = getIntent().getExtras();
        token = extras.getString("token");
        String bear = "Bearer " + token;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://todooktay.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceAPI interfaceAPI = retrofit.create(InterfaceAPI.class);

        titleET = findViewById(R.id.title);
        iconET = findViewById(R.id.icon);
        colorET = findViewById(R.id.color);
        postBtn = findViewById(R.id.post);
        rg = findViewById(R.id.rg);

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleET.getText().toString();
                String icon = iconET.getText().toString();
                String color = colorET.getText().toString();

                int selectedId = rg.getCheckedRadioButtonId();
                reminderRB = (RadioButton) findViewById(selectedId);
                Boolean reminder = Boolean.valueOf(reminderRB.getText().toString());

                if(!TextUtils.isEmpty(title) || !TextUtils.isEmpty(icon) || !TextUtils.isEmpty(color)){

                    ToDoPost toDoPost = new ToDoPost(title, reminder, icon, color);
                    Call<ResponseBody> call = interfaceAPI.postToDo(bear, toDoPost);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if(!response.isSuccessful()){
                                Toast.makeText(ToDoActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            //String s = response.body().string();
                            //Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                            Toast.makeText(ToDoActivity.this, "Post Success!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ToDoActivity.this, MainActivity.class);
                            intent.putExtra("token",token);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            //Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(ToDoActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(ToDoActivity.this, "Please Enter Required Information", Toast.LENGTH_SHORT).show();
                }

            }
        });



    }
}