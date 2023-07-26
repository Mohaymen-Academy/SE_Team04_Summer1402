import SearchQueryFilter.AndQueryHandler;
import SearchQueryFilter.NotQueryHandler;
import SearchQueryFilter.OrQueryHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.jupiter.api.Assertions.*;
class QueryHandlerTest extends BaseTest{

    AndQueryHandler andQueryHandler = new AndQueryHandler();
    OrQueryHandler orQueryHandler = new OrQueryHandler();
    NotQueryHandler notQueryHandler = new NotQueryHandler();
    @BeforeEach
    public void setUp() {
        super.setUp();
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
    }

    @Test
    void andQueryHandler_checkCorrectDataBeingHandled() throws Exception {
        andQueryHandler.handler("test");
        Assertions.assertEquals(
                new ArrayList<>(Collections.singleton("test")),
                andQueryHandler.queries
        );
    }

    @Test
    void orQueryHandler_checkCorrectDataBeingHandled() throws Exception {
        orQueryHandler.handler("+test");
        Assertions.assertEquals(
                new ArrayList<>(Collections.singleton("test")),
                orQueryHandler.queries
        );
    }

    @Test
    void notQueryHandler_checkCorrectDataBeingHandled() throws Exception {
        notQueryHandler.handler("-test");
        Assertions.assertEquals(
                new ArrayList<>(Collections.singleton("test")),
                notQueryHandler.queries
        );
    }

    @Test
    void queryHandler_throwExceptionOnInvalidInput(){
        InvalidParameterException thrown = assertThrows(
                InvalidParameterException.class,
                () -> andQueryHandler.handler("&test")
        );
        assertEquals("Invalid input", thrown.getMessage());
    }

    @Test
    void andWithOrQueryHandler_checkCorrectDataBeingHandled() throws Exception {
        andQueryHandler.setHandler(orQueryHandler);
        andQueryHandler.handler("test");
        andQueryHandler.handler("+java");
        andQueryHandler.handler("+code");
        Assertions.assertEquals(
                new ArrayList<>(Collections.singleton("test")),
                andQueryHandler.queries
        );
        Assertions.assertEquals(
                new ArrayList<>(Arrays.asList("java","code")),
                orQueryHandler.queries
        );
    }
}