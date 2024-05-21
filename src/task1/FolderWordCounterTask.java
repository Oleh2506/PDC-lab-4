package task1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderWordCounterTask extends RecursiveTask<HashMap<Integer, Integer>> {
    private final Folder folder;

    public FolderWordCounterTask(Folder folder) {
        this.folder = folder;
    }

    @Override
    protected HashMap<Integer, Integer> compute() {
        HashMap<Integer, Integer> wordsByLengthsCount = new HashMap<>();

        List<RecursiveTask<HashMap<Integer, Integer>>> tasks = new LinkedList<>();

        for (Folder subFolder : folder.getSubFolders()) {
            FolderWordCounterTask task = new FolderWordCounterTask(subFolder);
            tasks.add(task);
            task.fork();
        }

        for (Document document : folder.getDocuments()) {
            DocumentWordCounterTask task = new DocumentWordCounterTask(document);
            tasks.add(task);
            task.fork();
        }

        for (RecursiveTask<HashMap<Integer, Integer>> task : tasks) {
            wordsByLengthsCount = WordCounter.mergeMaps(wordsByLengthsCount, task.join());
        }

        return wordsByLengthsCount;
    }
}
