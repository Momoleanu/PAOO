package utils;

import main.Game;

import java.awt.geom.Rectangle2D;

public class Checks {

    public static boolean CanMoveHere(float x, float y, float width, float height, int[][] lvlData) {

        if (IsSolid(x, y, lvlData))
            if (IsSolid(x + width, y + height, lvlData))
                if (IsSolid(x + width, y, lvlData))
                    return IsSolid(x, y + height, lvlData);
        return false;
    }

    private static boolean IsSolid(float x, float y, int[][] lvlData) {

        if (x < 0 || x >= Game.GAME_WIDTH)
            return false;
        if (y < 0 || y >= Game.GAME_HEIGHT)
            return false;

        float xIndex = x / Game.TILES_SIZE;
        float yIndex = y / Game.TILES_SIZE;

        int value = lvlData[(int) yIndex][(int) xIndex];

        return value == 11;
    }

    public static float GetEntityXPosNextToWall(Rectangle2D.Float hitBox, float xSpeed) {

        int currentTile = (int) (hitBox.x / Game.TILES_SIZE);
        if (xSpeed > 0) {
            int tileXPos = currentTile * Game.TILES_SIZE;
            int xOffset = (int) (Game.TILES_SIZE - hitBox.width);
            return tileXPos + xOffset - 1;
        } else

            return currentTile * Game.TILES_SIZE;
    }


    public static float GetEntityIfUnderOrAboveFloor(Rectangle2D.Float hitBox, float airSpeed) {
        int currentTile = (int) (hitBox.y / Game.TILES_SIZE);
        if (airSpeed > 0) {

            int tileYPos = currentTile * Game.TILES_SIZE;
            int yOffset = (int) (Game.TILES_SIZE - hitBox.height);
            return tileYPos + yOffset - 1;
        } else

            return currentTile * Game.TILES_SIZE;

    }

    public static boolean isEntityOnFloor(Rectangle2D.Float hitBox, int[][] lvlD) {


        if (IsSolid(hitBox.x, hitBox.y + hitBox.height + 1, lvlD))
            return !IsSolid(hitBox.x + hitBox.width, hitBox.y + hitBox.height + 1, lvlD);
        return true;

    }
}
