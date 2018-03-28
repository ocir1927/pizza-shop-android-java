package com.costi.labmobile.cart;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.costi.labmobile.LoginActivity;
import com.costi.labmobile.R;
import com.costi.labmobile.model.Order;
import com.costi.labmobile.model.Pizza;
import com.costi.labmobile.model.User;
import com.costi.labmobile.pizzalist.PizzaListAdapter;
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

import static com.costi.labmobile.pizzalist.PizzaListAdapter.PIZZA_LIST;
import static com.costi.labmobile.pizzalist.PizzaListAdapter.SHOPPING_CART;

public class ShoppingCartActivity extends AppCompatActivity {
    RecyclerView shoppingCartRecyclerView;
    SharedPreferences sharedPreferences;
    ShoppingCartAdapter shoppingCartAdapter;
    Retrofit retrofit;

    ArrayList<Pizza> cartContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

        retrofit = RetrofitSingleton.getInstance();

        Button btnSendOrder = (Button) findViewById(R.id.btn_send_order);
        btnSendOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOrder();
            }
        });


        sharedPreferences = getSharedPreferences(SHOPPING_CART, this.MODE_PRIVATE);

        shoppingCartRecyclerView = (RecyclerView) findViewById(R.id.shopping_cart_recycler_view);

        shoppingCartRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchPizzaFromCart();

        shoppingCartAdapter = new ShoppingCartAdapter(cartContent, this);

        shoppingCartRecyclerView.setAdapter(shoppingCartAdapter);


        shoppingCartAdapter.notifyDataSetChanged();


    }

    private void sendOrder() {
        RetrofitClient client = retrofit.create(RetrofitClient.class);
        String username = sharedPreferences.getString(LoginActivity.USERNAME, "");
        Order order = new Order(new User(username, ""), this.cartContent);

        Call<ResponseBody> call = client.order(order);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(ShoppingCartActivity.this, "Your order was successfully sent", Toast.LENGTH_SHORT).show();
                    clearShoppingCart();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ShoppingCartActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void clearShoppingCart() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(PIZZA_LIST);
        editor.apply();

        shoppingCartAdapter.notifyDataSetChanged();
    }


    void fetchPizzaFromCart() {
        String jsonPizzaList = sharedPreferences.getString(PIZZA_LIST, null);

        if(jsonPizzaList!=null) {
            Gson gson = new Gson();
            Pizza[] pizzaListArray = gson.fromJson(jsonPizzaList,
                    Pizza[].class);
            List<Pizza> cartContent = Arrays.asList(pizzaListArray);
            this.cartContent = new ArrayList<>(cartContent);
        }
        else this.cartContent = new ArrayList<>();

    }
}
