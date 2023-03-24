package PaooGame;

import PaooGame.Entity.Player;
import PaooGame.GameWindow.GameWindow;
import PaooGame.Graphics.Assets;
import PaooGame.Map.Map;
import PaooGame.State.*;
import PaooGame.util.DataBase;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.sql.*;

/**  Clasa principala a intregului proiect. Implementeaza Game - Loop (Update -> Draw)
 *<p>
 *<p>             ------------
 *<p>             |           |
 *<p>             |     ------------
 *<p> 60 times/s  |     |  Update  |  -->{ actualizeaza variabile, stari, pozitii ale elementelor grafice etc.
 *<p>     =       |     ------------
 *<p>  16.7 ms    |           |
 *<p>             |     ------------
 *<p>             |     |   Draw   |  -->{ deseneaza totul pe ecran
 *<p>             |     ------------
 *<p>             |           |
 *<p>             -------------
 *<p> Implementeaza interfata Runnable:
 *<p>     public interface Runnable {
 *<p>         public void run();
 *<p>     }
 *<p> Interfata este utilizata pentru a crea un nou fir de executie avand ca argument clasa Game.
 *<p> Clasa Game trebuie sa aiba definita metoda "public void run()", metoda ce va fi apelata
 *<p> in noul thread(fir de executie). Mai multe explicatii veti primi la curs.
 *<p>
 *<p> In mod obisnuit aceasta clasa trebuie sa contina urmatoarele:
 *<p>     - public Game();            //constructor
 *<p>     - private void init();      //metoda privata de initializare
 *<p>     - private void update();    //metoda privata de actualizare a elementelor jocului
 *<p>     - private void draw();      //metoda privata de desenare a tablei de joc
 *<p>     - public run();             //metoda publica ce va fi apelata de noul fir de executie
 *<p>     - public synchronized void start(); //metoda publica de pornire a jocului
 *<p>     - public synchronized void stop()   //metoda publica de oprire a jocului
 */
public class Game implements Runnable
{
    /** Fereastra in care se va desena tabla jocului*/
    private GameWindow      gw;
    /** Flag ce starea firului de executie.*/
    private boolean         isRunning;
    /** Referinta catre thread-ul de update si draw al ferestrei*/
    private Thread          gameThread;
    /** Referinta catre un mecanism cu care se organizeaza memoria complexa pentru un canvas.*/
    private BufferStrategy  bs;
    /// Sunt cateva tipuri de "complex buffer strategies", scopul fiind acela de a elimina fenomenul de
    /// flickering (palpaire) a ferestrei.
    /// Modul in care va fi implementata aceasta strategie in cadrul proiectului curent va fi triplu buffer-at

    ///                         |------------------------------------------------>|
    ///                         |                                                 |
    ///                 ****************          *****************        ***************
    ///                 *              *   Show   *               *        *             *
    /// [ Ecran ] <---- * Front Buffer *  <------ * Middle Buffer * <----- * Back Buffer * <---- Draw()
    ///                 *              *          *               *        *             *
    ///                 ****************          *****************        ***************

    /** Referinta catre un context grafic.*/
    private Graphics2D        g;

    private Map map;
    private Player Player;
    /** Conexiune la baza de date */
    private Connection c;

    public State gameState;
    public static PlayState playState;
    public static PausedState pausedState;
    public static MenuState menuState;
    public static GameOverState gameOverState;
    public static YouWinState youWinState;
    public static LoadState loadState;

    /** Constructor de initializare al clasei Game.
        <p>
        Acest constructor primeste ca parametri titlul ferestrei, latimea si inaltimea
        acesteia avand in vedere ca fereastra va fi construita/creata in cadrul clasei Game.
        </p>
        @param title Titlul ferestrei.
        @param width Latimea ferestrei in pixeli.
        @param height Inaltimea ferestrei in pixeli.
     */
    public Game(String title, int width, int height) {
            /// Obiectul GameWindow este creat insa fereastra nu este construita
            /// Acest lucru va fi realizat in metoda init() prin apelul
            /// functiei BuildGameWindow();
        gw = new GameWindow(title, width, height);
            /// Resetarea flagului runState ce indica starea firului de executie (started/stoped)
        isRunning = false;
    }

    /** Metoda construieste fereastra jocului, initializeaza aseturile, listenerul de tastatura etc.
     <p>
        Fereastra jocului va fi construita prin apelul functiei BuildGameWindow();
        Sunt construite elementele grafice (assets): dale, player, elemente active si pasive.
     </p>
     */
    private void InitGame() {
            /// Este construita fereastra grafica.
        gw.BuildGameWindow();
            /// Se incarca toate elementele grafice (dale)
        Assets.Init();

        DataBase.setGame(this);

        playState = new PlayState(this);
        pausedState = new PausedState(this);
        menuState = new MenuState(this);
        gameOverState = new GameOverState(this);
        youWinState = new YouWinState(this);
        loadState = new LoadState(this);


        gameState = menuState;
        gameState.SetState(menuState);
        map =new Map(0);
        Player = new Player();
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:res/DataBase/PAOODataBase.db");
            if(c == null) System.out.println("help");
        }catch(Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return c;
    }

    public int GetWidth(){
        return gw.GetWndWidth();
    }

    public int GetHeight(){
        return gw.GetWndHeight();
    }

    public Map GetMap(){
        return this.map;
    }

    public Player getPlayer(){
        return Player;
    }


    /** Functia ce va rula in thread-ul creat.
        Aceasta functie va actualiza starea jocului si va redesena tabla de joc (va actualiza fereastra grafica)
     */
    public void run() {
            /// Initializeaza obiectul game
        InitGame();
        long oldTime = System.nanoTime();   /*!< Retine timpul in nanosecunde aferent frame-ului anterior.*/
        long curentTime;                    /*!< Retine timpul curent de executie.*/

            /// Apelul functiilor Update() & Draw() trebuie realizat la fiecare 16.7 ms
            /// sau mai bine spus de 60 ori pe secunda.

        final int framesPerSecond   = 60; /*!< Constanta intreaga initializata cu numarul de frame-uri pe secunda.*/
        final double timeFrame      = 1000000000 / framesPerSecond; /*!< Durata unui frame in nanosecunde.*/

            /// Atat timp timp cat threadul este pornit Update() & Draw()
        while (isRunning)
        {
                /// Se obtine timpul curent
            curentTime = System.nanoTime();
                /// Daca diferenta de timp dintre curentTime si oldTime mai mare decat 16.6 ms
            if((curentTime - oldTime) > timeFrame)
            {
                /// Actualizeaza pozitiile elementelor
                Update();
                /// Deseneaza elementele grafica in fereastra.
                Draw();
                oldTime = curentTime;
            }
        }
    }

    /** Creaza si starteaza firul separat de executie (thread).
        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StartGame() {
        if(!isRunning)
        {
                /// Se actualizeaza flagul de stare a threadului
            isRunning = true;
                /// Se construieste threadul avand ca parametru obiectul Game. De retinut faptul ca Game class
                /// implementeaza interfata Runnable. Threadul creat va executa functia run() suprascrisa in clasa Game.
            gameThread = new Thread(this);
                /// Threadul creat este lansat in executie (va executa metoda run())
            gameThread.start();
        }
        else
        {
                /// Thread-ul este creat si pornit deja
            return;
        }
    }

    /** Opreste executie thread-ului.
        Metoda trebuie sa fie declarata synchronized pentru ca apelul acesteia sa fie semaforizat.
     */
    public synchronized void StopGame() {
        if(isRunning)
        {
                /// Actualizare stare thread
            isRunning = false;
                /// Metoda join() arunca exceptii motiv pentru care trebuie incadrata intr-un block try - catch.
            try
            {
                    /// Metoda join() pune un thread in asteptare panca cand un altul isi termina executie.
                    /// Totusi, in situatia de fata efectul apelului este de oprire a threadului.
                c.close();
                gw.CloseWnd();
                gameThread.join();
            }
            catch(InterruptedException | SQLException ex)
            {
                    /// In situatia in care apare o exceptie pe ecran vor fi afisate informatii utile pentru depanare.
                ex.printStackTrace();
            }
        }
        else
        {
                /// Thread-ul este oprit deja.
            return;
        }
    }

    /** Actualizeaza starea elementelor din joc.
        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Update() {
        if(gameState.GetState() != null){
            gameState.GetState().Update();
        }

    }

    /** Deseneaza elementele grafice in fereastra coresponzator starilor actualizate ale elementelor.
        Metoda este declarata privat deoarece trebuie apelata doar in metoda run()
     */
    private void Draw() {
            /// Returnez buff erStrategy pentru canvasul existent
        bs = gw.GetCanvas().getBufferStrategy();
            /// Verific daca buffer strategy a fost construit sau nu
        if(bs == null)
        {
                /// Se executa doar la primul apel al metodei Draw()
            try
            {
                    /// Se construieste tripul buffer
                gw.GetCanvas().createBufferStrategy(3);
                return;
            }
            catch (Exception e)
            {
                    /// Afisez informatii despre problema aparuta pentru depanare.
                e.printStackTrace();
            }
        }
            /// Se obtine contextul grafic curent in care se poate desena.
        g = (Graphics2D) bs.getDrawGraphics();
            /// Se sterge ce era

        g.clearRect(0, 0, gw.GetWndWidth(), gw.GetWndHeight());

            /// operatie de desenare
            // ...............

        if(gameState.GetState() != null){
            gameState.GetState().Draw(g);
        }

            // end operatie de desenare
            /// Se afiseaza pe ecran
        bs.show();

            /// Elibereaza resursele de memorie aferente contextului grafic curent (zonele de memorie ocupate de
            /// elementele grafice ce au fost desenate pe canvas).
        g.dispose();
    }
}

