package com.CRAsteroids.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuState extends GameState{
	
	private String TAG = System.class.getName();
	
	//variables
	private SpriteBatch sb;
	private ShapeRenderer sr;
	private Viewport menuViewport;
	
	private float fontWidth;
	private float fontHeight;
	//Stage
	private Stage stage;
	//Font
	//private BitmapFont titleFont;
	private BitmapFont font;
	private BitmapFont optionStyle;
	private LabelStyle titleStyle;
	//Buttons
	private TextButton playButton;
	private TextButton highScoreButton;
	private TextButton quitButton;
	private TextButtonStyle textButtonStyle;
	
	//Text
	private final String title = "Captain Rick Asteroids";
	private final String play = "Play";
	private final String highScore = "Highscore";
	private final String quit = "Quit";
	
	private int currentItem;
	private String[] menuItems;
	
	private float currentScreenWidth;
	private float currentScreenHeight;
	
	private ArrayList<Asteroid> asteroids;
	
	protected MenuState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init(){
		
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		
		currentScreenWidth = Gdx.graphics.getWidth();
		currentScreenHeight = Gdx.graphics.getHeight();
//		
//		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Hyperspace Bold.ttf"));
//		FreeTypeFontParameter genPar = new FreeTypeFontParameter();
//		//titleFont = gen.generateFont(10);
//		
//		genPar.size = 50 ; //font size 30
//
//		genPar.magFilter = TextureFilter.Nearest;
//		genPar.minFilter = TextureFilter.Nearest;
//		
//		titleFont = gen.generateFont(genPar); // font size 12 pixels
//		
//		genPar.size = 12; // font size 12 pixels
//
//		genPar.magFilter = TextureFilter.Nearest;
//		genPar.minFilter = TextureFilter.Nearest;
//		
//		font = gen.generateFont(genPar);
//		
//		gen.dispose(); 
//		
//		System.out.println(titleFont.getBounds(title).width);
//		titleFont.setColor(255, 255, 255, 1);
		
		//Generate font
//		SmartFontGenerator fontGen = new SmartFontGenerator();
//		FileHandle exoFile = Gdx.files.internal("fonts/Hyperspace Bold.ttf");
//		BitmapFont fontSmall = fontGen.createFont(exoFile, "exo-small", 20);
//		BitmapFont fontMedium = fontGen.createFont(exoFile, "exo-medium", 25);
//		BitmapFont fontLarge = fontGen.createFont(exoFile, "exo-large", 30);
//
//		Label.LabelStyle smallStyle = new Label.LabelStyle();
//		smallStyle.font = fontSmall;
//		Label.LabelStyle mediumStyle = new Label.LabelStyle();
//		mediumStyle.font = fontMedium;
//		Label.LabelStyle largeStyle = new Label.LabelStyle();
//		largeStyle.font = fontLarge;
		
		menuViewport = new FitViewport(CRAsteroidsGame.WIDTH, CRAsteroidsGame.HEIGHT);
		stage = new Stage(menuViewport);
		Gdx.input.setInputProcessor(stage);

		if(currentScreenWidth <=800){
			titleStyle = CRAsteroidsGame.smallStyle;
		}else if(currentScreenWidth >=800 && currentScreenWidth <= 1600){
			titleStyle = CRAsteroidsGame.mediumStyle;
		}else{
			titleStyle = CRAsteroidsGame.largeStyle;
		}
		
		Label titleName = new Label(title, titleStyle);
//		Label playOption = new Label(play, smallStyle);
//		Label highScoreOption = new Label(highScore, smallStyle);
//		Label quitOption = new Label(quit, smallStyle);

		//buttons
		if(currentScreenWidth <=800){
			optionStyle = CRAsteroidsGame.smallStyle.font;
		}else{
			optionStyle = CRAsteroidsGame.mediumStyle.font;
		}
		
		textButtonStyle = new TextButtonStyle();
		textButtonStyle.font = optionStyle;
		playButton = new TextButton("Play", textButtonStyle);
		highScoreButton = new TextButton("Highscore", textButtonStyle);
		quitButton = new TextButton("Quit", textButtonStyle);
		
		
		
		
		//add actors
		Table titleTable = new Table();
		Table optionsTable = new Table();
		
		titleTable.setFillParent(true);
		optionsTable.setFillParent(true);
		
		stage.addActor(titleTable);
		stage.addActor(optionsTable);
		
			//title 
		titleTable.align(Align.top).padTop(currentScreenHeight * 0.1f);
		titleTable.add(titleName).top();
		
//			//options
//		optionsTable.align(Align.center);
//		optionsTable.add(playOption).spaceBottom(10).row();
//		optionsTable.add(highScoreOption).spaceBottom(10).row();
//		optionsTable.add(quitOption).spaceBottom(10).row();

			//buttons
		optionsTable.add(playButton).align(Align.center).row();
		optionsTable.add(highScoreButton).align(Align.center).row();
		optionsTable.add(quitButton).align(Align.center).row();
		
		//Debug
		titleTable.setDebug(true);
		optionsTable.setDebug(true);
		playButton.setDebug(true);
		highScoreButton.setDebug(true);
		quitButton.setDebug(true);
//		
//		menuItems = new String[]{
//				"Play",
//				"Highscores",
//				"Quit"
//		};
		
		asteroids = new ArrayList<Asteroid>();
		for(int i = 0; i < 6; i++){
			asteroids.add(new Asteroid(MathUtils.random(CRAsteroidsGame.WIDTH), 
					MathUtils.random(CRAsteroidsGame.HEIGHT), Asteroid.LARGE));
		}
		
	}

	@Override
	public void update(float dt) {
		
		handleInput();
		
		for(int i = 0; i < asteroids.size(); i++){
			asteroids.get(i).update(dt);
		}
		
		if(Gdx.graphics.getWidth() != currentScreenWidth || 
				Gdx.graphics.getHeight() != currentScreenHeight){
			System.out.println(stage.getWidth());
			System.out.println(stage.getHeight());
			Gdx.app.debug(TAG, "App restart");
			init();
		}
		
	}

	@Override
	public void resize(int width, int height) {
		stage.getViewport().update(width, height, true);
	}

	@Override
	public void draw() {
		
		sb.setProjectionMatrix(CRAsteroidsGame.cam.combined);
		sr.setProjectionMatrix(CRAsteroidsGame.cam.combined);
		
		//draw asteroids
		for(int i = 0; i < asteroids.size(); i++){
			asteroids.get(i).draw(sr);
		}
		
//		sb.begin();
		
//		//draw title
		//float width = titleFont.getBounds(title).width;
//		titleFont.draw(sb, title, (CRAsteroidsGame.WIDTH - width) / 2, 
//				CRAsteroidsGame.HEIGHT - CRAsteroidsGame.HEIGHT / 3);
		
		//draw menu
//		for(int i = 0; i < menuItems.length; i++){
//			width = font.getBounds(menuItems[i]).width;
//			if(currentItem == i) font.setColor(255, 0, 0, 1);
//			else font.setColor(255, 255, 255, 1);
//			font.draw(sb, menuItems[i], (CRAsteroidsGame.WIDTH - width) / 2,
//					CRAsteroidsGame.HEIGHT - (CRAsteroidsGame.HEIGHT / 1.5f + 35 * i));
//		}
		
//		sb.end();
		
		stage.act(Gdx.graphics.getDeltaTime());
		stage.draw();
	}

	@Override
	public void handleInput() {
		
		//button click
		playButton.addListener(new ChangeListener(){
		@Override
		public void changed(ChangeEvent event, Actor actor){
			System.out.println("play pressed");
			gsm.setState(GameStateManager.PLAY);
		}
		});
		
		highScoreButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				System.out.println("highScore pressed");
				gsm.setState(GameStateManager.HIGHSCORE);
			}
			});
		
		quitButton.addListener(new ChangeListener(){
			@Override
			public void changed(ChangeEvent event, Actor actor){
				System.out.println("quit pressed");
				Gdx.app.exit();
			}
			});
		
//		if(GameKeys.isPressed(GameKeys.UP)){
//			if(currentItem > 0){
//				currentItem--;
//			}
//		}
//		if(GameKeys.isPressed(GameKeys.DOWN)){
//			if(currentItem < menuItems.length - 1){
//				currentItem++;
//			}
//		}
//		if(GameKeys.isPressed(GameKeys.ENTER)){
//			select();
//		}
	}
	
//	private void select(){
//		//play
//		if(currentItem == 0){
//			gsm.setState(GameStateManager.PLAY);
//		}
//		else if(currentItem == 1){
//			gsm.setState(GameStateManager.HIGHSCORE);
//		}
//		else if(currentItem == 2){
//			Gdx.app.exit();
//		}
//	}

	@Override
	public void dispose() {
//		sb.dispose();
//		sr.dispose();
//		titleFont.dispose();
//		font.dispose();
	}

	

}
