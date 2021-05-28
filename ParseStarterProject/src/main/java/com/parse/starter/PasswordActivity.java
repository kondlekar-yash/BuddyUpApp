package com.parse.starter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class PasswordActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
    }

    public void clickLogin(View view) {
        EditText emailEditTextPassword = findViewById(R.id.emailEditTextPassword);
        EditText idEditTextNumberPassword = findViewById(R.id.idEditTextNumberPassword);
        final String id = idEditTextNumberPassword.getText().toString();
        EditText passwordEditTextPassword = findViewById(R.id.passwordEditTextPassword);
        final String pass = passwordEditTextPassword.getText().toString();
        EditText codeEditTextNumberPassword = findViewById(R.id.codeEditTextNumberPassword);

        if (emailEditTextPassword.getText().toString().matches("") // checking whether all fields are filled.
                || passwordEditTextPassword.getText().toString().matches("")
                || idEditTextNumberPassword.getText().toString().matches("")
                || codeEditTextNumberPassword.getText().toString().matches("")) {
            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
        }
        else {
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("username", emailEditTextPassword.getText().toString());
            query.findInBackground(new FindCallback<ParseUser>() {
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {
                        if(objects.size() > 0){
                            for (ParseUser singleobject : objects) {
                                String sId = singleobject.get("studentId").toString();
                                if(sId.equals(id))
                                {
                                    singleobject.setPassword(pass);
                                    singleobject.saveInBackground();
                                    Toast.makeText(PasswordActivity.this, "Password changed Successfully.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    } else {
                        Toast.makeText(PasswordActivity.this, "Password change Unsuccessful.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        /*Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);*/
    }
}