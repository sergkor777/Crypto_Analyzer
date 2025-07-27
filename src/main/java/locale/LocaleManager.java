package locale;

import java.util.Locale;

public class LocaleManager {
    public static Locale LOCALE_RU;
    public static Locale LOCALE_KZ;
    public static Locale LOCALE_EN;
    public static Locale LOCALE_AZ;

    private static LocaleManager instance;

    private Locale locale;

    //private EventListenerList listenerList;
    public static final String LANGUAGE_RU = "base.language.ru";
    public static final String LANGUAGE_KZ = "base.language.kz";
    public static final String LANGUAGE_EN = "base.language.en";
    public static final String LANGUAGE_AZ = "base.language.az";

    public synchronized static LocaleManager getInstance() {
        if (instance == null) {
            instance = new LocaleManager();
        }
        return instance ;
    }

    private LocaleManager() {
        //noinspection HardCodedStringLiteral
        LOCALE_RU = new Locale("ru");
        //noinspection HardCodedStringLiteral
        LOCALE_KZ = new Locale("kz");
        LOCALE_EN = new Locale("en");
        LOCALE_AZ = new Locale("az");
        locale = LOCALE_EN;
    }

    public synchronized Locale getLocale() {
        return locale;
    }

    public synchronized void setLocale(Locale locale) {
        checkArgument(locale);

        if (!this.locale.equals(locale)) {
            this.locale = locale;
        }
    }

    private void checkArgument(Object argument) {
        if (argument == null) {
            throw new IllegalArgumentException("Argument is null!!!");
        }
    }


}
