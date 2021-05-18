package com.mygdx.game;

import com.badlogic.gdx.Gdx;

import java.util.HashMap;
import java.util.Random;

public class Fleet extends Troop {
    private int force;
    private Region region;
    private int health;
    private float x, y;
    private float radius;
    private HashMap<String, Integer> divisions;
    private Troop target;
    private boolean alive;
    private Country country;

    public Fleet(int force, int health) {
        this.force = force;
        this.health = health;
        divisions = new HashMap<>();
        region = null;
        alive = true;
        target = null;
        radius = Gdx.graphics.getWidth()/25f;
    }

    public HashMap<String, Integer> getDivisions() {
        return divisions;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public int getHealth() {
        return health;
    }

    public void place(Region region, float x, float y) {
        this.region = region;
        this.x = x;
        this.y = y;
        radius = Gdx.graphics.getWidth()/25f;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isClicked(float tx, float ty) {
        return ((x <= tx) && (x + radius >= tx) &&
                (y <= ty) && (y + radius >= ty));
    }

    @Override
    public Region getRegion() {
        return region;
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

    public int getForce() {
        force = 0;
        for (String division: divisions.keySet()) {
            switch (division) {
                case "destroyer":
                    force += divisions.get(division)* Values.DESTROYER_FORCE;
                    break;
                case "cruiser":
                    force += divisions.get(division)* Values.CRUISERS_FORCE;
                    break;
                case "submarine":
                    force += divisions.get(division)* Values.SUBMARINES_FORCE;
                    break;
            }
        }
        return force;
    }

    public void battle() {
        float damage = target.getForce();
        if (damage >= health) {
            alive = false;
            region.getTroops().remove(this);
        }
        else {
            health -= damage;
            while (damage > 0) {
                if (divisions.size() > 0) {
                    Random randomDivision = new Random();
                    Object[] divisionNames = divisions.keySet().toArray();
                    String division = (String) divisionNames[randomDivision.nextInt(divisions.size())];
                    int hp = 0, total = divisions.get(division);
                    switch (division) {
                        case "destroyer":
                            hp = total * Values.DESTROYER_HEALTH;
                            break;
                        case "cruiser":
                            hp = total * Values.CRUISERS_HEALTH;
                            break;
                        case "submarine":
                            hp = total * Values.SUBMARINES_HEALTH;
                            break;
                    }
                    if (damage > hp)
                        divisions.remove(division);
                    else {
                        divisions.put(division, (int) (total - damage / hp * total));
                    }
                    damage -= hp;
                }
                else {
                    alive = false;
                    break;
                }
            }
        }
        if (!target.isAlive())
            target = null;

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
        return region.isWaterAccess() && region.getCountryDivisions(country.getName()) < Values.MAX_DIVISIONS;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }
}
