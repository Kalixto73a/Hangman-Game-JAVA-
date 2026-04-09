package HangmanGame;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class HangmanGame {

    public static int lives = 6;
    public static Random random = new Random();
    public static String[] randomWordsList = {"cat", "dog", "sun", "moon", "book", "tree", "car", "fish", "bird", "milk", "apple", "house", "chair", "table", "water", "bread", "smile", "happy", "green", "blue", "red", "yellow", "star", "light", "phone", "clock", "plant", "river", "stone", "grass", "computer", "keyboard", "screen", "window", "garden", "flower", "pencil", "notebook", "school", "teacher", "student", "library", "picture", "holiday", "weather", "morning", "evening", "family", "brother", "sister", "country", "city", "village", "market", "travel", "airport", "station", "bottle", "pocket", "mirror", "elephant", "giraffe", "dolphin", "penguin", "kangaroo", "alligator", "butterfly", "umbrella", "chocolate", "sandwich", "spaghetti", "restaurant", "adventure", "mountain", "desert", "island", "volcano", "language", "question", "answer", "knowledge", "history", "science", "future", "culture", "freedom", "strength", "success", "failure", "imagination", "programming", "development", "javascript", "hangman", "algorithm", "database", "function", "variable", "condition", "iteration", "exception", "framework", "application", "performance", "architecture"};
    public static String word;
    public static String guessWord;
    public static String letter;
    public static ArrayList<Character> listOfLetters = new ArrayList<>();
    public static ArrayList<Character> listOfWrongLetters = new ArrayList<>();

    static void main(String[] args) {

        mainMessage();

    }

    public static void mainMessage() {
        var scanner = new Scanner(System.in);
        int option;

        System.out.println("""
                 _                                             \s
                | |                                            \s
                | |__   __ _ _ __   __ _ _ __ ___   __ _ _ __ \s
                | '_ \\ / _` | '_ \\ / _` | '_ ` _ \\ / _` | '_ \\\s
                | | | | (_| | | | | (_| | | | | | | (_| | | | |
                |_| |_|\\__,_|_| |_|\\__, |_| |_| |_|\\__,_|_| |_|
                                    __/ |                     \s
                                   |___/                      \s
                """);
        System.out.println("\uD83C\uDFAF Welcome to the hangman game \uD83C\uDFAF");
        System.out.println("Please select an option");

        do {
            System.out.println();
            System.out.println("1. Input a custom word to guess");
            System.out.println("2. Select a random word");
            System.out.println("3. Exit");
            System.out.print("> ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        setTheSecretWord();
                        break;
                    case 2:
                        setRandomWord();
                    case 3:
                        break;
                    default:
                        System.out.println("That's not a valid option. Try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please select a option (letters are not valid).");
                scanner.nextLine();
                option = 0;
            }

        } while (option != 3);
        System.out.println("Closing...");
    }

    public static void setTheSecretWord() {
        var scanner = new Scanner(System.in);

        System.out.println("Please enter the secret word");
        System.out.print("> ");

        word = scanner.nextLine().toLowerCase();

        checkTheSecretWordIsValid();

        theHangManGame();
    }

    public static void setRandomWord() {
        if (randomWordsList.length == 0) {
            System.out.println("Word list is empty!");
            return;
        }

        word = randomWordsList[random.nextInt(randomWordsList.length)];

        checkTheSecretWordIsValid();

        theHangManGame();
    }

    public static void theHangManGame() {
        var scanner = new Scanner(System.in);
        int option;

        do {
            System.out.println();
            System.out.println("Lives left : " + lives);
            System.out.println("The length of the word is: " + word.length());
            addPartsToTheHangMan();

            System.out.print("Word ");

            wordParse();

            System.out.println("List of wrong letters " + listOfWrongLetters);

            if (isWordGuessed()) {

                winMessage();
                break;

            }

            System.out.println("Please select what you want to do");
            System.out.println("1. Try guess the secret word");
            System.out.println("2. Try guess a letter");
            System.out.print("> ");

            try {
                option = scanner.nextInt();
                scanner.nextLine();

                switch (option) {
                    case 1:
                        if (guessTheWordCase()) return;
                        break;
                    case 2:
                        if (guessTheLetterCase()) return;
                        break;
                    default:
                        System.out.println("That's not a valid option. Try again.");
                        break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Please select a option (letters are not valid).");
                scanner.nextLine();
            }

        } while (lives != 0);

        if (lives == 0) {
            addPartsToTheHangMan();
            System.out.println("Game Over!\uD83D\uDC80 The word was: " + word);
            resetInitialValues();
        }
    }

    public static void wordParse() {
        for (int i = 0; i < word.length(); i++) {
            char currentChar = word.charAt(i);
            if (listOfLetters.contains(currentChar)) {
                System.out.print(currentChar);
            } else {
                System.out.print("-");
            }
        }
        System.out.println();
    }

    public static void checkTheSecretWordIsValid() {
        if (!checkTheInputIsNotEmpty() || !checkTheSecretWordForNoNumbers() || !checkTheSecretWordIsLongEnough())
            setTheSecretWord();
        System.out.println("The word has been selected");
    }

    public static boolean checkTheSecretWordIsLongEnough() {
        if (!(word.length() <= 1)) return true;

        System.out.println("There is no word that contains only 1 letter. Try again.");

        return false;
    }

    public static boolean checkTheSecretWordForNoNumbers() {
        boolean flag = true;
        for (int i = 0; flag && i < word.length(); i++) {
            char letter = word.charAt(i);
            if (!Character.isLetter(letter)) {
                System.out.println("The word can only contain letters. Try again.");
                flag = false;
            }
        }
        return flag;
    }

    public static boolean checkTheInputIsNotEmpty() {
        if (!word.isEmpty()) return true;

        System.out.println("You cant leave this input empty. Try again.");

        return false;
    }

    public static boolean isWordGuessed() {
        for (int i = 0; i < word.length(); i++) {
            if (!listOfLetters.contains(word.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static void guessTheWord() {
        var scanner = new Scanner(System.in);

        System.out.println("Enter the word");
        System.out.print("> ");
        guessWord = scanner.nextLine().toLowerCase();

        if (!guessWord.chars().allMatch(Character::isLetter)) {
            System.out.println("Only letters are allowed. Try again.");
        }
    }

    public static boolean checkTheGuessWord() {
        if (guessWord.equals(word)) {

            addPartsToTheHangMan();

            winMessage();

            return true;
        } else {
            lives--;
            System.out.println("That's not the word, you just lost a life");
            return false;
        }
    }

    public static boolean guessTheWordCase() {
        guessTheWord();
        return (checkTheGuessWord());
    }

    public static void guessALetter() {
        var scanner = new Scanner(System.in);

        System.out.println("Enter the character");
        System.out.print("> ");
        letter = scanner.nextLine().toLowerCase();

        checkTheGuessLetterInputIsValid();
        if (word.contains(letter)) {
            setAddTheLetterToTheList();
        }
    }

    public static void checkTheGuessLetterInputIsValid() {
        if (!checkTheGuessLetterIsNotEmpty() || !checkTheGuessLetterIsNotANumber() || !checkTheGuessLetterLengthIsCorrect())
            guessALetter();
    }

    public static boolean checkTheGuessLetterIsNotEmpty() {
        if (!letter.isEmpty()) return true;

        System.out.println("You cant leave this input empty. Try again.");

        return false;
    }

    public static boolean checkTheGuessLetterIsNotANumber() {
        if (Character.isLetter(letter.charAt(0))) return true;

        System.out.println("The letter cant be a number. Try again.");

        return false;
    }

    public static boolean checkTheGuessLetterLengthIsCorrect() {
        if (letter.length() == 1) return true;

        System.out.println("You can only input one character. Try again.");

        return false;
    }

    public static void setAddTheLetterToTheList() {
        listOfLetters.add(letter.charAt(0));
    }

    public static boolean checkTheGuessLetter() {
        String text = "";

        if (listOfLetters.contains(letter.charAt(0)) || listOfWrongLetters.contains(letter.charAt(0))) {
            text = "You cant say the same letter two times ";
        }
        if (!listOfWrongLetters.contains(letter.charAt(0)) && !listOfLetters.contains(letter.charAt(0))) {
            lives--;
            listOfWrongLetters.add(letter.charAt(0));
            text = "That letter is not part of the word ";
        }

        System.out.println(text);
        return false;
    }

    public static boolean guessTheLetterCase() {
        guessALetter();
        return (checkTheGuessLetter());
    }

    public static void winMessage() {
        System.out.println("\uD83C\uDF89 Congratulations! You guessed the word");

        resetInitialValues();
    }

    public static void resetInitialValues() {
        listOfLetters.clear();
        listOfWrongLetters.clear();
        lives = 6;
    }

    public static void addPartsToTheHangMan() {
        System.out.println("  --------- ");

        switch (lives) {
            case 6:
                System.out.println("  |         ");
                System.out.println("  |         ");
                System.out.println("  |         ");
                break;
            case 5:
                System.out.println("  |       o ");
                System.out.println("  |         ");
                System.out.println("  |         ");
                break;
            case 4:
                System.out.println("  |       o ");
                System.out.println("  |       | ");
                System.out.println("  |         ");
                break;
            case 3:
                System.out.println("  |       o ");
                System.out.println("  |      /| ");
                System.out.println("  |         ");
                break;
            case 2:
                System.out.println("  |       o  ");
                System.out.println("  |      /|\\");
                System.out.println("  |          ");
                break;
            case 1:
                System.out.println("  |       o  ");
                System.out.println("  |      /|\\");
                System.out.println("  |      /   ");
                break;
            case 0:
                System.out.println("  |       o  ");
                System.out.println("  |      /|\\");
                System.out.println("  |      / \\");
                break;
        }
        System.out.println("-----       ");
    }
}

