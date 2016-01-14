package com.sergiogomeza.login;


import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Login extends Fragment {
    SQLiteDatabase database;
    private EditText etUser,etPass,etPhone,etEmail;
    private Button bLogin,bReg;
    private ImageView ivgp,ivfb;
    private LoginButton loginButton;
    private TextView tvUser;
    private String name,pass;
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private databaseHandler dbhandler;
    Cursor c;

    public Login() {
        // Required empty public constructor
    }
    private FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
        @Override
        public void onSuccess(LoginResult loginResult) {
            AccessToken accessToken = loginResult.getAccessToken();
            Profile profile = Profile.getCurrentProfile();
            if(profile != null){
                name = profile.getName();
                displayMessage(name);
            }else{
                name = getString(R.string.message5);
                displayMessage(name);
            }
        }

        @Override
        public void onCancel() {  }

        @Override
        public void onError(FacebookException e) {   }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        dbhandler = new databaseHandler(getActivity().getApplicationContext(),"SQLite",null,1);
        database = dbhandler.getWritableDatabase();


        accessTokenTracker= new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldToken, AccessToken newToken) {    }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                if(newProfile != null){
                    name = newProfile.getName();
                    displayMessage(name);
                }
            }
        };

        accessTokenTracker.startTracking();
        profileTracker.startTracking();

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        loginButton = (LoginButton) view.findViewById(R.id.ivfb);
        tvUser = (TextView) getView().findViewById(R.id.tvUser);
        etEmail = (EditText)getView().findViewById(R.id.etEmail);
        etUser = (EditText) getView().findViewById(R.id.etUser);
        etPhone = (EditText)getView().findViewById(R.id.etPhone);
        etPass = (EditText)getView().findViewById(R.id.etPass);
        bLogin = (Button)view.findViewById(R.id.bLogin);
        bReg = (Button)view.findViewById(R.id.bReg);
        ivgp = (ImageView)view.findViewById(R.id.ivgp);

        loginButton.setReadPermissions("user_friends");
        loginButton.setFragment(this);
        loginButton.registerCallback(callbackManager, callback);

    }


    @Override
    public void onStart() {
        super.onStart();
        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //dbhandler.getContactsCount()
                Toast.makeText(getActivity().getApplicationContext(), "Login", Toast.LENGTH_SHORT).show();
                /*Contact contact = new Contact(dbhandler.getContactsCount(),
                        String.valueOf(etUser.getText()),
                        String.valueOf(etEmail.getText()),
                        String.valueOf(etPhone.getText()),
                        String.valueOf(etPass.getText()));
                if (contact.get_name() != null) {
                    if (!contactExists(contact)) {
                        Intent intent = new Intent(getActivity().getApplicationContext(), FacebookInfo.class);
                        intent.putExtra("Username", contact.get_name());
                        intent.putExtra("email", contact.get_email());
                        intent.putExtra("phone", contact.get_phone());
                        startActivity(intent);
                    }
                }
                Toast.makeText(getActivity().getApplicationContext(), getString(R.string.message5), Toast.LENGTH_SHORT).show();*/
                String tabla="Users";
                String[] columna={"name"};
                String[] selectionArgs={etUser.getText().toString(),etPass.getText().toString()};
                String selection="name = ?"+" and "+"password = ?";
                Cursor c=database.query(tabla,columna,selection,selectionArgs,null,null,null);
                String resultado="";
                String n1="";
                String e1="";
                String p1="";

                if (c.moveToFirst()){
                    do {
                        for (int i =0; i<c.getColumnCount();i++){
                            resultado=c.getString(i);
                        }
                    }while (c.moveToNext());
                    //Toast.makeText(getActivity().getApplicationContext(),resultado.toString(),Toast.LENGTH_LONG).show();
                    etPass.setText("");
                    etUser.setText("");
                    etEmail.setText("");
                    etPhone.setText("");
                }else{
                    resultado="Usuario o contraseña invalida";
                    //Toast.makeText(getActivity().getApplicationContext(),resultado.toString(),Toast.LENGTH_LONG).show();
                    etPass.setText("");
                    etUser.setText("");
                    etEmail.setText("");
                    etPhone.setText("");
                }
                n1=resultado;
                String[] col={"email"};
                c = database.query(tabla,col,selection,selectionArgs,null,null,null);
                if (c.moveToFirst()){
                    do {
                        for (int i =0; i<c.getColumnCount();i++){
                            resultado=c.getString(i);
                        }
                    }while (c.moveToNext());
                    //Toast.makeText(getActivity().getApplicationContext(),resultado.toString(),Toast.LENGTH_LONG).show();
                    etPass.setText("");
                    etUser.setText("");
                    etEmail.setText("");
                    etPhone.setText("");
                }else{
                    resultado="Usuario o contraseña invalida";
                    Toast.makeText(getActivity().getApplicationContext(),resultado.toString(),Toast.LENGTH_LONG).show();
                    etPass.setText("");
                    etUser.setText("");
                    etEmail.setText("");
                    etPhone.setText("");
                }
                e1=resultado;
                String[] colu={"phone"};
                c = database.query(tabla,colu,selection,selectionArgs,null,null,null);
                if (c.moveToFirst()){
                    do {
                        for (int i =0; i<c.getColumnCount();i++){
                            resultado=c.getString(i);
                        }
                    }while (c.moveToNext());
                    //Toast.makeText(getActivity().getApplicationContext(),resultado.toString(),Toast.LENGTH_LONG).show();

                }else{
                    resultado="Usuario o contraseña invalida";
                    //Toast.makeText(getActivity().getApplicationContext(),resultado.toString(),Toast.LENGTH_LONG).show();

                }
                p1=resultado;
                Intent intent = new Intent(getActivity().getBaseContext(),FacebookInfo.class);
                intent.putExtra("Username",n1);
                intent.putExtra("email",e1);
                intent.putExtra("phone",p1);
                startActivity(intent);
            }
        });

            bReg.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity().getApplicationContext(), "Registration", Toast.LENGTH_SHORT).show();
                    /*Contact contact = new Contact(dbhandler.getContactsCount(),
                            String.valueOf(etUser.getText()),
                            String.valueOf(etEmail.getText()),
                            String.valueOf(etPhone.getText()),
                            String.valueOf(etPass.getText()));
                    if (contact.get_name() != null) {
                        if (!contactExists(contact)) {
                            if (!contactExists2(contact)) {
                                if (!contactExists3(contact)) {
                                    dbhandler.createContact(contact);
                                    Toast.makeText(getActivity().getApplicationContext(), String.valueOf(etUser.getText()) + getString(R.string.message2), Toast.LENGTH_SHORT).show();
                                    return;
                                }
                            }
                        }
                        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.message1), Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(getActivity().getApplicationContext(), getString(R.string.message5), Toast.LENGTH_SHORT).show();*/
                    String nombre = etUser.getText().toString();
                    String correo = etEmail.getText().toString();
                    String tel = etPhone.getText().toString();
                    String pass = etPass.getText().toString();

                    String tabla = "Users";
                    ContentValues valor = new ContentValues();
                    valor.put("name", nombre);
                    valor.put("email", correo);
                    valor.put("phone", tel);
                    valor.put("password", pass);
                    if (database.insert(tabla, null, valor) == -1) {
                        Toast.makeText(getActivity().getApplicationContext(), getString(R.string.message6), Toast.LENGTH_LONG).show();
                    }
                }
            });

            ivgp.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity().getApplicationContext(), "Google", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity().getApplicationContext(), googleLog.class);
                    startActivity(intent);
                }
            });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onStop() {
        super.onStop();
            accessTokenTracker.stopTracking();
            profileTracker.stopTracking();
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        if(profile != null){
            name = profile.getName();
            displayMessage(name);
        }
    }

    private void displayMessage(String name){
        tvUser.setText(name);
    }
/*
    private boolean contactExists (Contact contact){
        Contact dbcontact;
        String name,dbname;
        int contactCount = c.getColumnCount();
        name = contact.get_name();
        if(contactCount!=0){
            for(int i = 0; i < contactCount; i++){
                dbcontact=dbhandler.getContact(i+0);
                dbname=dbcontact.get_name();
                if(name.compareToIgnoreCase(dbname)==0){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean contactExists2 (Contact contact){
        Contact dbcontact;
        String email,dbemail;
        int contactCount = c.getColumnCount();
        email = contact.get_email();
        if(contactCount!=0) {
            for (int i = 0; i < contactCount; i++) {
                dbcontact = dbhandler.getContact(i + 0);
                dbemail = dbcontact.get_email();
                if (email.compareToIgnoreCase(dbemail) == 0) {
                    return true;
                }
            }
        }
        return false;
    }
    private boolean contactExists3 (Contact contact){
        Contact dbcontact;
        String phone,dbphone;
        int contactCount = c.getColumnCount();
        phone = contact.get_phone();
        if(contactCount!=0) {
            for (int i = 0; i < contactCount; i++) {
                dbcontact = dbhandler.getContact(i + 0);
                dbphone = dbcontact.get_phone();
                if (phone.compareToIgnoreCase(dbphone) == 0) {
                    return true;
                }
            }
        }
        return false;
    }*/
}
