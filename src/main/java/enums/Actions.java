package enums;

import actions.*;
import exceptions.AppException;
import locale.ResourceBundleCache;

public enum Actions {
    SELECT_LANGUAGE(new SelectLanguageAction()),
    ENCODE(new EncoderAction()),
    DECODE(new DecoderAction()),
    BRUTE_FORCE(new BruteForceAction()),
    STATIC_ANALYZER(new StaticAnalyzerAction()),
    EXIT(new ExitAction());

    private final Action action;

    Actions(Action action) {
        this.action = action;
    }

    public static Action find(String actionName) {
        try {
            Actions value = Actions.valueOf(actionName.toUpperCase());
            return value.action;
        } catch (IllegalArgumentException e) {
            throw new AppException(ResourceBundleCache.getInstance().getString("action_not_found"), e);
        }
    }
}
