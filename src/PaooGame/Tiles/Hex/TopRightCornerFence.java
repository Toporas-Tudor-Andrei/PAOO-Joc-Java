package PaooGame.Tiles.Hex;

import PaooGame.Graphics.Assets;
import PaooGame.Tiles.Tile;
import PaooGame.util.Coordonata;
import PaooGame.util.HexPolygon;

public class TopRightCornerFence extends Tile {
    public TopRightCornerFence(int idd) {
        super(Assets.top_right_corner_fence,idd);
    }

    public Coordonata[] GetNeighbors(Coordonata harta){
        Coordonata[] vecini = new Coordonata[6];
        if(harta.getY() % 2 == 0)
        {
            //vecini[0] = new Coordonata(harta.getX() - 1, harta.getY() - 1);
            //vecini[1] = new Coordonata(   harta.getX()    , harta.getY() - 1);
            //vecini[2] = new Coordonata(harta.getX() + 1,    harta.getY());
            vecini[3] = new Coordonata(   harta.getX()    , harta.getY() + 1);
            vecini[4] = new Coordonata(harta.getX() - 1, harta.getY() + 1);
            vecini[5] = new Coordonata(harta.getX() - 1,    harta.getY());
        }
        else
        {
            //vecini[0] = new Coordonata(   harta.getX()    ,harta.getY() - 1);
            //vecini[1] = new Coordonata(harta.getX() + 1,harta.getY() - 1);
            //vecini[2] = new Coordonata(harta.getX() + 1,   harta.getY());
            vecini[3] = new Coordonata(harta.getX() + 1,harta.getY() + 1);
            vecini[4] = new Coordonata(   harta.getX()    ,harta.getY() + 1);
            vecini[5] = new Coordonata(harta.getX() - 1,   harta.getY());
        }
        return vecini;
    }

    public Coordonata CheckNeighbor(Coordonata mouse, Coordonata harta){
        Coordonata[] vecini = GetNeighbors(harta);

        HexPolygon[] hitbox =new HexPolygon[6];
        for(int a = 0; a < 6; a++)
        {
            if(vecini[a] != null) {
                hitbox[a] = new HexPolygon(vecini[a]);
            }
        }

        for(int a = 0; a < 6; a++)
        {
            if(hitbox[a] != null) {
                if (hitbox[a].getHex().contains(mouse.getX(), mouse.getY())) return vecini[a];
            }
        }

        return harta;

    }

}
