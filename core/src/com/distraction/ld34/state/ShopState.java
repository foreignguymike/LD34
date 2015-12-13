package com.distraction.ld34.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld34.entity.Player;
import com.distraction.ld34.farm.Seed;
import com.distraction.ld34.util.Res;

public class ShopState extends State {
	
	private TextureRegion pixel;
	private TextureRegion[][] crops;
	
	private Player player;
	private int currentRow;
	private int currentCol;
	
	private BitmapFont fontSmall;
	private BitmapFont font;
	
	public ShopState(GSM gsm, Player player) {
		super(gsm);
		
		this.player = player;
		
		pixel = new TextureRegion(Res.i().getTexture("pixel"));
		crops = new TextureRegion[2][4];
		crops[0][0] = Seed.Type.POTATO.cropImage;
		crops[0][1] = Seed.Type.CORN.cropImage;
		crops[0][2] = Seed.Type.TOMATO.cropImage;
		crops[0][3] = Seed.Type.WHEAT.cropImage;
		crops[1][0] = Seed.Type.CABBAGE.cropImage;
		crops[1][1] = Seed.Type.ORANGE.cropImage;
		crops[1][2] = Seed.Type.GRAPES.cropImage;
		crops[1][3] = Seed.Type.CHERRIES.cropImage;
		
		fontSmall = Res.i().getFont("fontsmall");
		font = Res.i().getFont("font");
	}
	
	@Override
	public void update(float dt) {
		if(Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
			if(currentCol < 3) {
				currentCol++;
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.LEFT)) {
			if(currentCol > 0) {
				currentCol--;
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.UP)) {
			if(currentRow > 0) {
				currentRow--;
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.DOWN)) {
			if(currentRow < 2) {
				currentRow++;
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.ESCAPE)) {
			gsm.pop();
		}
		if(Gdx.input.isKeyJustPressed(Keys.ENTER)) {
			if(currentRow == 0 && currentCol == 0) {
				player.buySeed(Seed.Type.POTATO);
			}
			if(currentRow == 0 && currentCol == 1) {
				player.buySeed(Seed.Type.CORN);
			}
			if(currentRow == 0 && currentCol == 2) {
				player.buySeed(Seed.Type.TOMATO);
			}
			if(currentRow == 0 && currentCol == 3) {
				player.buySeed(Seed.Type.WHEAT);
			}
			if(currentRow == 1 && currentCol == 0) {
				player.buySeed(Seed.Type.CABBAGE);
			}
			if(currentRow == 1 && currentCol == 1) {
				player.buySeed(Seed.Type.ORANGE);
			}
			if(currentRow == 1 && currentCol == 2) {
				player.buySeed(Seed.Type.GRAPES);
			}
			if(currentRow == 1 && currentCol == 3) {
				player.buySeed(Seed.Type.CHERRIES);
			}
			if(currentRow == 2 && currentCol == 0) {
				player.upgradeAction(Player.Action.TILLING);
			}
			if(currentRow == 2 && currentCol == 1) {
				player.upgradeAction(Player.Action.WATERING);
			}
			if(currentRow == 2 && currentCol == 2) {
				player.upgradeAction(Player.Action.SEEDING);
			}
			if(currentRow == 2 && currentCol == 3) {
				player.upgradeAction(Player.Action.HARVESTING);
			}
		}

	}
	
	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		sb.setColor(Color.WHITE);
		for(int row = 0; row < crops.length; row++) {
			for(int col = 0; col < crops[0].length; col++) {
				sb.draw(crops[row][col], 100 * col + 34, 100 * (2 - row) + 34);
			}
		}
		
		fontSmall.draw(sb, "Upgrade", 26, 60);
		fontSmall.draw(sb, "Tilling", 26, 50);
		fontSmall.draw(sb, "Upgrade", 126, 60);
		fontSmall.draw(sb, "Watering", 123, 50);
		fontSmall.draw(sb, "Upgrade", 226, 60);
		fontSmall.draw(sb, "Seeding", 226, 50);
		fontSmall.draw(sb, "Upgrade", 326, 60);
		fontSmall.draw(sb, "Harvest", 326, 50);
		sb.draw(pixel, 100 * currentCol + 20, 100 * (3 - currentRow) - 20, 60, 1);
		sb.draw(pixel, 100 * currentCol + 20, 100 * (2 - currentRow) + 20, 60, 1);
		sb.draw(pixel, 100 * currentCol + 20, 100 * (2 - currentRow) + 20, 1, 60);
		sb.draw(pixel, 100 * (currentCol + 1) - 20, 100 * (2 - currentRow) + 20, 1, 60);
		
		font.draw(sb, "Enter to buy. Escape to return to game.", 30, 20);
		sb.end();
	}
	
}
