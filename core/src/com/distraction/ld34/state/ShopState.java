package com.distraction.ld34.state;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.distraction.ld34.Vars;
import com.distraction.ld34.entity.Player;
import com.distraction.ld34.entity.Player.Action;
import com.distraction.ld34.farm.Seed;
import com.distraction.ld34.util.Res;

public class ShopState extends State {

    private TextureRegion pixel;
    private Seed.Type[][] crops;

    private Player player;
    private int currentRow;
    private int currentCol;

    private BitmapFont fontSmall;
    private BitmapFont font;

    private boolean showingPopup;
    private String popupText;

    private ShapeRenderer shapeRenderer;

    public ShopState (GSM gsm, Player player) {
        super(gsm);

        this.player = player;

        pixel = new TextureRegion(Res.i().getTexture("pixel"));
        crops = new Seed.Type[2][4];
        crops[0][0] = Seed.Type.POTATO;
        crops[0][1] = Seed.Type.CORN;
        crops[0][2] = Seed.Type.TOMATO;
        crops[0][3] = Seed.Type.WHEAT;
        crops[1][0] = Seed.Type.CABBAGE;
        crops[1][1] = Seed.Type.ORANGE;
        crops[1][2] = Seed.Type.GRAPES;
        crops[1][3] = Seed.Type.CHERRIES;

        shapeRenderer = new ShapeRenderer();

        fontSmall = Res.i().getFont("fontsmall");
        font = Res.i().getFont("font");
    }

    @Override
    public void update (float dt) {
        if (Gdx.input.isKeyJustPressed(Keys.ESCAPE) ||
                Gdx.input.isKeyJustPressed(Keys.TAB) ||
                Gdx.input.isKeyJustPressed(Keys.BACKSPACE)) {
            if (!showingPopup)
                gsm.pop();
            else showingPopup = false;
        }
        if (showingPopup) {
            if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
                showingPopup = false;
            }
        } else {
            if (Gdx.input.isKeyJustPressed(Keys.RIGHT)) {
                if (currentCol < 3) {
                    Res.i().getSound("select_move").play(0.7f);
                    currentCol++;
                }
            }
            if (Gdx.input.isKeyJustPressed(Keys.LEFT)) {
                if (currentCol > 0) {
                    Res.i().getSound("select_move").play(0.7f);
                    currentCol--;
                }
            }
            if (Gdx.input.isKeyJustPressed(Keys.UP)) {
                if (currentRow > 0) {
                    Res.i().getSound("select_move").play(0.7f);
                    currentRow--;
                }
            }
            if (Gdx.input.isKeyJustPressed(Keys.DOWN)) {
                if (currentRow < 2) {
                    Res.i().getSound("select_move").play(0.7f);
                    currentRow++;
                }
            }
            if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
                boolean ok = false;
                if (currentRow == 0 && currentCol == 0) {
                    ok = player.buySeed(Seed.Type.POTATO);
                } else if (currentRow == 0 && currentCol == 1) {
                    ok = player.buySeed(Seed.Type.CORN);
                } else if (currentRow == 0 && currentCol == 2) {
                    ok = player.buySeed(Seed.Type.TOMATO);
                } else if (currentRow == 0 && currentCol == 3) {
                    ok = player.buySeed(Seed.Type.WHEAT);
                } else if (currentRow == 1 && currentCol == 0) {
                    ok = player.buySeed(Seed.Type.CABBAGE);
                } else if (currentRow == 1 && currentCol == 1) {
                    ok = player.buySeed(Seed.Type.ORANGE);
                } else if (currentRow == 1 && currentCol == 2) {
                    ok = player.buySeed(Seed.Type.GRAPES);
                } else if (currentRow == 1 && currentCol == 3) {
                    ok = player.buySeed(Seed.Type.CHERRIES);
                } else if (currentRow == 2 && currentCol == 0) {
                    ok = player.upgradeAction(Player.Action.TILLING);
                } else if (currentRow == 2 && currentCol == 1) {
                    ok = player.upgradeAction(Player.Action.WATERING);
                } else if (currentRow == 2 && currentCol == 2) {
                    ok = player.upgradeAction(Player.Action.SEEDING);
                } else if (currentRow == 2 && currentCol == 3) {
                    ok = player.upgradeAction(Player.Action.HARVESTING);
                }
                if (ok) {
                    Res.i().getSound("select").play(0.5f);
                    popupText = "Bought item!";
                    showingPopup = true;
                } else {
                    Res.i().getSound("select_fail").play(0.8f);
                    popupText = "Not enough money!";
                    showingPopup = true;
                }
            }
        }
    }

    @Override
    public void render (SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.setColor(Color.WHITE);
        for (int row = 0; row < crops.length; row++) {
            for (int col = 0; col < crops[0].length; col++) {
                sb.draw(crops[row][col].cropImage, 100 * col + 34, 100 * (2 - row) + 40);
                font.draw(sb, "$" + crops[row][col].cost, 100 * col + (crops[row][col].cost < 100 ? 37 : 30), 100 * (2 - row) + 40);
            }
        }
        fontSmall.draw(sb, "Upgrade", 26, 70);
        fontSmall.draw(sb, "Tilling", 26, 60);
        int cost = player.getActionCost(Action.TILLING);
        font.draw(sb, cost < 0 ? "MAXED" : "$" + cost, cost < 0 ? 28 : cost < 100 ? 35 : 32, 45);
        fontSmall.draw(sb, "Upgrade", 126, 70);
        fontSmall.draw(sb, "Watering", 123, 60);
        cost = player.getActionCost(Action.WATERING);
        font.draw(sb, cost < 0 ? "MAXED" : "$" + cost, cost < 0 ? 128 : cost < 100 ? 135 : 132, 45);
        fontSmall.draw(sb, "Upgrade", 226, 70);
        fontSmall.draw(sb, "Seeding", 226, 60);
        cost = player.getActionCost(Action.SEEDING);
        font.draw(sb, cost < 0 ? "MAXED" : "$" + cost, cost < 0 ? 228 : cost < 100 ? 235 : 232, 45);
        fontSmall.draw(sb, "Upgrade", 326, 70);
        fontSmall.draw(sb, "Harvest", 326, 60);
        cost = player.getActionCost(Action.HARVESTING);
        font.draw(sb, cost < 0 ? "MAXED" : "$" + cost, cost < 0 ? 328 : cost < 100 ? 335 : 332, 45);

        font.draw(sb, "Money: $" + player.getMoney(), 10, Vars.HEIGHT - 10);
        font.draw(sb, "Enter to buy. Escape/Tab to return to game.", 10, 20);

        sb.end();

        shapeRenderer.setProjectionMatrix(cam.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        Gdx.gl.glEnable(GL20.GL_BLEND);

        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(100 * currentCol + 20, 100 * (2 - currentRow) + 20 + (currentRow == 2 ? 12 : 0), 60, currentRow == 2 ? 45 : 60);

        shapeRenderer.setAutoShapeType(true);
        shapeRenderer.set(ShapeRenderer.ShapeType.Filled);
        for (int row = 0; row < crops.length; row++) {
            for (int col = 0; col < crops[0].length; col++) {
                if (crops[row][col].cost > player.getMoney()) {
                    shapeRenderer.setColor(0, 0, 0, 0.75f);
                    shapeRenderer.rect(18 + 100 * col, 100 * (2 - row) + 18, 64, 64);
                    shapeRenderer.setColor(.9f, 0, 0, .75f);
                    shapeRenderer.line(20 + 100 * col, 100 * (2 - row) + 20, 20 + 100 * col + 60, 100 * (2 - row) + 20 + 60);
                }
            }
        }

        shapeRenderer.setColor(0, 0, 0, 0.75f);
        Action[] actions = {Action.TILLING, Action.WATERING, Action.SEEDING, Action.HARVESTING};
        for (int r = 0; r < 4; r++) {
            int actionCost = player.getActionCost(actions[r]);
            if (actionCost >= 0 && player.getMoney() < actionCost)
                shapeRenderer.rect(10 + r * 102, 31, 80, 50);
        }

        shapeRenderer.end();

        if (showingPopup) {
            sb.begin();
            sb.setColor(Color.BLACK);
            sb.draw(pixel, Vars.WIDTH / 2 - 100, Vars.HEIGHT / 2 - 25, 200, 50);
            sb.setColor(Color.WHITE);
            sb.draw(pixel, Vars.WIDTH / 2 - 100, Vars.HEIGHT / 2 - 25, 200, 1);
            sb.draw(pixel, Vars.WIDTH / 2 - 100, Vars.HEIGHT / 2 - 25, 1, 50);
            sb.draw(pixel, Vars.WIDTH / 2 - 100, Vars.HEIGHT / 2 + 25, 200, 1);
            sb.draw(pixel, Vars.WIDTH / 2 + 100, Vars.HEIGHT / 2 - 25, 1, 50);
            if (popupText.equals("Not enough money!")) {
                font.draw(sb, popupText, 127, 154);
            } else {
                font.draw(sb, popupText, 147, 154);
            }
            sb.end();
        }
    }

}
