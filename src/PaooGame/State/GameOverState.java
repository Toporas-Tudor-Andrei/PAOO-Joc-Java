package PaooGame.State;

import PaooGame.EventHandler.EventHandler;
import PaooGame.Game;

import java.awt.*;

/**
 * Clasa de stare concreta care reprezinta momentul de final al jocului in cazul in care eroul moare*/
public class GameOverState extends State {

    public GameOverState(Game game){
        super(game);
    }

    @Override
    public void Update() {
        if(EventHandler.enter){
            game.gameState.SetState(Game.menuState);
            EventHandler.enter= false;
        }
    }

    @Override
    public void Draw(Graphics2D g) {
        game.gameState.GetPrevState().Draw(g);
        g.setColor(new Color(43,48,59,212));
        g.fill(new Rectangle(0,0,game.GetWidth(), game.GetHeight()));

        int size = g.getFont().getSize();
        Font font = g.getFont();

        g.setColor(Color.WHITE);
        g.setFont(new Font(g.getFont().getFontName(),Font.PLAIN,size*4));
        g.drawString("GAME OVER", (game.GetWidth()/2)-(g.getFontMetrics().charsWidth("GAME OVER".toCharArray(),0,9)/2),g.getFontMetrics().getHeight()*2);
        g.setFont(font);

        g.setColor(Color.GREEN);
        g.drawString("Main Menu", (game.GetWidth()/2)-(g.getFontMetrics().charsWidth("Main Menu".toCharArray(),0,9)/2),g.getFontMetrics().getHeight()*15);


    }
}
