package com.ph30891.asm_ph30891_qlsv.networking;

import com.ph30891.asm_ph30891_qlsv.model.Students;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiServices {
    public static  String BASE_URL = "http://10.0.2.2:3000/";
   @GET("/api/get-st")
   Call<ArrayList<Students>> getListStudent();

   @POST("/api/add-st")
    Call<Students> addStudent(@Body Students students);

   @PUT("/api/update-st/{id}")
    Call<Students> updateStudent(@Path("id") String id,@Body Students students);

   @DELETE("/api/delete-st/{id}")
    Call<Students> removeStudent(@Path("id") String id);

}
