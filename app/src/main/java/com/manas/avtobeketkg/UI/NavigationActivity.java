package com.manas.avtobeketkg.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.manas.avtobeketkg.R;
import com.manas.avtobeketkg.UI.Fragments.HelpFragment;
import com.manas.avtobeketkg.UI.Fragments.MapFragment;
import com.manas.avtobeketkg.UI.Fragments.ProfileFragment;
import com.manas.avtobeketkg.UI.Fragments.SearchFragment;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        initial();
    }

    public void initial(){
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_nav);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.home1){
                    SearchFragment fragment = new SearchFragment();

                    //fragment.setArguments(b);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.navigation_layout,fragment);
                    fragmentTransaction.commit();
                }

                if(id == R.id.profile){
                    ProfileFragment fragment = new ProfileFragment();

                    //fragment.setArguments(b);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.navigation_layout,fragment);
                    fragmentTransaction.commit();
                }

                if(id == R.id.help){
                    HelpFragment fragment = new HelpFragment();

                   // fragment.setArguments(b);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.navigation_layout,fragment);
                    fragmentTransaction.commit();
                }

                if(id == R.id.map){
                    MapFragment fragment = new MapFragment();

                    //fragment.setArguments(b);
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.navigation_layout,fragment);
                    fragmentTransaction.commit();
                }

                return true;
            }
        });

        bottomNavigationView.setSelectedItemId(R.id.home1);
    }

}
