package PaooGame.State;

import PaooGame.Game;

import java.awt.*;

/**
 * Clasa abstracta care defineste starea in care se poate afla jocul la un moment dat*/
public abstract class State {
    protected State current_state;
    protected State previous_state;
    protected Game game;

    /**
     * Constructorul care primeste o referinta la jocul curent
     * @param game referinta la joc*/
    public State(Game game){
        this.game = game;
        current_state = game.gameState;
    }

    public State GetState(){
        return this.current_state;
    }

    public State GetPrevState(){
        return this.previous_state;
    }

    public void SetState(State gamestate){
        previous_state = current_state;
        current_state = gamestate;
    }

    public abstract void Update();
    public abstract void Draw(Graphics2D g);
}
