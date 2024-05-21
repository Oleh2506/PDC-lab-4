package task3;

import task1.Folder;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;

public class Tester {
    public static void main(String[] args) throws IOException {
        Folder folder = Folder.fromDirectory(new File("books"));
        HashSet<String> commonWords = CommonWordsFinder.findCommonUniqueWords(folder);

        Object[] words = commonWords.toArray();
        for (int i = 0; i < words.length; i++) {
            System.out.print(words[i]);

            if (i != words.length - 1) {
                System.out.print(", ");
            }

            if (i % 25 == 0) {
                System.out.print("\n");
            }
        }
    }
}
