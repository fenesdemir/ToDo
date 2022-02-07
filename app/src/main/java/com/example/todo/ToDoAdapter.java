package com.example.todo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder>{

    ToDo[] toDos;
    Context context;
    String token;

    public ToDoAdapter(ToDo[] toDos,MainActivity activity) {
        this.toDos = toDos;
        this.context = activity;
        this.token = activity.token;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.todo_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final ToDo todolist = toDos[position];
        holder.tvID.setText(todolist.getId().toString());
        holder.tvTitle.setText(todolist.getTitle().toString());
        holder.tvReminder.setText(todolist.getReminder().toString());
        holder.tvDate.setText(todolist.getDate().toString());
        holder.tvIsdone.setText(todolist.getIsDone().toString());
        holder.tvIcon.setText(todolist.getIcon().toString());
        holder.tvColor.setText(todolist.getColor().toString());
        holder.tvUser.setText(todolist.getUser().toString());
        holder.tvV.setText(todolist.getV().toString());

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "Deleted " + holder.tvTitle.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(context, "Updated " + holder.tvTitle.getText(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, UpdateActivity.class);
                intent.putExtra("token",token);
                intent.putExtra("ID",holder.tvID.getText().toString());
                intent.putExtra("title",holder.tvTitle.getText().toString());
                intent.putExtra("icon",holder.tvIcon.getText().toString());
                intent.putExtra("color",holder.tvColor.getText().toString());
                intent.putExtra("reminder",Boolean.parseBoolean(holder.tvReminder.getText().toString()));
                intent.putExtra("isDone",Boolean.parseBoolean(holder.tvIsdone.getText().toString()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bear = "Bearer " + token;

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("https://todooktay.herokuapp.com/api/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                InterfaceAPI interfaceAPI = retrofit.create(InterfaceAPI.class);

                Call<ResponseBody> call = interfaceAPI.deleteToDo(bear, holder.tvID.getText().toString());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                        if(!response.isSuccessful()){
                            Toast.makeText(context, response.code(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(context, "Successfully Deleted!", Toast.LENGTH_SHORT).show();
                        ((MainActivity) context).recreate();
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        //Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                        Toast.makeText(context, "Failure", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        
    }

    @Override
    public int getItemCount() {
        return toDos.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvID, tvTitle, tvReminder, tvDate, tvIsdone, tvIcon, tvColor, tvUser, tvV;
        Button btnUpdate, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvID = itemView.findViewById(R.id.td_id);
            tvTitle = itemView.findViewById(R.id.td_title);
            tvReminder = itemView.findViewById(R.id.td_reminder);
            tvDate = itemView.findViewById(R.id.td_date);
            tvIsdone = itemView.findViewById(R.id.td_isdone);
            tvIcon = itemView.findViewById(R.id.td_icon);
            tvColor = itemView.findViewById(R.id.td_color);
            tvUser = itemView.findViewById(R.id.td_user);
            tvV = itemView.findViewById(R.id.td_v);
            btnUpdate = itemView.findViewById(R.id.update);
            btnDelete = itemView.findViewById(R.id.delete);
        }
    }
}
