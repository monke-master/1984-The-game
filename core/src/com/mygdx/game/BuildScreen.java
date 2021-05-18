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

public class BuildScreen implements Screen {
    private  Game game;
    private GameData gameData;
    private Stage stage;
    private  I18NBundle ui_bundle;
    private  float height, width;
    private  Label selectedBuildingLbl, selectedBuildingDescLbl, priceLbl, moneyLbl;
    private  Building selectedBuilding;
    private ArrayList<Skin> skins;
    private Label.LabelStyle priceLblStyle;
    private TextButton upgradeBtn;
    private Region region;
    private  Image priceImg;
    private  float x, y;

    public BuildScreen(Game game, GameData gameData) {
        this.game = game;
        this.gameData = gameData;
        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();

        ui_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/ui_strings"));

        priceLblStyle = new Label.LabelStyle();
        priceLblStyle.font = Styles.defaultFont;
    }

    public void setRegion(Region region) {
        this.region = region;
    }


    @Override
    public void show() {
        x = 0;
        y = 5 * height / 6;
        stage = new Stage(new ScreenViewport());

        skins = new ArrayList<>();
        Skin skin_close = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton closeBtn = new ImageButton(skin_close);
        closeBtn.setSize(width / 10, height / 10);
        closeBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/close.png")));
        closeBtn.setPosition(9 * width / 10, 9 * height / 10);
        closeBtn.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameData.getRegionScreen().setRegion(region);
                game.setScreen(gameData.getRegionScreen());
                return true;
            }
        });
        skins.add(skin_close);

        // Описание выбранной постройки
        Image descBack = new Image(new Texture(Gdx.files.internal("textures/back.png")));
        descBack.setSize(1.1f*width/3, 9.5f*height/10);
        descBack.setPosition(1.95f*width/3, height/10);

        selectedBuildingLbl = new Label(ui_bundle.get("selectBuilding"), Styles.defaultLbl);
        selectedBuildingLbl.setSize(width/4, height/10);
        selectedBuildingLbl.setPosition(2.30f*width/3, 3.95f*height/5);
        selectedBuildingLbl.setAlignment(Align.left);

        selectedBuildingDescLbl = new Label("", Styles.defaultLbl);
        selectedBuildingDescLbl.setSize(width/4, 2*height/3);
        selectedBuildingDescLbl.setPosition(2.7f*width/4, 1.3f*height/5);
        selectedBuildingDescLbl.setAlignment(Align.left);

        // Виджеты цены
        priceLbl = new Label("", priceLblStyle);
        priceLbl.setSize(width/10, height/10);
        priceLbl.setPosition(3.05f*width/4, 1.5f*height/5);

        priceImg = new Image(new Texture(Gdx.files.internal("textures/money.png")));
        priceImg.setSize(width/20, height/11);
        priceImg.setPosition(2.83f*width/4, 1.5f*height/5);
        priceImg.setVisible(false);

        // Виджеты денег
        final Image moneyImg = new Image(new Texture(Gdx.files.internal("textures/money.png")));
        moneyImg.setSize(width/20, height/11);
        moneyImg.setPosition(width/100, height/40);

        moneyLbl = new Label(ui_bundle.get("total") + ":" +
                gameData.getPlayer().getMoney() + ". " + ui_bundle.get("perDay") + ":" +
                gameData.getPlayer().getMoneyPd(), Styles.defaultLbl);
        moneyLbl.setSize(width/3, height/10);
        moneyLbl.setPosition(width/15, height/40);

        // Кнопка улучшения
        upgradeBtn = new TextButton(ui_bundle.get("upgrade"), Styles.defaultBtn);
        upgradeBtn.setSize(width/5, height/10);
        upgradeBtn.setPosition(3*width/4, height/15);
        upgradeBtn.setVisible(false);

        upgradeBtn.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                if (selectedBuilding.getLevel() < 5 && gameData.getPlayer().getMoney()
                        >= selectedBuilding.getPrice()) {
                    gameData.getPlayer().setMoney(gameData.getPlayer().getMoney() -
                            selectedBuilding.getPrice());
                    region.getBuildings().remove(selectedBuilding.getName());
                    selectedBuilding.upgrade();
                    region.getBuildings().put(selectedBuilding.getName(), selectedBuilding);
                    setSelected(selectedBuilding);
                }
                return true;
            }
        });

        // Вывод построенных построек
        for (final Building building : region.getBuildings().values()) {
            Skin skin = new Skin(Gdx.files.internal("skin/ui.json"));
            skins.add(skin);
            ImageButton button = new ImageButton(skin);
            button.setSize(width / 10, height / 10);
            button.setPosition(x, y);
            button.getStyle().imageUp = new TextureRegionDrawable(
                    new Texture(building.getImagePath()));
            button.addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    setSelected(building);
                    return true;
                }
            });
            building.setButton(button);

            if (y > height / 6)
                y -= height / 6;
            else {
                y = 5 * height / 6;
                x += width / 10;
            }
            stage.addActor(building.getButton());
        }

        Skin skin_add = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton addBtn = new ImageButton(skin_add);
        addBtn.setSize(width / 10, height / 10);
        addBtn.setPosition(x, y);
        addBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/plus.png")));
        addBtn.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                gameData.getAvailableBuildingScreen().setRegion(region);
                game.setScreen(gameData.getAvailableBuildingScreen());
                return true;
            }
        });
        skins.add(skin_add);


        stage.addActor(addBtn);
        stage.addActor(upgradeBtn);
        stage.addActor(descBack);
        stage.addActor(selectedBuildingDescLbl);
        stage.addActor(selectedBuildingLbl);
        stage.addActor(priceImg);
        stage.addActor(priceLbl);
        stage.addActor(closeBtn);
        Gdx.input.setInputProcessor(stage);
    }

    private void showPrice() {
        if (selectedBuilding.getPrice() > gameData.getPlayer().getMoney()) {
            priceLblStyle.font = Styles.defaultFont;
        }
        else {
            priceLblStyle.font = Styles.greenFont;
        }
        priceLbl.setStyle(priceLblStyle);
        priceLbl.setText(selectedBuilding.getPrice());
        priceLbl.setVisible(true);
        priceImg.setVisible(true);
    }

    // Отображение информации о выбранной постройке
    private void setSelected(Building building) {
        selectedBuilding = building;
        selectedBuildingLbl.setText(building.getName());
        selectedBuildingLbl.setPosition(2.63f * width / 3 - building.getName().length() * 15,
                3.95f * height / 5);
        selectedBuildingDescLbl.setText(ui_bundle.get("levelOfBuild") + ": " + String.valueOf(building.getLevel())
                + "\n " + ui_bundle.get("efficiency") + ": " +
                selectedBuilding.getEfficiency() + " " + ui_bundle.get("inDay"));
        if (selectedBuilding.getLevel() == 5) {
        selectedBuildingDescLbl.setText(ui_bundle.get("levelOfBuild") + ": " + String.valueOf(
                selectedBuilding.getLevel()) + "\n " + ui_bundle.get("efficiency") +
                ": " + selectedBuilding.getEfficiency() + " " + ui_bundle.get("inDay")
                + "\n " + ui_bundle.get("maxUpgrade"));
        }
        showPrice();
        upgradeBtn.setVisible(true);

    }

    @Override
    public void render(float delta) {
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
    }

    @Override
    public void dispose() {

    }
}
