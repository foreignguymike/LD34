package com.distraction.ld34.farm;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld34.util.Res;

public class Seed {
	
	public static Type[] types = Type.values();
	public enum Type {
		POTATO(new TextureRegion(Res.i().getTexture("farmtiles"), 96, 0, 32, 32),
				new TextureRegion(Res.i().getTexture("farmtiles"), 0, 32, 32, 32),
				20,
				14,
				10),
		CORN(new TextureRegion(Res.i().getTexture("farmtiles"), 96, 0, 32, 32),
				new TextureRegion(Res.i().getTexture("farmtiles"), 32, 32, 32, 32),
				25,
				18,
				12),
		TOMATO(new TextureRegion(Res.i().getTexture("farmtiles"), 96, 0, 32, 32),
				new TextureRegion(Res.i().getTexture("farmtiles"), 64, 32, 32, 32),
				20,
				24,
				15),
		WHEAT(new TextureRegion(Res.i().getTexture("farmtiles"), 96, 0, 32, 32),
				new TextureRegion(Res.i().getTexture("farmtiles"), 96, 32, 32, 32),
				22,
				35,
				20),
		CABBAGE(new TextureRegion(Res.i().getTexture("farmtiles"), 96, 0, 32, 32),
				new TextureRegion(Res.i().getTexture("farmtiles"), 0, 64, 32, 32),
				23,
				45,
				25),
		ORANGE(new TextureRegion(Res.i().getTexture("farmtiles"), 96, 0, 32, 32),
				new TextureRegion(Res.i().getTexture("farmtiles"), 32, 64, 32, 32),
				15,
				75,
				40),
		GRAPES(new TextureRegion(Res.i().getTexture("farmtiles"), 96, 0, 32, 32),
				new TextureRegion(Res.i().getTexture("farmtiles"), 64, 64, 32, 32),
				25,
				120,
				60),
		CHERRIES(new TextureRegion(Res.i().getTexture("farmtiles"), 96, 0, 32, 32),
				new TextureRegion(Res.i().getTexture("farmtiles"), 96, 64, 32, 32),
				24,
				500,
				300),
		;
		
		public TextureRegion image;
		public TextureRegion cropImage;
		public float requiredTime;
		public int value;
		public int cost;
		
		private Type(TextureRegion image, TextureRegion cropImage, float requiredTime, int value, int cost) {
			this.image = image;
			this.cropImage = cropImage;
			this.requiredTime = requiredTime;
			this.value = value;
			this.cost = cost;
		}
	};
	
	private Type type;
	private Crop crop;
	private float x;
	private float y;
	private float w;
	private float h;
	
	private boolean watered;
	private float time;
	private float requiredTime;
	private boolean finished;
	
	private TextureRegion image;
	private TextureRegion pixel;
	
	public Seed(Type type, float x, float y, float w, float h) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		image = type.image;
		requiredTime = type.requiredTime;
		crop = new Crop(this);
		
		pixel = new TextureRegion(Res.i().getTexture("pixel"));
	}
	
	public float getx() {
		return x;
	}
	
	public float gety() {
		return y;
	}
	
	public float getw() {
		return w;
	}
	
	public float geth() {
		return h;
	}
	
	public Type getType() {
		return type;
	}
	
	public Crop getCrop() {
		return crop;
	}
	
	public boolean isFinished() {
		return finished;
	}
	
	public void setWatered() {
		watered = true;
	}
	
	public void update(float dt) {
		if(watered) {
			time += dt;
			if(time >= requiredTime) {
				finished = true;
			}
		}
	}
	
	public void render(SpriteBatch sb) {
		sb.draw(image, x - w / 2, y - h / 2);
		if(watered) {
			Color c = sb.getColor();
			sb.setColor(Color.GREEN);
			sb.draw(pixel, x - w / 2, y + h / 2, w * time / requiredTime, 3);
			sb.setColor(Color.BLACK);
			sb.draw(pixel, x - w / 2, y + h / 2, w, 1);
			sb.draw(pixel, x - w / 2, y + h / 2 + 3, w, 1);
			sb.draw(pixel, x - w / 2, y + h / 2, 1, 4);
			sb.draw(pixel, x + w / 2, y + h / 2, 1, 4);
			sb.setColor(c);
		}
	}
	
}
