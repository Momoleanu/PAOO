package entities;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.PlayerConstants.*;
import static utils.HelpMethods.*;

import main.Game;

public class Player extends Entity {

    private int animationTick, animationIndex, animationSpeed = 15;
    private int playerAction = IDLE;
    private boolean left, up, right, down, jump;
    private boolean isMoving = false, isAttacking = false;
    private float playerSpeed = 1.0f;
    private int[][] lvlD;
    private float xOff = 12 * Game.SCALE;
    private float yOff = 16 * Game.SCALE;

    //Jump+Grav;

    private float airSpeed = 0f;
    private float gravity = 0.04f * Game.SCALE;
    private float jumpSpeed = -2.25f * Game.SCALE;
    private float fallSpeedAfterCol = 0.5f * Game.SCALE;
    private boolean inAir = false;


    private BufferedImage[][] animations;

    public Player(float x, float y, int width, int height) {
        super(x, y, width, height);
        loadAnimations();
        initHitBox(x, y, 31 * Game.SCALE, 28 * Game.SCALE);
    }

    public void update() {
        updatePos();
        updateAnimationTick();
        setAnimation();

    }

    public void render(Graphics g) {

        g.drawImage(animations[playerAction][animationIndex], (int) (hitBox.x - xOff), (int) (hitBox.y - yOff), width, height, null);
        // drawHitBox(g);
    }

    private void updateAnimationTick() {

        animationTick++;
        if (animationTick >= animationSpeed) {
            animationTick = 0;
            animationIndex++;
            if (animationIndex >= GetSA(playerAction)) {
                animationIndex = 0;
                isAttacking = false;
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
        }

        if (inAir) {
            if (airSpeed < 0) {
                playerAction = JUMP;
            } else {
                playerAction = FALL;
                if (isEntityOnFloor(hitBox, lvlD))
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
        if (!left && !right && !inAir)
            return;

        float xSpeed = 0;

        if (left)
            xSpeed -= playerSpeed;
        if (right)
            xSpeed += playerSpeed;

        if (!inAir)
            if (!isEntityOnFloor(hitBox, lvlD))
                inAir = true;

        if (inAir) {
            if (CanMoveHere(hitBox.x, hitBox.y + airSpeed, hitBox.width, hitBox.height, lvlD)) {
                hitBox.y += airSpeed;
                airSpeed += gravity;
                updateXPos(xSpeed);
            } else {
                hitBox.y = GetEntityIfUnderOrAboveFloor(hitBox, airSpeed);
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
        airSpeed = jumpSpeed;
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


    private void loadAnimations() {

        BufferedImage img = LoadSave.getPlayerSprite(LoadSave.PLAYER_SPRITE);

        animations = new BufferedImage[9][11];
        for (int j = 0; j < animations.length; j++)
            for (int i = 0; i < animations[j].length; ++i) {
                animations[j][i] = img.getSubimage(i * 78, j * 60, 78, 60);
            }
    }

    public void loadLvlData(int[][] lvlD) {
        this.lvlD = lvlD;
        if (!isEntityOnFloor(hitBox, lvlD)) {
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


}
