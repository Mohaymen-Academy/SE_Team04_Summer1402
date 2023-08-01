import Base.BaseSearchQueryTest;
import Documents.Document;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;




public class EdgeNGramSearchQueryTest extends BaseSearchQueryTest {

    public Map<String, ArrayList<Document>> wordDocuments = new HashMap<>();


    @Test
    public void extractEdgeWords_whileLengthLessThanMinAndMax(){
        EdgeNGram edgeNGram = new EdgeNGram(wordDocuments);
        Assertions.assertTrue(edgeNGram.extractEdgeWords("java", 5, 6).isEmpty());
    }

    @Test
    public void extractEdgeWords_whileLengthGreaterThanOrEqualToMinAndMax(){
        EdgeNGram edgeNGram = new EdgeNGram(wordDocuments);
        ArrayList<String> actual = new ArrayList<>();
        actual.add("devel");
        actual.add("evelo");
        actual.add("velop");
        actual.add("develo");
        actual.add("evelop");
        actual.add("develop");
        Assertions.assertArrayEquals(edgeNGram.extractEdgeWords("develop", 5, 7).toArray(), actual.toArray());
    }

    @Test
    public void extractEdgeWords_whileMinAndMaxAreNotValid(){
        EdgeNGram edgeNGram = new EdgeNGram(wordDocuments);
        Assertions.assertThrows(InvalidParameterException.class, () -> edgeNGram.extractEdgeWords("", -1, -2).toArray());
    }

}