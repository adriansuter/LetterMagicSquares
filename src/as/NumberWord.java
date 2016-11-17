package as;

/**
 * Represents a number as word (or multiple words) and calculates also its
 * length.
 *
 * @author Adrian Suter, https://github.com/adriansuter/
 */
public class NumberWord {

    private final String _word;
    private final int _length;

    /**
     * Constructor.
     *
     * @param word
     */
    public NumberWord(String word) {
        this._word = word;

        // We count only non-space characters.
        this._length = word.replaceAll("\\s+", "").length();
    }

    /**
     * Length of the word.
     *
     * @return
     */
    public int length() {
        return _length;
    }

    /**
     * The word.
     *
     * @return
     */
    public String word() {
        return _word;
    }

}
