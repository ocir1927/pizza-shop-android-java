package com.costi.labmobile.cart;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.costi.labmobile.R;
import com.costi.labmobile.model.Pizza;
import com.costi.labmobile.pizzalist.PizzaListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Costi  on 14.01.2018 . All rights reserved.
 */

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.CartItemViewHolder> {

    ArrayList<Pizza> cartContent;
    Context context;

    public ShoppingCartAdapter(ArrayList<Pizza> cartContent, Context context) {
        this.cartContent = cartContent;
        this.context = context;
    }


    @Override
    public CartItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.cart_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ShoppingCartAdapter.CartItemViewHolder vh = new ShoppingCartAdapter.CartItemViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(CartItemViewHolder holder, int position) {
        Pizza pizza = cartContent.get(position);

        Glide.with(context)
                .load(pizza.getPhotoUrl())
                .centerCrop()
                .into(holder.cartItemImage);
        holder.cartItemTitle.setText(pizza.getName());
        holder.cartItemSubtitle.setText(String.valueOf(pizza.getQuantity())+"g");
        holder.cartItemPrice.setText(String.valueOf(pizza.getPrice() + " RON"));

    }

    @Override
    public int getItemCount() {
        return cartContent.size();
    }

    public class CartItemViewHolder extends RecyclerView.ViewHolder {
        ImageView cartItemImage;
        TextView cartItemTitle;
        TextView cartItemSubtitle;
        TextView cartItemPrice;


        public CartItemViewHolder(View itemView) {
            super(itemView);
            cartItemImage = (ImageView) itemView.findViewById(R.id.cart_item_image);
            cartItemTitle = (TextView) itemView.findViewById(R.id.cart_item_name);
            cartItemSubtitle = (TextView) itemView.findViewById(R.id.cart_item_description);
            cartItemPrice = (TextView) itemView.findViewById(R.id.cart_item_price);

        }
    }
}
