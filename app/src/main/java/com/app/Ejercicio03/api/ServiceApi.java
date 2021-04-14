package com.app.Ejercicio03.api;

import com.app.Ejercicio03.entity.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ServiceApi {

    @GET("posts")
    public abstract Call<List<User>> listTitles();
}
