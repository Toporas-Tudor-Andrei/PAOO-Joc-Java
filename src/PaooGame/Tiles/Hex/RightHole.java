package PaooGame.Tiles.Hex;

import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;

public class RightHole extends Tile {
    public RightHole(int idd){
        super(Assets.right_hole,idd);
    }
    public boolean isDangerous(){
        return true;
    }
}
