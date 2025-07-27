package constants;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    private static final String ALPHABET_RUS = "ЙЦУКЕНГШЩЗХЪФЫВАПРОЛДЖЭЯЧСМИТЬБЮ";
    //private static final String ALPHABET_ENG = "QWERTYUIOPASDFGHJKLZXCVBNM";
    private static final String ALPHABET_DIGITS = "1234567890";
    private static final String ALPHABET_PUNCTUATION_MARKS = "–!@#$%^&*()_—-+={}[]|\\:;'\".<>,«»? ";
    public static final char[] ALPHABET =  (ALPHABET_RUS + ALPHABET_RUS.toLowerCase() + ALPHABET_DIGITS +
            ALPHABET_PUNCTUATION_MARKS).toCharArray();
    public static final String TXT_FOLDER = System.clearProperty("user.dir") + File.separator + "src" +
            File.separator + "main" + File.separator + "resources" + File.separator;
    private static Map<Character, Integer> alphabetIndex = new HashMap<>();

    public static final int BUFFER_SIZE = 8192; // 8KB
    public static final int MAX_SAMPLE_SIZE = 1024 * 1024; // 1MB для анализа
    public static final int SAMPLE_SIZE = 50_000;
    public static final double FREQUENCY_THRESHOLD = 0.01;
    public static final int MAX_ANALYZE_SYMBOLS = 10000;

    static {
        for (int i = 0; i < ALPHABET.length; i++) {
            alphabetIndex.put(ALPHABET[i], i);
        }
    }

    public static Map<Character, Integer> getAlphabetIndex() {
        return alphabetIndex;
    }

    public static void setAlphabetIndex(Map<Character, Integer> alphabetIndex) {
        Constants.alphabetIndex = alphabetIndex;
    }
}
