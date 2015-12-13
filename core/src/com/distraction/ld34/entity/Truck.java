package com.distraction.ld34.entity;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld34.tile.MapObject;
import com.distraction.ld34.tile.TileMap;
import com.distraction.ld34.util.Res;

public class Truck extends MapObject {
	
	private BitmapFont font;
	
	private boolean showing;
	private float time = 2;
	private float timer;
	
	public Truck(TileMap tileMap) {
		super(tileMap);
		TextureRegion[] reg = new TextureRegion[1];
		reg[0] = new TextureRegion(Res.i().getTexture("truck"));
		setAnimation(reg, 0, 1);
		cwidth = width;
		cheight = height;
		font = Res.i().getFont("fontlarge");
	}
	
	public void setShowing() {
		showing = true;
	}
	
	@Override
	public void update(float dt) {
		if(showing) {
			timer += dt;
			if(timer >= time) {
				timer = 0;
				showing = false;
			}
		}
	}
	
	@Override
	public void render(SpriteBatch sb) {
		super.render(sb);
		
		if(showing) {
			font.draw(sb, "$", x - 20, y + 30 + 30 * (timer / time));
		}
	}
	
}
