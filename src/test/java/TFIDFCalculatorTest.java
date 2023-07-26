import Documents.Document;
import Normalizer.Normalizable;
import Normalizer.TokenNormalization;
import TFIDFCalculator.TFIDFCalculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

class TFIDFCalculatorTest extends BaseTest{
    Normalizable normalizableMock = mock(TokenNormalization.class);
    TFIDFCalculator tfidfCalculator = new TFIDFCalculator(normalizableMock);
    public void setUp() {
        super.setUp();
    }

    @Test
    void tf_checkReturnTypeAndValueWhenPatternFound() {
        this.setUp();
        Document book = this.documents.get(0);
        Mockito.when(normalizableMock.normalize("test")).thenReturn("test");
        double result = tfidfCalculator.tf(book,"test","\s\n");
        Assertions.assertInstanceOf(Double.class,result);
        Assertions.assertEquals(0.3333333333333333, result);
        this.tearDown();
    }

    @Test
    void tf_checkReturnTypeAndValueWhenNoPatternFound(){
        this.setUp();
        Document book = this.documents.get(0);
        Mockito.when(normalizableMock.normalize("test2")).thenReturn("test2");
        double result = tfidfCalculator.tf(book,"test2","\s\n");
        Assertions.assertInstanceOf(Double.class,result);
        Assertions.assertEquals(0, result);
        this.tearDown();
    }

    @Test
    void idf_checkReturnTypeAndValueWhenTwoDocHaveTheWord() {
        this.setUp();
        Mockito.when(normalizableMock.normalize("code")).thenReturn("code");
        double result = tfidfCalculator.idf(this.documents,"code","\s\n");
        Assertions.assertInstanceOf(Double.class,result);
        double Expected = this.documents.size() / 2.0;
        Assertions.assertEquals(Math.log(Expected), result);
        this.tearDown();
    }
}