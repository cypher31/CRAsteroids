package com.CRAsteroids.game;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;

public class GameInputProcessor implements InputProcessor {

	@Override
	public boolean keyDown(int k) {
		if(k == Keys.UP){
			GameKeys.setKey(GameKeys.UP, true);
		}
		if(k == Keys.LEFT){
			GameKeys.setKey(GameKeys.LEFT, true);
		}
		if(k == Keys.DOWN){
			GameKeys.setKey(GameKeys.DOWN, true);
		}
		if(k == Keys.RIGHT){
			GameKeys.setKey(GameKeys.RIGHT, true);
		}
		if(k == Keys.ENTER){
			GameKeys.setKey(GameKeys.ENTER, true);
		}
		if(k == Keys.ESCAPE){
			GameKeys.setKey(GameKeys.ESCAPE, true);
		}
		if(k == Keys.SPACE){
			GameKeys.setKey(GameKeys.SPACE, true);
		}
		if(k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT){
			GameKeys.setKey(GameKeys.SHIFT, true);
		}
		return true;
	}

	@Override
	public boolean keyUp(int k) {
		if(k == Keys.UP){
			GameKeys.setKey(GameKeys.UP, false);
		}
		if(k == Keys.LEFT){
			GameKeys.setKey(GameKeys.LEFT, false);
		}
		if(k == Keys.DOWN){
			GameKeys.setKey(GameKeys.DOWN, false);
		}
		if(k == Keys.RIGHT){
			GameKeys.setKey(GameKeys.RIGHT, false);
		}
		if(k == Keys.ENTER){
			GameKeys.setKey(GameKeys.ENTER, false);
		}
		if(k == Keys.ESCAPE){
			GameKeys.setKey(GameKeys.ESCAPE, false);
		}
		if(k == Keys.SPACE){
			GameKeys.setKey(GameKeys.SPACE, false);
		}
		if(k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT){
			GameKeys.setKey(GameKeys.SHIFT, false);
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}
