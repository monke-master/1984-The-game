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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class TroopsScreen implements Screen {
    Stage stage;
    float height, width;
    ArrayList<Skin> skins;
    Game game;
    GameData gameData;
    I18NBundle ui_bundle;

    public TroopsScreen(Game game, GameData gameData) {
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
        Skin skin_close = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton closeBtn = new ImageButton(skin_close);
        closeBtn.setSize(width/10, height/10);
        closeBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/close.png")));
        closeBtn.setPosition(9*width/10, 9*height/10);
        closeBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                game.setScreen(gameData.getGameScreen());
                return true;
            }
        });
        skins.add(skin_close);

        Label infoLbl = new Label(ui_bundle.get("chooseType"), Styles.defaultLbl);
        infoLbl.setSize(4*width/5, height/10);
        infoLbl.setPosition(width/10, 9*height/10);
        infoLbl.setAlignment(Align.center);

        Skin skin1 = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin1);
        ImageButton armyBtn = new ImageButton(skin1);
        armyBtn.setSize(width/4, height/1.5f);
        armyBtn.setPosition(width/16, height/7);
        armyBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/army.png")));
        armyBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                game.setScreen(gameData.getArmyScreen());
                return true;
            }
        });
        Label armyLbl = new Label(ui_bundle.get("army"), Styles.defaultLbl);
        armyLbl.setSize(width/4, height/10);
        armyLbl.setPosition(width/16, height/7);
        armyLbl.setAlignment(Align.center);

        Skin skin2 = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin2);
        ImageButton fleetBtn = new ImageButton(skin2);
        fleetBtn.setSize(width/4, height/1.5f);
        fleetBtn.setPosition(3*width/8, height/7);
        fleetBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/fleet.png")));
        fleetBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                game.setScreen(gameData.getFleetScreen());
                return true;
            }
        });

        Label fleetLbl = new Label(ui_bundle.get("fleet"), Styles.defaultLbl);
        fleetLbl.setSize(width/4, height/10);
        fleetLbl.setPosition(3*width/8, height/7);
        fleetLbl.setAlignment(Align.center);

        Skin skin3 = new Skin(Gdx.files.internal("skin/ui.json"));
        skins.add(skin3);
        ImageButton squadronBtn = new ImageButton(skin3);
        squadronBtn.setSize(width/4, height/1.5f);
        squadronBtn.setPosition(11*width/16, height/7);
        squadronBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/squadron.png")));
        squadronBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                game.setScreen(gameData.getSquadronScreen());
                return true;
            }
        });

        Label squadronLbl = new Label(ui_bundle.get("squadron"), Styles.defaultLbl);
        squadronLbl.setSize(width/4, height/10);
        squadronLbl.setPosition(11*width/16, height/7);
        squadronLbl.setAlignment(Align.center);

        stage.addActor(closeBtn);
        stage.addActor(armyBtn);
        stage.addActor(armyLbl);
        stage.addActor(fleetBtn);
        stage.addActor(fleetLbl);
        stage.addActor(squadronBtn);
        stage.addActor(squadronLbl);
        stage.addActor(infoLbl);

        Gdx.input.setInputProcessor(stage);


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
