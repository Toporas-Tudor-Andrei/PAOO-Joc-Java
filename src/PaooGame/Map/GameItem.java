package PaooGame.Map;

import PaooGame.util.Coordonata;

import java.awt.*;

/**
 * Clasa abstracta care incapsuleaza elementele pasive ale hartii*/
public abstract class GameItem {

    protected Coordonata Map;
    protected Coordonata Screen;
    protected boolean open = false;

    public GameItem(Coordonata map){
        Map = new Coordonata(map);
        Screen = new Coordonata(getScreenPos());
    }

    public Coordonata getMap() {
        return Map;
    }

    public Coordonata getScreen() {
        return Screen;
    }

    public void activate(){
        open = true;
    }

    public boolean isActive() {
        return open;
    }

    public void deactivate(){
        open = false;
    }

    public abstract void Update();
    public abstract void Draw(Graphics2D g);
    public abstract Coordonata getScreenPos();
    public abstract void newItem();

}
