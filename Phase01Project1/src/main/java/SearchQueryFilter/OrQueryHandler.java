package main.java.SearchQueryFilter;

public class OrQueryHandler extends QueryHandler{

    @Override
    public boolean canHandle(String token) {
        if(!super.canHandle(token)){
            return false;
        }
        return token.startsWith("+");
    }

    @Override
    public void handle(String token) {
        queries.add(token.substring(1));
    }
}
