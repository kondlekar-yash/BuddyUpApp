package com.parse.starter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

public class MessagingActivity extends AppCompatActivity {

    String activeUser = "";
    String currentName = "";

    ArrayList<String> messages = new ArrayList<>();
    ArrayAdapter arrayAdapter;

    public void clickSend(View view)
    {
        final EditText msgEditTextMsg = (EditText) findViewById(R.id.msgEditTextMsg);
        ParseObject message = new ParseObject("Message");
        message.put("sender", currentName);
        message.put("recipient", activeUser);
        message.put("text", msgEditTextMsg.getText().toString());

        message.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null)
                {
                    Toast.makeText(MessagingActivity.this, "Text sent!", Toast.LENGTH_SHORT).show();
                    messages.add(msgEditTextMsg.getText().toString());
                    arrayAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void setName(String s)
    {
        currentName = s;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messaging);

        Intent intent = getIntent();
        activeUser = intent.getStringExtra("username");
        setTitle("Messaging " + activeUser);

        ListView msgsListViewMsg = (ListView) findViewById(R.id.msgsListViewMsg);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, messages);
        msgsListViewMsg.setAdapter(arrayAdapter);
        ParseQuery<ParseObject> query1 = new ParseQuery<ParseObject>("Message");
        query1.whereEqualTo("sender", currentName);
        query1.whereEqualTo("recipient", activeUser);

        ParseQuery<ParseObject> query2 = new ParseQuery<ParseObject>("Message");
        query2.whereEqualTo("recipient", currentName);
        query2.whereEqualTo("sender", activeUser);

        List<ParseQuery<ParseObject>> mainQuery = new ArrayList<ParseQuery<ParseObject>>();
        mainQuery.add(query1);
        mainQuery.add(query2);

        ParseQuery<ParseObject> query = ParseQuery.or(mainQuery);
        query.orderByAscending("createdAt");
        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (e == null)
                {
                    if (objects.size() > 0){
                        messages.clear();
                        for(ParseObject message : objects)
                        {
                            String msgContent = message.getString("text");
                            if (!message.getString("sender").equals(currentName)) {
                                msgContent = ">> " + msgContent;
                            }
                            messages.add(msgContent);
                        }
                        arrayAdapter.notifyDataSetChanged();
                    } else{
                        Toast.makeText(MessagingActivity.this, "no chat found", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MessagingActivity.this, "error", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ParseQuery<ParseUser> query0 = ParseUser.getQuery();
        query0.whereEqualTo("username", ParseUser.getCurrentUser().getUsername());
        query0.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> objects, ParseException e) {
                if (e == null) {
                    if (objects.size() > 0)
                    {
                        for (ParseUser user : objects)
                        {
                            String currentName = user.get("name").toString();
                            setName(currentName);
                        }
                    }
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
            startActivity(new Intent(this,SearchActivity.class));
        }
        if(id == R.id.Chat)
        {
            Toast.makeText(MessagingActivity.this, "Already on Chat Page.", Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.Logout)
        {
            ParseUser.logOut();
            Toast.makeText(MessagingActivity.this, "Signed out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this,MainActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}