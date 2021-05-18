package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


public class MainMenuScreen implements Screen {

    float height, width;
    Stage stage;
    Game game;

    public MainMenuScreen(Game aGame) {
        Sounds.MainTheme.play();
        Sounds.MainTheme.setVolume(0.3f);
        Sounds.MainTheme.setLooping(true);
        Styles.createStyles();
        game = aGame;

        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        I18NBundle ui_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/ui_strings"));

        TextButton newGameBtn = new TextButton(ui_bundle.get("newGame"), Styles.bigBtn);
        newGameBtn.setSize(width/4, height/4);
        newGameBtn.setPosition(width/8, 2*height/3);
        newGameBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                GameData gameData = new GameData(game);
                gameData.ClearData();
                game.setScreen(gameData.getChooseCountryScreen());
            }
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        TextButton continueBtn = new TextButton(ui_bundle.get("continue"), Styles.bigBtn);
        continueBtn.setSize(width/4, height/4);
        continueBtn.setPosition(width/8, 3*height/8);
        continueBtn.addListener(new InputListener() {
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                if (!Gdx.app.getPreferences("namesData").getString("playerName").equals("")) {
                    GameData gameData = new GameData(game);
                    gameData.LoadData();
                    gameData.createScreens();
                    game.setScreen(gameData.getGameScreen());
                }
            }
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        TextButton exitBtn = new TextButton(ui_bundle.get("exit"), Styles.bigBtn);
        exitBtn.setSize(width/4, height/4);
        exitBtn.setPosition(width/8, height/12);
        exitBtn.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                Gdx.app.exit();
            }
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        Image background = new Image(new Texture("textures/earth.png"));
        background.setSize(width/3f, 7*height/10);
        background.setPosition(width/2, 3*height/20);



        stage = new Stage(new ScreenViewport());
        stage.addActor(background);
        stage.addActor(continueBtn);
        stage.addActor(newGameBtn);
        stage.addActor(exitBtn);
    }


    @Override
    public void show() {
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
    }

    @Override
    public void dispose() {

    }
}
