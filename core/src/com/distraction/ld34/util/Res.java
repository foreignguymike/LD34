package com.distraction.ld34.util;

import java.util.HashMap;
import java.util.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;

public class Res {
	
	private static Res res = new Res();
	
	public static Res i() {
		return res;
	}
	
	private Map<String, Texture> textures;
	private Map<String, BitmapFont> fonts;
	private Map<String, Music> music;
	private Map<String, Sound> sounds;
	
	public Res() {
		textures = new HashMap<String, Texture>();
		fonts = new HashMap<String, BitmapFont>();
		music = new HashMap<String, Music>();
		sounds = new HashMap<String, Sound>();
	}
	
	public void loadTexture(String key, String path) {
		textures.put(key, new Texture(path));
	}
	
	public Texture getTexture(String key) {
		return textures.get(key);
	}
	
	public void loadFont(String key, int size) {
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal("prstart.ttf"));
		FreeTypeFontParameter param = new FreeTypeFontParameter();
		param.size = size;
		param.borderColor = new Color(0.2f, 0.2f, 0.2f, 1);
		param.borderStraight = true;
		param.borderWidth = 1;
		fonts.put(key, gen.generateFont(param));
		gen.dispose();
	}
	
	public BitmapFont getFont(String key) {
		return fonts.get(key);
	}
	
	public void loadMusic(String key, String path) {
		music.put(key, Gdx.audio.newMusic(Gdx.files.internal(path)));
	}
	
	public Music getMusic(String key) {
		return music.get(key);
	}
	
	public void loadSound(String key, String path) {
		sounds.put(key, Gdx.audio.newSound(Gdx.files.internal(path)));
	}
	
	public Sound getSound(String key) {
		return sounds.get(key);
	}
	
}
