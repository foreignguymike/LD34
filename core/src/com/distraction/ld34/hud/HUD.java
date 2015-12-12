package com.distraction.ld34.hud;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ld34.entity.Player;

public class HUD {
	
	private Player player;
	
	private BitmapFont font;
	
	public HUD(Player player) {
		this.player = player;
		font = new BitmapFont();
	}
	
	public void render(SpriteBatch sb) {
		font.draw(sb, player.getMoney() + "", 10, 20);
		font.draw(sb, player.getNumSeeds() + "", 10, 30);
	}
	
}
