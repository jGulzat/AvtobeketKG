package com.manas.avtobeketkg.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.manas.avtobeketkg.Model.Route;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.UI.Route2Activity;
import com.manas.avtobeketkg.UI.RouteActivity;

import java.util.ArrayList;

public class RoutesAdapter extends RecyclerView.Adapter <RoutesAdapter.RoutesViewHolder> {

    Context mContext;
    ArrayList<Route> mRoute;
    private OnRouteListener nOntouteListener;

    public RoutesAdapter(Context mContext, ArrayList<Route> mRoute, RouteActivity nOntouteListener) {
        this.mContext = mContext;
        this.mRoute = mRoute;
        this.nOntouteListener = (OnRouteListener) nOntouteListener;
    }
    public RoutesAdapter(Context mContext, ArrayList<Route> mRoute, Route2Activity nOntouteListener) {
        this.mContext = mContext;
        this.mRoute = mRoute;
        this.nOntouteListener = (OnRouteListener) nOntouteListener;
    }

    @NonNull
    @Override
    public RoutesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.list_of_routes,viewGroup,false);
        return new RoutesViewHolder(layout,nOntouteListener);
    }
    @Override
    public void onBindViewHolder(@NonNull RoutesViewHolder holder, int position) {

        String route = mRoute.get(position).getFrom() + " --> " + mRoute.get(position).getTo();
        holder.fromToTV.setText(route);
        holder.distanceTV.setText(mRoute.get(position).getDictance());
        holder.timeTV.setText(mRoute.get(position).getArrive_time());
        holder.dateTV.setText(mRoute.get(position).getDate());
        holder.priceTV.setText(mRoute.get(position).getPrice());
        if(mRoute.get(position).getStatus().equals("to"))
        { holder.transportImageView.setImageResource(R.drawable.transportto);
          holder.directionTV.setText("Туда");}
        else if(mRoute.get(position).getStatus().equals("from"))
        { holder.transportImageView.setImageResource(R.drawable.transportfrom);
          holder.directionTV.setText("Обратно");}

        holder.numberOfSeatsTV.setText(mRoute.get(position).getNumberOfSeats().toString());
        if(mRoute.get(position).isSelected())
              holder.routeRl.setBackgroundTintList((ColorStateList.valueOf(Color.parseColor("#DB1864"))));
        else  holder.routeRl.setBackgroundTintList((ColorStateList.valueOf(Color.parseColor("#f6f6f6"))));

    }
    @Override
    public int getItemCount() {
        return mRoute.size();
    }

    public class RoutesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        OnRouteListener onRouteListener;
        TextView fromToTV, distanceTV, dateTV, timeTV, priceTV, directionTV, numberOfSeatsTV;
        ImageView routeImageView, serviceImageView, transportImageView;
        RelativeLayout routeRl;
        public RoutesViewHolder(@NonNull View itemView, OnRouteListener onRouteListener) {
            super(itemView);
            this.onRouteListener = onRouteListener;
            fromToTV = itemView.findViewById(R.id.fromToTV);
            distanceTV = itemView.findViewById(R.id.distanceTV);
            dateTV = itemView.findViewById(R.id.dateTV);
            timeTV = itemView.findViewById(R.id.timeTV);
            priceTV = itemView.findViewById(R.id.priceTV);
            routeImageView = itemView.findViewById(R.id.routeImageView);
            serviceImageView = itemView.findViewById(R.id.serviceIV);
            transportImageView = itemView.findViewById(R.id.routeTransport);
            directionTV = itemView.findViewById(R.id.directionTV);
            numberOfSeatsTV = itemView.findViewById(R.id.numberSeatsTV);
            routeRl = itemView.findViewById(R.id.routeRL);


            itemView.setOnClickListener(this);
            routeImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onRouteListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onRouteListener.onRouteImageClick(position);
                        }
                    }
                }
            });

            serviceImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(onRouteListener != null){
                        int position = getAdapterPosition();
                        if(position != RecyclerView.NO_POSITION){
                            onRouteListener.onServiceImageClick(position);
                        }
                    }
                }
            });

        }

        @Override
        public void onClick(View v) {
            onRouteListener.onRouteClick(v,getAdapterPosition());
            //onRouteListener.onRouteImageClick(getAdapterPosition());
        }
    }

    public interface OnRouteListener{
        void onRouteClick(View view, int position);
        void onRouteImageClick(int position);
        void onServiceImageClick(int position);
    }
}
