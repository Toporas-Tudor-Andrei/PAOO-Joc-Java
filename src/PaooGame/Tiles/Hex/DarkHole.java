package PaooGame.Tiles.Hex;

import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;

public class DarkHole extends Tile {
    public DarkHole(int idd){
        super(Assets.dark_hole,idd);
    }
    public boolean isDangerous(){
        return true;
    }
}
