package edu.msoe.healthfinal;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import edu.msoe.healthfinal.databinding.FitnessFragmentBinding;
import io.realm.Realm;
import io.realm.RealmList;

public class FitnessFragment extends Fragment implements SensorEventListener {
    private FitnessFragmentBinding binding;
    private int workoutIndex = 0;
    private static final double HEAT_MULTIPLIER = 1.3;
    private Sensor ambientTemp;
    private SensorManager sensorManager;
    private float currentAmbientTemp;
    private static final int NOTIFICATION_ID = 19102193;
    private static final int POST_NOTIFICATIONS = 1;
    private boolean halfWay = false;
    private List<HashMap<Exercise, Integer>> exerciseWeightsUsed = new ArrayList<>();


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {


        binding = FitnessFragmentBinding.inflate(inflater, container, false);


        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        ambientTemp = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);


        Workout todayWorkout = initializeWorkout();

        List<Exercise> exercises = todayWorkout.getExercises();

        String name = exercises.get(workoutIndex).getName();
        int sets = exercises.get(workoutIndex).getSets();
        int reps = exercises.get(workoutIndex).getReps();
        workoutIndex++;



        binding.currentWorkout.setText(name + "\n"+sets+"x"+reps);

        binding.exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(workoutIndex == exercises.size()){
                    binding.currentWorkout.setText("You completed all of today's workouts!");
                    binding.exerciseButton.setEnabled(false);
                    promptUserToDrink(todayWorkout);
                    binding.weightUsedExercise.setVisibility(View.INVISIBLE);
                    insertToDB(exercises.size());
                }else{
                    String name = exercises.get(workoutIndex).getName();
                    int sets = exercises.get(workoutIndex).getSets();
                    int reps = exercises.get(workoutIndex).getReps();
                    binding.currentWorkout.setText(name + "\n"+sets+"x"+reps);

                    int weightUsed = Integer.parseInt(binding.weightUsedExercise.getText().toString());
                    HashMap<Exercise, Integer> weights = new HashMap<>();
                    weights.put(exercises.get(workoutIndex), weightUsed);
                    exerciseWeightsUsed.add(weights);

                    binding.weightUsedExercise.setText("");



                    workoutIndex++;
                }
                if(!halfWay &&workoutIndex > exercises.size()/2){

                    promptUserToDrink(todayWorkout);
                    halfWay = true;
                }
            }
        });



        return binding.getRoot();

    }

    private void insertToDB(int exercisesCompleted){
        Realm realm = Realm.getDefaultInstance();
        RealmList<WeightList> weightLists = new RealmList<>();
        Date date = new Date();
        int id = (int)System.currentTimeMillis();
        double caloriesBurned = 0;
        for(HashMap<Exercise, Integer> exerciseDetails: exerciseWeightsUsed){
            for(Exercise e: exerciseDetails.keySet()){
                caloriesBurned+=e.getCaloriesBurned();
                weightLists.add(new WeightList(e.getName(), exerciseDetails.get(e)));
            }
        }

        WorkoutSchema schema = new WorkoutSchema(id, date, caloriesBurned, weightLists, exercisesCompleted);


        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(schema);
            }
        });
    }



    private void promptUserToDrink(Workout todayWorkout){
        String message;
        if(currentAmbientTemp > 32.0){
            message = "It's currently a hot one out there, make sure you get extra water!" +
                    "\n Drink "+ (todayWorkout.getWaterIntake() * HEAT_MULTIPLIER)/2 + " liters";

        }else{
            message = "It's time to drink some water, drink " +
                    (todayWorkout.getWaterIntake())/2 + " liters right now";
        }

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS)
                ==PackageManager.PERMISSION_GRANTED){
            showNotification(message, new Intent(getContext(), FitnessFragment.class), getContext());

        }
    }


    private Workout initializeWorkout() {
        Workout workout1 = null;
        try{
            List<Exercise> exerciseList = new ArrayList<>();


            JSONObject jsonObject = new JSONObject(readFromJSONAsset());
            JSONObject workout = jsonObject.getJSONObject("workout");
            JSONArray exercises = workout.getJSONArray("exercises");

            for(int i=0; i<exercises.length(); i++){
                JSONObject exerciseObject = exercises.getJSONObject(i);
                String name = exerciseObject.getString("name");
                int sets = exerciseObject.getInt("sets");
                int reps = exerciseObject.getInt("reps");
                double caloriesBurned = exerciseObject.getDouble("calories");
                exerciseList.add(new Exercise(name, sets, reps, caloriesBurned));
            }

            double water = workout.getDouble("water");

            workout1 = new Workout(exerciseList, water);


        }catch (JSONException e){
            e.printStackTrace();
        }
        return workout1;
    }

    private void showNotification(String message, Intent intent, Context context) {

        PendingIntent pendingIntent = PendingIntent.getActivity(context, NOTIFICATION_ID, intent, PendingIntent.FLAG_IMMUTABLE);
        String CHANNEL_ID = "channel_name";
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.notification_icon)
                .setContentTitle("Water Reminder!")
                .setContentText(message)
                .setAutoCancel(true)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        CharSequence name = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel mChannel;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel(CHANNEL_ID, name, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());


    }

    @Override
    public void onResume() {
        super.onResume();

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED){
            getPostNotificationPermission();
        }

        sensorManager.registerListener(this, ambientTemp, SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void getPostNotificationPermission(){

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.POST_NOTIFICATIONS)
                != PackageManager.PERMISSION_GRANTED){
            if(shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)){
                Toast.makeText(getContext(), "We would like to send you notifications pretty please", Toast.LENGTH_LONG).show();
            }
            requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, POST_NOTIFICATIONS);
        }

    }

    @Override
    public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    private String readFromJSONAsset(){
        String json = null;
        try {
            InputStream is = requireContext().getAssets().open("workouts.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settings) {
            NavHostFragment.findNavController(FitnessFragment.this)
                    .navigate(R.id.fitness_to_settings);
        } else if (item.getItemId() == R.id.nutrition) {
            NavHostFragment.findNavController(FitnessFragment.this)
                    .navigate(R.id.fitness_to_nutrition);
        } else if (item.getItemId() == R.id.stats) {
            NavHostFragment.findNavController(FitnessFragment.this)
                    .navigate(R.id.fitness_to_stat);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE){
            currentAmbientTemp = event.values[0];
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
