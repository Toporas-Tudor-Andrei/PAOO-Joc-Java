package PaooGame.Tiles;

import PaooGame.Tiles.Hex.*;
import PaooGame.util.Coordonata;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 *  Retine toate dalele intr-un vector si ofera posibilitatea regasirii dupa un id.
 */
public class Tile
{
    private static final int NO_TILES   = 32;
    public static Tile[] tiles          = new Tile[NO_TILES];       /*!< Vector de referinte de tipuri de dale.*/

        /// De remarcat ca urmatoarele dale sunt statice si publice. Acest lucru imi permite sa le am incarcate
        /// o singura data in memorie
    public static Tile TopLeftSideFence     = new TopLeftSideFence(0);
    public static Tile TopLeftCornerFence   = new TopLeftCornerFence(1);
    public static Tile TopSideFence         = new TopSideFence(2);
    public static Tile TopLeftHole          = new TopLeftHole(3);
    public static Tile TopHole              = new TopHole(4);
    public static Tile TopRightHole         = new TopRightHole(5);
    public static Tile TopRightCornerFence  = new TopRightCornerFence(6);
    public static Tile TopRightSideFence    = new TopRightSideFence(7);
    public static Tile RightCornerFence     = new RightCornerFence(8);
    public static Tile LeftHole             = new LeftHole(9);
    public static Tile DarkHole             = new DarkHole(10);
    public static Tile RightHole            = new RightHole(11);
    public static Tile BotRightSideFence    = new BotRightSideFence(12);
    public static Tile BotRightCornerFence  = new BotRightCornerFence(13);
    public static Tile BotSideFence         = new BotSideFence(14);
    public static Tile BotLeftHole          = new BotLeftHole(15);
    public static Tile BotHole              = new BotHole(16);
    public static Tile BotRightHole         = new BotRightHole(17);
    public static Tile BotLeftCornerFence   = new BotLeftCornerFence(18);
    public static Tile BotLeftSideFence     = new BotLeftSideFence(19);
    public static Tile LeftCornerFence      = new LeftCornerFence(20);
    public static Tile Floor                = new Floor(21);

    public static final int TILE_SCALE = 2;
    public static final int TILE_STAGGER = 30 * TILE_SCALE;
    public static final int TILE_WIDTH  = 32 * TILE_SCALE;                       /*!< Latimea unei dale.*/
    public static final int TILE_HEIGHT = 54 * TILE_SCALE;                       /*!< Inaltimea unei dale.*/

    protected BufferedImage img;                                    /*!< Imaginea aferenta tipului de dala.*/
    protected final int id;                                         /*!< Id-ul unic aferent tipului de dala.*/

    /**
     *  Constructorul aferent clasei.
        @param image Imaginea corespunzatoare dalei.
        @param idd Id-ul dalei.
     */
    public Tile(BufferedImage image, int idd)
    {
        img = image;
        id = idd;

        tiles[id] = this;
    }

    /**
     * Actualizeaza proprietatile dalei.
     */
    public void Update() {

    }

    /**
     * Deseneaza in fereastra dala.
        @param g Contextul grafic in care sa se realizeze desenarea
        @param x Coordonata x in cadrul ferestrei unde sa fie desenata dala
        @param y Coordonata y in cadrul ferestrei unde sa fie desenata dala
     */
    public void Draw(Graphics2D g, int x, int y)
    {
        g.drawImage(img, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    /**
     * Returneaza id-ul dalei.
     */
    public int GetId()
    {
        return id;
    }

    /**
     * Functia (care este supraincarcata in tile-urile de pe harta) este folosita pentru a verifica daca clickul utilizatorului se afla in unul din vecinii dalei pe care se afla playerul
     * @return returneaza Coordonata dalei care a fost apasata sau coordonata pe care se afla playerul daca click-ul nu a fost pe nici un vecin*/
    public Coordonata CheckNeighbor(Coordonata mouse, Coordonata harta){
        return harta;
    }
    public Coordonata CheckNeighbor(int mousex, int mousey, int mapx, int mapy){
        Coordonata mouse = new Coordonata(mousex,mousey);
        Coordonata map = new Coordonata(mapx,mapy);
        return CheckNeighbor(mouse,map);
    }
    /**
     * @param harta coordonata a dalei a carei ecini vrem sa ii stim
     * @return returneaza o lista cu coordonate ale tuturor vecinilor a dalei data ca parametru*/
    public Coordonata[] GetNeighbors(Coordonata harta){
        return null;
    }
    /**
     * @return returneaza un boolean care semnifica daca tilul pe care a ajuns etitatea este sau nu periculos*/
    public boolean isDangerous(){
        return false;
    }
}
