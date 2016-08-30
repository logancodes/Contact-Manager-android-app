package com.example.ushanthloganathan.contactmanager;

import android.net.Uri;


/**
 * Created by ushanthloganathan on 2016-06-13.
 */
public class Content {
    private String _name, _phone, _email, _address;
    private Uri _imageuri;
    private int _id;

    public Content (int id,String name, String phone, String email, String address, Uri imageuri){
        _id= id;
        _name = name;
        _phone=phone;
        _email=email;
        _address=address;
        _imageuri=imageuri;


    }

    public String getName(){
        return _name;
    }
    public int getId(){return _id;};

    public String get_phone(){
        return _phone;
    }

    public String getMail(){
        return _email;
    }

    public String getAddress(){
        return _address;
    }

    public Uri getImageURI(){return _imageuri;}

}
