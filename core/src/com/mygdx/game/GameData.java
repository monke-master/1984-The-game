package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;


// Игровые данные
public class GameData {
    private PropagandaResearchScreen propagandaResearchScreen;
    private MinTruthScreen minTruthScreen;
    private GameScreen gameScreen;
    private RegionScreen regionScreen;
    private PlayerCountry player;
    private MinistriesScreen ministriesScreen;
    private HashMap<String, Technology> technologies;
    private MinLoveScreen minLoveScreen;
    private ThoughtPoliceScreen thoughtPoliceScreen;
    private ResourcesScreen resourcesScreen;
    private MinPlentyScreen minPlentyScreen;
    private Preferences playerData, namesData;
    private TroopsScreen troopsScreen;
    private BuildScreen buildScreen;
    private AvailableBuildingScreen availableBuildingScreen;
    private ArmyScreen armyScreen;
    private SquadronScreen squadronScreen;
    private FleetScreen fleetScreen;
    private ComputerCountry computer1, computer2;
    private MinPeaceScreen minPeaceScreen;
    private EndgameScreen endgameScreen;
    private HashMap<String, Country> countries;
    private ChooseCountryScreen chooseCountryScreen;
    private String playerName, computer1Name, computer2Name;
    private Game game;

    public GameData(Game game) {
        this.game = game;
        playerData = Gdx.app.getPreferences("playerData");
        namesData = Gdx.app.getPreferences("namesData");
        Map.createRegions();
        chooseCountryScreen = new ChooseCountryScreen(game, this);
    }

    public void createScreens() {
        // Инициализация всех экранов
        propagandaResearchScreen = new PropagandaResearchScreen(game, this);
        gameScreen = new GameScreen(game, this);
        regionScreen = new RegionScreen(game, this);
        minLoveScreen = new MinLoveScreen(game, this);
        minTruthScreen = new MinTruthScreen(game, this);
        minPlentyScreen = new MinPlentyScreen(game, this);
        resourcesScreen = new ResourcesScreen(game, this);
        ministriesScreen = new MinistriesScreen(game, this);
        thoughtPoliceScreen = new ThoughtPoliceScreen(game, this);
        troopsScreen = new TroopsScreen(game, this);
        buildScreen = new BuildScreen(game, this);
        availableBuildingScreen = new AvailableBuildingScreen(game, this);
        armyScreen = new ArmyScreen(game, this);
        squadronScreen = new SquadronScreen(game, this);
        fleetScreen = new FleetScreen(game, this);
        minPeaceScreen = new MinPeaceScreen(game, this);
        endgameScreen = new EndgameScreen(game, this);
    }

    public void setComputer1Name(String computer1Name) {
        this.computer1Name = computer1Name;
    }

    public void setComputer2Name(String computer2Name) {
        this.computer2Name = computer2Name;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public ChooseCountryScreen getChooseCountryScreen() { return chooseCountryScreen;}

    public EndgameScreen getEndgameScreen() { return endgameScreen;}

    public MinPeaceScreen getMinPeaceScreen() {
        return minPeaceScreen;
    }

    public ComputerCountry getComputer1() {
        return computer1;
    }

    public ComputerCountry getComputer2() {
        return computer2;
    }

    public FleetScreen getFleetScreen() {return fleetScreen;}

    public SquadronScreen getSquadronScreen() {
        return squadronScreen;
    }

    public ArmyScreen getArmyScreen() {
        return armyScreen;
    }

    public AvailableBuildingScreen getAvailableBuildingScreen() {
        return availableBuildingScreen;
    }

    public BuildScreen getBuildScreen() {
        return buildScreen;
    }

    public TroopsScreen getTroopsScreen() { return troopsScreen; }

    public MinPlentyScreen getMinPlentyScreen() {
        return minPlentyScreen;
    }

    public GameScreen getGameScreen() {
        return this.gameScreen;
    }

    public PropagandaResearchScreen getPropagandaResearchScreen() {
        return propagandaResearchScreen;
    }

    public MinistriesScreen getMinistriesScreen() {
        return this.ministriesScreen;
    }

    public MinTruthScreen getMinTruthScreen() {
        return this.minTruthScreen;
    }

    public RegionScreen getRegionScreen() {
        return regionScreen;
    }


    public MinLoveScreen getMinLoveScreen() {
        return this.minLoveScreen;
    }

    public PlayerCountry getPlayer() {
        return player;
    }

    public HashMap<String, Technology> getTechnologies() {
        return technologies;
    }

    public ThoughtPoliceScreen getThoughtPoliceScreen(){
        return this.thoughtPoliceScreen;
    }

    public ResourcesScreen getResourcesScreen() { return this.resourcesScreen;}

    public void SaveData() {
        namesData.putString("playerName", player.getName());
        namesData.putString("computer1Name", computer1.getName());
        namesData.putString("computer2Name", computer2.getName());
        namesData.flush();

        // Данные регионов
        for (Region region: Map.regions) {
            Preferences regionData = Gdx.app.getPreferences(region.getName());
            regionData.putString("country", region.getCountryName());
            for (String resource: region.getResources().keySet())
                regionData.putFloat(resource, region.getResources().get(resource));
            String buildings = "";
            for (Building building: region.getBuildings().values()) {
                buildings += building.getName() + ",";
                String info = building.toString();
                regionData.putString(building.getName(), info);
            }
            regionData.putString("buildings", buildings);

            int armies = 0, squadrons = 0, fleet = 0;
            for (Troop troop: region.getTroops()) {
                String key = "";
                String divisions = "";
                if (troop instanceof Army) {
                    armies++;
                    key = "army" + armies;
                }
                if (troop instanceof Fleet) {
                    fleet++;
                    key = "fleet" + fleet;
                }
                if (troop instanceof Squadron) {
                    squadrons++;
                    key = "squadron" + squadrons;
                }
                regionData.putInteger(key  + "health", troop.getHealth());
                regionData.putInteger(key + "force", troop.getForce());
                regionData.putFloat(key + "x", troop.getX());
                regionData.putFloat(key + "y", troop.getY());
                regionData.putString(key + "country", troop.getCountry().getName());
                for (String division: troop.getDivisions().keySet()) {
                    regionData.putInteger(key + division,
                            troop.getDivisions().get(division));
                    Gdx.app.log(region.getName(), key + division);
                    divisions += division + " ";
                }
                regionData.putString(key + "divisions", divisions);
            }
            regionData.putInteger("army", armies);
            regionData.putInteger("fleet", fleet);
            regionData.putInteger("squadron", squadrons);

            regionData.flush();
        }

        // Данные стран
        for (Country country: countries.values()) {
            Preferences countryData = Gdx.app.getPreferences(country.getName());
            countryData.putInteger("money", country.getMoney());
            countryData.putInteger("moneyPd", country.getMoneyPd());
            for (String resource: country.getExtractingResources().keySet()) {
                countryData.putInteger(resource + "_total", country.getExtractingResources().get(resource));
                countryData.putInteger(resource + "_in_day", country.getResourcesExtEff().get(resource));
                countryData.putInteger(resource + "_for_prod", country.getForProduction().get(resource));
            }

            for (String resource: country.getProducingResources().keySet()) {
                countryData.putInteger(resource + "_total", country.getProducingResources().get(resource));
                countryData.putInteger(resource + "_in_day", country.getResourcesExtEff().get(resource));
                countryData.putInteger(resource + "_for_prod", country.getForProduction().get(resource));
            }

            for (String resource: country.getMilitaryResources().keySet()) {
                countryData.putInteger(resource + "_total", country.getMilitaryResources().get(resource));
                countryData.putInteger(resource + "_in_day", country.getResourcesExtEff().get(resource));
                countryData.putInteger(resource + "_for_prod", country.getForProduction().get(resource));
            }
            countryData.flush();
        }

        // Данные игрока
        String technologies = "";
        for (String tech: player.getScience().getTechnologies())
            technologies += tech + ",";
        playerData.putString("technologies", technologies);

        String minTruthDecrees = "";
        for (String decree: player.getMinTruth().getDecrees())
            minTruthDecrees += decree + ",";
        playerData.putString("minTruthDecrees", minTruthDecrees);

        String minLoveDecrees = "";
        for (String decree: player.getMinLove().getDecrees())
            minLoveDecrees += decree + ",";
        playerData.putString("minLoveDecrees", minLoveDecrees);

        String minPlentyDecrees = "";
        for (String decree: player.getMinPlenty().getDecrees())
            minPlentyDecrees += decree + ",";
        playerData.putString("minPlentyDecrees", minPlentyDecrees);

        String minPeaceDecrees = "";
        for (String decree: player.getMinPeace().getDecrees())
            minPeaceDecrees += decree + ",";
        playerData.putString("minPeaceDecrees", minPeaceDecrees);

        String thoughtPoliceDecrees = "";
        for (String decree: player.getThoughtPolice().getDecrees())
            thoughtPoliceDecrees += decree + ",";
        playerData.putString("thoughtPoliceDecrees", thoughtPoliceDecrees);

        int minLovePoints = player.getMinLove().getPoints();
        playerData.putInteger("minLovePoints", minLovePoints);
        int minLovePointsPD = player.getMinLove().getPointsPD();
        playerData.putInteger("minLovePointsPD", minLovePointsPD);

        int minTruthPoints = player.getMinTruth().getPoints();
        playerData.putInteger("minTruthPoints", minTruthPoints);
        int minTruthPointsPD = player.getMinTruth().getPointsPD();
        playerData.putInteger("minTruthPointsPD", minTruthPointsPD);

        int minPlentyPoints = player.getMinPlenty().getPoints();
        playerData.putInteger("minPlentyPoints", minPlentyPoints);
        int minPlentyPointsPD = player.getMinPlenty().getPointsPD();
        playerData.putInteger("minPlentyPointsPD", minPlentyPointsPD);

        int minPeacePoints = player.getMinPeace().getPoints();
        playerData.putInteger("minPeacePoints", minPeacePoints);
        int minPeacePointsPD = player.getMinPeace().getPointsPD();
        playerData.putInteger("minPeacePointsPD", minPeacePointsPD);

        int thoughtPolicePoints = player.getThoughtPolice().getPoints();
        playerData.putInteger("thoughtPolicePoints", thoughtPolicePoints);
        int thoughtPolicePointsPD = player.getThoughtPolice().getPointsPD();
        playerData.putInteger("thoughtPolicePointsPD", thoughtPolicePointsPD);

        int sciencePoints = player.getScience().getPoints();
        playerData.putInteger("sciencePoints", sciencePoints);
        int sciencePointsPD = player.getScience().getPointsPD();
        playerData.putInteger("sciencePointsPD", sciencePointsPD);

        float minPlentyEff = player.getMinPlenty().getEfficiency();
        playerData.putFloat("minPlentyEff", minPlentyEff);

        float minTruthEff = player.getMinTruth().getEfficiency();
        playerData.putFloat("minTruthEff", minTruthEff);

        float minPeaceEff = player.getMinPeace().getEfficiency();
        playerData.putFloat("minPeaceEff", minPeaceEff);

        float minLoveEff = player.getMinLove().getEfficiency();
        playerData.putFloat("minLoveEff", minLoveEff);

        float thoughtPoliceEff = player.getThoughtPolice().getEfficiency();
        playerData.putFloat("thoughtPoliceEff", thoughtPoliceEff);

        playerData.flush();
    }



    public void LoadData() {
        player = new PlayerCountry(namesData.getString("playerName", playerName));
        computer1 = new ComputerCountry(namesData.getString("computer1Name", computer1Name));
        computer2 = new ComputerCountry(namesData.getString("computer2Name", computer2Name));
        countries = new HashMap<>();
        countries.put(player.getName(), player);
        countries.put(computer1.getName(), computer1);
        countries.put(computer2.getName(), computer2);
        computer1.setPlayer(player);
        computer2.setPlayer(player);
        computer1.setComputer(computer2);
        computer2.setComputer(computer1);
        // Данные регионов
        for (Region region: Map.regions) {
            Preferences regionData = Gdx.app.getPreferences(region.getName());
            String countryName = regionData.getString("country", region.getCountryName());
            region.setCountryName(countryName);
            region.setCountry(countries.get(countryName));
            countries.get(countryName).getRegions().add(region);

            // Ресурсы региона
            for (String resource: region.getResources().keySet()) {
                float value = regionData.getFloat(resource, region.getResources().get(resource));
                region.getResources().put(resource, value);
            }

            // Постройки региона
            String[] buildings = regionData.getString("buildings", "").split(",");
            if (!buildings[0].equals("")) {
                for (String buildingName : buildings) {
                    String[] info = regionData.getString(buildingName).split(" ");
                    int level = Integer.parseInt(info[0]);
                    int price = Integer.parseInt(info[1]);
                    int efficiency = Integer.parseInt(info[2]);
                    String imagePath = info[3];
                    switch (info[4]) {
                        case "money":
                            MoneyBuilding moneyBuilding = new MoneyBuilding(buildingName, level, price, efficiency);
                            moneyBuilding.setImagePath(imagePath);
                            region.Build(moneyBuilding);
                            break;
                        case "ministry":
                            MinistryBuilding ministryBuilding = new MinistryBuilding(buildingName,
                                    level, price, efficiency);
                            ministryBuilding.setImagePath(imagePath);
                            ministryBuilding.setMinistry(info[5]);
                            region.Build(ministryBuilding);
                            break;
                        default:
                            ResourceBuilding resourceBuilding = new ResourceBuilding(buildingName, level,
                                    info[4], price);
                            resourceBuilding.setEfficiency(efficiency);
                            resourceBuilding.setImagePath(imagePath);
                            ArrayList<String> requiredRes =
                                    new ArrayList<>(Arrays.asList(info).subList(5, info.length));
                            resourceBuilding.setRequiredRes(requiredRes);
                            region.Build(resourceBuilding);
                            break;
                    }
                }
            }

            // Войска в регионе
            int armies = regionData.getInteger("army", 0);
            int fleet = regionData.getInteger("fleet", 0);
            int squadron = regionData.getInteger("squadron", 0);

            for (int i = 1; i <= armies; i++) {
                String key = "army" + i;
                int force = regionData.getInteger(key + "force");
                int health = regionData.getInteger(key + "health");
                float x = regionData.getFloat(key + "x");
                float y = regionData.getFloat(key + "y");
                String country = regionData.getString(key + "country");
                String[] divisions = regionData.getString(key + "divisions").split(" ");
                Army army1 = new Army(force, health);
                army1.setCountry(countries.get(country));
                army1.place(region, x, y);
                countries.get(country).getTroops().add(army1);
                region.getTroops().add(army1);
                for (String division: divisions) {
                    int num = regionData.getInteger(key + division);
                    army1.getDivisions().put(division, num);
                }
            }

            for (int i = 1; i <= squadron; i++) {
                String key = "squadron" + i;
                int force = regionData.getInteger(key + "force");
                int health = regionData.getInteger(key + "health");
                float x = regionData.getFloat(key + "x");
                float y = regionData.getFloat(key + "y");
                String country = regionData.getString(key + "country");
                String[] divisions = regionData.getString(key + "divisions").split(" ");
                Squadron squadron1 = new Squadron(force, health);
                squadron1.setCountry(countries.get(country));
                squadron1.place(region, x, y);
                countries.get(country).getTroops().add(squadron1);
                region.getTroops().add(squadron1);
                for (String division: divisions) {
                    int num = regionData.getInteger(key + " division");
                    squadron1.getDivisions().put(division, num);
                }
            }

            for (int i = 1; i <= fleet; i++) {
                String key = "fleet" + i;
                int force = regionData.getInteger(key + "force");
                int health = regionData.getInteger(key + "health");
                float x = regionData.getFloat(key + "x");
                float y = regionData.getFloat(key + "y");
                String country = regionData.getString(key + "country");
                String[] divisions = regionData.getString(key + "divisions").split(" ");
                Fleet fleet1 = new Fleet(force, health);
                fleet1.setCountry(countries.get(country));
                fleet1.place(region, x, y);
                countries.get(country).getTroops().add(fleet1);
                region.getTroops().add(fleet1);
                for (String division: divisions) {
                    int num = regionData.getInteger(key + division);
                    fleet1.getDivisions().put(division, num);
                }
            }
        }

        // Данные стран
        for (Country country: countries.values()) {
            Gdx.app.log(country.getName(), "aboba");
            Preferences countryData = Gdx.app.getPreferences(country.getName());
            int money = countryData.getInteger("money", Values.MONEY);
            int moneyPd = countryData.getInteger("moneyPd", Values.MONEY_PD);
            country.setMoney(money);
            country.setMoneyPd(moneyPd);

            for (String resource: country.getExtractingResources().keySet()) {
                int total = countryData.getInteger(resource + "_total", Values.START_RES);
                int inDay = countryData.getInteger(resource + "_in_day", 0);
                int forProd = countryData.getInteger(resource + "_for_prod", 0);
                country.getExtractingResources().put(resource, total);
                country.getResourcesExtEff().put(resource, inDay);
                country.getForProduction().put(resource, forProd);
            }

            for (String resource: country.getProducingResources().keySet()) {
                int total = countryData.getInteger(resource + "_total", Values.START_RES);
                int inDay = countryData.getInteger(resource + "_in_day", 0);
                int forProd = countryData.getInteger(resource + "_for_prod", 0);
                country.getProducingResources().put(resource, total);
                country.getResourcesExtEff().put(resource, inDay);
                country.getForProduction().put(resource, forProd);
            }

            for (String resource: country.getMilitaryResources().keySet()) {
                Gdx.app.log(country.getName(), resource);
                int total = countryData.getInteger(resource + "_total", 0);
                int inDay = countryData.getInteger(resource + "_in_day", 0);
                int forProd = countryData.getInteger(resource + "_for_prod", 0);
                country.getMilitaryResources().put(resource, total);
                country.getResourcesExtEff().put(resource, inDay);
                country.getForProduction().put(resource, forProd);
            }
        }

        // Данные игрока
        String[] technologies = playerData.getString("technologies").split(",");
        for (String tech: technologies)
            player.getScience().getTechnologies().add(tech);

        String[] minTruthDecrees = playerData.getString("minTruthDecrees").split(",");
        for (String decree: minTruthDecrees)
            player.getMinTruth().getDecrees().add(decree);

        String[] minLoveDecrees = playerData.getString("minLoveDecrees").split(",");
        for (String decree: minLoveDecrees)
            player.getMinLove().getDecrees().add(decree);

        String[] minPlentyDecrees = playerData.getString("minPlentyDecrees").split(",");
        for (String decree: minPlentyDecrees)
            player.getMinPlenty().getDecrees().add(decree);

        String[] minPeaceDecrees = playerData.getString("minPeaceDecrees").split(",");
        for (String decree: minPeaceDecrees)
            player.getMinPeace().getDecrees().add(decree);

        String[] thoughtPoliceDecrees = playerData.getString("thoughtPoliceDecrees").split(",");
        for (String decree: thoughtPoliceDecrees)
            player.getThoughtPolice().getDecrees().add(decree);

        int minLovePoints = playerData.getInteger("minLovePoints", Values.MINISTRY_POINTS);
        player.getMinLove().setPoints(minLovePoints);
        int minLovePointsPD = playerData.getInteger("minLovePointsPD", Values.MINISTRY_POINTS_PD);
        player.getMinLove().setPointsPD(minLovePointsPD);

        int minTruthPoints = playerData.getInteger("minTruthPoints", Values.MINISTRY_POINTS);
        player.getMinTruth().setPoints(minTruthPoints);
        int minTruthPointsPD = playerData.getInteger("minTruthPointsPD", Values.MINISTRY_POINTS_PD);
        player.getMinTruth().setPointsPD(minTruthPointsPD);

        int minPlentyPoints = playerData.getInteger("minPlentyPoints", Values.MINISTRY_POINTS);
        player.getMinPlenty().setPoints(minPlentyPoints);
        int minPlentyPointsPD = playerData.getInteger("minPlentyPointsPD", Values.MINISTRY_POINTS_PD);
        player.getMinPlenty().setPointsPD(minPlentyPointsPD);

        int minPeacePoints = playerData.getInteger("minPeacePoints", Values.MINISTRY_POINTS);
        player.getMinPeace().setPoints(minPeacePoints);
        int minPeacePointsPD = playerData.getInteger("minPeacePointsPD", Values.MINISTRY_POINTS_PD);
        player.getMinPeace().setPointsPD(minPeacePointsPD);

        int thoughtPolicePoints = playerData.getInteger("thoughtPolicePoints", Values.MINISTRY_POINTS);
        player.getThoughtPolice().setPoints(thoughtPolicePoints);
        int thoughtPolicePointsPD = playerData.getInteger("thoughtPolicePointsPD", Values.MINISTRY_POINTS_PD);
        player.getThoughtPolice().setPointsPD(thoughtPolicePointsPD);

        int sciencePoints = playerData.getInteger("sciencePoints", Values.MINISTRY_POINTS);
        player.getScience().setPoints(sciencePoints);
        int sciencePointsPD = playerData.getInteger("sciencePointsPD", Values.MINISTRY_POINTS_PD);
        player.getScience().setPointsPD(sciencePointsPD);

        float minPlentyEff = playerData.getFloat("minPlentyEff", Values.EFFICIENCY);
        player.getMinPlenty().setEfficiency(minPlentyEff);

        float minTruthEff = playerData.getFloat("minTruthEff", Values.EFFICIENCY);
        player.getMinTruth().setEfficiency(minTruthEff);

        float minPeaceEff = playerData.getFloat("minPeaceEff", Values.EFFICIENCY);
        player.getMinPeace().setEfficiency(minPeaceEff);

        float minLoveEff = playerData.getFloat("minLoveEff", Values.EFFICIENCY);
        player.getMinLove().setEfficiency(minLoveEff);

        float thoughtPoliceEff = playerData.getFloat("thoughtPoliceEff", Values.EFFICIENCY);
        player.getThoughtPolice().setEfficiency(thoughtPoliceEff);

        createTechnologies();
    }

    public void createTechnologies() {
        technologies = new HashMap<>();
        I18NBundle tech_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/technologies"));
        Technology Telescreen = new Technology(tech_bundle.get("telescreen"),
                tech_bundle.get("telescreenDesc"), 300);
        technologies.put(Telescreen.getName(), Telescreen);

        Technology Microphone = new Technology(tech_bundle.get("microphone"),
                tech_bundle.get("microphoneDesc"), 200);
        technologies.put(Microphone.getName(), Microphone);

        Technology camera = new Technology(tech_bundle.get("camera"), tech_bundle.get("cameraDesc"), 400);
        technologies.put(camera.getName(), camera);

        Technology TrackingTS = new Technology(tech_bundle.get("trackingTS"),
                tech_bundle.get("trackingTSDesc"), 800);
        TrackingTS.addRequiredTech(Telescreen);
        TrackingTS.addRequiredTech(Microphone);
        TrackingTS.addRequiredTech(camera);
        technologies.put(TrackingTS.getName(), TrackingTS);

        Technology ThoughtController = new Technology(tech_bundle.get("thoughtController"),
                tech_bundle.get("thoughtControllerDesc"), 700);
        technologies.put(ThoughtController.getName(), ThoughtController);

        Technology CompactTC = new Technology(tech_bundle.get("compactTC"),
                tech_bundle.get("compactTCDesc"), 1200);
        CompactTC.addRequiredTech(ThoughtController);
        technologies.put(CompactTC.getName(), CompactTC);

        Technology ThoughtTS = new Technology(tech_bundle.get("thoughtTS"),
                tech_bundle.get("thoughtTSDesc"), 2500);
        ThoughtTS.addRequiredTech(CompactTC);
        ThoughtTS.addRequiredTech(TrackingTS);
        technologies.put(ThoughtTS.getName(), ThoughtTS);

        Technology SpyBugs1 = new Technology(tech_bundle.get("spyBugs"),
                tech_bundle.get("spyBugsDesc"), 500);
        technologies.put(SpyBugs1.getName(), SpyBugs1);

        Technology SpyBugs2 = new Technology(tech_bundle.get("spyBugs2"),
                tech_bundle.get("spyBugs2Desc"), 800);
        SpyBugs2.addRequiredTech(SpyBugs1);
        technologies.put(SpyBugs2.getName(), SpyBugs2);

        Technology SpyBugs3 = new Technology(tech_bundle.get("spyBugs3"),
                tech_bundle.get("spyBugs3Desc"), 1300);
        SpyBugs3.addRequiredTech(SpyBugs2);
        technologies.put(SpyBugs3.getName(), SpyBugs3);

        Technology WristTelescreen = new Technology(tech_bundle.get("wristTelescreen"),
                tech_bundle.get("wristTelescreenDesc"), 3500);
        WristTelescreen.addRequiredTech(ThoughtTS);
        WristTelescreen.addRequiredTech(SpyBugs3);
        technologies.put(WristTelescreen.getName(), WristTelescreen);

        Technology ImplantableChip = new Technology(tech_bundle.get("implantableChip"),
                tech_bundle.get("implantableChipDesc"), 5000);
        ImplantableChip.addRequiredTech(WristTelescreen);
        technologies.put(ImplantableChip.getName(), ImplantableChip);

        for (String name: player.getScience().getTechnologies()) {
            if (technologies.containsKey(name))
               technologies.get(name).setResearched(true);
        }
    }

    public void ClearData() {
        if (!namesData.getString("playerName", "").equals("")) {
            player = new PlayerCountry(namesData.getString("playerName", playerName));
            computer1 = new ComputerCountry(namesData.getString("computer1Name", computer1Name));
            computer2 = new ComputerCountry(namesData.getString("computer2Name", computer2Name));
            countries = new HashMap<>();
            countries.put(player.getName(), player);
            countries.put(computer1.getName(), computer1);
            countries.put(computer2.getName(), computer2);
            for (String country : countries.keySet()) {
                Preferences countryData = Gdx.app.getPreferences(country);
                countryData.clear();
                countryData.flush();
            }
            for (Region region : Map.regions) {
                Preferences regionData = Gdx.app.getPreferences(region.getName());
                regionData.clear();
                regionData.flush();
            }
            namesData.clear();
            namesData.flush();
            playerData.clear();
            playerData.flush();

        }
    }

}
