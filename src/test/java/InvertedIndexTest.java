import Base.BaseTest;
import Documents.Book;
import Documents.Document;
import Normalizer.TokenNormalization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class InvertedIndexTest extends BaseTest {

    @Override
    @BeforeEach
    public void setUp(){
        documents.add(new Book("book1", "Chapter 9 I Can’t Get This Class into a Test Harness"));
        documents.add(new Book("book2", "java,code,design,clean,search,SOLID,unit?!"));
    }

    @Test
    void fillWordDocument_whenDocumentIsValid(){
        InvertedIndex invertedIndex = new InvertedIndex(new TokenNormalization(), documents);
        invertedIndex.fillWordDocument("\s\n,");
        Map<String, ArrayList<Document>> wordDocuments = invertedIndex.getWordDocuments();
        assertTrue(wordDocuments.containsKey("chapter"));
        assertTrue(wordDocuments.containsKey("9"));
        assertTrue(wordDocuments.containsKey("can’t"));
        assertTrue(wordDocuments.containsKey("java"));
        assertTrue(wordDocuments.containsKey("code"));
        assertTrue(wordDocuments.containsKey("design"));
        assertTrue(wordDocuments.containsKey("clean"));
        assertTrue(wordDocuments.containsKey("search"));
        assertTrue(wordDocuments.containsKey("solid"));
        assertTrue(wordDocuments.containsKey("unit"));
    }


    @Test
    void fillWordDocument_whenDocumentsIsNull(){
        documents = null;
        InvertedIndex invertedIndex = new InvertedIndex(new TokenNormalization(), documents);
        assertThrows(InvalidParameterException.class, () -> invertedIndex.fillWordDocument(" "));
    }

}