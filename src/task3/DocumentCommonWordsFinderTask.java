package task3;

import task1.Document;

import java.util.HashSet;
import java.util.concurrent.RecursiveTask;

public class DocumentCommonWordsFinderTask extends RecursiveTask<HashSet<String>> {
    private final Document document;

    public DocumentCommonWordsFinderTask(Document document) {
        this.document = document;
    }

    @Override
    protected HashSet<String> compute() {
        return CommonWordsFinder.findUniqueWords(document);
    }
}
