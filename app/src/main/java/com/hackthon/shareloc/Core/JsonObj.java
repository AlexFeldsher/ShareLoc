package com.hackthon.shareloc.Core;

/**
 * Created by Naomi on 5/19/2017.
 */

public class JsonObj {
    public String _gpslong;
    public String _gpslat;
    public String _image;
    public String _text;
    public JsonObj(String gpslat, String gpslong, String image, String text ){
        _gpslat = gpslat;
        _gpslong = gpslong;
        _image = image;
        _text = text;
    }
}
