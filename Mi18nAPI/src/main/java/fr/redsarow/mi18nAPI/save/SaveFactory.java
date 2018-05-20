package fr.redsarow.mi18nAPI.save;

import fr.redsarow.mi18nAPI.Mi18nAPI;
import fr.redsarow.mi18nAPI.save.sql.SqlSavePlayerParam;
import fr.redsarow.mi18nAPI.save.yml.YmlSavePlayerParam;

import java.io.IOException;
import java.sql.SQLException;

import static fr.redsarow.mi18nAPI.Mi18nAPI.LANGUAGE_BUNDLE;
import static fr.redsarow.mi18nAPI.Mi18nAPI.LOGGER;

/**
 * @author redsarow
 * @since 1.0.0
 */
public abstract class SaveFactory {

    private static ISavePlayerParam savePlayerParam;

    /**
     * @param mi18nAPI
     * @param type
     *
     * @throws IOException flag by yml
     * @throws SQLException flag by sql
     */
    public static void initSave(Mi18nAPI mi18nAPI, String type) throws IOException, SQLException {
        type = type.toLowerCase();
        switch (type) {
            case "yml":
                LOGGER.info(LANGUAGE_BUNDLE.getString("init.save.yml"));
                savePlayerParam = new YmlSavePlayerParam(mi18nAPI);
                break;
            case "sql":
                LOGGER.info(LANGUAGE_BUNDLE.getString("init.save.sql"));
                savePlayerParam = new SqlSavePlayerParam(mi18nAPI);
                break;
        }
    }

    public static ISavePlayerParam getSavePlayerParam() {
        return savePlayerParam;
    }
}
