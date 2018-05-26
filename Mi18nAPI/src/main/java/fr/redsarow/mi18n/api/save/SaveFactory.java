package fr.redsarow.mi18n.api.save;

import fr.redsarow.mi18n.api.Mi18nAPI;
import fr.redsarow.mi18n.api.save.sql.SqlSavePlayerParam;
import fr.redsarow.mi18n.api.save.yml.YmlSavePlayerParam;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author redsarow
 * @since 1.0.0
 */
public abstract class SaveFactory {

    private static ISavePlayerParam savePlayerParam;

    private SaveFactory() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * @param mi18nAPI
     * @param type
     *
     * @throws IOException  flag by yml
     * @throws SQLException flag by sql
     */
    public static void initSave(Mi18nAPI mi18nAPI, String type) throws IOException, SQLException {
        type = type.toLowerCase();
        switch (type) {
            case "sql":
                Mi18nAPI.getLOGGER().info(Mi18nAPI.getLanguageBundle().getString("init.save.sql"));
                savePlayerParam = new SqlSavePlayerParam(mi18nAPI);
                break;
            default:// == yml
                Mi18nAPI.getLOGGER().info(Mi18nAPI.getLanguageBundle().getString("init.save.yml"));
                savePlayerParam = new YmlSavePlayerParam(mi18nAPI);
                break;

        }
    }

    public static ISavePlayerParam getSavePlayerParam() {
        return savePlayerParam;
    }
}
