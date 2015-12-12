package com.distraction.ld34.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld34.farm.Patch;
import com.distraction.ld34.farm.Seed;
import com.distraction.ld34.tile.MapObject;
import com.distraction.ld34.tile.TileMap;
import com.distraction.ld34.util.Res;

public class Player extends MapObject {
	
	private Patch[][] farm;
	
	public Player(TileMap tileMap) {
		super(tileMap);
		Texture tex = Res.i().getTexture("player");
		TextureRegion[] reg = new TextureRegion[1];
		reg[0] = new TextureRegion(tex, 0, 0, 32, 32);
		setAnimation(reg, 0);
		cwidth = 20;
		cheight = 20;
		
		moveSpeed = 100;
	}
	
	public void setFarm(Patch[][] farm) {
		this.farm = farm;
	}
	
	public void till() {
		int row = (int) (tileMap.getNumRows() - (y / tileSize));
		int col = (int) (x / tileSize);
		row -= 3;
		col -= 5;
		if(row < 0 || row >= farm.length || col < 0 || col >= farm[0].length) {
			return;
		}
		farm[row][col].till();
	}
	
	public void water() {
		int row = (int) (tileMap.getNumRows() - (y / tileSize));
		int col = (int) (x / tileSize);
		row -= 3;
		col -= 5;
		if(row < 0 || row >= farm.length || col < 0 || col >= farm[0].length) {
			return;
		}
		farm[row][col].water();
	}
	
	public void seed() {
		int row = (int) (tileMap.getNumRows() - (y / tileSize));
		int col = (int) (x / tileSize);
		row -= 3;
		col -= 5;
		if(row < 0 || row >= farm.length || col < 0 || col >= farm[0].length) {
			return;
		}
		farm[row][col].seed(new Seed(Seed.Type.POTATO,
				tileSize * ((int) (x / tileSize) + 0.5f),
				tileSize * ((int) (y / tileSize) + 0.5f),
				32, 32));
	}
	
	@Override
	public void update(float dt) {
		
		if(left) {
			dx = -moveSpeed * dt;
		}
		else if(right) {
			dx = moveSpeed * dt;
		}
		else {
			dx = 0;
		}
		if(down) {
			dy = -moveSpeed * dt;
		}
		else if(up) {
			dy = moveSpeed * dt;
		}
		else {
			dy = 0;
		}
		
		checkTileMapCollision();
		x = xtemp;
		y = ytemp;
		
	}
	
}
