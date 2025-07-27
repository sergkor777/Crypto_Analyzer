import constants.Constants;
import entity.Result;
import enums.ResultCode;
import locale.ResourceBundleCache;

import java.util.Scanner;

public class ConsoleRunner {

    public static void main(String[] args) {
        // sample args (Action name/source file_name/destination file_name/cryptography key)
        //encode source.txt encode.txt 7
        try {
            Application application = new Application();
            args = getArgs(args);
            Result result = application.run(args);
            while (result.getResultCode().equals(ResultCode.UPDATE_MENU)) {
                System.out.println(result.getMessage());
                System.out.println("===========================");
                args = new String[]{};
                args = getArgs(args);
                result = application.run(args);
            }
            System.out.println(result);
        } catch (RuntimeException e) {
            System.out.println(e.getMessage());
        }
    }


    public static String[][][] getQuestions() {
        return new String[][][]{
                {
                        {ResourceBundleCache.getInstance().getString("selected_language")},
                        {ResourceBundleCache.getInstance().getString("select_language"), "EN"},
                },
                {
                        {ResourceBundleCache.getInstance().getString("encode")},
                        {ResourceBundleCache.getInstance().getString("enter_source_path"),
                                Constants.TXT_FOLDER + "source.txt"},
                        {ResourceBundleCache.getInstance().getString("enter_destination_path"),
                                Constants.TXT_FOLDER + "encrypt.txt"},
                        {ResourceBundleCache.getInstance().getString("enter_crypto_key") +
                                "(1 - " + (Constants.ALPHABET.length - 1) + ")", "12"},
                },
                {
                        {ResourceBundleCache.getInstance().getString("decode")},
                        {ResourceBundleCache.getInstance().getString("enter_source_path_decode"),
                                Constants.TXT_FOLDER + "encrypt.txt"},
                        {ResourceBundleCache.getInstance().getString("enter_destination_path_decode"),
                                Constants.TXT_FOLDER + "decrypt.txt"},
                        {ResourceBundleCache.getInstance().getString("enter_crypto_key_decode") +
                                "(1 - " + (Constants.ALPHABET.length - 1) + ")", "12"},
                },
                {
                        {ResourceBundleCache.getInstance().getString("brute_force")},
                        {ResourceBundleCache.getInstance().getString("enter_source_path_brute_force"),
                                Constants.TXT_FOLDER + "encrypt.txt"},
                        {ResourceBundleCache.getInstance().getString("enter_destination_path_brute_force"),
                                Constants.TXT_FOLDER + "brute_force.txt"},
                },
                {
                        {ResourceBundleCache.getInstance().getString("analyze")},
                        {ResourceBundleCache.getInstance().getString("enter_source_path_analyze"),
                                Constants.TXT_FOLDER + "encrypt.txt"},
                        {ResourceBundleCache.getInstance().getString("enter_dictionary_path_analyze"),
                                Constants.TXT_FOLDER + "dictionary.txt"},
                        {ResourceBundleCache.getInstance().getString("enter_destination_path_analyze"),
                                Constants.TXT_FOLDER + "analyze.txt"},
                },
                {
                        {ResourceBundleCache.getInstance().getString("exit")},
                        {ResourceBundleCache.getInstance().getString("exit_confirm"), "Y"},
                },

        };
    }


    private static String[][][] questions = new String[][][]{};


    private static String[] getArgs(String[] args) {
        questions = getQuestions();
        if (args.length == 0) {
            Scanner scanner = new Scanner(System.in);
            int countParameters = getParameter(scanner);
            countParameters--;
            args = new String[questions[countParameters].length];
            args[0] = questions[countParameters][0][0];
            for (int i = 1; i < args.length; i++) {
                String question = questions[countParameters][i][0];
                System.out.print(question);
                String answer = scanner.nextLine();
                args[i] = answer.isEmpty() ? questions[countParameters][i][1] : answer;
            }
        }
        return args;
    }

    private static int getParameter(Scanner scanner) {
        int parameter;
        String messageSelectNode = ResourceBundleCache.getInstance().getString("message_select_node");
        String incorrectSelection = ResourceBundleCache.getInstance().getString("incorrect_selection");
        do {
            System.out.println(messageSelectNode);
            String input = scanner.nextLine();
            parameter = switch (input) {
                case "1" -> 1;
                case "2" -> 2;
                case "3" -> 3;
                case "4" -> 4;
                case "5" -> 5;
                case "6" -> 6;
                default -> {
                    System.out.println(incorrectSelection);
                    yield -1;
                }
            };
        } while (parameter < 0);
        return parameter;
    }
}
