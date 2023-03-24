package PaooGame.Tiles.Hex;

import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;

public class TopLeftHole extends Tile {
    public TopLeftHole(int idd){
        super(Assets.top_left_hole,idd);
    }
    public boolean isDangerous(){
        return true;
    }
}
