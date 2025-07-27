package actions;

import entity.Result;
import enums.ResultCode;
import locale.ResourceBundleCache;

public class ExitAction implements Action {

    @Override
    public Result execute(String[] parameters) {
        if (parameters[0].toUpperCase().equals("Y")) {
            System.exit(0);
        }
        return new Result(ResourceBundleCache.getInstance().getString("not_exit"), ResultCode.UPDATE_MENU);
    }
}
