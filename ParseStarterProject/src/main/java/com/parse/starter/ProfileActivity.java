package com.parse.starter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    EditText bioEditTextProfile;
    EditText locationEditTextProfile;
    EditText linkedInEditTextProfile;
    EditText courseEditTextProfile;
    EditText unitsEditTextProfile;
    TextView headerTextViewProfile;
    boolean update=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        setTitle("User Profile");

        bioEditTextProfile = findViewById(R.id.bioEditTextProfile);
        locationEditTextProfile = findViewById(R.id.locationEditTextProfile);
        linkedInEditTextProfile = findViewById(R.id.linkedInEditTextProfile);
        courseEditTextProfile = findViewById(R.id.courseEditTextProfile);
        unitsEditTextProfile = findViewById(R.id.unitsEditTextProfile);
        headerTextViewProfile = findViewById(R.id.headerTextViewProfile);
        getData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_tab, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.Profile)
        {
            Toast.makeText(ProfileActivity.this, "Already on Profile Page.", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.Search)
        {
            //startActivity(new Intent(this,SearchActivity.class));
            Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
            startActivity(intent);
        }
        if(id == R.id.Chat)
        {
            Toast.makeText(ProfileActivity.this, "Select a user from Search menu.", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this,MessagingActivity.class));
        }

        if(id == R.id.Logout)
        {
            ParseUser.logOut();
            Toast.makeText(ProfileActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }

    public void getData(){
        if (ParseUser.getCurrentUser() != null)
        {
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
            query.findInBackground(new FindCallback<ParseUser>() {
                @Override
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {
                        if(objects.size() > 0){
                            for (ParseUser singleobject : objects) {
                                if(update)
                                {
                                    singleobject.put("bio",bioEditTextProfile.getText().toString());
                                    singleobject.put("currentLocation",locationEditTextProfile.getText().toString());
                                    singleobject.put("linkedIn",linkedInEditTextProfile.getText().toString());
                                    singleobject.put("courseCode",courseEditTextProfile.getText().toString());
                                    singleobject.put("unitCode",unitsEditTextProfile.getText().toString());
                                    singleobject.saveInBackground();
                                    setData(bioEditTextProfile.getText().toString(), locationEditTextProfile.getText().toString(), linkedInEditTextProfile.getText().toString(), courseEditTextProfile.getText().toString(), unitsEditTextProfile.getText().toString(),findViewById(R.id.headerTextViewProfile).toString());
                                    Toast.makeText(ProfileActivity.this, "Profile Updated.", Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    String getName = singleobject.get("name").toString();
                                    String getBio = singleobject.get("bio").toString();
                                    String getCurrentLocation = singleobject.get("currentLocation").toString();
                                    String getLinkedIn = singleobject.get("linkedIn").toString();
                                    String getCourse = singleobject.get("courseCode").toString();
                                    String getUnits = singleobject.get("unitCode").toString();
                                    setData(getBio, getCurrentLocation, getLinkedIn, getCourse, getUnits,getName);
                                    Toast.makeText(ProfileActivity.this, "Profile Data loaded.", Toast.LENGTH_SHORT).show();
                                }
                                update=false;
                            }
                        }
                    }
                }
            });
        }else {
            Toast.makeText(ProfileActivity.this, "Profile Data not found.", Toast.LENGTH_SHORT).show();
        }
    }

    public void setData (String bio,String location,String linkedin,String course,String units,String name){
        bioEditTextProfile.setText(bio);
        locationEditTextProfile.setText(location);
        linkedInEditTextProfile.setText(linkedin);
        courseEditTextProfile.setText(course);
        unitsEditTextProfile.setText(units);
        headerTextViewProfile.setText("WELCOME "+name);
    }

    public void clickUpdate(View view ) {
        update = true;
        getData();
    }
}