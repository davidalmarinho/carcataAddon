package com.davidalmarinho.java2dgame.scenes;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.davidalmarinho.java2dgame.data_structures.Transform;
import com.davidalmarinho.java2dgame.game_objects.GameObject;
import com.davidalmarinho.java2dgame.game_objects.components.Hex;
import com.davidalmarinho.java2dgame.game_objects.components.Spritesheet;
import com.davidalmarinho.java2dgame.levels.Levels;
import com.davidalmarinho.java2dgame.main.GameManager;
import com.davidalmarinho.java2dgame.utils.Vector2;

import java.util.Random;

public class LevelScene extends Scene {
   public Spritesheet spritesheet;
   public Spritesheet spr;
   private Levels levels;
   private int level = 0;
   private boolean mayTouch;
   private boolean mayRandomize;
   private int randomizeFps;
   private int timeLeft;

    @Override
    public void init() {
        this.spritesheet = new Spritesheet("spritesheet.png", 4, 6, 16, 16);
        this.spr = new Spritesheet("hexs.png", 2, 1, 2048, 2048);
        int hexsNum = 8 + 9 + 10 + 11 + 12 + 13+ 14 + 15+ 14 +13 +12+ 11+ 10 + 9 + 8;
        // Player playerComp = new Player(spritesheet.getSprites().get(8));
        // GameObject player = new GameObject("Player", new Transform(new Vector2(200, 200)));
        // player.addComponent(playerComp);
        // player.addComponent(new RigidBody(new Vector2(200.0f, 200.0f)));
        // player.addComponent(new BoxBounds(Constants.TILE_SIZE, Constants.TILE_SIZE));
        // player.depth = 1;
        // addGameObject(player);
        initLevel();
    }

    private void initLevel() {
        this.levels = Levels.getInstance("levels/");
        GameObject hex = new GameObject("Hex", new Transform(new Vector2(80, 80)));
        hex.addComponent(new Hex(0, spr.getSprites().get(0), spr.getSprites().get(1)));
        hex.depth = 1;
        addGameObject(hex);
        Levels.getInstance("levels/").levels.get(level).init();
    }

    private void randomize() {
        if (Gdx.input.isTouched()) {
            if (mayTouch) {
                // Action
                mayRandomize = true;
                mayTouch = false;
            }
        } else {
            mayTouch = true;
        }

        if (mayRandomize) {
            randomizeFps++;

            if (randomizeFps > 10 + timeLeft) {
                timeLeft += 5;
                randomizeFps = 0;

                // Unselect the hex to make animation
                for (GameObject go : gameObjects) {
                    Hex hex = go.getComponent(Hex.class);
                    if (hex != null) {
                        hex.selected = false;
                    }
                }

                int randID;
                do {
                    randID = new Random().nextInt(169);

                } while (randID >= 6 && randID <= 8 || randID == 21 || randID == 22 || randID == 98 || randID == 99);

                boolean caughtID = false;
                for (int i = 0; i < gameObjects.size(); i++) {
                    GameObject go = gameObjects.get(i);
                    Hex hex = go.getComponent(Hex.class);
                    if (hex != null) {
                        if (randID == hex.ID) {
                            caughtID = true;
                            hex.selected = true;
                        }
                    }

                    if (i == gameObjects.size() - 1) {
                        if (!caughtID) {
                            System.out.println("ERROR: ID " + randID + " doesn't exist!");
                        }
                    }
                }
            }

            if (timeLeft > 20) {
                timeLeft = 0;
                mayRandomize = false;
            }
        }
    }

    @Override
    public void update(float dt) {
        updateGameObjects(dt);
        randomize();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.draw(GameManager.getInstance().getBatch());
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
