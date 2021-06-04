package Pengguna;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.teco.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.AdapterHistory;
import Adapter.AdapterHistoryAdmin;
import Admin.historyAdmin;
import Model.ModelHistory;
import Server.BaseURL;

public class History extends AppCompatActivity {

    ProgressDialog pDialog;

    AdapterHistory adapter;
    ListView list;

    ArrayList<ModelHistory> newsList = new ArrayList<ModelHistory>();
    private RequestQueue mRequestQueue;

    int socketTimeout = 500000;
    RetryPolicy policy = new DefaultRetryPolicy(socketTimeout,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

    TextView total;

    EditText edtSearch;

    SharedPreferences sp;
    public static final String USER_PREF = "USER_PREF" ;
    public static final String userName = "userName";
    public static final String ID = "_id";
    public static final String NamaLengkap = "namaLengkap";

    String idUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        getSupportActionBar().hide();
        mRequestQueue = Volley.newRequestQueue(this);
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);

        edtSearch = (EditText) findViewById(R.id.edtSearch);
        sp = getSharedPreferences(USER_PREF, Context.MODE_PRIVATE);
        idUser = sp.getString(ID, "");

        total = (TextView) findViewById(R.id.total);

        list = (ListView) findViewById(R.id.array_list);
        newsList.clear();
        adapter = new AdapterHistory(History.this, newsList, sp);
        list.setAdapter(adapter);

        getHistory();
        cari();

    }

    private void getHistory() {
        // Pass second argument as "null" for GET requests
        pDialog.setMessage("Loading");
//        showDialog();
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.GET, BaseURL.history+ idUser, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
//                        hideDialog();
                        try {

//                            String data = response.getString("data");
                            String data = response.getString("data");
                            JSONArray jsonArray = new JSONArray(data);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                final ModelHistory history = new ModelHistory();
                                final String _id = jsonObject.getString("_id");
                                final String status = jsonObject.getString("status");
                                final String tgl = jsonObject.getString("created_at");
                                final String namaUser = jsonObject.getString("namaUser");

                                history.setId(_id);
                                history.setStatus(status);
                                history.setTanggal(tgl);
                                history.setUser(namaUser);

                                newsList.add(history);
                            }

                            total.setText(response.getString("totalBukaPintu"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
//                hideDialog();
            }
        });

        req.setRetryPolicy(policy);
        /* Add your Requests to the RequestQueue to execute */
        mRequestQueue.add(req);
    }
    @Override
    public void onBackPressed(){
        Intent i = new Intent(History.this, Menu_Pengguna.class);
        startActivity(i);
        finish();
    }

    public void cari(){
        edtSearch.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence query, int start, int before, int count) {

                query = query.toString().toLowerCase();

                final List<ModelHistory> filteredList = new ArrayList<ModelHistory>();

                for (int i = 0; i < newsList.size(); i++) {

                    final String text = newsList.get(i).getUser().toLowerCase();
                    if (text.contains(query)) {
                        filteredList.add(newsList.get(i));
                    }
                }
                adapter = new AdapterHistory(History.this, filteredList, sp);
                list.setAdapter(adapter);
            }
        });
    }
}