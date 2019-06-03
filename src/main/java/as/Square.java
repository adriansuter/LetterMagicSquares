package as;

import java.util.ArrayList;

/**
 * A magic square.
 *
 * @author Adrian Suter, https://github.com/adriansuter/
 */
public class Square {

    /**
     * The numbers.
     */
    private final ArrayList<Integer> _numbers;

    /**
     * The order of the magic square.
     */
    private final int _order;

    /**
     * Constructor.
     *
     * @param order   the order
     * @param numbers the numbers of the square
     */
    Square(int order, ArrayList<Integer> numbers) {
        if (Math.pow(order, 2) != numbers.size()) {
            throw new IllegalArgumentException("Wrong number of numbers for the given order.");
        }

        this._numbers = numbers;
        this._order = order;
    }

    int getNumber(int row, int col) {
        return _numbers.get(row * _order + col);
    }

    boolean isMagic() {
        return isMagic(true);
    }

    boolean isMagic(boolean checkDistinct) {
        if (checkDistinct) {
            for (int i = 0; i < _numbers.size(); i++) {
                for (int j = i + 1; j < _numbers.size(); j++) {
                    if (_numbers.get(i) == _numbers.get(j)) {
                        return false;
                    }
                }
            }
        }

        int s = 0, sum = 0;
        // Check all the rows.
        for (int i = 0; i < _order; i++) {
            sum = 0;
            for (int j = 0; j < _order; j++) {
                sum += _numbers.get(i * _order + j);
            }
            if (i == 0) {
                // This is the first row.
                s = sum;
            } else if (sum != s) {
                return false;
            }
        }

        for (int i = 0; i < _order; i++) {
            sum = 0;
            for (int j = 0; j < _order; j++) {
                sum += _numbers.get(i + j * _order);
            }
            if (sum != s) {
                return false;
            }

        }

        sum = 0;
        for (int i = 0; i < _order; i++) {
            sum += _numbers.get(i * _order + i);
        }
        if (sum != s) {
            return false;
        }

        sum = 0;
        for (int i = 0; i < _order; i++) {
            sum += _numbers.get((i + 1) * _order - i - 1);
        }

        return sum == s;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < _order; row++) {
            if (row > 0) {
                sb.append("|");
            }

            for (int col = 0; col < _order; col++) {
                if (col > 0) {
                    sb.append(",");
                }
                sb.append(_numbers.get(row * _order + col));
            }
        }

        return sb.toString();
    }

    /**
     * @deprecated
     */
    public void rotate() {
        ArrayList<Integer> clone = (ArrayList<Integer>) _numbers.clone();

        for (int row = 0; row < _order; row++) {
            for (int col = 0; col < _order; col++) {
                _numbers.set(row * _order + col, clone.get((col + 1) * _order - (row + 1)));
            }
        }
    }

    /**
     * @deprecated
     */
    public void reflect() {
        ArrayList<Integer> clone = (ArrayList<Integer>) _numbers.clone();

        for (int row = 0; row < _order; row++) {
            for (int col = 0; col < _order; col++) {
                _numbers.set(row * _order + col, clone.get(row * _order + (_order - col - 1)));
            }
        }
    }

    int sumOfFirstRow() {
        int sum = 0;
        for (int i = 0; i < _order; i++) {
            sum += _numbers.get(i);
        }

        return sum;
    }

}
