

package controlling;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.teco.Flashscreen;
import com.example.teco.KonfirmasiSandi;

import org.json.JSONException;
import org.json.JSONObject;

import Server.BaseURL;
import User.MenuLogin;

public class Controlling {

    ProgressDialog pDialog;


    public void controll(RequestQueue requestQueue, Context context, String endPoint, String useId, String keterangan){
        requestQueue = Volley.newRequestQueue(context);
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);

        pDialog.setMessage("Loading");
        showDialog();
        RequestQueue finalRequestQueue = requestQueue;
        JsonObjectRequest req = new JsonObjectRequest(BaseURL.controlling + endPoint+ "?user=" + useId, null,
                new Response.Listener<JSONObject>
                        () {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String res = response.getString("msg");
                            Toast.makeText(context, keterangan, Toast.LENGTH_SHORT).show();
                            if (endPoint.equals("Buka Pintu")){
                                Thread timerThread = new Thread() {
                                    public void run() {
                                        try {
                                            sleep(3000);
                                        } catch (InterruptedException e) {
                                            e.printStackTrace();
                                        } finally {
                                            controllTutupPintu(finalRequestQueue, context, "Tutup Pintu", useId, "Tutup pintu");
                                        }
                                    }
                                };
                                timerThread.start();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
            }
        });
        requestQueue.add(req);
    }

    public void controllTutupPintu(RequestQueue requestQueue, Context context, String endPoint, String useId, String keterangan){
        requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest req = new JsonObjectRequest(BaseURL.controlling + endPoint+ "?user=" + useId, null,
                new Response.Listener<JSONObject>
                        () {
                    @Override
                    public void onResponse(JSONObject response) {
                        hideDialog();
                        try {
                            String res = response.getString("msg");
                            Toast.makeText(context, keterangan, Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hideDialog();
            }
        });
        requestQueue.add(req);
    }



    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

}
