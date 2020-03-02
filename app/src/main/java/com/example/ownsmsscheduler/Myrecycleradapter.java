package com.example.ownsmsscheduler;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Myrecycleradapter  extends RecyclerView.Adapter<Myrecycleradapter.RecyclerViewHolder> {


    public ArrayList<Myclassitem> arrayList=new ArrayList<>();
    Context context;

    public Myrecycleradapter(ArrayList<Myclassitem> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.listitems,parent,false);
        Myrecycleradapter.RecyclerViewHolder recyclerViewHolder=new Myrecycleradapter.RecyclerViewHolder(view);


        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        final Myclassitem myclassitem = arrayList.get(position);


        String temp_msg = myclassitem.getMessage();
        Log.i("----", "onBindViewHolder:  "+arrayList.get(position).getMessage());

        if (temp_msg.length() > 25)

            holder.tt1.setText(temp_msg.substring(0, 25) + "..");

        else
            holder.tt1.setText(temp_msg);

        Date temp_date=new Date(myclassitem.getDate());
        SimpleDateFormat temp_format=new SimpleDateFormat("d/MM/yyyy\nhh:mm a");

        holder.tt2.setText(temp_format.format(temp_date));
        holder.tt3.setText(myclassitem.getStatus());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int temp_id=myclassitem.getId();
                String temp_msg=myclassitem.getMessage();
                String temp_num=myclassitem.getNumber();
                long temp_date=myclassitem.getDate();
                String temp_status=myclassitem.getStatus();

              //  Toast.makeText(Myrecycleradapter.this,"no.:"+temp_num,Toast.LENGTH_LONG).show();

                Intent intent=new Intent(context,MyEdittime.class);
                intent.putExtra("schedule","edit");
                intent.putExtra("id",temp_id);
                intent.putExtra("msg",temp_msg);
                intent.putExtra("num",temp_num);
                intent.putExtra("date",temp_date);
                intent.putExtra("status",temp_status);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView tt1,tt2,tt3;
        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);




            tt1 = (TextView) itemView.findViewById(R.id.id_msg);
            tt2 = (TextView) itemView.findViewById(R.id.id_date);
            tt3 = (TextView) itemView.findViewById(R.id.id_status);

        }
    }


}
