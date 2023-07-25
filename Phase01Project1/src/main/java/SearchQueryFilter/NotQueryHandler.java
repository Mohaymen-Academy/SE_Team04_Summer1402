package main.java.SearchQueryFilter;

public class NotQueryHandler extends QueryHandler {

    @Override
    public boolean canHandle(String token) {
        return token.startsWith("-");
    }

    @Override
    public void handle(String token) {
        queries.add(token.substring(1));
    }
}
