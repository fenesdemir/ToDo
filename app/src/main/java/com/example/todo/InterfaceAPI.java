package com.example.todo;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface InterfaceAPI {

    @POST("user")
    Call<ResponseBody>register(@Body Register register);

    @POST("user/loginByEmail")
    Call<LoginResponse>login(@Body Login login);

    @POST("todo")
    Call<ResponseBody>postToDo(@Header("Authorization") String header, @Body ToDoPost toDoPost);

    @GET("todo")
    Call<List<ToDo>>getToDos(@Header("Authorization") String header);

    @PUT("todo/{id}")
    Call<ResponseBody>updateToDo(@Header("Authorization") String header, @Path("id") String postId, @Body ToDoUpdate toDoUpdate);

    @DELETE("todo/{id}")
    Call<ResponseBody>deleteToDo(@Header("Authorization") String header, @Path("id") String postId);
}
