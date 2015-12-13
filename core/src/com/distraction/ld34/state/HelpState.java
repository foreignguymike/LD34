package com.distraction.ld34.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.distraction.ld34.Vars;
import com.distraction.ld34.farm.Seed;
import com.distraction.ld34.util.Res;

public class HelpState extends State {
	
	private BitmapFont font;
	
	public HelpState(GSM gsm) {
		super(gsm);
		font = Res.i().getFont("font");
	}
	
	@Override
	public void update(float dt) {
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			cam.position.y -= 100 * dt;
			cam.update();
		}
		else if(Gdx.input.isKeyPressed(Keys.UP)) {
			cam.position.y += 100 * dt;
			cam.update();
		}
		else if(Gdx.input.isKeyJustPressed(Keys.ANY_KEY)) {
			gsm.pop();
		}
	}
	
	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		int height = Vars.HEIGHT - 10;
		font.draw(sb, "The goal is to reach $" + Vars.NUM_MONEY_REQUIRED + " in total profits", 10, height -= 15);
		font.draw(sb, "from crops.", 10, height -= 15);
		
		font.draw(sb, "To grow crops, you must (1) till the land,", 10, height -= 25);
		font.draw(sb, "(2) water the patch, (3) seed the patch,", 10, height -= 15);
		font.draw(sb, "then just wait for the crop to grow.", 10, height -= 15);
		font.draw(sb, "Once finished, you can (4) harvest the crop.", 10, height -= 15);
		
		font.draw(sb, "Sell your crops by walking to the truck.", 10, height -= 25);
		
		font.draw(sb, "Buy more seeds from the shop.", 10, height -= 25);
		font.draw(sb, "You start with 10 potato seeds.", 10, height -= 15);
		
		font.draw(sb, "Crop Info:", 10, height -= 25);
		for(Seed.Type type : Seed.types) {
			font.draw(sb, type.toString() + " - " + (float) Math.round((type.requiredTime / 30) * 100.0) / 100.0f + " days, $" + type.value + " value, $" + type.cost + " cost", 10, height -= 15);
		}
		sb.end();
	}
	
}
