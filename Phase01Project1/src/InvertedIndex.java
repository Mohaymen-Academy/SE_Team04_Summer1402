import java.util.*;

public class InvertedIndex {

    private ArrayList<Document> documents;
    private Map<String, ArrayList<Document>> wordDocuments;
    private TokenNormalization tokenNormalization;
    private ArrayList<String>highPriorityWords;
    private ArrayList<String>lowPriorityWords;
    private ArrayList<String>redPriorityWords;

    public InvertedIndex(){
        documents = new ArrayList<>();
        wordDocuments =  new HashMap<>();
        tokenNormalization = new TokenNormalization();
        highPriorityWords = new ArrayList<>();
        lowPriorityWords = new ArrayList<>();
        redPriorityWords = new ArrayList<>();
    }

    public InvertedIndex(TokenNormalization tokenNormalization){
        this();
        this.tokenNormalization = tokenNormalization;
    }
    public void extractDocument(String folderPath) {
        this.documents = new TxtFileReader().readFiles(folderPath);
    }

    private void fillWordDocument(String delimiter){
        Tokenizer tokenizer = new Tokenizer();
        for(Document document : documents){
            for(String documentWord : tokenizer.tokenize(document.getText(), delimiter)){
                documentWord = tokenNormalization.makeNormalizeAndStem(documentWord);
                if(!wordDocuments.containsKey(documentWord))
                    wordDocuments.put(documentWord, new ArrayList<>());
                if(!wordDocuments.get(documentWord).contains(document))
                    wordDocuments.get(documentWord).add(document);
            }
        }
    }

    public void fillPriorityWordsList(String query){
        for (String queryWord : query.split(" "))
        {
            switch (queryWord.charAt(0)) {
                case '+' -> lowPriorityWords.add(tokenNormalization.makeNormalizeAndStem(queryWord.substring(1)));
                case '-' -> redPriorityWords.add(tokenNormalization.makeNormalizeAndStem(queryWord.substring(1)));
                default -> highPriorityWords.add(tokenNormalization.makeNormalizeAndStem(queryWord));
            }
        }
    }

    private boolean isValidDocument(){
        return (documents != null && !documents.isEmpty());
    }

    public HashSet<Document> advancedSearch(String query, String delimiter){
        if (!isValidDocument()){
            System.out.println("Document not preset");
            return new HashSet<>();
        }
        fillWordDocument(delimiter);
        fillPriorityWordsList(query);

        ArrayList<Document> result = new ArrayList<>();

        if(!highPriorityWords.isEmpty()){
            result = this.manageHighPriorityWords();
        }
        if(!lowPriorityWords.isEmpty()){
            result.retainAll(this.manageLowPriorityWords());
        }
        if(!redPriorityWords.isEmpty()){
            for(Document documentToDelete : this.manageRedPriorityWords()){
                result.removeIf(resultDocument -> resultDocument.equals(documentToDelete));
            }
        }
        return new HashSet<>(result);
    }

    private ArrayList<Document> manageHighPriorityWords(){
        ArrayList<Document> result = new ArrayList<>();
        if (!wordDocuments.containsKey(highPriorityWords.get(0))){
            return result;
        }
        result = wordDocuments.get(highPriorityWords.get(0));
        for (String highPriorityWord : highPriorityWords) {
            if (wordDocuments.containsKey(highPriorityWord)) {
                result.retainAll(wordDocuments.get(highPriorityWord));
            }
            else {
                return new ArrayList<>();
            }
        }
        return result;
    }

    private ArrayList<Document> manageLowPriorityWords(){
        ArrayList<Document>result = new ArrayList<>();
        for (String lowPriorityWord : lowPriorityWords){
            if (wordDocuments.containsKey(lowPriorityWord)){
                result.addAll(wordDocuments.get(lowPriorityWord));
            }
        }
        return result;
    }

    private ArrayList<Document> manageRedPriorityWords(){
        ArrayList<Document>result = new ArrayList<>();
        for (String redPriorityWord : redPriorityWords){
            if (wordDocuments.containsKey(redPriorityWord)){
                result.addAll(wordDocuments.get(redPriorityWord));
            }
        }
        return result;
    }


    public ArrayList<Document> getDocuments() {
        return documents;
    }

    public void setDocuments(ArrayList<Document> documents) {
        this.documents = documents;
    }
}
