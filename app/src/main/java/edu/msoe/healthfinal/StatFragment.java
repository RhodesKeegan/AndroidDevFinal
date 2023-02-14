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

import edu.msoe.healthfinal.databinding.StatFragmentBinding;

public class StatFragment extends Fragment {

    private StatFragmentBinding binding;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = StatFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
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
