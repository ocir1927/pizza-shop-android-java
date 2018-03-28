package com.costi.labmobile.pizzalist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.costi.labmobile.R;
import com.costi.labmobile.model.Pizza;
import com.costi.labmobile.retrofit.RetrofitClient;
import com.costi.labmobile.retrofit.RetrofitSingleton;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import static com.costi.labmobile.pizzalist.PizzaListAdapter.SHOPPING_CART;

public class PizzaListActivity extends AppCompatActivity {
    public static final String PIZZA_FROM_SERVER = "PIZZA_FROM_SERVER";


    SharedPreferences sharedPreferences;
    RecyclerView pizzaRecylerView;
    ArrayList<Pizza> pizzaList;
    Retrofit retrofit;
    PizzaListAdapter pizzaListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_list);
        pizzaList = new ArrayList<>();
        retrofit = RetrofitSingleton.getInstance();
        sharedPreferences = getSharedPreferences(SHOPPING_CART, Context.MODE_PRIVATE);


        fetchPizzaList();

        pizzaRecylerView = (RecyclerView) findViewById(R.id.pizza_recycler_view);
        pizzaListAdapter = new PizzaListAdapter(pizzaList, this);
        pizzaRecylerView.setAdapter(pizzaListAdapter);
        pizzaRecylerView.setItemAnimator(new DefaultItemAnimator());


        pizzaRecylerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fetchPizzaList() {
        if (!isNetworkAvailable()) {
            fetchLocalPizzaList();
            return;
        }

        RetrofitClient client = retrofit.create(RetrofitClient.class);

        Call<List<Pizza>> call = client.getPizzaList();

        call.enqueue(new Callback<List<Pizza>>() {
            @Override
            public void onResponse(Call<List<Pizza>> call, Response<List<Pizza>> response) {
                if (response.body() != null) {
                    pizzaList.addAll(response.body());
                    savePizzaListLocal();
                    pizzaListAdapter.notifyDataSetChanged();
                } else
                    Toast.makeText(PizzaListActivity.this, "No pizza list", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<List<Pizza>> call, Throwable t) {
                Toast.makeText(PizzaListActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }



    void savePizzaListLocal() {
        if (sharedPreferences.contains(PIZZA_FROM_SERVER)) {
            return;
        }
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        editor.putString(PIZZA_FROM_SERVER, gson.toJson(pizzaList));

        editor.apply();
    }

    void fetchLocalPizzaList() {
        if (sharedPreferences.contains(PIZZA_FROM_SERVER)) {
            String jsonPizzaList = sharedPreferences.getString(PIZZA_FROM_SERVER, null);
            Gson gson = new Gson();
            Pizza[] pizzaListArray = gson.fromJson(jsonPizzaList,
                    Pizza[].class);

            List<Pizza> pizzas = Arrays.asList(pizzaListArray);
            pizzaList = new ArrayList<>(pizzas);
        }


    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }




}
