package Pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.teco.R;

import User.MenuLogin;

public class Menu_Pengguna extends AppCompatActivity {

        private Button buttonpin,buttonger,buttontet,buttonhis ;
        EditText edtlogout;
        SharedPreferences sp;
        public static final String USER_PREF = "USER_PREF" ;
        public static final String login = "login";
        LinearLayout linearLogout;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.menu_pengguna);
            getSupportActionBar().hide();

            buttonpin = (Button)findViewById(R.id.btnPintu);
            buttonger = (Button)findViewById(R.id.btnGerbang);
            buttontet = (Button)findViewById(R.id.btnTentang);
            buttonhis = (Button)findViewById(R.id.btnHistory);
            linearLogout = (LinearLayout) findViewById(R.id.linearLogout);

            sp = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);

            buttonpin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Menu_Pengguna.this, Menu_Pintu.class);
                    startActivity(i);
                    finish();
                }
            });
            buttonger.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Menu_Pengguna.this, Menu_Gerbang.class);
                    startActivity(i);
                    finish();
                }
            });
            buttontet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Menu_Pengguna.this, Tentang.class);
                    startActivity(i);
                    finish();
                }
            });
            buttonhis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(Menu_Pengguna.this, History.class);
                    startActivity(i);
                    finish();
                }
            });

            linearLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(Menu_Pengguna.this, MenuLogin.class);
                    startActivity(i);
                    finish();
                    SharedPreferences.Editor editor = sp.edit();
                    editor.putString(login, "0");
                    editor.commit();
                }
            });
        }

    }