package com.CRAsteroids.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.ParticleEmitter.Particle;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

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
	
	private void splitAsteroid(Asteroid a){
		createParticles(a.getx(), a.gety());
		numAsteroidsLeft--;
		currentDelay = ((maxDelay - minDelay) * numAsteroidsLeft / totalAsteroids)
				+ minDelay;
		if(a.getType() == Asteroid.LARGE){
			asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.MEDIUM));
			asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.MEDIUM));
		}
		if(a.getType() == Asteroid.MEDIUM){
			asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.SMALL));
			asteroids.add(new Asteroid(a.getx(), a.gety(), Asteroid.SMALL));
		}
	}
	
	private void spawnAsteroids(){
		asteroids.clear();
		
		int numToSpawn = 4 + level - 1;
		totalAsteroids = numToSpawn * 7;
		numAsteroidsLeft = totalAsteroids;
		currentDelay = maxDelay;
		
		for(int i = 0; i < numToSpawn; i++){
			float x = MathUtils.random(CRAsteroidsGame.WIDTH);
			float y = MathUtils.random(CRAsteroidsGame.HEIGHT);
			
			float dx = x - player.getx();
			float dy = y - player.gety();
			float dist = (float) Math.sqrt(dx * dx + dy * dy);
			
			while (dist < 100){
				x = MathUtils.random(CRAsteroidsGame.WIDTH);
				y = MathUtils.random(CRAsteroidsGame.HEIGHT);
				dx = x - player.getx();
				dy = y - player.gety();
				dist = (float) Math.sqrt(dx * dx + dy * dy);
			}
			
			asteroids.add(new Asteroid(x, y, Asteroid.LARGE));
		}
	}

	@Override
	public void update(float dt) {
		handleInput();
		
		//next level
		if(asteroids.size() == 0){
			level++;
			spawnAsteroids();
		}
		
		//update player
		player.update(dt);
		if(player.isDead()){
			if(player.getLives() == 0){
				Jukebox.stopAll();
				Save.gd.setTentativeScore(player.getScore());
				gsm.setState(GameStateManager.GAMEOVER);
				return;
			}
			player.reset();
			player.loseLife();
			flyingSaucer = null;
			Jukebox.stop("smallsaucer");
			Jukebox.stop("largesaucer");
			return;
		}
		//update player bullets
		for(int i = 0; i < bullets.size(); i++){
			bullets.get(i).update(dt);
			if(bullets.get(i).shouldRemove()){
				bullets.remove(i);
				i--;
			}
		}
		//update flying saucer
		if(flyingSaucer == null){
			fsTimer += dt;
			if(fsTimer > fsTime){
				fsTimer = 0;
				int type = MathUtils.random() < 0.5 ? FlyingSaucer.SMALL : FlyingSaucer.LARGE;
				int direction = MathUtils.random() < 0.5 ? FlyingSaucer.RIGHT: FlyingSaucer.LEFT;
				flyingSaucer = new FlyingSaucer(
						type,
						direction,
						player,
						enemyBullets
						);
			}
		}
		//if already a suacer
		else{
			flyingSaucer.update(dt);
			if(flyingSaucer.shoudlRemove()){
				flyingSaucer = null;
				Jukebox.stop("smallsaucer");
				Jukebox.stop("largesaucer");
			}
		}
		
		//update fs bullets
		for(int i = 0; i < enemyBullets.size(); i++){
			enemyBullets.get(i).update(dt);
			if(enemyBullets.get(i).shouldRemove()){
				enemybullets.remove(i);
				i++
			}
		}
		
		//update asteroids
		for(int i = 0; i < asteroids.size(); i++){
			asteroids.get(i).update(dt);
			if(asteroids.get(i).shouldRemove()){
				asteroids.remove(i);
				i--;
			}
		}
		
		//update particles
		for(int i = 0; i < particles.size(); i++){
			particles.get(i).update(dt);
			if(particles.get(i).shouldRemove()){
				particles.remove(i);
				i--;
			}
		}
		
		//check collisions
		checkCollisions();
		
//		//play bg music
//		bgTimer += dt;
//		if(!player.isHit() && bgTimer >= currentDelay){
//			if(playLowPulse){
//				Jukebox.play("pulselow");
//			}
//			else{
//				Jukebox.play("pulsehigh");
//			}
//			playLowPulse = !playLowPulse;
//			bgTimer = 0;
//		}
	}
	
	private void checkCollisions(){
		//player-asteroid collision
		if(!player.isHit()){
			for(int i = 0; i < asteroids.size(); i++){
				Asteroid a = asteroids.get(i);
				if(a.intersects(player)){
					player.hit();
					asteroids.remove(i);
					i--;
					splitAsteroid(a);
//					Jukebox.play("explode");
					break;
				}
			}
		}
		
		//bullet-asteroid collision
		for(int i = 0; i < bullets.size(); i++){
			Bullet b = bullets.get(i);
			for(int j = 0; j < asteroids.size(); j++){
				Asteroid a = asteroids.get(j);
				if(a.contains(b.getx(), b.gety())){
					bullets.remove(i);
					i--;
					asteroids.remove(j);
					j--;
					splitAsteroid(a);
					player.incrementScore(a.getScore());
	//				Jukebox.play("explode");
					break;
				}
			}
		}
		
		//player-flying saucer collision
		if(flyingSaucer != null){
			if(player.intersects(flyingSaucer)){
				player.hit();
				createParticles(player.getx(), player.gety());
				createParticles(flyingSaucer.getx(), flyingSaucer.gety());
				flyingSaucer = null;
//				Jukebox.stop("smallsaucer");
//				Jukebox.stop("largesaucer");
//				Jukebox.play("explode");
			}
		}
		
		//bullet-flying saucer collision
		if(flyignSaucer != null){
			for(int i = 0; i < bullets.size(); i++){
				Bullet b = bullets.get(i);
				if(flyingSaucer.contains(b.getx(), bgety())){
					bullets.remove(i);
					i--;
					createParticles(flyingSaucer.getx(), flyingSaucer.gety());
					player.incrementScore(flyingSaucer.getScore());
//					Jukebox.stop("smallsaucer");
//					Jukebox.stop("largesaucer");
//					Jukebox.play("explode")
					break;
				}
			}
		}
		
		//player-enemy bullets collision
		if(!player.isHit()){
			for(int i = 0; i < enemyBullets.size(); i++){
				Bullet b = enemyBullets.get(i);
				if(player.contains(b.getx(), b.gety())){
					player.hit();
					enemyBullets.remove(i);
					i--;
					Jukebox.play("explode");
					break;
				}
			}
		}
		
		//flying saucer-asteroid collision
		if(flyingSaucer != null){
			for(int i = 0; i < asteroids.size(); i++){
				Asteroid a = asteroids.get(i);
				if(a.intersects(flyingSaucer)){
					asteroids.remove(i);
					i--;
					splitAsteroid(a);
					createParticles(a.getx(), a.gety());
					createParticles(flyingSaucer.getx(), flyingSaucer.gety());
					flyingSaucer = null;
//					Jukebox.stop("smallsaucer");
//					Jukebox.stop("largesaucer");
//					Jukebox.play("explode");
					break;
				}
			}
		}
		
		//asteroid enemy bullet collision
		for(int i = 0; i < enemyBullets.size(); i++){
			Bullet b = enemyBullets.get(i);
			for(int j = 0; j < asteroids.size(); j++){
				Asteroid a = asteroids.get(j);
				if(a.contains(b.getx(), b.gety())){
					asteroids.remove(j);
					j--;
					splitAsteroid(a);
					enemyBullets.remove(i);
					i--;
					createParticles(a.getx(), a.gety());
					Jukebox.play("explode");
					break;
				}
			}
		}
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

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

}
