package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class Styles {
    public static TextButton.TextButtonStyle defaultBtn, bigBtn;
    public static Label.LabelStyle defaultLbl, bigLbl;
    public static BitmapFont defaultFont, bigFont;
    public static BitmapFont greenFont;

    public static void createStyles() {
        int height = Gdx.graphics.getHeight();
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Marmelad-Regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.characters = Strings.CHARACTERS;
        parameter.size = height/23;
        parameter.color = Colors.DEFAULT_RED;
        defaultFont = generator.generateFont(parameter);

        parameter.size = height/17;
        bigFont = generator.generateFont(parameter);

        defaultBtn = new TextButton.TextButtonStyle();
        defaultBtn.up = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/button.png")));
        defaultBtn.down = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/button_down.png")));
        defaultBtn.font = defaultFont;

        defaultLbl = new Label.LabelStyle();
        defaultLbl.font = defaultFont;

        bigBtn = new TextButton.TextButtonStyle();
        bigBtn.up = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/button.png")));
        bigBtn.down = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/button_down.png")));
        bigBtn.font = bigFont;

        bigLbl = new Label.LabelStyle();
        bigLbl.font = bigFont;

        parameter.color = Colors.GREEN;
        parameter.size = height/23;
        greenFont = generator.generateFont(parameter);


        generator.dispose();
    }
}
