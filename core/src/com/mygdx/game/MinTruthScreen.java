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

public class MinTruthScreen implements Screen {
    
    GameData gameData;
    Game game;
    Stage stage;
    float height, width;
    ArrayList<Decree> decrees;
    ArrayList<Skin> skins;
    Decree selectedDecree;
    Label selectedDecreeLbl, selectedDecreeDescLbl, requiredTechLbl, priceLbl, pointsLbl;
    Label.LabelStyle priceLblStyle;
    I18NBundle decrees_bundle, ui_bundle, tech_bundle;
    Image priceImg;
    TextButton issueBtn;

    public MinTruthScreen(Game game1, GameData gameData1) {
        // Начальные значения
        this.game = game1;
        this.gameData = gameData1;
        decrees = new ArrayList<>();
        skins = new ArrayList<>();

        height = Gdx.graphics.getHeight();
        width = Gdx.graphics.getWidth();

        decrees_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/decrees"));
        ui_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/ui_strings"));
        tech_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/technologies"));
        priceLblStyle = new Label.LabelStyle();
        priceLblStyle.font = Styles.defaultFont;
    }

    public void createDecrees() {

        // Указы
        Decree StreetTelescreens = new Decree(decrees_bundle.get("streetTS"),
                decrees_bundle.get("streetTSDesc"), Values.STREET_TS_PRICE, 3);
        Skin skin_sts= new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton streetTSBtn = new ImageButton(skin_sts);
        streetTSBtn.setSize(width/10, height/10);
        streetTSBtn.setPosition(0, 3*height/5);
        streetTSBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/telescreen.png")));
        StreetTelescreens.setButton(streetTSBtn);
        StreetTelescreens.addRequiredTech(gameData.getTechnologies().get(tech_bundle.get("telescreen")));
        decrees.add(StreetTelescreens);
        skins.add(skin_sts);

        Decree Posters = new Decree(decrees_bundle.get("poster"), decrees_bundle.get("posterDesc"),
                Values.POSTERS_PRICE, 2);
        Skin skin_p = new Skin(Gdx.files.internal("skin/ui.json"));
        final ImageButton postersBtn = new ImageButton(skin_p);
        postersBtn.setSize(width/10, height/10);
        postersBtn.setPosition(0, 0.8f*height/5);
        postersBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/poster.png")));
        Posters.setButton(postersBtn);
        decrees.add(Posters);
        skins.add(skin_p);

        Decree Leaflet = new Decree(decrees_bundle.get("leaflet"), decrees_bundle.get("leafletDesc"),
                Values.LEAFLET_PRICE, 1);
        Skin skin_lb = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton leafletBtn = new ImageButton(skin_lb);
        leafletBtn.setSize(width/10, height/10);
        leafletBtn.setPosition(1.5f*width/10, 0.8f*height/5);
        leafletBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/leaflet.png")));
        Leaflet.setButton(leafletBtn);
        Leaflet.addRequiredDecree(Posters);
        decrees.add(Leaflet);
        skins.add(skin_lb);

        Decree Films = new Decree(decrees_bundle.get("films"), decrees_bundle.get("filmsDesc"),
                Values.FILMS_PRICE, 5);
        Skin skin_f = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton filmBtn = new ImageButton(skin_f);
        filmBtn.setSize(width/10, height/10);
        filmBtn.setPosition(1.5f*width/10, 1.90f*height/5);
        filmBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/films.png")));
        Films.setButton(filmBtn);
        Films.addRequiredTech(gameData.getTechnologies().get(tech_bundle.get("telescreen")));
        Films.addRequiredDecree(Posters);
        decrees.add(Films);
        skins.add(skin_f);

        Decree Books = new Decree(decrees_bundle.get("books"), decrees_bundle.get("booksDesc"),
                Values.BOOKS_PRICE, 5);
        Skin skin_b = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton booksBtn = new ImageButton(skin_b);
        booksBtn.setSize(width/10, height/10);
        booksBtn.setPosition(1.5f*width/10, 1.35f*height/5);
        booksBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/books.png")));
        Books.setButton(booksBtn);
        Books.addRequiredDecree(Posters);
        decrees.add(Books);
        skins.add(skin_b);

        Decree Music = new Decree(decrees_bundle.get("music"), decrees_bundle.get("musicDesc"),
                Values.MUSIC_PRICE, 5);
        Skin skin_m = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton musicBtn = new ImageButton(skin_m);
        musicBtn.setSize(width/10, height/10);
        musicBtn.setPosition(1.5f*width/10, 2.45f*height/5);
        musicBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/music.png")));
        Music.setButton(musicBtn);
        Music.addRequiredTech(gameData.getTechnologies().get(tech_bundle.get("microphone")));
        Music.addRequiredDecree(Posters);
        decrees.add(Music);
        skins.add(skin_m);

        Decree ComputerGames = new Decree(decrees_bundle.get("games"), decrees_bundle.get("gamesDesc"),
                Values.GAMES_PRICE, 50);
        Skin skin_g = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton computerGamesBtn = new ImageButton(skin_g);
        computerGamesBtn.setSize(width/10, height/10);
        computerGamesBtn.setPosition(3*width/10, 1.90f*height/5);
        computerGamesBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/games.png")));
        ComputerGames.setButton(computerGamesBtn);
        ComputerGames.addRequiredDecree(Films);
        ComputerGames.addRequiredDecree(Music);
        decrees.add(ComputerGames);
        skins.add(skin_g);

        Decree TSOnWork = new Decree(decrees_bundle.get("tsOnWork"), decrees_bundle.get("tsOnWorkDesc"),
                Values.WORK_TS_PRICE, 50);
        Skin skin_tow = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton tsOnWorkBtn = new ImageButton(skin_tow);
        tsOnWorkBtn.setSize(width/10, height/10);
        tsOnWorkBtn.setPosition(1.5f*width/10, 3*height/5);
        tsOnWorkBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/telescreen.png")));
        TSOnWork.setButton(tsOnWorkBtn);
        TSOnWork.addRequiredDecree(StreetTelescreens);
        TSOnWork.addRequiredTech(gameData.getTechnologies().get(tech_bundle.get("telescreen")));
        decrees.add(TSOnWork);
        skins.add(skin_tow);

        Decree HomeTS = new Decree(decrees_bundle.get("houseTS"), decrees_bundle.get("houseTSDesc"),
                Values.HOME_TS_PRICE, 100);
        Skin skin_hts = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton homeTSBtn = new ImageButton(skin_hts);
        homeTSBtn.setSize(width/10, height/10);
        homeTSBtn.setPosition(3*width/10, 3*height/5);
        homeTSBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/telescreen.png")));
        HomeTS.setButton(homeTSBtn);
        HomeTS.addRequiredTech(gameData.getTechnologies().get(tech_bundle.get("telescreen")));
        HomeTS.addRequiredDecree(TSOnWork);
        decrees.add(HomeTS);
        skins.add(skin_hts);

        Decree TwoMinOfHate = new Decree(decrees_bundle.get("twoMinHate"),
                decrees_bundle.get("twoMinHateDesc"), Values.HATE_PRICE, 300);
        Skin skin_tmh = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton twoMinOfHateBtn = new ImageButton(skin_tmh);
        twoMinOfHateBtn.setSize(width/10, height/10);
        twoMinOfHateBtn.setPosition(3*width/10, 2.45f*height/5);
        twoMinOfHateBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/two_min_of_hate.png")));
        TwoMinOfHate.setButton(twoMinOfHateBtn);
        TwoMinOfHate.addRequiredDecree(TSOnWork);
        TwoMinOfHate.addRequiredDecree(Films);
        decrees.add(TwoMinOfHate);
        skins.add(skin_tmh);

        Decree WeekOfHate = new Decree(decrees_bundle.get("weekOfHate"),
                decrees_bundle.get("weekOfHateDesc"), Values.HATE_WEEK_PRICE, 1000);
        Skin skin_woh = new Skin(Gdx.files.internal("skin/ui.json"));
        ImageButton weekOfHateBtn = new ImageButton(skin_woh);
        weekOfHateBtn.setSize(width/10, height/10);
        weekOfHateBtn.setPosition(4.5f*width/10, 2.45f*height/5);
        weekOfHateBtn.getStyle().imageUp = new TextureRegionDrawable(new
                Texture(Gdx.files.internal("textures/week_of_hate.png")));
        WeekOfHate.setButton(weekOfHateBtn);
        WeekOfHate.addRequiredDecree(TwoMinOfHate);
        WeekOfHate.addRequiredDecree(ComputerGames);
        WeekOfHate.addRequiredDecree(HomeTS);
        decrees.add(WeekOfHate);
        skins.add(skin_woh);

        for (final Decree decree: decrees) {
            if (gameData.getPlayer().getMinTruth().getDecrees().contains(decree.getName())) {
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

    public void setSelected(Decree decree) {
        selectedDecree = decree;
        selectedDecreeLbl.setText(decree.getName());
        selectedDecreeDescLbl.setText(" " + decree.getDescription());
        selectedDecreeLbl.setPosition(2.6f*width/3 - decree.getName().length()*15,
                3.95f*height/5);
        if (!decree.isAvailable()) {
            requiredTechLbl.setText(ui_bundle.get("requiresTech") + "\n" +
                    decree.getRequiredTech());

        } else
            requiredTechLbl.setText("");
        if (selectedDecree.isIssued()) {
            priceLbl.setText(ui_bundle.get("decreeIssued"));
            priceLblStyle.font = Styles.greenFont;
            priceLbl.setStyle(priceLblStyle);
        }
        else {
            if (selectedDecree.getPrice() > gameData.getPlayer().getMinTruth().getPoints())
                priceLblStyle.font = Styles.defaultFont;
            else
                priceLblStyle.font = Styles.greenFont;
            priceLbl.setStyle(priceLblStyle);
            priceLbl.setText(String.valueOf(selectedDecree.getPrice()));
        }
        requiredTechLbl.setVisible(true);
        priceImg.setVisible(true);
        priceLbl.setVisible(true);
        issueBtn.setVisible(true);
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
        closeBtn.addListener(new InputListener() {
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                game.setScreen(gameData.getMinistriesScreen());
            }
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        skins.add(skin_close);

        Image background = new Image(new Texture("textures/screen_back.png"));
        background.setSize(width, height);
        background.setPosition(0, 0);

        // Виджеты очков
        final Image pointsImg = new Image(new Texture(Gdx.files.internal("textures/briefcase.png")));
        pointsImg.setSize(width/20, height/11);
        pointsImg.setPosition(width/100, height/40);

        pointsLbl = new Label(ui_bundle.get("total") + ":" +
                gameData.getPlayer().getMinTruth().getPoints()+ ". " + ui_bundle.get("perDay") + ":" +
                gameData.getPlayer().getMinTruth().getPointsPD(), Styles.defaultLbl);
        pointsLbl.setSize(width/3, height/10);
        pointsLbl.setPosition(width/15, height/40);

        priceLblStyle = new Label.LabelStyle();
        priceLblStyle.font = Styles.greenFont;

        priceLbl = new Label("", priceLblStyle);
        priceLbl.setSize(width/10, height/10);
        priceLbl.setPosition(3.05f*width/4, 0.7f*height/5);

        priceImg = new Image(new Texture(Gdx.files.internal("textures/briefcase.png")));
        priceImg.setSize(width/19, height/14);
        priceImg.setPosition(2.8f*width/4, 0.8f*height/5);
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

        Label.LabelStyle requiredStyle = new Label.LabelStyle();
        requiredStyle.font = Styles.defaultFont;

        requiredTechLbl = new Label("", requiredStyle);
        requiredTechLbl.setSize(width/4, height/10);
        requiredTechLbl.setPosition(2.8f*width/4, 1.5f*height/5);

        issueBtn = new TextButton(ui_bundle.get("research"), Styles.defaultBtn);
        issueBtn.setSize(width/5, height/10);
        issueBtn.setPosition(3*width/4, height/15);

        // Нажатие на кнопки
        issueBtn.addListener(new InputListener() {
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Sounds.Click.play();
                if (selectedDecree.isAvailable() && selectedDecree.getPrice() <=
                        gameData.getPlayer().getMinTruth().getPoints() && !selectedDecree.isIssued()) {
                    gameData.getPlayer().getMinTruth().setPoints(
                            gameData.getPlayer().getMinTruth().getPoints() - selectedDecree.getPrice());
                    gameData.getPlayer().getMinTruth().setEfficiency(gameData.getPlayer().getMinTruth().getEfficiency() +
                            selectedDecree.getBonus()*Values.DECREE_EFF);
                    gameData.getPlayer().getMinTruth().getDecrees().add(selectedDecree.getName());
                    selectedDecree.setIssued(true);
                    priceLblStyle.font = Styles.greenFont;
                    priceLbl.setStyle(priceLblStyle);
                    priceLbl.setText(ui_bundle.get("decreeIssued"));
                    Skin skin1 = new Skin(Gdx.files.internal("skin/ui.json"));
                    ImageButton imageButton = new ImageButton(skin1, "researched");
                    imageButton.setSize(width / 10, height / 10);
                    imageButton.setPosition(selectedDecree.getButton().getX(), selectedDecree.getButton().getY());
                    imageButton.getStyle().imageUp = selectedDecree.getButton().getStyle().imageUp;
                    final Decree temp_dec = selectedDecree;
                    temp_dec.setButton(imageButton);
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
        stage.addActor(requiredTechLbl);
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
        pointsLbl.setText((ui_bundle.get("total") + ":" +
                gameData.getPlayer().getMinTruth().getPoints() + ". " + ui_bundle.get("perDay") + ":" +
                gameData.getPlayer().getMinTruth().getPointsPD()));
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
