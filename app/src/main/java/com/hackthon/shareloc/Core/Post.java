package com.hackthon.shareloc.Core;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.File;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class Post {

    private String msg;
    private String imgLink;
    private File imgFile;
    private Upload upload;
    private ImageResponse imageResponse;


    private RestAdapter buildRestAdapter() {
        RestAdapter imgurAdapter = new RestAdapter.Builder()
                .setEndpoint(ImgurAPI.server)
                .build();

        /*
        Set rest adapter logging if we're already logging
        */
        if (Constants.LOGGING)
            imgurAdapter.setLogLevel(RestAdapter.LogLevel.FULL);
        return imgurAdapter;
    }

    public class Call implements Callback<ImageResponse> {
        @Override
        public void success(ImageResponse imageResponse, Response response) {
            if (response == null) {
                /*
                 Notify image was NOT uploaded successfully
                */
                return;
            }
            Log.d("IMGUR", imageResponse.data.link);
            imgLink = imageResponse.data.link;
            /*
            Notify image was uploaded successfully
            */
        }

        @Override
        public void failure(RetrofitError error) {
            /** failed upload */
        }
    }

    public int post(String text, String imgPath, Context context)
    {
        msg = text;

        imgFile = new File(imgPath);

        if (imgPath == null)
        {
            imgLink = "";
        } else {
            createUpload(imgFile);
            RestAdapter restAdapter = buildRestAdapter();

        restAdapter.create(ImgurAPI.class).postImage(
            Constants.getClientAuth(),
            upload.title,
            upload.description,
            upload.albumId,
            null,
            new TypedFile("image/*", upload.image),
            new Call());
        }

        PostConnection postConnection = new PostConnection();
        postConnection.execute(context);

        // TODO
        return 0;
    }

    private class PostConnection extends AsyncTask<Context, Void, String>
    {
        @Override
        protected String doInBackground(Context[] param) {
            ServerConnection serverConnection = new ServerConnection(param[0]);
            try {
                int k = serverConnection.post(msg, imgLink);
            } catch (Exception e) {
                Log.d("get", e.toString());
            }
            return "";
        }
    };

    private class UploadCallback implements Callback<ImageResponse> {

        @Override
        public void success(ImageResponse imageResponse, Response response) {
            // TODO
        }

        @Override
        public void failure(RetrofitError error) {
            //Assume we have no connection, since error is null
            if (error == null) {
                Log.d("fail", "no connection");
            }
        }
    }

    private void createUpload(File image) {
        upload = new Upload();

        upload.image = image;
        upload.title = "test";
        upload.description = "test";
    }
}

