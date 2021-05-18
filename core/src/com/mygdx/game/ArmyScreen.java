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
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;


public class ArmyScreen implements Screen {
    private Stage stage;
    private float height, width;
    private ArrayList<Skin> skins;
    private Game game;
    private GameData gameData;
    private I18NBundle ui_bundle;
    private Label infantrySum, tankSum, artillerySum;
    private Label infWeaponReq, tanksReq, artilleryReq;
    private Slider infantrySlider, tankSlider, artillerySlider;
    private Boolean infantryAvailable, tankAvailable, artilleryAvailable;

    public ArmyScreen(Game game, GameData gameData) {
        this.game = game;
        this.gameData = gameData;
        // Начальные значения
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        skins = new ArrayList<>();

        ui_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/ui_strings"));
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Skin skin = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin);

        // Кнопки
        Skin skin_close = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton closeBtn = new ImageButton(skin_close);
        closeBtn.setSize(width/10, height/10);
        closeBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/close.png")));
        closeBtn.setPosition(9*width/10, 9*height/10);
        closeBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                game.setScreen(gameData.getTroopsScreen());
            }

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        skins.add(skin_close);
        stage.addActor(closeBtn);


        // Пехота
        Image infantryImg = new Image(new Texture("textures/infantry.png"));
        infantryImg.setSize(width/15, height/10);
        infantryImg.setPosition(width/50, 7*height/10);
        stage.addActor(infantryImg);

        Label infantryLbl = new Label(ui_bundle.get("infantry"), Styles.defaultLbl);
        infantryLbl.setSize(width/10, height/10);
        infantryLbl.setPosition(width/10, 7*height/10);
        stage.addActor(infantryLbl);

        float max1 = Math.min(gameData.getPlayer().getMilitaryResources().get("weapon"),
                Values.MAX_INFANTRY);
        if (max1 > 0) {
            infantryAvailable = true;
            infantrySlider = new Slider(0, max1, max1 / 10, false, skin);
            infantrySlider.setSize(width/4, height/7);
            infantrySlider.setPosition(width/4, 6.8f*height/10);
            stage.addActor(infantrySlider);

            infantrySum = new Label("0", Styles.defaultLbl);
            infantrySum.setSize(width/10, height/10);
            infantrySum.setPosition(width/1.9f, 7*height/10);
            stage.addActor(infantrySum);

            infWeaponReq = new Label("0x", Styles.defaultLbl);
            infWeaponReq.setSize(width/10, height/10);
            infWeaponReq.setPosition(width/1.7f, 7*height/10);
            stage.addActor(infWeaponReq);

            Image infWeaponImg = new Image(new Texture("textures/weapon.png"));
            infWeaponImg.setSize(width/10, height/10);
            infWeaponImg.setPosition(width/1.33f, 7*height/10);
            stage.addActor(infWeaponImg);

        }
        else {
            Label noRes = new Label(ui_bundle.get("notEnoughRes"), Styles.defaultLbl);
            noRes.setSize(width/10, height/10);
            noRes.setPosition(width/4, 7f*height/10);
            stage.addActor(noRes);
            infantryAvailable = false;
        }

        // Танки
        Image tankImg = new Image(new Texture("textures/tank.png"));
        tankImg.setSize(width / 17, height / 10);
        tankImg.setPosition(width / 50, height / 2);
        stage.addActor(tankImg);

        Label tankLbl = new Label(ui_bundle.get("tank"), Styles.defaultLbl);
        tankLbl.setSize(width / 10, height / 10);
        tankLbl.setPosition(width / 10, height / 2);
        stage.addActor(tankLbl);

        float max2 = Math.min(gameData.getPlayer().getMilitaryResources().get("tank"), Values.MAX_TANKS);
        if (max2 > 0) {
            tankAvailable = true;

            tankSlider = new Slider(0, max2, max2/10, false, skin);
            tankSlider.setSize(width / 4, height / 7);
            tankSlider.setPosition(width / 4, 0.48f * height);
            stage.addActor(tankSlider);

            tankSum = new Label("0", Styles.defaultLbl);
            tankSum.setSize(width / 10, height / 10);
            tankSum.setPosition(width / 1.9f, 0.5f * height);
            stage.addActor(tankSum);

            tanksReq = new Label("0x", Styles.defaultLbl);
            tanksReq.setSize(width / 10, height / 10);
            tanksReq.setPosition(width / 1.7f, height / 2);
            stage.addActor(tanksReq);

            Image tankReqImg = new Image(new Texture("textures/tank.png"));
            tankReqImg.setSize(width / 17, height / 10);
            tankReqImg.setPosition(width / 1.32f, height / 2);
            stage.addActor(tankReqImg);

        }
        else {
            Label noRes = new Label(ui_bundle.get("notEnoughRes"), Styles.defaultLbl);
            noRes.setSize(width/10, height/10);
            noRes.setPosition(width/4, 0.5f*height);
            stage.addActor(noRes);
            tankAvailable = false;
        }


        // Артиллерия
        Image artilleryImg = new Image(new Texture("textures/artillery.png"));
        artilleryImg.setSize(width / 17, height / 10);
        artilleryImg.setPosition(width / 50, 3 * height / 10);
        stage.addActor(artilleryImg);

        Label artilleryLbl = new Label(ui_bundle.get("artillery"), Styles.defaultLbl);
        artilleryLbl.setSize(width / 10, height / 10);
        artilleryLbl.setPosition(width / 10, 3 * height / 10);
        stage.addActor(artilleryLbl);

        float max3 = Math.min(gameData.getPlayer().getMilitaryResources().get("artillery"),
                Values.MAX_ARTILLERY);
        if (max3 > 0) {
            artilleryAvailable = true;

            artillerySlider = new Slider(0, max3, max3/10, false, skin);
            artillerySlider.setSize(width / 4, height / 7);
            artillerySlider.setPosition(width / 4, 2.8f * height / 10);
            stage.addActor(artillerySlider);

            artillerySum = new Label("0", Styles.defaultLbl);
            artillerySum.setSize(width / 10, height / 10);
            artillerySum.setPosition(width / 1.9f, 3 * height / 10);
            stage.addActor(artillerySum);

            artilleryReq = new Label("0x", Styles.defaultLbl);
            artilleryReq.setSize(width / 10, height / 10);
            artilleryReq.setPosition(width / 1.7f, 3 * height / 10);
            stage.addActor(artilleryReq);

            Image artillReqImage = new Image(new Texture("textures/artillery.png"));
            artillReqImage.setSize(width / 17, height / 10);
            artillReqImage.setPosition(width / 1.33f, 3 * height / 10);
            stage.addActor(artillReqImage);

        }
        else {
            Label noRes = new Label(ui_bundle.get("notEnoughRes"), Styles.defaultLbl);
            noRes.setSize(width/10, height/10);
            noRes.setPosition(width/4, 3f*height/10);
            stage.addActor(noRes);
            artilleryAvailable = false;
        }

        TextButton placeBtn = new TextButton(ui_bundle.get("place"), Styles.defaultBtn);
        placeBtn.setSize(1.2f*width/5, 1.3f*height/7);
        placeBtn.setPosition(3.5f*width/5, height/10);
        placeBtn.addListener(new InputListener() {
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                int force = 0;
                int health = 0;
                int infantry = 0, tanks = 0, artillery = 0;
                if (infantryAvailable) {
                    force += infantrySlider.getValue()* Values.INFANTRY_FORCE;
                    health += infantrySlider.getValue()* Values.INFANTRY_HEALTH;
                    infantry = (int) infantrySlider.getValue();
                }
                if (tankAvailable) {
                    force += tankSlider.getValue()* Values.TANK_FORCE;
                    health += tankSlider.getValue()* Values.TANK_HEALTH;
                    tanks = (int)tankSlider.getValue();
                }
                if (artilleryAvailable) {
                    force += artillerySlider.getValue()* Values.ARTILLERY_FORCE;
                    health += artillerySlider.getValue()* Values.ARTILLERY_HEALTH;
                    artillery = (int)artillerySlider.getValue();
                }
                Army army = new Army(force, health);
                if (infantry != 0)
                    army.getDivisions().put("infantry", infantry);
                if (tanks != 0)
                    army.getDivisions().put("tank", tanks);
                if (artillery != 0)
                    army.getDivisions().put("artillery", artillery);
                int have = gameData.getPlayer().getMilitaryResources().get("weapon");
                gameData.getPlayer().getMilitaryResources().put("weapon", have - infantry);
                have = gameData.getPlayer().getMilitaryResources().get("tank");
                gameData.getPlayer().getMilitaryResources().put("tank", have - tanks);
                have = gameData.getPlayer().getMilitaryResources().get("artillery");
                gameData.getPlayer().getMilitaryResources().put("artillery", have - artillery);
                gameData.getGameScreen().setSelectedTroop(army);
                gameData.getGameScreen().setPlacingMode(true);
                game.setScreen(gameData.getGameScreen());
            }

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        if (tankAvailable || artilleryAvailable || infantryAvailable)
            stage.addActor(placeBtn);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Colors.BACKGROUND.r, Colors.BACKGROUND.g, Colors.BACKGROUND.b, Colors.BACKGROUND.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (infantryAvailable) {
            infantrySum.setText((int) infantrySlider.getValue());
            infWeaponReq.setText(ui_bundle.get("required") + " " +
                    (int)infantrySlider.getValue() + "x");
        }
        if (tankAvailable) {
            tankSum.setText((int) tankSlider.getValue());
            tanksReq.setText(ui_bundle.get("required") + " " +
                    (int)tankSlider.getValue() + "x");
        }
        if (artilleryAvailable) {
            artillerySum.setText((int) artillerySlider.getValue());
            artilleryReq.setText(ui_bundle.get("required") + " " +
                    (int) artillerySlider.getValue() + "x");
        }
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
