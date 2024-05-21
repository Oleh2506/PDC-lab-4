package task3;

import task1.Document;
import task1.Folder;

import java.util.HashSet;
import java.util.concurrent.ForkJoinPool;

public class CommonWordsFinder {
    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

    public static String[] wordsIn(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    public static HashSet<String> findUniqueWords(Document document) {
        HashSet<String> uniqueWords = new HashSet<>();

        for (String line : document.getLines()) {
            for (String word : wordsIn(line)) {
                if (!word.isEmpty()) {
                    uniqueWords.add(word.toLowerCase());
                }
            }
        }

        return uniqueWords;
    }

    public static HashSet<String> mergeSets(HashSet<String> setA, HashSet<String> setB) {
        if (setA.isEmpty()) {
            return setB;
        }

        if (setB.isEmpty()) {
            return setA;
        }

        HashSet<String> mergedSet = new HashSet<>(setA);
        mergedSet.retainAll(setB);
        return mergedSet;
    }

    public static HashSet<String> findCommonUniqueWords(Folder folder) {
        return forkJoinPool.invoke(new FolderCommonWordsFinderTask(folder));
    }
}
