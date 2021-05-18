package com.mygdx.game;

import com.badlogic.gdx.Game;

public class GameStart extends Game {

    // Запуск главного меню
    @Override
    public void create() {
        Sounds.LoadSounds();
        this.setScreen(new MainMenuScreen(this));
    }
}
