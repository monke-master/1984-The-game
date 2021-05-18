package com.mygdx.game;

public class ResourceDecree extends Decree {
    private String resource;

    public ResourceDecree(String name, String description, int price, String resource, int bonus) {
        super(name, description, price, bonus);
        this.resource = resource;
    }


    public String getResource() {
        return this.resource;
    }
}
