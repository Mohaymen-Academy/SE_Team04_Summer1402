package SearchFilters;

import Base.BaseTest;
import Documents.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.Not;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class NotSearchFilterTest extends BaseTest {

    private ArrayList<String> filterWords;
    private Map<String, ArrayList<Document>> wordDocuments;
    private ArrayList<Document> resultDocuments;
    private NotSearchFilter notSearchFilter;

    @Override
    @BeforeEach
    public void setUp(){
        super.setUp();
        filterWords = new ArrayList<>(Arrays.asList("facade", "proxy", "adaptor"));
        wordDocuments = new HashMap<>();
        wordDocuments.put("facade", new ArrayList<>(Arrays.asList(documents.get(0), documents.get(1))));
        wordDocuments.put("proxy", new ArrayList<>(Arrays.asList(documents.get(0), documents.get(1), documents.get(2))));
        wordDocuments.put("adaptor", new ArrayList<>(Arrays.asList(documents.get(1), documents.get(2))));
        wordDocuments.put("composite", new ArrayList<>(Arrays.asList(documents.get(0), documents.get(1))));
        resultDocuments = new ArrayList<>(Arrays.asList(documents.get(0), documents.get(1), documents.get(2)));
    }

    @Test
    public void handleFilterWords_whenWordsHaveCommonDocuments(){
        notSearchFilter = new NotSearchFilter(filterWords, wordDocuments, resultDocuments);
        ArrayList<Document> actual = notSearchFilter.handleFilterWords();
        Assertions.assertEquals(3, actual.size());
        Assertions.assertTrue(actual.contains(documents.get(0)));
        Assertions.assertTrue(actual.contains(documents.get(1)));
        Assertions.assertTrue(actual.contains(documents.get(2)));
    }

    @Test
    public void handleFilterWords_whenWordHasNoDocument(){
        filterWords.add("decorator");
        notSearchFilter = new NotSearchFilter(filterWords, wordDocuments, resultDocuments);
        ArrayList<Document> actual = notSearchFilter.handleFilterWords();
        Assertions.assertEquals(actual.size(), 3);
        Assertions.assertTrue(actual.contains(documents.get(0)));
        Assertions.assertTrue(actual.contains(documents.get(1)));
        Assertions.assertTrue(actual.contains(documents.get(2)));
    }

    @Test
    public void applyToResult(){
        resultDocuments.add(documents.get(3));
        notSearchFilter = new NotSearchFilter(filterWords, wordDocuments, resultDocuments);
        ArrayList<Document> actual = notSearchFilter.applyToResult();
        Assertions.assertEquals(actual.size(), 1);
        Assertions.assertTrue(actual.contains(documents.get(3)));
    }
}
