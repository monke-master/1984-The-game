package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

public class MinistryBuilding extends Building {
    private int price;
    private int level;
    private String name;
    private String description;
    private String ministry;
    private boolean built;
    private ImageButton button;
    private int efficiency;
    private String imagePath;

    public MinistryBuilding(String name, String description, String ministry, int price) {
        this.name = name;
        this.description = description;
        this.ministry = ministry;
        this.price = price;
        efficiency = Values.MINISTRY_EFF;

    }
    public MinistryBuilding(String name, int level, int price, int efficiency) {
        this.name = name;
        this.level = level;
        this.price = price;
        this.efficiency = efficiency;
    }

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

    @Override
    public void upgrade() {
        level += 1;
        price *= Values.LEVEL_INCREASE;
        efficiency *= Values.LEVEL_INCREASE;
    }

    public String toString() {
        return level + " " + price + " " + efficiency + " " + imagePath + " ministry " +
                ministry;
    }

    public String getMinistry() {
        return ministry;
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

    public void setMinistry(String ministry) {
        this.ministry = ministry;
    }
}
