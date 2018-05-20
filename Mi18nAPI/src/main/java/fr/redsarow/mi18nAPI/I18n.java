package fr.redsarow.mi18nAPI;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import static fr.redsarow.mi18nAPI.Mi18nAPI.DEFAULT_SERVER_LOCAL;
import static fr.redsarow.mi18nAPI.Mi18nAPI.LOGGER;

/**
 * @author redsarow
 * @since 1.0.0
 */
public class I18n {

    private final HashMap<Locale, ResourceBundle> bundleHashMap;
    private final Locale defaultLocale;
    private final List<Locale> locales;
    private String baseName;

    private final URLClassLoader classLoader;

    public I18n(Class clientClass, String baseName, Locale defaultLocale, List<Locale> locales) throws URISyntaxException {
        LOGGER.info("new plugin "+clientClass.getName()+" for baseName "+baseName);

        this.defaultLocale = defaultLocale;
        this.locales = locales;
        this.bundleHashMap = new HashMap<>();
        this.baseName = baseName;

        File file = new File(clientClass.getProtectionDomain().getCodeSource().getLocation().toURI());

        URL[] urls = new URL[0];
        try {
            urls = new URL[]{file.toURI().toURL()};
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        classLoader = new URLClassLoader(urls, null);
    }

    private ResourceBundle getResourceBundle (Locale locale){
        if (locale==null) {
            locale = defaultLocale;
        }

        if (!defaultLocale.equals(locale) && !this.locales.contains(locale)) {
            if(this.locales.contains(DEFAULT_SERVER_LOCAL)){
                locale=DEFAULT_SERVER_LOCAL;
            }else{
                locale=defaultLocale;
            }
        }
        ResourceBundle resourceBundle = bundleHashMap.get(locale);
        if(resourceBundle == null){
            resourceBundle = ResourceBundle.getBundle(this.baseName, locale, classLoader);
            bundleHashMap.put(locale, resourceBundle);
        }

        return resourceBundle;
    }

    public String get(String key){
        return "";//TODO
    }
}
