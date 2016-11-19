# Letter Magic Squares

Calculates letter magic squares and generates a TeX file which can be used to build a pdf.


## Example output

- [LetterMagicSquares.pdf](https://github.com/adriansuter/LetterMagicSquares/raw/master/LetterMagicSquares.pdf)


## Usage

Download the jar-file ([LetterMagicSquares.jar](https://github.com/adriansuter/LetterMagicSquares/raw/master/LetterMagicSquares.jar))
as well as the dictionary txt-file ([EnglishNumberDictionary.txt](https://github.com/adriansuter/LetterMagicSquares/raw/master/EnglishNumberDictionary.txt)).
Place both files into the same directory and run

```
java -jar LetterMagicSquares.jar <min> <max> [ <dictionary> ]
```

where `<min>` and `<max>` need to be between 1 and 999 (the default dictionary contains only the words for these numbers) and the optional `<dictionary>` is the filename of the dictionary to be used.


## Build the pdf

The command to build the pdf is as follows:

```
pdflatex -synctex=1 -interaction=nonstopmode LetterMagicSquares.tex
```

The example had been built with http://www.miktex.org/.


## Required TeX packages to build the pdf

- `geometry`
- `siunitx`
- `color`


## Development

The IDE for this project is NetBeans.
