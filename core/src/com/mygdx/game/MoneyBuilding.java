package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;


public class MoneyBuilding extends Building {
    private int price;
    private int level;
    private int efficiency;
    private String name;
    private String description;
    private boolean built;
    private String imagePath;
    private ImageButton button;

    public MoneyBuilding(String name, String description, int price, int efficiency) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.efficiency = efficiency;
        level = 0;
    }

    public MoneyBuilding(String name, int level, int price, int efficiency) {
        this.name = name;
        this.level = level;
        this.price = price;
        this.efficiency = efficiency;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void upgrade() {
        level += 1;
        efficiency *= Values.LEVEL_INCREASE;
        price *= Values.LEVEL_INCREASE;
    }

    @Override
    public int getEfficiency() {
        return this.efficiency;
    }

    @Override
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    @Override
    public String toString() {
        return level + " " + price + " " +
                efficiency + " " + imagePath + " money";
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public int getLevel() {
        return level;
    }

    @Override
    public int getPrice() {
        return price;
    }


    @Override
    public boolean isBuilt() {
        return built;
    }


    @Override
    public void setBuilt(boolean built) {
        this.built = built;
    }


    @Override
    public void setButton(ImageButton button) {
        this.button = button;
    }

    @Override
    public ImageButton getButton() {
        return button;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }


}
