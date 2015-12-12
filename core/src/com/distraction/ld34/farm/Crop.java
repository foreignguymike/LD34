package com.distraction.ld34.farm;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Crop {
	
	private TextureRegion image;
	
	private float x;
	private float y;
	private float w;
	private float h;
	
	public Crop(Seed seed) {
		image = seed.getType().cropImage;
		x = seed.getx();
		y = seed.gety();
		w = seed.getw();
		h = seed.geth();
	}
	
	public void render(SpriteBatch sb) {
		sb.draw(image, x - w / 2, y - h / 2);
	}
	
}
