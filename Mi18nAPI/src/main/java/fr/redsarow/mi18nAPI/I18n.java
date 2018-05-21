package fr.redsarow.mi18nAPI;

import org.bukkit.entity.Player;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.stream.Collectors;

import static fr.redsarow.mi18nAPI.Mi18nAPI.*;

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
        MESSAGE_FORMAT.applyPattern(LANGUAGE_BUNDLE.getString("I18n.init"));
        LOGGER.info(MESSAGE_FORMAT.format(new String[]{clientClass.getName(), baseName}));

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
            e.printStackTrace();
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
                if (allLocales.contains(DEFAULT_SERVER_LOCAL)) {
                    locale = DEFAULT_SERVER_LOCAL;
                } else {
                    locale = defaultLocale;
                }
            }
        }
        ResourceBundle resourceBundle = bundleHashMap.get(locale);
        if (resourceBundle == null) {
            resourceBundle = ResourceBundle.getBundle(this.baseName, locale, classLoader);
            bundleHashMap.put(locale, resourceBundle);
        }

        return resourceBundle;
    }

    /**
     * method use for write in console.
     *
     * @param key
     *
     * @return
     */
    public String get(String key) {
        ResourceBundle resourceBundle = getResourceBundle(DEFAULT_SERVER_LOCAL);
        return resourceBundle.getString(key);
    }

    public String get(Player player, String key) {
        ResourceBundle resourceBundle = getResourceBundle(PLAYER_PARAM_MNG.getPlayerLocal(player));
        return resourceBundle.getString(key);
    }

    public String get(Player player, String key, Object... param) {
        ResourceBundle resourceBundle = getResourceBundle(PLAYER_PARAM_MNG.getPlayerLocal(player));
        MESSAGE_FORMAT.applyPattern(resourceBundle.getString(key));
        return MESSAGE_FORMAT.format(param);
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
