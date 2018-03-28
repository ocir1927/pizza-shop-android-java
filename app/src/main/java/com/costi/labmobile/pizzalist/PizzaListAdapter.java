package com.costi.labmobile.pizzalist;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.costi.labmobile.R;
import com.costi.labmobile.model.Pizza;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Costi  on 14.01.2018 . All rights reserved.
 */

public class PizzaListAdapter extends RecyclerView.Adapter<PizzaListAdapter.PizzaViewHolder> {
    public static final String SHOPPING_CART = "SHOPPING CART";
    public static final String PIZZA_LIST = "PIZZA_LIST";

    private List<Pizza> pizzaList;
    Context context;
    SharedPreferences sharedPreferences;

    public PizzaListAdapter(List<Pizza> pizzaList, Context context) {
        this.pizzaList = pizzaList;
        this.context = context;
        this.sharedPreferences =  context.getSharedPreferences(SHOPPING_CART,Context.MODE_PRIVATE);
    }

    class PizzaViewHolder extends RecyclerView.ViewHolder {
        ImageView pizzaImage;
        TextView pizzaName;
        TextView pizzaIncredients;
        TextView pizzaPrice;
        Button btnAddToCart;

        PizzaViewHolder(View itemView) {
            super(itemView);
            pizzaImage = (ImageView) itemView.findViewById(R.id.card_image);
            pizzaName = (TextView) itemView.findViewById(R.id.text_view_title);
            pizzaIncredients = (TextView) itemView.findViewById(R.id.text_view_subtitle);
            pizzaPrice = (TextView) itemView.findViewById(R.id.price);
            btnAddToCart = (Button) itemView.findViewById(R.id.btn_add_to_cart);

        }
    }

    @Override
    public PizzaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.recycler_view_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        PizzaViewHolder vh = new PizzaViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(PizzaViewHolder holder, int position) {
        final Pizza pizza = pizzaList.get(position);
        Glide.with(context)
                .load(pizza.getPhotoUrl())
                .centerCrop()
                .into(holder.pizzaImage);
        holder.pizzaName.setText(pizza.getName());
        holder.pizzaIncredients.setText(String.valueOf(pizza.getContains()));
        holder.pizzaPrice.setText(String.valueOf(pizza.getPrice() + " RON"));
        holder.btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPizzaToCart(pizza);
            }
        });

    }

    private void addPizzaToCart(Pizza pizza) {
        if (sharedPreferences.contains(PIZZA_LIST)) {
            String jsonPizzaList = sharedPreferences.getString(PIZZA_LIST, null);
            Gson gson = new Gson();
            Pizza[] pizzaListArray = gson.fromJson(jsonPizzaList,
                    Pizza[].class);

            List<Pizza> cartContent = Arrays.asList(pizzaListArray);
            cartContent = new ArrayList<>(cartContent);
            cartContent.add(pizza);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(PIZZA_LIST,gson.toJson(cartContent));

            editor.apply();

            Toast.makeText(context, "Succesfuly added to cart", Toast.LENGTH_SHORT).show();
        }
        else
        {
            ArrayList<Pizza> pizzaArrayList = new ArrayList<>();
            pizzaArrayList.add(pizza);

            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();

            editor.putString(PIZZA_LIST,gson.toJson(pizzaArrayList));
            editor.apply();

            Toast.makeText(context, "Succesfuly added to cart, was just one", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public int getItemCount() {
        return pizzaList.size();
    }

}
