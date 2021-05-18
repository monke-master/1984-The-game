package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class Building {
    private int price;
    private int level;
    private String name;
    private String description;
    private String imagePath;
    private boolean built;
    private ImageButton button;
    private int efficiency;


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getLevel() {
        return level;
    }

    public int getPrice() {
        return price;
    }

    public boolean isBuilt() {
        return built;
    }

    public void setBuilt(boolean built) {
        this.built = built;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void upgrade() {
        level += 1;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public String toString() {
        return level + " " + price + " " + efficiency + " " + imagePath;
    }

    public void setButton(ImageButton button) {
        this.button = button;
    }

    public ImageButton getButton() {
        return button;
    }
}
