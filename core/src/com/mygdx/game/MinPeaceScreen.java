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

public class MinPeaceScreen implements Screen {

    GameData gameData;
    Game game;
    Stage stage;
    float height, width;
    ArrayList<ResourceDecree> decrees;
    ArrayList<Skin> skins;
    ResourceDecree selectedDecree;
    Label selectedDecreeLbl, selectedDecreeDescLbl, priceLbl, pointsLbl;
    Label.LabelStyle priceLblStyle;
    I18NBundle decrees_bundle, ui_bundle;
    Image priceImg;
    TextButton issueBtn;

    public MinPeaceScreen(Game game, GameData gameData) {
        // Начальные значения
        this.game = game;
        this.gameData = gameData;

        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();

        decrees_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/decrees"));
        ui_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/ui_strings"));
        priceLblStyle = new Label.LabelStyle();
        priceLblStyle.font = Styles.defaultFont;
        skins = new ArrayList<>();
    }

    public void createDecrees() {
        decrees = new ArrayList<>();
        ResourceDecree WeaponProd = new ResourceDecree(decrees_bundle.get("weaponProd"),
                decrees_bundle.get("weaponProdDesc"), Values.WAR_DECREE_PRICE,"weapon",
                Values.WAR_DECREE_EFF);
        Skin skin_mw = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin_mw);
        ImageButton weaponBtn = new ImageButton(skin_mw);
        weaponBtn.setSize(width/10, height/10);
        weaponBtn.setPosition(0, 3*height/5);
        weaponBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/weapon.png")));
        WeaponProd.setButton(weaponBtn);
        decrees.add(WeaponProd);

        ResourceDecree TankProd = new ResourceDecree(decrees_bundle.get("tankProd"),
                decrees_bundle.get("tankProdDesc"), Values.WAR_DECREE_PRICE, "tank",
                Values.WAR_DECREE_EFF);
        Skin skin_tp = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin_tp);
        ImageButton tankBtn = new ImageButton(skin_tp);
        tankBtn.setSize(width/10, height/10);
        tankBtn.setPosition(1.5f*width/10, 3*height/5);
        tankBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/tank.png")));
        TankProd.setButton(tankBtn);
        decrees.add(TankProd);

        ResourceDecree ArtilleryProd = new ResourceDecree(decrees_bundle.get("artilleryProd"),
                decrees_bundle.get("artilleryProdDesc"), Values.WAR_DECREE_PRICE, "artillery",
                Values.WAR_DECREE_EFF);
        Skin skin_ap = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin_ap);
        ImageButton artProdBtn = new ImageButton(skin_ap);
        artProdBtn.setSize(width/10, height/10);
        artProdBtn.setPosition(3f*width/10, 3*height/5);
        artProdBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/artillery.png")));
        ArtilleryProd.setButton(artProdBtn);
        decrees.add(ArtilleryProd);

        ResourceDecree PlaneProd = new ResourceDecree(decrees_bundle.get("planeProd"),
                decrees_bundle.get("planeProdDesc"), Values.WAR_DECREE_PRICE, "plane",
                Values.WAR_DECREE_EFF);
        Skin skin_pp = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin_pp);
        ImageButton planeBtn = new ImageButton(skin_pp);
        planeBtn.setSize(width/10, height/10);
        planeBtn.setPosition(4.5f*width/10, 3*height/5);
        planeBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/plane.png")));
        PlaneProd.setButton(planeBtn);
        decrees.add(PlaneProd);

        ResourceDecree ShipProd = new ResourceDecree(decrees_bundle.get("shipProd"),
                decrees_bundle.get("shipProdDesc"), Values.WAR_DECREE_PRICE, "ship",
                Values.WAR_DECREE_EFF);
        Skin skin_sp = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin_sp);
        ImageButton shipBtn = new ImageButton(skin_sp);
        shipBtn.setSize(width/10, height/10);
        shipBtn.setPosition(6f*width/10, 3*height/5);
        shipBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/ship.png")));
        ShipProd.setButton(shipBtn);
        decrees.add(ShipProd);

        ResourceDecree Infantry = new ResourceDecree(decrees_bundle.get("infantry"),
                decrees_bundle.get("infantryDesc"), Values.WAR_DECREE_PRICE,"infantry",
                Values.WAR_DECREE_EFF);
        Skin skin_i = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin_i);
        ImageButton infantryBtn = new ImageButton(skin_i);
        infantryBtn.setSize(width/10, height/10);
        infantryBtn.setPosition(0, 2.45f*height/5);
        infantryBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/infantry.png")));
        Infantry.setButton(infantryBtn);
        decrees.add(Infantry);

        ResourceDecree Tanks = new ResourceDecree(decrees_bundle.get("tanks"),
                decrees_bundle.get("tanksDesc"), Values.WAR_DECREE_PRICE, "tanks",
                Values.WAR_DECREE_EFF);
        Skin skin_t = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin_t);
        ImageButton tanksBtn = new ImageButton(skin_t);
        tanksBtn.setSize(width/10, height/10);
        tanksBtn.setPosition(1.5f*width/10, 2.45f*height/5);
        tanksBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/infantry.png")));
        Tanks.setButton(tanksBtn);
        decrees.add(Tanks);

        ResourceDecree Artillery = new ResourceDecree(decrees_bundle.get("artillery"),
                decrees_bundle.get("artilleryDesc"), Values.WAR_DECREE_PRICE, "artillery_division",
                Values.WAR_DECREE_EFF);
        Skin skin_a = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin_a);
        ImageButton artilleryBtn = new ImageButton(skin_a);
        artilleryBtn.setSize(width/10, height/10);
        artilleryBtn.setPosition(3*width/10, 2.45f*height/5);
        artilleryBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/artillery.png")));
        Artillery.setButton(artilleryBtn);
        decrees.add(Artillery);

        ResourceDecree Destroyers = new ResourceDecree(decrees_bundle.get("destroyer"),
                decrees_bundle.get("destroyerDesc"), Values.WAR_DECREE_PRICE, "destroyers",
                Values.WAR_DECREE_EFF);
        Skin skin_d = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin_d);
        ImageButton destroyersBtn = new ImageButton(skin_d);
        destroyersBtn.setSize(width/10, height/10);
        destroyersBtn.setPosition(0, 1.9f*height/5);
        destroyersBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/destroyer.png")));
        Destroyers.setButton(destroyersBtn);
        decrees.add(Destroyers);

        ResourceDecree Cruisers = new ResourceDecree(decrees_bundle.get("cruiser"),
                decrees_bundle.get("cruiserDesc"), Values.WAR_DECREE_PRICE, "cruisers",
                Values.WAR_DECREE_EFF);
        Skin skin_c = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin_c);
        ImageButton cruisersBtn = new ImageButton(skin_c);
        cruisersBtn.setSize(width/10, height/10);
        cruisersBtn.setPosition(1.5f*width/10, 1.9f*height/5);
        cruisersBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/cruiser.png")));
        Cruisers.setButton(cruisersBtn);
        decrees.add(Cruisers);

        ResourceDecree Submarines = new ResourceDecree(decrees_bundle.get("submarine"),
                decrees_bundle.get("submarineDesc"), Values.WAR_DECREE_PRICE, "submarines",
                Values.WAR_DECREE_EFF);
        Skin skin_s = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin_s);
        ImageButton submarinesBtn = new ImageButton(skin_s);
        submarinesBtn.setSize(width/10, height/10);
        submarinesBtn.setPosition(3f*width/10, 1.9f*height/5);
        submarinesBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/submarine.png")));
        Submarines.setButton(submarinesBtn);
        decrees.add(Submarines);

        ResourceDecree Fighters = new ResourceDecree(decrees_bundle.get("fighter"),
                decrees_bundle.get("fighterDesc"), Values.WAR_DECREE_PRICE, "fighters",
                Values.WAR_DECREE_EFF);
        Skin skin_f = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin_f);
        ImageButton fightersBtn = new ImageButton(skin_f);
        fightersBtn.setSize(width/10, height/10);
        fightersBtn.setPosition(0, 1.35f*height/5);
        fightersBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/fighter.png")));
        Fighters.setButton(fightersBtn);
        decrees.add(Fighters);

        ResourceDecree Bombers = new ResourceDecree(decrees_bundle.get("bomber"),
                decrees_bundle.get("bomberDesc"), Values.WAR_DECREE_PRICE, "bombers",
                Values.WAR_DECREE_EFF);
        Skin skin_b = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin_b);
        ImageButton bombersBtn = new ImageButton(skin_b);
        bombersBtn.setSize(width/10, height/10);
        bombersBtn.setPosition(1.5f*width/10, 1.35f*height/5);
        bombersBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/bomber.png")));
        Bombers.setButton(bombersBtn);
        decrees.add(Bombers);

        ResourceDecree Stormtroopers = new ResourceDecree(decrees_bundle.get("stormtrooper"),
                decrees_bundle.get("stormtrooperDesc"), Values.WAR_DECREE_PRICE, "stormtroopers",
                Values.WAR_DECREE_EFF);
        Skin skin_st = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin_st);
        ImageButton stormtrooperBtn = new ImageButton(skin_st);
        stormtrooperBtn.setSize(width/10, height/10);
        stormtrooperBtn.setPosition(3f*width/10, 1.35f*height/5);
        stormtrooperBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/stormtrooper.png")));
        Stormtroopers.setButton(stormtrooperBtn);
        decrees.add(Stormtroopers);

        for (final ResourceDecree decree: decrees) {
            if (gameData.getPlayer().getMinPeace().getDecrees().contains(decree.getName())) {
                decree.setIssued(true);
                Skin skin1 = new Skin(Gdx.files.internal("skin/ui.json"));
                ImageButton imageButton = new ImageButton(skin1, "researched");
                imageButton.setSize(width / 10, height / 10);
                imageButton.setPosition(decree.getButton().getX(), decree.getButton().getY());
                imageButton.getStyle().imageUp = decree.getButton().getStyle().imageUp;
                decree.setButton(imageButton);
                decree.getButton().addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Sounds.Click.play();
                        setSelected(decree);
                        return true;
                    }
                });
                skins.add(skin1);
            }
        }

    }

    public void setSelected(ResourceDecree decree) {
        selectedDecree = decree;
        selectedDecreeLbl.setText(decree.getName());
        selectedDecreeDescLbl.setText(" " + decree.getDescription());
        selectedDecreeLbl.setPosition(2.6f*width/3 - decree.getName().length()*15,
                3.95f*height/5);
        if (selectedDecree.isIssued()) {
            priceLblStyle.font = Styles.greenFont;
            priceLbl.setStyle(priceLblStyle);
            priceLbl.setText(ui_bundle.get("decreeIssued"));

        }
        else {
            if (selectedDecree.getPrice() > gameData.getPlayer().getMinLove().getPoints()) {
                priceLblStyle.font = Styles.defaultFont;
            }
            else
                priceLblStyle.font = Styles.greenFont;
            priceLbl.setStyle(priceLblStyle);
            priceLbl.setText(String.valueOf(selectedDecree.getPrice()));
        }
        priceImg.setVisible(true);
        priceLbl.setVisible(true);
        issueBtn.setVisible(true);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        createDecrees();
        // Кнопка выхода
        Skin skin_close = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton closeBtn = new ImageButton(skin_close);
        closeBtn.setSize(width/10, height/10);
        closeBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/close.png")));
        closeBtn.setPosition(9*width/10, 9*height/10);
        skins.add(skin_close);
        closeBtn.addListener(new InputListener() {
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                game.setScreen(gameData.getMinistriesScreen());
            }
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        Image background = new Image(new Texture("textures/screen_back.png"));
        background.setSize(width, height);
        background.setPosition(0, 0);

        // Виджеты очков
        final Image pointsImg = new Image(new Texture(Gdx.files.internal("textures/briefcase.png")));
        pointsImg.setSize(width/20, height/11);
        pointsImg.setPosition(width/100, height/40);

        pointsLbl = new Label(ui_bundle.get("total") + ":" +
                gameData.getPlayer().getMinLove().getPoints() + ". " + ui_bundle.get("perDay") + ":" +
                gameData.getPlayer().getMinLove().getPointsPD(), Styles.defaultLbl);
        pointsLbl.setSize(width/3, height/10);
        pointsLbl.setPosition(width/15, height/40);

        priceLblStyle = new Label.LabelStyle();
        priceLblStyle.font = Styles.greenFont;

        priceLbl = new Label("", priceLblStyle);
        priceLbl.setSize(width/10, height/10);
        priceLbl.setPosition(3*width/4, 1.4f*height/5);

        priceImg = new Image(new Texture(Gdx.files.internal("textures/briefcase.png")));
        priceImg.setSize(width/19, height/14);
        priceImg.setPosition(2.8f*width/4, 1.5f*height/5);
        priceImg.setVisible(false);

        // Виджеты выбранного указа
        Image descBack = new Image(new Texture(Gdx.files.internal("textures/back.png")));
        descBack.setSize(1.1f*width/3, 9.5f*height/10);
        descBack.setPosition(1.95f*width/3, height/10);

        selectedDecreeLbl = new Label(ui_bundle.get("selectBuilding"), Styles.defaultLbl);
        selectedDecreeLbl.setSize(width/4, height/10);
        selectedDecreeLbl.setPosition(2.30f*width/3, 3.95f*height/5);
        selectedDecreeLbl.setAlignment(Align.left);

        selectedDecreeDescLbl = new Label("", Styles.defaultLbl);
        selectedDecreeDescLbl.setSize(width/4, 2*height/3);
        selectedDecreeDescLbl.setPosition(2.7f*width/4, 1.3f*height/5);
        selectedDecreeDescLbl.setAlignment(Align.left);

        issueBtn = new TextButton(ui_bundle.get("research"), Styles.defaultBtn);
        issueBtn.setSize(width/5, height/10);
        issueBtn.setPosition(3*width/4, height/15);

        // Нажатие на кнопки
        issueBtn.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                if (selectedDecree.isAvailable() && selectedDecree.getPrice() <=
                        gameData.getPlayer().getMinPeace().getPoints() && !selectedDecree.isIssued()) {
                    gameData.getPlayer().getMinPeace().setPoints(
                            gameData.getPlayer().getMinPeace().getPoints() - selectedDecree.getPrice());
                    gameData.getPlayer().getMinPeace().getDecrees().add(selectedDecree.getName());
                    selectedDecree.setIssued(true);
                    String resource = selectedDecree.getResource();
                    float bonus = selectedDecree.getBonus();
                    if (gameData.getPlayer().getMilitaryResources().containsKey(resource)) {
                        gameData.getPlayer().getProducingBonuses().put(resource, bonus);
                    }
                    else {
                        switch (resource) {
                            case "infantry":
                                Values.INFANTRY_FORCE*=bonus;
                                Values.INFANTRY_HEALTH*=bonus;
                                break;
                            case "tanks":
                                Values.TANK_FORCE*=bonus;
                                Values.TANK_HEALTH*=bonus;
                                break;
                            case "artillery_division":
                                Values.ARTILLERY_FORCE*=bonus;
                                Values.ARTILLERY_HEALTH*=bonus;
                                break;
                            case "destroyer":
                                Values.DESTROYER_FORCE*=bonus;
                                Values.DESTROYER_HEALTH*=bonus;
                                break;
                            case "bombers":
                                Values.BOMBERS_FORCE*=bonus;
                                Values.BOMBERS_HEALTH*=bonus;
                                break;
                            case "fighters":
                                Values.FIGHTERS_FORCE*=bonus;
                                Values.FIGHTERS_HEALTH*=bonus;
                                break;
                            case "submarines":
                                Values.SUBMARINES_FORCE*=bonus;
                                Values.SUBMARINES_HEALTH*=bonus;
                                break;
                            case "stormtroopers":
                                Values.STORMTROOPERS_FORCE*=bonus;
                                Values.STORMTROOPERS_HEALTH*=bonus;
                                break;
                            case "cruisers":
                                Values.CRUISERS_FORCE*=bonus;
                                Values.CRUISERS_HEALTH*=bonus;
                                break;
                        }
                    }
                    priceLblStyle.font = Styles.greenFont;
                    priceLbl.setStyle(priceLblStyle);
                    priceLbl.setText(ui_bundle.get("decreeIssued"));
                    Skin skin1 = new Skin(Gdx.files.internal("skin/ui.json"));
                    ImageButton b = new ImageButton(skin1, "researched");
                    b.setSize(width / 10, height / 10);
                    b.setPosition(selectedDecree.getButton().getX(), selectedDecree.getButton().getY());
                    b.getStyle().imageUp = selectedDecree.getButton().getStyle().imageUp;
                    final ResourceDecree temp_dec = selectedDecree;
                    temp_dec.setButton(b);
                    temp_dec.getButton().addListener(new InputListener() {
                        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                            Sounds.Click.play();
                            setSelected(temp_dec);
                            return true;
                        }
                    });
                    skins.add(skin1);
                }
                return true;
            }

        });

        for (final ResourceDecree decree: decrees) {
            decree.getButton().addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Sounds.Click.play();
                    setSelected(decree);
                    return true;
                }
            });
        }

        // Добавление виджетов на экран
        stage.addActor(background);
        stage.addActor(descBack);
        stage.addActor(selectedDecreeLbl);
        stage.addActor(selectedDecreeDescLbl);
        stage.addActor(pointsImg);
        stage.addActor(pointsLbl);
        stage.addActor(priceImg);
        stage.addActor(priceLbl);
        stage.addActor(issueBtn);
        stage.addActor(closeBtn);
        Gdx.input.setInputProcessor(stage);

    }

    // Отрисовка
    @Override
    public void render(float delta) {
        // Добавление открытых указов на экран
        for (Decree decree: decrees) {
            if (decree.isOpened())
                stage.addActor(decree.getButton());
        }
        pointsLbl.setText(ui_bundle.get("total") + ":" +
                gameData.getPlayer().getMinLove().getPoints() + ". " + ui_bundle.get("perDay") + ":" +
                gameData.getPlayer().getMinLove().getPointsPD());
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
        decrees.clear();
    }

    @Override
    public void dispose() {

    }


}

