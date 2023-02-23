package edu.msoe.healthfinal;

import io.realm.RealmObject;

public class WeightList extends RealmObject {
    private String name;
    private int weightUsed;

    public WeightList(String name, int weightUsed){
        this.weightUsed = weightUsed;
        this.name = name;
    }

    public int getWeightUsed() {
        return weightUsed;
    }

    public void setWeightUsed(int weightUsed) {
        this.weightUsed = weightUsed;
    }

    public void setWorkout(String name) {
        this.name = name;
    }

    public String getWorkout() {
        return name;
    }

    public WeightList(){

    }

}
