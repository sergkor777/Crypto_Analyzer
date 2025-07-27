package actions;

import entity.Result;
import enums.ResultCode;
import locale.ResourceBundleCache;
import util.FileManager;

public class BruteForceAction implements Action {

    @Override
    public Result execute(String[] parameters) {
        try {
            FileManager fileManager = new FileManager();
            fileManager.bruteForceFile(parameters[0], parameters[1]);
            return new Result(ResourceBundleCache.getInstance().getString("brute_force_all_right"), ResultCode.OK);
        } catch (RuntimeException e) {
            return new Result(ResourceBundleCache.getInstance().getString("error") + " - " +
                    e.getMessage(), ResultCode.UPDATE_MENU);
        }
    }
}
