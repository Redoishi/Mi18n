package fr.redsarow.mi18n.api.save.yml;

import fr.redsarow.mi18n.api.Mi18nAPI;
import fr.redsarow.mi18n.api.save.ISavePlayerParam;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.Level;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class YmlSavePlayerParam extends AYmlSave implements ISavePlayerParam {

    private static final String FILE_NAME = "PlayerParam.yml";

    private static final String LANGUAGE = "language";
    private static final String COUNTRY = "country";

    private File dataFolder;
    private File file;
    private YamlConfiguration configFile;

    public YmlSavePlayerParam(Mi18nAPI mi18nAPI) throws IOException {

        this.dataFolder = mi18nAPI.getDataFolder();

        initFile(dataFolder, FILE_NAME);

        file = new File(this.dataFolder, FILE_NAME);
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

    private boolean save() {
        try {
            configFile.save(file);
        } catch (IOException e) {
            Mi18nAPI.getLOGGER().log(Level.SEVERE, e.getLocalizedMessage(), e);
            return false;
        }
        return true;
    }

    @Override
    public boolean saveLocalForPlayer(Player player, Locale locale) {
        String playerUUID = player.getUniqueId().toString();
        setSection(configFile, playerUUID);
        setSectionVal(configFile, playerUUID + "." + LANGUAGE, locale.getLanguage());
        setSectionVal(configFile, playerUUID + "." + COUNTRY, locale.getCountry());
        return save();
    }

    @Override
    public boolean rmLocalForPlayer(Player player) {
        String playerUUID = player.getUniqueId().toString();
        setSectionVal(configFile, playerUUID, null);
        return save();
    }

    @Override
    public void shutdown() {

    }
}
