package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import java.util.ArrayList;
import java.util.Objects;

public class Technology{

    private int price;
    private String name, description;
    private ArrayList<Technology> requiredTech;
    private boolean opened, researched;
    private ImageButton button;


    public Technology(String name, String description, int price) {
        opened = true;
        researched = false;
        this.name = name;
        this.description = description;
        this.price = price;
        requiredTech = new ArrayList<>();
    }

    public void setButton(ImageButton button) {
        this.button = button;
    }

    public ImageButton getButton() {
        return this.button;
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public void addRequiredTech(Technology t) {
        opened = false;
        requiredTech.add(t);
    }

    public void setResearched(boolean researched) {
        this.researched = researched;
    }

    public boolean isOpened() {
        for (Technology t: requiredTech) {
            if (t.researched) {
                requiredTech.remove(t);
                break;
            }
        }
        opened = requiredTech.size() == 0;
        return opened;
    }

    public boolean isResearched() {
        return researched;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Technology that = (Technology) o;
        return name.equals(that.getName());
    }

}
