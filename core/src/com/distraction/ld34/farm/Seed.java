package com.distraction.ld34.farm;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld34.util.Res;

public class Seed {
	
	public enum Type {
		POTATO(new TextureRegion(Res.i().getTexture("farmtiles"), 96, 0, 32, 32),
				new TextureRegion(Res.i().getTexture("farmtiles"), 0, 32, 32, 32),
				3);
		
		public TextureRegion image;
		public TextureRegion cropImage;
		private float requiredTime;
		
		private Type(TextureRegion image, TextureRegion cropImage, float requiredTime) {
			this.image = image;
			this.cropImage = cropImage;
			this.requiredTime = requiredTime;
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
	
	public Seed(Type type, float x, float y, float w, float h) {
		this.type = type;
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
		image = type.image;
		requiredTime = type.requiredTime;
		crop = new Crop(this);
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
	}
	
}
