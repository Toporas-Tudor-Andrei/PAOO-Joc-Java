package PaooGame.util;

/**
 * Clasa utilitara care incapsuleaza 2 numere intregi*/
public class Coordonata {

    private int x, y;

    /**
     * Contructorul care initializeaza membrii privati*/
    public Coordonata(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Contructorul care initializeaza membrii privati*/
    public Coordonata(Coordonata pos) {
        this.x = pos.getX();
        this.y = pos.getY();
    }
/// metode de get si set
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
///metode de verificare a egalitatii
    public boolean equals(int x, int y){
        return (this.x == x && this.y == y);
    }

    public boolean equals(Coordonata pos){
        return (this.x == pos.getX() && this.y == pos.getY());
    }

}
