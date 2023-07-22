package FileReaders;

import Documents.Book;
import Documents.Document;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class TxtFileReader implements FileReader {
    @Override
    public Document readFile(String filePath) {
         Book book = new Book("","");
        try {
            File file = new File(filePath);
            if (!hasValidExtension(file)){
                return book;
            }
            StringBuilder text = new StringBuilder();
            Scanner myReader = new Scanner(file);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                text.append(data).append('\n');
            }
            myReader.close();
            book = new Book(file.getName(),text.toString());
        } catch (Exception e) {
            System.out.println("Interrupted file");
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public ArrayList<Document> readFiles(String folderPath) {
        ArrayList<Document> documents = new ArrayList<>();
        File directoryPath = new File(folderPath);
        File[] filesList = directoryPath.listFiles();
        assert filesList != null;
        for(File file : filesList) {
            documents.add(this.readFile(file.getPath()));
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
