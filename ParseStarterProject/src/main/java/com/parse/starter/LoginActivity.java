package com.parse.starter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity implements View.OnKeyListener{

    EditText emailEditTextLogin;
    EditText passwordEditTextLogin;

    @Override
    public boolean onKey(View view, int i, KeyEvent keyevent){
        /*if(i == KeyEvent.KEYCODE_ENTER && keyevent.getAction() == KeyEvent.ACTION_DOWN){
            clickSignIn(view);
        }*/
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailEditTextLogin = findViewById(R.id.emailEditTextLogin);
        passwordEditTextLogin = findViewById(R.id.passwordEditTextLogin);
        passwordEditTextLogin.setOnKeyListener(this);
    }

    public void showProfile(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void clickForgot(View view) {
        Intent intent = new Intent(this, PasswordActivity.class);
        startActivity(intent);
    }

    public void clickSignIn(View view ) {

        if (emailEditTextLogin.getText().toString().matches("") || passwordEditTextLogin.getText().toString().matches("")) {
            Toast.makeText(this, "Email and password both are required.", Toast.LENGTH_SHORT).show();
        } else{
            ParseUser.logInInBackground(emailEditTextLogin.getText().toString(), passwordEditTextLogin.getText().toString(), new LogInCallback() {
                @Override
                public void done(ParseUser user, ParseException e) {

                    if (user != null) {

                        Log.i("Signup", "Login successful");
                        Toast.makeText(LoginActivity.this, "Login Successful!", Toast.LENGTH_SHORT).show();
                        showProfile();

                    } else {

                        Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }

                }
            });
        }


    }
}