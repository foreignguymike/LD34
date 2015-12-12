package com.distraction.ld34.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.distraction.ld34.LD34;
import com.distraction.ld34.Vars;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.resizable = false;
		config.width = Vars.WIDTH * Vars.SCALE;
		config.height = Vars.HEIGHT * Vars.SCALE;
		config.title = Vars.TITLE;
		new LwjglApplication(new LD34(), config);
	}
}
