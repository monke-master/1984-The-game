package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

public class Sounds {
    static Sound Ship;
    static Sound Plane;
    static Music War;
    static Sound Army;
    static Sound Click;
    static Music MainTheme;

    static void LoadSounds() {
        Ship = Gdx.audio.newSound(Gdx.files.internal("sounds/ship.mp3"));
        Plane = Gdx.audio.newSound(Gdx.files.internal("sounds/plane.mp3"));
        War = Gdx.audio.newMusic(Gdx.files.internal("sounds/war.mp3"));
        Army = Gdx.audio.newSound(Gdx.files.internal("sounds/army.mp3"));
        Click = Gdx.audio.newSound(Gdx.files.internal("sounds/click.mp3"));
        MainTheme = Gdx.audio.newMusic(Gdx.files.internal("sounds/main_menu_theme.mp3"));
    }
}
