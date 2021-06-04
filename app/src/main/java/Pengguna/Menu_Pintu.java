package Pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.example.teco.R;

import controlling.Controlling;

public class Menu_Pintu extends AppCompatActivity {

    LinearLayout linearBukaPintu;
    RequestQueue requestQueue;
    ProgressDialog pDialog;

    SharedPreferences sp;
    public static final String USER_PREF = "USER_PREF" ;
    public static final String userName = "userName";
    public static final String ID = "_id";
    public static final String NamaLengkap = "namaLengkap";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.menu__pintu);

        sp = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);

        linearBukaPintu = (LinearLayout) findViewById(R.id.linearBukaPintu);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        String idUser = sp.getString(ID, "");
        linearBukaPintu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pDialog.setMessage("Loading");
                Toast.makeText(getApplicationContext(), "Pintu Terbuka", Toast.LENGTH_LONG).show();
                Controlling controlling = new Controlling();
                controlling.controll(requestQueue, Menu_Pintu.this, "Buka Pintu", idUser,"Berhasil Membuka Pintu");
            }
        });
    }
    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(Menu_Pintu.this, Menu_Pengguna.class);
        startActivity(i);
        finish();
    }
}