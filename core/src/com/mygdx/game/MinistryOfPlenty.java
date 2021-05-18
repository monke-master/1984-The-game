package com.mygdx.game;

public class MinistryOfPlenty extends Ministry {
    private float gasSaving;
    private float foodSaving;
    private float hhgSaving;
    private int workPoints, pointsPD;

    public MinistryOfPlenty() {
        this.pointsPD = Values.MINISTRY_POINTS_PD;
        this.workPoints = Values.MINISTRY_POINTS;
        this.gasSaving = 0;
        this.foodSaving = 0;
        this.hhgSaving = 0;
    }


    public float getFoodSaving() {
        return foodSaving;
    }

    public float getGasSaving() {
        return gasSaving;
    }

    public float getHhgSaving() {
        return hhgSaving;
    }

    public void setFoodSaving(float foodSaving) {
        this.foodSaving = foodSaving;
    }

    public void setGasSaving(float gasSaving) {
        this.gasSaving = gasSaving;
    }

    public void setHhgSaving(float hhgSaving) {
        this.hhgSaving = hhgSaving;
    }

    public void setPointsPD(int pointsPD) {
        this.pointsPD = pointsPD;
    }
}
