package main.java.FileReaders;

import main.java.Documents.Document;

import java.io.File;
import java.util.ArrayList;

public interface FileReader {
    Document readFile(String filePath);
    ArrayList<Document> readFiles(String folderPath);
    Boolean hasValidExtension(File file);
}
