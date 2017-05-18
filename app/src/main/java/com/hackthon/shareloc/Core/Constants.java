package com.hackthon.shareloc.Core;

/**
 * Created by alex on 5/19/17.
 */

public class Constants {
        /*
      Logging flag
     */
    public static final boolean LOGGING = false;

    /*
      Your imgur client id. You need this to upload to imgur.

      More here: https://api.imgur.com/
     */
    public static final String MY_IMGUR_CLIENT_ID = "786d9af08e546da";
    public static final String MY_IMGUR_CLIENT_SECRET = "e619db1953ec53749fecfdeff0d64a3bd6924232";

    /*
      Redirect URL for android.
     */
    public static final String MY_IMGUR_REDIRECT_URL = "http://android";

    /*
      Client Auth
     */
    public static String getClientAuth() {
        return "Client-ID " + MY_IMGUR_CLIENT_ID;
    }
}
