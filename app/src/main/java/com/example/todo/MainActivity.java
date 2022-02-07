package com.example.todo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;
    private RecyclerView recV;
    protected String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String email;
        Bundle extras = getIntent().getExtras();
        email = extras.getString("email");
        token = extras.getString("token");
        String bear = "Bearer " + token;

        floatingActionButton = findViewById(R.id.floatingActionButton);
        recV = findViewById(R.id.rv_todo);
        recV.setHasFixedSize(true);
        recV.setLayoutManager(new LinearLayoutManager(this));

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://todooktay.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceAPI interfaceAPI = retrofit.create(InterfaceAPI.class);
        Call<List<ToDo>> call = interfaceAPI.getToDos(bear);
        call.enqueue(new Callback<List<ToDo>>() {
            @Override
            public void onResponse(Call<List<ToDo>> call, Response<List<ToDo>> response) {
                //Toast.makeText(MainActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                List<ToDo> ToDos = response.body();
                ToDo[] toDoArr = new ToDo[ToDos.size()];
                toDoArr = ToDos.toArray(toDoArr);
                ToDoAdapter toDoAdapter = new ToDoAdapter(toDoArr,MainActivity.this);
                recV.setAdapter(toDoAdapter);
            }
            @Override
            public void onFailure(Call<List<ToDo>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failure!", Toast.LENGTH_SHORT).show();
            }
        });

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ToDoActivity.class);
                intent.putExtra("token",token);
                intent.putExtra("email",email);
                startActivity(intent);
                finish();
            }
        });

    }
}