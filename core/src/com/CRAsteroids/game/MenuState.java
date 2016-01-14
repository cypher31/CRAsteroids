package com.CRAsteroids.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeBitmapFontData;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.sun.prism.paint.Color;

public class MenuState extends GameState{
	//variables
	private SpriteBatch sb;
	private ShapeRenderer sr;
	
	private BitmapFont titleFont;
	private BitmapFont font;
	
	private final String title = "Captain Rick Asteroids";
	
	private int currentItem;
	private String[] menuItems;
	
	private ArrayList<Asteroid> asteroids;
	
	protected MenuState(GameStateManager gsm) {
		super(gsm);
	}
	
	public void init(){
		
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Hyperspace Bold.ttf"));
		FreeTypeFontParameter genPar = new FreeTypeFontParameter();
		//titleFont = gen.generateFont(10);
		
		genPar.size = 30 ; //font size 30

		genPar.magFilter = TextureFilter.Nearest;
		genPar.minFilter = TextureFilter.Nearest;
		
		titleFont = gen.generateFont(genPar); // font size 12 pixels
		
		genPar.size = 12; // font size 12 pixels

		genPar.magFilter = TextureFilter.Nearest;
		genPar.minFilter = TextureFilter.Nearest;
		
		font = gen.generateFont(genPar);
		
		gen.dispose(); 
		
		System.out.println(titleFont.getBounds(title).width);
		titleFont.setColor(255, 255, 255, 1);
		
		menuItems = new String[]{
				"Play",
				"Highscores",
				"Quit"
		};
		
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
	}

	@Override
	public void draw() {
		
		sb.setProjectionMatrix(CRAsteroidsGame.cam.combined);
		sr.setProjectionMatrix(CRAsteroidsGame.cam.combined);
		
		//draw asteroids
		for(int i = 0; i < asteroids.size(); i++){
			asteroids.get(i).draw(sr);
		}
		
		sb.begin();
		
		//draw title
		float width = titleFont.getBounds(title).width;
		titleFont.draw(sb, title, (CRAsteroidsGame.WIDTH - width) / 2, 
				CRAsteroidsGame.HEIGHT - CRAsteroidsGame.HEIGHT / 3);
		
		//draw menu
		for(int i = 0; i < menuItems.length; i++){
			width = font.getBounds(menuItems[i]).width;
			if(currentItem == i) font.setColor(255, 0, 0, 1);
			else font.setColor(255, 255, 255, 1);
			font.draw(sb, menuItems[i], (CRAsteroidsGame.WIDTH - width) / 2,
					CRAsteroidsGame.HEIGHT - (CRAsteroidsGame.HEIGHT / 1.5f + 35 * i));
		}
		
		sb.end();
	}

	@Override
	public void handleInput() {
		
		if(GameKeys.isPressed(GameKeys.UP)){
			if(currentItem > 0){
				currentItem--;
			}
		}
		if(GameKeys.isPressed(GameKeys.DOWN)){
			if(currentItem < menuItems.length - 1){
				currentItem++;
			}
		}
		if(GameKeys.isPressed(GameKeys.ENTER)){
			select();
		}
	}
	
	private void select(){
		//play
		if(currentItem == 0){
			gsm.setState(GameStateManager.PLAY);
		}
		else if(currentItem == 1){
			gsm.setState(GameStateManager.HIGHSCORE);
		}
		else if(currentItem == 2){
			Gdx.app.exit();
		}
	}

	@Override
	public void dispose() {
		sb.dispose();
		sr.dispose();
		titleFont.dispose();
		font.dispose();
	}
	

}
