package task4;

import task1.Folder;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class Tester {
    public static void main(String[] args) throws IOException {
        Folder folder = Folder.fromDirectory(new File("books"));
        String[] searchedWords = new String[]{ "programming", "class", "method", "function", "algorithm" };

        HashMap<String, Boolean> result = WordOccurrencesCounter.getBooksBySearchedWords(folder, searchedWords);

        result.forEach((key, value) -> System.out.printf("Title: %-2s - Is about IT: %s%n", key, value));
    }
}
