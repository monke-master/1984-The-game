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
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

// Экран с выбором страны
public class ChooseCountryScreen implements Screen {

    private  Stage stage;
    private float height, width;
    private  ArrayList<Skin> skins;
    private  Game game;
    private  GameData gameData;
    private  I18NBundle ui_bundle, region_bundle;
    private  Label descLbl;
    private  String selectedCountry;
    private  ArrayList<String> countries;
    private  TextButton chooseBtn;
    private  Image image;

    public ChooseCountryScreen(Game game, GameData gameData) {
        this.game = game;
        this.gameData = gameData;
        // Начальные значения
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        skins = new ArrayList<>();
        ui_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/ui_strings"));
        region_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/region_names"));

        countries = new ArrayList<>();
        countries.add(region_bundle.get("oceania"));
        countries.add(region_bundle.get("eastasia"));
        countries.add(region_bundle.get("eurasia"));
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        Label chooseLbl = new Label(ui_bundle.get("chooseCountry"), Styles.bigLbl);
        chooseLbl.setSize(width/10, height/10);
        chooseLbl.setPosition(1.95f*width/5, 9*height/10);

        TextButton oceaniaBtn = new TextButton(region_bundle.get("oceania"), Styles.defaultBtn);
        oceaniaBtn.setSize(width/4, height/5);
        oceaniaBtn.setPosition(width/16, 0.7f*height);
        oceaniaBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                selectedCountry = region_bundle.get("oceania");
                showCountryInfo(region_bundle.get("oceaniaDesc"), "textures/oceania.png");
            }
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }

        });

        TextButton eurasiaBtn = new TextButton(region_bundle.get("eurasia"), Styles.defaultBtn);
        eurasiaBtn.setSize(width/4, height/5);
        eurasiaBtn.setPosition(3*width/8, 0.7f*height);
        eurasiaBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                selectedCountry = region_bundle.get("eurasia");
                showCountryInfo(region_bundle.get("eurasiaDesc"), "textures/eurasia.png");
            }
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        TextButton eastasiaBtn = new TextButton(region_bundle.get("eastasia"), Styles.defaultBtn);
        eastasiaBtn.setSize(width/4, height/5);
        eastasiaBtn.setPosition(11*width/16, 0.7f*height);
        eastasiaBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                selectedCountry = region_bundle.get("eastasia");
                showCountryInfo(region_bundle.get("eastasiaDesc"), "textures/eastasia.png");
            }
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        chooseBtn = new TextButton(ui_bundle.get("choose"), Styles.defaultBtn);
        chooseBtn.setSize(width/7, height/7);
        chooseBtn.setPosition(5.5f*width/7, height/20);
        chooseBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                countries.remove(selectedCountry);
                gameData.setPlayerName(selectedCountry);
                gameData.setComputer1Name(countries.get(0));
                gameData.setComputer2Name(countries.get(1));
                gameData.LoadData();
                gameData.createScreens();
                game.setScreen(gameData.getGameScreen());
            }
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        chooseBtn.setVisible(false);

        descLbl = new Label("", Styles.defaultLbl);
        descLbl.setSize(width/10, height/10);
        descLbl.setPosition(width/16, 1.3f*height/3);
        descLbl.setVisible(false);

        stage.addActor(chooseLbl);
        stage.addActor(oceaniaBtn);
        stage.addActor(eurasiaBtn);
        stage.addActor(eastasiaBtn);
        stage.addActor(descLbl);
        stage.addActor(chooseBtn);

        Gdx.input.setInputProcessor(stage);
    }

    private void showCountryInfo(String description, String imagePath) {
        descLbl.setText(" " + description);
        descLbl.setVisible(true);
        chooseBtn.setVisible(true);
        image = new Image(new Texture(imagePath));
        image.setSize(width/2, height/2);
        image.setPosition(width/2, height/7);
        stage.addActor(image);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
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
    }

    @Override
    public void dispose() {

    }
}
