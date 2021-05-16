import org.tinylog.Logger;
import java.io.IOException;
import GUI.ApplicationGUI;

/**
 * Egy linux fájlrendszert imintáló program, felhasználókkal
 * csoportokkal és ezeken alapuló engedélyekkel.
 *
 * @author  Boda Zsolt
 * @version SNAPSHOT-1
 * @since   2021-05-06
 */
public class Main {
    public static void main(String[] args) throws IOException {
        Logger.info("[APPLICATION] Starting main processes...");
        ApplicationGUI.main(args);
    }
}
