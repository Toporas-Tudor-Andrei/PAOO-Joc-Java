package PaooGame.State;

import static PaooGame.EventHandler.EventHandler.*;

import PaooGame.Entity.Enemy;
import PaooGame.EventHandler.EventHandler;
import PaooGame.Game;
import PaooGame.Map.GameItem;
import PaooGame.Map.Map;
import PaooGame.Tiles.Tile;
import PaooGame.util.Coordonata;

import java.awt.*;
import java.util.Arrays;

/**
 * Clasa de stare concreta care reprezinta momentul in care eroul poate fi controlat de jucator*/
public class PlayState extends State {

    public Enemy[][] enemy_list;

    public PlayState(Game game){
        super(game);
        enemy_list = new Enemy[][]{new Enemy[]{new Enemy(0),new Enemy(1)},

                                   new Enemy[]{new Enemy(6),new Enemy(7),new Enemy(8)}};

        enemy_list[0][0].SetMapLocation(new Coordonata(13,8));
        enemy_list[0][1].SetMapLocation(new Coordonata(8,10));
        enemy_list[1][0].SetMapLocation(new Coordonata(13,7));
        enemy_list[1][1].SetMapLocation(new Coordonata(8,1));
        enemy_list[1][2].SetMapLocation(new Coordonata(5,13));
        for(Enemy[] list: enemy_list){
            for(Enemy enemy: list){
                enemy.SetMapDestination(enemy.GetMapPos());
                enemy.updateScreenPos();
            }
        }
    }

    /**
     * Functia reseteaza Starea tuturor inamicilor la cea de default*/
    public void newEnemies(){
        enemy_list[0][0].SetMapLocation(new Coordonata(13,8));
        enemy_list[0][1].SetMapLocation(new Coordonata(8,10));
        enemy_list[1][0].SetMapLocation(new Coordonata(13,7));
        enemy_list[1][1].SetMapLocation(new Coordonata(8,1));
        enemy_list[1][2].SetMapLocation(new Coordonata(5,13));
        for(Enemy[] list: enemy_list){
            for(Enemy enemy: list){
                enemy.SetMapDestination(enemy.GetMapPos());
                enemy.updateScreenPos();
                enemy.newEnemy();
            }
        }
    }

    /**
     * Functia seteaza destinatia si celelalte flaguri pentru a determina miscarea inamicului, fiind in esenta inteligenta sa artificiala*/
    public void UpdateEnemy(){
        for(Enemy enemy : enemy_list[Map.current_map]){
            if(enemy.isDead()) continue;
            Coordonata[] vecini = Tile.tiles[Map.map_id[enemy.getMapX()][enemy.getMapY()]].GetNeighbors(enemy.GetMapPos());
            for (Coordonata vecin : vecini) {
                for (Enemy enem : enemy_list[Map.current_map]) {
                    if (enem == enemy) continue;
                    if (vecin != null && vecin.equals(enem.GetDestination())) vecin = null;
                }
            }

            boolean hasDest = false;
            double chance = 0.1666;
            while (!hasDest) {
                for (Coordonata vecin : vecini) {
                    if (vecin != null) {
                        if (Math.random() < (chance += 0.1666)) {
                            enemy.SetMapDestination(vecin);
                            enemy.checkDirectionChanged();
                            hasDest = true;
                            chance = 0.1666;
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Functia interogheaza clasa de tratare a evenimentelor si ia decizii bazate acestea*/
    public void HandleInput(){
        if(!game.getPlayer().isMoving() && clicked){
            Coordonata pozitie = Tile.tiles[Map.map_id[game.getPlayer().getMapX()][game.getPlayer().getMapY()]]
                    .CheckNeighbor(mouse.getX(), mouse.getY(), game.getPlayer().getMapX(), game.getPlayer().getMapY());
            game.getPlayer().SetMapDestination(pozitie);
            game.getPlayer().checkDirectionChanged();
            if(game.getPlayer().isMoving()) UpdateEnemy();
        }

        if(EventHandler.esc){
            game.gameState.SetState(Game.pausedState);
            EventHandler.esc = false;
        }
    }

    /**
     * Functia de update inplementata*/
    @Override
    public void Update() {
        HandleInput();

        game.getPlayer().Update();
        for(Enemy enemy: enemy_list[Map.current_map]){
            enemy.Update();
            //checking attacks
            if (game.getPlayer().GetDestination().equals(enemy.GetDestination())) enemy.takeDamage(1);
            if(game.getPlayer().GetDestination().equals(enemy.GetMapPos()) && game.getPlayer().GetMapPos().equals(enemy.GetDestination())) enemy.takeDamage(1);
            if(enemy.isDead()) game.getPlayer().earnScore(enemy.takeDrops());
        }
        for(GameItem item :Map.gameItems[Map.current_map]){
            item.Update();
        }

        if(game.getPlayer().isDead()) game.gameState.SetState(Game.gameOverState);

        if(Map.gameItems[Map.current_map][0].isActive() && game.getPlayer().GetMapPos().equals(Map.gameItems[Map.current_map][0].getMap())) {
            if(Map.current_map == Map.last_map) {
                game.gameState.SetState(Game.youWinState);
                return;
            }
            Map.NextMap();
            game.getPlayer().SetMapLocation(Map.getMapDefaultPos());
            game.getPlayer().updateScreenPos();
            game.getPlayer().setMoving(false);
            game.GetMap().updateMap();
        }

        if(Arrays.stream(enemy_list[Map.current_map]).allMatch(Enemy::isDead)){
            for(GameItem item :Map.gameItems[Map.current_map]){
                item.activate();
            }
        }
    }

    /**
     * Functia de draw implementata*/
    @Override
    public void Draw(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.fill(new Rectangle(0,0,game.GetWidth(),game.GetHeight()));
        game.GetMap().Draw(g);
        game.getPlayer().Draw(g);
        for(Enemy enemy: enemy_list[Map.current_map]){
            enemy.Draw(g);
        }
    }
}
