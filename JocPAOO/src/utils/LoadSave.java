package utils;

import main.Game;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class LoadSave {

    public static final String PLAYER_SPRITE = "css_sprites.png";
    public static final String LEVEL_SPRITE = "outside_sprites.png";
    public static final String LEVEL_TUTORIAL = "outside_sprites.png";

    public static BufferedImage getPlayerSprite(String file) {
        BufferedImage img = null;

        InputStream is = LoadSave.class.getResourceAsStream("/" + file);

        try {
            img = ImageIO.read(is);

        } catch (IOException e) {

            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return img;
    }
}

