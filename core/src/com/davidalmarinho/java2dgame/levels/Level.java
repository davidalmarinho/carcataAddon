package com.davidalmarinho.java2dgame.levels;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.davidalmarinho.java2dgame.data_structures.Transform;
import com.davidalmarinho.java2dgame.game_objects.GameObject;
import com.davidalmarinho.java2dgame.game_objects.MapColors;
import com.davidalmarinho.java2dgame.game_objects.components.BoxBounds;
import com.davidalmarinho.java2dgame.game_objects.components.Floor;
import com.davidalmarinho.java2dgame.game_objects.components.Hex;
import com.davidalmarinho.java2dgame.game_objects.components.Player;
import com.davidalmarinho.java2dgame.game_objects.components.RigidBody;
import com.davidalmarinho.java2dgame.game_objects.components.Sprite;
import com.davidalmarinho.java2dgame.game_objects.components.Wall;
import com.davidalmarinho.java2dgame.main.GameManager;
import com.davidalmarinho.java2dgame.scenes.LevelScene;
import com.davidalmarinho.java2dgame.utils.Constants;
import com.davidalmarinho.java2dgame.utils.Vector2;

import java.awt.Window;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Level {
    private final Pixmap map;

    public Level(FileHandle fileHandle) {
        // Create level
        map = new Pixmap(fileHandle); // Kept in BGRA (Blue, Green, Red, Alpha) format, not ARGB
    }

    public void init() {
        int width = map.getWidth();
        int height = map.getHeight();

        // World's real size
        Constants.WORLD_WIDTH = width * Constants.TILE_SIZE;
        Constants.WORLD_HEIGHT = height * Constants.TILE_SIZE;

        // Temporary list of game objects that will belong to the Scene
        List<GameObject> gameObjects = new ArrayList<>();

        // Clear the gameObjects' list
        GameManager.getInstance().getCurrentScene().clearGameObjects();

        // To don't have always write looooooong codes
        LevelScene levelScene = (LevelScene) GameManager.getInstance().getCurrentScene();

        Vector2 mid = new Vector2(Constants.WINDOW_WIDTH / 2.0f - Constants.HEX_WIDTH,
                Constants.WINDOW_HEIGHT / 2.0f - Constants.HEX_HEIGHT / 2.0f);

        // Size of the hex-table
        int rowWidth = 15;
        int rowHeight = 15;

        GameObject[] objs = new GameObject[169];

        int id = 0;
        boolean resetRowWidth = true;
        for (int yy = 0; yy < rowHeight; yy++) {
            Vector2 midBegin;

            // Set y's because we are creating the table from the center to bottom and
            // from center to top
            float yMid = mid.y - yy * Constants.HEX_HEIGHT;
            if (yy >= 8) {
                if (resetRowWidth) {
                    rowWidth = 14;
                    resetRowWidth = false;
                }
                yMid = mid.y + (yy - 8 + 1) * Constants.HEX_HEIGHT;
            }

            // Align hexs
            if (rowWidth % 2 == 0) {
                midBegin = new Vector2(mid.x - (int) (rowWidth / 2) * Constants.HEX_WIDTH + Constants.HEX_WIDTH / 2.0f,
                        yMid);
            } else {
                midBegin = new Vector2(mid.x - (int) (rowWidth / 2) * Constants.HEX_WIDTH,
                        yMid);
            }

            // Place hexs in horizontal
            for (int xx = 0; xx < rowWidth; xx++) {
                GameObject go = new GameObject("Hex", new Transform(midBegin.copy()));
                go.depth = 1;
                go.addComponent(new Hex(id, levelScene.spr.getSprites().get(0),
                        levelScene.spr.getSprites().get(1)));
                objs[id] = go.copy();
                midBegin.x += Constants.HEX_WIDTH;
                id++;
            }

            rowWidth--;
        }

        gameObjects.addAll(Arrays.asList(objs));

        // Parse our game objects list to a Scene
        for (GameObject gameObject : gameObjects) {
            if (gameObject == null) {
                System.out.println("Error: Found a null game object in '" + getClass() + "'");
            }
            GameManager.getInstance().getCurrentScene().addGameObject(gameObject);
        }
    }

    /*@Override
    public void render(Graphics2D g2) {
        g2.setColor(new Color(255, 255, 255, 100));
        g2.fillRect(0, 0, width * 64, height * 64);
    }*/
}
