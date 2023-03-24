package PaooGame.Map;

import PaooGame.Exceptions.MapNotUpdatedException;
import PaooGame.Tiles.Tile;
import PaooGame.util.Coordonata;

import java.awt.*;
import java.io.*;
/**
 * Clasa care Incapsuleaza harta si toate elementele pasive de pe aceasta*/
public class Map {
    private static final String[] maps = {"res/Maps/Map_1.csv","res/Maps/Map_2.csv"};

    public static int[][] map_id = new int[17][16];
    public static int current_map;
    public static final int last_map = maps.length-1;
    public static GameItem[][] gameItems = new GameItem[][]{new GameItem[]{new PortalGI(new Coordonata(15,8))}, new GameItem[]{new PortalGI(new Coordonata(15,10))}};

    private static final Coordonata[] map_default_pos =  new Coordonata[]{new Coordonata(5,3),new Coordonata(4,5)};

    public static Coordonata getMapDefaultPos() {
        return map_default_pos[current_map];
    }

    public static void NextMap(){
        if((maps.length - 1)==(current_map))
            current_map--;
        else current_map++;
    }

    public Map(int map_nr){
        current_map = map_nr;
    }

    /**
     * Functia citeste din fisierele de resurse harta si le incarca in variabila statica map_id */
    public void updateMap(){
        try {
            try {
                String row;
                boolean EvenRow = true;
                int xcoord = 0, ycoord = 0;
                BufferedReader map = new BufferedReader(new FileReader(maps[current_map]));
                while ((row = map.readLine()) != null) {
                    String[] data = row.split(",");

                    for (String datum : data) {
                        map_id[xcoord][ycoord] = Integer.parseInt(datum);
                        xcoord++;
                    }
                    xcoord = 0;
                    ycoord++;
                    EvenRow = !EvenRow;
                }
                map.close();
            } catch (IOException e) {
                throw new MapNotUpdatedException(e.getMessage());
            }
        }catch(MapNotUpdatedException e){
            e.printStackTrace();
        }
    }

    public void Draw(Graphics2D g) {
        updateMap();
        boolean EvenRow = true;
        for(int i = 0; i < map_id[0].length; i++){
            for(int j = 0; j < map_id.length; j++){
                if(map_id[j][i] != -1){
                    if (EvenRow) {
                        Tile.tiles[map_id[j][i]].Draw(g, j * Tile.TILE_WIDTH , i * (Tile.TILE_HEIGHT - Tile.TILE_STAGGER));
                    } else {
                        Tile.tiles[map_id[j][i]].Draw(g, j * Tile.TILE_WIDTH + Tile.TILE_WIDTH/2, i * (Tile.TILE_HEIGHT - Tile.TILE_STAGGER));
                    }
                }
            }
            EvenRow = !EvenRow;
        }
        for(GameItem item : gameItems[current_map]) item.Draw(g);
    }
}
