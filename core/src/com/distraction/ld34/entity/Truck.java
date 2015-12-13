package com.distraction.ld34.entity;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld34.tile.MapObject;
import com.distraction.ld34.tile.TileMap;
import com.distraction.ld34.util.Res;

public class Truck extends MapObject {
	
	public Truck(TileMap tileMap) {
		super(tileMap);
		TextureRegion[] reg = new TextureRegion[1];
		reg[0] = new TextureRegion(Res.i().getTexture("truck"));
		setAnimation(reg, 0);
		cwidth = width;
		cheight = height;
	}
	
	@Override
	public void update(float dt) {
	}
	
}
