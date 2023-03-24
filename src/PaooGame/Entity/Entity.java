package PaooGame.Entity;

import PaooGame.Tiles.Tile;
import PaooGame.util.Coordonata;
import java.awt.*;

/**Clasa de baza abstracta care defineste modul de comportare a entitatilor active ale jocului*/
public abstract class Entity {
    protected int ScreenX;
    protected int ScreenY;
    protected int MapX;
    protected int MapY;
    protected Coordonata destination;

    protected boolean Right_Facing =true;
    protected boolean Moving = false;
    protected boolean direction_changed = false;
    protected int direction = -1; /*0 - stanga sus
                                    1 - dreapta sus
                                    2 - dreapta
                                    3 - dreapta jos
                                    4 - stanga jos
                                    5 - stanga
                                   -1 - nu se misca*/

    protected int frames = 0;
    protected int count = 0;

    public abstract void Update();
    public abstract void Draw(Graphics2D g);
    public abstract Coordonata GetScreenPos(int map_x, int map_y);


    public int getMapX() {
        return MapX;
    }
    public int getMapY() {
        return MapY;
    }
    public boolean isMoving(){
        return Moving;
    }
    public void setMoving(boolean moving) {
        Moving = moving;
    }
    public boolean isRight(){
        return Right_Facing;
    }
    public void Direction_changed() {
        direction_changed = true;
    }
    public int getDirection() {
        return direction;
    }
    public Coordonata GetScreenPos(Coordonata map){
        return GetScreenPos(map.getX(),map.getY());
    }
    public Coordonata GetMapPos(){
        return new Coordonata(MapX,MapY);
    }
    public Coordonata GetDestination(){
        return destination;
    }

    public void setDirection(Coordonata direction) {
        if(MapY % 2 == 0){
            if(direction.getX() ==  0 && direction.getY() == -1)this.direction = 1;
            if(direction.getX() ==  1 && direction.getY() ==  0)this.direction = 2;
            if(direction.getX() ==  0 && direction.getY() ==  1)this.direction = 3;
            if(direction.getX() == -1 && direction.getY() ==  1)this.direction = 4;
            if(direction.getX() == -1 && direction.getY() ==  0)this.direction = 5;
            if(direction.getX() == -1 && direction.getY() == -1)this.direction = 0;
        }
        else
        {
            if(direction.getX() ==  1 && direction.getY() == -1)this.direction = 1;
            if(direction.getX() ==  1 && direction.getY() ==  0)this.direction = 2;
            if(direction.getX() ==  1 && direction.getY() ==  1)this.direction = 3;
            if(direction.getX() ==  0 && direction.getY() ==  1)this.direction = 4;
            if(direction.getX() == -1 && direction.getY() ==  0)this.direction = 5;
            if(direction.getX() ==  0 && direction.getY() == -1)this.direction = 0;
        }

        if(direction.getX() ==  0 && direction.getY() ==  0)this.direction = -1;
    }

    public void resetAnimation(){
    }

    public void SetMapLocation(Coordonata harta) {
        MapX = harta.getX();
        MapY = harta.getY();
    }

    public void SetMapDestination(Coordonata harta){
        destination.setX(harta.getX());
        destination.setY(harta.getY());

        Coordonata directie = new Coordonata(harta.getX() - getMapX(),harta.getY() - getMapY());

        setDirection(directie);
    }

    public void updateScreenPos(){
        ScreenX = GetScreenPos(MapX,MapY).getX();
        ScreenY = GetScreenPos(MapX,MapY).getY();
    }

    /**
     * Functie din clasa de baza Entity. Schimba incremental pozitia de pe ecran a entitatii in functie de directia in care trebuie sa se miste */
    public void Move() {
        switch (direction) {
            case 1 -> {
                ScreenX += (Tile.TILE_WIDTH / 2) / 8;
                ScreenY -= (Tile.TILE_HEIGHT - Tile.TILE_STAGGER) / 8;
            }
            case 2 -> ScreenX += Tile.TILE_WIDTH / 8;
            case 3 -> {
                ScreenX += (Tile.TILE_WIDTH / 2) / 8;
                ScreenY += (Tile.TILE_HEIGHT - Tile.TILE_STAGGER) / 8;
            }
            case 4 -> {
                ScreenX -= (Tile.TILE_WIDTH / 2) / 8;
                ScreenY += (Tile.TILE_HEIGHT - Tile.TILE_STAGGER) / 8;
            }
            case 5 -> ScreenX -= Tile.TILE_WIDTH / 8;
            case 0 -> {
                ScreenX -= (Tile.TILE_WIDTH / 2) / 8;
                ScreenY -= (Tile.TILE_HEIGHT - Tile.TILE_STAGGER) / 8;
            }
        }
        if(GetScreenPos(destination).equals(ScreenX,ScreenY)){
            MapX = destination.getX();
            MapY = destination.getY();
            updateScreenPos();
            Moving = false;
        }
    }

    public void checkDirectionChanged(){
        if (direction != -1) {

            if (Right_Facing) {
                switch (direction) {
                    case 0, 5, 4 -> Direction_changed();
                }
            } else {
                switch (direction) {
                    case 1, 2, 3 -> Direction_changed();
                }
            }
            resetAnimation();
            setMoving(true);
        }
    }
}
