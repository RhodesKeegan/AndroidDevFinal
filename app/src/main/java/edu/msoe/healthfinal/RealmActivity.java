package edu.msoe.healthfinal;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmActivity extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        Realm.init(this);
        String realmName = "Health Data";
        RealmConfiguration config = new RealmConfiguration.Builder().allowQueriesOnUiThread(true)
                .allowWritesOnUiThread(true)
                .name(realmName).build();
        Realm.setDefaultConfiguration(config);
    }

}
