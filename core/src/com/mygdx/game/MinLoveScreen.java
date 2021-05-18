package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.I18NBundle;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.util.ArrayList;

public class MinLoveScreen implements Screen {

    GameData gameData;
    Game game;
    Stage stage;
    float height, width;
    ArrayList<Decree> decrees;
    ArrayList<Skin> skins;
    Decree selectedDecree;
    Label selectedDecreeLbl, selectedDecreeDescLbl, priceLbl, pointsLbl;
    Label.LabelStyle priceLblStyle;
    I18NBundle decrees_bundle, ui_bundle;
    Image priceImg;
    TextButton issueBtn;

    public MinLoveScreen(Game game1, GameData gameData1) {
        // Начальные значения
        game = game1;
        gameData = gameData1;

        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();

        decrees_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/decrees"));
        ui_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/ui_strings"));
        priceLblStyle = new Label.LabelStyle();
        priceLblStyle.font = Styles.defaultFont;

        skins = new ArrayList<>();
    }

    public void createDecrees() {
        decrees = new ArrayList<>();
        // Указы
        Decree BeatingUp = new Decree(decrees_bundle.get("beatingUp"),
                decrees_bundle.get("beatingUpDesc"), Values.BEATING_UP_PRICE, 3);
        Skin skin_bu = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton beatingUpBtn = new ImageButton(skin_bu);
        beatingUpBtn.setSize(width/10, height/10);
        beatingUpBtn.setPosition(0, 3*height/5);
        beatingUpBtn.getStyle().imageUp =
                new TextureRegionDrawable(new Texture(Gdx.files.internal("textures/beating_up.png")));
        BeatingUp.setButton(beatingUpBtn);
        decrees.add(BeatingUp);
        skins.add(skin_bu);

        Decree Starvation = new Decree(decrees_bundle.get("starvation"),
                decrees_bundle.get("starvationDesc"), Values.STARVATION_PRICE, 2);
        Skin skin_s = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton starvationBtn = new ImageButton(skin_s);
        starvationBtn.setSize(width/10, height/10);
        starvationBtn.setPosition(0, 2.45f*height/5);
        starvationBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/starvation.png")));
        Starvation.setButton(starvationBtn);
        decrees.add(Starvation);
        skins.add(skin_s);

        Decree Poisons = new Decree(decrees_bundle.get("poisons"), decrees_bundle.get("poisonsDesc"),
                Values.POISONS_PRICE, 10);
        Skin skin_p = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton poisonsBtn = new ImageButton(skin_p);
        poisonsBtn.setSize(width/10, height/10);
        poisonsBtn.setPosition(1.5f*width/10, 3*height/5);
        poisonsBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/poison.png")));
        Poisons.setButton(poisonsBtn);
        Poisons.addRequiredDecree(Starvation);
        Poisons.addRequiredDecree(BeatingUp);
        decrees.add(Poisons);
        skins.add(skin_p);

        Decree Rack = new Decree(decrees_bundle.get("rack"), decrees_bundle.get("rackDesc"),
                Values.RACK_PRICE, 10);
        Skin skin_r = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton rackBtn = new ImageButton(skin_r);
        rackBtn.setSize(width/10, height/10);
        rackBtn.setPosition(1.5f*width/10, 2.45f*height/5);
        rackBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/rack.png")));
        Rack.setButton(rackBtn);
        Rack.addRequiredDecree(Starvation);
        Rack.addRequiredDecree(BeatingUp);
        decrees.add(Rack);
        skins.add(skin_r);

        Decree IronMaiden = new Decree(decrees_bundle.get("ironMaiden"),
                decrees_bundle.get("ironMaidenDesc"), Values.IRON_MAIDEN_PRICE, 10);
        Skin skin_im = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton ironMaidenBtn = new ImageButton(skin_im);
        ironMaidenBtn.setSize(width/10, height/10);
        ironMaidenBtn.setPosition(1.5f*width/10, 1.9f*height/5);
        ironMaidenBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/iron_maiden.png")));
        IronMaiden.setButton(ironMaidenBtn);
        IronMaiden.addRequiredDecree(Starvation);
        IronMaiden.addRequiredDecree(BeatingUp);
        decrees.add(IronMaiden);
        skins.add(skin_im);

        Decree ElectricChair = new Decree(decrees_bundle.get("electricChair"),
                decrees_bundle.get("electricChairDesc"), Values.ELECTRIC_CHAIR_PRICE, 50);
        Skin skin_ec = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton electricChairBtn = new ImageButton(skin_ec);
        electricChairBtn.setSize(width/10, height/10);
        electricChairBtn.setPosition(3f*width/10, 2.45f*height/5);
        electricChairBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/electric_chair.png")));
        ElectricChair.setButton(electricChairBtn);
        ElectricChair.addRequiredDecree(IronMaiden);
        ElectricChair.addRequiredDecree(Rack);
        ElectricChair.addRequiredDecree(Poisons);
        decrees.add(ElectricChair);
        skins.add(skin_ec);

        Decree Room101 = new Decree(decrees_bundle.get("room101"), decrees_bundle.get("room101Desc"),
                Values.ROOM_101_PRICE, 500);
        Skin skin_101 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton room101Btn = new ImageButton(skin_101);
        room101Btn.setSize(width/10, height/10);
        room101Btn.setPosition(4.5f*width/10, 2.45f*height/5);
        room101Btn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/room101.png")));
        Room101.setButton(room101Btn);
        Room101.addRequiredDecree(ElectricChair);
        decrees.add(Room101);
        skins.add(skin_101);

        Decree PublicPunish1 = new Decree(decrees_bundle.get("publicPunishments1"),
                decrees_bundle.get("publicPunishments1Desc"), Values.PUBLIC_PUNISH_PRICE, 10);
        Skin skin_pp1 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton publicPunish1Btn = new ImageButton(skin_pp1);
        publicPunish1Btn.setSize(width/10, height/10);
        publicPunish1Btn.setPosition(0, 0.9f*height/5);
        publicPunish1Btn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/public_punishment.png")));
        PublicPunish1.setButton(publicPunish1Btn);
        PublicPunish1.addRequiredDecree(BeatingUp);
        PublicPunish1.addRequiredDecree(Starvation);
        decrees.add(PublicPunish1);
        skins.add(skin_pp1);

        Decree PublicPunish2 = new Decree(decrees_bundle.get("publicPunishments2"),
                decrees_bundle.get("publicPunishments2Desc"), Values.PUBLIC_PUNISH_PRICE*2, 100);
        Skin skin_pp2 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton publicPunish2Btn = new ImageButton(skin_pp2);
        publicPunish2Btn.setSize(width/10, height/10);
        publicPunish2Btn.setPosition(1.5f*width/10, 0.9f*height/5);
        publicPunish2Btn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/public_punishment.png")));
        PublicPunish2.setButton(publicPunish2Btn);
        PublicPunish2.addRequiredDecree(IronMaiden);
        PublicPunish2.addRequiredDecree(Poisons);
        PublicPunish2.addRequiredDecree(Rack);
        PublicPunish2.addRequiredDecree(PublicPunish1);
        decrees.add(PublicPunish2);
        skins.add(skin_pp2);

        Decree PublicExecution = new Decree(decrees_bundle.get("publicExecution"),
                decrees_bundle.get("publicExecutionDesc"), Values.PUBLIC_PUNISH_PRICE*5, 200);
        Skin skin_pe = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton publicExecutionBtn = new ImageButton(skin_pe);
        publicExecutionBtn.setSize(width/10, height/10);
        publicExecutionBtn.setPosition(3*width/10, 0.9f*height/5);
        publicExecutionBtn.getStyle().imageUp = new TextureRegionDrawable(
                new Texture(Gdx.files.internal("textures/public_punishment.png")));
        PublicExecution.setButton(publicExecutionBtn);
        PublicExecution.addRequiredDecree(PublicPunish2);
        decrees.add(PublicExecution);
        skins.add(skin_pe);

        for (final Decree decree: decrees) {
            if (gameData.getPlayer().getMinLove().getDecrees().contains(decree.getName())) {
                decree.setIssued(true);
                Skin skin1 = new Skin(Gdx.files.internal("skin/ui.json"));
                ImageButton imageButton = new ImageButton(skin1, "researched");
                imageButton.setSize(width / 10, height / 10);
                imageButton.setPosition(decree.getButton().getX(), decree.getButton().getY());
                imageButton.getStyle().imageUp = decree.getButton().getStyle().imageUp;
                decree.setButton(imageButton);
                decree.getButton().addListener(new InputListener() {
                    public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                        Sounds.Click.play();
                        setSelected(decree);
                        return true;
                    }
                });
                skins.add(skin1);
            }
        }

    }



    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        createDecrees();
        // Кнопка выхода
        Skin skin_close = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton closeBtn = new ImageButton(skin_close);
        closeBtn.setSize(width/10, height/10);
        closeBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/close.png")));
        closeBtn.setPosition(9*width/10, 9*height/10);
        skins.add(skin_close);

        closeBtn.addListener(new InputListener() {
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                game.setScreen(gameData.getMinistriesScreen());
            }
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });

        Image background = new Image(new Texture("textures/screen_back.png"));
        background.setSize(width, height);
        background.setPosition(0, 0);

        // Виджеты очков
        final Image pointsImg = new Image(new Texture(Gdx.files.internal("textures/briefcase.png")));
        pointsImg.setSize(width/20, height/11);
        pointsImg.setPosition(width/100, height/40);

        pointsLbl = new Label(ui_bundle.get("total") + ":" +
                gameData.getPlayer().getMinLove().getPoints() + ". " + ui_bundle.get("perDay") + ":" +
                gameData.getPlayer().getMinLove().getPointsPD(), Styles.defaultLbl);
        pointsLbl.setSize(width/3, height/10);
        pointsLbl.setPosition(width/15, height/40);

        priceLblStyle = new Label.LabelStyle();
        priceLblStyle.font = Styles.greenFont;

        priceLbl = new Label("", priceLblStyle);
        priceLbl.setSize(width/10, height/10);
        priceLbl.setPosition(3*width/4, 1.4f*height/5);

        priceImg = new Image(new Texture(Gdx.files.internal("textures/briefcase.png")));
        priceImg.setSize(width/19, height/14);
        priceImg.setPosition(2.8f*width/4, 1.5f*height/5);
        priceImg.setVisible(false);

        // Виджеты выбранного указа
        Image descBack = new Image(new Texture(Gdx.files.internal("textures/back.png")));
        descBack.setSize(1.1f*width/3, 9.5f*height/10);
        descBack.setPosition(1.95f*width/3, height/10);

        selectedDecreeLbl = new Label(ui_bundle.get("selectBuilding"), Styles.defaultLbl);
        selectedDecreeLbl.setSize(width/4, height/10);
        selectedDecreeLbl.setPosition(2.30f*width/3, 3.95f*height/5);
        selectedDecreeLbl.setAlignment(Align.left);

        selectedDecreeDescLbl = new Label("", Styles.defaultLbl);
        selectedDecreeDescLbl.setSize(width/4, 2*height/3);
        selectedDecreeDescLbl.setPosition(2.7f*width/4, 1.3f*height/5);
        selectedDecreeDescLbl.setAlignment(Align.left);

        issueBtn = new TextButton(ui_bundle.get("research"), Styles.defaultBtn);
        issueBtn.setSize(width/5, height/10);
        issueBtn.setPosition(3*width/4, height/15);

        // Нажатие на кнопки
        issueBtn.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                if (selectedDecree.isAvailable() && selectedDecree.getPrice() <=
                        gameData.getPlayer().getMinLove().getPoints() && !selectedDecree.isIssued()) {
                    gameData.getPlayer().getMinLove().setPoints(
                            gameData.getPlayer().getMinLove().getPoints() - selectedDecree.getPrice());
                    gameData.getPlayer().getMinLove().setEfficiency(gameData.getPlayer().getMinLove().getEfficiency() +
                            selectedDecree.getBonus()*Values.DECREE_EFF);
                    gameData.getPlayer().getMinLove().getDecrees().add(selectedDecree.getName());
                    selectedDecree.setIssued(true);
                    priceLblStyle.font = Styles.greenFont;
                    priceLbl.setStyle(priceLblStyle);
                    priceLbl.setText(ui_bundle.get("decreeIssued"));
                    Skin skin1 = new Skin(Gdx.files.internal("skin/ui.json"));
                    ImageButton b = new ImageButton(skin1, "researched");
                    b.setSize(width / 10, height / 10);
                    b.setPosition(selectedDecree.getButton().getX(), selectedDecree.getButton().getY());
                    b.getStyle().imageUp = selectedDecree.getButton().getStyle().imageUp;
                    final Decree temp_dec = selectedDecree;
                    temp_dec.setButton(b);
                    temp_dec.getButton().addListener(new InputListener() {
                        public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                            Sounds.Click.play();
                            setSelected(temp_dec);
                            return true;
                        }
                    });
                    skins.add(skin1);
                }
                return true;
            }

        });

        for (final Decree decree: decrees) {
            decree.getButton().addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    Sounds.Click.play();
                    setSelected(decree);
                    return true;
                }
            });
        }

        // Добавление виджетов на экран
        stage.addActor(background);
        stage.addActor(descBack);
        stage.addActor(selectedDecreeLbl);
        stage.addActor(selectedDecreeDescLbl);
        stage.addActor(pointsImg);
        stage.addActor(pointsLbl);
        stage.addActor(priceImg);
        stage.addActor(priceLbl);
        stage.addActor(issueBtn);
        stage.addActor(closeBtn);
        Gdx.input.setInputProcessor(stage);

    }

    public void setSelected(Decree decree) {
        selectedDecree = decree;
        selectedDecreeLbl.setText(decree.getName());
        selectedDecreeDescLbl.setText(" " + decree.getDescription());
        selectedDecreeLbl.setPosition(2.6f*width/3 - decree.getName().length()*15,
                3.95f*height/5);
        if (selectedDecree.isIssued()) {
            priceLblStyle.font = Styles.greenFont;
            priceLbl.setStyle(priceLblStyle);
            priceLbl.setText(ui_bundle.get("decreeIssued"));

        }
        else {
            if (selectedDecree.getPrice() > gameData.getPlayer().getMinLove().getPoints()) {
                priceLblStyle.font = Styles.defaultFont;
            }
            else
                priceLblStyle.font = Styles.greenFont;
            priceLbl.setStyle(priceLblStyle);
            priceLbl.setText(String.valueOf(selectedDecree.getPrice()));
        }
        priceImg.setVisible(true);
        priceLbl.setVisible(true);
        issueBtn.setVisible(true);
    }

    // Отрисовка
    @Override
    public void render(float delta) {
        // Добавление открытых указов на экран
        for (Decree decree: decrees) {
            if (decree.isOpened())
                stage.addActor(decree.getButton());
        }
        pointsLbl.setText(ui_bundle.get("total") + ":" +
                gameData.getPlayer().getMinLove().getPoints() + ". " + ui_bundle.get("perDay") + ":" +
                gameData.getPlayer().getMinLove().getPointsPD());
        Gdx.gl.glClearColor(Colors.BACKGROUND.r, Colors.BACKGROUND.g, Colors.BACKGROUND.b, Colors.BACKGROUND.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        stage.dispose();
        for (Skin skin: skins)
            skin.dispose();
        skins.clear();
        decrees.clear();
    }

    @Override
    public void dispose() {

    }


}
