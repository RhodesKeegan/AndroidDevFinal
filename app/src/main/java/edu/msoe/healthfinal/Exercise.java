package edu.msoe.healthfinal;

public class Exercise {
    private String name;
    private int sets;
    private int reps;
    private double caloriesBurned;

    public Exercise(String name, int sets, int reps, double caloriesBurned){
        this.name = name;
        this.caloriesBurned = caloriesBurned;
        this.sets = sets;
        this.reps = reps;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public int getReps() {
        return reps;
    }

    public int getSets() {
        return sets;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }


}
