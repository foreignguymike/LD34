package com.distraction.ld34.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.graphics.Texture;

public class Res {
	
	private static Res res = new Res();
	
	public static Res i() {
		return res;
	}
	
	private Map<String, Texture> textures;
	
	public Res() {
		textures = new HashMap<String, Texture>();
	}
	
	public void loadTexture(String key, String path) {
		textures.put(key, new Texture(path));
	}
	
	public Texture getTexture(String key) {
		return textures.get(key);
	}
	
}
