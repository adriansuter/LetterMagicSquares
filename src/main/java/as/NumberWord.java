package as;

/**
 * Represents a number as word (or multiple words) and calculates also its
 * length.
 *
 * @author Adrian Suter, https://github.com/adriansuter/
 */
class NumberWord {

    private final String _word;
    private final int _length;

    /**
     * Constructor.
     *
     * @param word the number as a word
     */
    NumberWord(String word) {
        this._word = word;

        // We count only non-space characters.
        this._length = word.replaceAll("\\s+", "").length();
    }

    /**
     * Length of the word.
     *
     * @return the length of the number as a word
     */
    int length() {
        return _length;
    }

    /**
     * The word.
     *
     * @return the number as a word
     */
    String word() {
        return _word;
    }
    
}
