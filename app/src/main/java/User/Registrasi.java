package User;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
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
import com.example.teco.R;
import Server.BaseURL;

import org.json.JSONException;
import org.json.JSONObject;

public class Registrasi extends AppCompatActivity {

    private RequestQueue mRequestQueue;
    ProgressDialog pDialog;

    EditText edtEmail, edtUsername, edtNamaLengkap, edtPassword;

    Button btnRegistrasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrasi);
        getSupportActionBar().hide();

        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtNamaLengkap = (EditText) findViewById(R.id.edtNamaLengkap);
        edtPassword = (EditText) findViewById(R.id.edtPassword);

        btnRegistrasi = (Button) findViewById(R.id.btnRegistrasi);

        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmail.getText().toString();
                String userName = edtUsername.getText().toString();
                String namaLengkap = edtNamaLengkap.getText().toString();
                String password = edtPassword.getText().toString();

                if(email.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Email tidak boleh kosong", Toast.LENGTH_LONG).show();
                }else if(userName.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Username Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if(namaLengkap.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Nama Lengkap Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else if(password.isEmpty()){
                    Toast.makeText(getApplicationContext(), "Password Tidak Boleh Kosong", Toast.LENGTH_LONG).show();
                }else {
                    try {
                        JSONObject jsonObj1=null;
                        jsonObj1=new JSONObject();
                        jsonObj1.put("email", email);
                        jsonObj1.put("username", userName);
                        jsonObj1.put("namaLengkap", namaLengkap);
                        jsonObj1.put("password", password);
                        jsonObj1.put("rool", "2");
                        Log.d("Data = ", jsonObj1.toString());
                        registrasi(jsonObj1);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed(){
        Intent i = new Intent(Registrasi.this, MenuLogin.class);
        startActivity(i);
        finish();
    }

    public void registrasi(JSONObject datas){
        pDialog.setMessage("Mohon Tunggu .........");
        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, BaseURL.registrasi, datas,
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
                                Intent i = new Intent(Registrasi.this, MenuLogin.class);
                                startActivity(i);
                                finish();
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