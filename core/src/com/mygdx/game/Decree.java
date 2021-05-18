package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import java.util.ArrayList;

public class Decree extends Technology {
    private int price;
    private String name, description;
    private ArrayList<Decree> requiredDecrees;
    private ArrayList<Technology> requiredTech;
    private boolean opened, issued, available;
    private ImageButton button;
    private int bonus;


    public Decree(String name, String description, int price, int bonus) {
        super(name, description, price);
        opened = true;
        available = true;
        issued = false;
        this.name = name;
        this.description = description;
        this.price = price;
        this.bonus = bonus;
        requiredDecrees = new ArrayList<>();
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

    public void addRequiredTech(Technology technology) {
        available = false;
        requiredTech.add(technology);
    }

    public void addRequiredDecree(Decree decree) {
        opened = false;
        requiredDecrees.add(decree);
    }

    public void setIssued(boolean issued) {
        this.issued = issued;
    }

    public boolean isIssued() {
        return issued;
    }

    public boolean isAvailable() {
        for (Technology technology: requiredTech) {
            if (technology.isResearched()) {
                requiredTech.remove(technology);
                break;
            }
        }
        available = requiredTech.size() == 0;
        return available;
    }

    public boolean isOpened() {
        for (Decree decree: requiredDecrees) {
            if (decree.issued) {
                requiredDecrees.remove(decree);
                break;
            }
        }
        opened = requiredDecrees.size() == 0;
        return opened;
    }

    public int getPrice() {
        return price;
    }

    public String getRequiredTech() {
        String s = "";
        for (Technology technology: requiredTech) {
            s += technology.getName() + "\n";
        }
        return s;
    }

    public int getBonus() {
        return this.bonus;
    }

}
