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

public class RegionScreen implements Screen {

    Stage stage;
    float height, width;
    ArrayList<Skin> skins;
    Region region;
    Game game;
    GameData gameData;
    I18NBundle ui_bundle;


    // Экран выбранного региона
    public RegionScreen(final Game game, final GameData gameData) {
        this.game = game;
        this.gameData = gameData;
        // Начальные значения
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        skins = new ArrayList<>();
        ui_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/ui_strings"));
    }

    public void setRegion(Region region) {
        this.region = region;


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

        Label regionName = new Label(region.getName(), Styles.defaultLbl);
        regionName.setSize(width/3, height/10);
        regionName.setPosition(width/3, 9*height/10);
        regionName.setAlignment(Align.center);

        Label countryName = new Label(ui_bundle.get("regionControlledBy") + " " +
                region.getCountryName(), Styles.defaultLbl);
        countryName.setSize(width/3, height/10);
        countryName.setPosition(width/100, 8*height/10);
        countryName.setAlignment(Align.left);



        Label populationLbl = new Label(ui_bundle.get("population") + " " +
                String.valueOf(region.getPopulation()), Styles.defaultLbl);
        populationLbl.setSize(width/3, height/10);
        populationLbl.setPosition(width/100, 7*height/10);
        populationLbl.setAlignment(Align.left);

        Label waterAccessLbl = new Label("", Styles.defaultLbl);
        waterAccessLbl.setSize(width/10, height/10);
        waterAccessLbl.setPosition(width/100, 3*height/5);
        waterAccessLbl.setAlignment(Align.left);

        if (region.isWaterAccess())
            waterAccessLbl.setText(ui_bundle.get("waterAccess"));
        else
            waterAccessLbl.setText(ui_bundle.get("noWaterAccess"));

        Label landAccessLbl = new Label("", Styles.defaultLbl);
        landAccessLbl.setSize(width/10, height/10);
        landAccessLbl.setPosition(width/100, height/2);
        landAccessLbl.setAlignment(Align.left);

        if (region.isLandAccess())
            landAccessLbl.setText(ui_bundle.get("landAccess"));
        else
            landAccessLbl.setText(ui_bundle.get("noLandAccess"));


        Label resLbl = new Label(ui_bundle.get("resources"), Styles.defaultLbl);
        resLbl.setSize(width/10, height/10);
        resLbl.setPosition(8*width/10, 8*height/10);
        resLbl.setAlignment(Align.left);

        Image image;
        String path;
        Label label;
        float y = 7.5f*height/10;
        for (String key: region.getResources().keySet()) {
            path = "textures/" + key + ".png";

            image = new Image(new Texture(Gdx.files.internal(path)));
            image.setSize(width/22, height/14);
            image.setPosition(7.15f*width/10, y);

            label = new Label(String.valueOf(region.getResources().get(key)),
                    Styles.defaultLbl);
            label.setSize(width/10, height/10);
            label.setPosition(7.7f*width/10, y - height/100);

            stage.addActor(image);
            stage.addActor(label);

            y -= height/13;
        }

        if (region.getCountryName().equals(gameData.getPlayer().getName())) {
            TextButton buildingBtn = new TextButton(ui_bundle.get("building"), Styles.defaultBtn);
            buildingBtn.setSize(width/4, 1.5f*height/7);
            buildingBtn.setPosition(width/100, 1.5f*height/10);
            buildingBtn.addListener(new InputListener() {
                public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                    Sounds.Click.play();
                    gameData.getBuildScreen().setRegion(region);
                    game.setScreen(gameData.getBuildScreen());
                    return true;
                }
            });

            stage.addActor(buildingBtn);
        }

        stage.addActor(regionName);
        stage.addActor(countryName);
        stage.addActor(populationLbl);
        stage.addActor(closeBtn);
        stage.addActor(resLbl);
        stage.addActor(waterAccessLbl);
        stage.addActor(landAccessLbl);


        Gdx.input.setInputProcessor(stage);
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
        for (Skin skin : skins)
            skin.dispose();
        skins.clear();
    }


    @Override
    public void dispose() {

    }
}
