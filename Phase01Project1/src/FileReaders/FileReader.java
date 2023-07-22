package FileReaders;

import Documents.Document;

import java.util.ArrayList;

public interface FileReader {
    Document readFile(String filePath);
    ArrayList<Document> readFiles(String folderPath);
}
