package PaooGame.Entity;

import PaooGame.Graphics.Assets;
import PaooGame.Map.Map;
import PaooGame.Tiles.Tile;
import PaooGame.util.Coordonata;

import java.awt.*;

/**
 * Clasa concreta care reprezinta Inamicul controlat de calculator*/
public class Enemy extends Entity {
    private static final int Enemy_width = 24 * Tile.TILE_SCALE;
    private static final int Enemy_height = 18 * Tile.TILE_SCALE;

    private int type;
    private int health = 1;
    private boolean alive = true;
    private int scoreDrop = 0;

    public Enemy(int type){
        this.type = type;
        destination = new Coordonata(0,0);
        Right_Facing = false;
    }

    public void newEnemy(){
        health = 1;
        alive = true;
        scoreDrop = 0;
    }

    public int getType(){
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void takeDamage(int dmg){
        health -= dmg;
        if(health <= 0) die();
    }

    public void execute(){
        alive = false;
    }

    public void die(){
        alive = false;
        MapX = 0;
        MapY = 0;
        destination.setX(0);
        destination.setY(0);
        scoreDrop++;
    }

    public int takeDrops(){
        int drp = scoreDrop;
        scoreDrop = 0;
        return drp;
    }

    public boolean isDead() {
        return !alive;
    }

    public void updateAnimation(int frequency, int direction){
        count++;
        if (count == frequency){
            if(direction == 1){
                if(frames == 3) return;
                frames++;
                count = 0;
            }
            if(direction == 0){
                if(frames == 0) return;
                frames--;
                count = 0;
            }
        }
    }

    @Override
    public void Update() {
        if(isDead()) return;
        if (Moving && !direction_changed) {
            super.Move();
        }
        if (direction_changed) {
            if (Right_Facing) {
                updateAnimation(1, 0);
                if (frames == 0) {
                    direction_changed = false;
                    Right_Facing = false;
                }
            } else {
                updateAnimation(1, 1);
                if (frames == 3) {
                    direction_changed = false;
                    Right_Facing = true;
                }
            }
        }

        if (Tile.tiles[Map.map_id[MapX][MapY]].isDangerous()) {
            takeDamage(1);
        }
    }

    @Override
    public void Draw(Graphics2D g) {
        if(alive) g.drawImage(Assets.enemies[type][frames],ScreenX,ScreenY,Enemy_width,Enemy_height,null);
    }

    @Override
    public Coordonata GetScreenPos(int map_x, int map_y) {
        int x,y;
        if(map_y % 2 !=0){
            x = 8             + (Tile.TILE_WIDTH/2) + Tile.TILE_WIDTH * map_x;
              /*space for     | space for
                centering the | the fitting
                player on     | of the tiles
                the tile      | */
        }
        else x = 8 + Tile.TILE_WIDTH * map_x;
        y = 24 + (Tile.TILE_HEIGHT - Tile.TILE_STAGGER) * map_y;
        return new Coordonata(x,y);
    }

}
