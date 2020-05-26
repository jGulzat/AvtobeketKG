package com.manas.avtobeketkg.UI.Fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.gms.common.api.Api;
import com.manas.avtobeketkg.Api.ApiService;
import com.manas.avtobeketkg.Model.VokzalInfo;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.UI.Route2Activity;
import com.manas.avtobeketkg.UI.RouteActivity;
import com.manas.avtobeketkg.UI.VokzalInfoActivity;
import com.manas.avtobeketkg.ViewModel.SearchViewModel;
import com.manas.avtobeketkg.ViewModel.VokzalInfoViewModel;
import com.manas.avtobeketkg.adapter.RoutesAdapter;
import com.manas.avtobeketkg.adapter.VokzalAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class HelpFragment extends Fragment implements VokzalAdapter.VokzalListener {


    public HelpFragment() {
        // Required empty public constructor
    }

    private RecyclerView vokzalInfoRV;
    VokzalAdapter vokzalAdapter;
    ArrayList<VokzalInfo> vokzalInfoArrayList = new ArrayList<>();
    private VokzalInfoViewModel vokzalInfoViewModel;

    SharedPreferences sharedpreferences;
    public static final String mypreference = "mypref";
    public static final String Token = "token";

    ImageView logo;

    String token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_help,container,false);
        vokzalInfoRV = v.findViewById(R.id.list_of_vokzal);
        sharedpreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        if (sharedpreferences.contains(Token)) {
            token = sharedpreferences.getString(Token, "");
            Log.d("TAG", "run: tokennnnnnnnnnn" + sharedpreferences.getString(Token, ""));
        }

        vokzalInfoViewModel = ViewModelProviders.of(requireActivity()).get(VokzalInfoViewModel.class);

        logo = v.findViewById(R.id.logo_avtobeket);

       return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fillInfo();
    }

    private void fillInfo() {

        final ProgressDialog progressDoalog;
        progressDoalog = new ProgressDialog(getActivity());
        progressDoalog.setMessage("Its loading....");
        progressDoalog.setTitle("Stations");
        progressDoalog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        // show it
        progressDoalog.show();
        vokzalInfoViewModel.getVokzalInfo("Token " + token).observe(this, new Observer<List<VokzalInfo>>() {
            @Override
            public void onChanged(List<VokzalInfo> vokzalInfos) {
                progressDoalog.dismiss();
                vokzalInfoArrayList = (ArrayList<VokzalInfo>) vokzalInfos;
                vokzalAdapter = new VokzalAdapter(HelpFragment.this,vokzalInfoArrayList,HelpFragment.this);
                vokzalInfoRV.setAdapter(vokzalAdapter);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                vokzalInfoRV.setLayoutManager(layoutManager);
            }
        });
    }

    @Override
    public void onVokzalClicked(View view, int position) {

        Intent intent = new Intent(getActivity(), VokzalInfoActivity.class);
        intent.putExtra("name",vokzalInfoArrayList.get(position).getName());
        intent.putExtra("phoneNumber",vokzalInfoArrayList.get(position).getPhoneNumber());
        intent.putExtra("vokzalInfo",vokzalInfoArrayList.get(position).getInfo());
        intent.putExtra("image",vokzalInfoArrayList.get(position).getImage1());
        startActivity(intent);
    }
}
