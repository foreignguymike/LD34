package com.distraction.ld34.entity;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld34.Vars;
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
	private int money = 0;
	private int totalMoney;
	
	private Action action;
	private int actionRow;
	private int actionCol;
	private float actionTime;
	private float actionTimeRequired;
	private float[] actionSpeedMultipliers = {1, 1, 1, 1};
	
	private Seed.Type nextSeedType;
	
	private TextureRegion pixel;
	
	private Patch selectedPatch;
	
	private int row;
	private int col;
	
	private TextureRegion[] idle;
	private TextureRegion[] moving;
	private TextureRegion[] tilling;
	private TextureRegion[] watering;
	private TextureRegion[] seeding;
	
	public enum Action {
		TILLING(3),
		WATERING(3),
		SEEDING(2),
		HARVESTING(1);
		public float timeRequired;
		Action(float timeRequired) {
			this.timeRequired = timeRequired;
		}
	};
	
	public Player(TileMap tileMap) {
		super(tileMap);
		Texture tex = Res.i().getTexture("player");
		TextureRegion[][] split = TextureRegion.split(tex, 32, 32);
		idle = split[0];
		moving = split[1];
		tilling = split[2];
		watering = split[3];
		seeding = split[4];
		setAnimation(idle, 0, 1);
		cwidth = 20;
		cheight = 20;
		
		moveSpeed = 100;
		crops = new ArrayList<Crop>();
		seeds = new ArrayList<Seed.Type>();
		
		pixel = new TextureRegion(Res.i().getTexture("pixel"));
		for(int i = 0; i < Vars.NUM_SEEDS_START; i++) {
			seeds.add(Seed.Type.POTATO);
		}
		
	}
	
	public boolean buySeed(Seed.Type type) {
		if(money >= type.cost) {
			money -= type.cost;
			seeds.add(type);
			return true;
		}
		return false;
	}
	
	public void addMoney(int amount) {
		money += amount;
		totalMoney += amount;
	}
	
	public int getMoney() {
		return money;
	}
	
	public int getTotalMoney() {
		return totalMoney;
	}
	
	public int getNumSeeds() {
		return seeds.size();
	}
	
	public void setFarm(Patch[][] farm) {
		this.farm = farm;
	}
	
	public int getActionCost(Action action) {
		float speed = actionSpeedMultipliers[action.ordinal()];
		return speed == 1 ? 50 : speed == 0.7f ? 100 : -1;
	}
	
	public void actionStarted(Action action, int actionRow, int actionCol) {
		this.action = action;
		this.actionRow = actionRow;
		this.actionCol = actionCol;
		actionTime = 0;
		actionTimeRequired = action.timeRequired * actionSpeedMultipliers[action.ordinal()];
	}
	
	public boolean upgradeAction(Action action) {
		float speed = actionSpeedMultipliers[action.ordinal()];
		int requiredMoney = getActionCost(action);
		if(speed == 1) {
			speed = 0.7f;
		}
		else if(speed == 0.7f) {
			speed = 0.4f;
		}
		else {
			return false;
		}
		if(money >= requiredMoney) {
			actionSpeedMultipliers[action.ordinal()] = speed;
			money -= requiredMoney;
			return true;
		}
		return false;
	}
	
	public void actionFinished() {
		switch(action) {
		case TILLING:
			farm[actionRow][actionCol].till();
			break;
		case WATERING:
			farm[actionRow][actionCol].water();
			break;
		case SEEDING:
			farm[actionRow][actionCol].seed(new Seed(nextSeedType,
					tileSize * ((int) (x / tileSize) + 0.5f),
					tileSize * ((int) (y / tileSize) + 0.5f),
					32, 32));
			seeds.remove(0);
			break;
		case HARVESTING:
			Crop crop = farm[actionRow][actionCol].harvest();
			if(crop != null) {
				crops.add(crop);
			}
			break;
		}
		Res.i().getSound("plink").play(0.4f);
		action = null;
	}
	
	private void getCurrentTile() {
		row = (int) (tileMap.getNumRows() - (y / tileSize));
		col = (int) (x / tileSize);
		row -= 3;
		col -= 5;
	}
	
	public void till() {
		getCurrentTile();
		if(row < 0 || row >= farm.length || col < 0 || col >= farm[0].length) {
			return;
		}
		if(action == null && farm[row][col].canTill()) {
			actionStarted(Action.TILLING, row, col);
		}
	}
	
	public void water() {
		getCurrentTile();
		if(row < 0 || row >= farm.length || col < 0 || col >= farm[0].length) {
			return;
		}
		if(action == null && farm[row][col].canWater()) {
			actionStarted(Action.WATERING, row, col);
		}
	}
	
	public void seed() {
		getCurrentTile();
		if(row < 0 || row >= farm.length || col < 0 || col >= farm[0].length) {
			return;
		}
		nextSeedType = (seeds.isEmpty() || farm[row][col].hasSeed() || farm[row][col].getState() == Patch.State.NORMAL) ? null : seeds.get(0);
		if(action == null && farm[row][col].canSeed() && nextSeedType != null) {
			actionStarted(Action.SEEDING, row, col);
		}
	}
	
	public void harvest() {
		getCurrentTile();
		if(row < 0 || row >= farm.length || col < 0 || col >= farm[0].length) {
			return;
		}
		if(action == null && farm[row][col].canHarvest()) {
			actionStarted(Action.HARVESTING, row, col);
		}
	}
	
	public boolean unload() {
		if(crops.isEmpty()) {
			return false;
		}
		for(Crop crop : crops) {
			addMoney(crop.getValue());
		}
		crops.clear();
		return true;
	}
	
	public int getNumCrops() {
		return crops.size();
	}
	
	public Action getAction() {
		return action;
	}
	
	public int getLevel(Action action) {
		float speed = actionSpeedMultipliers[action.ordinal()];
		return speed == 1 ? 1 : speed == 0.7f ? 2 : 3; 
	}
	
	private void highlightPatch() {
		int row = (int) (tileMap.getNumRows() - (y / tileSize));
		int col = (int) (x / tileSize);
		row -= 3;
		col -= 5;
		if(row < 0 || row >= farm.length || col < 0 || col >= farm[0].length) {
			selectedPatch = null;
			return;
		}
		selectedPatch = farm[row][col];
	}
	
	@Override
	public void update(float dt) {
		if(action != null) {
			actionTime += dt;
			if(actionTime >= actionTimeRequired) {
				actionFinished();
			}
		}
		else {
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
			
			highlightPatch();
		}
		
		if(action == Action.TILLING) {
			if(animation.getAnimation() != tilling) {
				setAnimation(tilling, 0.3f, 2);
			}
		}
		else if(action == Action.WATERING) {
			if(animation.getAnimation() != watering) {
				setAnimation(watering, 0.1f, 2);
			}
		}
		else if(action == Action.SEEDING) {
			if(animation.getAnimation() != seeding) {
				setAnimation(seeding, 0.1f, 2);
			}
		}
		else if(action == Action.HARVESTING) {
			if(animation.getAnimation() != idle) {
				setAnimation(idle, 0, 1);
			}
		}
		else if(left || right || up || down) {
			if(animation.getAnimation() != moving) {
				setAnimation(moving, 0.1f, 4);
			}
		}
		else {
			if(animation.getAnimation() != idle) {
				setAnimation(idle, 0, 1);
			}
		}
		animation.update(dt);
	}
	
	@Override
	public void render(SpriteBatch sb) {
		super.render(sb);
		if(selectedPatch != null) {
			selectedPatch.renderHighlight(sb);
		}
		if(action != null) {
			Color c = sb.getColor();
			sb.setColor(Color.GREEN);
			sb.draw(pixel, x - width / 2, y + height / 2, width * actionTime / actionTimeRequired, 3);
			sb.setColor(Color.BLACK);
			sb.draw(pixel, x - width / 2, y + height / 2, width, 1);
			sb.draw(pixel, x - width / 2, y + height / 2 + 3, width, 1);
			sb.draw(pixel, x - width / 2, y + height / 2, 1, 4);
			sb.draw(pixel, x + width / 2, y + height / 2, 1, 4);
			sb.setColor(c);
		}
	}
	
}
