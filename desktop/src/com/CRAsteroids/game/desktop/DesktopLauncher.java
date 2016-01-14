package com.CRAsteroids.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.CRAsteroids.game.CRAsteroidsGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "Title";
		//cfg.useGL30 = true;
		cfg.height = 600;
		cfg.width = 600;
		new LwjglApplication(new CRAsteroidsGame(), cfg);
	}
}
