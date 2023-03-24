package PaooGame.Graphics;

import java.awt.image.BufferedImage;

/** Clasa retine o referinta catre o imagine formata din dale (sprite sheet)
    <p>
    Metoda crop() returneaza o dala de dimensiuni fixe (o subimagine) din sprite sheet
    de la adresa<p>(x * latimeDala, y * inaltimeDala)
 */
public class SpriteSheet
{
    private BufferedImage       spriteSheet;
    private static final int    tileWidth   = 32;
    private static final int    tileHeight  = 54;
    private static final int    PlayertileWidth   = 24;
    private static final int    PlayertileHeight  = 24;
    private static final int    EnemyWidth = 24;
    private static final int    EnemyHeight = 18;
    private static final int    PortalWidth = 32;
    private static final int    PortalHeight = 32;

    /** Constructor, initializeaza spriteSheet.

        @param buffImg Un obiect BufferedImage valid.
     */
    public SpriteSheet(BufferedImage buffImg)
    {
            /// Retine referinta catre BufferedImage object.
        spriteSheet = buffImg;
    }

    /** Returneaza un obiect BufferedImage ce contine o subimage (dala).
        <p>
        Subimaginea este localizata avand ca referinta punctul din stanga sus.

        @param x numarul dalei din sprite sheet pe axa x.
        @param y numarul dalei din sprite sheet pe axa y.
     */
    public BufferedImage cropTile(int x, int y)
    {
            /// Subimaginea (dala) este regasita in sprite sheet specificad coltul stanga sus
            /// al imaginii si apoi latimea si inaltimea (totul in pixeli). Coltul din stanga sus al imaginii
            /// se obtine inmultind numarul de ordine al dalei cu dimensiunea in pixeli a unei dale.
        return spriteSheet.getSubimage(x * tileWidth, y * tileHeight, tileWidth, tileHeight);
    }

    /** Returneaza un obiect BufferedImage ce contine o subimage (Player).
     <p>
     Subimaginea este localizata avand ca referinta punctul din stanga sus.

     @param x numarul dalei din sprite sheet pe axa x.
     @param y numarul dalei din sprite sheet pe axa y.
     */
    public BufferedImage cropPlayer(int x, int y)
    {
        /// Subimaginea (dala) este regasita in sprite sheet specificad coltul stanga sus
        /// al imaginii si apoi latimea si inaltimea (totul in pixeli). Coltul din stanga sus al imaginii
        /// se obtine inmultind numarul de ordine al dalei cu dimensiunea in pixeli a unei dale.
        return spriteSheet.getSubimage(x * PlayertileWidth, y * PlayertileHeight, PlayertileWidth, PlayertileHeight);
    }

    /** Returneaza un obiect BufferedImage ce contine o subimage (Inamic).
     <p>
     Subimaginea este localizata avand ca referinta punctul din stanga sus.

     @param x numarul dalei din sprite sheet pe axa x.
     @param y numarul dalei din sprite sheet pe axa y.
     */
    public BufferedImage crope(int x, int y)
    {
        /// Subimaginea (dala) este regasita in sprite sheet specificad coltul stanga sus
        /// al imaginii si apoi latimea si inaltimea (totul in pixeli). Coltul din stanga sus al imaginii
        /// se obtine inmultind numarul de ordine al dalei cu dimensiunea in pixeli a unei dale.
        return spriteSheet.getSubimage(x * EnemyWidth, y * EnemyHeight, EnemyWidth, EnemyHeight);
    }

    /** Returneaza un obiect BufferedImage ce contine o subimage (Iteme pasive).
     <p>
     Subimaginea este localizata avand ca referinta punctul din stanga sus.

     @param x numarul dalei din sprite sheet pe axa x.
     @param y numarul dalei din sprite sheet pe axa y.
     */
    public BufferedImage cropPortal(int x, int y)
    {
        /// Subimaginea (dala) este regasita in sprite sheet specificad coltul stanga sus
        /// al imaginii si apoi latimea si inaltimea (totul in pixeli). Coltul din stanga sus al imaginii
        /// se obtine inmultind numarul de ordine al dalei cu dimensiunea in pixeli a unei dale.
        return spriteSheet.getSubimage(x * PortalWidth, y * PortalHeight, PortalWidth, PortalHeight);
    }
}
