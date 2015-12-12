package com.distraction.ld34.state;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class PlayState extends State {
	
	public PlayState(GSM gsm) {
		super(gsm);
	}
	
	@Override
	public void update(float dt) {
		System.out.println("test");
	}
	
	@Override
	public void render(SpriteBatch sb) {
		
	}
	
}
