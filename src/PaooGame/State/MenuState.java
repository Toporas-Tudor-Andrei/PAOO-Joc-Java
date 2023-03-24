package PaooGame.State;

import PaooGame.EventHandler.EventHandler;
import PaooGame.Game;
import PaooGame.Map.GameItem;
import PaooGame.Map.Map;

import java.awt.*;

/**
 *Clasa de stare concreta care reprezinta Momentul de inceput al jocului, meniul */
public class MenuState extends State{
    private int selected = 0;
    private final String[] options = {"New Game",
                                      "Load Game",
                                      "Exit to Desktop"};


    public MenuState(Game game) {
        super(game);
    }

    @Override
    public void Update() {
        if(EventHandler.up && selected > 0){
            selected--;
            EventHandler.up = false;
        }
        if(EventHandler.down && selected < options.length-1){
            selected++;
            EventHandler.down = false;
        }

        if(EventHandler.enter){
            switch(selected){
                case 0 -> {
                    Map.current_map = 0;
                    for (GameItem item : Map.gameItems[Map.current_map])item.newItem();
                    game.getPlayer().newPlayer();
                    Game.playState.newEnemies();
                    game.gameState.SetState(Game.playState);
                    selected = 0;
                    EventHandler.enter= false;
                }
                case 1 ->{
                    game.gameState.SetState(Game.loadState);
                    Game.loadState.updateOptions();
                    selected = 0;
                    EventHandler.enter = false;
                }
                case 2 ->{
                    EventHandler.enter = false;
                    game.StopGame();
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
        g.drawString("One Step Ahead", (game.GetWidth()/2)-(g.getFontMetrics().charsWidth("One Step Ahead".toCharArray(),0,14)/2),g.getFontMetrics().getHeight()*2);
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
