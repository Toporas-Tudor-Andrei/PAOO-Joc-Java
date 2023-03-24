package PaooGame.Tiles.Hex;

import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;

public class LeftHole extends Tile {
    public LeftHole(int idd){
        super(Assets.left_hole,idd);
    }
    public boolean isDangerous(){
        return true;
    }
}
