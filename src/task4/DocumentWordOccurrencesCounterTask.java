package task4;

import task1.Document;

import java.util.HashMap;
import java.util.concurrent.RecursiveTask;

public class DocumentWordOccurrencesCounterTask extends RecursiveTask<HashMap<String, Boolean>> {
    private final Document document;
    private final String[] searchedWords;

    public DocumentWordOccurrencesCounterTask(Document document, String[] searchedWords) {
        this.document = document;
        this.searchedWords = searchedWords;
    }

    @Override
    protected HashMap<String, Boolean> compute() {
        HashMap<String, Boolean> res = new HashMap<>();
        boolean meetsConditions = WordOccurrencesCounter.doesDocMeetConditions(document, searchedWords, 100);
        res.put(document.getTitle(), meetsConditions);

        return res;
    }
}
