package com.distraction.ld34.state;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ld34.Vars;

public abstract class State {
	
	protected GSM gsm;
	protected OrthographicCamera cam;
	
	protected State(GSM gsm) {
		this.gsm = gsm;
		cam = new OrthographicCamera();
		cam.setToOrtho(false, Vars.WIDTH, Vars.HEIGHT);
	}
	
	public abstract void update(float dt);
	public abstract void render(SpriteBatch sb);
	
}
