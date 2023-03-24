package PaooGame.State;

import PaooGame.EventHandler.EventHandler;
import PaooGame.Game;
import PaooGame.util.DataBase;

import java.awt.*;
import java.sql.*;

/**
 * Clasa de stare concreta care reprezinta momentul de incarcare/stergerea a unei salvari de joc din baza de date*/
public class LoadState extends State {

    private String[] options;
    private String[] loaded = {" - Empty"," - Empty"," - Empty"," - Empty"," - Empty"};
    private String[] toDelete = {"","","","",""};
    private int selected = 0;
    public int[] entries = {-1,-1,-1,-1,-1};
    private boolean delete;

    private Connection c;

    public LoadState(Game game){
        super(game);
        updateOptions();
        options = new String[]{"1." + loaded[0], "2." + loaded[1], "3." + loaded[2], "4."+ loaded[3], "5."+ loaded[4]};
    }

    /**
     * Functia Updateaza lista de String-uri care vor fi desenate pe ecran*/
    public void updateOptions(){
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

            ResultSet rs = stm.executeQuery("SELECT * FROM Player;");
            entries = new int[]{-1, -1, -1, -1, -1};
            for(int i = 0; i < entries.length; i++){
                if(rs.next()){
                    entries[i] = rs.getInt("ID");
                }
            }
            loaded = new String[]{" - Empty", " - Empty", " - Empty", " - Empty", " - Empty"};
            for(int i = 0; i < entries.length; i++){
                if(entries[i] != -1) loaded[i] = " - load" + entries[i];
            }
            stm.close();
            c.close();
            c = null;
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
        options = new String[]{"1." + loaded[0], "2." + loaded[1], "3." + loaded[2], "4."+ loaded[3], "5."+ loaded[4]};
    }

    public void updateDelete(){
        options = new String[]{"1." + loaded[0] + toDelete[0], "2." + loaded[1] + toDelete[1], "3." + loaded[2] + toDelete[2], "4."+ loaded[3] + toDelete[3], "5."+ loaded[4] + toDelete[4]};
    }

    @Override
    public void Update() {
        if(EventHandler.up && selected > 0){
            toDelete[selected] = "";
            delete = false;
            updateDelete();
            selected--;
            EventHandler.up = false;
        }
        if(EventHandler.down && selected < options.length-1){
            toDelete[selected] = "";
            delete = false;
            updateDelete();
            selected++;
            EventHandler.down = false;
        }
        if(EventHandler.right){
            toDelete[selected] = " - Delete?";
            delete = true;
            updateDelete();
        }
        if(EventHandler.esc){
            game.gameState.SetState(Game.menuState);
            toDelete[selected] = "";
            delete = false;
            updateDelete();
            selected = 0;
            EventHandler.esc = false;
        }

        if(EventHandler.enter){
            switch(selected){
                case 0 -> {
                    if(delete) {
                        DataBase.DeleteLoad(entries[0]);
                        loaded = new String[]{" - Empty", " - Empty", " - Empty", " - Empty", " - Empty"};
                        updateOptions();
                    }
                    else if(entries[selected] != -1){
                        DataBase.LoadGame(entries[0]);
                        game.gameState.SetState(Game.playState);
                    }
                    EventHandler.enter= false;
                }
                case 1 ->{
                    if(delete) {
                        DataBase.DeleteLoad(entries[1]);
                        loaded = new String[]{" - Empty", " - Empty", " - Empty", " - Empty", " - Empty"};
                        updateOptions();
                    }
                    else if(entries[selected] != -1){
                        DataBase.LoadGame(entries[1]);
                        game.gameState.SetState(Game.playState);
                    }
                    EventHandler.enter = false;
                }
                case 2 ->{
                    if(delete) {
                        DataBase.DeleteLoad(entries[2]);
                        loaded = new String[]{" - Empty", " - Empty", " - Empty", " - Empty", " - Empty"};
                        updateOptions();
                    }
                    else if(entries[selected] != -1){
                        DataBase.LoadGame(entries[2]);
                        game.gameState.SetState(Game.playState);
                    }
                    EventHandler.enter = false;
                }
                case 3 ->{
                    if(delete) {
                        DataBase.DeleteLoad(entries[3]);
                        loaded = new String[]{" - Empty", " - Empty", " - Empty", " - Empty", " - Empty"};
                        updateOptions();
                    }
                    else if(entries[selected] != -1){
                        DataBase.LoadGame(entries[3]);
                        game.gameState.SetState(Game.playState);
                    }
                    EventHandler.enter = false;
                }
                case 4 ->{
                    if(delete) {
                        DataBase.DeleteLoad(entries[4]);
                        loaded = new String[]{" - Empty", " - Empty", " - Empty", " - Empty", " - Empty"};
                        updateOptions();
                    }
                    else if(entries[selected] != -1){
                        DataBase.LoadGame(entries[4]);
                        game.gameState.SetState(Game.playState);
                    }
                    EventHandler.enter = false;
                }
            }
        }
    }

    @Override
    public void Draw(Graphics2D g) {
        g.setColor(new Color(43,48,59,255));
        g.fill(new Rectangle(0,0,game.GetWidth(), game.GetHeight()));

        int size = g.getFont().getSize();
        Font font = g.getFont();

        g.setColor(Color.WHITE);
        g.setFont(new Font(g.getFont().getFontName(),Font.PLAIN,size*3));
        g.drawString("Load a Save", (game.GetWidth()/2)-(g.getFontMetrics().charsWidth("Load a Save".toCharArray(),0,11)/2),g.getFontMetrics().getHeight()*2);
        g.setFont(font);

        for(int i = 0; i < options.length; i++){
            if(i == selected){
                g.setColor(Color.GREEN);
                g.drawString(options[i],game.GetWidth()/10,g.getFontMetrics().getHeight()*(7+i));
                continue;
            }
            g.setColor(Color.WHITE);
            g.drawString(options[i],game.GetWidth()/10,g.getFontMetrics().getHeight()*(7+i));
        }
    }
}
