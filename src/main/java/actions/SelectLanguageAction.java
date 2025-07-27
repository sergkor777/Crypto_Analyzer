package actions;

import entity.Result;
import enums.ResultCode;
import exceptions.AppException;
import locale.LocaleManager;
import locale.ResourceBundleCache;

import java.util.Locale;

public class SelectLanguageAction implements Action{

    @Override
    public Result execute(String[] parameters) {
        try {
            Locale locale = switch (parameters[0].toUpperCase()) {
                case "EN" -> LocaleManager.LOCALE_EN;
                case "RU" -> LocaleManager.LOCALE_RU;
                case "KZ" -> LocaleManager.LOCALE_KZ;
                case "AZ" -> LocaleManager.LOCALE_AZ;
                default -> throw new AppException(parameters[0]+ " - " +
                        ResourceBundleCache.getInstance().getString("unsupported_language"));
            };
            LocaleManager.getInstance().setLocale(locale);
            return new Result(ResourceBundleCache.getInstance().getString("default_language") + " - " + locale.toString().toUpperCase(), ResultCode.UPDATE_MENU);
        } catch (RuntimeException e) {
            return new Result(ResourceBundleCache.getInstance().getString("unsupported_language") + " - " + parameters[0], ResultCode.UPDATE_MENU);
        }
    }
}
