package com.mygdx.game;

import java.util.HashMap;

public class Troop{
    private Region region;
    private int health;
    private int force;
    private float x, y, radius;
    private HashMap<String, Integer> divisions;
    private Troop target;
    private boolean alive;
    private Country country;

    public Troop() {

    }

    public void place(Region region, float x, float y) {
        this.region = region;
        this.x = x;
        this.y = y;
        divisions = new HashMap<>();
        region = null;
        target = null;
        alive = true;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getHealth() {
        return health;
    }

    public boolean isClicked(float tx, float ty) {
        return ((x <= tx) && (x + radius >= tx) &&
                (y <= ty) && (y + radius >= ty));
    }

    public HashMap<String, Integer> getDivisions() {
        return divisions;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public void checkForEnemies() {
        if (target == null) {
            for (Troop troop : region.getTroops()) {
                if (country.getEnemies().containsKey(troop.getCountry().getName()))
                    target = troop;
            }
        }
    }

    public boolean hasTarget() {
        return target != null;
    }

    public boolean isAlive() {
        return alive;
    }

    public void battle() {

    }

    public int getForce() {
        return force;
    }

    public void setTarget(Troop target) {
        this.target = target;
    }

    public void setForce(int force) {
        this.force = force;
    }

    public float getRadius() {
        return radius;
    }

    public void setDivisions(HashMap<String, Integer> divisions) {
        this.divisions = divisions;
    }

    public boolean canPlace(Region region) {
        return region.getCountryDivisions(country.getName()) < Values.MAX_DIVISIONS;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
