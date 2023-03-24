package PaooGame.State;

import PaooGame.EventHandler.EventHandler;
import PaooGame.Game;
import PaooGame.util.DataBase;

import java.awt.*;

/**
 * Clasa de stare concreta care reprezinta o oprire a timpului in joc*/
public class PausedState extends State {
    private int selected = 0;
    private String saved = "";
    private final String[] options = {"Resume",
                                      "Save",
                                      "Main Menu"};

    public PausedState(Game game){
        super(game);
    }

    @Override
    public void Update() {
        if(EventHandler.esc){
            game.gameState.SetState(Game.playState);
            saved = "";
            selected = 0;
            EventHandler.esc = false;
        }
        if(EventHandler.up && selected > 0){
            saved = "";
            selected--;
            EventHandler.up = false;
        }
        if(EventHandler.down && selected < options.length-1){
            saved = "";
            selected++;
            EventHandler.down = false;
        }
        if(EventHandler.enter){
            switch(selected){
                case 0 -> {
                    game.gameState.SetState(Game.playState);
                    saved = "";
                    selected = 0;
                    EventHandler.enter= false;
                }
                case 1 ->{
                    //save in data base
                    saved = DataBase.SaveGame();
                    EventHandler.enter = false;
                }
                case 2 ->{
                    game.gameState.SetState(Game.menuState);
                    saved = "";
                    selected = 0;
                    EventHandler.enter = false;
                }
            }
        }
    }

    @Override
    public void Draw(Graphics2D g) {
        game.gameState.GetPrevState().Draw(g);
        g.setColor(new Color(43,48,59,212));
        g.fill(new Rectangle(0,0,game.GetWidth(), game.GetHeight()));

        g.setColor(Color.WHITE);
        g.drawString("PAUSED", (game.GetWidth()/2)-50,g.getFontMetrics().getHeight()*2);

        for(int i = 0; i < options.length; i++){
            if(i == 1){
                g.setColor(Color.WHITE);
                g.drawString(saved, game.GetWidth() / 5 + g.getFontMetrics().charsWidth(options[i].toCharArray(), 0, options[i].length()), g.getFontMetrics().getHeight() * (5 + i));
            }

            if(i == selected){
                g.setColor(Color.GREEN);
                g.drawString(options[i],game.GetWidth()/5,g.getFontMetrics().getHeight()*(5+i));
                continue;
            }
            g.setColor(Color.WHITE);
            g.drawString(options[i],game.GetWidth()/5,g.getFontMetrics().getHeight()*(5+i));
        }
    }
}
