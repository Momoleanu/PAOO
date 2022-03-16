package levels;

import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.image.BufferedImage;

public class LevelHandler {

    private Game game;
    private BufferedImage[] levelSprite;

    public LevelHandler(Game game){
        this.game=game;
        //levelSprite= LoadSave.getPlayerSprite(LoadSave.LEVEL_SPRITE);
        importLevelSprite();
    }

    private void importLevelSprite() {
        BufferedImage img=LoadSave.getPlayerSprite(LoadSave.LEVEL_SPRITE);
        levelSprite=new BufferedImage[48];
       for(int j=0;j<4;++j)
         for(int i=0;i<12;++i){
               int index= j*12+i;
            levelSprite[index]=img.getSubimage(i*32,j*32,32,32);
          }
    }

    public void draw(Graphics g){
        g.drawImage(levelSprite[0], 0,0,null);
    }

    public void update(){

    }
}
