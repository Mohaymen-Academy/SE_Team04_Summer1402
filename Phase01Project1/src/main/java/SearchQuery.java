package main.java;

import main.java.Normalizer.Normalizable;
import main.java.Normalizer.TokenNormalization;
import main.java.Stemmer.PorterStemmer;

import java.util.ArrayList;

public class SearchQuery {
    public ArrayList<String> highPriorityWords = new ArrayList<>();
    public ArrayList<String> lowPriorityWords = new ArrayList<>();
    public ArrayList<String> redPriorityWords = new ArrayList<>();

    public Normalizable tokenNormalization;
    private PorterStemmer porterStemmer = new PorterStemmer();

    public SearchQuery(String query, Normalizable tokenNormalization){
        this.tokenNormalization = tokenNormalization;
        this.fillPriorityWordsList(query);
    }

    public SearchQuery(String  query){
        this.tokenNormalization = new TokenNormalization();
        this.fillPriorityWordsList(query);
    }
    private void fillPriorityWordsList(String query){
        for (String queryWord : query.split(" ")) {
            switch (queryWord.charAt(0)) {
                case '+' -> lowPriorityWords.add(
                        porterStemmer.stemWord(
                                tokenNormalization.makeNormalize(queryWord.substring(1))));
                case '-' -> redPriorityWords.add(
                        porterStemmer.stemWord(
                                tokenNormalization.makeNormalize(queryWord.substring(1))));
                default -> highPriorityWords.add(
                        porterStemmer.stemWord(
                                tokenNormalization.makeNormalize(queryWord)));
            }
        }
    }
}
