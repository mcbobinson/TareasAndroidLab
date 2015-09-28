package com.mygdx.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.game.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.height = 1920; //Altura de 1920
		config.width = 800; //Anchura de 1080
		config.vSyncEnabled = true; //vSync se prende para que los fps del juego se coordinen con los del monitor.
		new LwjglApplication(new MainGame(), config);
	}
}
