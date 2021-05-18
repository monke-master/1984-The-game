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

import java.util.ArrayList;

public class MinistriesScreen implements Screen {
    private Game game;
    private GameData gameData;
    float height, width;
    Stage stage;
    I18NBundle ui_bundle;
    ArrayList<Skin> skins;

    // Экран выбора министерства
    public MinistriesScreen(Game game1, GameData gameData1) {
        // Начальные значения
        this.game = game1;
        this.gameData = gameData1;
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        ui_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/ui_strings"));
        skins = new ArrayList<>();
    }


    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        // Кнопка выхода
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
        skins.add(skin_close);

        // Министерство правды
        Skin skin1 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton minTruthBtn = new ImageButton(skin1);
        minTruthBtn.setSize(width/6, height/2);
        minTruthBtn.setPosition(width/36, height/4);
        minTruthBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/two_min_of_hate.png")));

        Label minTruthLbl = new Label(ui_bundle.get("minTruth"), Styles.defaultLbl);
        minTruthLbl.setSize(width/6, height/10);
        minTruthLbl.setPosition(width/36, height/3);
        minTruthLbl.setAlignment(Align.center);
        minTruthBtn.addListener(new InputListener(){
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                game.setScreen(gameData.getMinTruthScreen());
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        skins.add(skin1);

        // Министерство любви
        Skin skin2 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton minLoveBtn = new ImageButton(skin2);
        minLoveBtn.setSize(width/6, height/2);
        minLoveBtn.setPosition(2*width/9, height/4);
        minLoveBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/beating_up.png")));

        Label minLoveLbl = new Label(ui_bundle.get("minLove"), Styles.defaultLbl);
        minLoveLbl.setSize(width/6, height/10);
        minLoveLbl.setPosition(2*width/9, height/3);
        minLoveLbl.setAlignment(Align.center);
        minLoveBtn.addListener(new InputListener(){
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                game.setScreen(gameData.getMinLoveScreen());
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        skins.add(skin2);

        Skin skin3 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton minPlentyBtn = new ImageButton(skin3);
        minPlentyBtn.setSize(width/6, height/2);
        minPlentyBtn.setPosition(15*width/36, height/4);
        minPlentyBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/gas.png")));

        Label minPlentyLbl = new Label(ui_bundle.get("minPlenty"), Styles.defaultLbl);
        minPlentyLbl.setSize(width/6, height/10);
        minPlentyLbl.setPosition(15*width/36, height/3);
        minPlentyLbl.setAlignment(Align.center);
        minPlentyBtn.addListener(new InputListener(){
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                game.setScreen(gameData.getMinPlentyScreen());
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        skins.add(skin3);

        // Министерство мира
        Skin skin4 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton minPeaceBtn = new ImageButton(skin4);
        minPeaceBtn.setSize(width/6, height/2);
        minPeaceBtn.setPosition(11*width/18, height/4);
        minPeaceBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/tank.png")));

        Label minPeaceLbl = new Label(ui_bundle.get("minPeace"), Styles.defaultLbl);
        minPeaceLbl.setSize(width/6, height/10);
        minPeaceLbl.setPosition(11*width/18, height/3);
        minPeaceLbl.setAlignment(Align.center);
        minPeaceBtn.addListener(new InputListener(){
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                game.setScreen(gameData.getMinPeaceScreen());
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        skins.add(skin4);

        // Полиция мыслей
        Skin skin5 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton thoughtPoliceBtn = new ImageButton(skin5);
        thoughtPoliceBtn.setSize(width/6, height/2);
        thoughtPoliceBtn.setPosition(29*width/36, height/4);
        thoughtPoliceBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/thought_police.png")));

        Label thoughtPoliceLbl = new Label(ui_bundle.get("thoughtPolice"), Styles.defaultLbl);
        thoughtPoliceLbl.setSize(width/6, height/10);
        thoughtPoliceLbl.setPosition(29*width/36, height/3);
        thoughtPoliceLbl.setAlignment(Align.center);
        thoughtPoliceBtn.addListener(new InputListener(){
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                game.setScreen(gameData.getThoughtPoliceScreen());
            }
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        skins.add(skin5);


        // Добавление виджетов на экран
        stage.addActor(minTruthBtn);
        stage.addActor(minTruthLbl);
        stage.addActor(closeBtn);
        stage.addActor(minLoveBtn);
        stage.addActor(minLoveLbl);
        stage.addActor(minPlentyBtn);
        stage.addActor(minPlentyLbl);
        stage.addActor(minPeaceBtn);
        stage.addActor(minPeaceLbl);
        stage.addActor(thoughtPoliceBtn);
        stage.addActor(thoughtPoliceLbl);
        Gdx.input.setInputProcessor(stage);

    }

    // Отрисовка
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
