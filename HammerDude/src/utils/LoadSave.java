package utils;

import entities.Pig;
import exceptions.NullPointerException;
import main.Game;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.PIG;

public class LoadSave {

    //imagini din folderul res

    public static final String PLAYER_SPRITE = "images/player_sprite.png";
    public static final String LEVEL_SPRITE = "images/outside_sprites.png";
    public static final String LEVEL_ONE = "images/level_one_data_long.png";
    public static final String LEVEL_TWO = "images/level_one_data.png";
    public static final String MENU_BUTTONS = "images/button_atlas.png";
    public static final String MENU_BG = "images/menu_background.png";
    public static final String BG = "images/bg_menu.jpg";
    public static final String LEVEL_ONE_BG = "images/playing_bg_img.png";
    public static final String PAUSE_BACKGROUND = "images/pause_menu.png";
    public static final String SOUND_BUTTONS = "images/sound_button.png";
    public static final String ACT_BUTTONS = "images/act_buttons.png";
    public static final String VOLUME_BUTTONS = "images/volume_buttons.png";
    public static final String BIG_CLOUDS = "images/big_clouds.png";
    public static final String SMALL_CLOUDS = "images/small_clouds.png";
    public static final String PIG_SPRITE = "images/pig_sprite_2.png";
    public static final String STATUS_BAR = "images/health_power_bar.png";
    public static final String GAME_OVER = "images/game_over.png";
    public static final String WIN = "images/win_screen.png";

    public static BufferedImage getPlayerSprite(String file) {
        BufferedImage img = null;

        InputStream is = LoadSave.class.getResourceAsStream("/" + file);

        try {

            if (is == null)
                throw new NullPointerException();
            img = ImageIO.read(is);

        } catch (IOException | NullPointerException e) {

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

    public static ArrayList<Pig> getPigs() {

        BufferedImage img = getPlayerSprite(LEVEL_ONE);
        ArrayList<Pig> list = new ArrayList<>();
        for (int j = 0; j < img.getHeight(); j++)
            for (int i = 0; i < img.getWidth(); ++i) {
                Color color = new Color(img.getRGB(i, j));
                int value = color.getGreen();
                if (value == PIG) {
                    list.add(new Pig(i * Game.TILES_SIZE, j * Game.TILES_SIZE));
                }

            }
        return list;
    }

//    public static int getKey() {
//
//        BufferedImage img = getPlayerSprite(LEVEL_ONE);
//        int key;
//        for (int j = 0; j < img.getHeight(); j++)
//            for (int i = 0; i < img.getWidth(); ++i) {
//                Color color = new Color(251,249,2);
//
//            }
//    }

    public static int[][] getLevel() {

        BufferedImage img = getPlayerSprite(LEVEL_ONE);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

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
    public static int[][] getLevelTwo() {

        BufferedImage img = getPlayerSprite(LEVEL_TWO);
        int[][] lvlData = new int[img.getHeight()][img.getWidth()];

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

