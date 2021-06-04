package com.example.teco;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import Admin.menu_Admin;
import Pengguna.Menu_Pengguna;
import Server.BaseURL;

public class KonfirmasiSandi extends AppCompatActivity {

    Button btnLoginKon;
    EditText edtUsernameKon, edtPasswordKon;

    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;
    SharedPreferences sp;
    public static final String USER_PREF = "USER_PREF" ;
    public static final String userName = "userName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_konfimasi_sandi);
        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sp = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);


        btnLoginKon = (Button) findViewById(R.id.btnLoginKon);
        edtUsernameKon = (EditText) findViewById(R.id.edtUsernameKon);
        edtPasswordKon = (EditText) findViewById(R.id.edtPasswordKon);

        String uName = sp.getString(userName, "");
        edtUsernameKon.setText(uName);
        btnLoginKon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(KonfirmasiSandi.this, Menu_Pengguna.class);
                startActivity(i);
                finish();
            }
        });
        btnLoginKon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = edtUsernameKon.getText().toString();
                String password = edtPasswordKon.getText().toString();
                if (password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong",
                            Toast.LENGTH_LONG).show();
                }else {
                    try {
                        JSONObject jsonObj1=null;
                        jsonObj1=new JSONObject();
                        jsonObj1.put("username", userName);
                        jsonObj1.put("password", password);

                        Log.d("Data = ", jsonObj1.toString());
                        konfirmasiSandi(jsonObj1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    public void konfirmasiSandi(JSONObject datas){
        pDialog.setMessage("Mohon Tunggu .........");
        Log.e("adada", "adadada");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, BaseURL.konfirmasiSandi, datas,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String strMsg = jsonObject.getString("msg");
                            boolean status= jsonObject.getBoolean("error");
                            if(status == false){
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                                String data = jsonObject.getString("data");
                                JSONObject jObjData = new JSONObject(data);
                                String _id = jObjData.getString("_id");
                                String username = jObjData.getString("username");
                                String rool = jObjData.getString("rool");
                                if (rool.equals("1")){
                                    Intent i = new Intent(KonfirmasiSandi.this, menu_Admin.class);
                                    i.putExtra("_id", _id);
                                    i.putExtra("username", username);
                                    startActivity(i);
                                    finish();
                                }
                                else {
                                    Intent i = new Intent(KonfirmasiSandi.this, Menu_Pengguna.class);
                                    i.putExtra("_id", _id);
                                    i.putExtra("username", username);
                                    startActivity(i);
                                    finish();
                                }
                            }else {
                                Toast.makeText(getApplicationContext(), strMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
                VolleyLog.e("Error: ", error.getMessage());
                Log.e("Error Response = ", error.toString());
            }
        });
        mRequestQueue.add(req);
    }

    private void showDialog(){
        if(!pDialog.isShowing()){
            pDialog.show();
        }
    }

    private void hideDialog(){
        if(pDialog.isShowing()){
            pDialog.dismiss();
        }
    }
}