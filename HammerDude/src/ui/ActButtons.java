package ui;

import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

import static utils.Constants.UI.ActButtons.ACT_DEFAULT_SIZE;
import static utils.Constants.UI.ActButtons.ACT_SIZE;


public class ActButtons extends PauseButton {

    private BufferedImage[] imgs;
    private int rowIndex, index;
    private boolean mouseOver, mousePressed;

    public ActButtons(int x, int y, int width, int height, int rowIndex) {
        super(x, y, width, height);
        this.rowIndex = rowIndex;
        loadImgs();
    }

    private void loadImgs() {
        BufferedImage temp = LoadSave.getPlayerSprite(LoadSave.ACT_BUTTONS);
        imgs = new BufferedImage[3];
        for (int i = 0; i < imgs.length; ++i) {
            imgs[i] = temp.getSubimage(i * ACT_DEFAULT_SIZE, rowIndex * ACT_DEFAULT_SIZE, ACT_DEFAULT_SIZE, ACT_DEFAULT_SIZE);
        }
    }

    public void draw(Graphics g) {

        g.drawImage(imgs[index], x, y, ACT_SIZE, ACT_SIZE, null);

    }

    public void update() {
        index = 0;
        if (mouseOver)
            index = 1;
        if (mousePressed)
            index = 2;

    }

    public void resetBools() {
        mousePressed = false;
        mouseOver = false;
    }

    public boolean isMouseOver() {
        return mouseOver;
    }

    public void setMouseOver(boolean mouseOver) {
        this.mouseOver = mouseOver;
    }

    public boolean isMousePressed() {
        return mousePressed;
    }

    public void setMousePressed(boolean mousePressed) {
        this.mousePressed = mousePressed;
    }
}
