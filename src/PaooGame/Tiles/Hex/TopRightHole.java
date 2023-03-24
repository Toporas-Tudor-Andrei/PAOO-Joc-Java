package PaooGame.Tiles.Hex;

import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;

public class TopRightHole extends Tile {
    public TopRightHole(int idd){
        super(Assets.top_right_hole,idd);
    }
    public boolean isDangerous(){
        return true;
    }
}
