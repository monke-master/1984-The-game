package com.mygdx.game;

import java.util.ArrayList;

public class Ministry {
    private float efficiency;
    private int points, pointsPD;
    private ArrayList<String> decrees;

    public Ministry() {
        efficiency = Values.EFFICIENCY;
        points = Values.MINISTRY_POINTS;
        pointsPD = Values.MINISTRY_POINTS_PD;
        decrees = new ArrayList<>();
    }

    public ArrayList<String> getDecrees() {
        return this.decrees;
    }

    public int getPoints() {
        return points;
    }

    public int getPointsPD() {
        return pointsPD;
    }

    public void setPoints(int workPoints) {
        this.points = workPoints;
    }

    public void setPointsPD(int pointsPD) {
        this.pointsPD = pointsPD;
    }

    public float getEfficiency() {
        return this.efficiency;
    }

    public void setEfficiency(float efficiency) {
        this.efficiency = efficiency;
    }
}
