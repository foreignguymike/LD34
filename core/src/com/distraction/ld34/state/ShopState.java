package com.distraction.ld34.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld34.util.Res;

public class ShopState extends State {
	
	private TextureRegion pixel;
	
	public ShopState(GSM gsm) {
		super(gsm);
		
		pixel = new TextureRegion(Res.i().getTexture("pixel"));
	}
	
	@Override
	public void update(float dt) {
		
	}
	
	@Override
	public void render(SpriteBatch sb) {
		
	}
	
}
