package com.example.cs360blazehaldermanprojecttwo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder> {

    Context context;
    Activity activity;
    ArrayList item_id, item_name, item_date, item_quantity;

    CustomAdapter(Activity activity, Context context, ArrayList item_id, ArrayList item_date, ArrayList item_name, ArrayList item_quantity) {
        this.activity = activity;
        this.context = context;
        this.item_id = item_id;
        this.item_name = item_name;
        this.item_date = item_date;
        this.item_quantity = item_quantity;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.item_id_txt.setText(String.valueOf(item_id.get(position)));
        holder.item_date_txt.setText(String.valueOf(item_date.get(position)));
        holder.item_name_txt.setText(String.valueOf(item_name.get(position)));
        holder.item_quantity_txt.setText(String.valueOf(item_quantity.get(position)));
        holder.mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateItemActivity.class);
                intent.putExtra("item_id", String.valueOf(item_id.get(position)));
                intent.putExtra("name", String.valueOf(item_name.get(position)));
                intent.putExtra("quantity", String.valueOf(item_quantity.get(position)));
                activity.startActivityForResult(intent, 1);
            }
        });
    }

    @Override
    public int getItemCount() {
        return item_id.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView item_id_txt, item_name_txt, item_date_txt, item_quantity_txt;
        LinearLayout mainLayout;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            item_id_txt = itemView.findViewById(R.id.item_id_txt);
            item_name_txt = itemView.findViewById(R.id.item_name_txt);
            item_date_txt = itemView.findViewById(R.id.item_date_txt);
            item_quantity_txt = itemView.findViewById(R.id.item_quantity_txt);
            mainLayout = itemView.findViewById(R.id.mainLayout);
        }
    }
}
