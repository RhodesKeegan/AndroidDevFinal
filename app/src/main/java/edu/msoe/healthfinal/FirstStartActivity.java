package edu.msoe.healthfinal;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class FirstStartActivity extends Activity {

    private SharedPreferences preferences;

    @Override
    public void onCreate(Bundle savedInstances){
        super.onCreate(savedInstances);
        preferences = getSharedPreferences("edu.msoe.healthfinal", MODE_PRIVATE);
        setContentView(R.layout.first_start_activity);
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int width = metrics.widthPixels;
        int height = metrics.heightPixels;
        getWindow().setLayout((int) (width*.8), (int) (height*.6));

        setSpinners();

        Button submitButton = findViewById(R.id.submitButton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Spinner genderSpinner = findViewById(R.id.genderSpinner);

                Spinner sleepSpinner = findViewById(R.id.sleepSpinner);

                Spinner goalSpinner = findViewById(R.id.goalSpinner);


                EditText weightInput = findViewById(R.id.weightChoice);
                int weight = Integer.parseInt(weightInput.getText().toString());

                preferences.edit().putString("gender", genderSpinner.getSelectedItem().toString())
                        .putInt("weight", weight)
                        .putString("sleep", sleepSpinner.getSelectedItem().toString())
                        .putString("goals", goalSpinner.getSelectedItem().toString())
                        .putBoolean("firstRun", false)
                        .apply();

                finish();
            }
        });


    }

    private void setSpinners(){
        String[] genderArray = getResources().getStringArray(R.array.gender);
        String[] sleepArray = getResources().getStringArray(R.array.sleep);
        String[] goalArray = getResources().getStringArray(R.array.goals);

        ArrayAdapter<String> genderAdapter = new ArrayAdapter<>
                (getApplicationContext(), android.R.layout.simple_spinner_item, genderArray);

        ArrayAdapter<String> sleepAdapter = new ArrayAdapter<>
                (getApplicationContext(), android.R.layout.simple_spinner_item, sleepArray);

        ArrayAdapter<String> goalAdapter = new ArrayAdapter<>
                (getApplicationContext(), android.R.layout.simple_spinner_item, goalArray);

        genderAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sleepAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        goalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Spinner genderSpinner = findViewById(R.id.genderSpinner);
        Spinner sleepSpinner = findViewById(R.id.sleepSpinner);
        Spinner goalSpinner = findViewById(R.id.goalSpinner);

        genderSpinner.setAdapter(genderAdapter);
        sleepSpinner.setAdapter(sleepAdapter);
        goalSpinner.setAdapter(goalAdapter);
    }

}
