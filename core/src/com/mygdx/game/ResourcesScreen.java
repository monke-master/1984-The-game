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
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.AbstractList;
import java.util.HashMap;

public class ResourcesScreen implements Screen {
    Game game;
    GameData gameData;
    Stage stage;
    I18NBundle ui_bundle, res_bundle;
    float height, width;
    int screen = 1;

    public ResourcesScreen(Game game1, GameData gameData1) {
        this.game = game1;
        this.gameData = gameData1;
        res_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/resources"));
        ui_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/ui_strings"));
        stage = new Stage(new ScreenViewport());
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Skin skin_close = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton closeBtn = new ImageButton(skin_close);
        closeBtn.setSize(width/10, height/10);
        closeBtn.setPosition(9*width/10, 9*height/10);
        closeBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/close.png")));
        closeBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                game.setScreen(gameData.getGameScreen());
                return true;
            }
        });

        Skin skin_next = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton nextBtn = new ImageButton(skin_next);
        nextBtn.setSize(width/10, height/10);
        nextBtn.setPosition(9*width/10, 4*height/10);
        nextBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/arrow.png")));
        nextBtn.addListener(new InputListener() {
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                if (screen == 3)
                    screen = 1;
                else
                    screen += 1;
                game.setScreen(gameData.getResourcesScreen());
                return true;
            }
        });

        Label ResourcesInfo = new Label(ui_bundle.get("resInfo"), Styles.defaultLbl);
        ResourcesInfo.setSize(width/5, height/10);
        ResourcesInfo.setPosition(2*width/5, 9*height/10);

        Label ResourceLbl = new Label(ui_bundle.get("resource"), Styles.defaultLbl);
        ResourceLbl.setSize(width/10, height/10);
        ResourceLbl.setPosition(width/20, 8*height/10);

        Label TotalLbl = new Label(ui_bundle.get("total"), Styles.defaultLbl);
        TotalLbl.setSize(width/10, height/10);
        TotalLbl.setPosition(11*width/50, 8*height/10);

        Label PerDayLbl = new Label(ui_bundle.get("perDay"), Styles.defaultLbl);
        PerDayLbl.setSize(width/10, height/10);
        PerDayLbl.setPosition(17*width/50, 8*height/10);

        Label ForProduction = new Label(ui_bundle.get("forProduction"), Styles.defaultLbl);
        ForProduction.setSize(width/10, height/10);
        ForProduction.setPosition(14*width/25, 8*height/10);

        Label ForPopulation = new Label(ui_bundle.get("forPopulation"), Styles.defaultLbl);
        ForPopulation.setSize(width/10, height/10);
        ForPopulation.setPosition(73*width/100, 8*height/10);


        Image resImage;
        Label resLbl, totalResLbl, perDayResLbl, forProdResLbl, forPopResLbl;
        String path = "textures/close.png";
        float y = 7f*height/10;
        HashMap<String, Integer> resources = gameData.getPlayer().getExtractingResources();
        switch (screen) {
            case 1:
                resources = gameData.getPlayer().getExtractingResources();
                break;
            case 2:
                resources = gameData.getPlayer().getProducingResources();
                break;
            case 3:
                resources = gameData.getPlayer().getMilitaryResources();

        }
        for (String key: resources.keySet()) {
            path = "textures/" + key + ".png";

            resImage = new Image(new Texture(Gdx.files.internal(path)));
            resImage.setSize(width/22, height/14);
            resImage.setPosition(0, y);

            resLbl = new Label(res_bundle.get(key), Styles.defaultLbl);
            resLbl.setSize(width/10, height/10);
            resLbl.setPosition(width/20, y - height/60);

            totalResLbl = new Label(String.valueOf(resources.get(key)),
                    Styles.defaultLbl);
            totalResLbl.setSize(width/10, height/10);
            totalResLbl.setPosition(11*width/50, y - height/60);

            perDayResLbl = new Label(String.valueOf(gameData.getPlayer().getResourcesExtEff().get(key)),
                    Styles.defaultLbl);
            perDayResLbl.setSize(width/10, height/10);
            perDayResLbl.setPosition(39*width/100, y - height/60);

            forProdResLbl = new Label(String.valueOf(gameData.getPlayer().getForProduction().get(key)),
                    Styles.defaultLbl);
            forProdResLbl.setSize(width/10, height/10);
            forProdResLbl.setPosition(14*width/25, y - height/60);

            forPopResLbl = new Label(String.valueOf(gameData.getPlayer().getForPopulation().get(key)),
                    Styles.defaultLbl);
            forPopResLbl.setSize(width/10, height/10);
            forPopResLbl.setPosition(73*width/100, y - height/60);

            y -= height/10;
            stage.addActor(resImage);
            stage.addActor(resLbl);
            stage.addActor(totalResLbl);
            stage.addActor(perDayResLbl);
            stage.addActor(forProdResLbl);
            stage.addActor(forPopResLbl);
        }

        stage.addActor(closeBtn);
        stage.addActor(ResourcesInfo);
        stage.addActor(ResourceLbl);
        stage.addActor(TotalLbl);
        stage.addActor(PerDayLbl);
        stage.addActor(nextBtn);
        stage.addActor(ForProduction);
        stage.addActor(ForPopulation);
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
