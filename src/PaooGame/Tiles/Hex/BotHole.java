package PaooGame.Tiles.Hex;

import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;

public class BotHole extends Tile {
    public BotHole(int idd){
        super(Assets.bot_hole,idd);
    }
    public boolean isDangerous(){
        return true;
    }
}
