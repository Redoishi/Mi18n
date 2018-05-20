package fr.redsarow.mi18nAPI.save.yml;

import fr.redsarow.mi18nAPI.Mi18nAPI;
import fr.redsarow.mi18nAPI.save.ISavePlayerParam;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class YmlSavePlayerParam extends AYmlSave implements ISavePlayerParam {

    private final static String FILE = "PlayerParam.yml";

    private final static String LANGUAGE = "language";
    private final static String COUNTRY = "country";

    private File dataFolder;
    private File file;
    private YamlConfiguration configFile;

    public YmlSavePlayerParam(Mi18nAPI Mi18nAPI) throws IOException {

        this.dataFolder = Mi18nAPI.getDataFolder();

        initFile(dataFolder, FILE);

        file = new File(this.dataFolder, FILE);
        configFile = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    public Locale getLocalForPlayer(Player player) {
        String playerUUID = player.getUniqueId().toString();
        if (configFile.get(playerUUID) == null) {
            return null;
        }
        String languae = configFile.getString(playerUUID + "." + LANGUAGE);
        String country = configFile.getString(playerUUID + "." + COUNTRY, "");
        return new Locale(languae, country);
    }

    @Override
    public boolean saveLocalForPlayer(Player player, Locale locale) {
        String playerUUID = player.getUniqueId().toString();
        setSection(configFile, playerUUID);
        setSectionVal(configFile, playerUUID + "." + LANGUAGE, locale.getLanguage());
        setSectionVal(configFile, playerUUID + "." + COUNTRY, locale.getCountry());
        return true;
    }

    @Override
    public boolean rmLocalForPlayer(Player player) {
        String playerUUID = player.getUniqueId().toString();
        setSectionVal(configFile, playerUUID, null);
        return true;
    }

    @Override
    public void shutdown() {

    }
}
