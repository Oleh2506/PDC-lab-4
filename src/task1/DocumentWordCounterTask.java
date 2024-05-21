package task1;

import java.util.HashMap;
import java.util.concurrent.RecursiveTask;

public class DocumentWordCounterTask extends RecursiveTask<HashMap<Integer, Integer>> {
    private final Document document;

    public DocumentWordCounterTask(Document document) {
        this.document = document;
    }

    @Override
    protected HashMap<Integer, Integer> compute() {
        return WordCounter.countWordsByLengths(document);
    }
}
