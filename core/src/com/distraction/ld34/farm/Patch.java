package com.distraction.ld34.farm;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld34.tile.TileMap;
import com.distraction.ld34.util.Res;

public class Patch {
	
	/**
	 * starts at NORMAL.
	 * till to TILLED.
	 * water to WATERED.
	 * 
	 * can only water at TILLED
	 * can add seeds when TILLED or WATERED
	 * when patch has seed and is WATERED, eventually turns to crop
	 * harvest crop, turns back to NORMAL.
	 *
	 */
	
	public enum State {
		NORMAL(new TextureRegion(Res.i().getTexture("farmtiles"), 0, 0, 32, 32)),
		TILLED(new TextureRegion(Res.i().getTexture("farmtiles"), 32, 0, 32, 32)),
		WATERED(new TextureRegion(Res.i().getTexture("farmtiles"), 64, 0, 32, 32));
		
		public TextureRegion image;
		private State(TextureRegion image) {
			this.image = image;
		}
	};
	
	private State state;
	
	private float x;
	private float y;
	private float w;
	private float h;
	
	private Seed seed;
	private Crop crop;
	
	private TextureRegion image;
	
	public Patch(TileMap tileMap, int row, int col) {
		state = State.NORMAL;
		int tileSize = tileMap.getTileSize();
		x = tileSize * (col + 0.5f);
		y = tileSize * (tileMap.getNumRows() - 1 - row + 0.5f);
		w = tileSize;
		h = tileSize;
		image = state.image;
	}
	
	public void till() {
		if(state == State.NORMAL) {
			state = State.TILLED;
			image = state.image;
		}
	}
	
	public void water() {
		if(state == State.TILLED) {
			state = State.WATERED;
			image = state.image;
			if(seed != null) {
				seed.setWatered();
			}
		}
	}
	
	public boolean seed(Seed seed) {
		if(state == State.NORMAL) {
			return false;
		}
		this.seed = seed;
		if(state == State.WATERED) {
			seed.setWatered();
		}
		return true;
	}
	
	private void crop() {
		if(seed != null) {
			crop = seed.getCrop();
			seed = null;
		}
	}
	
	public Crop harvest() {
		Crop ret = null;
		if(crop != null) {
			image = state.image;
			state = State.NORMAL;
			ret = crop;
			crop = null;
		}
		return ret;
	}
	
	public void update(float dt) {
		
		if(seed != null) {
			seed.update(dt);
			if(seed.isFinished()) {
				crop();
			}
		}
		
	}
	
	public void render(SpriteBatch sb) {
		sb.draw(image, x - w / 2, y - h / 2);
		if(seed != null) {
			seed.render(sb);
		}
		if(crop != null) {
			crop.render(sb);
		}
	}
	
}
