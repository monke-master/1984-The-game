package com.mygdx.game;

import com.badlogic.gdx.Gdx;

import java.util.ArrayList;
import java.util.HashMap;

public class PlayerCountry extends Country{
    private ArrayList<Region> regions;
    private long population, controlledPopulation, control;
    private String name;
    private Ministry minTruth, minLove, thoughtPolice, minPeace;
    private MinistryOfPlenty minPlenty;
    private Science science;
    private Brotherhood brotherhood;
    private float propagandaEff, fearEff, destabilizationEff, safetyEff, discontentEff;
    private HashMap<String, Integer> extractingResources, producingResources, militaryResources,
            resourcesExtEff, forProduction, forPopulation;
    private HashMap<String, Float> producingBonuses;
    private int money, moneyPd;
    private ArrayList<ResourceBuilding> industryBuildings;
    private int gasReq, foodReq, hhgReq;
    private ArrayList<Troop> troops;
    private int force;
    private boolean alive, win;
    private HashMap<String, Country> enemies;
    private boolean offeringPeace, settlePeace;
    private Country offeringCountry;

    public PlayerCountry(String name) {
        super(name);
        this.name = name;
        forPopulation = new HashMap<>();
        forProduction = new HashMap<>();
        regions = new ArrayList<>();
        extractingResources = new HashMap<>();
        resourcesExtEff = new HashMap<>();
        producingResources = new HashMap<>();
        militaryResources = new HashMap<>();
        industryBuildings = new ArrayList<>();
        producingBonuses = new HashMap<>();
        troops = new ArrayList<>();
        String[] resNames = {"oil", "gas", "iron", "food", "aluminum", "wood", "coal"};
        for (String key: resNames) {
            extractingResources.put(key, 0);
            forPopulation.put(key, 0);
            forProduction.put(key, 0);
        }
        resNames = new String[]{"canned_food", "household_goods", "steel", "pigIron", "plastic"};
        for (String key: resNames) {
            producingResources.put(key, 0);
            forPopulation.put(key, 0);
            forProduction.put(key, 0);
            producingBonuses.put(key, 1f);
        }
        resNames = new String[]{"weapon", "artillery", "plane", "tank", "ship"};
        for (String key: resNames) {
            militaryResources.put(key, 0);
            forPopulation.put(key, 0);
            forProduction.put(key, 0);
            producingBonuses.put(key, 1f);
        }

        minTruth = new Ministry();
        minLove = new Ministry();
        minPeace = new Ministry();
        minPlenty = new MinistryOfPlenty();
        science = new Science();
        brotherhood = new Brotherhood();
        thoughtPolice = new Ministry();
        alive = true;

        discontentEff = 0;


        enemies = new HashMap<>();
    }

    public long getControl() {
        return control;
    }


    public void calcPoints() {
        // Эффекты от министерств
        propagandaEff = minTruth.getEfficiency();
        fearEff = minLove.getEfficiency();
        safetyEff = thoughtPolice.getEfficiency();
        destabilizationEff = brotherhood.getEfficiency();

        // Перепись населения
        population = 0;
        for (Region region: regions)
            population += region.getPopulation();
        // Расчет контроля
        controlledPopulation = (long)(population*(propagandaEff + fearEff + safetyEff -
                destabilizationEff - discontentEff));
        control = (int)((float)(controlledPopulation)/population * 100);

        // Увеличение очков работы гос. учреждений
        science.setPoints(science.getPoints() + science.getPointsPD());
        minTruth.setPoints(minTruth.getPointsPD() + minTruth.getPoints());
        minLove.setPoints(minLove.getPointsPD() + minLove.getPoints());
        minPlenty.setPoints(minPlenty.getPoints() + minPlenty.getPointsPD());
        minPeace.setPoints(minPeace.getPoints() + minPeace.getPointsPD());
        thoughtPolice.setPoints(thoughtPolice.getPoints() + thoughtPolice.getPointsPD());
        brotherhood.setEfficiency(brotherhood.getEfficiencyPd()*brotherhood.getEfficiency());

        // Расчет потребления ресурсов
        gasReq = (int)((Values.GAS_CONSUMPTION - minPlenty.getGasSaving())*population);
        foodReq = (int)((Values.FOOD_CONSUMPTION - minPlenty.getFoodSaving())*population);
        hhgReq = (int)((Values.HHG_CONSUMPTION - minPlenty.getHhgSaving())*population);

        // Расчет добытых ресурсов
        String[] resNames = {"oil", "gas", "iron", "food", "aluminum", "wood", "coal",
                "canned_food", "household_goods", "steel", "pigIron", "plastic", "weapon",
                "artillery", "plane", "tank", "ship"};
        for (String key: resNames) {
            resourcesExtEff.put(key, 0);
            forPopulation.put(key, 0);
            forProduction.put(key, 0);
        }
        forPopulation.put("gas", gasReq);
        forPopulation.put("canned_food", foodReq);
        forPopulation.put("household_goods", hhgReq);
        for (Region region: regions) {
            region.extractResources();
            for (String key: region.getExtractedResources().keySet()) {
                extractingResources.put(key,
                        (int)(extractingResources.get(key) + region.getExtractedResources().get(key)));
                region.getExtractedResources().put(key, 0f);
            }
            for (ResourceBuilding resourceBuilding: region.getResourceBuildings()) {
                String key = resourceBuilding.getReceivedRes();
                resourcesExtEff.put(key, resourcesExtEff.get(key) + resourceBuilding.getEfficiency());
            }
        }
        money += moneyPd;
        for (ResourceBuilding building: industryBuildings) {
            int value = building.getEfficiency();
            boolean canProduce = true;
            for (String resource: building.getRequiredRes()) {
                forProduction.put(resource, value + forProduction.get(resource));
                if (extractingResources.containsKey(resource)) {
                    if (value > extractingResources.get(resource)) {
                        canProduce = false;
                    }
                } else {
                    if (value > producingResources.get(resource)) {
                        canProduce = false;
                    }
                }
            }
            float bonus = producingBonuses.get(building.getReceivedRes());
            if (canProduce) {
                for (String resource: building.getRequiredRes()) {
                    if (extractingResources.containsKey(resource)) {
                        extractingResources.put(resource,
                                (int) (extractingResources.get(resource) - value));
                    } else
                        producingResources.put(resource,
                                (int) (producingResources.get(resource) - value));
                }
                String receivedRes = building.getReceivedRes();
                float temp;
                if (producingResources.containsKey(receivedRes)) {
                    temp = producingResources.get(receivedRes);
                    producingResources.put(receivedRes, (int) (temp + value*bonus));
                } else {
                    temp = militaryResources.get(receivedRes);
                    militaryResources.put(receivedRes, (int) (temp + value*bonus));
                }
                resourcesExtEff.put(building.getReceivedRes(), (int)(value*bonus));
            }

        }
        if (extractingResources.get("gas") >= gasReq) {
            extractingResources.put("gas", (int) (extractingResources.get("gas") - gasReq));
        }
        else
            discontentEff += 0.01f;
        if (producingResources.get("canned_food") >= foodReq) {
            producingResources.put("canned_food", (int)(producingResources.get("canned_food") - foodReq));
        }
        else
            discontentEff += 0.1f;
        if (producingResources.get("household_goods") >= foodReq) {
            producingResources.put("canned_food", (int)(producingResources.get("household_goods") - hhgReq));
        }
        else
            discontentEff += 0.1f;

        force = 0;
        for (Troop troop: troops)
            force += troop.getForce();

        alive = regions.size() > 0 && control > 0;
        win = control >= 100 || regions.size() == Map.regions.size();
    }


    // Геттеры
    public HashMap<String, Integer> getForPopulation() {
        return forPopulation;
    }

    public HashMap<String, Integer> getForProduction() {
        return forProduction;
    }

    public HashMap<String, Integer> getExtractingResources() {
        return extractingResources;
    }

    public HashMap<String, Integer> getResourcesExtEff() {
        return resourcesExtEff;
    }

    public ArrayList<ResourceBuilding> getIndustryBuildings() {
        return industryBuildings;
    }

    public Ministry getMinTruth() {
        return minTruth;
    }

    public Ministry getMinLove() {
        return minLove;
    }

    public Ministry getThoughtPolice() {
        return this.thoughtPolice;
    }

    public MinistryOfPlenty getMinPlenty() {
        return minPlenty;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public int getMoneyPd() {
        return moneyPd;
    }

    public HashMap<String, Integer> getProducingResources() {
        return producingResources;
    }

    public Science getScience() {
        return science;
    }

    public ArrayList<Region> getRegions() {
        return regions;
    }

    public long getPopulation() {
        return population;
    }

    public HashMap<String, Integer> getMilitaryResources() {
        return militaryResources;
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public int getForce() {
        return force;
    }

    public HashMap<String, Country> getEnemies() {
        return enemies;
    }

    public Ministry getMinPeace() {
        return minPeace;
    }

    public HashMap<String, Float> getProducingBonuses() {
        return producingBonuses;
    }

    public boolean isOfferingPeace() {
        return offeringPeace;
    }

    public boolean isSettlePeace() {
        return settlePeace;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isWin() { return win;}

    public Country getOfferingCountry() {
        return offeringCountry;
    }

    // Сеттеры
    public void setMoneyPd(int moneyPd) {
        this.moneyPd = moneyPd;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setOfferingPeace(boolean offeringPeace) {
        this.offeringPeace = offeringPeace;
    }

    public void setSettlePeace(boolean settlePeace) {
        this.settlePeace = settlePeace;
    }

    public void setOfferingCountry(Country offeringCountry) {
        this.offeringCountry = offeringCountry;
    }
}
