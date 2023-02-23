package edu.msoe.healthfinal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import edu.msoe.healthfinal.databinding.StatFragmentBinding;
import io.realm.Realm;
import io.realm.RealmResults;

public class StatFragment extends Fragment {

    private StatFragmentBinding binding;
    private List<WorkoutSchema> workoutData = new ArrayList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = StatFragmentBinding.inflate(inflater, container, false);

        RecyclerView myRecyclerView = binding.recyclerView;

        myRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        RecyclerAdapter adapter = new RecyclerAdapter(workoutData);
        myRecyclerView.setAdapter(adapter);


        readData(adapter);


        return binding.getRoot();
    }


    private void readData(RecyclerAdapter adapter){
        Realm instance = Realm.getDefaultInstance();
        RealmResults<WorkoutSchema> workoutSchemaRealmResults = instance.where(WorkoutSchema.class).findAll();

//        if(workoutSchemaRealmResults.size() == 0){
//
//        }

        for(WorkoutSchema schema: workoutSchemaRealmResults){
            workoutData.add(schema);
            adapter.notifyItemInserted(workoutData.size()-1);
        }
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
            NavHostFragment.findNavController(StatFragment.this)
                    .navigate(R.id.stat_to_settings);
        } else if (item.getItemId() == R.id.nutrition) {
            NavHostFragment.findNavController(StatFragment.this)
                    .navigate(R.id.stat_to_nutrition);
        } else if (item.getItemId() == R.id.fitness) {
            NavHostFragment.findNavController(StatFragment.this)
                    .navigate(R.id.stat_to_nutrition);
        }
        return super.onOptionsItemSelected(item);
    }



}
