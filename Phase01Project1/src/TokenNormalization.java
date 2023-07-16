public class TokenNormalization {
    final String[] characterToDelete = {".","&","|",":",";","$","^","%","<",">","{","}","[","]"};
    final String[] uselessWords = {"i","the","we","is","and","an","a"};
    public String makeNormalize(String token){
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
