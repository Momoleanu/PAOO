package entities;

import gamestates.Playing;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import static utils.Constants.EnemyConstants.*;

public class EnemyHandler {

    public ArrayList<Pig> pigs;
    public int size;
    private Playing playing;
    private BufferedImage[][] pigArray;
    public int score = 0;

    public EnemyHandler(Playing playing) {

        pigs=new ArrayList<>();
        this.playing = playing;
        loadEnemyImgs();
        addEnemy();
    }

    private void addEnemy() {

        pigs = LoadSave.getPigs();
        System.out.println("Size of pigs: " + pigs.size());
        size = pigs.size();
    }

    private void loadEnemyImgs() {
        pigArray = new BufferedImage[6][11];
        BufferedImage temp = LoadSave.getPlayerSprite(LoadSave.PIG_SPRITE);
        for (int j = 0; j < pigArray.length; ++j)
            for (int i = 0; i < pigArray[j].length; ++i) {
                pigArray[j][i] = temp.getSubimage((int) (i * PIG_WIDTH_DEFAULT), j * PIG_HEIGHT_DEFAULT, (int) PIG_WIDTH_DEFAULT, PIG_HEIGHT_DEFAULT);
            }
    }

    public void update(int[][] lvlData, Player player) {

        for (Pig p : pigs) {
            if (p.doesExist())
                p.update(lvlData, player);
        }
    }

    public void draw(Graphics g, int xLvlOffset) {
        drawPigs(g, xLvlOffset);
    }

    private void drawPigs(Graphics g, int xLvlOffset) {
        for (Pig p : pigs) {
            if (p.doesExist()) {
                g.drawImage(pigArray[p.getEnemyState()][p.getAniIndex()], (int) p.getHitBox().x - xLvlOffset - PIG_DRAW_OFFSET_X + p.flipX(), (int) p.getHitBox().y, PIG_WIDTH * p.flipY(), PIG_HEIGHT, null);
                // p.drawHitBox(g, xLvlOffset);
            }
        }
    }

    public void checkHit(Rectangle2D.Float attackArea) {
        for (Pig p : pigs) {
            if (p.doesExist()) {
                if (attackArea.intersects(p.getHitBox())) {
                    p.dealDmg(10);
                    System.out.println("Ai omorat un inamic");
                    size--;
                    score += 10;
                    System.out.printf("Au mai ramas %d\n", size);
                    System.out.printf("Ai %d scor \n", score);
                    return;
                }
            }
        }
    }

    public void resetAll() {
        for (Pig p : pigs) {
            p.resetEnemy();
            size = pigs.size();
            score = 0;
        }
    }
}
