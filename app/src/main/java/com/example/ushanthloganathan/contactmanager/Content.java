package com.example.ushanthloganathan.contactmanager;

/**
 * Created by ushanthloganathan on 2016-06-13.
 */
public class Content {
    private String _name, _phone, _email, _address;

    public Content (String name, String phone, String email, String address){
        _name = name;
        _phone=phone;
        _email=email;
        _address=address;
    }

    public String getName(){
        return _name;
    }

    public String get_phone(){
        return _phone;
    }

    public String getMail(){
        return _email;
    }

    public String getAddress(){
        return _address;
    }

}
