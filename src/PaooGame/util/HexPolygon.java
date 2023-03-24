package PaooGame.util;

import PaooGame.Tiles.Tile;

import java.awt.*;

/**
 * Clasa de utilitate care este folosita pentru a definii planul inchis determinat de un hexagon de pe ecran*/
public class HexPolygon {
    private Polygon Hex;

    /**
     * Constructorul clasei
     * @param map Coordonata de pe harta al carei hitbox va fi determinat.*/
    public HexPolygon(Coordonata map){
        Coordonata tile_position;
        if(map.getY() % 2 == 0)
        {
            tile_position = new Coordonata(map.getX() * Tile.TILE_WIDTH , map.getY() * (Tile.TILE_HEIGHT - Tile.TILE_STAGGER));
        }
        else
        {
            tile_position = new Coordonata(map.getX() * Tile.TILE_WIDTH + Tile.TILE_WIDTH/2, map.getY() * (Tile.TILE_HEIGHT - Tile.TILE_STAGGER));
        }

        Hex = new Polygon(new int[]{tile_position.getX(),
                                    tile_position.getX() + Tile.TILE_WIDTH/2,
                                    tile_position.getX() + Tile.TILE_WIDTH,
                                    tile_position.getX() + Tile.TILE_WIDTH,
                                    tile_position.getX() + Tile.TILE_WIDTH/2,
                                    tile_position.getX()},
                          new int[]{tile_position.getY() + Tile.TILE_SCALE * (9 + 6),
                                    tile_position.getY() + Tile.TILE_SCALE * 6,
                                    tile_position.getY() + Tile.TILE_SCALE * (9 +  6),
                                    tile_position.getY() + Tile.TILE_SCALE * (24 + 6),  //cei 6 pixeli pe care ii adaug sunt de la un offset la tile-uri pentru a incapea gardurile
                                    tile_position.getY() + Tile.TILE_SCALE * (32 + 6),
                                    tile_position.getY() + Tile.TILE_SCALE * (24 + 6)},
                          6);


    }

    /**
     * @return Un obiect de tip Polygon care poate fi interogat daca positia mousului apartine acestuia, in esenta verifict daca clickul a fost sau nu in acel tile*/
    public Polygon getHex() {
        return Hex;
    }
}
