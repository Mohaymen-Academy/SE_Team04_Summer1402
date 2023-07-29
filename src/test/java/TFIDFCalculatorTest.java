import Base.BaseTest;
import Documents.Document;
import Normalizer.Normalizable;
import Normalizer.TokenNormalization;
import TFIDFCalculator.TFIDFCalculator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.Mockito.mock;

class TFIDFCalculatorTest extends BaseTest {
    Normalizable normalizableMock = mock(TokenNormalization.class);
    TFIDFCalculator tfidfCalculator = new TFIDFCalculator(normalizableMock);
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

    @Test
    void tf_checkReturnTypeAndValueWhenPatternFound() {
        Document book = this.documents.get(0);
        Mockito.when(normalizableMock.normalize("test")).thenReturn("test");
        book.getWordCounter().put("test",1L);
        book.setTextWordQuantity(3L);
        double result = tfidfCalculator.tf(book,"test");
        Assertions.assertInstanceOf(Double.class,result);
        Assertions.assertEquals(0.3333333333333333, result);
    }

    @Test
    void tf_checkReturnTypeAndValueWhenNoPatternFound(){
        Document book = this.documents.get(0);
        Mockito.when(normalizableMock.normalize("test2")).thenReturn("test2");
        book.setTextWordQuantity(4L);
        double result = tfidfCalculator.tf(book,"test2");
        Assertions.assertInstanceOf(Double.class,result);
        Assertions.assertEquals(0, result);
    }

    @Test
    void idf_checkReturnTypeAndValueWhenTwoDocHaveTheWord() {
        Mockito.when(normalizableMock.normalize("code")).thenReturn("code");
        documents.get(1).getWordCounter().put("code", 1L);
        documents.get(3).getWordCounter().put("code", 3L);
        double result = tfidfCalculator.idf(this.documents,"code");
        Assertions.assertInstanceOf(Double.class,result);
        double Expected = this.documents.size() / 2;
        Assertions.assertEquals(Math.log(Expected), result);
    }

    @Test
    void testWorkflow(){
        Assertions.assertTrue(false);
    }
}