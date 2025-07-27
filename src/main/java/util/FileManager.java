package util;

import constants.Constants;
import exceptions.AppException;
import locale.ResourceBundleCache;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//чтение и запись файлов.
public class FileManager {
    private static final int DEFAULT_BUFFER_SIZE = 16384;
    private final int bufferSize;

    public FileManager() {
        this(DEFAULT_BUFFER_SIZE);
    }

    public FileManager(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    /*

    public String readFile(String filePath) {
        // Логика чтения файла маленького размера
        try {
            if (Files.isRegularFile(Path.of(filePath))) {
                Path path = Paths.get(filePath);
                byte[] bytes = Files.readAllBytes(path);
                return new String(bytes, StandardCharsets.UTF_8);
            }
            throw new AppException(ResourceBundleCache.getInstance().getString("file_not_exists") + " - " + filePath);
        } catch (IOException e) {
            throw new AppException(ResourceBundleCache.getInstance().getString("read_file_error") + " - " + filePath, e);
        }
    }


    public void writeFile(String content, String filePath) {
        // Логика записи файла маленького размера
        try {
            Files.write(Paths.get(filePath), content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new AppException(ResourceBundleCache.getInstance().getString("write_file_error") + " - " + filePath, e);
        }
    }
*/

    public void encryptFile(String sourceFilePath, String destFilePath, int key) {
        // Инициализация шифра
        Cipher cipher = Cipher.getInstance();
        Validator validator = Validator.getInstance();
        if (!validator.isValidKey(key, Constants.ALPHABET))
            throw new AppException(ResourceBundleCache.getInstance().getString("invalid_key"));
        if (!validator.isFileExists(sourceFilePath))
            throw new AppException(ResourceBundleCache.getInstance().getString("file_not_exists") + " - " + sourceFilePath);
        if (!validator.isTxtFile(destFilePath))
            throw new AppException(destFilePath + " - " + ResourceBundleCache.getInstance().getString("enter_text_file_name"));

        // Потоковое чтение, шифрование и запись
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(Path.of(sourceFilePath)), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(Files.newOutputStream(Path.of(destFilePath)), StandardCharsets.UTF_8))) {

            char[] buffer = new char[bufferSize];
            int charsRead;
            StringBuilder chunk = new StringBuilder();

            while ((charsRead = reader.read(buffer)) != -1) {
                chunk.append(buffer, 0, charsRead);
                // Дешифруем по блокам (если алгоритм поддерживает)
                String encrypted = cipher.encrypt(chunk.toString(), key);
                writer.write(encrypted);
                chunk.setLength(0);
            }
        } catch (IOException e) {
            throw new AppException(e);
        }
    }

    public void decryptFile(String sourceFilePath, String destFilePath, int key) {
        // Инициализация шифра
        Cipher cipher = Cipher.getInstance();
        Validator validator = Validator.getInstance();
        if (!validator.isValidKey(key, Constants.ALPHABET))
            throw new AppException(ResourceBundleCache.getInstance().getString("invalid_key"));
        if (!validator.isFileExists(sourceFilePath))
            throw new AppException(ResourceBundleCache.getInstance().getString("file_not_exists") + " - " + sourceFilePath);
        if (!validator.isTxtFile(destFilePath))
            throw new AppException(destFilePath + " - " + ResourceBundleCache.getInstance().getString("enter_text_file_name"));

        // Потоковое чтение, расшифровка и запись
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(Files.newInputStream(Path.of(sourceFilePath)), StandardCharsets.UTF_8));
             BufferedWriter writer = new BufferedWriter(
                     new OutputStreamWriter(Files.newOutputStream(Path.of(destFilePath)), StandardCharsets.UTF_8))) {

            char[] buffer = new char[bufferSize];
            int charsRead;
            StringBuilder chunk = new StringBuilder();

            while ((charsRead = reader.read(buffer)) != -1) {
                chunk.append(buffer, 0, charsRead);
                // Дешифруем по блокам (если алгоритм поддерживает)
                String decrypted = cipher.decrypt(chunk.toString(), key);
                writer.write(decrypted);
                chunk.setLength(0);
            }
        } catch (IOException e) {
            throw new AppException(e);
        }
    }

    public void bruteForceFile(String sourceFilePath, String destFilePath) {
        try {
            Validator validator = Validator.getInstance();
            FileManager fileManager = new FileManager();
            if (!validator.isFileExists(sourceFilePath)) {
                throw new AppException(ResourceBundleCache.getInstance().getString("file_not_exists"));
            }
            // Чтение только части файла для анализа
            String sampleText = readSample(sourceFilePath);
            int bestKey = findBestKey(sampleText);
            fileManager.decryptFile(sourceFilePath, destFilePath, bestKey);
        } catch (Exception e) {
            throw new AppException(
                    ResourceBundleCache.getInstance().getString("error") + " - " + e.getMessage(), e
            );
        }
    }

    //взлом файла методом статистического анализа
    public void staticAnalyzer(String encryptedFile, String dictionaryFile, String destFilePath) {
        try {
            Validator validator = Validator.getInstance();
            // 1.Валидация файлов
            if (!validator.isFileExists(encryptedFile)) {
                throw new AppException(ResourceBundleCache.getInstance().getString("file_not_exists") + " - " + encryptedFile);
            }
            if (!validator.isFileExists(dictionaryFile)) {
                throw new AppException(ResourceBundleCache.getInstance().getString("file_not_exists") + " - " + dictionaryFile);
            }
             // 2. Анализ частот в словаре
            Map<Character, Double> dictFreq = analyzeTextFrequencies(dictionaryFile);

            // 3. Анализ зашифрованного текста
            Map<Character, Double> cipherFreq = analyzeTextFrequencies(encryptedFile);

            // 4. Сопоставление частот и создание таблицы замены
            Map<Character, Character> substitutionMap = createSubstitutionMap(cipherFreq, dictFreq);

            // 5. Расшифровка с подстановкой
            applySubstitution(encryptedFile, destFilePath, substitutionMap);        } catch (Exception e) {
            throw new AppException(
                    ResourceBundleCache.getInstance().getString("error") + " - " + e.getMessage(),
                    e
            );
        }

    }


    private Map<Character, Double> analyzeTextFrequencies(String filePath) throws IOException {
        String text = readSample(filePath).toLowerCase();
        return calculateCharacterFrequency(text);
    }


    private Map<Character, Double> calculateCharacterFrequency(String text) {
        Map<Character, Integer> counts = new HashMap<>();
        int totalLetters = 0;

        // Подсчет букв (игнорируем пробелы, пунктуацию)
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                counts.put(c, counts.getOrDefault(c, 0) + 1);
                totalLetters++;
            }
        }

        // Расчет частот
        Map<Character, Double> frequencies = new HashMap<>();
        for (Map.Entry<Character, Integer> entry : counts.entrySet()) {
            double freq = (double) entry.getValue() / totalLetters;
            if (freq >= Constants.FREQUENCY_THRESHOLD) {
                frequencies.put(entry.getKey(), freq);
            }
        }

        return frequencies;
    }

    private Map<Character, Character> createSubstitutionMap(
            Map<Character, Double> cipherFreq,
            Map<Character, Double> dictFreq) {

        // Получаем отсортированные списки символов по частоте
        List<Character> cipherChars = getSortedByFrequency(cipherFreq);
        List<Character> dictChars = getSortedByFrequency(dictFreq);

        // Создаем таблицу замены
        Map<Character, Character> substitution = new HashMap<>();
        int minLength = Math.min(cipherChars.size(), dictChars.size());

        for (int i = 0; i < minLength; i++) {
            substitution.put(cipherChars.get(i), dictChars.get(i));
        }

        return substitution;
    }

    private List<Character> getSortedByFrequency(Map<Character, Double> freqMap) {
        return freqMap.entrySet().stream()
                .sorted(Map.Entry.<Character, Double>comparingByValue().reversed())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    private void applySubstitution(String inputFile, String outputFile,
                                   Map<Character, Character> substitution) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(inputFile));
             BufferedWriter writer = Files.newBufferedWriter(Paths.get(outputFile))) {

            int c;
            while ((c = reader.read()) != -1) {
                char original = (char) c;
                char lower = Character.toLowerCase(original);

                if (substitution.containsKey(lower)) {
                    char decrypted = substitution.get(lower);
                    writer.write(Character.isUpperCase(original) ?
                            Character.toUpperCase(decrypted) : decrypted);
                } else {
                    writer.write(original); // Оставляем символ как есть
                }
            }
        }
    }

    private String readSample(String filePath) {
        try (BufferedReader reader = new BufferedReader(
                new FileReader(filePath, StandardCharsets.UTF_8))) {
            char[] buffer = new char[Constants.MAX_SAMPLE_SIZE];
            int bytesRead = reader.read(buffer, 0, Constants.MAX_SAMPLE_SIZE);
            return new String(buffer, 0, bytesRead);
        } catch (IOException e) {
            throw new AppException(ResourceBundleCache.getInstance().getString("error"), e);
        }
    }

    private int findBestKey(String encryptedText) {
        int bestKey = 0;
        double maxSpaces = -1;
        Cipher cipher = Cipher.getInstance();

        for (int key = 0; key < Constants.ALPHABET.length; key++) {
            String decrypted = cipher.decrypt(encryptedText, key);
            double spaceRatio = calculateSpaceRatio(decrypted);

            if (spaceRatio > maxSpaces) {
                maxSpaces = spaceRatio;
                bestKey = key;
            }
        }
        return bestKey;
    }

    private double calculateSpaceRatio(String text) {
        int spaceCount = 0;
        for (int i = 0; i < Math.min(text.length(), 10000); i++) {
            if (text.charAt(i) == ' ') spaceCount++;
        }
        return (double) spaceCount / text.length();
    }
}

