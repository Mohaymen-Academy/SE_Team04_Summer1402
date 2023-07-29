package Tokenize;

import Tokenize.Tokenizable;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class Tokenizer implements Tokenizable {
    @Override
    public ArrayList<String> tokenize(String text, String delimiter) {
        ArrayList<String> tokens = new ArrayList<>();

        StringTokenizer st = new StringTokenizer(text, delimiter, false);
        while (st.hasMoreTokens()) {
            tokens.add(st.nextToken());
        }

        return tokens;
    }
}
