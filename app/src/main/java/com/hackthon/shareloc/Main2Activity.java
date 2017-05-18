package com.hackthon.shareloc;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.hackthon.shareloc.Core.ServerConnection;


public class Main2Activity extends AppCompatActivity {
    private static final int SELECTED_IMAGE = 1;
    private static final String TAG = "Main2Activity";
    ImageView imgView = null;
    String str = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        imgView = (ImageView) findViewById(R.id.imageView);
        EditText text = (EditText) findViewById(R.id.editText);
        str = text.getText().toString();

        //GetConnection getConnection = new GetConnection();
        //getConnection.execute(this.getApplicationContext());
        //Log.d("get", "got here");
        //PostConnection postConnection = new PostConnection();
        //postConnection.execute(this.getApplicationContext());
        //Log.d("post", "got here");
    }

    /**
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
     */

    public void shareButten(View v) {
        String msg;
        Intent intent = new Intent(Main2Activity.this, MainActivity.class);
        int res = 0;
        if (imgView == null && str == "")
        {
            msg = "You should add photo/text before you share";
            Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            startActivity(intent);
        }

        // call to alex function and send imgView + str
        res++;
        if(res !=0){
            //return value from alex
        }
        else
        {
            msg = "Problems with the server. Try again later.";
            Toast.makeText(this.getApplicationContext(), msg, Toast.LENGTH_LONG).show();
        }
        startActivity(intent); // get into mainActivity.

    }

    public void imageUpload(View v) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECTED_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == SELECTED_IMAGE) {
            Uri selectedImage = data.getData(); // Get the url from data
            if (selectedImage != null) {
                String imagePath = getPath(selectedImage);
                Log.i(TAG, "Image Path : " + imagePath);
                imgView.setImageURI(selectedImage); // Set the image in ImageView
            }
        }
    }

    public String getPath(Uri selectedImage)
    {
        String imagePath = null ;
        String[] path = {MediaStore.Images.Media.DATA}; // Get the path from the Uri
        Cursor cursor = getContentResolver().query(selectedImage, path, null, null, null);
        if (cursor.moveToFirst()) {
            int colIndex = cursor.getColumnIndex(path[0]);
            imagePath = cursor.getString(colIndex);
            cursor.close();
        }
        return imagePath;
    }
}

