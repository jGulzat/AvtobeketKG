package com.manas.avtobeketkg.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.manas.avtobeketkg.Model.FoodService;
import com.manas.avtobeketkg.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {
    Context mContext;
    ArrayList<FoodService>foodServices;

    public ServiceAdapter(Context mContext, ArrayList<FoodService> services) {
        this.mContext = mContext;
        this.foodServices = services;
    }

    @NonNull
    @Override
    public ServiceAdapter.ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_services,parent,false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ServiceViewHolder holder, int position) {
        ((ServiceViewHolder) holder).BindView(position);

    }

    @Override
    public int getItemCount() {
        return foodServices.size();
    }

    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        ImageView serviceIV;
        TextView serviceNameTV, serviceAddressTV;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            serviceIV = itemView.findViewById(R.id.serviceImage);
            serviceNameTV = itemView.findViewById(R.id.serviceNameTV);
            serviceAddressTV = itemView.findViewById(R.id.serviceAdressTV);
        }
        public void BindView(int position){
            serviceNameTV.setText(foodServices.get(position).getName());
            serviceAddressTV.setText(foodServices.get(position).getAddress());
            String URL = foodServices.get(position).getImage();
            Log.d("TAG", "BindView: URl: " + URL);
            Picasso.with(mContext).load("https://avtobeketkg.herokuapp.com"  + URL).into(serviceIV);
           // serviceIV.setImageResource(R.drawable.bus);
        }
    }
}
