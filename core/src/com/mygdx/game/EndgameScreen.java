package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class EndgameScreen implements Screen {

    Stage stage;
    float height, width;
    ArrayList<Skin> skins;
    Game game;
    GameData gameData;
    I18NBundle ui_bundle;
    String heading, description;

    public EndgameScreen(Game game, GameData gameData) {
        this.game = game;
        this.gameData = gameData;
        // Начальные значения
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();
        skins = new ArrayList<>();
        ui_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/ui_strings"));
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setHeading(String heading) {
        this.heading = heading;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());

        Label defeatLbl = new Label(heading, Styles.bigLbl);
        defeatLbl.setSize(width/10, height/10);
        defeatLbl.setPosition(8.5f*width/20, 8*height/10);

        Label causeLbl = new Label(" " + description, Styles.defaultLbl);
        causeLbl.setSize(width, height/5);
        causeLbl.setPosition(0, 6*height/10);

        TextButton menuBtn = new TextButton(ui_bundle.get("exit"), Styles.bigBtn);
        menuBtn.setSize(width/5, height/5);
        menuBtn.setPosition(2*width/5, height/10);
        menuBtn.addListener(new InputListener(){
            @Override
            public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                Gdx.app.exit();
            }

            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        stage.addActor(defeatLbl);
        stage.addActor(causeLbl);
        stage.addActor(menuBtn);

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
