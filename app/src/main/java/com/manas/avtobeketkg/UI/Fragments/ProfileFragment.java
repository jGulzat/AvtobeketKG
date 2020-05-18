package com.manas.avtobeketkg.UI.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.manas.avtobeketkg.MyInfoActivity;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.UI.ChangePassActivity;
import com.manas.avtobeketkg.UI.HistoryActivity;
import com.manas.avtobeketkg.UI.LoginActivity;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    public ProfileFragment() {
        // Required empty public constructor
    }

    public static final String mypreference = "mypref";
    public static final String Token = "token";

    private Button MyInfoBtn, changePass,logoutBtn,historyBtn;
    View v;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(mypreference, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        MyInfoBtn =  v.findViewById(R.id.MyInfoBtn);
        changePass = v.findViewById(R.id.changePassBtn);
        logoutBtn = v.findViewById(R.id.logoutBTN);
        historyBtn = v.findViewById(R.id.historyBTN);


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


        return v;
    }
}
