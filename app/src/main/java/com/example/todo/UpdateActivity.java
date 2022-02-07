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
import android.widget.Toast;

import java.util.Calendar;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateActivity extends AppCompatActivity {

    EditText titleET, iconET, colorET;
    Button updateBtn;
    RadioGroup rg_rem;
    RadioButton reminderRB;
    RadioGroup rg_done;
    RadioButton isDoneRB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Bundle extras = getIntent().getExtras();
        String token = extras.getString("token");
        String Id = extras.getString("ID");
        String title = extras.getString("title");
        String icon = extras.getString("icon");
        String color = extras.getString("color");
        Boolean reminder = Boolean.valueOf(extras.getString("reminder"));
        Boolean isDone = Boolean.valueOf(extras.getString("isDone"));
        String bear = "Bearer " + token;

        Calendar calendar = Calendar.getInstance();
        //Toast.makeText(UpdateActivity.this, calendar.getTime().toString(), Toast.LENGTH_SHORT).show();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://todooktay.herokuapp.com/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        InterfaceAPI interfaceAPI = retrofit.create(InterfaceAPI.class);

        titleET = findViewById(R.id.title);
        iconET = findViewById(R.id.icon);
        colorET = findViewById(R.id.color);
        updateBtn = findViewById(R.id.update);
        rg_rem = findViewById(R.id.rg);
        rg_done = findViewById(R.id.rg_isDone);

        titleET.setText(title);
        iconET.setText(icon);
        colorET.setText(color);

        if (reminder == true){
            rg_rem.check(R.id.rb_true);
        }else{
            rg_rem.check(R.id.rb_false);
        }

        if (isDone == true){
            rg_done.check(R.id.rb_isDoneTrue);
        }else{
            rg_done.check(R.id.rb_isDonefalse);
        }

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = titleET.getText().toString();
                String icon = iconET.getText().toString();
                String color = colorET.getText().toString();

                int selectedIdRem = rg_rem.getCheckedRadioButtonId();
                reminderRB = (RadioButton) findViewById(selectedIdRem);
                Boolean reminder = Boolean.valueOf(reminderRB.getText().toString());

                int selectedIdıIsdone = rg_done.getCheckedRadioButtonId();
                isDoneRB = (RadioButton) findViewById(selectedIdıIsdone);
                Boolean isDone = Boolean.valueOf(reminderRB.getText().toString());

                if(!TextUtils.isEmpty(title) || !TextUtils.isEmpty(icon) || !TextUtils.isEmpty(color)){

                    ToDoUpdate toDoUpdate = new ToDoUpdate(title, reminder, calendar.getTime().toString(),isDone, icon, color);
                    Call<ResponseBody> call = interfaceAPI.updateToDo(bear, Id, toDoUpdate);
                    call.enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            if(!response.isSuccessful()){
                                Toast.makeText(UpdateActivity.this, response.code(), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            //String s = response.body().string();
                            //Toast.makeText(LoginActivity.this, s, Toast.LENGTH_SHORT).show();
                            //Toast.makeText(ToDoActivity.this, "Post Success!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                            intent.putExtra("token",token);
                            startActivity(intent);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            //Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            Toast.makeText(UpdateActivity.this, "Failure", Toast.LENGTH_SHORT).show();
                        }
                    });

                }else{
                    Toast.makeText(UpdateActivity.this, "Please Enter Required Information", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}