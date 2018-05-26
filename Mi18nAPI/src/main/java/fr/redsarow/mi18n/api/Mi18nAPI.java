package fr.redsarow.mi18n.api;

import fr.redsarow.mi18n.api.config.Config;
import fr.redsarow.mi18n.api.listener.Leave;
import fr.redsarow.mi18n.api.save.ISavePlayerParam;
import fr.redsarow.mi18n.api.save.SaveFactory;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class Mi18nAPI extends JavaPlugin {

    public static final double VERS_CONFIG = 1.0;

    private static Logger LOGGER;
    private static ResourceBundle LANGUAGE_BUNDLE;
    private static MessageFormat MESSAGE_FORMAT;

    static Locale DEFAULT_SERVER_LOCAL = Locale.ENGLISH;

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

            LOGGER.info(LANGUAGE_BUNDLE.getString("init.listener"));
            PluginManager pm = Bukkit.getPluginManager();
            pm.registerEvents(new Leave(this), this);


        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, e.getLocalizedMessage(), e);
            this.getPluginLoader().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        ISavePlayerParam savePlayerParam = SaveFactory.getSavePlayerParam();
        if (savePlayerParam != null) {
            savePlayerParam.shutdown();
        }
    }

    public static Logger getLOGGER() {
        return LOGGER;
    }

    public static ResourceBundle getLanguageBundle() {
        return LANGUAGE_BUNDLE;
    }

    public static MessageFormat getMessageFormat() {
        return MESSAGE_FORMAT;
    }

    static void setLOGGER(Logger LOGGER) {
        Mi18nAPI.LOGGER = LOGGER;
    }

    static void setLanguageBundle(ResourceBundle languageBundle) {
        LANGUAGE_BUNDLE = languageBundle;
    }

    static void setMessageFormat(MessageFormat messageFormat) {
        MESSAGE_FORMAT = messageFormat;
    }
}
