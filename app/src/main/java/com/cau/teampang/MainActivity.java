package com.cau.teampang;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.view.MenuItem;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bottom nav
        BottomNavigationView btnNav=findViewById(R.id.bottomNavigationView);
        btnNav.setOnNavigationItemSelectedListener(navListener);

        //Setting Home Fragment as main fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,new WhenFragment()).commit();



    }

    //Listener Nav Bar
    private BottomNavigationView.OnNavigationItemSelectedListener navListener = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.tab_calendar:
                            selectedFragment = new CalendarFragment();
                            break;
                        case R.id.tab_when:
                            selectedFragment = new WhenFragment();
                            break;
                        case R.id.tab_where:
                            selectedFragment = new WhereFragment();
                            break;
                    }
                    //Begin Transaction
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout, selectedFragment).commit();
                    return true;
                }
            };
}
