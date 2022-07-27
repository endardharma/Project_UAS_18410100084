package id.undika.project_uas;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

import id.undika.project_uas.fragment.FragAbout;
import id.undika.project_uas.fragment.FragJadwal;
import id.undika.project_uas.fragment.FragMk;

public class MainActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener {

    FragJadwal fragJadwal = new FragJadwal();
    FragAbout fragmentAbout = new FragAbout();
    FragMk fragMk = new FragMk();
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.menu_jadwal);
        bottomNavigationView.setSelectedItemId(R.id.menu_matkul);
        bottomNavigationView.setSelectedItemId(R.id.about);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_jadwal:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, fragJadwal).commit();
                return true;
            case R.id.menu_matkul:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, fragMk).commit();
                return true;
            case R.id.about:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, fragmentAbout).commit();
                return true;
        }
        return false;
    }
}