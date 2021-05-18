package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;

public class Science {
    private int points, pointsPD;
    private ArrayList<String> technologies;

    public Science() {
        points = Values.MINISTRY_POINTS;
        pointsPD = Values.MINISTRY_POINTS_PD;
        technologies = new ArrayList<>();
    }

    public ArrayList<String> getTechnologies() {
        return technologies;
    }

    public void setTechnologies(ArrayList<String> technologies) {
        this.technologies = technologies;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    public void setPointsPD(int pointsPD) {
        this.pointsPD = pointsPD;
    }

    public int getPointsPD() {
        return pointsPD;
    }



}
