package com.parse.starter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    ArrayList<String> users = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Intent intent = getIntent();

        setTitle("Search Users");

        ListView usersListView = (ListView) findViewById(R.id.userListViewSearch);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,users);
        usersListView.setAdapter(arrayAdapter);
        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), MessagingActivity.class);
                intent.putExtra("username", users.get(position));
                startActivity(intent);
            }
        });

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.whereNotEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0)
                    {
                        for (ParseUser user : objects)
                        {
                            users.add(user.get("name").toString());
                        }
                        arrayAdapter.notifyDataSetChanged();
                    }
                } else{
                    Toast.makeText(SearchActivity.this, "Users not found.", Toast.LENGTH_SHORT).show();
                }
            }
        });
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
            startActivity(new Intent(this,ProfileActivity.class));
        }
        if(id == R.id.Search)
        {
            Toast.makeText(SearchActivity.this, "Already on Search Page.", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.Chat)
        {
            Toast.makeText(SearchActivity.this, "Select a user from list.", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(this,MessagingActivity.class));
        }
        if(id == R.id.Logout)
        {
            ParseUser.logOut();
            Toast.makeText(SearchActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));
        }

        return super.onOptionsItemSelected(item);
    }
}