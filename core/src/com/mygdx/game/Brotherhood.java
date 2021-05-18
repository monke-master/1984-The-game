package com.mygdx.game;

// Повстанцы
public class Brotherhood {
    private float efficiency, efficiencyPd;

    public Brotherhood() {
        efficiency = Values.EFFICIENCY;
        efficiencyPd = Values.BROTHERHOOD_INC;
    }

    public float getEfficiencyPd() {
        return efficiencyPd;
    }

    public float getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(float efficiency) {
        this.efficiency = efficiency;
    }
}
