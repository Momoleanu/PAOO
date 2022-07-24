package entities;

import main.Game;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static utils.Constants.Directions.RIGHT;
import static utils.Constants.EnemyConstants.*;

public class Pig extends Enemy {

    private Rectangle2D.Float attackArea;
    private int attackAreaOffsetX;

    public Pig(float x, float y) {
        super(x, y, PIG_WIDTH, PIG_HEIGHT, PIG);
        initHitBox(x, y, (int) (22 * Game.SCALE), (int) (19 * Game.SCALE));
        initAttackArea();

    }

    public void drawAttackArea(Graphics2D g, int xLvlOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) (attackArea.x - xLvlOffset), (int) attackArea.y, (int) attackArea.width, (int) attackArea.height);

    }

    private void initAttackArea() {
        attackArea = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (28 * Game.SCALE));
        attackAreaOffsetX = (int) (Game.SCALE * 10);
    }

    public void update(int[][] lvlData, Player player) {
        updateBehavior(lvlData, player);
        updateAnimationTick();
        updateAttackArea();
    }

    private void updateAttackArea() {
        attackArea.x = hitBox.x - attackAreaOffsetX;
        attackArea.y = hitBox.y;
    }


    private void updateBehavior(int[][] lvlData, Player player) {
        if (firstUpdate) {
            firstUpdateCheck(lvlData);
        }
        if (inAir) {
            inAirCheck(lvlData);
        } else {
            switch (enemyState) {
                case IDLE -> changeState(RUN);
                case RUN -> {
                    if (canSeePlayer(lvlData, player))
                        moveTowardsPlayer(player);
                    if (isPlayerClose(player) && hitBox.y == player.hitBox.y)
                        changeState(ATTACK);
                    move(lvlData);
                }
                case ATTACK -> {
                    if (aniIndex == 0)
                        attackChecked = false;
                    if (aniIndex == 2 && !attackChecked) {
                        checkEnemyHit(attackArea, player);
                    }
                }

            }
        }

    }

    public int flipX() {
        if (walkD == RIGHT)
            return width;
        else
            return 0;
    }

    public int flipY() {
        if (walkD == RIGHT)
            return -1;
        else
            return 1;
    }
}
