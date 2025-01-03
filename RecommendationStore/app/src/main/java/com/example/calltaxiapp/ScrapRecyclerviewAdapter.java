package com.example.calltaxiapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ScrapRecyclerviewAdapter extends RecyclerView.Adapter<ScrapRecyclerviewAdapter.CustomViewHolder> {

    public ArrayList<FactoryInfo> list;
    private LayoutInflater mInflater;

    @NonNull
    @Override
    public ScrapRecyclerviewAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.scrapitem,parent,false);
        CustomViewHolder holder = new CustomViewHolder(view);
        return holder;
    }

    public ScrapRecyclerviewAdapter(Context context, ArrayList<FactoryInfo> data) {
        this.list = data;
        this.mInflater = LayoutInflater.from(context);
    }

    public ScrapRecyclerviewAdapter(ArrayList<FactoryInfo> list) {
        this.list = list;
    }

    @Override
    public void onBindViewHolder(@NonNull ScrapRecyclerviewAdapter.CustomViewHolder holder, int position) {
        FactoryInfo item = list.get(position);
        holder.amount.setText(item.getTransactionAmount());
        holder.address.setText(item.getAddress());
        holder.storeName.setText(item.getStoreName());
        holder.etc.setText(item.getLandArea()+""+"("+(2024-Integer.valueOf(item.getConstructYear()))+"년차)");
        holder.paymentType.setText(item.getContractMethod());
    }

    public void setList(ArrayList<FactoryInfo> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        TextView storeName;
        TextView amount;
        TextView priceMonthly;
        TextView address;
        TextView paymentType;
        TextView etc;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.storeName = (TextView) itemView.findViewById(R.id.name);
            this.amount = (TextView) itemView.findViewById(R.id.amount);
            this.priceMonthly = (TextView) itemView.findViewById(R.id.Textview_content);
            this.address = (TextView) itemView.findViewById(R.id.address);
            this.paymentType = (TextView) itemView.findViewById(R.id.amount2);
            this.etc = (TextView) itemView.findViewById(R.id.etc);

        }

        void onBind(FactoryInfo item){

        }
    }
}
