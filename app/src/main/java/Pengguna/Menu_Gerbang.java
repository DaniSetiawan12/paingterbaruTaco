package Pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.example.teco.R;

import controlling.Controlling;

public class Menu_Gerbang extends AppCompatActivity {

    LinearLayout linearBukaGerbang;
    LinearLayout linearJedaBukaGerbang;
    LinearLayout linearTutupGerbang;
    LinearLayout linearJedaTutupGerbang;
    RequestQueue requestQueue;

    SharedPreferences sp;
    public static final String USER_PREF = "USER_PREF" ;
    public static final String userName = "userName";
    public static final String ID = "_id";
    public static final String NamaLengkap = "namaLengkap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.menu__gerbang);

        sp = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);

        linearBukaGerbang = (LinearLayout) findViewById(R.id.linearBukaGerbang);
        linearJedaBukaGerbang = (LinearLayout) findViewById(R.id.linearJedaBukaGerbang);
        linearTutupGerbang = (LinearLayout) findViewById(R.id.linearTutupGerbang);
        linearJedaTutupGerbang = (LinearLayout) findViewById(R.id.linearJedaTutupGerbang);

        String idUser = sp.getString(ID, "");

        linearBukaGerbang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controlling controlling = new Controlling();
                controlling.controll(requestQueue, Menu_Gerbang.this, "Buka Gerbang", idUser, "Berhasil Membuka Gerbang");
            }
        });

        linearJedaBukaGerbang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controlling controlling = new Controlling();
                controlling.controll(requestQueue, Menu_Gerbang.this, "Jeda Buka Gerbang", idUser,"Gerbang Buka Terjeda");
            }
        });
        linearTutupGerbang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controlling controlling = new Controlling();
                controlling.controll(requestQueue, Menu_Gerbang.this, "Tutup Gerbang", idUser,"Berhasil Menutup Pintu");
            }
        });
        linearJedaTutupGerbang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controlling controlling = new Controlling();
                controlling.controll(requestQueue, Menu_Gerbang.this, "Jeda Tutup Gerbang", idUser,"Gerbang Tutup Terjeda");
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(Menu_Gerbang.this, Menu_Pengguna.class);
        startActivity(i);
        finish();
    }
}