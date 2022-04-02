package utils;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {

    public static final String PLAYER_SPRITE = "images/css_sprites.png";
    public static final String LEVEL_SPRITE = "images/outside_sprites.png";
    public static final String LEVEL_TUTORIAL = "images/level_one_data.png";
    public static final String MENU_BUTTONS = "images/button_atlas.png";
    public static final String MENU_BG = "images/menu_background.png";
    public static final String BG = "images/bg_menu.jpg";
    public static final String LEVEL_ONE_BG = "images/bg.jpg";

    public static BufferedImage getPlayerSprite(String file) {
        BufferedImage img = null;

        InputStream is = LoadSave.class.getResourceAsStream("/" + file);

        try {
            assert is != null;
            img = ImageIO.read(is);

        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            try {
                assert is != null;
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }

    public static int[][] getLevel() {
        int[][] lvlData = new int[Game.TILES_IN_HEIGHT][Game.TILES_IN_WIDTH];
        BufferedImage img = getPlayerSprite(LEVEL_TUTORIAL);

        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); ++i) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getRed();
                if (value >= 48)
                    value = 0;
                lvlData[j][i] = value;
            }
        return lvlData;
    }
}

