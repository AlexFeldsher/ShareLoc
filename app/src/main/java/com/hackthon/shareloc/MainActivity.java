package com.hackthon.shareloc;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;


import com.hackthon.shareloc.Core.ServerConnection;

import im.delight.android.location.SimpleLocation;

public class MainActivity extends AppCompatActivity {

    SimpleLocation location;
    TextView text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("create", "creating app");
        setContentView(R.layout.activity_main);

        GetConnection getConnection = new GetConnection();
        getConnection.execute(this.getApplicationContext());
        Log.d("get", "got here");

        PostConnection postConnection = new PostConnection();
        postConnection.execute(this.getApplicationContext());
        Log.d("post", "got here");
    }

    //public void onClick2() {
    //}

    private class GetConnection extends AsyncTask<Context, Void, String>
    {
        @Override
        protected String doInBackground(Context[] param) {
            ServerConnection serverConnection = new ServerConnection(param[0]);
            try {
                String k = serverConnection.get();
            } catch (Exception e) {
                Log.d("get", e.toString());
            }
            return "";
        }
    };

        private class PostConnection extends AsyncTask<Context, Void, String>
    {
        @Override
        protected String doInBackground(Context[] param) {
            ServerConnection serverConnection = new ServerConnection(param[0]);
            try {
                int k = serverConnection.post("text", "image");
            } catch (Exception e) {
                Log.d("get", e.toString());
            }
            return "";
        }
    };

}
