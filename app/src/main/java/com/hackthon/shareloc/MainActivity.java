package com.hackthon.shareloc;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

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
                vh.photo = (ImageView) vh.itemView.findViewById(R.id.photo);
                vh.title = (TextView) vh.itemView.findViewById(R.id.title);
                return vh;
            }

            @Override
            public void onBindViewHolder(PhotoVH holder, int position) {
                if(photos.get(position).id.equals(""))
                {
                    holder.photo.setVisibility(View.GONE);
                }
                else
                {
                    Picasso.with(MainActivity.this).load(photos.get(position).id).into(holder.photo);
                }
                if(photos.get(position).title.equals(""))
                {
                    holder.title.setVisibility(View.GONE);
                }
                else
                {
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
                outRect.bottom = 16; // Gap of 16px
            }
        });

    }

    private ArrayList<Photo> fetchData() {
        ArrayList<Photo> photos = new ArrayList<Photo>();
        for (int i = 0; i < 5; i++) {
            Photo photo = new Photo();
            photo.id = "";
            photo.title = "this is a long text that I need to check if and how it looks" +
                    " in th fucking app";
            photos.add(photo); // Add photo to list
        }


        return photos;
    }

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
}