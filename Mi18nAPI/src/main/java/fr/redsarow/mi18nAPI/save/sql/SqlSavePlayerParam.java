package fr.redsarow.mi18nAPI.save.sql;

import fr.redsarow.mi18nAPI.Mi18nAPI;
import fr.redsarow.mi18nAPI.save.ISavePlayerParam;
import org.bukkit.entity.Player;

import java.util.Locale;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class SqlSavePlayerParam implements ISavePlayerParam {

    private Mi18nAPI mi18nAPI;

    public SqlSavePlayerParam(Mi18nAPI mi18nAPI) {
        this.mi18nAPI = mi18nAPI;
    }

    @Override
    public Locale getLocalForPlayer(Player player) {
        return null;
    }

    @Override
    public boolean saveLocalForPlayer(Player player, Locale locale) {
        return false;
    }

    @Override
    public boolean rmLocalForPlayer(Player player) {
        return false;
    }
}
