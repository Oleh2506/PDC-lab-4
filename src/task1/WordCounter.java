package task1;

import java.util.HashMap;
import java.util.concurrent.ForkJoinPool;

public class WordCounter {
    private static final ForkJoinPool forkJoinPool = new ForkJoinPool();

    public static String[] wordsIn(String line) {
        return line.trim().split("(\\s|\\p{Punct})+");
    }

    public static HashMap<Integer, Integer> countWordsByLengths(Document document) {
        HashMap<Integer, Integer> wordsByLengthsCount = new HashMap<>();

        for (String line : document.getLines()) {
            for (String word : wordsIn(line)) {
                int length = word.length();
                if (length > 0 && length <= 35) {
                    int count = 0;
                    if (wordsByLengthsCount.containsKey(length)) {
                        count = wordsByLengthsCount.get(length);
                    }
                    wordsByLengthsCount.put(length, count + 1);
                }
            }
        }

        return wordsByLengthsCount;
    }

    public static HashMap<Integer, Integer> mergeMaps(HashMap<Integer, Integer> hashMapA, HashMap<Integer, Integer> hashMapB) {
        HashMap<Integer, Integer> mergedHashMap = new HashMap<>(hashMapA);

        for (HashMap.Entry<Integer, Integer> entry : hashMapB.entrySet()) {
            int key = entry.getKey();
            int value = entry.getValue();
            mergedHashMap.merge(key, value, Integer::sum);
        }

        return mergedHashMap;
    }

    public static HashMap<Integer, Integer> countTotalWordsByLengthsOnSingleThread(Folder folder) {
        HashMap<Integer, Integer> totalWordsByLengths = new HashMap<>();

        for (Folder subFolder : folder.getSubFolders()) {
            HashMap<Integer, Integer> subFolderWordsByLengths = countTotalWordsByLengthsOnSingleThread(subFolder);
            totalWordsByLengths = WordCounter.mergeMaps(totalWordsByLengths, subFolderWordsByLengths);
        }

        for (Document document : folder.getDocuments()) {
            HashMap<Integer, Integer> docWordsByLengths = countWordsByLengths(document);
            totalWordsByLengths = WordCounter.mergeMaps(totalWordsByLengths, docWordsByLengths);
        }

        return totalWordsByLengths;
    }

    public static HashMap<Integer, Integer> countTotalWordsByLengthsFJ(Folder folder) {
        return forkJoinPool.invoke(new FolderWordCounterTask(folder));
    }
}
