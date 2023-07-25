package main.java.Normalizer;

import java.util.ArrayList;

public class TokenNormalization implements Normalizable {
    private final String[] characterToDelete = {"/",".","&","|",":",";","$","^","%","<",">","{","}","[","]","'",")","(","_","-"};
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

    public ArrayList<String> normalizeArray(ArrayList<String> tokens){
        ArrayList<String> result = new ArrayList<>();
        for(String token : tokens){
            result.add(this.normalize(token));
        }
        return result;
    }
}
