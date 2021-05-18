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

public class FleetScreen implements Screen {
    Stage stage;
    float height, width;
    ArrayList<Skin> skins;
    Game game;
    GameData gameData;
    I18NBundle ui_bundle;
    Label destroyerSum, cruiserSum, submarineSum;
    Label destroyerReq, cruiserReq, submarineReq;
    Slider destroyerSlider, cruiserSlider, submarineSlider;
    Boolean destroyerAvailable, cruiserAvailable, submarineAvailable;

    public FleetScreen(Game game, GameData gameData) {
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


        // Эсминцы
        Image destroyerImg = new Image(new Texture("textures/destroyer.png"));
        destroyerImg.setSize(width/15, height/10);
        destroyerImg.setPosition(width/50, 7*height/10);
        stage.addActor(destroyerImg);

        Label destroyerLbl = new Label(ui_bundle.get("destroyer"), Styles.defaultLbl);
        destroyerLbl.setSize(width/10, height/10);
        destroyerLbl.setPosition(width/10, 7*height/10);
        stage.addActor(destroyerLbl);

        float max1 = Math.min(gameData.getPlayer().getMilitaryResources().get("ship"),
                Values.MAX_DESTROYERS);
        if (max1 > 0) {
            destroyerAvailable = true;
            destroyerSlider = new Slider(0, max1, max1 / 10, false, skin);
            destroyerSlider.setSize(width/4, height/7);
            destroyerSlider.setPosition(width/4, 6.8f*height/10);
            stage.addActor(destroyerSlider);

            destroyerSum = new Label("0", Styles.defaultLbl);
            destroyerSum.setSize(width/10, height/10);
            destroyerSum.setPosition(width/1.9f, 7*height/10);
            stage.addActor(destroyerSum);

            destroyerReq = new Label("0x", Styles.defaultLbl);
            destroyerReq.setSize(width/10, height/10);
            destroyerReq.setPosition(width/1.7f, 7*height/10);
            stage.addActor(destroyerReq);

            Image shipImg = new Image(new Texture("textures/ship.png"));
            shipImg.setSize(width/17, height/10);
            shipImg.setPosition(width/1.3f, 7*height/10);
            stage.addActor(shipImg);

        }
        else {
            Label noRes = new Label(ui_bundle.get("notEnoughRes"), Styles.defaultLbl);
            noRes.setSize(width/10, height/10);
            noRes.setPosition(width/4, 7f*height/10);
            stage.addActor(noRes);
            destroyerAvailable = false;
        }

        // Крейсеры
        Image cruiserImg = new Image(new Texture("textures/cruiser.png"));
        cruiserImg.setSize(width / 17, height / 10);
        cruiserImg.setPosition(width / 50, height / 2);
        stage.addActor(cruiserImg);

        Label cruiserLbl = new Label(ui_bundle.get("cruiser"), Styles.defaultLbl);
        cruiserLbl.setSize(width / 10, height / 10);
        cruiserLbl.setPosition(width / 10, height / 2);
        stage.addActor(cruiserLbl);

        float max2 = Math.min(gameData.getPlayer().getMilitaryResources().get("ship"),
                Values.MAX_CRUISERS);
        if (max2 > 0) {
            cruiserAvailable = true;

            cruiserSlider = new Slider(0, max2, max2/10, false, skin);
            cruiserSlider.setSize(width / 4, height / 7);
            cruiserSlider.setPosition(width / 4, 0.48f * height);
            stage.addActor(cruiserSlider);

            cruiserSum = new Label("0", Styles.defaultLbl);
            cruiserSum.setSize(width / 10, height / 10);
            cruiserSum.setPosition(width / 1.9f, 0.5f * height);
            stage.addActor(cruiserSum);

            cruiserReq = new Label("0x", Styles.defaultLbl);
            cruiserReq.setSize(width / 10, height / 10);
            cruiserReq.setPosition(width / 1.7f, height / 2);
            stage.addActor(cruiserReq);

            Image shipImg = new Image(new Texture("textures/ship.png"));
            shipImg.setSize(width / 17, height / 10);
            shipImg.setPosition(width / 1.3f, height / 2);
            stage.addActor(shipImg);

        }
        else {
            Label noRes = new Label(ui_bundle.get("notEnoughRes"), Styles.defaultLbl);
            noRes.setSize(width/10, height/10);
            noRes.setPosition(width/4, 0.5f*height);
            stage.addActor(noRes);
            submarineAvailable = false;
        }


        // Подводные лодки
        Image submarineImg = new Image(new Texture("textures/submarine.png"));
        submarineImg.setSize(width / 17, height / 10);
        submarineImg.setPosition(width / 50, 3 * height / 10);
        stage.addActor(submarineImg);

        Label submarineLbl = new Label(ui_bundle.get("submarine"), Styles.defaultLbl);
        submarineLbl.setSize(width / 10, height / 10);
        submarineLbl.setPosition(width / 10, 3 * height / 10);
        stage.addActor(submarineLbl);

        float max3 = Math.min(gameData.getPlayer().getMilitaryResources().get("ship"),
                Values.MAX_SUBMARINES);
        if (max3 > 0) {
            submarineAvailable = true;

            submarineSlider = new Slider(0, max3, max3/10, false, skin);
            submarineSlider.setSize(width / 4, height / 7);
            submarineSlider.setPosition(width / 4, 2.8f * height / 10);
            stage.addActor(submarineSlider);

            submarineSum = new Label("0", Styles.defaultLbl);
            submarineSum.setSize(width / 10, height / 10);
            submarineSum.setPosition(width / 1.9f, 3 * height / 10);
            stage.addActor(submarineSum);

            submarineReq = new Label("0x", Styles.defaultLbl);
            submarineReq.setSize(width / 10, height / 10);
            submarineReq.setPosition(width / 1.7f, 3 * height / 10);
            stage.addActor(submarineReq);

            Image shipImage = new Image(new Texture("textures/ship.png"));
            shipImage.setSize(width / 17, height / 10);
            shipImage.setPosition(width / 1.3f, 3 * height / 10);
            stage.addActor(shipImage);

        }
        else {
            Label noRes = new Label(ui_bundle.get("notEnoughRes"), Styles.defaultLbl);
            noRes.setSize(width/10, height/10);
            noRes.setPosition(width/4, 3f*height/10);
            stage.addActor(noRes);
            cruiserAvailable = false;
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
                int destroyer = 0, cruiser = 0, submarine = 0;
                if (destroyerAvailable) {
                    force += destroyerSlider.getValue()* Values.DESTROYER_FORCE;
                    health += destroyerSlider.getValue()* Values.DESTROYER_HEALTH;
                    destroyer = (int) destroyerSlider.getValue();
                }
                if (cruiserAvailable) {
                    force += cruiserSlider.getValue()* Values.CRUISERS_FORCE;
                    health += cruiserSlider.getValue()* Values.CRUISERS_HEALTH;
                    cruiser = (int) cruiserSlider.getValue();
                }
                if (submarineAvailable) {
                    force += submarineSlider.getValue()* Values.SUBMARINES_FORCE;
                    health += submarineSlider.getValue()* Values.SUBMARINES_HEALTH;
                    submarine = (int) submarineSlider.getValue();
                }
                Fleet fleet = new Fleet(force, health);
                if (destroyer != 0)
                    fleet.getDivisions().put("destroyer", destroyer);
                if (cruiser != 0)
                    fleet.getDivisions().put("cruiser", cruiser);
                if (submarine != 0)
                    fleet.getDivisions().put("submarine", submarine);
                int have = gameData.getPlayer().getMilitaryResources().get("ship");
                int need = destroyer + submarine + cruiser;
                if (have >= need) {
                    gameData.getPlayer().getMilitaryResources().put("ship", have - need);
                    gameData.getGameScreen().setSelectedTroop(fleet);
                    gameData.getGameScreen().setPlacingMode(true);
                    game.setScreen(gameData.getGameScreen());
                }
            }

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        if (cruiserAvailable || submarineAvailable || destroyerAvailable)
            stage.addActor(placeBtn);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(Colors.BACKGROUND.r, Colors.BACKGROUND.g, Colors.BACKGROUND.b, Colors.BACKGROUND.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (destroyerAvailable) {
            destroyerSum.setText((int) destroyerSlider.getValue());
            destroyerReq.setText(ui_bundle.get("required") + " " +
                    (int)destroyerSlider.getValue() + "x");
        }
        if (cruiserAvailable) {
            cruiserSum.setText((int) cruiserSlider.getValue());
            cruiserReq.setText(ui_bundle.get("required") + " " +
                    (int)cruiserSlider.getValue() + "x");
        }
        if (submarineAvailable) {
            submarineSum.setText((int) submarineSlider.getValue());
            submarineReq.setText(ui_bundle.get("required") + " " +
                    (int)submarineSlider.getValue() + "x");
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
