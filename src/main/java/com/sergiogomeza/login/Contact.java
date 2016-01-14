package com.sergiogomeza.login;

/**
 * Created by Sergio on 12/01/2016.
 */
public class Contact {
    private int id;
    private String _name,_email,_phone,_pass;

    public Contact(int id, String _name, String _email, String _phone, String _pass) {
        this.id = id;
        this._name = _name;
        this._email = _email;
        this._phone = _phone;
        this._pass = _pass;
    }

    public int getId() {
        return id;
    }

    public String get_name() {
        return _name;
    }

    public String get_email() {
        return _email;
    }

    public String get_phone() { return _phone; }

    public String get_pass() {
        return _pass;
    }
}
