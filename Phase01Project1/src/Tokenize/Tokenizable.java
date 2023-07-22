package Tokenize;

import java.util.ArrayList;

public interface Tokenizable {
    ArrayList<String> tokenize(String text, String delimiter);
}

