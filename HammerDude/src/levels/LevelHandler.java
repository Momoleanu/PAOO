package levels;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelHandler {

    private BufferedImage[] levelSprite;
    private Level levelOne;
    private Level levelTwo;

    public LevelHandler(Game game) {
        importLevelSprite();
        levelOne = new Level(LoadSave.getLevel());
        levelTwo = new Level(LoadSave.getLevel());
    }

    private void importLevelSprite() {
        BufferedImage img = LoadSave.getPlayerSprite(LoadSave.LEVEL_SPRITE);
        levelSprite = new BufferedImage[48];
        for (int j = 0; j < 4; ++j)
            for (int i = 0; i < 12; ++i) {
                int index = j * 12 + i;
                levelSprite[index] = img.getSubimage(i * 32, j * 32, 32, 32);
            }
    }

    public void draw(Graphics g, int lvlOffset) {

        for (int j = 0; j < Game.TILES_IN_HEIGHT; j++)
            for (int i = 0; i < levelOne.getLevelData()[0].length; ++i) {
                int index = levelOne.getSpriteIndex(i, j);
                g.drawImage(levelSprite[index], Game.TILES_SIZE * i - lvlOffset, Game.TILES_SIZE * j, Game.TILES_SIZE, Game.TILES_SIZE, null);
            }
    }

    public void update() {

    }

    public Level getCrntLevel() {

        return levelOne;
    }
    public Level getLevelTwo(){
        return levelTwo;
    }
}
