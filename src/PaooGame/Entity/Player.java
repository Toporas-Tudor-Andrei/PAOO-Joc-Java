package PaooGame.Entity;

import PaooGame.Graphics.Assets;
import PaooGame.Map.Map;
import PaooGame.Tiles.Tile;
import PaooGame.util.Coordonata;
import java.awt.*;

/**
 * Clasa concreta care reprezinta eroul controlat de jucator*/
public class Player extends Entity{
    private static final int Player_width = 24 * Tile.TILE_SCALE;
    private static final int Player_height = 24 * Tile.TILE_SCALE;

    private int score = 0;
    private int lives = 3;
    private boolean living = true;

    public Player(){
        MapX = Map.getMapDefaultPos().getX();
        MapY = Map.getMapDefaultPos().getY();

        destination = new Coordonata(MapX,MapY);

        ScreenX = GetScreenPos(MapX,MapY).getX();
        ScreenY = GetScreenPos(MapX,MapY).getY();
    }

    public void newPlayer(){
        MapX = Map.getMapDefaultPos().getX();
        MapY = Map.getMapDefaultPos().getY();

        destination.setX(MapX);
        destination.setY(MapY);

        ScreenX = GetScreenPos(MapX,MapY).getX();
        ScreenY = GetScreenPos(MapX,MapY).getY();
        Moving = false;
        score = 0;
        lives = 3;
        living = true;
    }

    public int getScore(){
        return score;
    }

    public void setScore(int score){
        this.score = score;
    }

    public int getLives(){
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public void setLiving(){
        living = true;
    }

    public void loseLife(int lives){
        this.lives -= lives;
        if(this.lives <= 0) die();
    }

    public void die(){
        living = false;
    }

    public boolean isDead(){
        return !living;
    }

    public void updateAnimation(int frequency, int noFrames){
        count++;
        if (count == frequency) {
            frames++;
            if (frames == noFrames) frames = 0;
            count = 0;
        }
    }

    public void earnScore(int points){
        score += points;
        if(points != 0) System.out.println("Player has earned "+ points +" points he now has a total score of: "+ score);
    }

    @Override
    public void resetAnimation(){
        count = 0;
        frames = 0;
    }

    @Override
    public Coordonata GetScreenPos(int map_x, int map_y){
        int x,y;
        if(map_y % 2 !=0){
            x = 8             + (Tile.TILE_WIDTH/2) + Tile.TILE_WIDTH * map_x;
              /*space for     | space for
                centering the | the fitting
                player on     | of the tiles
                the tile      | */
        }
        else
            x = 8 + Tile.TILE_WIDTH * map_x;
        y = 8 + (Tile.TILE_HEIGHT - Tile.TILE_STAGGER) * map_y;
        return new Coordonata(x,y);
    }

    @Override
    public void Update() {
        //movement update
        if(Moving && !direction_changed){
           Move();
        }
        else
        {
            if(Tile.tiles[Map.map_id[MapX][MapY]].isDangerous()){
                loseLife(1);
                if(!isDead()) {
                    SetMapLocation(Map.getMapDefaultPos()); //default map position
                    updateScreenPos();
                }
                Moving = false;

            }
        }
        // animation frame update
        if(!Moving) updateAnimation(11, 3);
        else
        {
            if(direction_changed){
                updateAnimation(1,4);
                if (frames == 0 && count == 0) {
                    direction_changed = false;
                    Right_Facing = !Right_Facing;
                }
            }
            else updateAnimation(8,3);
        }
    }

    @Override
    public void Draw(Graphics2D g) {
        /*every second there are 60 frames
        * the animations of the player are nice at roughly 175 ms per frame
        * so that means every frame of the animation will stay on screen for 10 in game frames
        * at 4 frames per animation */
        g.setColor(Color.WHITE);
        g.drawString("LIVES: " + lives, 0,g.getFontMetrics().getHeight());
        g.drawString("SCORE: " + score,0,g.getFontMetrics().getHeight()*2);
        if(!Moving)
        {
            if(Right_Facing) g.drawImage(Assets.Player_idle_right[frames], ScreenX, ScreenY,Player_width,Player_height,null);
            else g.drawImage(Assets.Player_idle_left[frames], ScreenX, ScreenY,Player_width,Player_height,null);
        }
        else
        {
            if(direction_changed){
                if(Right_Facing)g.drawImage(Assets.Player_turn_right_left[frames], ScreenX, ScreenY,Player_width,Player_height,null);
                else g.drawImage(Assets.Player_turn_left_right[frames], ScreenX, ScreenY,Player_width,Player_height,null);
            }
            else
            {
                if(Right_Facing)g.drawImage(Assets.Player_run_right[frames], ScreenX, ScreenY,Player_width,Player_height,null);
                else g.drawImage(Assets.Player_run_left[frames], ScreenX, ScreenY,Player_width,Player_height,null);
            }
        }
    }
}
