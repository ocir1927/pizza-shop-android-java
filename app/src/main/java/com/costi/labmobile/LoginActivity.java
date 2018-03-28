package com.costi.labmobile;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.costi.labmobile.model.User;
import com.costi.labmobile.retrofit.RetrofitClient;
import com.costi.labmobile.retrofit.RetrofitSingleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.HTTP;

import static com.costi.labmobile.pizzalist.PizzaListAdapter.SHOPPING_CART;

public class LoginActivity extends AppCompatActivity {
    public static final String USERNAME = "USERNAME";

    Button btnLogin;
    EditText emailEditText;
    EditText passwordEditText;
    Retrofit retrofit;
    SharedPreferences sharedPreferences;
    ImageView pizzaIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        pizzaIcon = (ImageView) findViewById(R.id.pizza_icon);

        sharedPreferences = getSharedPreferences(SHOPPING_CART, this.MODE_PRIVATE);

        if (sharedPreferences.getString(USERNAME, null) != null) {
            Intent alreadyLoggedIntent = new Intent(this, MainScreenActivity.class);
            startActivity(alreadyLoggedIntent);
        }

        retrofit = RetrofitSingleton.getInstance();
        btnLogin = (Button) findViewById(R.id.login_btn);
        emailEditText = (EditText) findViewById(R.id.email);
        passwordEditText = (EditText) findViewById(R.id.password);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        animateIcon();

    }



    void animateIcon() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(pizzaIcon, "x", -1000f,250f);
        animator.setDuration(1000);
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(animator);
        animatorSet.start();
    }

    private void login() {
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        final User mockUser = new User("ana", "dada");
        User user = new User(email, password);

        RetrofitClient client = retrofit.create(RetrofitClient.class);
        Call<ResponseBody> call = client.login(mockUser);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    saveUserName(mockUser.getUsername());
                    Intent i = new Intent(LoginActivity.this, MainScreenActivity.class);
                    i.putExtra("text", "hello from first activity");
                    startActivity(i);
                } else if (response.code() == 404) {
                    Toast.makeText(LoginActivity.this, "Wrong Credentials", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void saveUserName(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME, username);

        editor.apply();

    }

}
