import Base.BaseSearchQueryTest;
import Normalizer.TokenNormalization;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.ArrayList;
import java.util.Arrays;

public class TokenNormalizationSearchQueryTest extends BaseSearchQueryTest {

    private TokenNormalization tokenNormalization;

    @BeforeEach
    public void setUp() {
        super.setUp();
        tokenNormalization = new TokenNormalization();
    }

    @Test
    public void testNormalize() {
        String token1 = tokenNormalization.normalize("/test.word");
        assertEquals("testword", token1);

        String token2 = tokenNormalization.normalize("TeSt");
        assertEquals("test", token2);

        String token3 = tokenNormalization.normalize("the");
        assertEquals("", token3);

        String token4 = tokenNormalization.normalize("We");
        assertEquals("", token4);

        String token5 = tokenNormalization.normalize("hello");
        assertEquals("hello", token5);
    }

    @Test
    public void testNormalizeArray() {
        ArrayList<String> inputTokens = new ArrayList<>(Arrays.asList("Hello", "/world", "i", "am", "a", "test", "for", "you"));
        ArrayList<String> expectedTokens = new ArrayList<>(Arrays.asList("hello", "world", "", "am", "", "test", "for", "you"));
        ArrayList<String> normalizedTokens = tokenNormalization.normalizeArray(inputTokens);
        assertEquals(expectedTokens, normalizedTokens);
    }
}
