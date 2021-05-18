
package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
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
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


import java.util.ArrayList;
import java.util.HashMap;

public class GameScreen implements Screen, GestureDetector.GestureListener {
	SpriteBatch batch;
	TextButton regionBtn;
	Texture map_texture, background;
	Label dateLbl, controlLbl, moneyLbl, errorLbl;
	Stage interfaceStage, menu, troopInfo, offer;
	Region selectedRegion;
	OrthographicCamera camera;
	float height, width;
	boolean clicked, redrawBorders, menuMode, showOffer;
	float tx = -8096, ty = -8096;
	I18NBundle regions_bundle, ui_bundle;
	Pixmap pixmap;
	long startTime;
	Game game;
	PlayerCountry playerCountry;
	ComputerCountry computerCountry1, computerCountry2;
	GameData gameData;
	BitmapFont font;
	InputMultiplexer inputMultiplexer;
	boolean placingMode;
	Troop selectedTroop;
	boolean troopSelecting;
	ArrayList<Troop> troops;
	HashMap<String, Country> countries;
	Texture armyTexture, fleetTexture, squadronTexture;
	Sound SHIP;


    public GameScreen(Game aGame, final GameData gameData) {
    	// Начальные значения
    	game = aGame;
    	this.gameData = gameData;
    	playerCountry = gameData.getPlayer();
    	computerCountry1 = gameData.getComputer1();
    	computerCountry2 = gameData.getComputer2();
    	troops = new ArrayList<>();
    	countries = new HashMap<>();
    	countries.put(playerCountry.getName(), playerCountry);
    	countries.put(computerCountry1.getName(), computerCountry1);
    	countries.put(computerCountry2.getName(), computerCountry2);

		// Размеры экрана
		height = Gdx.graphics.getHeight();
		width = Gdx.graphics.getWidth();


		regions_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/region_names"));
		ui_bundle = I18NBundle.createBundle(Gdx.files.internal("properties/ui_strings"));

		startTime = TimeUtils.millis();
		TheGame.resume();
		clicked = false;
		redrawBorders = true;
		menuMode = false;
		showOffer = false;
		placingMode = false;
		troopSelecting = false;

		batch = new SpriteBatch();
		map_texture = new Texture("textures/map.png");
		background = new Texture("textures/background.png");
		armyTexture = new Texture("textures/army.png");
		fleetTexture = new Texture("textures/fleet.png");
		squadronTexture = new Texture("textures/squadron.png");


		selectedRegion = Map.regions.get(0);

		camera = new OrthographicCamera(width, height);
		interfaceStage = new Stage(new ScreenViewport());

		Skin skin = new Skin(Gdx.files.internal("skin/ui.json"));

		// Виджеты даты
		dateLbl = new Label("01.04.1984", Styles.defaultLbl);
		dateLbl.setSize(width/7, height/7);
		dateLbl.setPosition(6.75f*width/8, 7*height/8);
		dateLbl.setAlignment(Align.center);

		Image date_background = new Image(new
				Texture(Gdx.files.internal("textures/back.png")));
		date_background.setSize(width/6,height/5);
		date_background.setPosition(6.65f*width/8, 6.45f*height/8);

		ImageButton pauseBtn = new ImageButton(skin);
		pauseBtn.setSize(width/25, height/20);
		pauseBtn.getStyle().imageUp = new TextureRegionDrawable(new
				Texture(Gdx.files.internal("textures/pause.png")));
		pauseBtn.setPosition(6.82f*width/8, 6.9f*height/8);
		pauseBtn.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Sounds.Click.play();
				TheGame.putOnPause();
				return true;
			}
		});

		Skin skin2 = new Skin(Gdx.files.internal("skin/ui.json"));
		final ImageButton resumeBtn = new ImageButton(skin2);
		resumeBtn.setSize(width/25, height/20);
		resumeBtn.getStyle().imageUp = new TextureRegionDrawable(new
				Texture(Gdx.files.internal("textures/resume.png")));
		resumeBtn.setPosition(7.15f*width/8, 6.9f*height/8);
		resumeBtn.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Sounds.Click.play();
				TheGame.resume();
				TheGame.speed = 1;
				return true;
			}
		});

		Skin skin3 = new Skin(Gdx.files.internal("skin/ui.json"));
		ImageButton speedUpBtn = new ImageButton(skin3);
		speedUpBtn.setSize(width/20, height/20);
		speedUpBtn.getStyle().imageUp = new TextureRegionDrawable(new
				Texture(Gdx.files.internal("textures/speed_up.png")));
		speedUpBtn.setPosition(7.47f*width/8, 6.9f*height/8);
		speedUpBtn.addListener(new InputListener() {
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				Sounds.Click.play();
				TheGame.resume();
				TheGame.speed = 2;
				return true;
			}
		});


		// Виджеты главных показателей
		Image control_background = new Image(new
				Texture(Gdx.files.internal("textures/back.png")));
		control_background.setSize(width/5, height/7);
		control_background.setPosition(0, 0);

		Image control_image = new Image(new
				Texture(Gdx.files.internal("textures/control.png")));
		control_image.setSize(width/30, width/35);
		control_image.setPosition(width/100, 0.3f*height/8);

		controlLbl = new Label(playerCountry.getControl() + "%", Styles.defaultLbl);
		controlLbl.setSize(width/10, height/10);
		controlLbl.setPosition(width/25, 0.15f*height/8);
		controlLbl.setAlignment(Align.left);

		Image money_image = new Image(new Texture(Gdx.files.internal("textures/money.png")));
		money_image.setSize(width/30, width/35);
		money_image.setPosition(width/13, 0.33f*height/8);

		moneyLbl = new Label(String.valueOf((int)playerCountry.getMoney()), Styles.defaultLbl);
		moneyLbl.setSize(width/10, height/10);
		moneyLbl.setPosition(width/9, 0.15f*height/8);
		moneyLbl.setAlignment(Align.left);

		// Кнопка меню
		Skin skin_m = new Skin(Gdx.files.internal("skin/ui.json"));
		ImageButton menuBtn = new ImageButton(skin_m);
		menuBtn.setSize(width/10, height/7);
		menuBtn.setPosition(width/80, 6.7f*height/8);
		menuBtn.getStyle().imageUp = new TextureRegionDrawable(new
				Texture(Gdx.files.internal("textures/menu.png")));
		menuBtn.addListener(new InputListener() {
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Sounds.Click.play();
				TheGame.putOnPause();
				showMenu();
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});

		// Кнопки управления страной
		TextButton researchesBtn = new TextButton(ui_bundle.get("researches"), Styles.defaultBtn);
		researchesBtn.setSize(width/5, height/7);
		researchesBtn.setPosition(0, 4*height/7);
		researchesBtn.addListener(new InputListener() {
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Sounds.Click.play();
				game.setScreen(gameData.getPropagandaResearchScreen());
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});

		TextButton ministriesBtn = new TextButton(ui_bundle.get("ministries"), Styles.defaultBtn);
		ministriesBtn.setSize(width/5, height/7);
		ministriesBtn.setPosition(0, 2.95f*height/7);
		ministriesBtn.addListener(new InputListener() {
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Sounds.Click.play();
				game.setScreen(gameData.getMinistriesScreen());
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});

		TextButton resourcesBtn = new TextButton(ui_bundle.get("resources"), Styles.defaultBtn);
		resourcesBtn.setSize(width/5, height/7);
		resourcesBtn.setPosition(0, 1.9f*height/7);
		resourcesBtn.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Sounds.Click.play();
				game.setScreen(gameData.getResourcesScreen());
			}

			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}

		});

		TextButton troopsBtn = new TextButton(ui_bundle.get("troops"), Styles.defaultBtn);
		troopsBtn.setSize(width/5, height/7);
		troopsBtn.setPosition(0, 0.85f*height/7);
		troopsBtn.addListener(new InputListener() {
			@Override
			public void touchUp(InputEvent event, float x, float y, int pointer, int button) {
				Sounds.Click.play();
				game.setScreen(gameData.getTroopsScreen());
			}

			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});

		regionBtn = new TextButton(selectedRegion.getName(), Styles.defaultBtn);
		regionBtn.setSize(width/2.4f, height/4.5f);
		regionBtn.setPosition(7*width/24, -height/26);
		regionBtn.addListener(new InputListener() {
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Sounds.Click.play();
				if (troopSelecting) {
					placingMode = true;
				}
				else {
					gameData.getRegionScreen().setRegion(selectedRegion);
					game.setScreen(gameData.getRegionScreen());
				}
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});

		errorLbl = new Label("", Styles.defaultLbl);
		errorLbl.setSize(width/2, height/10);
		errorLbl.setPosition(width/4, height/8);
		errorLbl.setVisible(false);

		// Добавление виджетов на экран
		interfaceStage.addActor(date_background);
		interfaceStage.addActor(dateLbl);
		interfaceStage.addActor(regionBtn);
		interfaceStage.addActor(pauseBtn);
		interfaceStage.addActor(resumeBtn);
		interfaceStage.addActor(speedUpBtn);
		interfaceStage.addActor(control_background);
		interfaceStage.addActor(control_image);
		interfaceStage.addActor(controlLbl);
		interfaceStage.addActor(researchesBtn);
		interfaceStage.addActor(ministriesBtn);
		interfaceStage.addActor(resourcesBtn);
		interfaceStage.addActor(money_image);
		interfaceStage.addActor(moneyLbl);
		interfaceStage.addActor(menuBtn);
		interfaceStage.addActor(troopsBtn);
		interfaceStage.addActor(errorLbl);

		inputMultiplexer = new InputMultiplexer();
		inputMultiplexer.addProcessor(interfaceStage);
		inputMultiplexer.addProcessor(new GestureDetector(this));

		SHIP = Gdx.audio.newSound(Gdx.files.internal("sounds/ship.mp3"));

	}

	public void setPlacingMode(boolean placingMode) {
		this.placingMode = placingMode;
	}

	public void setSelectedTroop(Troop selectedTroop) {
		this.selectedTroop = selectedTroop;
	}

	@Override
	public void show() {
		Gdx.input.setInputProcessor(inputMultiplexer);
	}


    // Отрисовка
	@Override
	public void render (float delta) {
    	if (!playerCountry.isAlive()) {
    		gameData.getEndgameScreen().setHeading(ui_bundle.get("defeat"));
    		if (playerCountry.getRegions().size() == 0)
    		    gameData.getEndgameScreen().setDescription(ui_bundle.get("occupied"));
    		else
				gameData.getEndgameScreen().setDescription(ui_bundle.get("insurrection"));
    		game.setScreen(gameData.getEndgameScreen());
		}
    	if (playerCountry.isWin() || (!computerCountry1.isAlive() && !computerCountry2.isAlive())) {
    		gameData.getEndgameScreen().setHeading(ui_bundle.get("victory"));
    		if (playerCountry.getControl() >= 100)
    			gameData.getEndgameScreen().setDescription(ui_bundle.get("controlled"));
    		else
    			gameData.getEndgameScreen().setDescription(ui_bundle.get("capturedWorld"));
    		game.setScreen(gameData.getEndgameScreen());
		}

		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setProjectionMatrix(camera.combined);

		batch.begin();
		batch.draw(background, -4096, -4096);

		// Если игра идет (выполняется каждую секунду)
		if (TimeUtils.timeSinceMillis(startTime) > Values.DAY_LENGTH/TheGame.speed && !TheGame.onPause) {
			TheGame.nextDay();
			if (gameData.getComputer1().isAlive())
				gameData.getComputer1().setTarget();
			if (gameData.getComputer2().isAlive())
			    gameData.getComputer2().setTarget();
			gameData.getPlayer().calcPoints();
			startTime = TimeUtils.millis();
			dateLbl.setText(TheGame.getDate());
			controlLbl.setText(playerCountry.getControl() + "%");
			moneyLbl.setText(String.valueOf(playerCountry.getMoney()));
			for (Troop troop: troops) {
				troop.checkForEnemies();
				if (troop.hasTarget()) {
					troop.battle();
					Sounds.War.play();
				}
			}
			errorLbl.setVisible(false);
		}


		// Если нужно перерисовать границы
		if (redrawBorders) {
			pixmap = new Pixmap(Gdx.files.internal("textures/map.png"));
			for (Region region: Map.regions) {
				region.setCountry(countries.get(region.getCountryName()));
				if (region.getCountryName().equals(playerCountry.getName()))
					pixmap.setColor(new Color(0.643137255f, 0, 0, 1));
				else if (region.getCountryName().equals(computerCountry1.getName()))
					pixmap.setColor(new Color(0, 0, 0.643137255f, 1));
				else
					pixmap.setColor(new Color(0, 0.643137255f, 0, 1));
				pixmap.fillRectangle((int) region.getX1() + 2048, 1024 -
						(int) region.getY2(), 4, (int) region.getScaleY());
				pixmap.fillRectangle((int) region.getX1() + 2048, 1024 -
								(int) region.getY1(), (int) region.getScaleX(), 4);
				pixmap.fillRectangle((int) region.getX2() + 2048, 1024 -
						(int) region.getY2(), 4, (int) region.getScaleY() + 4);
				pixmap.fillRectangle((int) region.getX1() + 2048, 1021 -
								(int) region.getY2(), (int) region.getScaleX() + 4, 4);
			}
			FileHandle fh = Gdx.files.local("map_borders.cim");
			PixmapIO.writeCIM(fh, pixmap);
			redrawBorders = false;
			map_texture.dispose();
			map_texture = new Texture(pixmap);
			pixmap.dispose();
			pixmap = null;
		}

		// Выделение региона
		if (clicked) {
			for (Region region: Map.regions) {
				if (region.clicked(tx, ty)) {
					Sounds.Click.play();
					troopSelecting = false;
					for (Troop troop: region.getTroops())
						if (troop.isClicked(tx, ty) && troop.getCountry().getName().equals(playerCountry.getName())) {
							regionBtn.setText(ui_bundle.get("moveTroops"));
							troopSelecting = true;
							selectedTroop = troop;
							showTroopInfo();
						}
					selectedRegion = region;
					break;
				}
			}
			if (!troopSelecting)
				regionBtn.setText(selectedRegion.getName());
			FileHandle fh = Gdx.files.local("map_borders.cim");
			pixmap = PixmapIO.readCIM(fh);
			pixmap.setColor(Color.BLACK);
			pixmap.fillRectangle((int) selectedRegion.getX1() + 2048, 1024 -
					(int) selectedRegion.getY2(), 4, (int)selectedRegion.getScaleY());
			pixmap.fillRectangle((int) selectedRegion.getX1() + 2048, 1024 -
							(int) selectedRegion.getY1(),(int)selectedRegion.getScaleX(),
					4);
			pixmap.fillRectangle((int)selectedRegion.getX2() + 2048, 1024 -
					(int)selectedRegion.getY2(), 4,(int)selectedRegion.getScaleY() + 4);
			pixmap.fillRectangle((int) selectedRegion.getX1() + 2048, 1021 -
							(int) selectedRegion.getY2(), (int)selectedRegion.getScaleX() + 4,
					4);
			map_texture.dispose();
			map_texture = new Texture(pixmap);
			pixmap.dispose();
			pixmap = null;
			clicked = false;

			if (placingMode) {
				boolean canPlace = true;
				if (selectedTroop instanceof Army && !selectedRegion.isLandAccess())
					canPlace = false;
				else if (selectedTroop instanceof Fleet && !selectedRegion.isWaterAccess())
					canPlace = false;
				canPlace = canPlace & (selectedRegion.getCountryDivisions(playerCountry.getName())
						< Values.MAX_DIVISIONS);
				if (canPlace) {
					if (tx - width / 50 <= selectedRegion.getX1())
						tx = selectedRegion.getX1() + width / 50;
					if (tx + width / 50 >= selectedRegion.getX2())
						tx = selectedRegion.getX2() - width / 50;
					if (ty - width / 50 <= selectedRegion.getY1())
						ty = selectedRegion.getY1() + width / 50;
					if (ty + width / 50 >= selectedRegion.getY2())
						ty = selectedRegion.getY2() - width / 50;
					if (selectedTroop.getRegion() != null)
						selectedTroop.getRegion().getTroops().remove(selectedTroop);
					selectedTroop.place(selectedRegion, tx - width / 50, ty - width / 50);
					selectedRegion.getTroops().add(selectedTroop);
					selectedTroop.setRegion(selectedRegion);
					playerCountry.getTroops().add(selectedTroop);
					selectedTroop.setCountry(playerCountry);
					if (!selectedRegion.getCountryName().equals(playerCountry.getName())) {
						ComputerCountry country = (ComputerCountry)selectedRegion.getCountry();
						country.declareWar(playerCountry);
					}
					if (selectedTroop instanceof Fleet)
						Sounds.Ship.play();
                    if (selectedTroop instanceof Squadron)
                    	Sounds.Plane.play();
                    if (selectedTroop instanceof Army)
                    	Sounds.Army.play();

				}
				else {
					errorLbl.setText(ui_bundle.get("cantPlace"));
					errorLbl.setVisible(true);
				}
				placingMode = false;
				troopSelecting = false;
			}

		}

		batch.draw(map_texture, -map_texture.getWidth()/2.0f, -map_texture.getHeight()/2.0f);

		if (placingMode)
			regionBtn.setText(ui_bundle.get("selectRegion"));

		for (Country country: countries.values()) {
			for (Troop troop: country.getTroops())
				if (!troops.contains(troop))
					troops.add(troop);
		}

		for (Troop troop: troops) {
			if (troop.isAlive()) {
				Texture texture = null;
				if (troop instanceof Army)
					texture = armyTexture;
				else if (troop instanceof Squadron)
					texture = squadronTexture;
				else if (troop instanceof Fleet)
					texture = fleetTexture;
				Sprite troopSprite = new Sprite(texture);
				troopSprite.setPosition(troop.getX(), troop.getY());
				troopSprite.setSize(width / 25, width / 25);
				troopSprite.draw(batch);
			}
			else {
				troops.remove(troop);
				countries.get(troop.getCountry().getName()).getTroops().remove(troop);
				troop.getRegion().getTroops().remove(troop);
				break;
			}
		}

		batch.end();
		interfaceStage.act();
		interfaceStage.draw();

		if (menuMode) {
			menu.act();
			menu.draw();
		}

		if (showOffer) {
			offer.act();
			offer.draw();
		}

		if (troopSelecting) {
			troopInfo.act();
			troopInfo.draw();
		}

		for (Region region: Map.regions) {
			region.checkCountry(playerCountry, computerCountry1,
					computerCountry2);
			if (region.isRedrawBorders()) {
				redrawBorders = true;
				region.setRedrawBorders(false);
			}
		}

		if (playerCountry.isOfferingPeace() && !showOffer) {
			TheGame.putOnPause();
			offerPeace();
		}
	}

	public void offerPeace() {
    	showOffer = true;
    	offer = new Stage(new ScreenViewport());
    	Gdx.input.setInputProcessor(offer);

		Image background = new Image(new Texture("textures/menu_back.png"));
		background.setSize(width/3, height/2);
		background.setPosition(width/3, height/4);

		Image background_map = new Image(new Texture("textures/back_menumode.png"));
		background_map.setSize(width, height);
		background_map.setPosition(0, 0);

		Label offerLbl = new Label(" " + playerCountry.getOfferingCountry().getName() + " " +
				ui_bundle.get("offerPeace"), Styles.defaultLbl);
		offerLbl.setSize(width/10, height/10);
		offerLbl.setPosition(width/3, 2.3f*height/4);

		TextButton yesBtn = new TextButton(ui_bundle.get("yes"), Styles.defaultBtn);
		yesBtn.setSize(width/15, height/10);
		yesBtn.setPosition(43*width/120, height/4);
		yesBtn.addListener(new InputListener() {
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Sounds.Click.play();
				TheGame.resume();
				offer.dispose();
				showOffer = false;
				playerCountry.setSettlePeace(true);
				playerCountry.setOfferingPeace(false);
				show();
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});

		TextButton noBtn = new TextButton(ui_bundle.get("no"), Styles.defaultBtn);
		noBtn.setSize(width/15, height/10);
		noBtn.setPosition(23*width/40, height/4);
		noBtn.addListener(new InputListener() {
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Sounds.Click.play();
				TheGame.resume();
				offer.dispose();
				showOffer = false;
				playerCountry.setOfferingPeace(false);
				show();
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});

		offer.addActor(background_map);
		offer.addActor(background);
		offer.addActor(offerLbl);
		offer.addActor(noBtn);
		offer.addActor(yesBtn);
	}

	public void showMenu() {
    	menuMode = true;
    	menu = new Stage(new ScreenViewport());
		Gdx.input.setInputProcessor(menu);

		Image background = new Image(new Texture("textures/menu_back.png"));
		background.setSize(width/3, height/2);
		background.setPosition(width/3, height/4);

		Image background_map = new Image(new Texture("textures/back_menumode.png"));
		background_map.setSize(width, height);
		background_map.setPosition(0, 0);

		Label menuLbl = new Label(ui_bundle.get("menu"), Styles.defaultLbl);
		menuLbl.setSize(width/10, height/10);
		menuLbl.setPosition(9*width/20, 2.7f*height/4);
		menuLbl.setAlignment(Align.center);

		TextButton resumeBtn = new TextButton(ui_bundle.get("resume"), Styles.defaultBtn);
		resumeBtn.setSize(width/3.5f, height/7);
		resumeBtn.setPosition(width/2.8f, height/2);
		resumeBtn.addListener(new InputListener() {
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Sounds.Click.play();
				TheGame.resume();
				menu.dispose();
				menuMode = false;
				show();
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});

		TextButton exitBtn = new TextButton(ui_bundle.get("saveAndExit"), Styles.defaultBtn);
		exitBtn.setSize(width/3.5f, height/7);
		exitBtn.setPosition(width/2.8f, height/2.5f);
		exitBtn.addListener(new InputListener() {
			public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
				Sounds.Click.play();
				gameData.SaveData();
				Gdx.app.exit();
			}
			public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
				return true;
			}
		});

		menu.addActor(background_map);
		menu.addActor(background);
		menu.addActor(menuLbl);
		menu.addActor(resumeBtn);
		menu.addActor(exitBtn);

	}

	public void showTroopInfo() {
		troopInfo = new Stage(new ScreenViewport());
		Image back = new Image(new Texture("textures/back.png"));
		back.setSize(width/2f, height/3);
		back.setPosition(2.8f*width/4, 0);
		troopInfo.addActor(back);
		Label divisionName, divisionTotal;
		float y = 0.65f*height/3;
		for (String name: selectedTroop.getDivisions().keySet()) {
			divisionName = new Label(ui_bundle.get(name), Styles.defaultLbl);
			divisionName.setSize(width/20, height/20);
			divisionName.setPosition(3.05f*width/4, y);
			troopInfo.addActor(divisionName);

            divisionTotal = new Label("x" + selectedTroop.getDivisions().get(name),
					Styles.defaultLbl);
            divisionTotal.setSize(width/20, height/20);
            divisionTotal.setPosition(3.7f*width/4, y);
            troopInfo.addActor(divisionTotal);

			y -= 0.2f*height/3;
		}


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
	}

	@Override
	public void dispose () {
	}

	// Касание
	@Override
	public boolean touchDown(float x, float y, int pointer, int button) {
		tx = camera.position.x + (x - Gdx.graphics.getWidth()/2f)*camera.zoom;
		ty = camera.position.y + (Gdx.graphics.getHeight()/2f - y)*camera.zoom;
		clicked = true;
		return true;
	}

	@Override
	public boolean tap(float x, float y, int count, int button) {
		return false;
	}

	@Override
	public boolean longPress(float x, float y) {
		return false;
	}

	@Override
	public boolean fling(float velocityX, float velocityY, int button) {
		return false;
	}

	// Проведение по экрану
	@Override
	public boolean pan(float x, float y, float deltaX, float deltaY) {
		float xc = camera.position.x;
		float yc = camera.position.y;
		if (xc - deltaX > -width/2 && xc - deltaX < width/2 &&
		yc + deltaY > -height/2 && yc + deltaY < height/2) {
			camera.translate(-deltaX, deltaY);
			camera.update();
		}
		clicked = false;
		return true;
	}

	@Override
	public boolean panStop(float x, float y, int pointer, int button) {
		return false;
	}

	// Масштабирование
	@Override
	public boolean zoom(float initialDistance, float distance) {
		if (camera.zoom + 0.02 <= 1.8 && distance < initialDistance) {
			camera.zoom += 0.02;
			camera.update();
		}
		else if (camera.zoom - 0.02 >= 0.6 && distance > initialDistance) {
			camera.zoom -= 0.02;
			camera.update();
		}
		return true;
	}

	@Override
	public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
		return false;
	}

	@Override
	public void pinchStop() {

	}
}
