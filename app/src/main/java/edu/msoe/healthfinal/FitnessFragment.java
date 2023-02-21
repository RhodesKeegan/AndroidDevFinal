package edu.msoe.healthfinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import edu.msoe.healthfinal.databinding.FitnessFragmentBinding;

public class FitnessFragment extends Fragment {
    private FitnessFragmentBinding binding;
    private int workoutIndex = 0;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FitnessFragmentBinding.inflate(inflater, container, false);

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
                }else{
                    String name = exercises.get(workoutIndex).getName();
                    int sets = exercises.get(workoutIndex).getSets();
                    int reps = exercises.get(workoutIndex).getReps();
                    binding.currentWorkout.setText(name + "\n"+sets+"x"+reps);
                    workoutIndex++;
                }
            }
        });



        return binding.getRoot();

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
}
