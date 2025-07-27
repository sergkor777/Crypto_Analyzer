package util;

import constants.Constants;
import exceptions.AppException;
import locale.ResourceBundleCache;

// Реализует функциональность шифра Цезаря и дешифровки
public final class Cipher {

    // Экземпляр Singleton
    private static final Cipher INSTANCE = new Cipher();

    private Cipher() { }

    public static Cipher getInstance() {
        return INSTANCE;
    }
    public String encrypt(String text, int shift) {
        // Логика шифрования
        if (text == null) {
            throw new AppException(ResourceBundleCache.getInstance().getString("null_input"));
        }
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '\n' || ch == '\r') {
                result.append(ch);
                continue;
            }
            if (Constants.getAlphabetIndex().containsKey(ch)) {
                int newIndex = (Constants.getAlphabetIndex().get(ch) + shift) % Constants.ALPHABET.length;
                if (newIndex < 0) {
                    newIndex += Constants.ALPHABET.length;
                }
                result.append(Constants.ALPHABET[newIndex]);
            }
        }
        return result.toString();
    }


    public String decrypt(String encryptedText, int shift) {
        if (encryptedText == null) {
            throw new AppException(ResourceBundleCache.getInstance().getString("null_input"));
        }
        StringBuilder result = new StringBuilder();
        try {
            for (int i = 0; i < encryptedText.length(); i++) {
                char ch = encryptedText.charAt(i);
                if (ch == '\n' || ch == '\r') {
                    result.append(ch);
                    continue;
                }
                if (Constants.getAlphabetIndex().containsKey(ch)) {
                    int newIndex = (Constants.getAlphabetIndex().get(ch) - shift) % Constants.ALPHABET.length;
                    if (newIndex < 0) {
                        newIndex += Constants.ALPHABET.length;
                    }
                    result.append(Constants.ALPHABET[newIndex]);
                } else {
                    throw new AppException(
                            ResourceBundleCache.getInstance().getString("invalid_symbol") +
                                    " - " + ch + ", key=" + shift
                    );
                }
            }
            return result.toString();
        } catch (RuntimeException e) {
            throw new AppException(
                    ResourceBundleCache.getInstance().getString("error"),
                    e
            );
        }
    }
}
