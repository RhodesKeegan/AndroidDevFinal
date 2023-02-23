package edu.msoe.healthfinal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class WorkoutSchema  extends RealmObject {
    @PrimaryKey private int id;
    private Date date;
    private double caloriesBurned;
    private RealmList<WeightList> workoutWeights;
    private int exercisesCompleted;

    public WorkoutSchema(int id, Date date, double caloriesBurned, RealmList<WeightList> workoutWeights, int exercisesCompleted){
        this.caloriesBurned = caloriesBurned;
        this.date = date;
        this.id = id;
        this.workoutWeights = workoutWeights;
        this.exercisesCompleted = exercisesCompleted;
    }

    public WorkoutSchema(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(double caloriesBurned) {
        this.caloriesBurned = caloriesBurned;
    }

    public RealmList<WeightList> getWeightUsedForExercise() {
        return workoutWeights;
    }

    public int getExercisesCompleted() {
        return exercisesCompleted;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setExerciseDone(RealmList<WeightList> workoutWeights) {
        this.workoutWeights = workoutWeights;
    }

    public void setExercisesCompleted(int exercisesCompleted) {
        this.exercisesCompleted = exercisesCompleted;
    }

    @Override
    public String toString() {

        String workoutNames= "";
        for(WeightList weightList: workoutWeights){
            workoutNames+=weightList.getWorkout() + " weight used: " + weightList.getWeightUsed() + " ,";
        }

        return "Workout " + id + ": {date completed: " + date +
                ", total burned calories: " + caloriesBurned +
                ", exercises completed: " + exercisesCompleted +
                ", workouts done:[" + workoutNames + "]";
    }
}
