

package controlling;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import Server.BaseURL;

public class Controlling {

    ProgressDialog pDialog;


    public void controll(RequestQueue requestQueue, Context context, String endPoint, String useId, String keterangan){
        requestQueue = Volley.newRequestQueue(context);
        pDialog = new ProgressDialog(context);
        pDialog.setCancelable(false);

        pDialog.setMessage("Loading");
        showDialog();
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
