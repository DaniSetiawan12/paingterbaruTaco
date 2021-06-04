package User;

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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import Admin.menu_Admin;
import Server.BaseURL;
import Pengguna.Menu_Pengguna;
import com.example.teco.R;

import org.json.JSONException;
import org.json.JSONObject;

public class MenuLogin extends AppCompatActivity {

    Button btnLogin, btnDaftar;
    EditText edtUserName, edtPassword;

    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;
    SharedPreferences sp;
    public static final String USER_PREF = "USER_PREF" ;
    public static final String login = "login";
    public static final String userName = "userName";
    public static final String ID = "_id";
    public static final String NamaLengkap = "namaLengkap";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_login);
        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        sp = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);


        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnDaftar = (Button) findViewById(R.id.btnDaftar);
        edtUserName = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);


        btnDaftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuLogin.this, Registrasi.class);
                startActivity(i);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = edtUserName.getText().toString();
                String password = edtPassword.getText().toString();
                if (userName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong",
                            Toast.LENGTH_LONG).show();
                }else if (password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong",
                            Toast.LENGTH_LONG).show();
                }else {
                    try {
                        JSONObject jsonObj1=null;
                        jsonObj1=new JSONObject();
                        jsonObj1.put("username", userName);
                        jsonObj1.put("password", password);

                        Log.d("Data = ", jsonObj1.toString());
                        login(jsonObj1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
    public void login(JSONObject datas){
        pDialog.setMessage("Mohon Tunggu .........");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, BaseURL.MenuLogin, datas,
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
                                String namaTaco = jObjData.getString("namaLengkap");
                                String username = jObjData.getString("username");
                                String rool = jObjData.getString("rool");
                                if (rool.equals("1")){
                                    Intent i = new Intent(MenuLogin.this, menu_Admin.class);
                                    i.putExtra("_id", _id);
                                    i.putExtra("namaTaco", namaTaco);
                                    i.putExtra("username", username);
                                    startActivity(i);
                                    finish();
                                }
                                else {
                                    Intent i = new Intent(MenuLogin.this, Menu_Pengguna.class);
                                    i.putExtra("_id", _id);
                                    i.putExtra("namaTaco", namaTaco);
                                    i.putExtra("username", username);
                                    startActivity(i);
                                    finish();
                                }
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString(login, "1");
                                editor.putString(userName, username);
                                editor.putString(ID, _id);
                                editor.putString(NamaLengkap, namaTaco);
                                editor.commit();
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

