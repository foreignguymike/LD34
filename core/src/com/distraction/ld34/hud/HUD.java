package com.distraction.ld34.hud;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld34.Vars;
import com.distraction.ld34.entity.Player;
import com.distraction.ld34.util.Res;

public class HUD {
	
	private Player player;
	
	private BitmapFont font;
	private float globalTime;
	
	private TextureRegion pixel;
	private int height = 70;
	private Color bgColor = new Color(0.6f, 0.5f, 0.2f, 1);
	private Color borderColor = new Color(0.3f, 0.2f, 0.1f, 1);
	
	private StringBuilder builder;
	
	public HUD(Player player) {
		this.player = player;
		font = Res.i().getFont("font");
		pixel = new TextureRegion(Res.i().getTexture("pixel"));
		builder = new StringBuilder();
	}
	
	public void setGlobalTime(float globalTime) {
		this.globalTime = globalTime;
	}
	
	public void render(SpriteBatch sb) {
		sb.setColor(bgColor);
		sb.draw(pixel, 0, 0, Vars.WIDTH, height);
		sb.setColor(borderColor);
		sb.draw(pixel, 0, height, Vars.WIDTH, 1);
		font.draw(sb, "1. Till (Lv. " + player.getLevel(Player.Action.TILLING) + ")", 10, 65);
		font.draw(sb, "2. Water (Lv. " + player.getLevel(Player.Action.WATERING) + ")", 10, 50);
		font.draw(sb, "3. Seed (Lv. " + player.getLevel(Player.Action.SEEDING) + ")", 10, 35);
		font.draw(sb, "4. Harvest (Lv. " + player.getLevel(Player.Action.HARVESTING) + ")", 10, 20);
		Player.Action action = player.getAction();
		if(action != null) {
			font.draw(sb, action.toString(), 10, Vars.HEIGHT - 25);
		}
		int seconds = (int) globalTime;
		int day = (int) TimeUnit.SECONDS.toDays(seconds);
		long hours = TimeUnit.SECONDS.toHours(seconds) - (day *24);
		long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
		long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
		builder.setLength(0);
		builder.append(day).append(" days, ");
		if(hours < 10) {
			builder.append("0");
		}
		builder.append(hours).append(":");
		if(minute < 10) {
			builder.append("0");
		}
		builder.append(minute).append(":");
		if(second < 10) {
			builder.append("0");
		}
		builder.append(second);
		font.draw(sb, builder.toString(), 10, Vars.HEIGHT - 10);
		font.draw(sb, "Money: $" + player.getMoney(), 200, 65);
		font.draw(sb, "# of Seeds: " + player.getNumSeeds(), 200, 50);
		font.draw(sb, "# of Crops: " + player.getNumCrops(), 200, 35);
		font.draw(sb, "Total profit: $" + player.getTotalMoney(), 200, 20);
		font.draw(sb, Vars.NUM_DAYS + " day goal: $" + Vars.NUM_MONEY_REQUIRED + " total profit", 10, 85);
		font.draw(sb, "H for help", Vars.WIDTH - 100, Vars.HEIGHT - 10);
		font.draw(sb, "S for shop", Vars.WIDTH - 100, Vars.HEIGHT - 25);
	}
	
}
