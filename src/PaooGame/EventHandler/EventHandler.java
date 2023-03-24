package PaooGame.EventHandler;

import PaooGame.util.Coordonata;
import java.awt.event.*;

/**
 * Clasa care furnizeaza niste variabile boolean statice si publice care reprezinta starea de apasare a unor butoane de pe tastatura sau mouse*/
public class EventHandler implements MouseListener , KeyListener {

    public static Coordonata mouse = new Coordonata(0,0);
    public static boolean clicked;
    public static boolean esc = false;
    public static boolean enter = false;
    public static boolean up = false;
    public static boolean down = false;
    public static boolean right = false;

    public void mouseClicked(MouseEvent mouseEvent) {
    }

    public void mousePressed(MouseEvent mouseEvent) {
        mouse.setX(mouseEvent.getX());
        mouse.setY(mouseEvent.getY());
        clicked = true;
    }

    public void mouseReleased(MouseEvent mouseEvent) {
        clicked = false;
    }

    public void mouseEntered(MouseEvent mouseEvent) {

    }

    public void mouseExited(MouseEvent mouseEvent) {

    }

    public void keyTyped(KeyEvent keyEvent) {

    }

    public void keyPressed(KeyEvent keyEvent) {
        if(keyEvent.getExtendedKeyCode() == KeyEvent.VK_ESCAPE){
            esc =true;
        }
        if(keyEvent.getExtendedKeyCode() == KeyEvent.VK_ENTER){
            enter = true;
        }
        if(keyEvent.getExtendedKeyCode() == KeyEvent.VK_UP){
            up = true;
        }
        if(keyEvent.getExtendedKeyCode() == KeyEvent.VK_DOWN){
            down = true;
        }
        if(keyEvent.getExtendedKeyCode() == KeyEvent.VK_RIGHT){
            right =true;
        }
    }

    public void keyReleased(KeyEvent keyEvent) {
        if(keyEvent.getExtendedKeyCode() == KeyEvent.VK_ESCAPE){
            esc =false;
        }
        if(keyEvent.getExtendedKeyCode() == KeyEvent.VK_ENTER){
            enter = false;
        }
        if(keyEvent.getExtendedKeyCode() == KeyEvent.VK_UP){
            up = false;
        }
        if(keyEvent.getExtendedKeyCode() == KeyEvent.VK_DOWN){
            down = false;
        }
        if(keyEvent.getExtendedKeyCode() == KeyEvent.VK_RIGHT){
            right =false;
        }
    }
}
