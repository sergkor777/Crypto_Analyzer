package util;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//Валидация входных данных, таких как существование файла, допустимость ключа.
public final class Validator {

    private static final Validator INSTANCE = new Validator();

    private Validator() {}

    public static Validator getInstance() {
        return INSTANCE;
    }


    public boolean isValidKey(int key, char[] alphabet) {
        // Проверка ключа
        return (key >=0 && key <= alphabet.length-1);
    }
    public  boolean isFileExists(String filePath) {
        // Проверка существования файла
        return Files.exists(Path.of(filePath));
    }

    public boolean isTxtFile(String filePath) {
        if (filePath == null || filePath.isEmpty()) {
            return false;
        }
        Path path = Paths.get(filePath);
        String fileName = path.getFileName().toString();
        return fileName.toLowerCase().endsWith(".txt");
    }
}
