package fr.redsarow.mi18nAPI;

import fr.redsarow.mi18nAPI.config.Config;
import fr.redsarow.mi18nAPI.save.SaveFactory;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Logger;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class Mi18nAPI extends JavaPlugin {

    public final static double VERS_CONFIG = 1.0;

    public static Logger LOGGER;
    public static Locale DEFAULT_SERVER_LOCAL = Locale.ENGLISH;
    public static ResourceBundle LANGUAGE_BUNDLE;
    public static MessageFormat MESSAGE_FORMAT;

    @Override
    public void onEnable() {
        try {
            LOGGER = getLogger();

            LOGGER.info("init config");
            if (!Config.checkConfig(this, "config.yml", "")) {
                getLogger().severe("Error setup config. Stop plugin");
                this.getPluginLoader().disablePlugin(this);
                return;
            }

            LOGGER.info("init language");
            FileConfiguration config = getConfig();
            DEFAULT_SERVER_LOCAL = new Locale(
                    config.getString("language", "en"),
                    config.getString("country", ""));
            LANGUAGE_BUNDLE = ResourceBundle.getBundle("Mi18nAPI_language", DEFAULT_SERVER_LOCAL);
            MESSAGE_FORMAT = new MessageFormat("");
            MESSAGE_FORMAT.setLocale(DEFAULT_SERVER_LOCAL);

            LOGGER.info(LANGUAGE_BUNDLE.getString("init.language"));

            LOGGER.info(LANGUAGE_BUNDLE.getString("init.save"));
            SaveFactory.initSave(this, config.getString("save"));

            PlayerParamMng.initInstance();
            I18n.setPlayerParam(PlayerParamMng.getInstance());

        } catch (Exception e) {
            LOGGER.severe(e.getLocalizedMessage());
            e.printStackTrace();
            this.getPluginLoader().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        SaveFactory.getSavePlayerParam().shutdown();
    }
}
