package task3;

import task1.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.RecursiveTask;

public class FolderCommonWordsFinderTask extends RecursiveTask<HashSet<String>> {
    private final Folder folder;

    public FolderCommonWordsFinderTask(Folder folder) {
        this.folder = folder;
    }

    @Override
    protected HashSet<String> compute() {
        HashSet<String> commonWords = new HashSet<>();

        List<RecursiveTask<HashSet<String>>> tasks = new LinkedList<>();

        for (Folder subFolder : folder.getSubFolders()) {
            FolderCommonWordsFinderTask task = new FolderCommonWordsFinderTask(subFolder);
            tasks.add(task);
            task.fork();
        }

        for (Document document : folder.getDocuments()) {
            DocumentCommonWordsFinderTask task = new DocumentCommonWordsFinderTask(document);
            tasks.add(task);
            task.fork();
        }

        for (RecursiveTask<HashSet<String>> task : tasks) {
            commonWords = CommonWordsFinder.mergeSets(commonWords, task.join());
        }

        return commonWords;
    }
}
