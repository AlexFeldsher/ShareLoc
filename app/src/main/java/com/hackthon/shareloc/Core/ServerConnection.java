package com.hackthon.shareloc.Core;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import im.delight.android.location.SimpleLocation;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ServerConnection {


    private URL url;
    private HttpURLConnection connection;
    private DataOutputStream wr;
    private Context context;
    private SimpleLocation location;

    public ServerConnection(Context context)
    {
        this.context = context;
    }

    private ArrayList<String> getGPS()
    {
        ArrayList<String> coords = new ArrayList<>();
        location = new SimpleLocation(context);
        if (!location.hasLocationEnabled()) {
            // ask the user to enable location access
            SimpleLocation.openSettings(context);
        }

        coords.add(Double.toString(location.getLatitude()));
        coords.add(Double.toString(location.getLongitude()));
        return coords;
    }

    public int post(String text, String imagePath)
    {
        ArrayList<String> coords = getGPS();
        // TODO: parameters
        String body = String.format("{\"gpslat\": \"%s\", \"gpslong\": \"%s\", \"text\": \"%s\", \"image\": \"%s\"}", coords.get(0), coords.get(1), text, "imagelink");
        try {

            url = new URL("http://ec2-52-56-159-238.eu-west-2.compute.amazonaws.com/addPost");
        } catch (Exception e) {

        }
        Log.d("json", body);
        OkHttpClient client = new OkHttpClient();
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");
        okhttp3.RequestBody body2 = RequestBody.create(JSON, body);
        okhttp3.Request request = new okhttp3.Request.Builder()
                    .url(url)
                    .post(body2)
                    .build();

        try {
            okhttp3.Response response = client.newCall(request).execute();
        } catch (Exception e)
        {

        }

        return 0;
    }


    public String get() throws IOException {

        ArrayList<String> coords = getGPS();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://ec2-52-56-159-238.eu-west-2.compute.amazonaws.com/?lat="
                    + coords.get(0) + "&long=" + coords.get(1)).build();
        Response response2 = client.newCall(request).execute();
        Log.d("res", response2.body().string());
        return response2.body().string();
    }
}
