package Normalizer;

public class TokenNormalizationToLowerCase implements Normalizable {
    private final String[] characterToDelete = {".","&","|",":",";","$","^","%","<",">","{","}","[","]","'",")","(","_"};
    private final String[] uselessWords = {"i","the","we","is","and","an","a"};


    @Override
    public String normalize(String token) {
        token = token.toLowerCase();
        for (String target : characterToDelete){
            token = token.replace(target,"");
        }
        for (String word : uselessWords){
            if (token.equals(word)){
                return "";
            }
        }
        return token;
    }
}
