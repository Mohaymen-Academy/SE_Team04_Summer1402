import Base.BaseTest;
import Documents.Document;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;




public class EdgeNGramTest extends BaseTest {

    public Map<String, ArrayList<Document>> wordDocuments = new HashMap<>();


    @Test
    public void extractEdgeWords_whileLengthLessThanMinAndMax(){
        EdgeNGram edgeNGram = new EdgeNGram(wordDocuments);
        ArrayList<String> actual = new ArrayList<>();
        Assertions.assertArrayEquals(edgeNGram.extractEdgeWords("java", 5, 6).toArray(), actual.toArray());
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
        ArrayList<String> actual = new ArrayList<>();
        Assertions.assertArrayEquals(edgeNGram.extractEdgeWords("", -1, -2).toArray(), actual.toArray());
    }

}