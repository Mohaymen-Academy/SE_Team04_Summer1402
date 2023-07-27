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

class TFIDFCalculatorTest extends BaseTest{
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
        double result = tfidfCalculator.tf(book,"test","\s\n");
        Assertions.assertInstanceOf(Double.class,result);
        Assertions.assertEquals(0.3333333333333333, result);
    }

    @Test
    void tf_checkReturnTypeAndValueWhenNoPatternFound(){
        Document book = this.documents.get(0);
        Mockito.when(normalizableMock.normalize("test2")).thenReturn("test2");
        double result = tfidfCalculator.tf(book,"test2","\s\n");
        Assertions.assertInstanceOf(Double.class,result);
        Assertions.assertEquals(0, result);
    }

    @Test
    void idf_checkReturnTypeAndValueWhenTwoDocHaveTheWord() {
        Mockito.when(normalizableMock.normalize("code")).thenReturn("code");
        double result = tfidfCalculator.idf(this.documents,"code","\s\n");
        Assertions.assertInstanceOf(Double.class,result);
        double Expected = this.documents.size() / 2.0;
        Assertions.assertEquals(Math.log(Expected), result);
    }
}