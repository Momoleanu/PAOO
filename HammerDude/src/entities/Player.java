package entities;

import gamestates.Gamestate;
import gamestates.Playing;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import static utils.Checks.*;
import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {

    public boolean didIWin = false;
    public boolean didILose = false;
    private int animationTick;
    private int animationIndex;
    private int playerAction = IDLE;
    private int[][] lvlD;
    private float airSpeed = 0f;
    private boolean left, up, right, down, jump;
    private boolean isMoving = false, isAttacking = false;
    private boolean inAir = false;
    private int flipX = 0;
    private int flipY = 1;
    private boolean attackCheck = false;
    private Gamestate gamest;
    private BufferedImage[][] animations;
    private BufferedImage statusBarImg;
    private Playing playing;
    private EnemyHandler enemyHandler;
    private int statusBarWidth = (int) (192 * Game.SCALE);
    private int statusBarHeight = (int) (58 * Game.SCALE);
    private int statusBarX = (int) (10 * Game.SCALE);
    private int statusBarY = (int) (10 * Game.SCALE);
    private int healthBarWidth = (int) (150 * Game.SCALE);
    private int healthBarHeight = (int) (4 * Game.SCALE);
    private int healthBarXStart = (int) (34 * Game.SCALE);
    private int healthBarYStart = (int) (14 * Game.SCALE);
    private int maxHealth = 100;
    private int currentHealth = maxHealth;
    private int healthWidth = healthBarWidth;
    private Rectangle2D.Float attackArea;


    public Player(float x, float y, int width, int height, Playing playing, EnemyHandler enemyHandler) {
        super(x, y, width, height);
        this.playing = playing;
        this.enemyHandler = enemyHandler;
        loadAnimations();
        initHitBox(x, y, width, height);
        initAttack();
    }

    private void initAttack() {
        attackArea = new Rectangle2D.Float(x, y, (int) (20 * Game.SCALE), (int) (20 * Game.SCALE));

    }

    public void update() {

        updateStatus();
        if (currentHealth <= 0) {
            playing.setGameOver(true);
            didIWin = false;
            didILose = true;
            return;
        } else if (enemyHandler.size == 0) {
            playing.setWin(true);
            didIWin = true;
            didILose = false;
            return;
        }
        updateAttack();
        updatePos();
        if (isAttacking)
            checkAttack();
        updateAnimationTick();
        setAnimation();

    }

    private void checkAttack() {
        if (attackCheck || animationIndex == 0)
            return;
        attackCheck = true;
        playing.checkEnemyHit(attackArea);

    }

    private void updateAttack() {
        if (right) {
            attackArea.x = hitBox.x + hitBox.width + (int) (Game.SCALE * 10);
        } else if (left) {
            attackArea.x = hitBox.x - hitBox.width - (int) (Game.SCALE * 10) + 60;
        }
        attackArea.y = hitBox.y + (Game.SCALE * 10);
    }

    private void updateStatus() {

        healthWidth = (int) ((currentHealth / (float) maxHealth) * healthBarWidth);
    }

    public void render(Graphics g, int lvlOffset) {

        float xOff = 12 * Game.SCALE;
        float yOff = 16 * Game.SCALE;
        g.drawImage(animations[playerAction][animationIndex], (int) (hitBox.x - xOff) - lvlOffset + flipX, (int) (hitBox.y - yOff), width * flipY, height, null);

        // drawHitBox(g, lvlOffset);
        //drawAttack(g,lvlOffset);
        drawStatus(g);
    }

    private void drawAttack(Graphics g, int lvlOffset) {
        g.setColor(Color.RED);
        g.drawRect((int) attackArea.x - lvlOffset, (int) attackArea.y, (int) attackArea.width, (int) attackArea.height);
    }

    private void drawStatus(Graphics g) {
        g.drawImage(statusBarImg, statusBarX, statusBarY, statusBarWidth, statusBarHeight, null);
        g.setColor(Color.RED);
        g.fillRect(healthBarXStart + statusBarX, healthBarYStart + statusBarY, healthWidth, healthBarHeight);
    }

    private void updateAnimationTick() {

        animationTick++;
        int animationSpeed = 15;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSA(playerAction)) {
                animationIndex = 0;
                isAttacking = false;
                attackCheck = false;
            }
        }
    }


    private void setAnimation() {

        int startA = playerAction;

        if (isMoving) {
            playerAction = RUNNING;
        } else {
            playerAction = IDLE;
        }

        if (isAttacking) {

            playerAction = ATTACK;
            if (startA != ATTACK) {
                animationIndex = 0;
                animationTick = 0;
            }
        }

        if (inAir) {
            if (airSpeed < 0) {
                playerAction = JUMP;
            } else {
                playerAction = FALL;
                if (IsEntityOnFloor(hitBox, lvlD))
                    playerAction = GROUND;

            }
        }


        if (startA != playerAction) {
            resetA();
        }
    }

    private void resetA() {

        animationTick = 0;
        animationIndex = 0;
    }

    private void updatePos() {
        isMoving = false;

        if (jump)
            jump();
        if (!inAir)
            if ((!left && !right) || (right && left))
                return;

        float xSpeed = 0;

        float playerSpeed = Game.SCALE;
        if (left) {
            xSpeed -= playerSpeed;
            flipX = width;
            flipY = -1;
        }
        if (right) {
            xSpeed += playerSpeed;
            flipX = 0;
            flipY = 1;
        }
        if (!inAir)
            if (!IsEntityOnFloor(hitBox, lvlD))
                inAir = true;

        if (inAir) {
            if (CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlD)) {
                hitBox.y += airSpeed;
                float gravity = 0.04f * Game.SCALE;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitBox.y = GetEntityIfUnderOrAboveFloor(hitBox, airSpeed);
                float fallSpeedAfterCol = 0.5f * Game.SCALE;
                if (airSpeed > 0)
                    resetAir();
                else
                    airSpeed = fallSpeedAfterCol;
                updateXPos(xSpeed);
            }

        } else
            updateXPos(xSpeed);
        isMoving = true;
    }

    private void jump() {

        if (inAir)
            return;
        inAir = true;
        airSpeed = -2.25f * Game.SCALE;
    }

    private void resetAir() {
        inAir = false;
        airSpeed = 0;
    }

    private void updateXPos(float xSpeed) {

        if (CanMoveHere(hitBox.x + xSpeed, hitBox.y, hitBox.width, hitBox.height, lvlD)) {
            hitBox.x += xSpeed;
        } else {
            hitBox.x = GetEntityXPosNextToWall(hitBox, xSpeed);
        }
    }

    public void changeHealth(int value) {
        currentHealth += value;
        if (currentHealth <= 0) {
            currentHealth = 0;
            //gameOver();
        } else if (currentHealth >= maxHealth) {
            currentHealth = maxHealth;
        }

    }

    private void loadAnimations() {

        BufferedImage img = LoadSave.getPlayerSprite(LoadSave.PLAYER_SPRITE);

        animations = new BufferedImage[8][11];
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; ++i) {
                animations[j][i] = img.getSubimage(i * 78, j * 60, 78, 60);
            }
        statusBarImg = LoadSave.getPlayerSprite(LoadSave.STATUS_BAR);
    }

    public void loadLvlData(int[][] lvlD) {
        this.lvlD = lvlD;
        if (!IsEntityOnFloor(hitBox, lvlD)) {
            inAir = true;
        }

    }

    public void resetBool() {
        left = false;
        up = false;
        right = false;
        down = false;
    }

    public void setAttacking(boolean isAttacking) {

        this.isAttacking = isAttacking;

    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }


    public void resetAll() {
        resetBool();
        inAir = false;
        isAttacking = false;
        isMoving = false;
        playerAction = IDLE;
        currentHealth = 100;

        hitBox.x = x;
        hitBox.y = y;

        if (!IsEntityOnFloor(hitBox, lvlD))
            inAir = true;

    }
}
