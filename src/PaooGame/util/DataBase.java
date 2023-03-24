package PaooGame.util;

import PaooGame.Entity.Enemy;
import PaooGame.Game;
import PaooGame.Map.Map;

import java.sql.*;

/**
 * Este clasa intermediara de utilitate care face accesul la baza de date, pentru incarcarea, salvarea si stergerea datelor
 * <p> Are doar membri si metode statice*/
public class DataBase {
    private static Game game;
    private static Connection c;

    /**
     * Seteaza referinta la jocul care ruleaza
     * @param instance instanta jocului*/
    public static void setGame(Game instance){
        game = instance;
    }

    /**
     * Functia care sterge un row de date din baza de date
     * @param row reprezinta ID-ul intrarii care urmeaza a fii strearsa*/
    public static void DeleteLoad(int row){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/DataBase/PAOODataBase.db");
            c.setAutoCommit(false);
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }

        try{
            Statement stm = c.createStatement();

            stm.executeUpdate("DELETE from Player where ID=" + row + ";");
            stm.executeUpdate("DELETE from Enemy0 where ID=" + row + ";");
            stm.executeUpdate("DELETE from Enemy1 where ID=" + row + ";");
            stm.executeUpdate("DELETE from Portal where ID=" + row + ";");
            c.commit();
            stm.close();
            c.close();
            c = null;
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Functia care incarca o stare salvata a jocului din baza de date
     * @param row reprezinta ID-l de la care se vor lua datele din baza de date*/
    public static void LoadGame(int row){
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/DataBase/PAOODataBase.db");
            c.setAutoCommit(true);
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }

        try{
            Statement stm = c.createStatement();
            ResultSet rs = stm.executeQuery("SELECT * FROM Player where ID=" + row + ";");
            Map.current_map = rs.getInt("Map");
            game.getPlayer().SetMapLocation(new Coordonata(rs.getInt("X"),rs.getInt("Y")));
            game.getPlayer().SetMapDestination(new Coordonata(rs.getInt("X"),rs.getInt("Y")));
            game.getPlayer().updateScreenPos();
            game.getPlayer().setScore(rs.getInt("score"));
            game.getPlayer().setLives(rs.getInt("lives"));
            if(game.getPlayer().getLives() > 0) game.getPlayer().setLiving();

            rs = stm.executeQuery("SELECT * FROM Enemy0 where ID=" + row + ";");
            Game.playState.enemy_list[0][0].SetMapLocation(new Coordonata(rs.getInt("X1"),rs.getInt("Y1")));
            Game.playState.enemy_list[0][0].setType(rs.getInt("Type1"));
            Game.playState.enemy_list[0][1].SetMapLocation(new Coordonata(rs.getInt("X2"),rs.getInt("Y2")));
            Game.playState.enemy_list[0][1].setType(rs.getInt("Type2"));

            rs = stm.executeQuery("SELECT * FROM Enemy1 where ID=" + row + ";");
            Game.playState.enemy_list[1][0].SetMapLocation(new Coordonata(rs.getInt("X1"),rs.getInt("Y1")));
            Game.playState.enemy_list[1][0].setType(rs.getInt("Type1"));
            Game.playState.enemy_list[1][1].SetMapLocation(new Coordonata(rs.getInt("X2"),rs.getInt("Y2")));
            Game.playState.enemy_list[1][1].setType(rs.getInt("Type2"));
            Game.playState.enemy_list[1][2].SetMapLocation(new Coordonata(rs.getInt("X3"),rs.getInt("Y3")));
            Game.playState.enemy_list[1][2].setType(rs.getInt("Type3"));

            for(Enemy[] list: Game.playState.enemy_list){
                for(Enemy enemy:list){
                    enemy.SetMapDestination(enemy.GetMapPos());
                    enemy.updateScreenPos();
                    if(enemy.GetMapPos().equals(0,0)) enemy.execute();
                }
            }

            rs = stm.executeQuery("SELECT * FROM Portal where ID=" + row + ";");
            if(rs.getInt("Active0") == 1) Map.gameItems[0][0].activate();
            else  Map.gameItems[0][0].deactivate();
            if(rs.getInt("Active1") == 1) Map.gameItems[1][0].activate();
            else  Map.gameItems[1][0].deactivate();

            stm.close();
            c.close();
            c= null;
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Functia care salveaza starea curenta a jocului in baza de date
     * @return returneaza un String care semnifica modul in care a fost desfasurata salvarea*/
    public static String SaveGame(){
        Game.loadState.updateOptions();
        if(Game.loadState.entries[Game.loadState.entries.length - 1] != -1) return " - cannot save, too many saves";
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/DataBase/PAOODataBase.db");
            c.setAutoCommit(true);
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
        try {
            Statement stm = c.createStatement();
            int row = 1;
            for(int i = 0; i < Game.loadState.entries.length; i++) {
                if(Game.loadState.entries[i] != -1) row = Game.loadState.entries[i] + 1;
                else break;
            }

            String sql = "INSERT INTO Player (ID,Map,X,Y,score,lives) " +
                    "VALUES (" + row + ", " + Map.current_map + ", " + game.getPlayer().GetMapPos().getX() + ", " + game.getPlayer().GetMapPos().getY() + ", " +
                    game.getPlayer().getScore() + ", " + game.getPlayer().getLives() + " );";
            stm.executeUpdate(sql);

            sql = "INSERT INTO Enemy0 (ID,X1,Y1,Type1,X2,Y2,Type2) " +
            "VALUES (" + row + ", " + Game.playState.enemy_list[0][0].getMapX() + ", " + Game.playState.enemy_list[0][0].getMapY() + ", " + Game.playState.enemy_list[0][0].getType() + ", " +
                    Game.playState.enemy_list[0][1].getMapX() + ", " + Game.playState.enemy_list[0][1].getMapY() + ", " + Game.playState.enemy_list[0][1].getType() + " );";
            stm.executeUpdate(sql);

            sql = "INSERT INTO Enemy1 (ID,X1,Y1,Type1,X2,Y2,Type2,X3,Y3,Type3) " +
                    "VALUES (" + row + ", " + Game.playState.enemy_list[1][0].getMapX() + ", " + Game.playState.enemy_list[1][0].getMapY() + ", " + Game.playState.enemy_list[1][0].getType() + ", " +
                    Game.playState.enemy_list[1][1].getMapX() + ", " + Game.playState.enemy_list[1][1].getMapY() + ", " + Game.playState.enemy_list[1][1].getType() + ", " +
                    Game.playState.enemy_list[1][2].getMapX() + ", " + Game.playState.enemy_list[1][2].getMapY() + ", " + Game.playState.enemy_list[1][2].getType() + " );";
            stm.executeUpdate(sql);


            int Active0, Active1;
            if(Map.gameItems[0][0].isActive())Active0 = 1;
            else Active0 = 0;

            if(Map.gameItems[1][0].isActive())Active1 = 1;
            else Active1 = 0;

            sql = "INSERT INTO Portal (ID,Active0,Active1) " +
                    "VALUES (" + row  + ", " + Active0  + ", " + Active1 + " );";
            stm.executeUpdate(sql);

            stm.close();
            c.close();
            c = null;
            return " - saved successfully";
        } catch (SQLException e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
        return " - Exeption was thrown";
    }
}
