package SearchQueryFilter;

public class AndQueryHandler extends QueryHandler{
    @Override
    public boolean canHandle(String token) {
        if(!super.canHandle(token)){
            return false;
        }
        char firstCharacter = token.toLowerCase().charAt(0);
        return (firstCharacter >= 'a' && firstCharacter <= 'z');
    }

    @Override
    public void handle(String token) {
        queries.add(token);
    }
}
