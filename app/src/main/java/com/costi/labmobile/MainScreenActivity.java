package com.costi.labmobile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.costi.labmobile.cart.ShoppingCartActivity;
import com.costi.labmobile.pizzalist.PizzaListActivity;

public class MainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);

        Button btnGoToProducts = (Button) findViewById(R.id.btn_go_to_products);
        btnGoToProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pizzaListIntent = new Intent(MainScreenActivity.this,PizzaListActivity.class);
                startActivity(pizzaListIntent);
            }
        });

        Button btnGoToCart = (Button) findViewById(R.id.btn_go_to_cart);
        btnGoToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shoppingCartIntent = new Intent(MainScreenActivity.this,ShoppingCartActivity.class);
                startActivity(shoppingCartIntent);
            }
        });


        Button btnGoToAbout = (Button) findViewById(R.id.btn_go_to_about);
        btnGoToAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aboutIntent = new Intent(MainScreenActivity.this,AboutActivity.class);
                startActivity(aboutIntent);
            }
        });




    }
}
