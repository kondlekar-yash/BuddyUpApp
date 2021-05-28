/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseAnalytics;
import com.parse.ParseUser;


public class MainActivity extends AppCompatActivity {
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ParseAnalytics.trackAppOpenedInBackground(getIntent());

    if (ParseUser.getCurrentUser() != null)
    {
      Toast.makeText(MainActivity.this, ParseUser.getCurrentUser().getUsername()+" logged in!", Toast.LENGTH_SHORT).show();
      showProfile();
    }else{
      Toast.makeText(MainActivity.this,"Not logged in!", Toast.LENGTH_SHORT).show();
    }
  }

  public void showProfile(){
    Intent intent = new Intent(this, ProfileActivity.class);
    startActivity(intent);
  }

  public void clickLogin(View view ) {
    Intent intent = new Intent(this, LoginActivity.class);
    startActivity(intent);
  }

  public void clickSignUp(View view ) {
    Intent intent = new Intent(this, SignUpActivity.class);
    startActivity(intent);
  }
}