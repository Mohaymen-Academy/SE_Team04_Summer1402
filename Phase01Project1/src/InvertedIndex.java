import java.util.*;

public class InvertedIndex {

    private Map<String, String> books;
    private Map<String, ArrayList<String>> bookWords;
    private TokenNormalization tokenNormalization;

    InvertedIndex(){
        books = TxtFileReader.getFileDataWithFileName();
        bookWords =  new HashMap<>();
        tokenNormalization = new TokenNormalization();
        this.prepareBooksWords();
    }
    private void prepareBooksWords() {
        for (String bookName : books.keySet()) {
            String[] currentBookWords = books.get(bookName).split("[ \\n]", -1);
            for (String word : currentBookWords) {
                word = tokenNormalization.makeNormalizeAndStem(word);
                if (this.bookWords.containsKey(word)) {
                    if (!this.bookWords.get(word).contains(bookName)) {
                        this.bookWords.get(word).add(bookName);
                    }
                } else {
                    this.bookWords.put(word, new ArrayList<>(Arrays.asList(bookName)));
                }
            }
        }
    }

    public HashSet<String> multiSearch(String query){
        ArrayList<String>highPriorityWords = new ArrayList<>();
        ArrayList<String>lowPriorityWords = new ArrayList<>();
        ArrayList<String>redPriorityWords = new ArrayList<>();
        ArrayList<String>result = new ArrayList<>();
        for (String queryWord : query.split(" "))
        {
            switch (queryWord.charAt(0)) {
                case '+' -> lowPriorityWords.add(tokenNormalization.makeNormalizeAndStem(
                        queryWord.substring(1)));
                case '-' -> redPriorityWords.add(tokenNormalization.makeNormalizeAndStem(
                        queryWord.substring(1)));
                default -> highPriorityWords.add(tokenNormalization.makeNormalizeAndStem(
                        queryWord));
            }
        }
        if(!highPriorityWords.isEmpty()){
            result = this.highPriorityWords(highPriorityWords);
        }
        if(!lowPriorityWords.isEmpty()){
            result.retainAll(this.lowPriorityWords(lowPriorityWords));
        }
        if(!redPriorityWords.isEmpty()){
            for(String bookNameToDelete : this.redPriorityWords(redPriorityWords)){
                result.remove(bookNameToDelete);
            }
        }
        return new HashSet<>(result);
    }

    private ArrayList<String> highPriorityWords(ArrayList<String> highPriorityWords){
        ArrayList<String>result = new ArrayList<>();
        if (!bookWords.containsKey(highPriorityWords.get(0))){
            return result;
        }
        result = bookWords.get(highPriorityWords.get(0));
        for (String highPriorityWord : highPriorityWords) {
            if (bookWords.containsKey(highPriorityWord)) {
                result.retainAll(bookWords.get(highPriorityWord));
            }
        }
        return result;
    }

    private ArrayList<String> lowPriorityWords(ArrayList<String> lowPriorityWords){
        ArrayList<String>result = new ArrayList<>();
        for (String lowPriorityWord : lowPriorityWords){
            if (bookWords.containsKey(lowPriorityWord)){
                result.addAll(bookWords.get(lowPriorityWord));
            }
        }
        return result;
    }

    private ArrayList<String> redPriorityWords(ArrayList<String> redPriorityWords){
        ArrayList<String>result = new ArrayList<>();
        for (String redPriorityWord : redPriorityWords){
            if (bookWords.containsKey(redPriorityWord)){
                result.addAll(bookWords.get(redPriorityWord));
            }
        }
        return result;
    }
}
