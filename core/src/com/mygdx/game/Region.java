package com.mygdx.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Region {

    private float x1, y1, x2, y2;
    private String name, countryName, prevCountryName;
    private long population;
    private HashMap<String, Float> resources, extractedResources;
    private ArrayList<ResourceBuilding> resourceBuildings;
    private HashMap<String, Building> buildings;
    private ArrayList<Troop> troops;
    private boolean waterAccess;
    private boolean landAccess;
    private int force;
    private boolean redrawBorders;
    private Country country;


    public Region(String name, String countryName) {
        this.name = name;
        prevCountryName = countryName;
        this.countryName = countryName;
        resources = new HashMap<>();
        extractedResources = new HashMap<>();
        resourceBuildings = new ArrayList<>();
        buildings = new HashMap<>();
        troops = new ArrayList<>();
        force = 0;
        redrawBorders = false;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public int getForce() {
        force = 0;
        for (Troop troop: troops) {
            if (troop.getCountry().getName().equals(countryName))
                force += troop.getForce();
        }
        return force;
    }

    public void setWaterAccess(boolean waterAccess) {
        this.waterAccess = waterAccess;
    }

    public boolean isWaterAccess() {
        return waterAccess;
    }

    public boolean isLandAccess() {
        return landAccess;
    }

    public void setLandAccess(boolean landAccess) {
        this.landAccess = landAccess;
    }

    public int getCountryDivisions(String country) {
        int divisions = 0;
        for (Troop troop: troops)
            if (troop.getCountry().getName().equals(country))
                divisions++;
        return divisions;
    }

    public void checkCountry(Country country1, Country country2, Country country3) {
        int div1 = getCountryDivisions(country1.getName());
        int div2 = getCountryDivisions(country2.getName());
        int div3 = getCountryDivisions(country3.getName());
        Country newCountry = country;
        int maxDiv = getCountryDivisions(countryName);
        if (div1 > div2) {
            maxDiv = div1;
            newCountry = country1;
        }
        else if (div2 > div1) {
            maxDiv = div2;
            newCountry = country2;
        }
        if (div3 > maxDiv) {
            maxDiv = div3;
            newCountry = country3;
        }
        if (!newCountry.getName().equals(countryName)) {
            redrawBorders = true;
            country.getRegions().remove(this);
            prevCountryName = countryName;
            countryName = newCountry.getName();
            country = newCountry;
            country.getRegions().add(this);
        }

    }

    public void extractResources() {
        resourceBuildings = new ArrayList<>();
        for (Building building: buildings.values()) {
            if (building instanceof ResourceBuilding) {
                ArrayList<String> producingRes =
                        new ArrayList(Arrays.asList(
                                new String[]{"steel", "canned_food", "household_goods",
                                        "pigIron", "plastic", "tank", "bomb_preparation",
                                        "artillery", "ship", "equipment", "plane", "weapon"}));
                if (!producingRes.contains(((ResourceBuilding) building).getReceivedRes()))
                    resourceBuildings.add((ResourceBuilding) building);
            }
        }

        for (ResourceBuilding resourceBuilding: resourceBuildings) {
            String key = resourceBuilding.getReceivedRes();
            String[] reqRes = resourceBuilding.getRequiredRes();
            float value = 0;
            for (String k: reqRes) {
                value += resourceBuilding.getEfficiency();
                if (resources.get(k) >= value) {
                    extractedResources.put(key, value);
                    resources.put(k, resources.get(k) - value);
                }
            }
        }
    }

    public void Build(Building building) {
        buildings.put(building.getName(), building);
        // Добавление промышленного здания
        if (building instanceof ResourceBuilding) {
            ArrayList producingRes =
                    new ArrayList(Arrays.asList("steel", "canned_food",
                            "household_goods", "pigIron", "plastic", "tank", "bomb_preparation",
                            "artillery", "ship", "equipment", "plane", "weapon"));
            if (producingRes.contains(((ResourceBuilding) building).getReceivedRes()))
                country.getIndustryBuildings().add((ResourceBuilding) building);
        }
        if (building instanceof MoneyBuilding) {
            country.setMoneyPd(country.getMoneyPd() +
                    building.getEfficiency());
        }
        if (country instanceof PlayerCountry) {
            PlayerCountry country = (PlayerCountry)this.country;
            if (building instanceof MinistryBuilding) {
                MinistryBuilding ministryBuilding = (MinistryBuilding) building;
                float points;
                switch (ministryBuilding.getMinistry()) {
                    case "minTruth":
                        points = country.getMinTruth().getPointsPD();
                        country.getMinTruth().setPointsPD((int) (points +
                                ministryBuilding.getEfficiency()));
                        break;
                    case "minLove":
                        points = country.getMinLove().getPointsPD();
                        country.getMinLove().setPointsPD((int) (points +
                                ministryBuilding.getEfficiency()));
                        break;
                    case "minPlenty":
                        points = country.getMinPlenty().getPointsPD();
                        country.getMinPlenty().setPointsPD((int) (points +
                                ministryBuilding.getEfficiency()));
                        break;
                    case "minPeace":
                        points = country.getMinPeace().getPointsPD();
                        country.getMinPeace().setPointsPD((int) (points +
                                ministryBuilding.getEfficiency()));
                        break;
                    case "thoughtPolice":
                        points = country.getThoughtPolice().getPointsPD();
                        country.getThoughtPolice().setPointsPD((int) (points +
                                ministryBuilding.getEfficiency()));
                        break;
                    case "science":
                        points = country.getScience().getPointsPD();
                        country.getScience().setPointsPD((int) (points +
                                ministryBuilding.getEfficiency()));
                        break;
                }
            }
        }
    }


    public ArrayList<ResourceBuilding> getResourceBuildings() {
        return resourceBuildings;
    }

    public HashMap<String, Building> getBuildings() {
        return buildings;
    }

    public void setCoordinates(float x1, float y1, float x2, float y2) {
        this.y1 = y1;
        this.y2 = y2;
        this.x1 = x1;
        this.x2 = x2;
    }

    public boolean clicked(float x, float y) {
        return this.x1 <= x && x <= this.x2 && this.y1 <= y && y <= this.y2;
    }

    public float getScaleX() {
        return Math.abs(this.x2 - this.x1);
    }

    public float getScaleY() {
        return Math.abs(this.y2 - this.y1);
    }

    public float getX1() {
        return this.x1;
    }

    public float getX2() {
        return this.x2;
    }

    public float getY1() {
        return this.y1;
    }

    public float getY2() {
        return this.y2;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public long getPopulation() {
        return this.population;
    }

    public String getName(){
        return this.name;
    }

    public String getCountryName() { return this.countryName;}

    public void addResource(String key, float value) {
        resources.put(key, value*Values.RESOURCE);
    }

    public HashMap<String, Float> getResources() {
        return resources;
    }

    public HashMap<String, Float> getExtractedResources() {
        return extractedResources;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public boolean isRedrawBorders() {
        return redrawBorders;
    }

    public void setRedrawBorders(boolean redrawBorders) {
        this.redrawBorders = redrawBorders;
    }

    public String getPrevCountryName() {
        return prevCountryName;
    }

    public Country getCountry() {
        return country;
    }

}
