import java.util.ArrayList;

public interface FileReader {
    public Document readFile(String filePath);
    public ArrayList<Document> readFiles(String folderPath);
}
