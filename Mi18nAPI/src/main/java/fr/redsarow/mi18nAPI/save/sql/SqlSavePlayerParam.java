package fr.redsarow.mi18nAPI.save.sql;

import fr.redsarow.mi18nAPI.Mi18nAPI;
import fr.redsarow.mi18nAPI.save.ISavePlayerParam;
import org.apache.commons.dbcp2.BasicDataSource;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.Locale;

import static fr.redsarow.mi18nAPI.Mi18nAPI.LOGGER;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class SqlSavePlayerParam implements ISavePlayerParam {

    private static final String TAB_NAME = "player_param";
    private static final String UUID = "uuid";
    private static final String LANGUAGE = "language";
    private static final String COUNTRY = "country";

    private BasicDataSource dataSource;
    private Mi18nAPI mi18nAPI;

    public SqlSavePlayerParam(Mi18nAPI mi18nAPI) throws SQLException {
        this.mi18nAPI = mi18nAPI;

        FileConfiguration config = mi18nAPI.getConfig();
        dataSource = new BasicDataSource();
        dataSource.setUrl(config.getString("sql.url"));
        dataSource.setUsername(config.getString("sql.user"));
        dataSource.setPassword(config.getString("sql.pwd"));
        initDb();

    }

    private void initDb() throws SQLException {
        Connection connection = null;
        Statement statement = null;
        try {
            connection = dataSource.getConnection();
            statement = connection.createStatement();
            statement.execute("CREATE TABLE IF NOT EXISTS " + TAB_NAME +
                    " (" + UUID + " varchar(50), " +
                    LANGUAGE + " varchar(50), " +
                    COUNTRY + " varchar(50), " +
                    "CONSTRAINT PK_uuid PRIMARY KEY (" + UUID + ")" +
                    ");");
        } catch (Exception e) {
            LOGGER.severe(e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            close(connection, statement);
        }
    }

    private void close(Connection connection, Statement ... statement) {
        try {
            for (Statement statement1 : statement) {
                if (statement1 != null) {
                    statement1.close();
                }
            }
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            LOGGER.severe(e.getLocalizedMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Locale getLocalForPlayer(Player player) {
        Locale locale = null;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();
            String sql = "SELECT * FROM " + TAB_NAME +
                    " WHERE " + UUID + "=?;";
            statement = connection.prepareStatement(sql);
            statement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String language = resultSet.getString(LANGUAGE);
                String country = resultSet.getString(COUNTRY);
                locale = new Locale(language, country);
            }
        } catch (Exception e) {
            LOGGER.severe(e.getLocalizedMessage());
            e.printStackTrace();
        } finally {
            close(connection, statement);
        }
        return locale;
    }

    @Override
    public boolean saveLocalForPlayer(Player player, Locale locale) {
        boolean ok = true;
        Connection connection = null;
        PreparedStatement statement = null;
        PreparedStatement statementUpdate = null;
        try {
            connection = dataSource.getConnection();

            //check exist
            String sqlExist = "SELECT * FROM " + TAB_NAME +
                     " WHERE " + UUID + "=?;";
            statement = connection.prepareStatement(sqlExist);
            statement.setString(1, player.getUniqueId().toString());
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.next()) {
                //insert
                String sqlInsert = "INSERT INTO " + TAB_NAME +
                        " VALUES (?, ?, ?);";
                statementUpdate = connection.prepareStatement(sqlInsert);
                statementUpdate.setString(1, player.getUniqueId().toString());
                statementUpdate.setString(2, locale.getLanguage());
                statementUpdate.setString(3, locale.getCountry());
            } else {
                //update
                String sqlUpdate = "UPDATE " + TAB_NAME +
                        " SET " + LANGUAGE + "=?, " + COUNTRY + "=?" +
                        " WHERE " + UUID + "=?;";
                statementUpdate = connection.prepareStatement(sqlUpdate);
                statementUpdate.setString(1, locale.getLanguage());
                statementUpdate.setString(2, locale.getCountry());
                statementUpdate.setString(3, player.getUniqueId().toString());
            }
            int i = statementUpdate.executeUpdate();
            ok = i == 1;

        } catch (Exception e) {
            LOGGER.severe(e.getLocalizedMessage());
            e.printStackTrace();
            ok = false;
        } finally {
            close(connection, statement, statementUpdate);
        }
        return ok;
    }

    @Override
    public boolean rmLocalForPlayer(Player player) {
        boolean ok = true;
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = dataSource.getConnection();

            //check exist
            String sql = "DELETE FROM " + TAB_NAME +
                    " WHERE " + UUID + "=?;";
            statement = connection.prepareStatement(sql);
            statement.setString(1, player.getUniqueId().toString());
            int i = statement.executeUpdate();
            ok = i <= 1;
        } catch (Exception e) {
            LOGGER.severe(e.getLocalizedMessage());
            e.printStackTrace();
            ok = false;
        } finally {
            close(connection, statement);
        }
        return ok;
    }

    @Override
    public void shutdown() {
        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (SQLException e) {
                LOGGER.severe(e.getLocalizedMessage());
                e.printStackTrace();
            }
        }
    }
}
