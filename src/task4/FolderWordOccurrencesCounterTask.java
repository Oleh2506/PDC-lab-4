package task4;

import task1.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderWordOccurrencesCounterTask extends RecursiveTask<HashMap<String, Boolean>> {
    private final Folder folder;
    private final String[] searchedWords;

    public FolderWordOccurrencesCounterTask(Folder folder, String[] searchedWords) {
        this.folder = folder;
        this.searchedWords = searchedWords;
    }

    @Override
    protected HashMap<String, Boolean> compute() {
        HashMap<String, Boolean> results = new HashMap<>();

        List<RecursiveTask<HashMap<String, Boolean>>> tasks = new LinkedList<>();

        for (Folder subFolder : folder.getSubFolders()) {
            FolderWordOccurrencesCounterTask task = new FolderWordOccurrencesCounterTask(subFolder, searchedWords);
            tasks.add(task);
            task.fork();
        }

        for (Document document : folder.getDocuments()) {
            DocumentWordOccurrencesCounterTask task = new DocumentWordOccurrencesCounterTask(document, searchedWords);
            tasks.add(task);
            task.fork();
        }

        for (RecursiveTask<HashMap<String, Boolean>> task : tasks) {
            results.putAll(task.join());
        }

        return results;
    }
}
