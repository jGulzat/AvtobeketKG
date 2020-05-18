package com.manas.avtobeketkg.adapter;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.manas.avtobeketkg.Model.Passenger;
import com.manas.avtobeketkg.R;
import java.util.ArrayList;

public class PassengerAdapter extends RecyclerView.Adapter<PassengerAdapter.ViewHolder>{

    private Context context;
    private ArrayList<Passenger> passengers = new ArrayList<>();
    public PassengerAdapter(Context context, ArrayList<Passenger>passengers){
       this.passengers = passengers;
       this.context = context;
    }

    @NonNull
    @Override
    public PassengerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_passengersdate,parent,false);
        return new PassengerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PassengerAdapter.ViewHolder holder, int position) {

        /*holder.emailP.setText(passengers.get(position).getEmail());
        Log.d("print","yes:" + passengers.get(position).getEmail());
        holder.fullnameP.setText(passengers.get(position).getFullname());
        holder.idCardP.setText(passengers.get(position).getIdentityCard());*/
        holder.passengerNumber.setText("Passenger " + (position+1));
        if(passengers.get(position).getPlace_number() == 0)
            holder.seatNumber.setVisibility(View.GONE);
        else
        {  holder.seatNumber.setVisibility(View.VISIBLE);
           holder.seatNumber.setText("Место №: " + passengers.get(position).getPlace_number());}
    }

    @Override
    public int getItemCount() {
        return passengers.size();
    }

    public class  ViewHolder  extends RecyclerView.ViewHolder{
        private EditText emailP;
        private EditText fullnameP;
        private TextView  seatNumber;
        private TextView passengerNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            emailP = itemView.findViewById(R.id.passengerEmail);
            fullnameP = itemView.findViewById(R.id.passengerFullname);
            seatNumber = itemView.findViewById(R.id.passengerCardNumber);
            passengerNumber = itemView.findViewById(R.id.passengerId);

            emailP.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    //passengers.get(getAdapterPosition()).setEmail(emailP.getText().toString());
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    passengers.get(getAdapterPosition()).setEmail(emailP.getText().toString());
                    Log.d("TAG", "onTextChanged: " + passengers.get(getAdapterPosition()).getEmail());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            fullnameP.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    passengers.get(getAdapterPosition()).setFullname(fullnameP.getText().toString());
                    Log.d("TAG", "onTextChanged: " + passengers.get(getAdapterPosition()).getFullname());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }

    }
}

/*

    public PassengerAdapter(Context ctx, ArrayList<Passenger> passengers){

        inflater = LayoutInflater.from(ctx);
        this.passengers = passengers;
    }

    @NonNull
    @Override
    public PassengerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = inflater.inflate(R.layout.list_of_passengersdate, parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PassengerAdapter.MyViewHolder holder, int position) {

        holder.emailP.setText(passengers.get(position).getEmail());
        Log.d("print","yes:" + passengers.get(position).getEmail());
        holder.fullnameP.setText(passengers.get(position).getFullname());
        holder.idCardP.setText(passengers.get(position).getIdentityCard());

    }

    @Override
    public int getItemCount() {
        return passengers.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        protected EditText emailP;
        protected EditText fullnameP;
        protected  EditText idCardP;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            emailP = itemView.findViewById(R.id.passengerEmail);
            fullnameP = itemView.findViewById(R.id.passengerFullname);
            idCardP = itemView.findViewById(R.id.passengerCardNumber);

            emailP.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    passengers.get(getAdapterPosition()).setEmail(emailP.getText().toString());
                   }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            fullnameP.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                   passengers.get(getAdapterPosition()).setFullname(fullnameP.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            idCardP.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                passengers.get(getAdapterPosition()).setIdentityCard(idCardP.getText().toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }
    }
 */
