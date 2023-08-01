import Database.UserEntityQuery;
import Documents.Book;
import Entity.UserEntity;
import TFIDFCalculator.TFIDFCalculator;
import Documents.Document;
import FileReaders.TxtFileReader;
import Normalizer.TokenNormalization;
import SearchFilters.AndSearchFilter;
import SearchFilters.NotSearchFilter;
import SearchFilters.OrSearchFilter;
import SearchQueryFilter.AndQueryHandler;
import SearchQueryFilter.NotQueryHandler;
import SearchQueryFilter.OrQueryHandler;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class Main {

    private final static String folderPath = "/home/amirack/Code/Java/SE_Team04_Summer1402/src/main/resources/SoftwareBooksDataset";
    private static ArrayList<Document> result = new ArrayList<>();
    private static ArrayList<Book> sortedResult = new ArrayList<>();
    private static String query;
    private static TokenNormalization normalizer;
    private static TFIDFCalculator tfidfCalculator;
    private static ArrayList<Document> documents;

    public static void getInput() {
        System.out.println("Search query : ");
        Scanner scanner = new Scanner(System.in);
        String inputQuery = scanner.nextLine();
        if (inputQuery.isEmpty()) {
            System.out.println("No Input Query");
            System.exit(0);
        }
        query = inputQuery;
        normalizer = new TokenNormalization();
    }

    public static void setSearchResult(){
        TxtFileReader txtFileReader = new TxtFileReader();
        documents = txtFileReader.readFiles(folderPath);
        InvertedIndex invertedIndex = new InvertedIndex(normalizer, documents);
        try {
            invertedIndex.fillWordDocument("\s\n");
        }catch (Exception e){
            System.out.println("No Document");
            System.exit(0);
        }
        EdgeNGram edgeNGram = new EdgeNGram(invertedIndex.getWordDocuments());
        edgeNGram.fillEdgeWordDocuments(5,5);
        AndQueryHandler andQueryHandler = new AndQueryHandler();
        OrQueryHandler orQueryHandler = new OrQueryHandler();
        NotQueryHandler notQueryHandler = new NotQueryHandler();
        andQueryHandler.setHandler(orQueryHandler).setHandler(notQueryHandler);
        for(String token : query.split(" ")){
            try {
                andQueryHandler.handler(token);
            }catch (Exception e){
                System.out.println(e.getMessage());
                System.exit(0);
            }
        }
        ArrayList<String> highPriorityWords = (normalizer.normalizeArray(andQueryHandler.queries));
        ArrayList<String> lowPriorityWords = (normalizer.normalizeArray(orQueryHandler.queries));
        ArrayList<String> redPriorityWords = (normalizer.normalizeArray(notQueryHandler.queries));
        AndSearchFilter andSearchFilter = new AndSearchFilter(highPriorityWords, edgeNGram.getEdgeWordDocuments(), invertedIndex.getDocuments());
        result = andSearchFilter.applyToResult();
        OrSearchFilter orSearchFilter = new OrSearchFilter(lowPriorityWords, edgeNGram.getEdgeWordDocuments(), result);
        result = orSearchFilter.applyToResult();
        NotSearchFilter notSearchFilter = new NotSearchFilter(redPriorityWords, edgeNGram.getEdgeWordDocuments(), result);
        result = notSearchFilter.applyToResult();
        tfidfCalculator = new TFIDFCalculator(normalizer);
        tfIdfSort(
                highPriorityWords,
                lowPriorityWords
        );
    }
    public static void printSearchResult(){
        for(Book book : sortedResult){
            System.out.println(book.getName());
        }
        if(result.isEmpty())
            System.out.println("No result found");
        System.out.println("$ " + result.size());
    }

    public static void tfIdfSort(ArrayList<String> highPriorityWords, ArrayList<String> lowPriorityWords){
        for (Document doc : Main.result){
            double tfidf = 0;
            for (String highPriorityWord : highPriorityWords)
                tfidf += tfidfCalculator.tf(doc,highPriorityWord) * 3;
            for (String lowPriorityWord : lowPriorityWords)
                tfidf += tfidfCalculator.tfIdf(doc,Main.result,lowPriorityWord) * 2;
            Book book = (Book) doc;
            book.setTfIdf(tfidf);
            sortedResult.add(book);
        }
        sortedResult.sort(Book.tfIdfComparator);
    }

    public static void main(String[] args) throws SQLException {
//        getInput();
//        setSearchResult();
//        printSearchResult();\
        UserEntity userEntity = new UserEntity(
                "amirack",
                new Timestamp(System.currentTimeMillis()),
                "bio",
                "09353355339",
                " ",
                "Amir",
                "Hosein",
                "1234"
        );
//        UserEntityQuery.addNewUser(userEntity);
        UserEntity userEntity2 = new UserEntity(
                "hosein",
                new Timestamp(System.currentTimeMillis()),
                "bio",
                "09210851073",
                " ",
                "hosein",
                "amir",
                "4321"
        );
//        UserEntityQuery.addNewUser(userEntity2);
        UserEntityQuery.updateUserBioByUsername("amirack", "this is update command");
    }
}