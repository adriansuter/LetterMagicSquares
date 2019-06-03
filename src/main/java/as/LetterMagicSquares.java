package as;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

/**
 * Searches 3x3 magic squares with corresponding number word length magic
 * squares.
 *
 * @author Adrian Suter, https://github.com/adriansuter/
 */
public class LetterMagicSquares {

    /**
     * The main method (entry point).
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String usage = "Usage:\n\n"
                + "java -jar LetterMagicSquares.jar <min> <max> [ <dictionary> ]\n\n"
                + "  where <min> and <max> need to be between 1 and 999 (the default\n"
                + "  dictionary contains only the words for these numbers) and the\n"
                + "  optional <dictionary> is the filename of the dictionary to\n"
                + "  be used.\n";
        if (args.length < 2) {
            System.out.println(usage);
            System.exit(0);
        }

        int min = 0;
        try {
            min = Integer.parseInt(args[0]);
            if (min < 1 || min > 999) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println(usage);
            System.exit(0);
        }

        int max = 0;
        try {
            max = Integer.parseInt(args[1]);
            if (max < 1 || max > 999) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            System.out.println(usage);
            System.exit(0);
        }

        String dictionaryFileName = "EnglishNumberDictionary.txt";
        if (args.length > 2) {
            dictionaryFileName = args[2];
        }

        try {
            NumberDictionary numberDictionary = new NumberDictionary(dictionaryFileName);

            TreeMap<Integer, ArrayList<String>> sortedLetterMagicSquares = new TreeMap<>();

            for (int a = 1; a <= max; a++) {
                for (int b = a + 1; b <= max; b++) {
                    for (int c = b + 1; c <= max; c++) {
                        if (a + b + c > max || c - a - b < min) {
                            // The smaller number has to be bigger than min
                            // and the biggest number has to be smaller than
                            // max. Otherwise we continue immediately.
                            continue;
                        }

                        // Exploit the general formula for 3x3 magic squares as 
                        // discovered by Édouard Lucas.
                        ArrayList<Integer> numbers = new ArrayList<>();
                        // First row...
                        numbers.add(c - b);
                        numbers.add(c + (a + b));
                        numbers.add(c - a);
                        // Second row...
                        numbers.add(c - (a - b));
                        numbers.add(c);
                        numbers.add(c + (a - b));
                        // Third row...
                        numbers.add(c + a);
                        numbers.add(c - (a + b));
                        numbers.add(c + b);

                        // Build the square.
                        Square square = new Square(3, numbers);

                        // Get the corresponding word length.
                        ArrayList<Integer> wordLengths = new ArrayList<>();
                        for (Integer number : numbers) {
                            wordLengths.add(numberDictionary.getWordLength(number));
                        }

                        // Build the word-length square.
                        Square wordLengthSquare = new Square(3, wordLengths);
                        if (wordLengthSquare.isMagic(false)) {
                            StringBuilder sb = new StringBuilder();
                            sb.append("\\section{Magic sum is ").append(square.sumOfFirstRow());
                            if (wordLengthSquare.isMagic()) {
                                sb.append(" - I am truly magic");
                            }
                            sb.append("}");
                            sb.append("$ \\left( \\begin{array}{ccc}\n");
                            for (int row = 0; row < 3; row++) {
                                if (row > 0) {
                                    sb.append("\\\\ \n");
                                }
                                for (int col = 0; col < 3; col++) {
                                    if (col > 0) {
                                        sb.append(" & ");
                                    }

                                    sb.append("\\begin{array}{c}\n");
                                    sb.append("\\rule{0pt}{1.5em}").append(square.getNumber(row, col)).append(" \\\\ \n");
                                    sb.append("\\text{\\color{blue}").append(numberDictionary.getWord(square.getNumber(row, col))).append("} \\\\ \n");
                                    sb.append("\\color{red}").append(wordLengthSquare.getNumber(row, col)).append(" \\vspace{.5em} \n");
                                    sb.append("\\end{array}");
                                }
                            }
                            sb.append("\\end{array} \\right) $\n");
                            sb.append("\n");

                            if (sortedLetterMagicSquares.containsKey(square.sumOfFirstRow())) {
                                sortedLetterMagicSquares.get(square.sumOfFirstRow()).add(sb.toString());
                            } else {
                                ArrayList<String> items = new ArrayList<>();
                                items.add(sb.toString());
                                sortedLetterMagicSquares.put(square.sumOfFirstRow(), items);
                            }
                        }
                    }
                }
            }

            // Write the tex file.
            File texFile = new File("LetterMagicSquares.tex");
            boolean ignored = texFile.createNewFile();
            try (BufferedWriter texWriter = new BufferedWriter(new FileWriter(texFile))) {
                texWriter.write("\\documentclass{article}\n");
                texWriter.write("\\usepackage{geometry}\n");
                texWriter.write("\\geometry{a4paper, portrait, left=1.5cm, right=1.5cm, top=2.5cm, bottom=2cm}\n");
                texWriter.write("\\usepackage{siunitx}\n");
                texWriter.write("\\sisetup{group-separator = \\text{\\,}}\n");
                texWriter.write("\\sisetup{group-minimum-digits = 3}\n");
                texWriter.write("\\usepackage{color}\n");
                texWriter.write("\\begin{document}\n");
                texWriter.write("\\begin{titlepage}\n");
                texWriter.write("\\centering\n");
                texWriter.write("{\\scshape\\LARGE Magic Squares Project\\par}\n");
                texWriter.write("\\vspace{1.5cm}\n");
                texWriter.write("{\\huge\\bfseries Letter Magic Squares\\par}\n");
                texWriter.write("\\vspace{1.5cm}\n");
                texWriter.write("{\\large $a_{i,j} \\in [" + min + ", " + max + "] \\hspace{1em} \\forall \\hspace{1em} i,j \\in [1,3]$\\par\n");
                texWriter.write("Dictionary: " + dictionaryFileName + "\\par\n}");
                texWriter.write("\\vspace{5cm}\n");
                texWriter.write("{\\Large\\itshape Adrian Suter\\par}\n");
                texWriter.write("\\vspace{1cm}\n");
                texWriter.write("{\\large \\today\\par}\n");
                texWriter.write("\\vfill\n");
                texWriter.write("generated and calculated by\\par\n");
                texWriter.write("https://github.com/adriansuter/LetterMagicSquares\\par\n");
                texWriter.write("\\vspace{1cm}\n");
                texWriter.write("inspired by Matt Parker\\par\n");
                texWriter.write("http://standupmaths.com/\\footnote{https://www.youtube.com/watch?v=cZ1W1vbuYuQ}\\par\n");
                texWriter.write("\\vspace{2cm}\n");
                texWriter.write("\\end{titlepage}\n");

                int counter = 0;
                for (Map.Entry<Integer, ArrayList<String>> entry : sortedLetterMagicSquares.entrySet()) {
                    for (String item : entry.getValue()) {
                        counter++;
                        if (counter > 1) {
                            texWriter.write("\\vspace{2em} \n");
                        }
                        texWriter.write(item);
                    }
                }

                texWriter.write("\\end{document}");
                texWriter.flush();
            }

            System.out.println("Now you can run:");
            System.out.println("pdflatex -synctex=1 -interaction=nonstopmode LetterMagicSquares.tex");
        } catch (IOException ex) {
            System.err.println(":( Number dictionary file could not be found.");
        } catch (Exception e) {
            System.err.println(":( Oops, there was an error.");
            e.printStackTrace();
        }
    }

}
