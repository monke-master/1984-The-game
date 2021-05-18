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

public class MinPlentyScreen implements Screen {

    GameData gameData;
    Game game;
    Stage stage;
    float height, width;
    ArrayList<ResourceDecree> decrees;
    ArrayList<Skin> skins;
    ResourceDecree selectedDecree;
    Label selectedDecreeLbl, selectedDecreeDescLbl, priceLbl, pointsLbl;
    Label.LabelStyle priceLblStyle;
    I18NBundle decrees_bundle, ui_bundle;
    Image priceImg;
    TextButton issueBtn;

    public MinPlentyScreen(Game game1, GameData gameData1) {
        // Начальные значения
        game = game1;
        gameData = gameData1;

        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();

        decrees_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/decrees"));
        ui_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/ui_strings"));
        priceLblStyle = new Label.LabelStyle();
        priceLblStyle.font = Styles.defaultFont;

        decrees = new ArrayList<>();
        skins = new ArrayList<>();
    }

    public void createDecrees() {
        // Указы
        ResourceDecree SavingGas1 = new ResourceDecree(decrees_bundle.get("gasSaving1"),
                decrees_bundle.get("gasSavingDesc"), Values.SAVING_DECREE_PRICE,
                "gas", 1);
        Skin skin_g1 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton savingGas1Btn = new ImageButton(skin_g1);
        savingGas1Btn.setSize(width/10, height/10);
        savingGas1Btn.setPosition(0, 1.90f*height/5);
        savingGas1Btn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/gas.png")));
        SavingGas1.setButton(savingGas1Btn);
        decrees.add(SavingGas1);
        skins.add(skin_g1);

        ResourceDecree SavingGas2 = new ResourceDecree(decrees_bundle.get("gasSaving2"),
                decrees_bundle.get("gasSavingDesc"), Values.SAVING_DECREE_PRICE*3,
                "gas", 5);
        Skin skin_g2= new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton savingGas2Btn = new ImageButton(skin_g2);
        savingGas2Btn.setSize(width/10, height/10);
        savingGas2Btn.setPosition(1.5f*width/10, 1.90f*height/5);
        savingGas2Btn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/gas.png")));
        SavingGas2.setButton(savingGas2Btn);
        SavingGas2.addRequiredDecree(SavingGas1);
        decrees.add(SavingGas2);
        skins.add(skin_g2);

        ResourceDecree SavingGas3 = new ResourceDecree(decrees_bundle.get("gasSaving3"),
                decrees_bundle.get("gasSavingDesc"), Values.SAVING_DECREE_PRICE*6,
                "gas", 10);
        Skin skin_g3= new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton savingGas3Btn = new ImageButton(skin_g3);
        savingGas3Btn.setSize(width/10, height/10);
        savingGas3Btn.setPosition(3*width/10, 1.90f*height/5);
        savingGas3Btn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/gas.png")));
        SavingGas3.setButton(savingGas3Btn);
        SavingGas3.addRequiredDecree(SavingGas2);
        decrees.add(SavingGas3);
        skins.add(skin_g3);

        ResourceDecree SavingGas4 = new ResourceDecree(decrees_bundle.get("gasSaving4"),
                decrees_bundle.get("gasSavingDesc"), Values.SAVING_DECREE_PRICE*10,
                "gas", 100);
        Skin skin_g4= new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton savingGas4Btn = new ImageButton(skin_g4);
        savingGas4Btn.setSize(width/10, height/10);
        savingGas4Btn.setPosition(4.5f*width/10, 1.90f*height/5);
        savingGas4Btn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/gas.png")));
        SavingGas4.setButton(savingGas4Btn);
        SavingGas4.addRequiredDecree(SavingGas3);
        decrees.add(SavingGas4);
        skins.add(skin_g4);

        ResourceDecree SavingGas5 = new ResourceDecree(decrees_bundle.get("gasSaving5"),
                decrees_bundle.get("gasSavingDesc"), Values.SAVING_DECREE_PRICE*20,
                "gas", 1000);
        Skin skin_g5= new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton savingGas5Btn = new ImageButton(skin_g5);
        savingGas5Btn.setSize(width/10, height/10);
        savingGas5Btn.setPosition(6*width/10, 1.90f*height/5);
        savingGas5Btn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/gas.png")));
        SavingGas5.setButton(savingGas5Btn);
        SavingGas5.addRequiredDecree(SavingGas4);
        decrees.add(SavingGas5);
        skins.add(skin_g5);


        ResourceDecree SavingFood1 = new ResourceDecree(decrees_bundle.get("foodSaving1"),
                decrees_bundle.get("foodSavingDesc"), Values.SAVING_DECREE_PRICE,
                "canned_food", 1);
        Skin skin_f1 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton savingFood1Btn = new ImageButton(skin_f1);
        savingFood1Btn.setSize(width/10, height/10);
        savingFood1Btn.setPosition(0, 1.35f*height/5);
        savingFood1Btn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/canned_food.png")));
        SavingFood1.setButton(savingFood1Btn);
        decrees.add(SavingFood1);
        skins.add(skin_f1);

        ResourceDecree SavingFood2 = new ResourceDecree(decrees_bundle.get("foodSaving2"),
                decrees_bundle.get("foodSavingDesc"), Values.SAVING_DECREE_PRICE*3,
                "canned_food", 5);
        Skin skin_f2 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton savingFood2Btn = new ImageButton(skin_f2);
        savingFood2Btn.setSize(width/10, height/10);
        savingFood2Btn.setPosition(1.5f*width/10, 1.35f*height/5);
        savingFood2Btn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/canned_food.png")));
        SavingFood2.setButton(savingFood2Btn);
        SavingFood2.addRequiredDecree(SavingFood1);
        decrees.add(SavingFood2);
        skins.add(skin_f2);

        ResourceDecree SavingFood3 = new ResourceDecree(decrees_bundle.get("foodSaving3"),
                decrees_bundle.get("foodSavingDesc"), Values.SAVING_DECREE_PRICE*6,
                "canned_food", 10);
        Skin skin_f3 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton savingFood3Btn = new ImageButton(skin_f3);
        savingFood3Btn.setSize(width/10, height/10);
        savingFood3Btn.setPosition(3*width/10, 1.35f*height/5);
        savingFood3Btn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/canned_food.png")));
        SavingFood3.setButton(savingFood3Btn);
        SavingFood3.addRequiredDecree(SavingFood2);
        decrees.add(SavingFood3);
        skins.add(skin_f3);

        ResourceDecree SavingFood4 = new ResourceDecree(decrees_bundle.get("foodSaving4"),
                decrees_bundle.get("foodSavingDesc"), Values.SAVING_DECREE_PRICE*10,
                "canned_food", 100);
        Skin skin_f4 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton savingFood4Btn = new ImageButton(skin_f4);
        savingFood4Btn.setSize(width/10, height/10);
        savingFood4Btn.setPosition(4.5f*width/10, 1.35f*height/5);
        savingFood4Btn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/canned_food.png")));
        SavingFood4.setButton(savingFood4Btn);
        SavingFood4.addRequiredDecree(SavingFood3);
        decrees.add(SavingFood4);
        skins.add(skin_f4);

        ResourceDecree SavingFood5 = new ResourceDecree(decrees_bundle.get("foodSaving5"),
                decrees_bundle.get("foodSavingDesc"), Values.SAVING_DECREE_PRICE*20,
                "canned_food", 1000);
        Skin skin_f5 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton savingFood5Btn = new ImageButton(skin_f4);
        savingFood5Btn.setSize(width/10, height/10);
        savingFood5Btn.setPosition(6*width/10, 1.35f*height/5);
        savingFood5Btn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/canned_food.png")));
        SavingFood5.setButton(savingFood5Btn);
        SavingFood5.addRequiredDecree(SavingFood4);
        decrees.add(SavingFood5);
        skins.add(skin_f5);

        ResourceDecree SavingHHG1 = new ResourceDecree(decrees_bundle.get("hhgSaving1"),
                decrees_bundle.get("hhgSavingDesc"), Values.SAVING_DECREE_PRICE,
                "household_goods", 1);
        Skin skin_h1 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton savingHHG1Btn = new ImageButton(skin_h1);
        savingHHG1Btn.setSize(width/10, height/10);
        savingHHG1Btn.setPosition(0, 2.45f*height/5);
        savingHHG1Btn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/household_goods.png")));
        SavingHHG1.setButton(savingHHG1Btn);
        decrees.add(SavingHHG1);
        skins.add(skin_h1);

        ResourceDecree SavingHHG2 = new ResourceDecree(decrees_bundle.get("hhgSaving2"),
                decrees_bundle.get("hhgSavingDesc"), Values.SAVING_DECREE_PRICE*3,
                "household_goods", 5);
        Skin skin_h2 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton savingHHG2Btn = new ImageButton(skin_h2);
        savingHHG2Btn.setSize(width/10, height/10);
        savingHHG2Btn.setPosition(1.5f*width/10, 2.45f*height/5);
        savingHHG2Btn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/household_goods.png")));
        SavingHHG2.setButton(savingHHG2Btn);
        SavingHHG2.addRequiredDecree(SavingHHG1);
        decrees.add(SavingHHG2);
        skins.add(skin_h2);

        ResourceDecree SavingHHG3 = new ResourceDecree(decrees_bundle.get("hhgSaving3"),
                decrees_bundle.get("hhgSavingDesc"), Values.SAVING_DECREE_PRICE*6,
                "household_goods", 10);
        Skin skin_h3 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton savingHHG3Btn = new ImageButton(skin_h3);
        savingHHG3Btn.setSize(width/10, height/10);
        savingHHG3Btn.setPosition(3*width/10, 2.45f*height/5);
        savingHHG3Btn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/household_goods.png")));
        SavingHHG3.setButton(savingHHG3Btn);
        SavingHHG3.addRequiredDecree(SavingHHG2);
        decrees.add(SavingHHG3);
        skins.add(skin_h3);

        ResourceDecree SavingHHG4 = new ResourceDecree(decrees_bundle.get("hhgSaving4"),
                decrees_bundle.get("hhgSavingDesc"), Values.SAVING_DECREE_PRICE*10,
                "household_goods", 100);
        Skin skin_h4 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton savingHHG4Btn = new ImageButton(skin_h4);
        savingHHG4Btn.setSize(width/10, height/10);
        savingHHG4Btn.setPosition(4.5f*width/10, 2.45f*height/5);
        savingHHG4Btn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/household_goods.png")));
        SavingHHG4.setButton(savingHHG4Btn);
        SavingHHG4.addRequiredDecree(SavingHHG3);
        decrees.add(SavingHHG4);
        skins.add(skin_h4);

        ResourceDecree SavingHHG5 = new ResourceDecree(decrees_bundle.get("hhgSaving5"),
                decrees_bundle.get("hhgSavingDesc"), Values.SAVING_DECREE_PRICE*20,
                "household_goods", 1000);
        Skin skin_h5 = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton savingHHG5Btn = new ImageButton(skin_h5);
        savingHHG5Btn.setSize(width/10, height/10);
        savingHHG5Btn.setPosition(6*width/10, 2.45f*height/5);
        savingHHG5Btn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/household_goods.png")));
        SavingHHG5.setButton(savingHHG5Btn);
        SavingHHG5.addRequiredDecree(SavingHHG4);
        decrees.add(SavingHHG5);
        skins.add(skin_h5);

        for (final ResourceDecree decree: decrees) {
            if (gameData.getPlayer().getMinPlenty().getDecrees().contains(decree.getName())) {
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

    public void setSelected(ResourceDecree decree) {
        selectedDecree = decree;
        selectedDecreeLbl.setText(" " + decree.getName());
        selectedDecreeDescLbl.setText(decree.getDescription());
        selectedDecreeLbl.setPosition(2.6f*width/3 - decree.getName().length()*15,
                3.95f*height/5);
        if (selectedDecree.isIssued()) {
            priceLblStyle.font = Styles.greenFont;
            priceLbl.setStyle(priceLblStyle);
            priceLbl.setText(ui_bundle.get("decreeIssued"));

        }
        else {
            if (selectedDecree.getPrice() > gameData.getPlayer().getMinPlenty().getPoints()) {
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

    @Override
    public void show() {
        createDecrees();
        stage = new Stage(new ScreenViewport());
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
                gameData.getPlayer().getMinPlenty().getPoints() + ". " + ui_bundle.get("perDay") + ":" +
                gameData.getPlayer().getMinPlenty().getPointsPD(), Styles.defaultLbl);
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
                        gameData.getPlayer().getMinPlenty().getPoints() && !selectedDecree.isIssued()) {
                    gameData.getPlayer().getMinPlenty().setPoints(
                            gameData.getPlayer().getMinPlenty().getPoints() - selectedDecree.getPrice());
                    float bonus = selectedDecree.getBonus()*Values.SAVING_DECREE_EFF;
                    switch (selectedDecree.getResource()) {
                        case "gas":
                            float temp = gameData.getPlayer().getMinPlenty().getGasSaving();
                            gameData.getPlayer().getMinPlenty().setGasSaving(temp + bonus);
                            break;
                        case "canned_food":
                            temp = gameData.getPlayer().getMinPlenty().getFoodSaving();
                            gameData.getPlayer().getMinPlenty().setFoodSaving(temp + bonus);
                            break;
                        case "household_goods":
                            temp = gameData.getPlayer().getMinPlenty().getHhgSaving();
                            gameData.getPlayer().getMinPlenty().setHhgSaving(temp + bonus);
                            break;
                    }
                    gameData.getPlayer().getMinPlenty().getDecrees().add(selectedDecree.getName());
                    selectedDecree.setIssued(true);
                    priceLblStyle.font = Styles.greenFont;
                    priceLbl.setStyle(priceLblStyle);
                    priceLbl.setText(ui_bundle.get("decreeIssued"));
                    Skin skin1 = new Skin(Gdx.files.internal("skin/ui.json"));
                    ImageButton b = new ImageButton(skin1, "researched");
                    b.setSize(width / 10, height / 10);
                    b.setPosition(selectedDecree.getButton().getX(), selectedDecree.getButton().getY());
                    b.getStyle().imageUp = selectedDecree.getButton().getStyle().imageUp;
                    final ResourceDecree temp_dec = selectedDecree;
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

        for (final ResourceDecree decree: decrees) {
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

    // Отрисовка
    @Override
    public void render(float delta) {
        // Добавление открытых указов на экран
        for (Decree decree: decrees) {
            if (decree.isOpened())
                stage.addActor(decree.getButton());
        }
        pointsLbl.setText(ui_bundle.get("total") + ":" +
                gameData.getPlayer().getMinPlenty().getPoints() + ". " + ui_bundle.get("perDay") + ":" +
                gameData.getPlayer().getMinPlenty().getPointsPD());
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
        decrees.clear();
        skins.clear();
    }

    @Override
    public void dispose() {

    }
}
