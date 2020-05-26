package com.manas.avtobeketkg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.manas.avtobeketkg.Model.VokzalInfo;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.UI.Fragments.HelpFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class VokzalAdapter  extends RecyclerView.Adapter<VokzalAdapter.VokzalViewHolder> {

    HelpFragment mContext;
    ArrayList<VokzalInfo> vokzalInfo;
    private VokzalListener vokzalListener;

    public VokzalAdapter(HelpFragment mContext, ArrayList<VokzalInfo> vokzalInfo, HelpFragment onVokzalListener) {
        this.mContext = mContext;
        this.vokzalInfo = vokzalInfo;
        this.vokzalListener =(VokzalListener) onVokzalListener;
    }

    @NonNull
    @Override
    public VokzalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_of_vokzal,parent,false);
        return new VokzalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VokzalViewHolder holder, int position) {
        ((VokzalViewHolder) holder).bindView(position);
    }

    @Override
    public int getItemCount()
    {
        return vokzalInfo.size();
    }


    public class VokzalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView nameTV, adressTV;
        private ImageView vokzalImage;

        public VokzalViewHolder(@NonNull View itemView)
        {  super(itemView);

           nameTV = itemView.findViewById(R.id.name_vokzal);
           adressTV = itemView.findViewById(R.id.phone_vokzal);
           vokzalImage = itemView.findViewById(R.id.image_vokzal);
           itemView.setOnClickListener(this);
        }

        public void bindView(int position)
        {
            nameTV.setText(vokzalInfo.get(position).getName());
            adressTV.setText(vokzalInfo.get(position).getPhoneNumber());
           // vokzalImage.setImageResource(vokzalInfo.get(position).getImage());
            Picasso.with(vokzalImage.getContext()).load("https://avtobeketkg.herokuapp.com" + vokzalInfo.get(position).getImage1())
                    .into(vokzalImage);

        }

        @Override
        public void onClick(View v) {
           vokzalListener.onVokzalClicked(v,getAdapterPosition());
        }

    }

    public interface VokzalListener{
        void onVokzalClicked(View view,int position);
    }



}
