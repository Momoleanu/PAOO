package entities;

import main.Game;

import java.awt.geom.Rectangle2D;

import static utils.Checks.*;
import static utils.Constants.Directions.LEFT;
import static utils.Constants.Directions.RIGHT;
import static utils.Constants.EnemyConstants.*;

public abstract class Enemy extends Entity {

    protected int aniIndex, enemyState, enemyType;
    protected int aniTick, aniSpeed = 25;
    protected boolean firstUpdate = true;
    protected boolean inAir = true;
    protected float fallSpeed = 0;
    protected float gravity = 0.04f * Game.SCALE;
    protected float walkSpeed = 0.35f * Game.SCALE;
    protected int walkD = LEFT;
    protected int tileY;
    protected float attackDist = Game.TILES_SIZE;
    protected int maxHealth;
    protected int currentHealth;
    protected boolean exists = true;
    protected boolean attackChecked;

    public Enemy(float x, float y, int width, int height, int enemyType) {
        super(x, y, width, height);
        this.enemyType = enemyType;
        initHitBox(x, y, width, height);
        maxHealth = getMaxHealth(enemyType);currentHealth = maxHealth;
    }

    protected void updateAnimationTick() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= getSpriteAmount(enemyType, enemyState)) {
                aniIndex = 0;

                switch (enemyState) {
                    case ATTACK, HIT -> enemyState = IDLE;
                    case DEAD -> exists = false;
                }
            }
        }
    }

    protected void changeDirections() {
        if (walkD == LEFT)
            walkD = RIGHT;
        else
            walkD = LEFT;
    }

    protected void firstUpdateCheck(int[][] lvlData) {
        if (!IsEntityOnFloor(hitBox, lvlData))
            inAir = true;
        firstUpdate = false;
    }

    protected void inAirCheck(int[][] lvlData) {
        if (CanMoveHere(hitBox.x, hitBox.y + fallSpeed, hitBox.width, hitBox.height, lvlData)) {
            hitBox.y += fallSpeed;
            fallSpeed += gravity;
        } else {
            inAir = false;
            hitBox.y = GetEntityIfUnderOrAboveFloor(hitBox, fallSpeed);
            tileY = (int) (hitBox.y / Game.TILES_SIZE);
        }
    }

    protected void move(int[][] lvlData) {
        float xSpeed = 0;
        if (walkD == LEFT)
            xSpeed = -walkSpeed;
        else
            xSpeed = walkSpeed;
        if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlData))
            if (IsFloor(hitBox, xSpeed, lvlData)) {
                hitBox.x += xSpeed;
                return;
            }
        changeDirections();
    }

    protected void changeState(int enemyState) {
        this.enemyState = enemyState;
        aniTick = 0;
        aniIndex = 0;
    }

    protected boolean canSeePlayer(int[][] lvlData, Player player) {
        int playerTileY = (int) (player.getHitBox().y / Game.TILES_SIZE);
        if (playerTileY == tileY) {
            if (isPlayerInRange(player)) {
                if (IsSightClear(lvlData, hitBox, player.hitBox, tileY))
                    return true;
            }
        }
        return false;

    }

    protected void moveTowardsPlayer(Player player) {
        if (player.hitBox.x > hitBox.x)
            walkD = RIGHT;
        else
            walkD = LEFT;
    }

    protected boolean isPlayerInRange(Player player) {
        int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
        return absValue <= attackDist * 5;
    }

    protected boolean isPlayerClose(Player player) {
        int absValue = (int) Math.abs(player.hitBox.x - hitBox.x);
        return absValue <= attackDist;
    }

    protected void checkEnemyHit(Rectangle2D.Float attackArea, Player player) {
        if (attackArea.intersects(player.hitBox))
            player.changeHealth(-enemyDmg(enemyType));
        attackChecked = true;
    }

    public void dealDmg(int amount) {
        currentHealth -= amount;
        if (currentHealth <= 0) {
            changeState(DEAD);
        } else
            changeState(HIT);
    }

    public int getAniIndex() {
        return aniIndex;
    }

    public int getEnemyState() {
        return enemyState;
    }

    public boolean doesExist() {
        return exists;
    }

    public void resetEnemy() {
        hitBox.x = x;
        hitBox.y = y;
        firstUpdate = true;
        currentHealth = maxHealth;
        changeState(IDLE);
        exists = true;
        fallSpeed = 0;
    }
}
