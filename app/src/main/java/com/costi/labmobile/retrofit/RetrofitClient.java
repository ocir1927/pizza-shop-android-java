package com.costi.labmobile.retrofit;

import android.support.v7.widget.RecyclerView;

import com.costi.labmobile.model.Order;
import com.costi.labmobile.model.Pizza;
import com.costi.labmobile.model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by Costi on 14.01.2018.
 */

public interface RetrofitClient {
    @POST("/users")
    Call<ResponseBody> login(@Body User user);

    @GET("/pizza")
    Call<List<Pizza>> getPizzaList();

    @POST("/comanda")
    Call<ResponseBody> order(@Body Order pizzaList);


}
