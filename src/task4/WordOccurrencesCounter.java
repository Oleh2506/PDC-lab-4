package task4;

import task1.Document;
import task1.Folder;

import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class WordOccurrencesCounter {
    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

    public static String[] wordsIn(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    public static long countOccurrences(Document document, String[] searchedWords) {
        long count = 0;
        for (String line : document.getLines()) {
            for (String word : wordsIn(line)) {
                for (String searchedWord : searchedWords) {
                    if (searchedWord.equals(word)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    public static boolean doesDocMeetConditions(Document document, String[] searchedWords, long threshold) {
        long count = countOccurrences(document, searchedWords);
        return count >= threshold;
    }

    public static HashMap<String, Boolean> getBooksBySearchedWords(Folder folder, String[] searchedWords) {
        return forkJoinPool.invoke(new FolderWordOccurrencesCounterTask(folder, searchedWords));
    }
}
