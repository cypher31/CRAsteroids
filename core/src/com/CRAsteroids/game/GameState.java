package com.CRAsteroids.game;


public abstract class GameState {
	
	protected GameStateManager gsm;
	
	protected GameState(GameStateManager gsm){
		this.gsm = gsm;
		init();
	}
	
	public abstract void init();
	public abstract void update(float dt);
	public abstract void draw();
	public abstract void handleInput();
	public abstract void dispose();
	public abstract void resize(int width, int height);

}
