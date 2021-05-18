package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import java.util.ArrayList;

public class ResourceBuilding extends Building {

    private int price, level;
    private String name;
    private String description;
    private String[] requiredRes;
    private String receivedRes;
    private int efficiency;
    private String imagePath;
    private ImageButton button;
    private boolean available, built;

    public ResourceBuilding(String name, String description, String receivedRes, int price) {
        this.price = price;
        this.name = name;
        this.receivedRes = receivedRes;
        this.description = description;
        this.level = 0;
        this.efficiency = Values.MINE_EFF;
        this.available = true;
    }

    public ResourceBuilding(String name, int level, String receivedRes, int price) {
        this.name = name;
        this.level = level;
        this.receivedRes = receivedRes;
        this.price = price;
    }


    public String[] getRequiredRes() {
        return requiredRes;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public int getEfficiency() {
        return efficiency;
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
        String info = level + " " + price + " " +
                efficiency + " " + imagePath + " " + receivedRes + " ";
        for (String res: requiredRes)
                info += res + " ";
        return info;
    }

    public void upgrade() {
        level += 1;
        efficiency = efficiency*Values.LEVEL_INCREASE;
        price *= Values.LEVEL_INCREASE;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setBuilt(boolean built) {
        this.built = built;
    }

    public boolean isBuilt() {
        return built;
    }

    public int getLevel() {
        return level;
    }

    public String getReceivedRes() {
        return receivedRes;
    }

    public void setRequiredRes(String[] requiredRes) {
        this.requiredRes = requiredRes;
    }

    public void setRequiredRes(ArrayList<String> requiredRes) {
        this.requiredRes = requiredRes.toArray(new String[0]);
    }

    @Override
    public ImageButton getButton() {
        return button;
    }

    @Override
    public void setButton(ImageButton button) {
        this.button = button;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }
}
