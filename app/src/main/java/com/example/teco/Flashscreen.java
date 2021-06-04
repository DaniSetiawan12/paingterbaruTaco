package com.example.teco;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import User.MenuLogin;

public class Flashscreen extends AppCompatActivity {
    SharedPreferences sp;
    public static final String USER_PREF = "USER_PREF" ;
    public static final String login = "login";
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.flashscreen);
            getSupportActionBar().hide();
            sp = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);
            Thread timerThread = new Thread() {
                public void run() {
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } finally {
                        if (sp.contains(login)) {
                            String cek = sp.getString(login, "");
                            Log.e("Login = ", cek);
                            if(cek.equals("1")){
                                Intent intent = new Intent(Flashscreen.this, KonfirmasiSandi.class);
                                startActivity(intent);
                            }else {
                                Intent intent = new Intent(Flashscreen.this, MenuLogin.class);
                                startActivity(intent);
                            }
                        }else{
                            Intent intent = new Intent(Flashscreen.this, MenuLogin.class);
                            startActivity(intent);
                        }

                    }
                }
            };
            timerThread.start();
        }
        @Override
        protected void onPause() {
            super.onPause();
            finish();
        }
    }
