package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class AvailableBuildingScreen implements Screen {

    private Game game;
    private  GameData gameData;
    private  Stage stage;
    private I18NBundle ui_bundle, buildings_bundle;
    private  float height, width;
    private Label selectedBuildingLbl, selectedBuildingDescLbl, priceLbl, moneyLbl;
    private ArrayList<Building> buildings;
    private Building selectedBuilding;
    private ArrayList<Skin> skins;
    private  Label.LabelStyle priceLblStyle;
    private Image priceImg;
    private  TextButton BuildBtn;
    private  Region region;
    private float x, y;

    public AvailableBuildingScreen(Game game, GameData gameData) {
        this.game = game;
        this.gameData = gameData;

        // Строковые ресурсы
        ui_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/ui_strings"));
        buildings_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/buildings"));

        // Параметры экрана
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        stage = new Stage(new ScreenViewport());

        skins = new ArrayList<>();
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @Override
    public void show() {
        x = 0;
        y = 5*height/6;
        stage = new Stage(new ScreenViewport());
        createBuildings();

        // Кнопка выхода
        Skin skin_close = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton closeBtn = new ImageButton(skin_close);
        closeBtn.setSize(width/10, height/10);
        closeBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/close.png")));
        closeBtn.setPosition(9*width/10, 9*height/10);
        closeBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                gameData.getBuildScreen().setRegion(region);
                game.setScreen(gameData.getBuildScreen());
                return true;
            }
        });
        skins.add(skin_close);

        // Описание выбранной постройки
        Image descBack = new Image(new Texture(Gdx.files.internal("textures/back.png")));
        descBack.setSize(1.1f*width/3, 9.5f*height/10);
        descBack.setPosition(1.95f*width/3, height/10);

        selectedBuildingLbl = new Label(ui_bundle.get("selectBuilding"), Styles.defaultLbl);
        selectedBuildingLbl.setSize(width/4, height/10);
        selectedBuildingLbl.setPosition(2.30f*width/3, 3.95f*height/5);
        selectedBuildingLbl.setAlignment(Align.left);

        selectedBuildingDescLbl = new Label("", Styles.defaultLbl);
        selectedBuildingDescLbl.setSize(width/4, 2*height/3);
        selectedBuildingDescLbl.setPosition(2.7f*width/4, 1.3f*height/5);
        selectedBuildingDescLbl.setAlignment(Align.left);


        // Виджеты цены
        priceLblStyle = new Label.LabelStyle();
        priceLblStyle.font = Styles.greenFont;

        priceLbl = new Label("", priceLblStyle);
        priceLbl.setSize(width/10, height/10);
        priceLbl.setPosition(3.05f*width/4, 1.5f*height/5);

        priceImg = new Image(new Texture(Gdx.files.internal("textures/money.png")));
        priceImg.setSize(width/20, height/11);
        priceImg.setPosition(2.83f*width/4, 1.5f*height/5);
        priceImg.setVisible(false);

        // Виджеты денег
        final Image moneyImg = new Image(new Texture(Gdx.files.internal("textures/money.png")));
        moneyImg.setSize(width/20, height/11);
        moneyImg.setPosition(width/100, height/40);

        moneyLbl = new Label(ui_bundle.get("total") + ":" +
                gameData.getPlayer().getMoney() + ". " + ui_bundle.get("perDay") + ":" +
                gameData.getPlayer().getMoneyPd(), Styles.defaultLbl);
        moneyLbl.setSize(width/3, height/10);
        moneyLbl.setPosition(width/15, height/40);

        // Кнопка улучшения
        BuildBtn = new TextButton(ui_bundle.get("build"), Styles.defaultBtn);
        BuildBtn.setSize(width/5, height/10);
        BuildBtn.setPosition(3*width/4, height/15);
        BuildBtn.setVisible(false);

        BuildBtn.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (gameData.getPlayer().getMoney() >= selectedBuilding.getPrice()) {
                    gameData.getPlayer().setMoney(gameData.getPlayer().getMoney() -
                            selectedBuilding.getPrice());
                    selectedBuilding.setBuilt(true);
                    selectedBuilding.upgrade();
                    region.Build(selectedBuilding);
                    gameData.getBuildScreen().setRegion(region);
                    game.setScreen(gameData.getBuildScreen());
                }
                return true;
            }

        });

        // Размещение доступных игроку построек в данном регионе
        for (final Building building: buildings) {
            if (!region.getBuildings().containsKey(building.getName())) {
                Skin skin = new Skin(Gdx.files.internal("skin/ui.json"));
                skins.add(skin);
                ImageButton button = new ImageButton(skin);
                button.setSize(width / 10, height / 10);
                button.setPosition(x, y);
                button.getStyle().imageUp = new TextureRegionDrawable(
                        new Texture(building.getImagePath()));
                building.setButton(button);
                if (y > height / 6)
                    y -= height / 6;
                else {
                    y = 5 * height / 6;
                    x += width / 10;
                }
                building.getButton().addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        setSelected(building);
                        return true;
                    }
                });
                stage.addActor(building.getButton());
            }
        }

        // Добавление виджетов на экран
        stage.addActor(descBack);
        stage.addActor(BuildBtn);
        stage.addActor(selectedBuildingDescLbl);
        stage.addActor(selectedBuildingLbl);
        stage.addActor(priceLbl);
        stage.addActor(priceImg);
        stage.addActor(moneyImg);
        stage.addActor(moneyLbl);
        stage.addActor(closeBtn);
        Gdx.input.setInputProcessor(stage);
    }

    // Инициализация построек
    private  void createBuildings() {
        buildings = new ArrayList<>();
        if (region.isLandAccess()) {
            ResourceBuilding Farm = new ResourceBuilding(buildings_bundle.get("farm"),
                    buildings_bundle.get("farmDesc"), "food", Values.FARM_PRICE);
            Farm.setRequiredRes(new String[]{"crops", "cattle"});
            if (region.getResources().containsKey("crops") && !region.getResources().containsKey("cattle"))
                Farm.setRequiredRes(new String[]{"crops"});
            else if (!region.getResources().containsKey("crops") && region.getResources().containsKey("cattle"))
                Farm.setRequiredRes(new String[]{"cattle"});
            Farm.setImagePath("textures/farm.png");
            if (region.getResources().containsKey("crops") || region.getResources().containsKey("cattle"))
                buildings.add(Farm);

            ResourceBuilding Sawmill = new ResourceBuilding(buildings_bundle.get("sawmill"),
                    buildings_bundle.get("sawmillDesc"), "wood", Values.SAWMILL_PRICE);
            Sawmill.setRequiredRes(new String[]{"wood"});
            Sawmill.setImagePath("textures/sawmill.png");
            if (region.getResources().containsKey("wood"))
                buildings.add(Sawmill);

            ResourceBuilding OilBorehole = new ResourceBuilding(buildings_bundle.get("oilBorehole"),
                    buildings_bundle.get("oilBoreholeDesc"), "oil", Values.MINE_PRICE);
            OilBorehole.setRequiredRes(new String[]{"oil"});
            OilBorehole.setImagePath("textures/oil_borehole.png");
            if (region.getResources().containsKey("oil"))
                buildings.add(OilBorehole);

            ResourceBuilding GasBorehole = new ResourceBuilding(buildings_bundle.get("gasBorehole"),
                    buildings_bundle.get("gasBoreholeDesc"), "gas", Values.MINE_PRICE);
            GasBorehole.setRequiredRes(new String[]{"gas"});
            GasBorehole.setImagePath("textures/gas_borehole.png");
            if (region.getResources().containsKey("gas"))
                buildings.add(GasBorehole);

            ResourceBuilding CoalMine = new ResourceBuilding(buildings_bundle.get("coalMine"),
                    buildings_bundle.get("coalMineDesc"), "coal", Values.MINE_PRICE);
            CoalMine.setRequiredRes(new String[]{"coal"});
            CoalMine.setImagePath("textures/coal_mine.png");
            if (region.getResources().containsKey("coal"))
                buildings.add(CoalMine);

            ResourceBuilding IronMine = new ResourceBuilding(buildings_bundle.get("ironMine"),
                    buildings_bundle.get("ironMineDesc"), "iron", Values.MINE_PRICE);
            IronMine.setRequiredRes(new String[]{"iron"});
            IronMine.setImagePath("textures/iron_mine.png");
            if (region.getResources().containsKey("iron"))
                buildings.add(IronMine);

            ResourceBuilding AluminumMine = new ResourceBuilding(buildings_bundle.get("aluminumMine"),
                    buildings_bundle.get("aluminumMineDesc"), "aluminum", Values.MINE_PRICE);
            AluminumMine.setRequiredRes(new String[]{"aluminum"});
            AluminumMine.setImagePath("textures/aluminum_mine.png");
            if (region.getResources().containsKey("aluminum"))
                buildings.add(AluminumMine);

            // Финансовые постройки
            MoneyBuilding Market = new MoneyBuilding(buildings_bundle.get("market"),
                    buildings_bundle.get("marketDesc"), Values.MARKET_PRICE, Values.MARKET_EFF);
            Market.setImagePath("textures/market.png");
            buildings.add(Market);

            MoneyBuilding Bank = new MoneyBuilding(buildings_bundle.get("bank"),
                    buildings_bundle.get("bankDesc"), Values.BANK_PRICE, Values.BANK_EFF);
            Bank.setImagePath("textures/bank.png");
            buildings.add(Bank);

            MoneyBuilding StockMarket = new MoneyBuilding(buildings_bundle.get("stockMarket"),
                    buildings_bundle.get("stockMarketDesc"), Values.STOCK_MARKET_PRICE, Values.STOCK_MARKET_EFF);
            StockMarket.setImagePath("textures/stock_market.png");
            buildings.add(StockMarket);

            // Министерства
            MinistryBuilding MinistryOfTruth = new MinistryBuilding(buildings_bundle.get("minTruth"),
                    buildings_bundle.get("minTruthDesc"), "minTruth", Values.MINISTRY_PRICE);
            MinistryOfTruth.setImagePath("textures/min_truth.png");
            buildings.add(MinistryOfTruth);

            MinistryBuilding MinistryOfLove = new MinistryBuilding(buildings_bundle.get("minLove"),
                    buildings_bundle.get("minLoveDesc"), "minLove", Values.MINISTRY_PRICE);
            MinistryOfLove.setImagePath("textures/min_love.png");
            buildings.add(MinistryOfLove);

            MinistryBuilding MinistryOfPlenty = new MinistryBuilding(buildings_bundle.get("minPlenty"),
                    buildings_bundle.get("minPlentyDesc"), "minPlenty", Values.MINISTRY_PRICE);
            MinistryOfPlenty.setImagePath("textures/min_plenty.png");
            buildings.add(MinistryOfPlenty);

            MinistryBuilding MinistryOfPeace = new MinistryBuilding(buildings_bundle.get("minPeace"),
                    buildings_bundle.get("minPeaceDesc"), "minPeace", Values.MINISTRY_PRICE);
            MinistryOfPeace.setImagePath("textures/briefcase.png");
            buildings.add(MinistryOfPeace);

            MinistryBuilding ThoughtPolice = new MinistryBuilding(buildings_bundle.get("thoughtPolice"),
                    buildings_bundle.get("thoughtPoliceDesc"), "thoughtPolice", Values.POLICE_PRICE);
            ThoughtPolice.setImagePath("textures/thought_police.png");
            buildings.add(ThoughtPolice);

            MinistryBuilding Laboratory = new MinistryBuilding(buildings_bundle.get("laboratory"),
                    buildings_bundle.get("laboratoryDesc"), "science", Values.MINISTRY_PRICE);
            Laboratory.setImagePath("textures/flask.png");
            buildings.add(Laboratory);

            ResourceBuilding Cannery = new ResourceBuilding(buildings_bundle.get("cannery"),
                    buildings_bundle.get("canneryDesc"), "canned_food", Values.CANNERY_PRICE);
            Cannery.setRequiredRes(new String[]{"food"});
            Cannery.setImagePath("textures/canned_food.png");
            buildings.add(Cannery);

            ResourceBuilding HhgFactory = new ResourceBuilding(buildings_bundle.get("hhgFactory"),
                    buildings_bundle.get("hhgFactoryDesc"), "household_goods", Values.PLANT_PRICE);
            HhgFactory.setRequiredRes(new String[]{"wood", "plastic"});
            HhgFactory.setImagePath("textures/household_goods.png");
            buildings.add(HhgFactory);

            ResourceBuilding SteelPlant = new ResourceBuilding(buildings_bundle.get("steelPlant"),
                    buildings_bundle.get("steelPlantDesc"), "steel", Values.PLANT_PRICE);
            SteelPlant.setRequiredRes(new String[]{"iron", "coal"});
            SteelPlant.setImagePath("textures/steel.png");
            buildings.add(SteelPlant);

            ResourceBuilding PigIronPlant = new ResourceBuilding(buildings_bundle.get("pigIronPlant"),
                    buildings_bundle.get("pigIronPlantDesc"), "pigIron", Values.PLANT_PRICE);
            PigIronPlant.setRequiredRes(new String[]{"iron", "coal"});
            PigIronPlant.setImagePath("textures/pigIron.png");
            buildings.add(PigIronPlant);

            ResourceBuilding PlasticPlant = new ResourceBuilding(buildings_bundle.get("plasticPlant"),
                    buildings_bundle.get("plasticPlantDesc"), "plastic", Values.PLANT_PRICE);
            PlasticPlant.setRequiredRes(new String[]{"oil"});
            PlasticPlant.setImagePath("textures/plastic.png");
            buildings.add(PlasticPlant);

            ResourceBuilding Armory = new ResourceBuilding(buildings_bundle.get("weaponPlant"),
                    buildings_bundle.get("weaponPlantDesc"), "weapon", Values.MILITARY_PLANT_PRICE);
            Armory.setRequiredRes(new String[]{"steel", "aluminum", "pigIron"});
            Armory.setImagePath("textures/weapon.png");
            buildings.add(Armory);

            ResourceBuilding AircraftPlant = new ResourceBuilding(buildings_bundle.get("planePlant"),
                    buildings_bundle.get("planePlantDesc"), "plane", Values.MILITARY_PLANT_PRICE);
            AircraftPlant.setRequiredRes(new String[]{"aluminum", "steel", "pigIron"});
            AircraftPlant.setImagePath("textures/plane.png");
            buildings.add(AircraftPlant);

            ResourceBuilding TankFactory = new ResourceBuilding(buildings_bundle.get("tankPlant"),
                    buildings_bundle.get("tankPlantDesc"), "tank", Values.MILITARY_PLANT_PRICE);
            TankFactory.setRequiredRes(new String[]{"aluminum", "steel", "pigIron"});
            TankFactory.setImagePath("textures/tank.png");
            buildings.add(TankFactory);

            ResourceBuilding ArtilleryPlant = new ResourceBuilding(buildings_bundle.get("artilleryPlant"),
                    buildings_bundle.get("artilleryPlantDesc"), "artillery", Values.MILITARY_PLANT_PRICE);
            ArtilleryPlant.setRequiredRes(new String[]{"aluminum", "steel", "pigIron"});
            ArtilleryPlant.setImagePath("textures/artillery.png");
            buildings.add(ArtilleryPlant);

        }
        if (region.isWaterAccess()) {
            ResourceBuilding FishProduction = new ResourceBuilding(buildings_bundle.get("fishProd"),
                    buildings_bundle.get("fishProdDesc"), "food", Values.FARM_PRICE);
            FishProduction.setRequiredRes(new String[]{"fish"});
            FishProduction.setImagePath("textures/fish_prod.png");
            if (region.getResources().containsKey("fish"))
                buildings.add(FishProduction);
        }
        if (region.isWaterAccess() && region.isLandAccess()) {
            ResourceBuilding Shipyard = new ResourceBuilding(buildings_bundle.get("shipPlant"),
                    buildings_bundle.get("shipPlantDesc"), "ship", Values.MILITARY_PLANT_PRICE);
            Shipyard.setRequiredRes(new String[]{"aluminum", "steel", "pigIron"});
            Shipyard.setImagePath("textures/ship.png");
            buildings.add(Shipyard);
        }
    }

    // Вывод информации о выбранной постройке
    private  void setSelected(Building building) {
        selectedBuilding = building;
        selectedBuildingLbl.setText(building.getName());
        selectedBuildingLbl.setPosition(2.63f*width/3 - building.getName().length()*15,
                3.95f*height/5);
        selectedBuildingDescLbl.setText(" " + building.getDescription());
        showPrice();
        BuildBtn.setVisible(true);
    }

    private  void showPrice() {
        if (selectedBuilding.getPrice() > gameData.getPlayer().getMoney()) {
            priceLblStyle.font = Styles.defaultFont;
        }
        else {
            priceLblStyle.font = Styles.greenFont;
        }
        priceLbl.setStyle(priceLblStyle);
        priceLbl.setText(selectedBuilding.getPrice());
        priceLbl.setVisible(true);
        priceImg.setVisible(true);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Colors.BACKGROUND.r, Colors.BACKGROUND.g, Colors.BACKGROUND.b, Colors.BACKGROUND.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.dispose();
        for (Skin skin: skins)
            skin.dispose();
        skins.clear();
    }

    @Override
    public void dispose() {

    }
}
