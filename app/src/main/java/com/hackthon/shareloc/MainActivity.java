package com.hackthon.shareloc;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.hackthon.shareloc.Core.ImageResponse;
import com.hackthon.shareloc.Core.JsonObj;
import com.hackthon.shareloc.Core.ServerConnection;
import com.hackthon.shareloc.Core.Upload;
import com.hackthon.shareloc.Core.UploadService;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import im.delight.android.location.SimpleLocation;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    SimpleLocation location;
    TextView text;

    //    Button button;
//
//    public void addListenerOnButton(){
//        final Context context = this;
//        button = (Button)findViewById(R.id.share_button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Intent intent = new Intent(context, MainActivity2.class);
////                startActivity(intent);
//            }
//        });
//    }
    private static class Photo {
        String id;
        String title;
    }


    private static class PhotoVH extends RecyclerView.ViewHolder {
        ImageView photo;
        TextView title;

        public PhotoVH(View itemView) {
            super(itemView);
        }
    }



    private void render(final List<Photo> photos) {
        RecyclerView rv = (RecyclerView) findViewById(R.id.rv_of_photos);
        rv.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.Adapter<PhotoVH> adapter = new RecyclerView.Adapter<PhotoVH>() {
            @Override
            public PhotoVH onCreateViewHolder(ViewGroup parent, int viewType) {
                PhotoVH vh = new PhotoVH(getLayoutInflater().inflate(R.layout.item, null));
                vh.photo = (ImageView) vh.itemView.findViewById(R.id.photo);
                vh.title = (TextView) vh.itemView.findViewById(R.id.title);
                return vh;
            }

            @Override
            public void onBindViewHolder(PhotoVH holder, int position) {
                if (photos.get(position).id.equals("")) {
                    holder.photo.setVisibility(View.GONE);
                } else {
                    holder.photo.setVisibility(View.VISIBLE);
                    Picasso.with(MainActivity.this).load(photos.get(position).id).into(holder.photo);
                }
                if (photos.get(position).title.equals("")) {
                    holder.title.setVisibility(View.GONE);
                } else {
                    holder.title.setVisibility(View.VISIBLE);
                    holder.title.setText(photos.get(position).title);
                }
            }

            @Override
            public int getItemCount() {
                return photos.size();
            }
        };
        rv.setAdapter(adapter);

        rv.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.bottom = 30; // Gap of 16px
            }
        });
    }

    private ArrayList<Photo> fetchData() {
        ArrayList<Photo> photos = new ArrayList<Photo>();
        for (int i = 0; i < 5; i++) {
            Photo photo = new Photo();
            photo.id = "http://i.imgur.com/1JrhixL.jpg";
            photo.title = "cxczxczxczxcz";
            photos.add(photo); // Add photo to list
        }


        return photos;
    }


    /*

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

*/
    //public void onClick2() {
    //}

   public void onClick(View v) {
       Log.d("OnClick", "Pushed Button");
        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
        startActivity(intent);
   }


    private class GetConnection extends AsyncTask<Context, Void, String>
    {
        @Override
        protected String doInBackground(Context[] param) {
            ServerConnection serverConnection = new ServerConnection(param[0]);
            try {
                ArrayList<JsonObj> k = serverConnection.get();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fetchData();

        final ArrayList<Photo> photos = fetchData();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                render(photos);
            }
        });
    }

    private Upload upload;
    public void uploadImage(File chosenFile) {
    /*
      Create the @Upload object
     */
        if (chosenFile == null) return;
        createUpload(chosenFile);

    /*
      Start upload
     */
        new UploadService(this).Execute(upload, new UiCallback());
    }

     private void createUpload(File image) {
        upload = new Upload();

        upload.image = image;
        upload.title = "test";
        upload.description = "test";
    }

    private class UiCallback implements Callback<ImageResponse> {

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

}
