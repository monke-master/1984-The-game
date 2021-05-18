package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

// Компьютерный игрок
public class ComputerCountry extends Country {

    private ArrayList<Region> regions;
    private String name;
    private HashMap<String, Integer> extractingResources, producingResources, militaryResources;
    private HashMap<String, Integer> resourcesExtEff, forProduction;
    private int money, moneyPd;
    private ArrayList<ResourceBuilding> industryBuildings;
    private ArrayList<Troop> troops;
    private PlayerCountry player;
    private ComputerCountry computer;
    private Region targetBuildRegion;
    private int force, targetForce;
    private Building targetBuilding;
    private boolean targetComplete, war, buildingBuilt;
    private int currentBuilding;
    private I18NBundle buildings_bundle;
    private Troop targetTroop;
    private HashMap<String, Integer> targetRes;
    private boolean hasTargetTroop, disperseTroops;
    private Country targetCountry;
    private HashMap<String, Country> enemies;
    private boolean offeringPeace, settlePeace, triedPeace;
    private Country offeringCountry;
    private boolean alive;

    public ComputerCountry(String name) {
        // Стартовые значения
        super(name);
        this.name = name;
        regions = new ArrayList<>();
        extractingResources = new HashMap<>();
        resourcesExtEff = new HashMap<>();
        producingResources = new HashMap<>();
        militaryResources = new HashMap<>();
        forProduction = new HashMap<>();
        industryBuildings = new ArrayList<>();
        targetRes = new HashMap<>();
        troops = new ArrayList<>();
        String[] resNames = {"iron", "aluminum", "coal"};
        for (String key: resNames) {
            extractingResources.put(key, 0);
            forProduction.put(key, 0);
        }
        resNames = new String[]{"steel", "pigIron"};
        for (String key: resNames) {
            producingResources.put(key, 0);
            forProduction.put(key, 0);
        }
        resNames = new String[]{"weapon", "artillery", "plane", "tank", "ship"};
        for (String key: resNames) {
            militaryResources.put(key, 0);
        }

        for (Region region: Map.regions) {
            if (region.getCountryName().equals(name))
                regions.add(region);
        }

        alive = true;
        war = false;
        buildingBuilt = true;
        currentBuilding = 0;
        money = Values.MONEY;
        moneyPd = Values.MONEY_PD;
        hasTargetTroop = false;
        disperseTroops = false;
        triedPeace = false;

        buildings_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/buildings"));

        enemies = new HashMap<>();
    }

    public void setPlayer(PlayerCountry player) {
        this.player = player;
    }

    public void setComputer(ComputerCountry computer) {
        this.computer = computer;
    }

    public ArrayList<Troop> getTroops() {
        return troops;
    }

    public void declareWar(Country country) {
        war = true;
        enemies.put(country.getName(), country);
        country.setWar(true);
        country.getEnemies().put(name, this);
    }

    private void offerPeace(Country country) {
        country.setOfferingPeace(true);
        country.setOfferingCountry(this);
        triedPeace = true;
    }

    public void setTarget() {
        // Подсчет очков
        countPoints();
        // Если компьютер жив
        if (alive) {
            // Выбрать самую слабую страну
            targetCountry = findTargetCountry();
            // Если предложили выгодный мир
            if (offeringPeace && force <= (int)Values.MIN_ADVANTAGE*offeringCountry.getForce() ) {
                war = false;
                offeringPeace = false;
            }
            // Если не война, то поставить цель - набрать мощь больше врага
            if (!war) {
                targetForce = (int) (targetCountry.getForce() * Values.MIN_ADVANTAGE);
                targetComplete = force > targetForce;
            }
            // Если цель достигнута, объявить войну
            if (targetComplete && !war) {
                declareWar(targetCountry);
            }
            // Если проигрывает, то попросить мир
            if (war && targetCountry.getForce() >= force && !triedPeace)
                offerPeace(targetCountry);
            // Если враг побежден, выходим из состояния войны
            if (!targetCountry.isAlive())
                war = false;
            // Если мир принят, то заканчиваем войну и перемещаем войска
            if (triedPeace && targetCountry.isSettlePeace()) {
                war = false;
                disperseTroops = true;
                enemies.remove(targetCountry.getName());
                targetCountry.getEnemies().remove(name);
            }
            // Поставить цель-войска
            if (!hasTargetTroop) {
                Troop maxForceTroop1 = new Troop(), maxForceTroop2 = new Troop();
                Troop maxForceTroop;
                maxForceTroop1.setForce(0);
                maxForceTroop2.setForce(0);
                if (player.getTroops().size() > 0) {
                    maxForceTroop1 = player.getTroops().get(0);
                    for (Troop troop : player.getTroops()) {
                        if (troop.getForce() > maxForceTroop1.getForce())
                            maxForceTroop1 = troop;
                    }
                }
                if (computer.getTroops().size() > 0) {
                    maxForceTroop2 = computer.getTroops().get(0);
                    for (Troop troop : computer.getTroops()) {
                        if (troop.getForce() > maxForceTroop2.getForce())
                            maxForceTroop2 = troop;
                    }
                }
                if (maxForceTroop1.getForce() > 0 || maxForceTroop2.getForce() > 0) {
                    if (maxForceTroop1.getForce() > maxForceTroop2.getForce())
                        maxForceTroop = maxForceTroop1;
                    else
                        maxForceTroop = maxForceTroop2;
                    targetRes.clear();
                    if (maxForceTroop instanceof Army) {
                        if (maxForceTroop.getDivisions().containsKey("infantry"))
                            targetRes.put("weapon", maxForceTroop.getDivisions().get("infantry"));
                        if (maxForceTroop.getDivisions().containsKey("tank"))
                            targetRes.put("tank", maxForceTroop.getDivisions().get("tank"));
                        if (maxForceTroop.getDivisions().containsKey("artillery"))
                            targetRes.put("artillery", maxForceTroop.getDivisions().get("artillery"));
                        targetTroop = new Army(maxForceTroop.getForce(), maxForceTroop.getHealth());
                    } else if (maxForceTroop instanceof Fleet) {
                        int total = 0;
                        for (Integer value : maxForceTroop.getDivisions().values())
                            total += value;
                        targetRes.put("ship", total);
                        targetTroop = new Fleet(maxForceTroop.getForce(), maxForceTroop.getHealth());
                    } else if (maxForceTroop instanceof Squadron) {
                        int total = 0;
                        for (Integer value : maxForceTroop.getDivisions().values())
                            total += value;
                        targetRes.put("plane", total);
                        targetTroop = new Squadron(maxForceTroop.getForce(), maxForceTroop.getHealth());
                    }
                    targetTroop.setDivisions(maxForceTroop.getDivisions());
                    hasTargetTroop = true;
                }
            }
            // Разместить максимально сильные войска, если они не требуют ресурсы цели
            Random random = new Random();
            int troopType = random.nextInt(3) + 1;
            int troopForce, troopHealth;
            int infantry = 0, tanks = 0, artillery = 0;
            if (militaryResources.get("weapon") >= Values.MIN_INFANTRY &&
                    !targetRes.containsKey("weapon"))
                infantry = militaryResources.get("weapon");
            if (militaryResources.get("tank") >= Values.MIN_TANKS &&
                    !targetRes.containsKey("tank"))
                tanks = militaryResources.get("tank");
            if (militaryResources.get("artillery") >= Values.MIN_ARTILLERY &&
                    !targetRes.containsKey("artillery"))
                artillery = militaryResources.get("artillery");
            troopForce = infantry * Values.INFANTRY_FORCE + tanks * Values.TANK_FORCE +
                    artillery * Values.ARTILLERY_FORCE;
            troopHealth = infantry * Values.INFANTRY_HEALTH + tanks * Values.TANK_HEALTH +
                    artillery * Values.ARTILLERY_HEALTH;
            if (troopForce > 0) {
                Army army = new Army(troopForce, troopHealth);
                if (infantry > 0) {
                    army.getDivisions().put("infantry", infantry);
                    int temp = militaryResources.get("weapon");
                    militaryResources.put("weapon", temp - infantry);
                }
                if (tanks > 0) {
                    army.getDivisions().put("tank", tanks);
                    int temp = militaryResources.get("tank");
                    militaryResources.put("tank", temp - tanks);
                }
                if (artillery > 0) {
                    army.getDivisions().put("artillery", artillery);
                    int temp = militaryResources.get("artillery");
                    militaryResources.put("artillery", temp - artillery);
                }
                placeTroop(army, regionForTroop(army));
                troops.add(army);
            }
            if (!targetRes.containsKey("ship")) {
                int destroyers = 0, cruisers = 0, submarines = 0;
                if (militaryResources.get("ship") >= Values.MIN_DESTROYERS)
                    destroyers = militaryResources.get("ship");
                if (militaryResources.get("ship") >= Values.MIN_CRUISERS)
                    cruisers = militaryResources.get("ship") - destroyers;
                if (militaryResources.get("ship") >= Values.MIN_SUBMARINES)
                    submarines = militaryResources.get("ship") - destroyers - cruisers;
                troopForce = destroyers * Values.DESTROYER_FORCE + cruisers * Values.CRUISERS_FORCE
                        + submarines * Values.SUBMARINES_FORCE;
                troopHealth = destroyers * Values.DESTROYER_HEALTH + cruisers * Values.CRUISERS_HEALTH +
                        submarines * Values.SUBMARINES_HEALTH;
                if (troopForce > 0) {
                    Fleet fleet = new Fleet(troopForce, troopHealth);
                    if (destroyers > 0) {
                        fleet.getDivisions().put("destroyer", destroyers);int temp = militaryResources.get("ship");
                        militaryResources.put("ship", temp - destroyers);
                    }
                    if (cruisers > 0) {
                        fleet.getDivisions().put("cruiser", cruisers);
                        int temp = militaryResources.get("ship");
                        militaryResources.put("ship", temp - cruisers);
                    }
                    if (submarines > 0) {
                        fleet.getDivisions().put("submarine", submarines);
                        int temp = militaryResources.get("ship");
                        militaryResources.put("ship", temp - submarines);
                    }
                    troops.add(fleet);
                    placeTroop(fleet, regionForTroop(fleet));
                }
            }
            if (!targetRes.containsKey("plane")) {
                int fighters = 0, bombers = 0, stormtroopers = 0;
                if (militaryResources.get("plane") >= Values.MIN_FIGHTERS)
                    fighters = militaryResources.get("plane");
                if (militaryResources.get("plane") >= Values.MIN_BOMBERS)
                    bombers = militaryResources.get("plane") - fighters;
                if (militaryResources.get("plane") >= Values.MIN_STORMTROOPERS)
                    stormtroopers = militaryResources.get("plane") - fighters - bombers;
                troopForce = fighters * Values.FIGHTERS_FORCE + bombers * Values.FIGHTERS_FORCE
                        + stormtroopers * Values.STORMTROOPERS_FORCE;
                troopHealth = fighters * Values.FIGHTERS_HEALTH + bombers * Values.FIGHTERS_HEALTH
                        + stormtroopers * Values.STORMTROOPERS_HEALTH;
                if (troopForce > 0) {
                    Squadron squadron = new Squadron(troopForce, troopHealth);
                    if (fighters > 0) {
                        squadron.getDivisions().put("fighter", fighters);
                        int temp = militaryResources.get("plane");
                        militaryResources.put("plane", temp - fighters);
                    }
                    if (bombers > 0) {
                        squadron.getDivisions().put("bomber", bombers);
                        int temp = militaryResources.get("plane");
                        militaryResources.put("plane", temp - bombers);
                    }
                    if (stormtroopers > 0) {
                        squadron.getDivisions().put("stormtrooper", stormtroopers);
                        int temp = militaryResources.get("plane");
                        militaryResources.put("plane", temp - stormtroopers);
                    }
                    troops.add(squadron);
                    placeTroop(squadron, regionForTroop(squadron));
                }
            }
            // Выбрать цель-постройку
            if (buildingBuilt) {
                switch (currentBuilding % 2) {
                    case 0:
                        MoneyBuilding Bank = new MoneyBuilding(buildings_bundle.get("bank"), 0,
                                Values.BANK_PRICE, Values.BANK_EFF);
                        Bank.setImagePath("textures/bank.png");
                        targetBuilding = Bank;
                        targetBuildRegion = regionForBuild(Bank);
                        buildingBuilt = false;
                        break;
                    case 1:
                        boolean selected = false;
                        for (String res : extractingResources.keySet()) {
                            if (resourcesExtEff.get(res) < forProduction.get(res)) {
                                ResourceBuilding building = new ResourceBuilding(
                                        buildings_bundle.get(res + "Mine"), 0, res,
                                        Values.MINE_PRICE);
                                building.setRequiredRes(new String[]{res});
                                building.setImagePath("textures/" + res + ".png");
                                building.setEfficiency(Values.MINE_EFF);
                                targetBuilding = building;
                                targetBuildRegion = regionForBuild(building);
                                buildingBuilt = false;
                                selected = true;
                                break;
                            }
                        }
                        if (!selected) {
                            for (String res : producingResources.keySet()) {
                                if (resourcesExtEff.get(res) < forProduction.get(res)) {
                                    ResourceBuilding building = new ResourceBuilding(
                                            buildings_bundle.get(res + "Plant"), 0, res,
                                            Values.MINE_PRICE);
                                    building.setRequiredRes(new String[]{"coal", "iron"});
                                    building.setImagePath("textures/" + res + ".png");
                                    building.setEfficiency(Values.MINE_EFF);
                                    targetBuilding = building;
                                    targetBuildRegion = regionForBuild(building);
                                    buildingBuilt = false;
                                    selected = true;
                                    break;
                                }
                            }
                        }
                        if (!selected) {
                            String resource;
                            if (hasTargetTroop)
                                resource = MinEffRes(targetRes);
                            else
                                resource = MinEffRes(militaryResources);
                            ResourceBuilding plant = new ResourceBuilding(buildings_bundle.get(
                                    resource + "Plant"), 0, resource, Values.PLANT_PRICE);
                            plant.setRequiredRes(new String[]{"steel", "aluminum", "pigIron"});
                            plant.setEfficiency(Values.MINE_EFF);
                            plant.setImagePath("textures/" + resource + ".png");
                            targetBuilding = plant;
                            targetBuildRegion = regionForBuild(plant);
                            buildingBuilt = false;
                        }
                        break;
                }
                currentBuilding++;
            } else if (money >= targetBuilding.getPrice()) {
                money -= targetBuilding.getPrice();
                targetBuilding.upgrade();
                if (targetBuildRegion != null) {
                    targetBuildRegion.Build(targetBuilding);
                }
                buildingBuilt = true;

            }
            if (hasTargetTroop) {
                boolean canPlace = true;
                for (String res : targetRes.keySet()) {
                    if (militaryResources.get(res) < targetRes.get(res))
                        canPlace = false;
                }
                if (canPlace) {
                    troops.add(targetTroop);
                    placeTroop(targetTroop, regionForTroop(targetTroop));
                    hasTargetTroop = false;
                    for (String res : targetRes.keySet()) {
                        int temp = militaryResources.get(res);
                        militaryResources.put(res, temp - targetRes.get(res));
                    }
                    targetRes = new HashMap<>();
                }
            }
            if (war) {
                for (Troop troop : troops)
                    replaceTroop(troop, regionForTroop(troop));
            }
            if (disperseTroops) {
                for (Troop troop : troops)
                    replaceTroop(troop, regionForTroop(troop));
                disperseTroops = false;
            }
        }

    }

    private Country findTargetCountry() {
        if (war) {
            Country minForceCountry = null;
            int minForce = Integer.MAX_VALUE;
            for (Country country: enemies.values()) {
                if (country.getForce() < minForce) {
                    minForce = country.getForce();
                    minForceCountry = country;
                }
            }
            return minForceCountry;
        } else {
            int playerForce = 0;
            int computerForce = 0;
            for (Troop troop : player.getTroops())
                playerForce += troop.getForce();
            for (Troop troop : computer.getTroops())
                computerForce += troop.getForce();
            if (playerForce <= computerForce || !computer.isAlive())
                return player;
            return computer;
        }
    }

    private void placeTroop(Troop troop, Region region) {
        if (region != null) {
            Random random = new Random();
            float x = random.nextInt((int) (region.getScaleX())) + region.getX1();
            float y = random.nextInt((int) (region.getScaleY())) + region.getY1();
            if (x - troop.getRadius() / 2 <= region.getX1())
                x = region.getX1() + troop.getRadius() / 2;
            if (x + troop.getRadius() / 2 >= region.getX2())
                x = region.getX2() - troop.getRadius() / 2;
            if (y - troop.getRadius() / 2 <= region.getY1())
                y = region.getY1() + troop.getRadius() / 2;
            if (y + troop.getRadius() / 2 >= region.getY2())
                y = region.getY2() - troop.getRadius() / 2;
            troop.place(region, x - troop.getRadius() / 2, y - troop.getRadius() / 2);
            region.getTroops().add(troop);
        }
    }

    private void replaceTroop(Troop troop, Region region) {
        if (region != null) {
            if (!troop.getRegion().getName().equals(region.getName())) {
                troop.getRegion().getTroops().remove(troop);
                placeTroop(troop, region);
            }
        }
    }

    private Region regionForTroop(Troop troop) {
        troop.setCountry(this);
        if (war) {
            if (regions.size() > 0) {
                Region maxEnemyDivReg = regions.get(0);
                for (Region region : regions) {
                    if (region.getCountryDivisions(targetCountry.getName()) >
                            maxEnemyDivReg.getCountryDivisions(targetCountry.getName()) && troop.canPlace(region))
                        maxEnemyDivReg = region;
                }
                if (maxEnemyDivReg.getCountryDivisions(player.getName()) > 0 && troop.canPlace(maxEnemyDivReg))
                    return maxEnemyDivReg;
            }
            if (targetCountry.getRegions().size() > 0) {
                Region lostRegion = targetCountry.getRegions().get(0);
                for (Region region: targetCountry.getRegions()) {
                    if (region.getPrevCountryName().equals(name) && troop.canPlace(region))
                        lostRegion = region;
                }
                if (lostRegion.getPrevCountryName().equals(name) && troop.canPlace(lostRegion))
                    return lostRegion;
                Region minForceReg = targetCountry.getRegions().get(0);
                for (Region region : targetCountry.getRegions()) {
                    if (region.getForce() < minForceReg.getForce() && troop.canPlace(region))
                        minForceReg = region;
                }
                if (troop.canPlace(minForceReg))
                    return minForceReg;
                return null;
            }
        }
        else {
            Region minDivReg = regions.get(0);
            for (Region region: regions) {
                if (region.getCountryDivisions(name) < minDivReg.getCountryDivisions(name) && troop.canPlace(region))
                    minDivReg = region;
            }
            if (troop.canPlace(minDivReg))
                return minDivReg;
        }
        return null;
    }

    private Region regionForBuild(Building building) {
        if (building instanceof ResourceBuilding) {
            ResourceBuilding resBld = (ResourceBuilding)building;
            if (resBld.getRequiredRes().length < 2) {
                String resource = resBld.getReceivedRes();
                int k = 0;
                Region maxRegion = regions.get(k);
                while (!maxRegion.getResources().containsKey(resource)) {
                    k++;
                    if (k < regions.size())
                        maxRegion = regions.get(k);
                    else return null;
                }
                for (Region region: regions) {
                    if (region.getResources().containsKey(resource))
                        if (region.getResources().get(resource) > maxRegion.getResources().get(resource) &&
                                !region.getBuildings().containsKey(building.getName()))
                            maxRegion = region;
                }
                return maxRegion;
            }
            else {
                for (Region region: regions) {
                   if (!region.getBuildings().containsKey(building.getName()))
                       return region;
                }
            }
        }
        if (building instanceof MoneyBuilding)
            for (Region region: regions) {
                if (!region.getBuildings().containsKey(building.getName()))
                    return region;
            }
        return null;
    }

    public void countPoints() {
        force = 0;
        for (Troop troop : troops)
            force += troop.getForce();
        money += moneyPd;
        // Расчет добытых ресурсов
        String[] resNames = {"iron", "aluminum", "coal", "steel", "pigIron", "weapon", "artillery",
                "plane", "tank", "ship"};
        for (String key: resNames) {
            resourcesExtEff.put(key, 0);
            forProduction.put(key, 0);
        }
        for (Region region: regions) {
            region.extractResources();
            for (String key: region.getExtractedResources().keySet()) {
                extractingResources.put(key, (int)(extractingResources.get(key) +
                        region.getExtractedResources().get(key)));
                region.getExtractedResources().put(key, 0f);
            }
            for (ResourceBuilding resourceBuilding: region.getResourceBuildings()) {
                String key = resourceBuilding.getReceivedRes();
                resourcesExtEff.put(key, resourcesExtEff.get(key) + resourceBuilding.getEfficiency());
            }
        }

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

            if (canProduce) {
                for (String resource : building.getRequiredRes()) {
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
                    producingResources.put(receivedRes, (int) (temp + value));
                } else {
                    temp = militaryResources.get(receivedRes);
                    militaryResources.put(receivedRes, (int) (temp + value));
                }
                resourcesExtEff.put(building.getReceivedRes(), value);

            }

        }
        alive = regions.size() > 0;
    }

    private String MinEffRes(HashMap<String, Integer> resources) {
        float minEff = Integer.MAX_VALUE;
        String minEffRes = "";
        for (String resource: resources.keySet()) {
            if (resourcesExtEff.get(resource) < minEff) {
                minEff = resourcesExtEff.get(resource);
                minEffRes = resource;
            }
        }
        return minEffRes;
    }

    public ArrayList<ResourceBuilding> getIndustryBuildings() {
        return industryBuildings;
    }

    public int getMoney() {
        return money;
    }

    public int getMoneyPd() {
        return moneyPd;
    }

    public void setMoneyPd(int moneyPd) {
        this.moneyPd = moneyPd;
    }

    @Override
    public ArrayList<Region> getRegions() {
        return regions;
    }

    public HashMap<String, Country> getEnemies() {
        return enemies;
    }

    public void setOfferingPeace(boolean offeringPeace) {
        this.offeringPeace = offeringPeace;
    }

    public boolean isOfferingPeace() {
        return offeringPeace;
    }

    public boolean isSettlePeace() {
        return settlePeace;
    }

    public void setOfferingCountry(Country offeringCountry) {
        this.offeringCountry = offeringCountry;
    }

    public Country getOfferingCountry() {
        return offeringCountry;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setWar(boolean war) {
        this.war = war;
    }

    @Override
    public HashMap<String, Integer> getExtractingResources() {
        return extractingResources;
    }

    @Override
    public HashMap<String, Integer> getMilitaryResources() {
        return militaryResources;
    }

    @Override
    public HashMap<String, Integer> getProducingResources() {
        return producingResources;
    }

    @Override
    public HashMap<String, Integer> getResourcesExtEff() {
        return resourcesExtEff;
    }

    public HashMap<String, Integer> getForProduction() {
        return forProduction;
    }

    @Override
    public int getForce() {
        return force;
    }
}

