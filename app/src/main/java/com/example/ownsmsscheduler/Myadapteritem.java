package com.example.ownsmsscheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Myadapteritem extends ArrayAdapter<Myclassitem> {


    public Myadapteritem(Context context, int resource, List<Myclassitem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

     View v=convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            v = vi.inflate(R.layout.listitems, null);
        }

        Myclassitem p = getItem(position);

        if (p != null) {
            TextView tt1 = (TextView) v.findViewById(R.id.id_msg);
            TextView tt2 = (TextView) v.findViewById(R.id.id_date);
            TextView tt3 = (TextView) v.findViewById(R.id.id_status);

            String temp_msg=p.getMessage();

            if(temp_msg.length()>25)
                tt1.setText(temp_msg.substring(0,25)+"..");
            else
                tt1.setText(temp_msg);

            Date temp_date=new Date(p.getDate());
            SimpleDateFormat temp_format=new SimpleDateFormat("d/MM/yyyy\nhh:mm a");

            tt2.setText(temp_format.format(temp_date));

            tt3.setText(p.getStatus());
        }

        return v;

    }
}
