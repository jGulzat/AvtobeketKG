package com.manas.avtobeketkg.UI.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.manas.avtobeketkg.Model.User;
import com.manas.avtobeketkg.MyInfoActivity;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.UI.ChangePassActivity;
import com.manas.avtobeketkg.UI.HistoryActivity;
import com.manas.avtobeketkg.UI.LoginActivity;
import com.manas.avtobeketkg.ViewModel.MyInfoViewModel;
import com.manas.avtobeketkg.ViewModel.SearchViewModel;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    public ProfileFragment() {
        // Required empty public constructor
    }

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    public static final String mypreference = "mypref";
    public static final String Token = "token";

    private Button MyInfoBtn, changePass,logoutBtn,historyBtn;
    TextView nameTV;
    View v;
    MyInfoViewModel viewModel;
    String token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        sharedPreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.contains(Token))
        { Log.d("SearchFragment", "run: tokennnnnnnnnnn" + sharedPreferences.getString(Token, ""));
            token = sharedPreferences.getString(Token, ""); }

        viewModel =  ViewModelProviders.of(requireActivity()).get(MyInfoViewModel.class);

         initUI();
         initDate();
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel.getUserInfo("Token " + token).observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                Log.d("TAG", "onChanged: " + user.getFirstname());
                nameTV.setText(user.getFirst_name() + " " + user.getLast_name());
            }
        });
    }
    public void initUI(){
        MyInfoBtn =  v.findViewById(R.id.MyInfoBtn);
        changePass = v.findViewById(R.id.changePassBtn);
        logoutBtn = v.findViewById(R.id.logoutBTN);
        historyBtn = v.findViewById(R.id.historyBTN);
        nameTV  = v.findViewById(R.id.fullnameTextView);

    }

   public void initDate(){
       MyInfoBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(getActivity(), MyInfoActivity.class);
               startActivity(i);
           }
       });
       changePass.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(getActivity(), ChangePassActivity.class);
               startActivity(i);
           }
       });
       logoutBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
               alert.setMessage("Выйти из приложения?");
               alert.setNeutralButton("OK", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       editor.clear();
                       editor.commit();
                       Log.d("TAG1111111111111", sharedPreferences.getString(Token, ""));
                       Toast.makeText(getContext(),"Ваши данные удалены из приложения!",Toast.LENGTH_SHORT).show();
                       Intent i = new Intent(getActivity(),LoginActivity.class);
                       i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                       startActivity(i);
                   }
               });
               alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       dialog.cancel();
                   }
               });
               alert.show();
           }
       });

       historyBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent = new Intent(getActivity(), HistoryActivity.class);
               startActivity(intent);
           }
       });
   }
}
