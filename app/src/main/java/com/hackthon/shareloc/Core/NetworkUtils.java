package com.hackthon.shareloc.Core;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;


public class NetworkUtils {
      public static final String TAG = NetworkUtils.class.getSimpleName();

  public static boolean isConnected(Context mContext) {
  try {
    ConnectivityManager connectivityManager =
        (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
    if (connectivityManager != null) {
      NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
      return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
  }catch (Exception ex){
    Log.d(TAG, ex.getMessage());
  }
    return false;
  }

  public static boolean connectionReachable() {
    Socket socket = null;
    boolean reachable = false;
    try {
      socket = new Socket("google.com", 80);
      reachable = socket.isConnected();
    } catch (UnknownHostException e) {
      Log.d(TAG, "Error connecting to server");
      reachable = false;
    } catch (IOException e) {
      Log.d(TAG, "Error connecting to server");
    } finally {
      if (socket != null) {
        try {
          socket.close();
        } catch (IOException e) {
          Log.d(TAG, "Error closing connecting socket test");
        }
      }
    }
    Log.d(TAG, "Data connectivity change detected, ping test=" + String.valueOf(reachable));
    return reachable;
  }

}
