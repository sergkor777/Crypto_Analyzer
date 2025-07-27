import controllers.MainController;
import entity.Result;
import exceptions.AppException;
import locale.ResourceBundleCache;

import java.util.Arrays;

public class Application {
    private final MainController mainController;

    public Application() {
        mainController = new MainController();
    }

    public Result run(String[] args) {
        // sample args (Action name/source file_name/destination file_name/cryptography key)
        // encode source.txt encode.txt 7
        if (args.length > 0) {
            String action = args[0];   // encode
            //parameters source.txt encode.txt 7
            String[] parameters = Arrays.copyOfRange(args, 1, args.length);
            return  mainController.doAction(action, parameters); }
        else {
            throw new AppException(ResourceBundleCache.getInstance().getString("no_arguments_found"));
        }
    }
}
