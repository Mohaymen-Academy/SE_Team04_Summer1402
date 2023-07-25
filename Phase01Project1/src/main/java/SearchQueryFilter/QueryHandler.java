package main.java.SearchQueryFilter;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

public abstract class QueryHandler implements SearchQueryHandler {

    private SearchQueryHandler nextHandler;
    public ArrayList<String> queries = new ArrayList<>();

    @Override
    public void handler(String token) throws Exception {
        if(canHandle(token)){
            handle(token);
            return;
        }
        if(nextHandler == null)
            throw new InvalidParameterException();
        nextHandler.handle(token);
    }

    @Override
    public SearchQueryHandler setHandler(SearchQueryHandler searchQueryHandler) {
        return nextHandler = searchQueryHandler;
    }

    @Override
    public boolean canHandle(String token){
        return (token.length() > 0);
    }

}
