package SearchFilters;

import Base.BaseTest;
import Documents.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class AndSearchFilterTest extends BaseTest {

    private ArrayList<String> filterWords;
    private Map<String, ArrayList<Document>> wordDocuments;
    private ArrayList<Document> resultDocuments;
    private AndSearchFilter andSearchFilter;

    @Override
    @BeforeEach
    public void setUp(){
        super.setUp();
        filterWords = new ArrayList<>(Arrays.asList("java", "code", "design"));
        wordDocuments = new HashMap<>();
        wordDocuments.put("java", new ArrayList<Document>(Arrays.asList(documents.get(0), documents.get(1), documents.get(3))));
        wordDocuments.put("develop", new ArrayList<Document>(Arrays.asList(documents.get(1), documents.get(2))));
        wordDocuments.put("code", new ArrayList<Document>(Arrays.asList(documents.get(0), documents.get(1), documents.get(2), documents.get(3))));
        wordDocuments.put("design", new ArrayList<Document>(Arrays.asList(documents.get(0), documents.get(2), documents.get(3))));
        resultDocuments = new ArrayList<>(Arrays.asList(documents.get(0), documents.get(1), documents.get(4)));
    }

    @Test
    public void handleFilterWords_whenWordIsNotInDocuments_shouldReturnEmpty(){
        filterWords.add("ai");
        andSearchFilter = new AndSearchFilter(filterWords, wordDocuments, resultDocuments);
        Assertions.assertTrue(andSearchFilter.handleFilterWords().isEmpty());
    }

    @Test
    public void handleFilterWords(){
        andSearchFilter = new AndSearchFilter(filterWords, wordDocuments, resultDocuments);
        ArrayList<Document> actual = andSearchFilter.handleFilterWords();
        Assertions.assertTrue(actual.size() == 2);
        Assertions.assertTrue(actual.contains(documents.get(0)));
        Assertions.assertTrue(actual.contains(documents.get(3)));
    }

    @Test
    public void applyToResult_whenFilterWordsIsEmpty(){
        filterWords = new ArrayList<>();
        andSearchFilter = new AndSearchFilter(filterWords, wordDocuments, resultDocuments);
        ArrayList<Document> actual = andSearchFilter.applyToResult();
        Assertions.assertEquals(3, actual.size());
        Assertions.assertTrue(actual.contains(documents.get(0)));
        Assertions.assertTrue(actual.contains(documents.get(1)));
        Assertions.assertTrue(actual.contains(documents.get(4)));
    }

    @Test
    public void applyToResult_whenFilterDocumentsAndResultDocumentsSharingNoneInCommon(){
        resultDocuments = new ArrayList<>(Arrays.asList(documents.get(1), documents.get(4)));
        andSearchFilter = new AndSearchFilter(filterWords, wordDocuments, resultDocuments);
        ArrayList<Document> actual = andSearchFilter.applyToResult();
        Assertions.assertTrue(actual.isEmpty());
    }
}
