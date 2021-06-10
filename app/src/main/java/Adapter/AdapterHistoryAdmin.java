package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.teco.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import Admin.historyAdmin;
import Admin.menu_Admin;
import Model.ModelHistory;
import Pengguna.Menu_Pengguna;
import Server.BaseURL;
import User.MenuLogin;

public class AdapterHistoryAdmin extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelHistory> item;
    private RequestQueue mRequestQueue;

    SharedPreferences sp;
    public static final String USER_PREF = "USER_PREF" ;
    public static final String userName = "userName";
    public static final String ID = "_id";
    public static final String NamaLengkap = "namaLengkap";

    String namaLengkap;
    public AdapterHistoryAdmin(Activity activity, List<ModelHistory> item, SharedPreferences sp) {
        this.activity = activity;
        this.item = item;
        this.sp = sp;
        mRequestQueue = Volley.newRequestQueue(activity);
    }

    @Override
    public int getCount () {
        return item.size();
    }

    @Override
    public Object getItem ( int position){
        return item.get(position);
    }

    @Override
    public long getItemId ( int position){
        return position;
    }

    @Override
    public View getView (int position, View convertView, ViewGroup parent){
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_history_admin, null);


        TextView namaTaco = (TextView) convertView.findViewById(R.id.txtNamaTaco);
        TextView Status = (TextView) convertView.findViewById(R.id.edtStatus);
        TextView Tanggal = (TextView) convertView.findViewById(R.id.edtTanggal);
        Button hps       = (Button) convertView.findViewById(R.id.Hapus);
//        namaLengkap = sp.getString(NamaLengkap, "");
        hps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(activity, item.get(position).getId(), Toast.LENGTH_LONG).show();
                hapusData(item.get(position).getId());
            }
        });
        namaTaco.setText(item.get(position).getUser());
        Status.setText(item.get(position).getStatus());
        Tanggal.setText(item.get(position).getTanggal());
        return convertView;
    }

    public void hapusData(String id){
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.DELETE, BaseURL.hapusHistory+id, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.toString());
                            String strMsg = jsonObject.getString("msg");
                            boolean status= jsonObject.getBoolean("error");
                            if(status == false){
                                Toast.makeText(activity, "Berhasil mengahpus data", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(activity, historyAdmin.class);
                                activity.startActivity(i);
                                activity.finish();
                            }else {
                                Toast.makeText(activity, strMsg, Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.e("Error: ", error.getMessage());
            }
        });
        mRequestQueue.add(req);
    }
}

