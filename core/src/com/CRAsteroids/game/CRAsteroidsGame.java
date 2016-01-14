package com.CRAsteroids.game;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class CRAsteroidsGame implements ApplicationListener {
	
	//width and height of screen
	public static int WIDTH;
	public static int HEIGHT;
	
	//Camera perpendicular to game plane
	public static OrthographicCamera cam;
	
	private GameStateManager gsm;

	@Override
	public void create() {
		
		//width of screen
		WIDTH = Gdx.graphics.getWidth();
		HEIGHT = Gdx.graphics.getHeight();
		
		//create camera
		cam = new OrthographicCamera(WIDTH, HEIGHT);
		//set camera position
		cam.position.set(WIDTH / 2, HEIGHT /2, 0);
		//updates camera
		cam.update();
		
		//sets input processor to this
		Gdx.input.setInputProcessor(new GameInputProcessor());
		
//		Jukebox.load("sounds/explode.ogg", "explode");
//		Jukebox.load("sounds/extralife.ogg", "extralife");
//		Jukebox.load("sounds/largesaucer.ogg", "largesaucer");
//		Jukebox.load("sounds/pulsehigh.ogg", "pulsehigh");
//		Jukebox.load("sounds/pulselow.ogg", "pulselow");
//		Jukebox.load("sounds/saucershoot.ogg", "saucershoot");
//		Jukebox.load("sounds/shoot.ogg", "shoot");
//		Jukebox.load("sounds/smallsaucer.ogg", "smallsaucer");
//		Jukebox.load("sounds/thruster.ogg", "thruster");
		
		gsm = new GameStateManager();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void render() {
		//clear screen to black
		Gdx.gl20.glClearColor(0, 0, 0, 1);
		Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.draw();
		
		GameKeys.update();
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
	}
}
