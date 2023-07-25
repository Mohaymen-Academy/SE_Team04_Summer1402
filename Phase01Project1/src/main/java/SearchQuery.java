package main.java;

import main.java.Normalizer.Normalizable;
import main.java.Normalizer.TokenNormalization;
import main.java.SearchQueryFilter.AndQueryHandler;
import main.java.SearchQueryFilter.OrQueryHandler;
import main.java.Stemmer.PorterStemmer;

import java.util.ArrayList;

public class SearchQuery {
    public Normalizable tokenNormalization;
    private PorterStemmer porterStemmer = new PorterStemmer();

    public SearchQuery(String query, Normalizable tokenNormalization){
        this.tokenNormalization = tokenNormalization;
    }

    public SearchQuery(String  query){
        this.tokenNormalization = new TokenNormalization();

    }

}
