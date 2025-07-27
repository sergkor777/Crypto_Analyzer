package actions;

import entity.Result;
import enums.ResultCode;
import locale.ResourceBundleCache;
import util.FileManager;

public class DecoderAction implements Action {

    @Override
    public Result execute(String[] parameters) {
        try {
            FileManager fileManager = new FileManager();
            fileManager.decryptFile(parameters[0], parameters[1], Integer.parseInt(parameters[2]));
            return new Result(ResourceBundleCache.getInstance().getString("decode_all_right"), ResultCode.OK);
        } catch (RuntimeException e) {
            return new Result(ResourceBundleCache.getInstance().getString("error") + " - "  +
                    e.getMessage(), ResultCode.UPDATE_MENU);
        }
    }
}
