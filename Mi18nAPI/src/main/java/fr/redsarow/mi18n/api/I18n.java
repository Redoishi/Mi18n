package fr.redsarow.mi18n.api;

import org.bukkit.entity.Player;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class I18n {

    private static PlayerParamMng PLAYER_PARAM_MNG;

    static void setPlayerParam(PlayerParamMng playerParam) {
        PLAYER_PARAM_MNG = playerParam;
    }

    private final HashMap<Locale, ResourceBundle> bundleHashMap;
    private final Locale defaultLocale;
    private final List<Locale> locales;
    private final List<Locale> allLocales;
    private String baseName;

    private final URLClassLoader classLoader;

    public I18n(Class clientClass, String baseName, Locale defaultLocale, Locale... locales) throws URISyntaxException {
        Mi18nAPI.getMessageFormat().applyPattern(Mi18nAPI.getLanguageBundle().getString("I18n.init"));
        Mi18nAPI.getLOGGER().info(Mi18nAPI.getMessageFormat().format(new String[]{clientClass.getName(), baseName}));

        this.defaultLocale = defaultLocale;
        this.locales = Arrays.stream(locales).collect(Collectors.toList());
        this.bundleHashMap = new HashMap<>();
        this.baseName = baseName;
        this.allLocales = new ArrayList<>();
        this.allLocales.add(defaultLocale);
        this.allLocales.addAll(this.locales);

        File file = new File(clientClass.getProtectionDomain().getCodeSource().getLocation().toURI());

        URL[] urls = new URL[0];
        try {
            urls = new URL[]{file.toURI().toURL()};
        } catch (MalformedURLException e) {
            Mi18nAPI.getLOGGER().log(Level.SEVERE, e.getLocalizedMessage(), e);
        }

        classLoader = new URLClassLoader(urls, null);
    }

    private ResourceBundle getResourceBundle(Locale locale) {
        if (locale == null) {
            locale = defaultLocale;
        }

        if (!allLocales.contains(locale)) {
            locale = Locale.lookup(
                    Locale.LanguageRange.parse(locale.toLanguageTag()),
                    allLocales
            );
            if(locale == null){
                if (allLocales.contains(Mi18nAPI.DEFAULT_SERVER_LOCAL)) {
                    locale = Mi18nAPI.DEFAULT_SERVER_LOCAL;
                } else {
                    locale = defaultLocale;
                }
            }
        }

        return bundleHashMap.computeIfAbsent(locale,
                locale1 -> ResourceBundle.getBundle(this.baseName, locale1, classLoader));
    }

    /**
     *
     * @param key
     *
     * @return
     */
    public String get(String key) {
        ResourceBundle resourceBundle = getResourceBundle(Mi18nAPI.DEFAULT_SERVER_LOCAL);
        return resourceBundle.getString(key);
    }

    public String get(String key, Object... param) {
        ResourceBundle resourceBundle = getResourceBundle(Mi18nAPI.DEFAULT_SERVER_LOCAL);
        Mi18nAPI.getMessageFormat().applyPattern(resourceBundle.getString(key));
        return Mi18nAPI.getMessageFormat().format(param);
    }

    public String get(Player player, String key) {
        ResourceBundle resourceBundle = getResourceBundle(PLAYER_PARAM_MNG.getPlayerLocal(player));
        return resourceBundle.getString(key);
    }

    public String get(Player player, String key, Object... param) {
        ResourceBundle resourceBundle = getResourceBundle(PLAYER_PARAM_MNG.getPlayerLocal(player));
        Mi18nAPI.getMessageFormat().applyPattern(resourceBundle.getString(key));
        return Mi18nAPI.getMessageFormat().format(param);
    }

    //<editor-fold desc="default get">
    public Locale getDefaultLocale() {
        return defaultLocale;
    }

    public List<Locale> getLocales() {
        return locales;
    }

    public String getBaseName() {
        return baseName;
    }
    //</editor-fold>
}
