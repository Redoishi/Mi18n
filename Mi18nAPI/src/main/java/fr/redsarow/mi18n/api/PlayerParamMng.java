package fr.redsarow.mi18n.api;

import fr.redsarow.mi18n.api.save.ISavePlayerParam;
import fr.redsarow.mi18n.api.save.SaveFactory;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class PlayerParamMng {

    private static PlayerParamMng ourInstance;

    private HashMap<UUID, Locale> playerLocal;
    private ISavePlayerParam iSavePlayerParam;

    private PlayerParamMng(ISavePlayerParam iSavePlayerParam) {
        this.playerLocal = new HashMap<>();
        this.iSavePlayerParam = iSavePlayerParam;
    }

    static void initInstance() {
        ourInstance = new PlayerParamMng(SaveFactory.getSavePlayerParam());
    }

    public static PlayerParamMng getInstance() {
        return ourInstance;
    }

    /**
     * utils for test
     *
     * @param iSavePlayerParam
     */
    protected void setiSavePlayerParam(ISavePlayerParam iSavePlayerParam) {
        this.iSavePlayerParam = iSavePlayerParam;
    }

    public Locale getPlayerLocal(Player player) {
        UUID uniqueId = player.getUniqueId();
        if (playerLocal.containsKey(uniqueId)) {
            Locale locale = playerLocal.get(uniqueId);
            return locale == null ? Mi18nAPI.DEFAULT_SERVER_LOCAL : locale;
        }
        Locale localForPlayer = iSavePlayerParam.getLocalForPlayer(player);
        playerLocal.put(uniqueId, localForPlayer);
        return localForPlayer == null ? Mi18nAPI.DEFAULT_SERVER_LOCAL : localForPlayer;
    }

    public boolean setPlayerLocal(Player player, Locale locale) {
        boolean ok = iSavePlayerParam.saveLocalForPlayer(player, locale);
        if (ok) {
            playerLocal.put(player.getUniqueId(), locale);
        }
        return ok;
    }

    public boolean rmPlayerLocal(Player player) {
        boolean ok = iSavePlayerParam.rmLocalForPlayer(player);
        if (ok) {
            playerLocal.put(player.getUniqueId(), null);
        }
        return ok;
    }

    public boolean rmPlayer(Player player) {
        return playerLocal.remove(player.getUniqueId()) != null;
    }
}
