package com.distraction.ld34.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld34.farm.Crop;
import com.distraction.ld34.farm.Patch;
import com.distraction.ld34.farm.Seed;
import com.distraction.ld34.tile.MapObject;
import com.distraction.ld34.tile.TileMap;
import com.distraction.ld34.util.Res;

public class Player extends MapObject {
	
	private Patch[][] farm;
	
	private List<Crop> crops;
	private List<Seed.Type> seeds;
	private int money = 50;
	
	public Player(TileMap tileMap) {
		super(tileMap);
		Texture tex = Res.i().getTexture("player");
		TextureRegion[] reg = new TextureRegion[1];
		reg[0] = new TextureRegion(tex, 0, 0, 32, 32);
		setAnimation(reg, 0);
		cwidth = 20;
		cheight = 20;
		
		moveSpeed = 100;
		crops = new ArrayList<Crop>();
		seeds = new ArrayList<Seed.Type>();
	}
	
	public void buySeed(Seed.Type type) {
		if(money >= type.cost) {
			money -= type.cost;
			seeds.add(type);
		}
	}
	
	public void addMoney(int amount) {
		money += amount;
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
		Seed.Type nextSeedType = (seeds.isEmpty() || farm[row][col].hasSeed() || farm[row][col].getState() == Patch.State.NORMAL) ? null : seeds.remove(0);
		if(nextSeedType != null) {
			farm[row][col].seed(new Seed(nextSeedType,
					tileSize * ((int) (x / tileSize) + 0.5f),
					tileSize * ((int) (y / tileSize) + 0.5f),
					32, 32));
		}
	}
	
	public void harvest() {
		int row = (int) (tileMap.getNumRows() - (y / tileSize));
		int col = (int) (x / tileSize);
		row -= 3;
		col -= 5;
		if(row < 0 || row >= farm.length || col < 0 || col >= farm[0].length) {
			return;
		}
		Crop crop = farm[row][col].harvest();
		if(crop != null) {
			crops.add(crop);
		}
	}
	
	public void unload() {
		for(Crop crop : crops) {
			addMoney(crop.getValue());
		}
	}
	
	@Override
	public void update(float dt) {
		System.out.println(money);
		
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
