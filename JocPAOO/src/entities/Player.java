package entities;

import utils.LoadSave;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import static utils.Constants.PlayerConstants.*;

public class Player extends Entity {

    private int animationTick, animationIndex, animationSpeed=15;
    private int playerAction=IDLE;
    private boolean left,up,right,down;
    private boolean isMoving=false,isAttacking=false;
    private float playerSpeed=1.0f;


    private BufferedImage[][] animations;
    public Player(float x, float y) {
        super(x, y);
        loadAnimations();
    }

    public void update() {
        updateAnimationTick();
        setAnimation();
        updatePosition();
    }

    public void render(Graphics g) {

            g.drawImage(animations[playerAction][animationIndex], (int) x, (int) y, 128, 116, null);
    }

    private void updateAnimationTick() {

        animationTick++;
        if(animationTick>=animationSpeed)
        {
            animationTick=0;
            animationIndex++;
            if(animationIndex>=GetSA(playerAction)){
                animationIndex=0;
                isAttacking=false;
            }
        }
    }
    private void updatePosition() {

        isMoving=false;

        if(left && !right){
            x-=playerSpeed;
            isMoving=true;

        }
        else if(right && !left){
            x+=playerSpeed;
            isMoving=true;
        }

        if (up&& !down){
            y-=playerSpeed;
            isMoving=true;
        }
        else if(down&& !up){
            y+=playerSpeed;
            isMoving=true;
        }

    }

    private void setAnimation() {
        
        int startA=playerAction;

        if(isMoving)
        {
            playerAction=RUNNING;
        }
        else
        {
            playerAction=IDLE;
        }

        if(isAttacking){

            playerAction=ATTACK;
        }
        
        if (startA != playerAction){
            resetA();
        }
    }

    private void resetA() {

        animationTick=0;
        animationIndex=0;
    }


    private void loadAnimations() {

            BufferedImage img= LoadSave.getPlayerSprite(LoadSave.PLAYER_SPRITE);

            animations =new BufferedImage[9][11];
            for(int j=0;j<animations.length; j++)
                for(int i = 0; i< animations[j].length; ++i)
                {
                    animations[j][i]=img.getSubimage(i*78,j*58,78,58);
                }
    }

    public void setAttacking(boolean isAttacking){

        this.isAttacking=isAttacking;

    }

    public boolean isLeft() {
        return left;
    }

    public boolean isUp() {
        return up;
    }

    public boolean isRight() {
        return right;
    }

    public boolean isDown() {
        return down;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public void resetBool() {
        left=false;
        up=false;
        right=false;
        down=false;
    }


}
