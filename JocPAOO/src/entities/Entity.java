package entities;

import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class Entity {

    protected float x, y;
    protected int width, height;
    protected Rectangle2D.Float hitBox;


    public Entity(float x, float y, int width, int height) {

        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    protected void drawHitBox(Graphics g) {
        g.setColor(Color.PINK);
        g.drawRect((int) hitBox.x, (int) hitBox.y, (int) hitBox.width, (int) hitBox.height);

    }

    protected void initHitBox(float x, float y) {
        hitBox = new Rectangle2D.Float(x, y, 46, 40);

    }

    public Rectangle2D.Float getHitBox() {

        return hitBox;
    }
}
