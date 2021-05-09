package lightfilesystem;

import lombok.Data;
import org.tinylog.Logger;

/**
 * Maga az alkalmazás modell és vezérlő rétege.
 * Valamint a folyamata irányító eljárásokat idító osztály.
 */
@Data
public class Application {
    FileSystem filesystem;
    ApplicationStateManager stateManager;

    public final String CONFIG_FILE_NAME = "filesystem.json";

    /**
     * Megkeresi a config fájlt
     *
     * @return Fájl útvonala
     */
    public String getConfigFilePaht(){
        return "ASD";
    }

    /**
     * Leellenőrzni, hogy a config fájl létezik-e.
     *
     * @return Igaz, ha létezik a config fájl.
     */
    public boolean isConfigFileExists(){
        return true;
    }

    /**
     * Betölti a config fájlból az adatokat.
     */
    public void loadConfigFile(){
        Logger.info("[APPLICATION] Trying to load config file...");

    }

    /**
     * Elmenti a config fájlba az adatokat.
     */
    public void saveConfigFile(){
        Logger.info("[APPLICATION] Saving config file.");
    }
}
