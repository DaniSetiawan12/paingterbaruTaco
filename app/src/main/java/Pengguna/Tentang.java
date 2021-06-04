package Pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.teco.R;


public class Tentang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tentang);
        getSupportActionBar().hide();

    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(Tentang.this, Menu_Pengguna.class);
        startActivity(i);
        finish();
    }
}