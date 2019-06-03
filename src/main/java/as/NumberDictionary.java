package as;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * The number dictionary.
 *
 * @author Adrian Suter, https://github.com/adriansuter/
 */
class NumberDictionary {

    /**
     * The dictionary.
     */
    private final HashMap<Integer, NumberWord> _numberDictionary = new HashMap<>();

    /**
     * Constructor.
     *
     * @param fileName the file name
     */
    NumberDictionary(String fileName) throws IOException {
        int number = 0;
        File numberDictionaryFile = new File(fileName);
        try (BufferedReader numberDictionaryBufferedReader = new BufferedReader(new FileReader(numberDictionaryFile))) {
            while (numberDictionaryBufferedReader.ready()) {
                number++;
                String line = numberDictionaryBufferedReader.readLine();

                _numberDictionary.put(number, new NumberWord(line));
            }
        }
    }

    /**
     * Gets the number word given its number.
     *
     * @param number the number
     * @return the number as a word
     */
    String getWord(int number) throws IndexOutOfBoundsException {
        NumberWord numberWord = _numberDictionary.get(number);
        if (null == numberWord) {
            throw new IndexOutOfBoundsException();
        }
        return numberWord.word();
    }

    /**
     * Gets the length of the number word given its number.
     *
     * @param number the number
     * @return the length of the number as a word
     */
    int getWordLength(int number) throws IndexOutOfBoundsException {
        NumberWord numberWord = _numberDictionary.get(number);
        if (null == numberWord) {
            throw new IndexOutOfBoundsException();
        }
        return numberWord.length();
    }

}
