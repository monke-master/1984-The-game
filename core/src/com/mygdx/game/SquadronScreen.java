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

public class SquadronScreen implements Screen {
    Stage stage;
    float height, width;
    ArrayList<Skin> skins;
    Game game;
    GameData gameData;
    I18NBundle ui_bundle;
    Label fighterSum, bomberSum, stormtrooperSum;
    Label fighterReq, bomberReq, stormtrooperReq;
    Slider fighterSlider, bomberSlider, stormtrooperSlider;
    Boolean fighterAvailable, bomberAvailable, stormtrooperAvailable;

    public SquadronScreen(Game game, GameData gameData) {
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


        // Истребители
        Image fighterImg = new Image(new Texture("textures/fighter.png"));
        fighterImg.setSize(width/15, height/10);
        fighterImg.setPosition(width/50, 7*height/10);
        stage.addActor(fighterImg);

        Label fighterLbl = new Label(ui_bundle.get("fighter"), Styles.defaultLbl);
        fighterLbl.setSize(width/10, height/10);
        fighterLbl.setPosition(width/10, 7*height/10);
        stage.addActor(fighterLbl);

        float max1 = Math.min(gameData.getPlayer().getMilitaryResources().get("plane"),
                Values.MAX_FIGHTERS);
        if (max1 > 0) {
            fighterAvailable = true;
            fighterSlider = new Slider(0, max1, max1 / 10, false, skin);
            fighterSlider.setSize(width/4, height/7);
            fighterSlider.setPosition(1.3f*width/4, 6.8f*height/10);
            stage.addActor(fighterSlider);

            fighterSum = new Label("0", Styles.defaultLbl);
            fighterSum.setSize(width/10, height/10);
            fighterSum.setPosition(width/1.7f, 7*height/10);
            stage.addActor(fighterSum);

            fighterReq = new Label("0x", Styles.defaultLbl);
            fighterReq.setSize(width/10, height/10);
            fighterReq.setPosition(width/1.55f, 7*height/10);
            stage.addActor(fighterReq);

            Image planeImg = new Image(new Texture("textures/plane.png"));
            planeImg.setSize(width/10, height/10);
            planeImg.setPosition(width/1.25f, 7*height/10);
            stage.addActor(planeImg);

        }
        else {
            Label noRes = new Label(ui_bundle.get("notEnoughRes"), Styles.defaultLbl);
            noRes.setSize(width/10, height/10);
            noRes.setPosition(width/4, 7f*height/10);
            stage.addActor(noRes);
            fighterAvailable = false;
        }

        // Бомбардировщики
        Image bomberImg = new Image(new Texture("textures/bomber.png"));
        bomberImg.setSize(width / 17, height / 10);
        bomberImg.setPosition(width / 50, height / 2);
        stage.addActor(bomberImg);

        Label bomberLbl = new Label(ui_bundle.get("bomber"), Styles.defaultLbl);
        bomberLbl.setSize(width / 10, height / 10);
        bomberLbl.setPosition(width / 10, height / 2);
        stage.addActor(bomberLbl);

        float max2 = Math.min(gameData.getPlayer().getMilitaryResources().get("plane"),
                Values.MAX_BOMBERS);
        if (max2 > 0) {
            bomberAvailable = true;
            bomberSlider = new Slider(0, max2, max2/10, false, skin);
            bomberSlider.setSize(width / 4, height / 7);
            bomberSlider.setPosition(1.3f*width/4, 0.48f * height);
            stage.addActor(bomberSlider);

            bomberSum = new Label("0", Styles.defaultLbl);
            bomberSum.setSize(width / 10, height / 10);
            bomberSum.setPosition(width/1.7f, 0.5f * height);
            stage.addActor(bomberSum);

            bomberReq = new Label("0x", Styles.defaultLbl);
            bomberReq.setSize(width / 10, height / 10);
            bomberReq.setPosition(width/1.55f, height / 2);
            stage.addActor(bomberReq);

            Image planeImg = new Image(new Texture("textures/plane.png"));
            planeImg.setSize(width/10, height/10);
            planeImg.setPosition(width/1.25f, height / 2);
            stage.addActor(planeImg);

        }
        else {
            Label noRes = new Label(ui_bundle.get("notEnoughRes"), Styles.defaultLbl);
            noRes.setSize(width/10, height/10);
            noRes.setPosition(width/4, 0.5f*height);
            stage.addActor(noRes);
            stormtrooperAvailable = false;
        }


        // Штурмовики
        Image stormtrooperImg = new Image(new Texture("textures/stormtrooper.png"));
        stormtrooperImg.setSize(width / 17, height / 10);
        stormtrooperImg.setPosition(width / 50, 3 * height / 10);
        stage.addActor(stormtrooperImg);

        Label stormtrooperLbl = new Label(ui_bundle.get("stormtrooper"), Styles.defaultLbl);
        stormtrooperLbl.setSize(width / 10, height / 10);
        stormtrooperLbl.setPosition(width / 10, 3 * height / 10);
        stage.addActor(stormtrooperLbl);

        float max3 = Math.min(gameData.getPlayer().getMilitaryResources().get("plane"),
                Values.MAX_STORMTROOPERS);
        if (max3 > 0) {
            stormtrooperAvailable = true;

            stormtrooperSlider = new Slider(0, max3, max3/10, false, skin);
            stormtrooperSlider.setSize(width / 4, height / 7);
            stormtrooperSlider.setPosition(1.3f*width/4, 2.8f * height / 10);
            stage.addActor(stormtrooperSlider);

            stormtrooperSum = new Label("0", Styles.defaultLbl);
            stormtrooperSum.setSize(width / 10, height / 10);
            stormtrooperSum.setPosition(width/1.7f, 3 * height / 10);
            stage.addActor(stormtrooperSum);

            stormtrooperReq = new Label("0x", Styles.defaultLbl);
            stormtrooperReq.setSize(width / 10, height / 10);
            stormtrooperReq.setPosition(width/1.55f, 3 * height / 10);
            stage.addActor(stormtrooperReq);

            Image stormtrooperImage = new Image(new Texture("textures/plane.png"));
            stormtrooperImage.setSize(width/10, height/10);
            stormtrooperImage.setPosition(width/1.25f, 3 * height / 10);
            stage.addActor(stormtrooperImage);

        }
        else {
            Label noRes = new Label(ui_bundle.get("notEnoughRes"), Styles.defaultLbl);
            noRes.setSize(width/10, height/10);
            noRes.setPosition(width/4, 3f*height/10);
            stage.addActor(noRes);
            bomberAvailable = false;
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
                int fighters = 0, bombers = 0, stormtroopers = 0;
                if (fighterAvailable) {
                    force += fighterSlider.getValue()* Values.FIGHTERS_FORCE;
                    health += fighterSlider.getValue()* Values.FIGHTERS_HEALTH;
                    fighters = (int) fighterSlider.getValue();
                }
                if (bomberAvailable) {
                    force += bomberSlider.getValue()* Values.BOMBERS_FORCE;
                    health += bomberSlider.getValue()* Values.BOMBERS_HEALTH;
                    bombers = (int) bomberSlider.getValue();
                }
                if (stormtrooperAvailable) {
                    force += stormtrooperSlider.getValue()* Values.STORMTROOPERS_FORCE;
                    health += stormtrooperSlider.getValue()* Values.STORMTROOPERS_HEALTH;
                    stormtroopers = (int) stormtrooperSlider.getValue();
                }
                Squadron army = new Squadron(force, health);
                if (fighters != 0)
                    army.getDivisions().put("fighter", fighters);
                if (bombers != 0)
                    army.getDivisions().put("bomber", bombers);
                if (stormtroopers != 0)
                    army.getDivisions().put("stormtrooper", stormtroopers);
                int have = gameData.getPlayer().getMilitaryResources().get("plane");
                int need = bombers + fighters + stormtroopers;
                if (have >= need) {
                    gameData.getPlayer().getMilitaryResources().put("plane", have - need);
                    gameData.getGameScreen().setSelectedTroop(army);
                    gameData.getGameScreen().setPlacingMode(true);
                    game.setScreen(gameData.getGameScreen());
                }

            }

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        if (bomberAvailable || stormtrooperAvailable || fighterAvailable)
            stage.addActor(placeBtn);

        Gdx.input.setInputProcessor(stage);

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Colors.BACKGROUND.r, Colors.BACKGROUND.g, Colors.BACKGROUND.b, Colors.BACKGROUND.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (fighterAvailable) {
            fighterSum.setText((int) fighterSlider.getValue());
            fighterReq.setText(ui_bundle.get("required") + " " +
                    (int)fighterSlider.getValue() + "x");
        }
        if (bomberAvailable) {
            bomberSum.setText((int) bomberSlider.getValue());
            bomberReq.setText(ui_bundle.get("required") + " " +
                    (int)bomberSlider.getValue() + "x");
        }
        if (stormtrooperAvailable) {
            stormtrooperSum.setText((int) stormtrooperSlider.getValue());
            stormtrooperReq.setText(ui_bundle.get("required") + " " +
                    (int)stormtrooperSlider.getValue() + "x");
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
