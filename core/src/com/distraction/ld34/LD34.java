package com.distraction.ld34;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ld34.state.GSM;
import com.distraction.ld34.state.PlayState;
import com.distraction.ld34.util.Res;

public class LD34 extends ApplicationAdapter {
	
	private SpriteBatch sb;
	private GSM gsm;
	
	private Res res;
	
	@Override
	public void create () {
		
		Gdx.gl.glClearColor(0, 0, 0, 1);
		
		res = Res.i();
		res.loadTexture("tileset", "tileset.png");
		res.loadTexture("player", "player.png");
		res.loadTexture("farmtiles", "farmtiles.png");
		res.loadTexture("pixel", "pixel.png");
		res.loadTexture("truck", "truck.png");
		res.loadFont("font", 8);
		res.loadFont("fontsmall", 6);
		res.loadFont("fontlarge", 16);
		res.loadMusic("ld34", "ld34.mp3");
		res.loadSound("plink", "plink.wav");
		res.loadSound("crop", "crop.wav");
		res.loadSound("select", "select.wav");
		res.loadSound("select_move", "select_move.wav");
		res.loadSound("select_fail", "select_fail.wav");
		res.loadSound("money", "money.wav");
		
		sb = new SpriteBatch();
		gsm = new GSM();
		gsm.push(new PlayState(gsm));
		
	}

	@Override
	public void render () {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(sb);
	}
}
