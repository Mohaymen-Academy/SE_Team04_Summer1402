package Base;

import Documents.Book;
import Documents.Document;
import java.util.ArrayList;

public class BaseTest {
    public ArrayList<Document> documents = new ArrayList<>();
    public void setUp(){
        fillDocuments();
    }

    public void fillDocuments(){
        documents.add(new Book("book1", "this is test"));
        documents.add(new Book("book2", "java code develop git"));
        documents.add(new Book("book3", "facade decorator"));
        documents.add(new Book("book4", "this is not test !!! code code and more code"));
        documents.add(new Book("book5", "- [Chapter 1: The Strategy Pattern - Welcome to Design Patterns](#chapter-1-welcome-to-design-\npatterns)"));
    }

    public void tearDown() {
        documents.clear();
    }
}
