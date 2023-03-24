package PaooGame.Tiles.Hex;

import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;

public class BotRightHole extends Tile {
    public BotRightHole(int idd){
        super(Assets.bot_right_hole,idd);
    }
    public boolean isDangerous(){
        return true;
    }
}
