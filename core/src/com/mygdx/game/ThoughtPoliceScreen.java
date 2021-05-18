package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttribute;
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

public class ThoughtPoliceScreen implements Screen {
    GameData gameData;
    Game game;
    Stage stage;
    float height, width;
    ArrayList<Decree> decrees ;
    ArrayList<Skin> skins;
    Decree selectedDecree;
    Label selectedDecreeLbl, selectedDecreeDescLbl, requiredTechLbl, priceLbl, pointsLbl;
    Label.LabelStyle priceLblStyle;
    I18NBundle decrees_bundle, ui_bundle, tech_bundle;
    Image priceImg;
    TextButton issueBtn;

    public ThoughtPoliceScreen(Game game1, GameData gameData1) {
        // Начальные значения
        game = game1;
        gameData = gameData1;

        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();

        decrees_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/decrees"));
        ui_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/ui_strings"));
        tech_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/technologies"));
        priceLblStyle = new Label.LabelStyle();
        priceLblStyle.font = Styles.defaultFont;

        skins = new ArrayList<>();


    }

    public void createDecrees() {
        decrees = new ArrayList<>();
        // Указы
        Decree SurveillanceCam = new Decree(decrees_bundle.get("surveillanceCam"),
                decrees_bundle.get("surveillanceCamDesc"), Values.CAMERAS_PRICE, 100);
        Skin skin_sc = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton surveillanceCamBtn = new ImageButton(skin_sc);
        surveillanceCamBtn.setSize(width/10, height/10);
        surveillanceCamBtn.setPosition(0, 3*height/5);
        surveillanceCamBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/camera.png")));
        SurveillanceCam.setButton(surveillanceCamBtn);
        SurveillanceCam.addRequiredTech(gameData.getTechnologies().get(tech_bundle.get("camera")));
        decrees.add(SurveillanceCam);
        skins.add(skin_sc);

        Decree Eavesdropping = new Decree(decrees_bundle.get("eavesdropping"),
                decrees_bundle.get("eavesdroppingDesc"), Values.EAVESDROPPING_PRICE, 70);
        Skin skin_e = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton eavesdroppingBtn = new ImageButton(skin_e);
        eavesdroppingBtn.setSize(width/10, height/10);
        eavesdroppingBtn.setPosition(0, 1.9f*height/5);
        eavesdroppingBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/microphone.png")));
        Eavesdropping.setButton(eavesdroppingBtn);
        Eavesdropping.addRequiredTech(gameData.getTechnologies().get(tech_bundle.get("microphone")));
        decrees.add(Eavesdropping);
        skins.add(skin_e);

        Decree SurveillanceTS = new Decree(decrees_bundle.get("surveillanceTS"),
                decrees_bundle.get("surveillanceTSDesc"), Values.SURVEILLANCE_TS_PRICE, 1000);
        Skin skin_ts = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton surveillanceTSBtn = new ImageButton(skin_ts);
        surveillanceTSBtn.setSize(width/10, height/10);
        surveillanceTSBtn.setPosition(1.5f*width/10, 2.45f*height/5);
        surveillanceTSBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/tracking_ts.png")));
        SurveillanceTS.setButton(surveillanceTSBtn);
        SurveillanceTS.addRequiredTech(gameData.getTechnologies().get(tech_bundle.get("trackingTS")));
        SurveillanceTS.addRequiredDecree(Eavesdropping);
        SurveillanceTS.addRequiredDecree(SurveillanceCam);
        decrees.add(SurveillanceTS);
        skins.add(skin_ts);

        Decree SurveillanceTC = new Decree(decrees_bundle.get("surveillanceTC"),
                decrees_bundle.get("surveillanceTCDesc"), Values.SURVEILLANCE_TC_PRICE, 600);
        Skin skin_tc = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton surveillanceTCBtn = new ImageButton(skin_tc);
        surveillanceTCBtn.setSize(width/10, height/10);
        surveillanceTCBtn.setPosition(0, 1.35f*height/5);
        surveillanceTCBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/thought_controller.png")));
        SurveillanceTC.setButton(surveillanceTCBtn);
        SurveillanceTC.addRequiredTech(gameData.getTechnologies().get(tech_bundle.get("thoughtController")));
        decrees.add(SurveillanceTC);
        skins.add(skin_tc);

        Decree SurveillanceCTC = new Decree(decrees_bundle.get("surveillanceCTC"),
                decrees_bundle.get("surveillanceCTCDesc"), Values.SURVEILLANCE_CTC_PRICE, 2000);
        Skin skin_ctc = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton surveillanceCTCBtn = new ImageButton(skin_ctc);
        surveillanceCTCBtn.setSize(width/10, height/10);
        surveillanceCTCBtn.setPosition(1.5f*width/10, 1.35f*height/5);
        surveillanceCTCBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/thought_controller2.png")));
        SurveillanceCTC.setButton(surveillanceCTCBtn);
        SurveillanceCTC.addRequiredTech(gameData.getTechnologies().get(tech_bundle.get("compactTC")));
        SurveillanceCTC.addRequiredDecree(SurveillanceTC);
        decrees.add(SurveillanceCTC);
        skins.add(skin_ctc);

        Decree SurveillanceTTS = new Decree(decrees_bundle.get("surveillanceTTS"),
                decrees_bundle.get("surveillanceTTSDesc"), Values.SURVEILLANCE_TTS_PRICE, 1500);
        Skin skin_tts = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton surveillanceTTSBtn = new ImageButton(skin_tts);
        surveillanceTTSBtn.setSize(width/10, height/10);
        surveillanceTTSBtn.setPosition(3*width/10, 1.9f*height/5);
        surveillanceTTSBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/thought_telescreen.png")));
        SurveillanceTTS.setButton(surveillanceTTSBtn);
        SurveillanceTTS.addRequiredTech(gameData.getTechnologies().get(tech_bundle.get("thoughtTS")));
        SurveillanceTTS.addRequiredDecree(SurveillanceCTC);
        SurveillanceTTS.addRequiredDecree(SurveillanceTS);
        decrees.add(SurveillanceTTS);
        skins.add(skin_tts);

        Decree SurveillanceWTS = new Decree(decrees_bundle.get("surveillanceWTS"),
                decrees_bundle.get("surveillanceWTSDesc"), Values.SURVEILLANCE_WTS_PRICE, 10000);
        Skin skin_wts = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton surveillanceWTSBtn = new ImageButton(skin_wts);
        surveillanceWTSBtn.setSize(width/10, height/10);
        surveillanceWTSBtn.setPosition(4.5f*width/10, 1.9f*height/5);
        surveillanceWTSBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/wrist_telescreen.png")));
        SurveillanceWTS.setButton(surveillanceWTSBtn);
        SurveillanceWTS.addRequiredTech(gameData.getTechnologies().get(tech_bundle.get("wristTelescreen")));
        SurveillanceWTS.addRequiredDecree(SurveillanceTTS);
        decrees.add(SurveillanceWTS);
        skins.add(skin_wts);

        Decree Chipping = new Decree(decrees_bundle.get("chipping"), decrees_bundle.get("chippingDesc"),
                Values.CHIPPING_PRICE,100000);
        Skin skin_c = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton chippingBtn = new ImageButton(skin_c);
        chippingBtn.setSize(width/10, height/10);
        chippingBtn.setPosition(6f*width/10, 1.9f*height/5);
        chippingBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/chip.png")));
        Chipping.setButton(chippingBtn);
        Chipping.addRequiredTech(gameData.getTechnologies().get(tech_bundle.get("implantableChip")));
        Chipping.addRequiredDecree(SurveillanceWTS);
        decrees.add(Chipping);
        skins.add(skin_c);

        Decree Spy1 = new Decree(decrees_bundle.get("spy1"), decrees_bundle.get("spyDesc"),
                Values.SPY_PRICE, 100);
        Skin skin_s1 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton spy1Btn = new ImageButton(skin_s1);
        spy1Btn.setSize(width/10, height/10);
        spy1Btn.setPosition(0, 0.8f*height/5);
        spy1Btn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/spy1.png")));
        Spy1.setButton(spy1Btn);
        Spy1.addRequiredTech(gameData.getTechnologies().get(tech_bundle.get("spyBugs")));
        decrees.add(Spy1);
        skins.add(skin_s1);

        Decree Spy2 = new Decree(decrees_bundle.get("spy2"), decrees_bundle.get("spyDesc"),
                Values.SPY_PRICE*4, 400);
        Skin skin_s2 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton spy2Btn = new ImageButton(skin_s2);
        spy2Btn.setSize(width/10, height/10);
        spy2Btn.setPosition(1.5f*width/10, 0.8f*height/5);
        spy2Btn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/spy2.png")));
        Spy2.setButton(spy2Btn);
        Spy2.addRequiredTech(gameData.getTechnologies().get(tech_bundle.get("spyBugs2")));
        Spy2.addRequiredDecree(Spy1);
        decrees.add(Spy2);
        skins.add(skin_s2);

        Decree Spy3 = new Decree(decrees_bundle.get("spy3"), decrees_bundle.get("spyDesc"),
                Values.SPY_PRICE*7, 700);
        Skin skin_s3 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton spy3Btn = new ImageButton(skin_s3);
        spy3Btn.setSize(width/10, height/10);
        spy3Btn.setPosition(3f*width/10, 0.8f*height/5);
        spy3Btn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/spy3.png")));
        Spy3.setButton(spy3Btn);
        Spy3.addRequiredTech(gameData.getTechnologies().get(tech_bundle.get("spyBugs3")));
        Spy3.addRequiredDecree(Spy2);
        decrees.add(Spy3);
        skins.add(skin_s3);

        for (final Decree decree: decrees) {
            if (gameData.getPlayer().getThoughtPolice().getDecrees().contains(decree.getName())) {
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

    public void setSelected(Decree decree) {
        selectedDecree = decree;
        selectedDecreeLbl.setText(decree.getName());
        selectedDecreeDescLbl.setText(" " + decree.getDescription());
        selectedDecreeLbl.setPosition(2.63f*width/3 - decree.getName().length()*15,
                3.95f*height/5);
        if (!decree.isAvailable() && !decree.isIssued()) {
            requiredTechLbl.setText(ui_bundle.get("requiresTech") + "\n" +
                    decree.getRequiredTech());

        } else
            requiredTechLbl.setText("");
        if (selectedDecree.isIssued()) {
            priceLbl.setText(ui_bundle.get("decreeIssued"));
            priceLblStyle.font = Styles.greenFont;
        }
        else {
            if (selectedDecree.getPrice() > gameData.getPlayer().getThoughtPolice().getPoints())
                priceLblStyle.font = Styles.defaultFont;
            else
                priceLblStyle.font = Styles.greenFont;
            priceLbl.setStyle(priceLblStyle);
            priceLbl.setText(String.valueOf(selectedDecree.getPrice()));
        }

        issueBtn.setVisible(true);
        requiredTechLbl.setVisible(true);
        priceImg.setVisible(true);
        priceLbl.setVisible(true);
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
                gameData.getPlayer().getThoughtPolice().getPoints()+ ". " + ui_bundle.get("perDay") + ":" +
                gameData.getPlayer().getThoughtPolice().getPointsPD(), Styles.defaultLbl);
        pointsLbl.setSize(width/3, height/10);
        pointsLbl.setPosition(width/15, height/40);

        priceLblStyle = new Label.LabelStyle();
        priceLblStyle.font = Styles.greenFont;

        pointsLbl = new Label(ui_bundle.get("total") + ":" +
                gameData.getPlayer().getMinPlenty().getPoints() + ". " + ui_bundle.get("perDay") + ":" +
                gameData.getPlayer().getMinPlenty().getPointsPD(), Styles.defaultLbl);
        pointsLbl.setSize(width/3, height/10);
        pointsLbl.setPosition(width/15, height/40);

        priceLblStyle = new Label.LabelStyle();
        priceLblStyle.font = Styles.greenFont;

        priceLbl = new Label("", priceLblStyle);
        priceLbl.setSize(width/10, height/10);
        priceLbl.setPosition(3.05f*width/4, 0.7f*height/5);

        priceImg = new Image(new Texture(Gdx.files.internal("textures/briefcase.png")));
        priceImg.setSize(width/19, height/14);
        priceImg.setPosition(2.8f*width/4, 0.8f*height/5);
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

        Label.LabelStyle requiredStyle = new Label.LabelStyle();
        requiredStyle.font = Styles.defaultFont;

        requiredTechLbl = new Label("", requiredStyle);
        requiredTechLbl.setSize(width/4, height/10);
        requiredTechLbl.setPosition(2.8f*width/4, 1.5f*height/5);

        issueBtn = new TextButton(ui_bundle.get("research"), Styles.defaultBtn);
        issueBtn.setSize(width/5, height/10);
        issueBtn.setPosition(3*width/4, height/15);

        // Нажатие на кнопки
        issueBtn.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                if (selectedDecree.isAvailable() && selectedDecree.getPrice() <=
                        gameData.getPlayer().getThoughtPolice().getPoints() && !selectedDecree.isIssued()) {
                    gameData.getPlayer().getThoughtPolice().setPoints(
                            gameData.getPlayer().getThoughtPolice().getPoints() - selectedDecree.getPrice());
                    gameData.getPlayer().getThoughtPolice().setEfficiency(
                            gameData.getPlayer().getThoughtPolice().getEfficiency() +
                                    selectedDecree.getBonus()*Values.DECREE_EFF);
                    gameData.getPlayer().getThoughtPolice().getDecrees().add(selectedDecree.getName());
                    selectedDecree.setIssued(true);
                    priceLbl.setText(ui_bundle.get("decreeIssued"));
                    priceLblStyle.fontColor = Color.GREEN;
                    Skin skin1 = new Skin(Gdx.files.internal("skin/ui.json"));
                    ImageButton imageButton = new ImageButton(skin1, "researched");
                    imageButton.setSize(width / 10, height / 10);
                    imageButton.setPosition(selectedDecree.getButton().getX(), selectedDecree.getButton().getY());
                    imageButton.getStyle().imageUp = selectedDecree.getButton().getStyle().imageUp;
                    final Decree temp_dec = selectedDecree;
                    temp_dec.setButton(imageButton);
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

        for (final Decree decree: decrees) {
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
        stage.addActor(closeBtn);
        stage.addActor(issueBtn);
        stage.addActor(requiredTechLbl);
        stage.addActor(priceLbl);
        stage.addActor(priceImg);
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
                gameData.getPlayer().getThoughtPolice().getPoints() + ". " + ui_bundle.get("perDay") + ":" +
                gameData.getPlayer().getThoughtPolice().getPointsPD());
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
