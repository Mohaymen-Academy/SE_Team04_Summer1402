package SearchFilters;

import Base.BaseTest;
import Documents.Document;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Or;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class OrSearchFilterTest extends BaseTest {

    private ArrayList<String> filterWords;
    private Map<String, ArrayList<Document>> wordDocuments;
    private ArrayList<Document> resultDocuments;
    private OrSearchFilter orSearchFilter;

    @Override
    @BeforeEach
    public void setUp(){
        super.setUp();
        filterWords = new ArrayList<>(Arrays.asList("design", "git", "mvc"));
        wordDocuments = new HashMap<>();
        wordDocuments.put("design", new ArrayList<>(Arrays.asList(documents.get(0), documents.get(1))));
        wordDocuments.put("git", new ArrayList<>(Arrays.asList(documents.get(0), documents.get(1), documents.get(2))));
        wordDocuments.put("mvc", new ArrayList<>(Arrays.asList(documents.get(1), documents.get(3))));
        wordDocuments.put("model", new ArrayList<>(Arrays.asList(documents.get(0), documents.get(1))));
        resultDocuments = new ArrayList<>(Arrays.asList(documents.get(0), documents.get(1), documents.get(2)));
    }

    @Test
    public void handleFilterWords_whenWordsHaveCommonDocuments(){
        orSearchFilter = new OrSearchFilter(filterWords, wordDocuments, resultDocuments);
        ArrayList<Document> actual = orSearchFilter.handleFilterWords();
        Assertions.assertEquals(4, actual.size());
        Assertions.assertTrue(actual.contains(documents.get(0)));
        Assertions.assertTrue(actual.contains(documents.get(1)));
        Assertions.assertTrue(actual.contains(documents.get(2)));
        Assertions.assertTrue(actual.contains(documents.get(3)));

    }

    @Test
    public void handleFilterWords_whenWordHaveNoDocument(){
        filterWords.add("mvvm");
        orSearchFilter = new OrSearchFilter(filterWords, wordDocuments, resultDocuments);
        ArrayList<Document> actual = orSearchFilter.handleFilterWords();
        Assertions.assertEquals(4, actual.size());
        Assertions.assertTrue(actual.contains(documents.get(0)));
        Assertions.assertTrue(actual.contains(documents.get(1)));
        Assertions.assertTrue(actual.contains(documents.get(2)));
        Assertions.assertTrue(actual.contains(documents.get(3)));
    }

    @Test
    public void applyToResult_whenResultDocumentIsSharingNoneWithFilterDocuments(){
        resultDocuments = new ArrayList<>(Arrays.asList(documents.get(4)));
        orSearchFilter = new OrSearchFilter(filterWords, wordDocuments, resultDocuments);
        ArrayList<Document> actual = orSearchFilter.applyToResult();
        Assertions.assertTrue(actual.isEmpty());
    }

    @Test
    public void applyToResult_whenResultDocumentIsEmpty(){
        resultDocuments = new ArrayList<>();
        orSearchFilter = new OrSearchFilter(filterWords, wordDocuments, resultDocuments);
        ArrayList<Document> actual = orSearchFilter.applyToResult();
        Assertions.assertTrue(actual.isEmpty());
    }
}
