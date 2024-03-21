package com.ph30891.asm_ph30891_qlsv.networking;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpRequest {
    private ApiServices  apiServices;

    public HttpRequest(){
        apiServices = new Retrofit.Builder()
                .baseUrl(apiServices.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(ApiServices.class);

    }
    public ApiServices callAPI(){
        return apiServices;
    }
}
