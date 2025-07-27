package locale;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

 public class ResourceBundleCache {
    private HashMap bundles;
    private static ResourceBundleCache instance;
    public static final String DEFAULT_BUNDLE = "bundles.translations";
    /** @noinspection HardCodedStringLiteral*/


    public synchronized static ResourceBundleCache getInstance() {
        if (instance == null) {
            instance = new ResourceBundleCache();
        }
        return instance ;
    }

    private ResourceBundleCache() {
        bundles = new HashMap();
    }

    public String getString(String base, String key) {
        return getBundle(base).getString(key);
    }

    public String getString(String key) {
        return getBundle(DEFAULT_BUNDLE).getString(key);
    }

    public ResourceBundle getBundle(String base) {
        Locale locale = LocaleManager.getInstance().getLocale();
        String key = base + locale.getLanguage();
        ResourceBundle result = (ResourceBundle) bundles.get(key);
        if (result == null) {
            try {
                result = ResourceBundle.getBundle(base, locale);
                bundles.put(key, result);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result;
    }
}

