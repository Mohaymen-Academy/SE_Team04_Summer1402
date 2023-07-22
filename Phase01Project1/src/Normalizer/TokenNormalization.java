package Normalizer;
import Stemmer.PorterStemmer;

public class TokenNormalization implements Normalizable {
    private final String[] characterToDelete = {".","&","|",":",";","$","^","%","<",">","{","}","[","]","'",")","(","_"};
    private final String[] uselessWords = {"i","the","we","is","and","an","a"};


    @Override
    public String makeNormalize(String token) {
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

    public String makeNormalizeAndStem(String token){
        PorterStemmer porterStemmer = new PorterStemmer();
        return porterStemmer.stemWord(this.makeNormalize(token));
    }
}
