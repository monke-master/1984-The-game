package com.mygdx.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class Country {
    private ArrayList<Region> regions;
    private String name;
    private HashMap<String, Integer> extractingResources, producingResources, militaryResources,
            resourcesExtEff, forProduction;
    private int money, moneyPd;
    private ArrayList<ResourceBuilding> industryBuildings;
    private ArrayList<Troop> troops;
    private int force;
    private boolean alive;
    private HashMap<String, Country> enemies;
    private boolean offeringPeace, settlePeace;
    private Country offeringCountry;
    private boolean war;

    public Country(String name) {
        this.name = name;
        regions = new ArrayList<>();
        extractingResources = new HashMap<>();
        resourcesExtEff = new HashMap<>();
        producingResources = new HashMap<>();
        militaryResources = new HashMap<>();
        industryBuildings = new ArrayList<>();
        forProduction = new HashMap<>();
        troops = new ArrayList<>();
        enemies = new HashMap<>();
        offeringPeace = false;
        settlePeace = false;
        war = false;
    }



    // Геттеры

    public HashMap<String, Integer> getExtractingResources() {
        return extractingResources;
    }

    public HashMap<String, Integer> getResourcesExtEff() {
        return resourcesExtEff;
    }

    public ArrayList<ResourceBuilding> getIndustryBuildings() {
        return industryBuildings;
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

    public ArrayList<Region> getRegions() {
        return regions;
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

    public boolean isAlive() {
        return alive;
    }

    public HashMap<String, Country> getEnemies() {
        return enemies;
    }

    public boolean isOfferingPeace() {
        return offeringPeace;
    }

    public boolean isSettlePeace() {
        return settlePeace;
    }

    public Country getOfferingCountry() {
        return offeringCountry;
    }

    public HashMap<String, Integer> getForProduction() {
        return forProduction;
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

    public void setWar(boolean war) {
        this.war = war;
    }
}
