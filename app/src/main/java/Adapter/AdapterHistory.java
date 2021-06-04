package Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import Model.ModelHistory;

import com.example.teco.R;

import java.util.List;

public class AdapterHistory extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelHistory> item;

    SharedPreferences sp;
    public static final String USER_PREF = "USER_PREF" ;
    public static final String userName = "userName";
    public static final String ID = "_id";
    public static final String NamaLengkap = "namaLengkap";

    String namaLengkap;
    public AdapterHistory(Activity activity, List<ModelHistory> item, SharedPreferences sp) {
        this.activity = activity;
        this.item = item;
        this.sp = sp;
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
        public View getView ( int position, View convertView, ViewGroup parent){
            if (inflater == null)
                inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            if (convertView == null)
                convertView = inflater.inflate(R.layout.content_history, null);


            TextView namaTaco = (TextView) convertView.findViewById(R.id.txtNamaTaco);
            TextView Status = (TextView) convertView.findViewById(R.id.edtStatus);
            TextView Tanggal = (TextView) convertView.findViewById(R.id.edtTanggal);
            namaLengkap = sp.getString(NamaLengkap, "");
            namaTaco.setText(namaLengkap);
            Status.setText(item.get(position).getStatus());
            Tanggal.setText(item.get(position).getTanggal());
            return convertView;
    }
}

