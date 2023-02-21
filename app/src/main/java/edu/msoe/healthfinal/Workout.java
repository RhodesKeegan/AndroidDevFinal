package edu.msoe.healthfinal;

import java.util.List;

public class Workout {
    private List<Exercise> exercises;

    private double waterIntake;

    /**
     * Takes in a list of exercises for a workout and the expected calories burned
     * along with the amount of water needed to stay hydrated
     * @param exercises exercises for a given workout
     * @param waterIntake amount of water to drink to stay hydrated in liters
     */
    public Workout(List<Exercise> exercises, double waterIntake){
        this.exercises = exercises;

        this.waterIntake = waterIntake;
    }


    public double getWaterIntake() {
        return waterIntake;
    }


    public void setWaterIntake(double waterIntake) {
        this.waterIntake = waterIntake;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }
}
