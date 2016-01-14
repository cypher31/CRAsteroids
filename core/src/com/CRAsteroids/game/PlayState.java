package com.CRAsteroids.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter.Particle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class PlayState extends GameState {
	
	private SpriteBatch sb;
	private ShapeRenderer sr;
	
	private BitmapFont font;
	private Player hudPlayer;
	
	private Player player;
	private ArrayList<Bullet> bullets;
	private ArrayList<Asteroid> asteroids;
	private ArrayList<Bullet> enemyBullets;
	
	private FlyingSaucer flyingSaucer;
	private float fsTimer;
	private float fsTime;
	
	private ArrayList<Particle> particles;
	
	private int level;
	private int totalAsteroids;
	private int numAsteroidsLeft;
	
	private float maxDelay;
	private float minDelay;
	private float currentDelay;
	private float bgTimer;
	private boolean playLowPulse;

	protected PlayState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Hyperspace bold.ttf"));
		FreeTypeFontParameter genPar = new FreeTypeFontParameter();
		
		genPar.size = 20;
		
		BitmapFont font = gen.generateFont(genPar);
		gen.dispose();
		
		bullets = new ArrayList<Bullet>();
		
		player = new Player(bullets);
		
		asteroids = new ArrayList<Asteroid>();
		
		particles = new ArrayList<Particle>();
		
		level = 1;
		spawnAsteroids();
		
		hudPlayer = new Player(null);
		
		fsTimer = 0;
		fsTime = 15;
		enemyBullets = new ArrayList<Bullet>();
		
		//Set up bg music
		maxDelay = 1;
		minDelay = .25f;
		currentDelay = maxDelay;
		bgTimer = maxDelay;
		playLowPulse = true;
	}
	
	private void createParticles(float x, float y){
		for(int i = 0; i < 6; i++){
			particles.add(new Particle(x, y));
		}
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw() {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		font.dispose();
	}

}
