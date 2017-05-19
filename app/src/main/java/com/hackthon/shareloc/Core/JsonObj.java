package com.hackthon.shareloc.Core;

public class JsonObj {
    public String _gpslong;
    public String _gpslat;
    public String _image;
    public String _text;
    public JsonObj(String gpslat, String gpslong, String text, String image ){
        _gpslat = gpslat;
        _gpslong = gpslong;
        _image = image;
        _text = text;
    }
}
