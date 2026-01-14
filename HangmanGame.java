import java.io.*;
import java.util.*;

public class HangmanGame {

    private static final int MAX_ATTEMPTS = 6;

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        List<String> words = loadWords("words.txt");

        if (words.isEmpty()) {
            System.out.println("Word list is empty!");
            return;
        }

        System.out.println("🎮 WELCOME TO HANGMAN 🎮");

        boolean playAgain;

        do {
            String word = words.get(random.nextInt(words.size())).toLowerCase();
            char[] guessedWord = new char[word.length()];
            Arrays.fill(guessedWord, '_');

            Set<Character> guessedLetters = new HashSet<>();
            int attemptsLeft = MAX_ATTEMPTS;

            while (attemptsLeft > 0 && new String(guessedWord).contains("_")) {

                System.out.println("\nWord: " + displayWord(guessedWord));
                System.out.println("Guessed Letters: " + guessedLetters);
                System.out.println("Attempts Left: " + attemptsLeft);

                System.out.print("Enter a letter: ");
                char guess = scanner.next().toLowerCase().charAt(0);

                if (!Character.isLetter(guess) || guessedLetters.contains(guess)) {
                    System.out.println("Invalid or repeated guess!");
                    continue;
                }

                guessedLetters.add(guess);

                if (word.indexOf(guess) >= 0) {
                    for (int i = 0; i < word.length(); i++) {
                        if (word.charAt(i) == guess) {
                            guessedWord[i] = guess;
                        }
                    }
                } else {
                    attemptsLeft--;
                }
            }

            if (!new String(guessedWord).contains("_")) {
                System.out.println("\n🎉 You won! Word: " + word);
            } else {
                System.out.println("\n💀 You lost! Word was: " + word);
            }

            System.out.print("\nPlay again? (yes/no): ");
            playAgain = scanner.next().equalsIgnoreCase("yes");

        } while (playAgain);

        scanner.close();
    }

    private static List<String> loadWords(String fileName) throws IOException {
        List<String> words = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));

        String line;
        while ((line = br.readLine()) != null) {
            if (!line.trim().isEmpty()) {
                words.add(line.trim());
            }
        }
        br.close();
        return words;
    }

    private static String displayWord(char[] word) {
        StringBuilder sb = new StringBuilder();
        for (char c : word) {
            sb.append(c).append(" ");
        }
        return sb.toString();
    }
}
