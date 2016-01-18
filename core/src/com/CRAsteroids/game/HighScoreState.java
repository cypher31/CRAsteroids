package com.CRAsteroids.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class HighScoreState extends GameState {
	
	private SpriteBatch sb;
	
	private BitmapFont font;
	
	private long[] highScores;
	private String[] names;

	protected HighScoreState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	public void init() {
		
		sb = new SpriteBatch();
		
//		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("fonts/Hyperspace bold.ttf"));
//		FreeTypeFontParameter genPar = new FreeTypeFontParameter();
//		
//		genPar.size = 45;
//		genPar.magFilter = TextureFilter.Linear;
//		genPar.minFilter = TextureFilter.Linear;
//		
//		font = gen.generateFont(genPar);
//		gen.dispose();
		
		font = CRAsteroidsGame.fontLarge;
		
		Save.load();
		highScores = Save.gd.getHighScores();
		names = Save.gd.getNames();
	}

	@Override
	public void update(float dt) {
		handleInput();
	}

	@Override
	public void draw() {
		
		sb.setProjectionMatrix(CRAsteroidsGame.cam.combined);
		
		sb.begin();
		
		String s;
		float w;
		
		s = "High Scores";
		w = font.getBounds(s).width;
		font.draw(sb, s, (CRAsteroidsGame.WIDTH - w) / 2, 950);
		
		for(int i = 0; i < highScores.length; i++){
			s = String.format(
						"%2d. %7s %s",
						i + 1,
						highScores[i],
						names[i]
					);
			w = font.getBounds(s).width;
			font.draw(sb, s, (CRAsteroidsGame.WIDTH - w) / 2, 800 - (font.getBounds(s).height + 20) * i);
		}
		
		sb.end();
	}

	@Override
	public void handleInput() {
		if(GameKeys.isPressed(GameKeys.ENTER) || GameKeys.isPressed(GameKeys.ESCAPE)){
			gsm.setState(GameStateManager.MENU);
		}
	}

	@Override
	public void dispose() {
		sb.dispose();
//		font.dispose();
	}

	@Override
	public void resize(int width, int height) {
		// TODO Auto-generated method stub
		
	}

}
