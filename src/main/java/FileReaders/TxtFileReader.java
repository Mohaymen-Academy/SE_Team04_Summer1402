package FileReaders;

import Documents.Book;
import Documents.Document;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TxtFileReader implements FileReader {

    @Override
    public Document readFile(String filePath) {
         Book book = null;
        try {
            File file = new File(filePath);
            StringBuilder text = new StringBuilder();
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                text.append(data).append('\n');
            }
            myReader.close();
            book = new Book(file.getName(),text.toString());
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            System.exit(0);
        }
        return book;
    }

    @Override
    public ArrayList<Document> readFiles(String folderPath) {
        ArrayList<Document> documents = new ArrayList<>();
        File directoryPath = new File(folderPath);
        try {
            File[] filesList = directoryPath.listFiles();
            for (File file : filesList) {
                if (!hasValidExtension(file)) {
                    continue;
                }
                documents.add(this.readFile(file.getPath()));
            }
        } catch (Exception e) {
            System.out.println("Directory Not Found");
            System.exit(0);
        }
        return documents;
    }

    @Override
    public Boolean hasValidExtension(File file) {
        String fileName = file.getName();
        int index = fileName.lastIndexOf('.');
        return index > 0 && fileName.substring(index + 1).equals("txt");
    }
}
