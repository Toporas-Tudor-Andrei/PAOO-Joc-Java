package PaooGame.Tiles.Hex;

import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;

public class TopHole extends Tile {
    public TopHole(int idd){
        super(Assets.top_hole,idd);
    }
    public boolean isDangerous(){
        return true;
    }
}
