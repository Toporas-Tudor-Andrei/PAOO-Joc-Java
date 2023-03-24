package PaooGame.Map;

import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;
import PaooGame.util.Coordonata;

import java.awt.*;

/**
 * CLasa concreta care incapsuleaza metoda de trecere de la un nivel la urmatorul*/
public class PortalGI extends GameItem {

    private static final int Portal_width = 32 * Tile.TILE_SCALE;
    private static final int Portal_height = 32 * Tile.TILE_SCALE;

    private int frame = 0;

    public PortalGI(Coordonata map){
        super(map);
    }

    @Override
    public void Update() {
        if(open && frame < 12) frame++;
        if(!open && frame > 0) frame--;
    }

    @Override
    public void Draw(Graphics2D g) {
        g.drawImage(Assets.portal[frame],Screen.getX(), Screen.getY(), Portal_width,Portal_height,null);
    }

    @Override
    public Coordonata getScreenPos() {
        int x,y;

        if(Map.getY() % 2 ==0) x = Tile.TILE_WIDTH * Map.getX();
        else x = (Tile.TILE_WIDTH/2) + Tile.TILE_WIDTH * Map.getX();

        y = (Tile.TILE_HEIGHT - Tile.TILE_STAGGER) * Map.getY();
        return new Coordonata(x,y);
    }

    /**
     * Functia reinitializeaza obiectul la o stare de default*/
    @Override
    public void newItem(){
        frame = 0;
        open = false;
    }
}
