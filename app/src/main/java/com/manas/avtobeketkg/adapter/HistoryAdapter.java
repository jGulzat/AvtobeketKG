package com.manas.avtobeketkg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.manas.avtobeketkg.Model.History;
import com.manas.avtobeketkg.R;

import java.util.ArrayList;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder> {
    Context mContext;
    ArrayList<History> histories;

    public HistoryAdapter(Context mContext, ArrayList<History> histories) {
        this.mContext = mContext;
        this.histories = histories;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout;
        layout = LayoutInflater.from(mContext).inflate(R.layout.list_of_history,parent,false);
        return new HistoryViewHolder(layout);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {

        String fromToTV = histories.get(position).getFrom() + " --> "
                + histories.get(position).getTo();
        holder.fromToTV.setText(fromToTV);
        holder.usernameTV.setText(histories.get(position).getUsername());
        holder.fullnameTV.setText(histories.get(position).getFullname());
        holder.dateTV.setText(histories.get(position).getDate());
        holder.priceTV.setText(histories.get(position).getPrice());
    }

    @Override
    public int getItemCount() { return histories.size(); }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView fromToTV, fullnameTV, dateTV, usernameTV, priceTV;


        public HistoryViewHolder(@NonNull View itemView)
        {    super(itemView);
             fromToTV = itemView.findViewById(R.id.fromToTV);
             fullnameTV = itemView.findViewById(R.id.fullnameTV);
             dateTV = itemView.findViewById(R.id.dateTV);
             usernameTV = itemView.findViewById(R.id.usernameTV);
             priceTV = itemView.findViewById(R.id.priceTV);
        }
    }
}
