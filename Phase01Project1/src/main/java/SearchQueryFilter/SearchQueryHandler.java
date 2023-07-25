package main.java.SearchQueryFilter;

public interface SearchQueryHandler {

    void handler(String token) throws Exception;
    SearchQueryHandler setHandler(SearchQueryHandler searchQueryHandler);
    boolean canHandle(String token);
    void handle(String token);
}
