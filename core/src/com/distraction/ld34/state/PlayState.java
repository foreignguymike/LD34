package com.distraction.ld34.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.distraction.ld34.Vars;
import com.distraction.ld34.entity.Player;
import com.distraction.ld34.entity.Truck;
import com.distraction.ld34.farm.Patch;
import com.distraction.ld34.hud.HUD;
import com.distraction.ld34.tile.TileMap;
import com.distraction.ld34.util.BoundCamera;
import com.distraction.ld34.util.Res;

public class PlayState extends State {
	
	private TileMap tileMap;
	private BoundCamera cam;
	
	private Player player;
	
	private Patch[][] farm;
	
	private HUD hud;
	
	private float globalTime;
	
	private Truck truck;
	
	public PlayState(GSM gsm) {
		super(gsm);
		
		tileMap = new TileMap(32);
		Texture tiles = Res.i().getTexture("tileset");
		TextureRegion[][] tileset = TextureRegion.split(tiles, 32, 32);
		tileMap.loadTileset(tileset);
		tileMap.loadMap("test.tme");
		
		cam = new BoundCamera();
		cam.setToOrtho(false, Vars.WIDTH, Vars.HEIGHT);
		cam.setBounds(0, 0, tileMap.getWidth(), tileMap.getHeight());
		
		player = new Player(tileMap);
		player.setPosition(tileMap.getWidth() / 2, tileMap.getHeight() / 2 - 64);
		
		farm = new Patch[4][10];
		for(int row = 0; row < farm.length; row++) {
			for(int col = 0; col < farm[0].length; col++) {
				farm[row][col] = new Patch(tileMap, row + 3, col + 5);
			}
		}
		
		player.setFarm(farm);
		
		hud = new HUD(player);
		
		truck = new Truck(tileMap);
		truck.setPosition(450, 106);
		
		Res.i().getMusic("ld34").setLooping(true);
		Res.i().getMusic("ld34").setVolume(0.6f);
		Res.i().getMusic("ld34").play();
	}
	
	@Override
	public void update(float dt) {
		
		globalTime += 2880 * dt;
		hud.setGlobalTime(globalTime);
		
		if(player.getTotalMoney() >= Vars.NUM_MONEY_REQUIRED) {
			gsm.set(new WinState(gsm));
		}
		if(globalTime >= Vars.NUM_SECONDS) {
			gsm.set(new GameOverState(gsm));
		}
		
		player.setLeft(Gdx.input.isKeyPressed(Keys.LEFT));
		player.setRight(Gdx.input.isKeyPressed(Keys.RIGHT));
		player.setUp(Gdx.input.isKeyPressed(Keys.UP));
		player.setDown(Gdx.input.isKeyPressed(Keys.DOWN));
		player.update(dt);
		
		if(Gdx.input.isKeyJustPressed(Keys.NUM_1)) {
			player.till();
		}
		if(Gdx.input.isKeyJustPressed(Keys.NUM_2)) {
			player.water();
		}
		if(Gdx.input.isKeyJustPressed(Keys.NUM_3)) {
			player.seed();
		}
		if(Gdx.input.isKeyJustPressed(Keys.NUM_4)) {
			player.harvest();
		}
		if(player.intersects(truck)) {
			if(player.unload()) {
				Res.i().getSound("money").play(0.5f);
				truck.setShowing();
			}
		}
		
		if(Gdx.input.isKeyJustPressed(Keys.H)) {
			gsm.push(new HelpState(gsm));
			return;
		}
		if(Gdx.input.isKeyJustPressed(Keys.S) && player.getAction() == null) {
			gsm.push(new ShopState(gsm, player));
			return;
		}
		
		cam.position.set(player.getx(), player.gety(), 0);
		cam.update();
		
		for(int row = 0; row < farm.length; row++) {
			for(int col = 0; col < farm[0].length; col++) {
				farm[row][col].update(dt);
			}
		}
		
		truck.update(dt);
		
	}
	
	@Override
	public void render(SpriteBatch sb) {
		sb.setProjectionMatrix(cam.combined);
		sb.begin();
		tileMap.render(sb, cam);
		for(int row = 0; row < farm.length; row++) {
			for(int col = 0; col < farm[0].length; col++) {
				farm[row][col].render(sb);
			}
		}
		player.render(sb);
		truck.render(sb);
		sb.setProjectionMatrix(super.cam.combined);
		hud.render(sb);
		sb.end();
	}
	
}
