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
import java.util.HashMap;

public class PropagandaResearchScreen implements Screen {
    Game game;
    Stage stage;
    float height, width;
    HashMap<String, Technology> technologies;
    Technology selectedTech;
    Label scienceLbl, selectedTechLbl, selectedTechDescLbl, priceLbl;
    TextButton researchBtn;
    Image priceImg;
    I18NBundle ui_bundle, tech_bundle;
    GameData gameData;
    ArrayList<Skin> skins;
    Label.LabelStyle priceLblStyle;

    public PropagandaResearchScreen(Game aGame, final GameData gameData) {
        // Начальные значения
        this.game = aGame;
        this.gameData = gameData;
        technologies = new HashMap<>();
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        skins = new ArrayList<>();
        tech_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/technologies"));
        ui_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/ui_strings"));
        stage = new Stage(new ScreenViewport());
        priceLblStyle = new Label.LabelStyle();
        priceLblStyle.font = Styles.defaultFont;
    }

    public void createTechnologies() {
        // Технологии слежки и пропаганды
        Skin skin_t = new Skin(Gdx.files.internal("skin/ui.json"));
        Technology Telescreen = new Technology(tech_bundle.get("telescreen"),
                tech_bundle.get("telescreenDesc"), Values.TELESCREEN_PRICE);
        ImageButton telescreenBtn = new ImageButton(skin_t);
        telescreenBtn.setSize(width/10, height/10);
        telescreenBtn.setPosition(0, 3*height/5);
        telescreenBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/telescreen.png")));
        Telescreen.setButton(telescreenBtn);
        technologies.put("Telescreen", Telescreen);
        skins.add(skin_t);

        Skin skin_micro = new Skin(Gdx.files.internal("skin/ui.json"));
        Technology Microphone = new Technology(tech_bundle.get("microphone"),
                tech_bundle.get("microphoneDesc"), Values.MICROPHONE_PRICE);
        ImageButton microphoneBtn = new ImageButton(skin_micro);
        microphoneBtn.setSize(width/10, height/10);
        microphoneBtn.setPosition(0, 2.45f*height/5);
        microphoneBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/microphone.png")));
        Microphone.setButton(microphoneBtn);
        technologies.put("Microphone", Microphone);
        skins.add(skin_micro);

        Skin skin_camera = new Skin(Gdx.files.internal("skin/ui.json"));
        Technology camera = new Technology(tech_bundle.get("camera"), tech_bundle.get("cameraDesc"),
                Values.CAMERA_PRICE);
        ImageButton cameraBtn = new ImageButton(skin_camera);
        cameraBtn.setSize(width/10, height/10);
        cameraBtn.setPosition(0, 1.90f*height/5);
        cameraBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/camera.png")));
        camera.setButton(cameraBtn);
        technologies.put("camera", camera);
        skins.add(skin_camera);

        Technology TrackingTS = new Technology(tech_bundle.get("trackingTS"),
                tech_bundle.get("trackingTSDesc"), Values.TRACKING_TS_PRICE);
        Skin skin_tts = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton trackingTSBtn = new ImageButton(skin_tts);
        trackingTSBtn.setSize(width/10, height/10);
        trackingTSBtn.setPosition(1.5f*width/10, 2.45f*height/5);
        trackingTSBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/tracking_ts.png")));
        TrackingTS.setButton(trackingTSBtn);
        TrackingTS.addRequiredTech(Telescreen);
        TrackingTS.addRequiredTech(Microphone);
        TrackingTS.addRequiredTech(camera);
        technologies.put("TrackingTS", TrackingTS);
        skins.add(skin_tts);

        Technology ThoughtController = new Technology(tech_bundle.get("thoughtController"),
                tech_bundle.get("thoughtControllerDesc"), Values.TC_PRICE);
        Skin skin_tc = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton thoughtContBtn = new ImageButton(skin_tc);
        thoughtContBtn.setSize(width/10, height/10);
        thoughtContBtn.setPosition(0, 1.35f*height/5);
        thoughtContBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/thought_controller.png")));
        ThoughtController.setButton(thoughtContBtn);
        technologies.put("ThoughtController", ThoughtController);
        skins.add(skin_tc);

        Technology CompactTC = new Technology(tech_bundle.get("compactTC"),
                tech_bundle.get("compactTCDesc"), Values.COMPACT_TC_PRICE);
        Skin skin_ctc = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton compactTCBtn = new ImageButton(skin_ctc);
        compactTCBtn.setSize(width/10, height/10);
        compactTCBtn.setPosition(1.5f*width/10, 1.35f*height/5);
        compactTCBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/thought_controller2.png")));
        CompactTC.setButton(compactTCBtn);
        CompactTC.addRequiredTech(ThoughtController);
        technologies.put("CompactTC", CompactTC);
        skins.add(skin_ctc);

        Technology ThoughtTS = new Technology(tech_bundle.get("thoughtTS"),
                tech_bundle.get("thoughtTSDesc"), Values.THOUGHT_TS_PRICE);
        Skin skin_thts = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton thoughtTSBtn = new ImageButton(skin_thts);
        thoughtTSBtn.setSize(width/10, height/10);
        thoughtTSBtn.setPosition(3*width/10, 1.90f*height/5);
        thoughtTSBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/thought_telescreen.png")));
        ThoughtTS.setButton(thoughtTSBtn);
        ThoughtTS.addRequiredTech(CompactTC);
        ThoughtTS.addRequiredTech(TrackingTS);
        technologies.put("ThoughtTS", ThoughtTS);
        skins.add(skin_thts);

        Technology SpyBugs1 = new Technology(tech_bundle.get("spyBugs"),
                tech_bundle.get("spyBugsDesc"), Values.SPY_BUGS_PRICE);
        Skin skin_sb = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton spyBugsBtn = new ImageButton(skin_sb);
        spyBugsBtn.setSize(width/10, height/10);
        spyBugsBtn.setPosition(0, 0.8f*height/5);
        spyBugsBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/bug1.png")));
        SpyBugs1.setButton(spyBugsBtn);
        technologies.put("SpyBugs1", SpyBugs1);
        skins.add(skin_sb);

        Technology SpyBugs2 = new Technology(tech_bundle.get("spyBugs2"),
                tech_bundle.get("spyBugs2Desc"), Values.SPY_BUGS_PRICE*2);
        Skin skin_sb2 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton spyBugsBtn2 = new ImageButton(skin_sb2);
        spyBugsBtn2.setSize(width/10, height/10);
        spyBugsBtn2.setPosition(1.5f*width/10, 0.8f*height/5);
        spyBugsBtn2.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/bug2.png")));
        SpyBugs2.setButton(spyBugsBtn2);
        SpyBugs2.addRequiredTech(SpyBugs1);
        technologies.put("SpyBugs2", SpyBugs2);
        skins.add(skin_sb2);

        Technology SpyBugs3 = new Technology(tech_bundle.get("spyBugs3"),
                tech_bundle.get("spyBugs3Desc"), Values.SPY_BUGS_PRICE*3);
        Skin skin_sb3 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton spyBugsBtn3 = new ImageButton(skin_sb3);
        spyBugsBtn3.setSize(width/10, height/10);
        spyBugsBtn3.setPosition(3f*width/10, 0.8f*height/5);
        spyBugsBtn3.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/bug3.png")));
        SpyBugs3.setButton(spyBugsBtn3);
        SpyBugs3.addRequiredTech(SpyBugs2);
        technologies.put("SpyBugs3", SpyBugs3);
        skins.add(skin_sb3);

        Technology WristTelescreen = new Technology(tech_bundle.get("wristTelescreen"),
                tech_bundle.get("wristTelescreenDesc"), Values.WRIST_TS_PRICE);
        Skin skin_wts = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton wristTelescreenBtn = new ImageButton(skin_wts);
        wristTelescreenBtn.setSize(width/10, height/10);
        wristTelescreenBtn.setPosition(4.5f*width/10, 1.35f*height/5);
        wristTelescreenBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/wrist_telescreen.png")));
        WristTelescreen.setButton(wristTelescreenBtn);
        WristTelescreen.addRequiredTech(ThoughtTS);
        WristTelescreen.addRequiredTech(SpyBugs3);
        technologies.put("WristTelescreen", WristTelescreen);
        skins.add(skin_wts);

        Technology ImplantableChip = new Technology(tech_bundle.get("implantableChip"),
                tech_bundle.get("implantableChipDesc"), Values.CHIP_PRICE);
        Skin skin_ic = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton implantableChipBtn = new ImageButton(skin_ic);
        implantableChipBtn.setSize(width/10, height/10);
        implantableChipBtn.setPosition(6*width/10, 1.35f*height/5);
        implantableChipBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/chip.png")));
        ImplantableChip.setButton(implantableChipBtn);
        ImplantableChip.addRequiredTech(WristTelescreen);
        technologies.put("ImplantableChip", ImplantableChip);
        skins.add(skin_ic);

        for (final Technology technology: technologies.values()) {
            if (gameData.getPlayer().getScience().getTechnologies().contains(technology.getName())) {
                technology.setResearched(true);
                gameData.getTechnologies().get(technology.getName()).setResearched(true);
                Skin skin1 = new Skin(Gdx.files.internal("skin/ui.json"));
                ImageButton b = new ImageButton(skin1, "researched");
                b.setSize(width / 10, height / 10);
                b.setPosition(technology.getButton().getX(), technology.getButton().getY());
                b.getStyle().imageUp = technology.getButton().getStyle().imageUp;
                technology.setButton(b);
                technology.getButton().addListener(new InputListener() {
                    public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                        Sounds.Click.play();
                        setSelected(technology);
                        return true;
                    }
                });
                skins.add(skin1);
            }
        }

    }

    public void setSelected(Technology technology) {
        selectedTech = technology;
        selectedTechLbl.setText(technology.getName());
        selectedTechLbl.setPosition(2.6f*width/3 - technology.getName().length()*15,
                3.95f*height/5);
        selectedTechDescLbl.setText(" " + technology.getDescription());
        if (selectedTech.isResearched()) {
            priceLblStyle.font = Styles.greenFont;
            priceLbl.setStyle(priceLblStyle);
            priceLbl.setText(ui_bundle.get("techResearched"));
        }
        else {
            if (selectedTech.getPrice() > gameData.getPlayer().getScience().getPoints())
                priceLblStyle.font = Styles.defaultFont;
            else
                priceLblStyle.font = Styles.greenFont;
            priceLbl.setStyle(priceLblStyle);
            priceLbl.setText(String.valueOf(selectedTech.getPrice()));
        }
        priceLbl.setSize(width/10, height/10);
        priceLbl.setPosition(3*width/4, 1.4f*height/5);
        priceImg.setVisible(true);
        researchBtn.setVisible(true);
        priceImg.setVisible(true);
        priceLbl.setVisible(true);
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        createTechnologies();

        Image background = new Image(new Texture("textures/screen_back.png"));
        background.setSize(width, height);
        background.setPosition(0, 0);

        Image scienceImg = new Image(new Texture("textures/flask.png"));
        scienceImg.setSize(width/17, height/10);
        scienceImg.setPosition(0, height/20);

        scienceLbl = new Label(ui_bundle.get("total") + ":" +
                gameData.getPlayer().getScience().getPoints() + ". " + ui_bundle.get("perDay") + ":" +
                gameData.getPlayer().getScience().getPointsPD(), Styles.defaultLbl);
        scienceLbl.setSize(width/3, height/10);
        scienceLbl.setPosition(width/20, height/40);

        Image descBack = new Image(new Texture(Gdx.files.internal("textures/back.png")));
        descBack.setSize(1.1f*width/3, 9.5f*height/10);
        descBack.setPosition(1.95f*width/3, height/10);

        // Виджеты для выбранной технологии
        selectedTechLbl = new Label(ui_bundle.get("selectTech"), Styles.defaultLbl);
        selectedTechLbl.setSize(width/4, height/10);
        selectedTechLbl.setPosition(2.30f*width/3, 3.95f*height/5);
        selectedTechLbl.setAlignment(Align.left);

        selectedTechDescLbl = new Label("", Styles.defaultLbl);
        selectedTechDescLbl.setSize(width/4, 2*height/3);
        selectedTechDescLbl.setPosition(2.7f*width/4, 1.3f*height/5);
        selectedTechDescLbl.setAlignment(Align.left);

        // Кнопка выхода в предыдущее меню
        Skin skin_close = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton closeBtn = new ImageButton(skin_close);
        closeBtn.setSize(width/10, height/10);
        closeBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/close.png")));
        closeBtn.setPosition(9*width/10, 9*height/10);
        closeBtn.addListener(new InputListener() {
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                game.setScreen(gameData.getGameScreen());
            }
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        researchBtn = new TextButton(ui_bundle.get("research"), Styles.defaultBtn);
        researchBtn.setSize(width/5, height/10);
        researchBtn.setPosition(3*width/4, height/15);

        priceLbl = new Label("", priceLblStyle);

        priceImg = new Image(new Texture(Gdx.files.internal("textures/flask.png")));
        priceImg.setSize(width/19, height/14);
        priceImg.setPosition(2.8f*width/4, 1.5f*height/5);
        priceImg.setVisible(false);

        // Нажатия на кнопки
        researchBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                if (selectedTech.getPrice() <= gameData.getPlayer().getScience().getPoints() &&
                        !selectedTech.isResearched()) {
                    gameData.getPlayer().getScience().setPoints(gameData.getPlayer().getScience().getPoints()
                            - selectedTech.getPrice());
                    selectedTech.setResearched(true);
                    gameData.getPlayer().getScience().getTechnologies().add(selectedTech.getName());
                    gameData.getTechnologies().get(selectedTech.getName()).setResearched(true);
                    priceLblStyle.font = Styles.greenFont;
                    priceLbl.setStyle(priceLblStyle);
                    priceLbl.setText(ui_bundle.get("techResearched"));
                    Skin skin1 = new Skin(Gdx.files.internal("skin/ui.json"));
                    ImageButton b = new ImageButton(skin1, "researched");
                    b.setSize(width / 10, height / 10);
                    b.setPosition(selectedTech.getButton().getX(), selectedTech.getButton().getY());
                    b.getStyle().imageUp = selectedTech.getButton().getStyle().imageUp;
                    final Technology temp_tech = selectedTech;
                    temp_tech.setButton(b);
                    temp_tech.getButton().addListener(new InputListener() {
                        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                            Sounds.Click.play();
                            setSelected(temp_tech);
                            return true;
                        }
                    });
                    skins.add(skin1);
                }
                return true;
            }
        });

        for (final Technology technology: technologies.values()) {
            technology.getButton().addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    Sounds.Click.play();
                    setSelected(technology);
                    return true;
                }
            });
        }

        // Добавление виджетов на экран
        stage.addActor(background);
        stage.addActor(descBack);
        stage.addActor(selectedTechLbl);
        stage.addActor(selectedTechDescLbl);
        stage.addActor(scienceImg);
        stage.addActor(scienceLbl);
        stage.addActor(priceImg);
        stage.addActor(priceLbl);
        stage.addActor(researchBtn);
        stage.addActor(closeBtn);

        Gdx.input.setInputProcessor(stage);
    }

    // Отрисовка
    @Override
    public void render(float delta) {
        // Добавление на экран открытых технологий
        for (Technology t: technologies.values()) {
            if (t.isOpened())
                stage.addActor(t.getButton());
        }
        Gdx.gl.glClearColor(Colors.BACKGROUND.r, Colors.BACKGROUND.g, Colors.BACKGROUND.b, Colors.BACKGROUND.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        scienceLbl.setText(ui_bundle.get("total") + ":" +
                gameData.getPlayer().getScience().getPoints() + ". " + ui_bundle.get("perDay") + ":" +
                gameData.getPlayer().getScience().getPointsPD());

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
        technologies.clear();
        skins.clear();
    }

    @Override
    public void dispose() {

    }


}

