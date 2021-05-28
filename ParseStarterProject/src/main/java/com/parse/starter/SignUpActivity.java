package com.parse.starter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;

public class SignUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
    }

    public void showProfile(){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    public void clickLogUp(View view) {
        EditText emailEditTextSignup = findViewById(R.id.emailEditTextSignup);
        EditText passwordEditTextSignup = findViewById(R.id.passwordEditTextSignup);
        EditText idEditTextNumberSignup = findViewById(R.id.idEditTextNumberSignup);
        EditText nameEditTextSignup = findViewById(R.id.nameEditTextSignup);

        if (emailEditTextSignup.getText().toString().matches("") // checking whether all fields are filled.
            || passwordEditTextSignup.getText().toString().matches("")
            || idEditTextNumberSignup.getText().toString().matches("")
            || nameEditTextSignup.getText().toString().matches("")){
            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
        } else{
            ParseUser user = new ParseUser();
            user.setEmail(emailEditTextSignup.getText().toString());
            user.setUsername(emailEditTextSignup.getText().toString());
            user.setPassword(passwordEditTextSignup.getText().toString());
            user.put("name", nameEditTextSignup.getText().toString());
            user.put("studentId", Integer.parseInt(idEditTextNumberSignup.getText().toString()));
            user.put("bio","");
            user.put("currentLocation","");
            user.put("linkedIn","");


            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    if (e == null) {

                        Log.i("Signup", "Successful");
                        Toast.makeText(SignUpActivity.this, "Sign-up Successful!", Toast.LENGTH_SHORT).show();
                        showProfile();

                    } else {

                        Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}