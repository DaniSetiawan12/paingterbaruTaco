package Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import Model.ModelTaco;
import com.example.teco.R;

import java.util.List;

public class AdapterTaco extends BaseAdapter {

    private Activity activity;
    private LayoutInflater inflater;
    private List<ModelTaco> item;

    public AdapterTaco(Activity activity, List<ModelTaco> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int position) {
        return item.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.content_admin, null);



        TextView namaTaco = (TextView) convertView.findViewById(R.id.txtNamaTaco);

        namaTaco.setText(item.get(position).getNamaTaco());

        return convertView;
    }
}
