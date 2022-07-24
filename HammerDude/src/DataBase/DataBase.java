package DataBase;

import entities.Player;
import ui.PauseOverlay;
import ui.SoundButton;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DataBase {

    PauseOverlay pO;
    Player player;

    public DataBase(PauseOverlay pO, Player player) {

        this.pO=pO;
        this.player = player;

    }

    public void saveToDB() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:PAOO_game.db");
             stmt = c.createStatement();
//            String sql = "CREATE TABLE HAMMER_DUDE " +
//                    "( 'SOUND_INDEX'       INT    NOT NULL,"+
//                    "   WINS               INT    NOT NULL,"+
//                    "   LOST               INT    NOT NULL)";
//            stmt.execute(sql);
//            sql = "INSERT INTO HAMMER_DUDE ('SOUND_INDEX', WINS, LOST) " +
//                    "VALUES (1,0,0);";
//            stmt.execute(sql);
            String sql = "UPDATE HAMMER_DUDE set SOUND_INDEX =\"" + pO.isMusicMuted +
                    "\", " + "WINS = \"" + player.didIWin + "\", " + "LOST = \""
                    + player.didILose + "\" WHERE rowid=1";
            stmt.execute(sql);

            stmt.close();
            c.close();
        } catch (Exception ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        System.out.println("Baza de date a fost actualizata cu succes!");
    }

    public void loadFromDB() {
        Connection c = null;
        Statement stmt = null;
        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:PAOO_game.db");
            stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * from HAMMER_DUDE");
            pO.isMusicMuted = rs.getBoolean("SOUND_INDEX");
            player.didIWin = rs.getBoolean("WINS");
            player.didILose = rs.getBoolean("LOST");

            rs.close();
            stmt.close();
            c.close();
        } catch (Exception ex) {
            System.err.println(ex.getClass().getName() + ": " + ex.getMessage());
            System.exit(0);
        }
        System.out.println("S-a incarcat cu succes din baza de date!");
    }
}
