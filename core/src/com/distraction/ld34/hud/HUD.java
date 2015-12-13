package com.distraction.ld34.hud;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld34.entity.Player;

public class HUD {
	
	private Player player;
	
	private BitmapFont font;
	private float globalTime;
	
	private TextureRegion pixel;
	
	public HUD(Player player) {
		this.player = player;
		font = new BitmapFont();
	}
	
	public void setGlobalTime(float globalTime) {
		this.globalTime = globalTime;
	}
	
	public void render(SpriteBatch sb) {
		font.draw(sb, player.getMoney() + "", 10, 20);
		font.draw(sb, player.getNumSeeds() + "", 10, 35);
		font.draw(sb, globalTime + "", 10, 50);
		font.draw(sb, player.getNumCrops() + "", 10, 65);
	}
	
}
