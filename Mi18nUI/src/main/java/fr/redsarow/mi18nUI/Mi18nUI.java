package fr.redsarow.mi18nUI;

import fr.redsarow.mi18nAPI.I18n;
import fr.redsarow.mi18nAPI.PlayerParamMng;
import fr.redsarow.mi18nUI.commands.Language;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Locale;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class Mi18nUI extends JavaPlugin {

    public static I18n i18n;
    public static PlayerParamMng playerParamMng;

    @Override
    public void onEnable() {
        try{
            i18n = new I18n(this.getClass(), "language",  Locale.ENGLISH, Locale.FRENCH);
            playerParamMng = PlayerParamMng.getInstance();

            getLogger().info(i18n.get("init.command"));
            Field f = null;
            f = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            CommandMap commandMap = (CommandMap) f.get(Bukkit.getServer());

            new Language(this, commandMap);

        } catch (Exception e) {
            getLogger().severe(e.getLocalizedMessage());
            e.printStackTrace();
            this.getPluginLoader().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {

    }
}
