package com.hackthon.shareloc;


import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

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
        RecyclerView rv = (RecyclerView)findViewById(R.id.rv_of_photos);
        rv.setLayoutManager(new LinearLayoutManager(this));

        RecyclerView.Adapter<PhotoVH> adapter = new RecyclerView.Adapter<PhotoVH>() {
            @Override
            public PhotoVH onCreateViewHolder(ViewGroup parent, int viewType) {
                PhotoVH vh = new PhotoVH(getLayoutInflater().inflate(R.layout.item, null));
                if(vh.photo.equals(""))
                {
                    vh.photo.setVisibility(View.INVISIBLE);
                }
                else
                {
                    vh.photo = (ImageView) vh.itemView.findViewById(R.id.photo);
                }
                if(vh.title.equals(""))
                {
                    vh.title.setVisibility(View.INVISIBLE);
                }
                else
                {
                    vh.title = (TextView) vh.itemView.findViewById(R.id.title);
                }
                return vh;
            }

            @Override
            public void onBindViewHolder(PhotoVH holder, int position) {
                Picasso.with(MainActivity.this).load(photos.get(position).id).into(holder.photo);
                holder.title.setText(photos.get(position).title);
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
                outRect.bottom = 16; // Gap of 16px
            }
        });

    }

    private ArrayList<Photo> fetchData() {
        ArrayList<Photo> photos = new ArrayList<Photo>();
        for (int i = 0; i < 5; i++) {
            Photo photo = new Photo();
            photo.id = "http://i.imgur.com/1JrhixL.jpg";
            photo.title = "this is an example";
            photos.add(photo); // Add photo to list
        }


        return photos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Photo> photos = fetchData();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                render(photos);
            }
        });
    }
}