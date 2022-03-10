package main;

import inputs.KeyboardInputs;
import inputs.MouseInputs;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.*;
import javax.swing.*;
import static utils.Constants.PlayerConstants.*;
import static utils.Constants.Directions.*;


public class GamePanel extends JPanel {
    private MouseInputs mouseInputs = new MouseInputs(this);
    private float xDelta = 100.0F;
    private float yDelta = 100.0F;
    private BufferedImage img;
    private BufferedImage[][] animations;
    private int animationTick, animationIndex, animationSpeed=5;
    private int playerAction=IDLE;
    private int playerDirection=-1;
    private boolean isMoving=false;


    public GamePanel() {
        this.addKeyListener(new KeyboardInputs(this));
        importImg();
        loadAnimations();
        setPanelSize();
    }

    private void loadAnimations() {

        animations =new BufferedImage[4][11];
        for(int j=0;j<animations.length; j++)
            for(int i = 0; i< animations[j].length; ++i)
             {
                animations[j][i]=img.getSubimage(i*78,j*58,78,58);
             }

    }

    private void importImg() {
        InputStream is= getClass().getResourceAsStream("/css_sprites.png");

        try {
            img = ImageIO.read(is);

        }catch(IOException e) {

            e.printStackTrace();
        }finally {
            try{
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateAnimationTick() {

        animationTick++;
        if(animationTick>=animationSpeed)
        {
            animationTick=0;
            animationIndex++;
            if(animationIndex>=GetSA(playerAction))
                animationIndex=0;
        }
    }
    private void updatePosition() {

        if(isMoving)
        {
            switch (playerDirection){
                case LEFT:
                    xDelta-=2;
                    break;
                case UP:
                    yDelta-=2;
                    break;
                case RIGHT:
                    xDelta+=2;
                    break;
                case DOWN:
                    yDelta+=2;
                    break;

            }
        }
    }


    private void setPanelSize() {
        Dimension size=new Dimension(1280,800);
        setMinimumSize(size);
        setPreferredSize(size);
        setMaximumSize(size);

    }
    public void setDirection(int direction)
    {
        this.playerDirection=direction;
        isMoving=true;
    }

    public void setMoving(boolean moving)
    {
        this.isMoving=moving;
    }
    private void setAnimation() {

        if(isMoving)
        {
            playerAction=RUNNING;
        }
        else
        {
            playerAction=IDLE;
        }
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        updateAnimationTick();
        setAnimation();
        updatePosition();
        if(playerDirection==LEFT) {

            g.drawImage(animations[playerAction][animationIndex], (int) xDelta+100, (int) yDelta, -128, 116, null);
        }
        else
        {
            g.drawImage(animations[playerAction][animationIndex], (int) xDelta, (int) yDelta, 128, 116, null);
        }

    }

}
