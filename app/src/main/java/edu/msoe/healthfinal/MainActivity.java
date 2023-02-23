package edu.msoe.healthfinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import edu.msoe.healthfinal.databinding.ActivityMainBinding;
import io.realm.Realm;

public class MainActivity extends AppCompatActivity {



    private SharedPreferences preferences;
    private ActivityMainBinding binding;
    private AppBarConfiguration appBarConfiguration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        preferences = getSharedPreferences("edu.msoe.healthfinal", MODE_PRIVATE);
//        preferences.edit().putBoolean("firstRun", true).apply();
//        Realm instance = Realm.getDefaultInstance();
//        instance.executeTransaction(new Realm.Transaction() {
//            @Override
//            public void execute(Realm realm) {
//                realm.deleteAll();
//            }
//        });


    }

    @Override
    protected void onResume(){
        super.onResume();

        if(preferences.getBoolean("firstRun", true)){
            startActivity(new Intent(this, FirstStartActivity.class));


        }

    }



    @Override
    public boolean onSupportNavigateUp(){
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            return NavigationUI.navigateUp(navController, appBarConfiguration)
                    || super.onSupportNavigateUp();

    }
}
