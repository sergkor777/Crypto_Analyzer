package locale;

import java.util.Locale;

public final class LocaleManager {
    private static Locale localeRu;
    private static Locale localeKz;
    private static Locale localeEn;
    private static Locale localeAz;

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
        return instance;
    }

    private LocaleManager() {
        //noinspection HardCodedStringLiteral
        localeRu = new Locale("ru");
        //noinspection HardCodedStringLiteral
        localeKz = new Locale("kz");
        localeEn = new Locale("en");
        localeAz = new Locale("az");
        locale = localeEn;
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

    public static Locale getLocaleRu() {
        return localeRu;
    }

    public static Locale getLocaleKz() {
        return localeKz;
    }

    public static Locale getLocaleEn() {
        return localeEn;
    }

    public static Locale getLocaleAz() {
        return localeAz;
    }
}
